//package ext.ziang.common.helper.epm;
//
//import java.rmi.RemoteException;
//import java.util.*;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.ptc.arbortext.windchill.metadata.MetadataService;
//import com.ptc.tml.NewTmlResource;
//import com.ptc.tml.TranslationException;
//
//import ext.ziang.common.util.IbaUtil;
//import wt.epm.EPMDocument;
//import wt.epm.EPMDocumentMaster;
//import wt.epm.EPMDocumentMasterIdentity;
//import wt.fc.IdentityHelper;
//import wt.fc.collections.WTKeyedHashMap;
//import wt.iba.definition.litedefinition.AttributeDefDefaultView;
//import wt.iba.definition.litedefinition.StringDefView;
//import wt.services.ServiceFactory;
//import wt.util.WTException;
//import wt.util.WTPropertyVetoException;
//
//public class EpmDocHelper {
//    private static final Logger log = LoggerFactory.getLogger(IbaUtil.class);
//    private static final String NEWRESOURCE = NewTmlResource.class.getName();
//    public static final MetadataService metadataService =
//        (MetadataService)ServiceFactory.getService(MetadataService.class);
//
//    /**
//     * 更新原数据
//     *
//     * @param epmDocuments 图纸数据
//     * @throws RemoteException
//     * @throws WTPropertyVetoException
//     * @throws TranslationException
//     */
//    public static void updateMetadata(Collection<EPMDocument> epmDocuments)
//        throws RemoteException, WTPropertyVetoException, TranslationException {
//        log.trace("updateMetadata - entered");
//        if (epmDocuments != null && !epmDocuments.isEmpty()) {
//            try {
//                Map var1 = null;
//                try {
//                    var1 = metadataService.getAttributes(new HashSet(epmDocuments));
//                } catch (WTException var14) {
//                    if (log.isDebugEnabled()) {
//                        log.debug("Exception message from metadataService.getAttributes(): ");
//                        log.debug(var14.getLocalizedMessage());
//                    }
//                    throw new TranslationException(var14);
//                }
//
//                if (var1 == null) {
//                    return;
//                }
//
//                HashMap var2 = new HashMap();
//                Iterator var3 = var1.entrySet().iterator();
//                while (var3.hasNext()) {
//                    Map.Entry var4 = (Map.Entry)var3.next();
//                    EPMDocument var5 = (EPMDocument)var4.getKey();
//                    Map var6 = (Map)var4.getValue();
//                    Iterator var7 = var6.entrySet().iterator();
//                    while (var7.hasNext()) {
//                        Map.Entry var8 = (Map.Entry)var7.next();
//                        if (((String)var8.getKey()).equalsIgnoreCase("WC_NAME_ATTR")) {
//                            var2.put(var5, var8.getValue());
//                        } else {
//                            AttributeDefDefaultView var9 =
//                                IbaUtil.getAttributeDefDefaultView((String)var8.getKey());
//                            if (var9 != null && var9 instanceof StringDefView) {
//                                IbaUtil.setIBAValue(var5, (String)var8.getKey(), var8.getValue());
//                            }
//                        }
//                    }
//                }
//                renameEpmDocuments(var2);
//            } catch (WTException var15) {
//                throw new TranslationException(var15);
//            } finally {
//                log.trace("updateMetadata - exiting");
//            }
//        }
//    }
//
//    /**
//     * 修改文档名称
//     *
//     * @param epmDocumentStringHashMap
//     * @throws WTException
//     * @throws WTPropertyVetoException
//     */
//    public static void renameEpmDocuments(HashMap<EPMDocument, String> epmDocumentStringHashMap)
//        throws WTException, WTPropertyVetoException {
//        log.trace("renameEpmDocuments - entered");
//        if (epmDocumentStringHashMap != null && !epmDocumentStringHashMap.isEmpty()) {
//            WTKeyedHashMap var1 = new WTKeyedHashMap();
//            EPMDocumentMaster var4;
//            EPMDocumentMasterIdentity var5;
//            for (Iterator var2 = epmDocumentStringHashMap.entrySet().iterator(); var2.hasNext(); var1.put(var4, var5)) {
//                Map.Entry var3 = (Map.Entry)var2.next();
//                var4 = (EPMDocumentMaster)((EPMDocumentMaster)((EPMDocument)var3.getKey()).getMaster());
//                var5 = (EPMDocumentMasterIdentity)var4.getIdentificationObject();
//                if (var3.getValue() != null && !((String)var3.getValue()).trim().equals("")
//                    && !((String)var3.getValue()).equals(var5.getName())) {
//                    var5.setName(((String)var3.getValue()).trim());
//                }
//            }
//            IdentityHelper.service.changeIdentities(var1);
//            log.trace("renameEpmDocuments - exiting");
//        }
//    }
//
////    /**
////     *
////     * @param var0
////     * @param var1
////     * @param var2
////     * @param var3
////     * @throws WTException
////     * @throws RemoteException
////     * @throws WTPropertyVetoException
////     */
////    public static void copyIbasAndSetLanguage(EPMDocument var0, EPMDocument var1, String var2, TMLContext var3)
////        throws WTException, RemoteException, WTPropertyVetoException {
////        String var4 = var3.getDocumentLanguageAttributeId();
////        boolean var5 = var1.getPersistInfo().isPersisted();
////        Locale var6 = WTContext.getContext().getLocale();
////        TypeIdentifier var7 = OidHelper.getTypeIdentifier(var0);
////        Set var8 = TypeHelper.getSoftAttributes(var7);
////        PersistableAdapter var9 = new PersistableAdapter(var0, (String)null, var6, new UpdateOperationIdentifier());
////        PersistableAdapter var10 = new PersistableAdapter(var1, (String)null, var6, new UpdateOperationIdentifier());
////        ArrayList var11 = new ArrayList();
////        Object var12 = null;
////        if (var9 != null && var8 != null && var10 != null) {
////            Iterator var13 = var8.iterator();
////
////            while (var13.hasNext()) {
////                AttributeTypeIdentifier var14 = (AttributeTypeIdentifier)var13.next();
////                String var15 = var14.toLogicalForm();
////                var11.add(var15);
////            }
////
////            if (!var11.isEmpty()) {
////                var9.load(var11);
////                var10.load(var11);
////                var13 = var11.iterator();
////
////                while (var13.hasNext()) {
////                    String var16 = (String)var13.next();
////                    if (var16.equals(var4)) {
////                        var1 = (EPMDocument) IbaUtil.setIBAValue(var1, var16, var2, false);
////                    } else {
////                        var12 = var9.get(var16);
////                        if (var12 != null && !"PTC_DD_EXTENDED_DOC_TYPE".equalsIgnoreCase(var16)) {
////                            var1 = (EPMDocument) IbaUtil.setIBAValue(var1, var16, var9.get(var16), false);
////                        }
////                    }
////                }
////            }
////        }
////
////        if (var5) {
////            IbaUtil.updateIBAHolder(var1);
////        }
////
////    }
////
////    public static EPMDocument setLanguageAttribute(EPMDocument var0, String var1, TMLContext var2)
////        throws WTPropertyVetoException, RemoteException, WTException {
////        log.trace("setLanguageAttribute - entered");
////        String var3 = var2.getDocumentLanguageAttributeId();
////        var0 = (EPMDocument) IbaUtil.setStringIBAValue(var0, var3, var1);
////        if (PersistenceHelper.isPersistent(var0) && !IbaUtil.updateIBAHolder(var0)) {
////            throw new WTException(NEWRESOURCE, "EXCEPTION_UNABLE_TO_UPDATE_IBAHOLDER", (Object[])null);
////        } else {
////            log.trace("setLanguageAttribute - exiting");
////            return var0;
////        }
////    }
//}
