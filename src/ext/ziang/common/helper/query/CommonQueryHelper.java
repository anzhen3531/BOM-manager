package ext.ziang.common.helper.query;

import com.ptc.core.managedcollection.ManagedCollectionIdentity;
import com.ptc.core.meta.common.IdentifierFactory;
import com.ptc.core.meta.common.TypeInstanceIdentifier;
import com.ptc.core.meta.server.TypeIdentifierUtility;
import com.ptc.windchill.classproxy.WorkPackageClassProxy;
import com.ptc.windchill.mpml.MPMLinkHelper;
import com.ptc.windchill.option.model.*;
import com.ptc.windchill.option.service.OptionHelper;
import com.ptc.wpcfg.doc.DocHelper;
import com.ptc.wpcfg.doc.VariantSpecMaster;
import ext.ziang.common.helper.CommonQuerySpec;
import ext.ziang.common.util.LoggerHelper;
import org.apache.log4j.Logger;
import wt.access.agreement.AgreementHelper;
import wt.access.agreement.AuthorizationAgreementMaster;
import wt.change2.ChangeHelper2;
import wt.change2.ChangeItemIfc;
import wt.doc.WTDocument;
import wt.doc.WTDocumentHelper;
import wt.doc.WTDocumentMaster;
import wt.facade.mpmlink.MPMLinkFacade;
import wt.facade.persistedcollection.ManagedCollection;
import wt.fc.*;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTOrganization;
import wt.part.WTPart;
import wt.part.WTPartHelper;
import wt.part.WTPartMaster;
import wt.part.alternaterep.WTPartAlternateRepMaster;
import wt.part.alternaterep.service.WTPartAlternateRepService;
import wt.query.ClassAttribute;
import wt.query.ClassTableExpression;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.services.applicationcontext.implementation.DefaultServiceProvider;
import wt.util.InstalledProperties;
import wt.util.WTException;
import wt.vc.Iterated;
import wt.vc.Mastered;
import wt.vc.baseline.ManagedBaseline;
import wt.vc.baseline.ManagedBaselineIdentity;
import wt.vc.config.LatestConfigSpec;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用查询帮助程序
 *
 * @author anzhen
 * @date 2024/05/09
 */
public class CommonQueryHelper implements RemoteAccess {
    private static final Logger log = Logger.getLogger(CommonQueryHelper.class.getName());
    private static final IdentifierFactory DEFAULT_IDENTIFIER_FACTORY = (IdentifierFactory) DefaultServiceProvider
            .getService(IdentifierFactory.class, "default");

    /**
     * 按对象更新名称和编号
     *
     * @param name        名称
     * @param number      编码
     * @param orgId       组织 ID
     * @param persistable 实例对象
     */
    public static void updateNameAndNumberByObject(Persistable persistable, String name, String number, String orgId) {
        log.info("persistable = " + persistable + ", name = " + name + ", number = " + number + ", orgId = " + orgId);
        Identified master = null;
        if (persistable instanceof Iterated) {
            Iterated iterated = (Iterated) persistable;
            master = (Identified) iterated.getMaster();
        } else if (persistable instanceof Identified) {
            master = (Identified) persistable;
        }
        try {
            if (master != null) {
                WTOrganization organization = getWTOrganization(orgId);
                if (master instanceof WTPartMaster) {
                    WTPartHelper.service.changeWTPartMasterIdentity((WTPartMaster) master, name, number, organization);
                } else if (master instanceof WTPartAlternateRepMaster) {
                    WTPartAlternateRepService alternateRepService = ServiceFactory
                            .getService(WTPartAlternateRepService.class);
                    alternateRepService.updateWTPartAlternateRepMasterIdentity((WTPartAlternateRepMaster) master, name,
                            number,
                            organization);
                } else if (master instanceof AuthorizationAgreementMaster) {
                    AgreementHelper.service.changeAuthorizationAgreementMasterIdentity(
                            (AuthorizationAgreementMaster) master, name, number);
                } else if (persistable instanceof ChangeItemIfc && master instanceof Mastered) {
                    ChangeHelper2.service.changeChangeItemMasterIdentity((Mastered) master, name, number, organization);
                } else if (WorkPackageClassProxy.isWorkPackageMaster(persistable)
                        || WorkPackageClassProxy.isWorkPackage(persistable) && master instanceof Mastered) {
                    WorkPackageClassProxy.changeWorkPackageMasterIdentity((Mastered) master, name, number,
                            organization);
                } else if (master instanceof WTDocumentMaster && persistable instanceof WTDocument) {
                    // 更改文档名称
                    boolean flag = true;
                    WTDocument document = (WTDocument) persistable;
                    LoggerHelper.log("WTDocument object:" + document);
                    String var17 = number != null && !number.isEmpty() ? number : document.getNumber();
                    if (document.isTemplated() && !document.getName().equals(name)) {
                        flag = WTDocumentHelper.service.validDocTemplateIdentity(name, var17,
                                document.getContainerName());
                    }
                    LoggerHelper.log("perform Rename:" + flag);
                    if (flag) {
                        WTDocumentHelper.service.changeWTDocumentMasterIdentity((WTDocumentMaster) master, name, var17,
                                organization);
                    }
                } else if (master instanceof ManagedBaseline) {
                    // 更改基线名称
                    ManagedBaseline baseline = (ManagedBaseline) master;
                    ManagedBaselineIdentity baselineIdentity = ManagedBaselineIdentity
                            .newManagedBaselineIdentity(baseline);
                    baselineIdentity.setName(name != null && !name.isEmpty() ? name : baseline.getName());
                    baselineIdentity.setNumber(number != null && !number.isEmpty() ? number : baseline.getNumber());
                    if (!baselineIdentity.getName().equals(baseline.getName())
                            || !baselineIdentity.getNumber().equals(baseline.getNumber())) {
                        IdentityHelper.service.changeIdentity(master, baselineIdentity);
                        LoggerHelper.log("Set baseline identity");
                    }
                } else if (master instanceof ManagedCollection) {
                    // 更改管理集合
                    ManagedCollection managedCollection = (ManagedCollection) master;
                    ManagedCollectionIdentity collectionIdentity = ManagedCollectionIdentity
                            .newManagedCollectionIdentity(managedCollection);
                    collectionIdentity.setName(name != null && !name.isEmpty() ? name : managedCollection.getName());
                    collectionIdentity
                            .setNumber(number != null && !number.isEmpty() ? number : managedCollection.getNumber());
                    if (!collectionIdentity.getName().equals(managedCollection.getName())
                            || !collectionIdentity.getNumber().equals(managedCollection.getNumber())) {
                        IdentityHelper.service.changeIdentity(master, collectionIdentity);
                        LoggerHelper.log("Set managed collection identity");
                    }
                } else if (master instanceof OptionSetMaster) {
                    // 更改选项集名称
                    OptionSetMaster optionSetMaster = (OptionSetMaster) master;
                    OptionHelper.service.changeOptionSetMasterIdentity(optionSetMaster, name);
                    OptionHelper.service.dispatchVetoableEvent("RENAME_OPTION_SET", master);
                } else if (master instanceof OptionMaster) {
                    // 更改选项名称
                    OptionMaster optionMaster = (OptionMaster) master;
                    OptionHelper.service.changeOptionMasterIdentity(optionMaster, name);
                    OptionHelper.service.dispatchVetoableEvent("RENAME_OPTION", master);
                } else if (master instanceof ExpressionAliasMaster) {
                    ExpressionAliasMaster expressionAliasMaster = (ExpressionAliasMaster) master;
                    OptionHelper.service.changeExpressionAliasMasterIdentity(expressionAliasMaster, name, number);
                } else if (master instanceof ChoiceMaster) {
                    ChoiceMaster choiceMaster = (ChoiceMaster) master;
                    OptionHelper.service.changeChoiceMasterIdentity(choiceMaster, name, number);
                    OptionHelper.service.dispatchVetoableEvent("RENAME_CHOICE", master);
                } else if (master instanceof IndependentAssignedExpressionMaster) {
                    IndependentAssignedExpressionMaster independentAssignedExpressionMaster = (IndependentAssignedExpressionMaster) master;
                    OptionHelper.service.changeIAEMasterIdentity(independentAssignedExpressionMaster, name, number);
                } else {
                    // 判断是否为工艺资源的名称修改
                    if (MPMLinkFacade.isMPMLinkInstance(master)) {
                        MPMLinkHelper.service.changeMPMObjectMasterIdentity((Mastered) master, name, number);
                    } else {
                        if (InstalledProperties.isInstalled("Windchill.PDMLink") && master.getClass()
                                .isAssignableFrom(Class.forName("com.ptc.wpcfg.doc.VariantSpecMaster"))) {
                            DocHelper.service.changeVariantSpecMasterIdentity((VariantSpecMaster) master, name, number,
                                    organization);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.trace(e, e);
            LoggerHelper.log("CustomCommonUtil.updateNameAndNumberByObject  error ====> " + e.getMessage());
        }
    }

    /**
     * 获取 WTOrganization
     *
     * @param orgId 组织 ID
     * @return {@link WTOrganization}
     * @throws WTException WT异常
     */
    private static WTOrganization getWTOrganization(String orgId) throws WTException {
        WTOrganization organization = null;
        if (orgId != null && !orgId.isEmpty()) {
            TypeInstanceIdentifier typeInstanceIdentifier = (TypeInstanceIdentifier) DEFAULT_IDENTIFIER_FACTORY.get(orgId);
            if (typeInstanceIdentifier != null) {
                ObjectReference objectReference = TypeIdentifierUtility.getObjectReference(typeInstanceIdentifier);
                Persistable persistable = objectReference.getObject();
                if (persistable instanceof WTOrganization) {
                    organization = (WTOrganization) persistable;
                }
            }
        }
        return organization;
    }

    /**
     * 按前缀查找零件编号
     *
     * @param prefix 前缀
     * @return {@link String}
     */
    public static String findPartNumberByPrefix(String prefix) {
        return findLastSerialByColumnNotLength(prefix, WTPartMaster.NUMBER, WTPartMaster.class);
    }

    /**
     * 按前缀查找文档编号
     *
     * @param prefix 前缀
     * @return {@link String}
     */
    public static String findDocNumberByPrefix(String prefix) {
        return findLastSerialByColumnNotLength(prefix, WTDocumentMaster.NUMBER, WTDocumentMaster.class);
    }

    /**
     * 按列查找上一个序列号
     *
     * @param partNumberPrefix ## 零件编号前缀
     * @param column           列
     * @return WTPartMaster
     */
    public static String findLastSerialByColumnNotLength(String partNumberPrefix, String column, Class clazz) {
        LoggerHelper.log("CustomCommonUtil.findLastSerialNumberByPrefix Start");
        String number = null;
        try {
            QuerySpec qs = new QuerySpec();
            qs.appendSelect(new ClassAttribute(clazz, column), false);
            int tableIndex = qs.appendFrom(new ClassTableExpression(clazz));
            qs.setAdvancedQueryEnabled(true);
            CommonQuerySpec.addSearchConditionByClass(qs, tableIndex, clazz, column, SearchCondition.LIKE,
                    partNumberPrefix + SearchCondition.PATTERN_MATCH_MULITPLE);
            qs.appendAnd();
            CommonQuerySpec.addSearchNumberLimit(qs, 1, 0);
            CommonQuerySpec.addOrderByClass(qs, clazz, column, true, tableIndex);
            LoggerHelper.log("findLastSerialNumberByPrefix qs = ", qs);
            QueryResult qr = PersistenceHelper.manager.find(qs);
            if (qr.hasMoreElements()) {
                return (String) ((Object[]) qr.nextElement())[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoggerHelper.log("CustomCommonUtil.findLastSerialNumberByPrefix End");
        return number;
    }

    /**
     * 按号码查找Master
     *
     * @param originNumber 编号
     * @param clazz        master 类
     * @param column       column name
     * @return {@link Mastered}
     */
    public static Object findMasterByColumn(String originNumber, Class clazz, String column)
            throws RemoteException, InvocationTargetException {
        // 远程调用
        if (!RemoteMethodServer.ServerFlag) {
            return RemoteMethodServer.getDefault().invoke(
                    "findMasterByColumn",
                    CommonQueryHelper.class.getName(), null,
                    new Class[]{String.class, Class.class, String.class},
                    new Object[]{originNumber, clazz, column});
        } else {
            LoggerHelper.log("CustomCommonUtil.findLastSerialNumberByPrefix Start :" + LocalDateTime.now());
            try {
                QuerySpec querySpec = new QuerySpec(clazz);
                CommonQuerySpec.addSearchConditionByClass(querySpec, 0, clazz, column, SearchCondition.EQUAL,
                        originNumber);
                QueryResult qr = PersistenceHelper.manager.find(querySpec);
                if (qr.hasMoreElements()) {
                    return qr.nextElement();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            LoggerHelper.log("CustomCommonUtil.findLastSerialNumberByPrefix End :" + LocalDateTime.now());
            return null;
        }
    }

    /**
     * 按前缀查找零件
     *
     * @param keyword 关键词
     * @return {@link List }<{@link WTPart }>
     * @throws WTException WT异常
     */
    public static List<WTPart> findPartByPrefix(String keyword) throws WTException {
        return findObjectByPrefix(keyword, WTPart.newWTPart(), WTPart.NUMBER);
    }

    /**
     * 按前缀查找文档
     *
     * @param keyword 关键词
     * @return {@link List }<{@link WTPart }>
     * @throws WTException WT异常
     */
    public static List<WTDocument> findDocByPrefix(String keyword) throws WTException {
        return findObjectByPrefix(keyword, WTDocument.newWTDocument(), WTDocument.NUMBER);
    }

    /**
     * 按前缀查找零件
     *
     * @param prefix 前缀
     * @param t      t
     * @param cloumn 克鲁姆
     * @return {@link List }<{@link WTPart }>
     * @throws WTException WT异常
     */
    public static <T> List<T> findObjectByPrefix(String prefix, T t, String cloumn) throws WTException {
        QuerySpec querySpec = new QuerySpec(t.getClass());
        List<T> partList = new ArrayList<>();
        CommonQuerySpec.addSearchConditionByClass(querySpec, 0, t.getClass(), cloumn, SearchCondition.LIKE,
                prefix + SearchCondition.PATTERN_MATCH_MULITPLE);
        LoggerHelper.log("findObjectByPrefix querySpec = ", querySpec);
        QueryResult queryResult = PersistenceHelper.manager.find(querySpec);
        LatestConfigSpec latestConfigSpec = new LatestConfigSpec();
        QueryResult result = latestConfigSpec.process(queryResult);
        while (result.hasMoreElements()) {
            partList.add((T) result.nextElement());
        }
        return partList;
    }

    // /**
    // * 全部修改
    // *
    // * @param var0
    // * 变量0
    // * @return {@link WTKeyedMap}
    // * @throws WTException
    // * WT异常
    // */
    // public static WTKeyedMap reviseAll(WTKeyedMap var0) throws WTException {
    // Transaction var1 = null;
    //
    // try {
    // var1 = new Transaction();
    // var1.start();
    // WTPrincipal var2 = SessionHelper.manager.getPrincipal();
    // setEPMMultiReviseInProgress(true);
    // WTHashSet var3 = new WTHashSet();
    // WTKeyedHashMap var4 = new WTKeyedHashMap();
    // ReviseOptions.ObjectOptions var5 = null;
    // Iterator var6 = var0.entrySet().iterator();
    // while (var6.hasNext()) {
    // WTKeyedMap.WTEntry var7 = (WTKeyedMap.WTEntry) var6.next();
    // Versioned var8 = (Versioned) var7.getKeyAsPersistable();
    // validateObjectToRevise(var8, var2);
    // ReviseOptions var9 = (ReviseOptions) var7.getValue();
    // var5 = var8 instanceof WTPart ? var9.part : var9.document;
    // if (var5.versionId == null) {
    // var3.add(var8);
    // } else {
    // Object[] var10 = new Object[] { var5.versionId,
    // VersionControlHelper.firstIterationId(var8) };
    // var4.put(var8, var10);
    // }
    // }
    //
    // Iterator var22;
    // WTKeyedMap.WTEntry var24;
    // Versioned var27;
    // if (!var3.isEmpty()) {
    // WTKeyedMap var20 = VersionControlHelper.service.getNextReviseLabels(var3, 1);
    // var22 = var20.entrySet().iterator();
    //
    // while (var22.hasNext()) {
    // var24 = (WTKeyedMap.WTEntry) var22.next();
    // var27 = (Versioned) var24.getKeyAsPersistable();
    // List var29 = (List) var24.getValue();
    // if (!var29.isEmpty()) {
    // String var11 = (String) var29.get(var29.size() - 1);
    // MultilevelSeries var12 =
    // VersionControlHelper.getVersionIdentifierSeries(var27);
    // var12.setValueWithValidation(var11, (new StringTokenizer(var11,
    // ".")).countTokens());
    // VersionIdentifier var13 = VersionIdentifier.newVersionIdentifier(var12);
    // Object[] var14 = new Object[ ] { var13,
    // VersionControlHelper.firstIterationId(var27) };
    // var4.put(var27, var14);
    // }
    // }
    // }
    //
    // WTValuedMap var21 = VersionControlHelper.service.newVersions(var4, true);
    // var22 = var0.entrySet().iterator();
    //
    // Versioned var30;
    // while (var22.hasNext()) {
    // var24 = (WTKeyedMap.WTEntry) var22.next();
    // var27 = (Versioned) var24.getKeyAsPersistable();
    // var30 = (Versioned) var21.getPersistable(var27);
    // ReviseOptions var31 = (ReviseOptions) var24.getValue();
    // var5 = var27 instanceof WTPart ? var31.part : var31.document;
    // updateRevised(var27, var30, var31.teamTemplate, var5);
    // }
    //
    // PersistenceHelper.manager.store(var21.wtValues());
    // var1.commit();
    // var1 = null;
    // WTKeyedHashMap var23 = new WTKeyedHashMap();
    // Iterator var25 = var21.entrySet().iterator();
    //
    // while (var25.hasNext()) {
    // WTValuedMap.WTValuedEntry var28 = (WTValuedMap.WTValuedEntry) var25.next();
    // var30 = (Versioned) var28.getKeyAsPersistable();
    // Versioned var32 = (Versioned) var28.getValueAsPersistable();
    // var23.put(var30, var32);
    // }
    //
    // WTKeyedHashMap var26 = var23;
    // return var26;
    // } catch (WTPropertyVetoException var18) {
    // throw new WTException(var18);
    // } finally {
    // setEPMMultiReviseInProgress(false);
    // if (var1 != null) {
    // var1.rollback();
    // }
    //
    // }
    // }

    /**
     * 查找链接
     *
     * @param clazz       类
     * @param persistable 持久性
     * @param relateStr   关联 str
     * @param isReturnObj 是返回obj
     * @return {@code QueryResult }
     * @throws WTException WT异常
     */
    public static QueryResult findLink(Class clazz, Persistable persistable, String relateStr,
                                       Boolean isReturnObj) throws WTException {
        return PersistenceHelper.manager.navigate(persistable, relateStr, clazz, isReturnObj);
    }
}
