package ext.ziang.common.util;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import com.ptc.core.lwc.server.LWCNormalizedObject;
import com.ptc.core.meta.common.UpdateOperationIdentifier;
import com.sun.istack.NotNull;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.WTObject;
import wt.iba.definition.DefinitionLoader;
import wt.iba.definition.litedefinition.AbstractAttributeDefinizerView;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.definition.litedefinition.AttributeDefNodeView;
import wt.iba.definition.litedefinition.BooleanDefView;
import wt.iba.definition.litedefinition.FloatDefView;
import wt.iba.definition.litedefinition.IntegerDefView;
import wt.iba.definition.litedefinition.RatioDefView;
import wt.iba.definition.litedefinition.ReferenceDefView;
import wt.iba.definition.litedefinition.StringDefView;
import wt.iba.definition.litedefinition.TimestampDefView;
import wt.iba.definition.litedefinition.URLDefView;
import wt.iba.definition.litedefinition.UnitDefView;
import wt.iba.definition.service.IBADefinitionHelper;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.IBAHolder;
import wt.iba.value.IBAValueUtility;
import wt.iba.value.litevalue.AbstractValueView;
import wt.iba.value.litevalue.BooleanValueDefaultView;
import wt.iba.value.litevalue.FloatValueDefaultView;
import wt.iba.value.litevalue.IntegerValueDefaultView;
import wt.iba.value.litevalue.RatioValueDefaultView;
import wt.iba.value.litevalue.StringValueDefaultView;
import wt.iba.value.litevalue.TimestampValueDefaultView;
import wt.iba.value.litevalue.URLValueDefaultView;
import wt.iba.value.litevalue.UnitValueDefaultView;
import wt.iba.value.service.IBAValueHelper;
import wt.iba.value.service.LoadValue;
import wt.iba.value.service.StandardIBAValueService;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionHelper;
import wt.units.service.MeasurementSystemDefaultView;
import wt.util.WTContext;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * IBA 操作工具
 *
 * @author anzhen
 * @date 2024/03/01
 */
public class IBAUtils implements RemoteAccess {
    Hashtable ibaContainer;

    private IBAUtils() {
        ibaContainer = new Hashtable();
    }

    public IBAUtils(IBAHolder ibaholder) {
        initializeIBAHolder(ibaholder);
    }

    /**
     * 获取 iba value 视图
     *
     * @param defaultAttributeContainer 默认属性容器
     * @param ibaName                   IBA 名称
     * @param ibaClass                  IBA类
     * @return {@link AbstractValueView}
     */
    public static AbstractValueView getIBAValueView(DefaultAttributeContainer defaultAttributeContainer, String ibaName, String ibaClass) {
        AbstractValueView[] abstractValueViews;
        AbstractValueView valueView = null;
        // 通过属性容器获取值视图
        abstractValueViews = defaultAttributeContainer.getAttributeValues();
        for (AbstractValueView abstractValueView : abstractValueViews) {
            // 获取IBA 内部名称
            String thisIBAName = abstractValueView.getDefinition().getName();
            String thisIBAClass = (abstractValueView.getDefinition()).getAttributeDefinitionClassName();
            if (thisIBAName.equals(ibaName) && thisIBAClass.equals(ibaClass)) {
                valueView = abstractValueView;
                break;
            }
        }
        return valueView;
    }

    /**
     * 获取容器
     *
     * @param ibaHolder IBA支架
     * @return {@link DefaultAttributeContainer}
     * @throws WTException     WT异常
     * @throws RemoteException 远程异常
     */
    public static DefaultAttributeContainer getContainer(IBAHolder ibaHolder) throws WTException, RemoteException {
        ibaHolder = IBAValueHelper.service.refreshAttributeContainerWithoutConstraints(ibaHolder);
        return (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
    }


    private static DefaultAttributeContainer getRefreshableAttributeContainer(IBAHolder ibaHolder, boolean flag) throws WTException, RemoteException {
        DefaultAttributeContainer attributeContainer = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
        if (attributeContainer == null || PersistenceHelper.isPersistent(ibaHolder) && flag) {
            ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, (Object) null, (Locale) null, (MeasurementSystemDefaultView) null);
            attributeContainer = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
        }
        return attributeContainer;
    }

    private static final java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * 获取所有 ibastring
     *
     * @param delim  德林
     * @param filter 滤波器
     * @return {@link String}
     */
    public String getAllIBAString(String delim, Vector filter) {
        StringBuffer stringbuffer = new StringBuffer();
        Enumeration enumeration = ibaContainer.keys();
        try {
            while (enumeration.hasMoreElements()) {
                String s = (String) enumeration.nextElement();
                if (filter != null && filter.contains(s)) {
                    AbstractValueView abstractvalueview = (AbstractValueView) ((Object[]) ibaContainer.get(s))[1];
                    if (abstractvalueview instanceof TimestampValueDefaultView) {
                        try {
                            TimestampValueDefaultView w = (TimestampValueDefaultView) abstractvalueview;
                            ResourceBundle formatResource = ResourceBundle.getBundle(
                                    "wt.iba.value.litevalue.DisplayFormat", WTContext.getContext().getLocale());
                            String formatString = formatResource.getString("timestampOutputFormat");
                            java.text.SimpleDateFormat formats = new java.text.SimpleDateFormat(formatString);
                            stringbuffer.append(
                                    s + delim + format.format(formats.parse(w.getLocalizedDisplayString())) + delim);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        stringbuffer
                                .append(s + delim + IBAValueUtility.getLocalizedIBAValueDisplayString(abstractvalueview,
                                        SessionHelper.manager.getLocale()) + delim);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return stringbuffer.toString();
    }

    @Override
    public String toString() {
        StringBuffer stringbuffer = new StringBuffer();
        Enumeration enumeration = ibaContainer.keys();
        try {
            while (enumeration.hasMoreElements()) {
                String s = (String) enumeration.nextElement();
                AbstractValueView abstractvalueview = (AbstractValueView) ((Object[]) ibaContainer.get(s))[1];
                stringbuffer.append(s + " - " + IBAValueUtility.getLocalizedIBAValueDisplayString(abstractvalueview,
                        SessionHelper.manager.getLocale()));
                stringbuffer.append('\n');
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return stringbuffer.toString();
    }

    /**
     * 获取 ibavalue
     *
     * @param s s
     * @return {@link String}
     */
    public String getIBAValue(String s) {
        try {
            return getIBAValue(s, SessionHelper.manager.getLocale());
        } catch (WTException wte) {
            wte.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 ibavalue
     *
     * @param key    s
     * @param locale 现场
     * @return {@link String}
     */
    public String getIBAValue(String key, Locale locale) {
        Object[] obj = (Object[]) ibaContainer.get(key);
        if (obj == null) {
            return null;
        }
        AbstractValueView avv = (AbstractValueView) obj[1];
        if (avv == null) {
            return null;
        }
        try {
            return IBAValueUtility.getLocalizedIBAValueDisplayString(avv, locale);
        } catch (WTException wte) {
            wte.printStackTrace();
        }
        return null;
    }

    public String getIBAValueWithDefault(String s) {
        try {
            return getIBAValueWithDefault(s, SessionHelper.manager.getLocale());
        } catch (WTException wte) {
            wte.printStackTrace();
        }
        return null;
    }

    public String getIBAValueWithDefault(String s, Locale loc) {
        String str = getIBAValue(s, loc);
        if (str != null && str.equalsIgnoreCase("default")) {
            str = "";
        }
        return str;
    }

    /**
     * 初始化 IBA holder
     *
     * @param ibaholder iba属性持有者
     */
    private void initializeIBAHolder(IBAHolder ibaholder) {
        ibaContainer = new Hashtable();
        try {
            ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, null,
                    SessionHelper.manager.getLocale(), null);
            DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer) ibaholder
                    .getAttributeContainer();
            if (defaultattributecontainer != null) {
                AttributeDefDefaultView[] aattributedefdefaultview = defaultattributecontainer
                        .getAttributeDefinitions();
                for (int i = 0; i < aattributedefdefaultview.length; i++) {
                    AbstractValueView[] aabstractvalueview = defaultattributecontainer
                            .getAttributeValues(aattributedefdefaultview[i]);
                    if (aabstractvalueview != null) {
                        Object[] aobj = new Object[2];
                        aobj[0] = aattributedefdefaultview[i];
                        aobj[1] = aabstractvalueview[0];
                        ibaContainer.put(aattributedefdefaultview[i].getName(), ((aobj)));
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 更新 IBAholder
     *
     * @param ibaholder 伊巴霍尔德
     * @return {@link IBAHolder}
     * @throws Exception 例外
     */
    public IBAHolder updateIBAHolder(IBAHolder ibaholder) throws Exception {
        ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, null, SessionHelper.manager.getLocale(),
                null);
        DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer) ibaholder
                .getAttributeContainer();
        for (Enumeration enumeration = ibaContainer.elements(); enumeration.hasMoreElements(); ) {
            try {
                Object[] aobj = (Object[]) enumeration.nextElement();
                AbstractValueView abstractvalueview = (AbstractValueView) aobj[1];
                AttributeDefDefaultView attributedefdefaultview = (AttributeDefDefaultView) aobj[0];
                if (abstractvalueview.getState() == 1) {
                    defaultattributecontainer.deleteAttributeValues(attributedefdefaultview);
                    abstractvalueview.setState(3);
                    defaultattributecontainer.addAttributeValue(abstractvalueview);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        ibaholder.setAttributeContainer(defaultattributecontainer);
        return ibaholder;
    }

    /**
     * 设置 IBASTRING 值
     * 设置 IBA STRING 值
     *
     * @param value      value
     * @param ibaAttrKey IBA ATTR 密钥
     * @throws WTPropertyVetoException WTPROPERTY 否决例外
     */
    public void setIBAStringValue(String ibaAttrKey, String value) throws WTPropertyVetoException {
        AbstractValueView abstractvalueview = null;
        AttributeDefDefaultView attributedefdefaultview = null;
        Object[] aobj = (Object[]) ibaContainer.get(ibaAttrKey);
        if (aobj != null) {
            abstractvalueview = (AbstractValueView) aobj[1];
            attributedefdefaultview = (AttributeDefDefaultView) aobj[0];
        }
        if (abstractvalueview == null) {
            attributedefdefaultview = getAttributeDefinition(ibaAttrKey);
        }
        if (attributedefdefaultview == null) {
            System.out.println("definition is null ...");
            return;
        }
        abstractvalueview = internalCreateValue(attributedefdefaultview, value);
        if (abstractvalueview == null) {
            System.out.println("after creation, iba value is null ..");
        } else {
            abstractvalueview.setState(1);
            Object[] aobj1 = new Object[2];
            aobj1[0] = attributedefdefaultview;
            aobj1[1] = abstractvalueview;
            ibaContainer.put(attributedefdefaultview.getName(), ((aobj1)));
        }
    }

    /**
     * 获取属性定义
     *
     * @param s s
     * @return {@link AttributeDefDefaultView}
     */
    public static AttributeDefDefaultView getAttributeDefinition(String s) {
        AttributeDefDefaultView attributedefdefaultview = null;
        try {
            attributedefdefaultview = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(s);
            if (attributedefdefaultview == null) {
                AbstractAttributeDefinizerView abstractattributedefinizerview = DefinitionLoader
                        .getAttributeDefinition(s);
                if (abstractattributedefinizerview != null) {
                    attributedefdefaultview = IBADefinitionHelper.service
                            .getAttributeDefDefaultView((AttributeDefNodeView) abstractattributedefinizerview);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return attributedefdefaultview;
    }

    /**
     * 内部创造价值
     *
     * @param abstractattributedefinizerview AbstractAttributeDefinizerView
     * @param s                              s
     * @return {@link AbstractValueView}
     */
    private AbstractValueView internalCreateValue(AbstractAttributeDefinizerView abstractattributedefinizerview,
                                                  String s) {
        AbstractValueView abstractvalueview = null;
        if (abstractattributedefinizerview instanceof FloatDefView) {
            abstractvalueview = LoadValue.newFloatValue(abstractattributedefinizerview, s, null);
        } else if (abstractattributedefinizerview instanceof StringDefView) {
            abstractvalueview = LoadValue.newStringValue(abstractattributedefinizerview, s);
        } else if (abstractattributedefinizerview instanceof IntegerDefView) {
            abstractvalueview = LoadValue.newIntegerValue(abstractattributedefinizerview, s);
        } else if (abstractattributedefinizerview instanceof RatioDefView) {
            abstractvalueview = LoadValue.newRatioValue(abstractattributedefinizerview, s, null);
        } else if (abstractattributedefinizerview instanceof TimestampDefView) {
            abstractvalueview = LoadValue.newTimestampValue(abstractattributedefinizerview, s);
        } else if (abstractattributedefinizerview instanceof BooleanDefView) {
            abstractvalueview = LoadValue.newBooleanValue(abstractattributedefinizerview, s);
        } else if (abstractattributedefinizerview instanceof URLDefView) {
            abstractvalueview = LoadValue.newURLValue(abstractattributedefinizerview, s, null);
        } else if (abstractattributedefinizerview instanceof ReferenceDefView) {
            abstractvalueview = LoadValue.newReferenceValue(abstractattributedefinizerview, "ClassificationNode", s);
        } else if (abstractattributedefinizerview instanceof UnitDefView) {
            abstractvalueview = LoadValue.newUnitValue(abstractattributedefinizerview, s, null);
        }
        return abstractvalueview;
    }

    public static AttributeDefDefaultView getAttributeDefinition(String s, boolean flag) {
        AttributeDefDefaultView attributedefdefaultview = null;
        try {
            attributedefdefaultview = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(s);
            if (attributedefdefaultview == null) {
                AbstractAttributeDefinizerView abstractattributedefinizerview = DefinitionLoader
                        .getAttributeDefinition(s);
                if (abstractattributedefinizerview != null) {
                    attributedefdefaultview = IBADefinitionHelper.service
                            .getAttributeDefDefaultView((AttributeDefNodeView) abstractattributedefinizerview);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return attributedefdefaultview;
    }

    public static Vector getIBAValueViews(DefaultAttributeContainer dac, String ibaName, String ibaClass)
            throws WTException {
        AbstractValueView[] aabstractvalueview = null;
        AbstractValueView avv = null;
        Vector vResult = new Vector();
        aabstractvalueview = dac.getAttributeValues();
        for (int j = 0; j < aabstractvalueview.length; j++) {
            String thisIBAName = aabstractvalueview[j].getDefinition().getName();
            String thisIBAClass = (aabstractvalueview[j].getDefinition()).getAttributeDefinitionClassName();
            if (thisIBAName.equals(ibaName) && thisIBAClass.equals(ibaClass)) {
                avv = aabstractvalueview[j];
                vResult.add(avv);
            }
        }
        return vResult;
    }

    public static void setIBAAnyValue(WTObject obj, String ibaName, String newValue)
            throws WTException, RemoteException, ParseException {
        AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
        IBAHolder ibaholder = (IBAHolder) obj;
        String ibaClass = "";
        if (attributedefdefaultview instanceof FloatDefView) {
            ibaClass = "wt.iba.definition.FloatDefinition";
        } else if (attributedefdefaultview instanceof StringDefView) {
            ibaClass = "wt.iba.definition.StringDefinition";
        } else if (attributedefdefaultview instanceof IntegerDefView) {
            ibaClass = "wt.iba.definition.IntegerDefinition";
        } else if (attributedefdefaultview instanceof RatioDefView) {
            ibaClass = "wt.iba.definition.RatioDefinition";
        } else if (attributedefdefaultview instanceof TimestampDefView) {
            ibaClass = "wt.iba.definition.TimestampDefinition";
        } else if (attributedefdefaultview instanceof BooleanDefView) {
            ibaClass = "wt.iba.definition.BooleanDefinition";
        } else if (attributedefdefaultview instanceof URLDefView) {
            ibaClass = "wt.iba.definition.URLDefinition";
        } else if (attributedefdefaultview instanceof ReferenceDefView) {
            ibaClass = "wt.iba.definition.ReferenceDefinition";
        } else if (attributedefdefaultview instanceof UnitDefView) {
            ibaClass = "wt.iba.definition.UnitDefinition";
        }

        // store the new iteration (this will copy forward the obsolete set of
        // IBA values in the database)
        // ibaholder = (IBAHolder)PersistenceHelper.manager.store(
        // (Persistable)ibaholder );

        // load IBA values from DB (because obsolete IBA values have
        // been copied forward to new iteration by IBA persistence event
        // handlers)
        ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, "CSM", null, null);

        // clear the container to remove all obsolete IBA values and persist
        // this
        // to remove IBA values from database
        // *deleteAllIBAValues(ibaholder );
        ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, null, SessionHelper.manager.getLocale(),
                null);
        DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer) (ibaholder)
                .getAttributeContainer();

        Vector vAbstractvalueview = getIBAValueViews(defaultattributecontainer, ibaName, ibaClass);

        for (int i = 0; i < vAbstractvalueview.size(); i++) {
            AbstractValueView abstractvalueview = (AbstractValueView) vAbstractvalueview.get(i);
            defaultattributecontainer.deleteAttributeValue(abstractvalueview);
            StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaholder, null, null, null);
            ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, "CSM", null, null);
        }

        if (!newValue.equals("")) {
            if (attributedefdefaultview instanceof FloatDefView) {
                setIBAFloatValue(obj, ibaName, Float.parseFloat(newValue));
                System.out.println("setIBAFloatValue");
            } else if (attributedefdefaultview instanceof StringDefView) {
                if (newValue.contains("strMultiValuePTC")) {
                    String[] newMultiString = newValue.split("strMultiValuePTC");
                    setIBAStringValues(obj, ibaName, newMultiString);
                    System.out.println("setIBAStringMultiValue");
                } else {
                    setIBAStringValue(obj, ibaName, newValue);
                    System.out.println("setIBAStringValue");
                }
            } else if (attributedefdefaultview instanceof IntegerDefView) {
                setIBAIntegerValue(obj, ibaName, Integer.parseInt(newValue));
                System.out.println("setIBAIntegerValue");
            } else if (attributedefdefaultview instanceof RatioDefView) {
                setIBARatioValue(obj, ibaName, Double.parseDouble(newValue));
                System.out.println("setIBARatioValue");
            } else if (attributedefdefaultview instanceof TimestampDefView) {
                if (!newValue.contains(":")) {
                    newValue = newValue + " 00:00:00";
                }

                String format = "yyyy-MM-dd HH:mm:ss";
                if (SessionHelper.manager.getLocale().toString().equals("zh_CN")
                        || SessionHelper.manager.getLocale().toString().equals("zh_TW")) {
                    format = "yyyy/MM/dd HH:mm:ss";
                }
                java.text.SimpleDateFormat formats = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.text.SimpleDateFormat formatSource = new java.text.SimpleDateFormat(format);
                setIBATimestampValue(obj, ibaName, Timestamp.valueOf(formats.format(formatSource.parse(newValue))));
                System.out.println("setIBATimestampValue");
            } else if (attributedefdefaultview instanceof BooleanDefView) {
                setIBABooleanValue(obj, ibaName, Boolean.parseBoolean(newValue));
                System.out.println("setIBABooleanValue");
            } else if (attributedefdefaultview instanceof URLDefView) {
                setIBAURLValue(obj, ibaName, newValue);
                System.out.println("setIBAURLValue");
            } else if (attributedefdefaultview instanceof ReferenceDefView) {
                System.out.println("ReferenceDefView");
            } else if (attributedefdefaultview instanceof UnitDefView) {
                setIBAUnitValue(obj, ibaName, Double.parseDouble(newValue));
                System.out.println("setIBAUnitValue");
            }
        }
    }

    /**
     * set iba attribute value
     *
     * @param obj      object
     * @param ibaName  attribute name
     * @param newValue attribute value
     * @return void
     */
    public static void setIBAStringValue(@NotNull WTObject obj, @NotNull String ibaName, @NotNull String newValue) {
        String ibaClass = "wt.iba.definition.StringDefinition";
        System.out.println("ENTER..." + ibaName + "..." + newValue);
        if (ObjectUtil.isEmpty(obj) || StrUtil.equalsAny(ibaName, newValue)) {
            return;
        }
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                StringValueDefaultView abstractvaluedefaultview = (StringValueDefaultView) getIBAValueView(
                        defaultattributecontainer, ibaName, ibaClass);
                if (abstractvaluedefaultview != null) {
                    abstractvaluedefaultview.setValue(newValue);
                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    StringValueDefaultView abstractvaluedefaultview1 = new StringValueDefaultView(
                            (StringDefView) attributedefdefaultview, newValue);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
                // wt.iba.value.service.LoadValue.applySoftAttributes(ibaHolder);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 设置 IBA STRING 值
     *
     * @param obj      OBJ
     * @param ibaName  IBA 名称
     * @param newValue 新价值
     */
    public static void setIBAStringValues(WTObject obj, String ibaName, String[] newValue) {
        String oneNewValue = "";
        try {
            if (obj instanceof IBAHolder) {
                for (String value : newValue) {
                    oneNewValue = value;
                    IBAHolder ibaHolder = (IBAHolder) obj;
                    DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                    if (defaultattributecontainer == null) {
                        defaultattributecontainer = new DefaultAttributeContainer();
                        ibaHolder.setAttributeContainer(defaultattributecontainer);
                    }
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    StringValueDefaultView newStringValueView = new StringValueDefaultView(
                            (StringDefView) attributedefdefaultview, oneNewValue);
                    defaultattributecontainer.addAttributeValue(newStringValueView);
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                    StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                    ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
                    // wt.iba.value.service.LoadValue.applySoftAttributes(ibaHolder);
                }
            }
            System.out.println("ENTER..." + ibaName + "..." + newValue.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void setIBABooleanValue(WTObject obj, String ibaName, boolean newValue) throws WTException {
        String ibaClass = "wt.iba.definition.BooleanDefinition";
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                BooleanValueDefaultView abstractvaluedefaultview = (BooleanValueDefaultView) getIBAValueView(
                        defaultattributecontainer, ibaName, ibaClass);
                if (abstractvaluedefaultview != null) {
                    abstractvaluedefaultview.setValue(newValue);
                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    BooleanValueDefaultView abstractvaluedefaultview1 = new BooleanValueDefaultView(
                            (BooleanDefView) attributedefdefaultview, newValue);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public static void setIBAIntegerValue(WTObject obj, String ibaName, int newValue) throws WTException {
        String ibaClass = "wt.iba.definition.IntegerDefinition";
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                IntegerValueDefaultView abstractvaluedefaultview = (IntegerValueDefaultView) getIBAValueView(
                        defaultattributecontainer, ibaName, ibaClass);
                if (abstractvaluedefaultview != null) {
                    abstractvaluedefaultview.setValue(newValue);
                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    IntegerValueDefaultView abstractvaluedefaultview1 = new IntegerValueDefaultView(
                            (IntegerDefView) attributedefdefaultview, newValue);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void setIBAFloatValue(WTObject obj, String ibaName, float newValue) throws WTException {
        String ibaClass = "wt.iba.definition.FloatDefinition";
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }

                String strFloatValue = String.valueOf(newValue);
                StringTokenizer st = new StringTokenizer(strFloatValue, ".");
                System.out.println();
                int iFloatLength = 0;
                if (st.hasMoreElements()) {
                    st.nextElement();
                    if (st.hasMoreElements()) {
                        iFloatLength = ((String) st.nextElement()).length();
                    }
                }
                if (iFloatLength == 0) {
                    iFloatLength = -1;
                } else {
                    iFloatLength = iFloatLength + 1;
                }

                FloatValueDefaultView abstractvaluedefaultview = (FloatValueDefaultView) getIBAValueView(
                        defaultattributecontainer, ibaName, ibaClass);
                if (abstractvaluedefaultview != null) {
                    abstractvaluedefaultview.setValue(newValue);
                    abstractvaluedefaultview.setPrecision(iFloatLength);
                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    FloatValueDefaultView abstractvaluedefaultview1 = new FloatValueDefaultView(
                            (FloatDefView) attributedefdefaultview, newValue, iFloatLength);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void setIBARatioValue(WTObject obj, String ibaName, double newValue) throws WTException {
        String ibaClass = "wt.iba.definition.RatioDefinition";
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                RatioValueDefaultView abstractvaluedefaultview = (RatioValueDefaultView) getIBAValueView(
                        defaultattributecontainer, ibaName, ibaClass);
                if (abstractvaluedefaultview != null) {
                    abstractvaluedefaultview.setValue(newValue);

                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    RatioValueDefaultView abstractvaluedefaultview1 = new RatioValueDefaultView(
                            (RatioDefView) attributedefdefaultview);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void setIBATimestampValue(WTObject obj, String ibaName, Timestamp newValue) throws WTException {
        String ibaClass = "wt.iba.definition.TimestampDefinition";
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                TimestampValueDefaultView abstractvaluedefaultview = (TimestampValueDefaultView) getIBAValueView(
                        defaultattributecontainer, ibaName, ibaClass);
                if (abstractvaluedefaultview != null) {
                    abstractvaluedefaultview.setValue(newValue);

                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    TimestampValueDefaultView abstractvaluedefaultview1 = new TimestampValueDefaultView(
                            (TimestampDefView) attributedefdefaultview, newValue);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 设置 IBAURL值
     *
     * @param obj      OBJ
     * @param ibaName  IBA 名称
     * @param newValue 新价值
     * @throws WTException WT异常
     */
    public static void setIBAURLValue(WTObject obj, String ibaName, String newValue) throws WTException {
        String ibaClass = "wt.iba.definition.URLDefinition";

        try {
            StringTokenizer st = new StringTokenizer(newValue, "$$$");
            String urlValue = "";
            String urlDesc = "";
            while (st.hasMoreElements()) {
                urlValue = st.nextToken();
                if (st.hasMoreElements()) {
                    urlDesc = st.nextToken();
                }
            }
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                URLValueDefaultView abstractvaluedefaultview = (URLValueDefaultView) getIBAValueView(
                        defaultattributecontainer, ibaName, ibaClass);
                if (abstractvaluedefaultview != null) {
                    abstractvaluedefaultview.setValue(urlValue);
                    abstractvaluedefaultview.setDescription(urlDesc);
                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    URLValueDefaultView abstractvaluedefaultview1 = new URLValueDefaultView(
                            (URLDefView) attributedefdefaultview, urlValue, urlDesc);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 设置 IBAUnit 值
     *
     * @param obj      OBJ
     * @param ibaName  IBA 名称
     * @param newValue 新价值
     * @throws WTException WT异常
     */
    public static void setIBAUnitValue(WTObject obj, String ibaName, double newValue) throws WTException {
        String ibaClass = "wt.iba.definition.UnitDefinition";
        try {
            if (obj instanceof IBAHolder) {
                IBAHolder ibaHolder = (IBAHolder) obj;
                DefaultAttributeContainer defaultattributecontainer = getContainer(ibaHolder);
                if (defaultattributecontainer == null) {
                    defaultattributecontainer = new DefaultAttributeContainer();
                    ibaHolder.setAttributeContainer(defaultattributecontainer);
                }
                UnitValueDefaultView abstractvaluedefaultview = (UnitValueDefaultView) getIBAValueView(
                        defaultattributecontainer, ibaName, ibaClass);

                String strFloatValue = String.valueOf(newValue);
                StringTokenizer st = new StringTokenizer(strFloatValue, ".");
                System.out.println();
                int iFloatLength = 0;
                if (st.hasMoreElements()) {
                    st.nextElement();
                    if (st.hasMoreElements()) {
                        iFloatLength = ((String) st.nextElement()).length();
                    }
                }
                iFloatLength = iFloatLength + 1;
                if (abstractvaluedefaultview != null) {
                    abstractvaluedefaultview.setValue(newValue);
                    abstractvaluedefaultview.setPrecision(iFloatLength);
                    defaultattributecontainer.updateAttributeValue(abstractvaluedefaultview);
                } else {
                    AttributeDefDefaultView attributedefdefaultview = getAttributeDefinition(ibaName, false);
                    UnitValueDefaultView abstractvaluedefaultview1 = new UnitValueDefaultView(
                            (UnitDefView) attributedefdefaultview, newValue, iFloatLength);
                    defaultattributecontainer.addAttributeValue(abstractvaluedefaultview1);
                }
                ibaHolder.setAttributeContainer(defaultattributecontainer);
                StandardIBAValueService.theIBAValueDBService.updateAttributeContainer(ibaHolder, null, null, null);
                ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, "CSM", null, null);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Filer iba value
     *
     * @param obj      OBJ
     * @param ibaName  IBA 名称
     * @param ibaValue IBA 值
     * @return boolean
     */
    public static boolean filerIBAValue(WTObject obj, String ibaName, String ibaValue) {
        if (obj instanceof IBAHolder) {
            IBAUtils ibaUtil = new IBAUtils((IBAHolder) obj);
            String value = ibaUtil.getIBAValue(ibaName);
            if (ibaValue == null && value == null) {
                return true;
            } else {
                return ibaValue != null && ibaValue.equals(value);
            }
        }
        return false;
    }

    public static boolean isIBAContained(String iba, Map IBAMap) throws WTException {
        boolean contained = false;
        try {
            Iterator itr = IBAMap.entrySet().iterator();
            while (itr.hasNext()) {
                Entry entry = (Entry) itr.next();
                Object key = entry.getKey();
                if (iba.equals(key)) {
                    contained = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contained;
    }

    public static String getIBAValue(Object obj, String ibaName) throws Exception {
        if (!RemoteMethodServer.ServerFlag) {
            return (String) RemoteMethodServer.getDefault().invoke("getIBAValue", IBAUtils.class.getName(), null,
                    new Class[]{Object.class, String.class}, new Object[]{obj, ibaName});
        }
        String ibaValue = "";
        LWCNormalizedObject obj0 = new LWCNormalizedObject((Persistable) obj, null, Locale.US,
                new UpdateOperationIdentifier());
        obj0.load(ibaName);
        Object value = obj0.get(ibaName);

        if (value != null) {
            if (value instanceof Object[]) {
                Object[] list = (Object[]) value;
                for (int i = 0; i < list.length; i++) {
                    String val = list[i].toString();
                    ibaValue += val + ",";
                }
                ibaValue = ibaValue.substring(0, ibaValue.length() - 1);
            } else {
                ibaValue = (String) value;
            }
        }
        return ibaValue;
    }

    /**
     * 设置 IBAvalue
     *
     * @param ibaMap IBA地图
     * @param object 对象
     */
    public static void setIBAStringValue(Map<String, String> ibaMap, WTObject object) {
        for (Entry<String, String> entry : ibaMap.entrySet()) {
            try {
                setIBAStringValue(object, entry.getKey(), entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
