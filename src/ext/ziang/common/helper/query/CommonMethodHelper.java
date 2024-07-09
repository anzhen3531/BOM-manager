package ext.ziang.common.helper.query;

import com.ptc.core.command.common.bean.repository.PageMode;
import com.ptc.core.command.common.bean.repository.ResultContainer;
import com.ptc.core.managedcollection.ManagedCollectionIdentity;
import com.ptc.core.meta.common.*;
import com.ptc.core.meta.container.common.AttributeContainerSet;
import com.ptc.core.meta.container.common.AttributeContainerSpec;
import com.ptc.core.meta.server.TypeIdentifierUtility;
import com.ptc.core.query.command.common.BasicQueryCommand;
import com.ptc.core.query.common.CriteriaHelper;
import com.ptc.core.query.common.RelationalConfigSpecAttributeContainerFunction;
import com.ptc.windchill.classproxy.WorkPackageClassProxy;
import com.ptc.windchill.mpml.MPMLinkHelper;
import com.ptc.windchill.option.model.*;
import com.ptc.windchill.option.service.OptionHelper;
import com.ptc.wpcfg.doc.DocHelper;
import com.ptc.wpcfg.doc.VariantSpecMaster;
import ext.ziang.common.helper.CommonQuerySpec;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import wt.pom.DBProperties;
import wt.query.ClassAttribute;
import wt.query.ClassTableExpression;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.services.applicationcontext.implementation.DefaultServiceProvider;
import wt.session.SessionServerHelper;
import wt.type.ClientTypedUtility;
import wt.util.InstalledProperties;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.Iterated;
import wt.vc.Mastered;
import wt.vc.baseline.ManagedBaseline;
import wt.vc.baseline.ManagedBaselineIdentity;
import wt.vc.config.LatestConfigSpec;
import wt.vc.views.ViewHelper;

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
public class CommonMethodHelper implements RemoteAccess {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommonMethodHelper.class);
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
    public static void updateNameAndNumberByObject(Persistable persistable, String name, String number, String orgId) throws WTException {
        WTOrganization organization = getWTOrganization(orgId);
        updateNameAndNumberByObject(persistable, name, number, organization);
    }

    /**
     * 按对象更新名称和编号
     *
     * @param persistable  实例对象
     * @param name         名称
     * @param number       编码
     * @param organization
     */
    public static void updateNameAndNumberByObject(Persistable persistable, String name, String number, WTOrganization organization) {
        LOGGER.error("persistable = " + persistable + ", name = " + name + ", number = " + number + ", organization = " + organization);
        Identified master = null;
        if (persistable instanceof Iterated) {
            Iterated iterated = (Iterated) persistable;
            master = (Identified) iterated.getMaster();
        } else if (persistable instanceof Identified) {
            master = (Identified) persistable;
        }
        try {
            if (master != null) {
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
                    LOGGER.debug("WTDocument object:" + document);
                    String newNumber = number != null && !number.isEmpty() ? number : document.getNumber();
                    if (document.isTemplated() && !document.getName().equals(name)) {
                        flag = WTDocumentHelper.service.validDocTemplateIdentity(name, newNumber, document.getContainerName());
                    }
                    LOGGER.debug("perform Rename:" + flag);
                    if (flag) {
                        WTDocumentHelper.service.changeWTDocumentMasterIdentity((WTDocumentMaster) master, name, newNumber,
                                organization);
                    }
                } else if (master instanceof ManagedBaseline) {
                    // 更改基线名称
                    ManagedBaseline baseline = (ManagedBaseline) master;
                    ManagedBaselineIdentity baselineIdentity = ManagedBaselineIdentity.newManagedBaselineIdentity(baseline);
                    baselineIdentity.setName(name != null && !name.isEmpty() ? name : baseline.getName());
                    baselineIdentity.setNumber(number != null && !number.isEmpty() ? number : baseline.getNumber());
                    if (!baselineIdentity.getName().equals(baseline.getName())
                            || !baselineIdentity.getNumber().equals(baseline.getNumber())) {
                        IdentityHelper.service.changeIdentity(master, baselineIdentity);
                        LOGGER.debug("Set baseline identity");
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
                        LOGGER.debug("Set managed collection identity");
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
            LOGGER.error("CustomCommonUtil.updateNameAndNumberByObject  error ====> " + e.getMessage(), e);
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
        LOGGER.debug("CustomCommonUtil.findLastSerialNumberByPrefix Start");
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
            LOGGER.debug("findLastSerialNumberByPrefix qs {}", qs);
            QueryResult qr = PersistenceHelper.manager.find(qs);
            if (qr.hasMoreElements()) {
                return (String) ((Object[]) qr.nextElement())[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.debug("CustomCommonUtil.findLastSerialNumberByPrefix End");
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
                    CommonMethodHelper.class.getName(), null,
                    new Class[]{String.class, Class.class, String.class},
                    new Object[]{originNumber, clazz, column});
        } else {
            LOGGER.debug("CustomCommonUtil.findLastSerialNumberByPrefix Start :" + LocalDateTime.now());
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
            LOGGER.debug("CustomCommonUtil.findLastSerialNumberByPrefix End :" + LocalDateTime.now());
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
        LOGGER.debug("findObjectByPrefix querySpec = ", querySpec);
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

    /**
     * Describe：查询指定试图物料对应的属性值是否唯一
     * 可以用来查询物料描述等字段是否是全局唯一的
     *
     * @param partNumber   物料编号
     * @param ibaAttrName  属性名称
     * @param ibaAttrValue 属性值
     * @param viewName     试图名称
     * @param type         类型
     * @return 部件
     * @throws WTException WTException
     */
    public static List<Persistable> findWTPartIsOnly(String partNumber, String ibaAttrName, String ibaAttrValue,
                                                     String viewName, String type) throws WTException {
        List<Persistable> listPersistables = Lists.newArrayList();
        boolean flag = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            // 获取类型Id
            TypeIdentifier typeIdentifier = TypeIdentifierHelper.getTypeIdentifier(type);
            // 获取iba属性id
            AttributeTypeIdentifier attrAti = ClientTypedUtility.getAttributeTypeIdentifier(ibaAttrName, typeIdentifier);
            // 获取视图id
            AttributeTypeIdentifier viewNameAti = ClientTypedUtility.getAttributeTypeIdentifier("view.id", typeIdentifier);
            // 获取最新版本
            RelationalConfigSpecAttributeContainerFunction function = new RelationalConfigSpecAttributeContainerFunction();
            function.setTypeDefinition(typeIdentifier);
            function.setRelationalConfigSpec(new LatestConfigSpec().getRelationalConfigSpec());

            AttributeContainerSpec filter = new AttributeContainerSpec();
            filter.putEntries(new AttributeTypeIdentifier[]{attrAti, viewNameAti});
            filter.setNextOperation(new DisplayOperationIdentifier());
            int querylimit = DBProperties.PAGING_SNAPSHOT_QUERY_LIMIT;
            if (querylimit < 0) {
                querylimit = 20000;
            }
            BasicQueryCommand queryCmd = new BasicQueryCommand();
            try {
                queryCmd.setPageMode(PageMode.EXPLICIT_PAGING);
                queryCmd.setCount(querylimit);
                queryCmd.setOffset(0);
                queryCmd.setFilter(filter);
                // 最新版本条件查询
                AttributeContainerSet functionCriteria = CriteriaHelper.newCriteria(function, false);
                queryCmd.appendCriteria(functionCriteria, false);
                if (StringUtils.isNotBlank(viewName)) {
                    Object attrValue = (Object) PersistenceHelper
                            .getObjectIdentifier(ViewHelper.service.getView(viewName)).getId();
                    AttributeContainerSet viewCriteria = CriteriaHelper.newCriteria(viewNameAti, attrValue, false);
                    queryCmd.appendCriteria(viewCriteria, false);
                }
                // 增加值条件
                AttributeContainerSet attrCriteria = CriteriaHelper.newCriteria(attrAti, WildcardSet.EQUALS,
                        ibaAttrValue, false);
                queryCmd.appendCriteria(attrCriteria, false);
                queryCmd = (BasicQueryCommand) queryCmd.execute();
                ResultContainer result = queryCmd.getResultContainer();
                int size = result.getSize();
                for (int i = 0; i < size; ++i) {
                    Persistable persistable = new ReferenceFactory()
                            .getReference(result.getTypeInstanceAt(i).getPersistenceIdentifier()).getObject();
                    String persistableNumber = "";
                    if (persistable instanceof WTPart) {
                        WTPart wtPart = (WTPart) persistable;
                        persistableNumber = wtPart.getNumber();
                    }
                    // 判断当前编号和传递的编号是否一致 不一致则存储
                    if (!StringUtils.equals(persistableNumber, partNumber)) {
                        listPersistables.add(persistable);
                    }
                }
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            } catch (WTPropertyVetoException e) {
                e.printStackTrace();
            }
        } finally {
            SessionServerHelper.manager.setAccessEnforced(flag);
        }
        return listPersistables;
    }
}
