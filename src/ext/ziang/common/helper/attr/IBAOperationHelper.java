package ext.ziang.common.helper.attr;

import com.ptc.arbortext.windchill.metadata.MetadataService;
import com.ptc.core.components.util.OidHelper;
import com.ptc.core.htmlcomp.util.TypeHelper;
import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.AttributeTypeIdentifier;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.UpdateOperationIdentifier;
import com.ptc.tml.NewTmlResource;
import com.ptc.tml.TranslationException;
import com.ptc.tml.utils.TMLContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.epm.EPMDocumentMasterIdentity;
import wt.fc.IdentityHelper;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.collections.WTKeyedHashMap;
import wt.iba.definition.litedefinition.*;
import wt.iba.definition.service.StandardIBADefinitionService;
import wt.iba.value.AttributeContainer;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.IBAHolder;
import wt.iba.value.litevalue.*;
import wt.iba.value.service.IBAValueDBService;
import wt.iba.value.service.IBAValueHelper;
import wt.services.ServiceFactory;
import wt.units.service.MeasurementSystemDefaultView;
import wt.util.*;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class IBAOperationHelper {
    private static final Logger log = LoggerFactory.getLogger(IBAOperationHelper.class);
    private static final String NEWRESOURCE = NewTmlResource.class.getName();
    public static final MetadataService metadataService =
        (MetadataService)ServiceFactory.getService(MetadataService.class);

    /**
     * 获取所有的IBA属性
     * 
     * @param ibaNameList IBA Name
     * @param ibaHolder IBA属性持有者
     * @return
     */
    public static Map<String, Object> findIBAValueByNameList(List<String> ibaNameList, IBAHolder ibaHolder) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        try {
            Collection<String> ibaNames = new HashSet<>();
            // 刷新最新的属性和约束条件
            ibaHolder = IBAValueHelper.service.refreshAttributeContainerWithoutConstraints(ibaHolder);
            //
            DefaultAttributeContainer attributeContainer = (DefaultAttributeContainer)ibaHolder.getAttributeContainer();
            AbstractValueView[] attributeValues = attributeContainer.getAttributeValues();
            if (ArrayUtils.isEmpty(attributeValues)) {
                return map;
            }
            // 遍历所有的值视图
            for (AbstractValueView attributeValue : attributeValues) {
                // 获取IBA属性
                AttributeDefDefaultView definition = attributeValue.getDefinition();
                String name = definition.getName();
                if (CollectionUtils.isNotEmpty(ibaNameList) && !ibaNameList.contains(name)) {
                    continue;
                }
                if (StringUtils.isNotBlank(name)) {
                    ibaNames.add(name);
                }
            }
            // 获取当前值
            PersistableAdapter adapter = new PersistableAdapter((Persistable)ibaHolder, null, Locale.CHINA, null);
            adapter.load(ibaNames);
            for (String ibaName : ibaNames) {
                map.put(ibaName, adapter.get(ibaName));
            }
        } catch (Exception e) {
            log.error("findIBAValueByNameList Exception Message:", e);
        }
        return map;
    }

    public static Map<String, Object> findAllIBAValue(IBAHolder ibaHolder) {
        return findIBAValueByNameList(null, ibaHolder);
    }

    /**
     * 
     * @param var0
     * @param var1
     * @param var2
     * @param var3
     * @throws WTException
     * @throws RemoteException
     * @throws WTPropertyVetoException
     */
    public static void copyIbasAndSetLanguage(EPMDocument var0, EPMDocument var1, String var2, TMLContext var3)
        throws WTException, RemoteException, WTPropertyVetoException {
        String var4 = var3.getDocumentLanguageAttributeId();
        boolean var5 = var1.getPersistInfo().isPersisted();
        Locale var6 = WTContext.getContext().getLocale();
        TypeIdentifier var7 = OidHelper.getTypeIdentifier(var0);
        Set var8 = TypeHelper.getSoftAttributes(var7);
        PersistableAdapter var9 = new PersistableAdapter(var0, (String)null, var6, new UpdateOperationIdentifier());
        PersistableAdapter var10 = new PersistableAdapter(var1, (String)null, var6, new UpdateOperationIdentifier());
        ArrayList var11 = new ArrayList();
        Object var12 = null;
        if (var9 != null && var8 != null && var10 != null) {
            Iterator var13 = var8.iterator();

            while (var13.hasNext()) {
                AttributeTypeIdentifier var14 = (AttributeTypeIdentifier)var13.next();
                String var15 = var14.toLogicalForm();
                var11.add(var15);
            }

            if (!var11.isEmpty()) {
                var9.load(var11);
                var10.load(var11);
                var13 = var11.iterator();

                while (var13.hasNext()) {
                    String var16 = (String)var13.next();
                    if (var16.equals(var4)) {
                        var1 = (EPMDocument)setIba(var1, var16, var2, false);
                    } else {
                        var12 = var9.get(var16);
                        if (var12 != null && !"PTC_DD_EXTENDED_DOC_TYPE".equalsIgnoreCase(var16)) {
                            var1 = (EPMDocument)setIba(var1, var16, var9.get(var16), false);
                        }
                    }
                }
            }
        }

        if (var5) {
            updateIBAHolder(var1);
        }

    }

    public static EPMDocument setLanguageAttribute(EPMDocument var0, String var1, TMLContext var2)
        throws WTPropertyVetoException, RemoteException, WTException {
        log.trace("setLanguageAttribute - entered");
        String var3 = var2.getDocumentLanguageAttributeId();
        var0 = (EPMDocument)setStringIBAValue(var0, var3, var1);
        if (PersistenceHelper.isPersistent(var0) && !updateIBAHolder(var0)) {
            throw new WTException(NEWRESOURCE, "EXCEPTION_UNABLE_TO_UPDATE_IBAHOLDER", (Object[])null);
        } else {
            log.trace("setLanguageAttribute - exiting");
            return var0;
        }
    }

    private static IBAHolder deleteIBAValue(IBAHolder var0, String var1) throws WTException, RemoteException {
        DefaultAttributeContainer var2 = (DefaultAttributeContainer)var0.getAttributeContainer();
        if (var2 == null) {
            return var0;
        } else {
            AttributeDefDefaultView var3 = getAttributeDefDefaultView(var1);
            if (var3 == null) {
                throw new WTException("No attribute container found for " + var1);
            } else {
                AbstractValueView[] var4 = var2.getAttributeValues(var3);
                if (var4.length == 0) {
                    return var0;
                } else {
                    var2.deleteAttributeValue(var4[0]);
                    var0.setAttributeContainer(var2);
                    return var0;
                }
            }
        }
    }

    public static AttributeDefDefaultView getAttributeDefDefaultView(String var0) throws RemoteException, WTException {
        StandardIBADefinitionService var1 = new StandardIBADefinitionService();
        AttributeDefDefaultView var2 = var1.getAttributeDefDefaultViewByPath(var0);
        return var2;
    }

    private static DefaultAttributeContainer getRefreshableAttributeContainer(IBAHolder var0, boolean var1)
        throws WTException, RemoteException {
        DefaultAttributeContainer var2 = (DefaultAttributeContainer)var0.getAttributeContainer();
        if (var2 == null || PersistenceHelper.isPersistent(var0) && var1) {
            var0 = IBAValueHelper.service.refreshAttributeContainer(var0, (Object)null, (Locale)null,
                (MeasurementSystemDefaultView)null);
            var2 = (DefaultAttributeContainer)var0.getAttributeContainer();
        }

        return var2;
    }

    public static String getStringIBAValue(IBAHolder var0, String var1) throws WTException {
        return getStringIBAValue(var0, var1, false);
    }

    public static String getStringIBAValue(IBAHolder var0, String var1, boolean var2) throws WTException {
        try {
            DefaultAttributeContainer var3 = getRefreshableAttributeContainer(var0, var2);
            if (var3 == null) {
                return null;
            } else {
                AttributeDefDefaultView var4 = getAttributeDefDefaultView(var1);
                ArrayList var5 = new ArrayList();
                AbstractValueView[] var6 = var3.getAttributeValues(var4);
                int var7 = var6.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    AbstractValueView var9 = var6[var8];
                    if (var9.getState() != 2) {
                        var5.add(var9);
                    }
                }

                StringBuffer var11 = new StringBuffer();
                if (var5.size() > 1) {
                    Iterator var12 = var5.iterator();

                    while (var12.hasNext()) {
                        AbstractValueView var13 = (AbstractValueView)var12.next();
                        if (var13 instanceof StringValueDefaultView) {
                            var11.append(((StringValueDefaultView)var13).getValueAsString()).append('|');
                        } else if (var13 instanceof BooleanValueDefaultView) {
                            var11.append(((BooleanValueDefaultView)var13).getValueAsString()).append('|');
                        }
                    }

                    return var11.toString();
                } else if (var5.size() == 1) {
                    if (var5.get(0) instanceof StringValueDefaultView) {
                        return ((StringValueDefaultView)var5.get(0)).getValueAsString();
                    } else if (var5.get(0) instanceof BooleanValueDefaultView) {
                        return ((BooleanValueDefaultView)var5.get(0)).getValueAsString();
                    } else {
                        return ((AbstractValueView)var5.get(0)).toString();
                    }
                } else {
                    return "";
                }
            }
        } catch (RemoteException var10) {
            var10.printStackTrace();
            return null;
        }
    }

    private static IBAHolder setBooleanIBAValue(IBAHolder var0, String var1, String var2)
        throws WTException, WTPropertyVetoException, RemoteException {
        boolean var3 = false;
        var3 = Boolean.valueOf(var2);

        try {
            DefaultAttributeContainer var4 = getRefreshableAttributeContainer(var0, false);
            AttributeDefDefaultView var5 = getAttributeDefDefaultView(var1);
            if (!(var5 instanceof BooleanDefView)) {
                String var10 =
                    WTMessage.getLocalizedMessage(NEWRESOURCE, "EXCEPTION_IBA_NOT_BOOL", new Object[] {var1});
                throw new WTException(var10);
            }

            BooleanDefView var6 = (BooleanDefView)var5;
            AbstractValueView[] var7 = var4.getAttributeValues(var6);
            BooleanValueDefaultView var8;
            if (var7.length == 0) {
                var8 = new BooleanValueDefaultView(var6, var3);
                var4.addAttributeValue(var8);
            } else {
                var8 = (BooleanValueDefaultView)var7[0];
                var8.setValue(var3);
                var4.updateAttributeValue(var8);
            }

            var0.setAttributeContainer(var4);
        } catch (WTException var9) {
            var9.printStackTrace();
        }

        return var0;
    }

    private static IBAHolder setFloatIBAValue(IBAHolder var0, String var1, String var2)
        throws WTException, RemoteException, WTPropertyVetoException {
        var2 = var2.trim();
        double var3 = 0.0;
        if (var2 != null && !"".equals(var2)) {
            var3 = Double.valueOf(var2);
            DefaultAttributeContainer var5 = getRefreshableAttributeContainer(var0, false);
            AttributeDefDefaultView var6 = getAttributeDefDefaultView(var1);
            if (!(var6 instanceof FloatDefView)) {
                String var10 =
                    WTMessage.getLocalizedMessage(NEWRESOURCE, "EXCEPTION_IBA_NOT_FLOAT", new Object[] {var1});
                throw new WTException(var10);
            } else {
                FloatDefView var7 = (FloatDefView)var6;
                AbstractValueView[] var8 = var5.getAttributeValues(var7);
                FloatValueDefaultView var9;
                if (var8.length == 0) {
                    var9 = new FloatValueDefaultView(var7, var3, NumericToolkitUtil.countSigFigs(var2));
                    var5.addAttributeValue(var9);
                } else {
                    var9 = (FloatValueDefaultView)var8[0];
                    var9.setValue(var3);
                    var5.updateAttributeValue(var9);
                }

                var0.setAttributeContainer(var5);
                return var0;
            }
        } else {
            return var0;
        }
    }

    public static IBAHolder setIba(IBAHolder var0, String var1, Object var2)
        throws WTException, RemoteException, WTPropertyVetoException {
        return setIba(var0, var1, var2, true);
    }

    public static IBAHolder setIba(IBAHolder var0, String var1, Object var2, boolean var3)
        throws WTException, RemoteException, WTPropertyVetoException {
        var0 = deleteIBAValue(var0, var1);
        if (var2 instanceof Boolean) {
            var0 = setBooleanIBAValue(var0, var1, var2.toString());
        } else if (var2 instanceof String) {
            var0 = setStringIBAValue(var0, var1, (String)var2);
        } else if (var2 instanceof Float) {
            var0 = setFloatIBAValue(var0, var1, var2.toString());
        } else if (var2 instanceof Integer) {
            var0 = setIntegerIBAValue(var0, var1, var2.toString());
        } else {
            if (!(var2 instanceof Timestamp)) {
                throw new WTException(NEWRESOURCE, "EXCEPTION_UNSUPPORTED_TYPE",
                    new Object[] {var2.getClass().getName()});
            }

            var0 = setTimestampValue(var0, var1, (Timestamp)var2);
        }

        if (var3 && PersistenceHelper.isPersistent(var0)) {
            if (updateIBAHolder(var0)) {
                return var0;
            } else {
                throw new WTException(NEWRESOURCE, "EXCEPTION_UNABLE_TO_UPDATE_IBAHOLDER", (Object[])null);
            }
        } else {
            return var0;
        }
    }

    private static IBAHolder setIntegerIBAValue(IBAHolder var0, String var1, String var2)
        throws WTException, RemoteException, WTPropertyVetoException {
        var2 = var2.trim();
        boolean var3 = false;
        int var9 = Integer.parseInt(var2);
        DefaultAttributeContainer var4 = getRefreshableAttributeContainer(var0, false);
        AttributeDefDefaultView var5 = getAttributeDefDefaultView(var1);
        if (!(var5 instanceof IntegerDefView)) {
            throw new WTException(NEWRESOURCE, "EXCEPTION_IBA_NOT_INTEGER", new Object[] {var1});
        } else {
            IntegerDefView var6 = (IntegerDefView)var5;
            AbstractValueView[] var7 = var4.getAttributeValues(var6);
            IntegerValueDefaultView var8;
            if (var7.length == 0) {
                var8 = new IntegerValueDefaultView(var6, (long)var9);
                var4.addAttributeValue(var8);
            } else {
                var8 = (IntegerValueDefaultView)var7[0];
                var8.setValue((long)var9);
                var4.updateAttributeValue(var8);
            }

            var0.setAttributeContainer(var4);
            return var0;
        }
    }

    private static IBAHolder setTimestampValue(IBAHolder var0, String var1, Timestamp var2)
        throws RemoteException, WTException, WTPropertyVetoException {
        DefaultAttributeContainer var3 = getRefreshableAttributeContainer(var0, false);
        AttributeDefDefaultView var4 = getAttributeDefDefaultView(var1);
        if (!(var4 instanceof TimestampDefView)) {
            throw new WTException(NEWRESOURCE, "EXCEPTION_IBA_NOT_TIMESTAMP", new Object[] {var1});
        } else {
            TimestampDefView var5 = (TimestampDefView)var4;
            AbstractValueView[] var6 = var3.getAttributeValues(var5);
            TimestampValueDefaultView var7;
            if (var6.length == 0) {
                var7 = new TimestampValueDefaultView(var5, var2);
                var3.addAttributeValue(var7);
            } else {
                var7 = (TimestampValueDefaultView)var6[0];
                var7.setValue(var2);
                var3.updateAttributeValue(var7);
            }

            var0.setAttributeContainer(var3);
            return var0;
        }
    }

    public static IBAHolder setStringIBAValue(IBAHolder var0, String var1, String var2)
        throws WTException, WTPropertyVetoException, RemoteException {
        AttributeDefDefaultView var3 = getAttributeDefDefaultView(var1);
        if (!(var3 instanceof StringDefView)) {
            throw new WTException(NEWRESOURCE, "EXCEPTION_IBA_NOT_STRING", new Object[] {var1});
        } else {
            StringDefView var4 = (StringDefView)var3;
            DefaultAttributeContainer var5 = getRefreshableAttributeContainer(var0, false);
            AbstractValueView[] var6 = var5.getAttributeValues(var4);
            StringValueDefaultView var7;
            if (var6.length > 0) {
                var7 = (StringValueDefaultView)var6[0];
                var7.setValue(var2);
                var5.updateAttributeValue(var7);
            } else {
                var7 = new StringValueDefaultView(var4, var2);
                var5.addAttributeValue(var7);
            }

            var0.setAttributeContainer(var5);
            return var0;
        }
    }

    private static boolean updateIBAHolder(IBAHolder var0) {
        IBAValueDBService var1 = new IBAValueDBService();

        try {
            PersistenceServerHelper.manager.update((Persistable)var0);
            AttributeContainer var2 = var0.getAttributeContainer();
            if (var2 == null) {
                return false;
            } else {
                Object var3 = ((DefaultAttributeContainer)var2).getConstraintParameter();
                AttributeContainer var4 =
                    var1.updateAttributeContainer(var0, var3, (Locale)null, (MeasurementSystemDefaultView)null);
                var0.setAttributeContainer(var4);
                return true;
            }
        } catch (WTException var5) {
            log.error("updateIBAHOlder: Couldn't update. " + var5);
            return false;
        }
    }

    /**
     * 更新原数据
     * 
     * @param epmDocuments 图纸数据
     * @throws RemoteException
     * @throws WTPropertyVetoException
     * @throws TranslationException
     */
    public static void updateMetadata(Collection<EPMDocument> epmDocuments)
        throws RemoteException, WTPropertyVetoException, TranslationException {
        log.trace("updateMetadata - entered");
        if (epmDocuments != null && !epmDocuments.isEmpty()) {
            try {
                Map var1 = null;
                try {
                    var1 = metadataService.getAttributes(new HashSet(epmDocuments));
                } catch (WTException var14) {
                    if (log.isDebugEnabled()) {
                        log.debug("Exception message from metadataService.getAttributes(): ");
                        log.debug(var14.getLocalizedMessage());
                    }
                    throw new TranslationException(var14);
                }

                if (var1 == null) {
                    return;
                }

                HashMap var2 = new HashMap();
                Iterator var3 = var1.entrySet().iterator();
                while (var3.hasNext()) {
                    Map.Entry var4 = (Map.Entry)var3.next();
                    EPMDocument var5 = (EPMDocument)var4.getKey();
                    Map var6 = (Map)var4.getValue();
                    Iterator var7 = var6.entrySet().iterator();
                    while (var7.hasNext()) {
                        Map.Entry var8 = (Map.Entry)var7.next();
                        if (((String)var8.getKey()).equalsIgnoreCase("WC_NAME_ATTR")) {
                            var2.put(var5, var8.getValue());
                        } else {
                            AttributeDefDefaultView var9 = getAttributeDefDefaultView((String)var8.getKey());
                            if (var9 != null && var9 instanceof StringDefView) {
                                setIba(var5, (String)var8.getKey(), var8.getValue());
                            }
                        }
                    }
                }
                renameEpmDocuments(var2);
            } catch (WTException var15) {
                throw new TranslationException(var15);
            } finally {
                log.trace("updateMetadata - exiting");
            }
        }
    }

    public static void renameEpmDocuments(HashMap<EPMDocument, String> epmDocumentStringHashMap)
        throws WTException, WTPropertyVetoException {
        log.trace("renameEpmDocuments - entered");
        if (epmDocumentStringHashMap != null && !epmDocumentStringHashMap.isEmpty()) {
            WTKeyedHashMap var1 = new WTKeyedHashMap();
            EPMDocumentMaster var4;
            EPMDocumentMasterIdentity var5;
            for (Iterator var2 = epmDocumentStringHashMap.entrySet().iterator(); var2.hasNext(); var1.put(var4, var5)) {
                Map.Entry var3 = (Map.Entry)var2.next();
                var4 = (EPMDocumentMaster)((EPMDocumentMaster)((EPMDocument)var3.getKey()).getMaster());
                var5 = (EPMDocumentMasterIdentity)var4.getIdentificationObject();
                if (var3.getValue() != null && !((String)var3.getValue()).trim().equals("")
                    && !((String)var3.getValue()).equals(var5.getName())) {
                    var5.setName(((String)var3.getValue()).trim());
                }
            }
            IdentityHelper.service.changeIdentities(var1);
            log.trace("renameEpmDocuments - exiting");
        }
    }
}
