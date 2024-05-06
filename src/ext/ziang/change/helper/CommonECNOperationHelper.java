package ext.ziang.change.helper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.TypeIdentifierHelper;
import com.ptc.windchill.enterprise.copy.server.CoreMetaUtility;

import cn.hutool.core.util.StrUtil;
import ext.ziang.common.helper.user.CommonWTPrincipalHelper;
import ext.ziang.common.util.CommonLog;
import ext.ziang.common.util.ToolUtils;
import wt.change2.AffectedActivityData;
import wt.change2.ChangeHelper2;
import wt.change2.ChangeNoticeComplexity;
import wt.change2.ChangeRecord2;
import wt.change2.IncludedInIfc;
import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.inf.container.WTContainer;
import wt.inf.library.WTLibrary;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle._State;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.project.Role;
import wt.query.ClassAttribute;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.TableColumn;
import wt.session.SessionServerHelper;
import wt.team.Team;
import wt.team.TeamHelper;
import wt.team.TeamReference;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtility;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * 通用 ECN operation 助手
 *
 * @author ander
 * @date 2023/11/13
 */
public class CommonECNOperationHelper {
	/**
	 * ECCB Store libray 名称
	 */
	public static String ECCB_STORE_LIBRAY_NAME = "ECCB变更库";

	/**
	 * 创建 ECN BY 模板
	 *
	 * @param name
	 *            名字
	 * @param desc
	 *            描述
	 * @param number
	 *            编号
	 * @param date
	 *            日期
	 * @param container
	 *            容器
	 * @param templateName
	 *            模板名称
	 * @param folderPath
	 *            文件夹路径
	 * @param typeName
	 *            键入名称
	 * @return {@link WTChangeOrder2}
	 */
	public static WTChangeOrder2 createECNByTemplate(String name, String desc, String number, Date date,
			WTContainer container, String templateName, String folderPath, String typeName) {
		// 设置创建者
		boolean flag = SessionServerHelper.manager.setAccessEnforced(false);
		WTChangeOrder2 ecn = null;
		try {
			WTChangeOrder2 order2 = findByECNNumber(number);
			if (order2 != null) {
				return order2;
			}
			ecn = WTChangeOrder2.newWTChangeOrder2(name);
			ecn.setChangeNoticeComplexity(ChangeNoticeComplexity.BASIC);
			ecn.setDescription(desc);
			ecn.setNumber(number);
			ToolUtils.assignFolder(container, folderPath, ecn);
			ecn.setNeedDate(new Timestamp(date.getTime()));
			TypeDefinitionReference tdRef = TypedUtility.getTypeDefinitionReference(typeName);
			ecn.setTypeDefinitionReference(tdRef);
			ecn = (WTChangeOrder2) ChangeHelper2.service.saveChangeOrder(ecn, true);
			WTChangeOrder2 templateEcn = findTemplateByECNNumber(templateName);
			addUserToTeam(ecn.getTeamId(), "wcadmin");
			if (templateEcn != null) {
				createECAByTemplate(ecn, templateEcn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionServerHelper.manager.setAccessEnforced(flag);
		}
		return ecn;
	}

	/**
	 * 通过ECN number查找模板
	 * select *
	 * from WTCHANGEORDER2
	 * where IDA3MASTERREFERENCE in (select ida2a2
	 * from WTCHANGEORDER2MASTER
	 * where name = '电池变更'
	 * and CLASSNAMEKEYCONTAINERREFEREN = 'wt.inf.container.OrgContainer');
	 *
	 * @param eccbName
	 *            ECCB型
	 */
	private static WTChangeOrder2 findTemplateByECNNumber(String eccbName) throws WTException {
		QuerySpec querySpec = new QuerySpec(WTChangeOrder2.class);
		querySpec.appendWhere(new SearchCondition(new ClassAttribute(WTChangeOrder2.class, WTChangeOrder2.NAME),
				SearchCondition.EQUAL, new ConstantExpression(eccbName)), new int[] { 0 });
		querySpec.appendAnd();
		querySpec.appendWhere(new SearchCondition(new ClassAttribute(WTChangeOrder2.class, WTChangeOrder2.TEMPLATED),
				SearchCondition.EQUAL, new ConstantExpression(1)), new int[] { 0 });
		CommonLog.log("查询ECN querySpec = ", querySpec);
		QueryResult qr = PersistenceHelper.manager.find(querySpec);
		if (qr.hasMoreElements()) {
			return (WTChangeOrder2) qr.nextElement();
		}
		return null;
	}

	/**
	 * 创建 ECA BY 模板
	 *
	 * @param originEca
	 *            原产地 ECA
	 * @param templateEcn
	 *            模板ECN
	 */
	public static void createECAByTemplate(WTChangeOrder2 originEca, WTChangeOrder2 templateEcn)
			throws WTException, WTPropertyVetoException {
		QueryResult queryResult = PersistenceHelper.manager.navigate(templateEcn, "theChangeActivityIfc",
				IncludedInIfc.class);
		while (queryResult.hasMoreElements()) {
			Object object = queryResult.nextElement();
			if (object instanceof WTChangeActivity2) {
				// 先创建ECA
				WTChangeActivity2 eca = (WTChangeActivity2) object;
				WTChangeActivity2 activity2 = WTChangeActivity2.newWTChangeActivity2();
				activity2.setName(eca.getName());
				activity2.setDescription(eca.getDescription());
				activity2.setOwnership(eca.getOwnership());
				activity2.setTeamId(eca.getTeamId());
				activity2.setContainer(originEca.getContainer());
				activity2.setTypeDefinitionReference(eca.getTypeDefinitionReference());
				// 创建关联关系
				ChangeHelper2.service.saveChangeActivity(originEca, activity2);
				// TODO 未完成
				TeamReference teamReference = addUserToTeam(activity2.getTeamId(), "wcadmin");
				activity2.setTeamId(teamReference);
				PersistenceHelper.manager.save(activity2);
			}
		}
	}

	/**
	 * 关闭变更通知单
	 *
	 * @param ecn
	 *            ECN对象
	 * @param ecnState
	 *            ECN状态
	 * @param ecaState
	 *            ECA状态
	 * @throws WTException
	 *             WT异常
	 */
	public static void closeChangeOrder(WTChangeOrder2 ecn, String ecnState, String ecaState) throws WTException {
		try {
			LifeCycleHelper.service.setLifeCycleState(ecn, _State.toState(ecnState));
			// 设置状态
			QueryResult qr = ChangeHelper2.service.getChangeActivities(ecn);
			while (qr.hasMoreElements()) {
				Object object = qr.nextElement();
				if (object instanceof WTChangeActivity2) {
					WTChangeActivity2 activity2 = (WTChangeActivity2) object;
					LifeCycleHelper.service.setLifeCycleState(activity2,
							_State.toState(ecaState));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WTException(e);
		}
	}

	/**
	 * 按 ECNnumber 查找
	 * select *
	 * from WTCHANGEORDER2
	 * where IDA3MASTERREFERENCE in (select ida2a2
	 * from WTCHANGEORDER2MASTER
	 * where name = '电池变更'
	 * and CLASSNAMEKEYCONTAINERREFEREN = 'wt.inf.container.OrgContainer');
	 *
	 * @param eccbNumber
	 *            ECCB编号
	 * @return {@link WTChangeOrder2}
	 * @throws WTException
	 *             WT异常
	 */
	private static WTChangeOrder2 findByECNNumber(String eccbNumber) throws WTException {
		QuerySpec querySpec = new QuerySpec(WTChangeOrder2.class);
		querySpec.appendWhere(new SearchCondition(new ClassAttribute(WTChangeOrder2.class, WTChangeOrder2.NUMBER),
				SearchCondition.EQUAL, new ConstantExpression(eccbNumber)), new int[] { 0 });
		// querySpec.appendAnd();
		// querySpec.appendWhere(new SearchCondition(new
		// ClassAttribute(WTChangeOrder2.class, WTChangeOrder2.TEMPLATED),
		// SearchCondition.EQUAL, new ConstantExpression(0)), new int[] { 0 });
		System.out.println("查询ECN querySpec = " + querySpec);
		QueryResult qr = PersistenceHelper.manager.find(querySpec);
		if (qr.hasMoreElements()) {
			return (WTChangeOrder2) qr.nextElement();
		}
		return null;
	}

	/**
	 * 将用户添加到团队
	 *
	 * @param reference
	 *            参考
	 */
	public static TeamReference addUserToTeam(TeamReference reference, String userName) throws WTException {
		Persistable object = reference.getObject();
		Team team = (Team) object;
		WTUser wtUser = CommonWTPrincipalHelper.getUserByFullName(userName);
		Vector roles = team.getRoles();
		for (Object roleObj : roles) {
			if (roleObj instanceof Role) {
				Role role = (Role) roleObj;
				String display = role.getDisplay(Locale.CHINA);
				if (display.equals("工作负责人")) {
					TeamHelper.service.addRolePrincipalMap(role, wtUser, team);
				}
			}
		}
		return TeamReference.newTeamReference(team);
	}

	/**
	 * 查找相同 ECN CHILD ECA 通过 活动和名称
	 *
	 * @param activity2
	 *            eca
	 * @param name
	 *            名字
	 * @return {@link WTChangeActivity2}
	 * @throws WTException
	 *             WT异常
	 */
	public static WTChangeActivity2 findSameECNChildECAByActivityAndName(WTChangeActivity2 activity2, String name)
			throws WTException {
		WTChangeActivity2 sampleActivity = null;
		// 获取ECN
		QueryResult result = ChangeHelper2.service.getChangeOrder(activity2);
		if (result.hasMoreElements()) {
			Object object = result.nextElement();
			if (object instanceof WTChangeOrder2) {
				WTChangeOrder2 order2 = (WTChangeOrder2) object;
				sampleActivity = findActivityByName(order2, name);
			}
		}
		return sampleActivity;
	}

	/**
	 * 按名称查找活动
	 *
	 * @param order2
	 *            订单2
	 * @param name
	 *            名字
	 * @return {@link WTChangeActivity2}
	 */
	public static WTChangeActivity2 findActivityByName(WTChangeOrder2 order2, String name) throws WTException {
		WTChangeActivity2 activity2 = null;
		QueryResult changeActivities = ChangeHelper2.service.getChangeActivities(order2);
		while (changeActivities.hasMoreElements()) {
			Object element = changeActivities.nextElement();
			if (element instanceof WTChangeActivity2) {
				WTChangeActivity2 changeActivity2 = (WTChangeActivity2) element;
				if (changeActivity2.getName().contains(name)) {
					activity2 = changeActivity2;
				}
			}
		}
		return activity2;
	}

	/**
	 * 查找 ECN BY ECA
	 *
	 * @param activity2
	 *            活动2
	 * @return {@link WTChangeOrder2}
	 */
	public static WTChangeOrder2 findECNByECA(WTChangeActivity2 activity2) throws WTException {
		WTChangeOrder2 order2 = null;
		QueryResult result = ChangeHelper2.service.getChangeOrder(activity2);
		if (result.hasMoreElements()) {
			Object object = result.nextElement();
			if (object instanceof WTChangeOrder2) {
				order2 = (WTChangeOrder2) object;
			}
		}
		return order2;
	}

	/**
	 * 查找产生对象
	 *
	 * @param activity2
	 *            活动2
	 * @return {@link List}<{@link WTPart}>
	 */
	public static List<WTPart> findGenerateObject(WTChangeActivity2 activity2) throws WTException {
		List<WTPart> wtParts = new ArrayList<>();
		QueryResult queryResult = ChangeHelper2.service.getChangeablesAfter(activity2);
		while (queryResult.hasMoreElements()) {
			Object object = queryResult.nextElement();
			if (object instanceof WTPart) {
				wtParts.add((WTPart) object);
			}
		}
		return wtParts;
	}

	/**
	 * 验证是否存在产生对象
	 *
	 * @param object
	 *            对象
	 * @param activity2
	 *            活动2
	 * @return {@link ChangeRecord2}
	 * @throws WTException
	 *             WTException
	 */
	public static ChangeRecord2 validateExistRecord2(Object object, WTChangeActivity2 activity2)
			throws WTException {
		QuerySpec querySpec = new QuerySpec(ChangeRecord2.class);
		String aliasAt = querySpec.getFromClause().getAliasAt(0);
		long activity2Id = activity2.getBranchIdentifier();
		long documentId;
		if (object instanceof WTPart) {
			documentId = ((WTPart) object).getPersistInfo().getObjectIdentifier().getId();
		} else {
			documentId = ((WTDocument) object).getPersistInfo().getObjectIdentifier().getId();
		}
		// 查询是否重复绑定
		querySpec.appendWhere(new SearchCondition(new TableColumn(aliasAt, "BRANCHIDA3A5"), SearchCondition.EQUAL,
				ConstantExpression.newExpression(activity2Id)), new int[] { 0 });
		querySpec.appendAnd();
		querySpec.appendWhere(new SearchCondition(new TableColumn(aliasAt, "IDA3B5"), SearchCondition.EQUAL,
				ConstantExpression.newExpression(documentId)), new int[] { 0 });
		System.out.println("querySpec = " + querySpec);
		QueryResult qr = PersistenceHelper.manager.find(querySpec);
		System.out.println("qr.size() = " + qr.size());
		if (qr.hasMoreElements()) {
			return (ChangeRecord2) qr.nextElement();
		}
		return null;
	}

	/**
	 * 创建产生对象link
	 *
	 * @param object
	 *            文档
	 * @param activity2
	 *            eca
	 * @throws WTException
	 */
	public static void createRecord2LinkObject(Object object, WTChangeActivity2 activity2) throws WTException {
		System.out.println("ExtWTChangeActivityHelper.createRecord2Link");
		System.out.println("object = " + object + ", activity2 = " + activity2);
		ChangeRecord2 record2 = validateExistRecord2(object, activity2);
		if (record2 != null) {
			System.out.println("当前已经Eca关联了产生了相同的对象");
			// throw new WTException("当前已经Eca关联了产生了相同的对象");
		}
		ChangeRecord2 changeRecord2 = null;
		if (object instanceof WTDocument) {
			changeRecord2 = ChangeRecord2.newChangeRecord2((WTDocument) object, activity2);
		} else if (object instanceof WTPart) {
			changeRecord2 = ChangeRecord2.newChangeRecord2((WTPart) object, activity2);
		}
		Vector vector = new Vector();
		vector.add(changeRecord2);
		ChangeHelper2.service.saveChangeRecord(vector);
	}

	/**
	 * 创建ECN
	 *
	 * @param name
	 *            名字
	 * @param description
	 *            描述
	 * @param needDate
	 *            2024/01/08
	 *            需要日期
	 * @param type
	 *            com.ziang.BatteryChange
	 *            类型
	 * @return {@link WTChangeOrder2}
	 */
	public static WTChangeOrder2 createECN(String name, String description, String needDate,
			String type) {
		System.out.println("==start Time" + LocalDateTime.now());
		WTChangeOrder2 ecn = null;
		try {
			ecn = WTChangeOrder2.newWTChangeOrder2(name);
			ecn.setChangeNoticeComplexity(ChangeNoticeComplexity.BASIC);
			if (StrUtil.isNotBlank(description)) {
				ecn.setDescription(description);
			}
			// 需要时间
			if (StrUtil.isNotBlank(needDate)) {
				Date dh = new Date(needDate);
				ecn.setNeedDate(new Timestamp(dh.getTime()));
			}

			// 设置容器
			WTLibrary library = findWTLibraryByName("ECCB变更库");
			ecn.setContainer(library);
			if (StrUtil.isNotBlank(type)) {
				TypeIdentifier id = TypeIdentifierHelper.getTypeIdentifier(type);
				// TypeIdentifier id = TypeHelper.getTypeIdentifier(type);
				ecn = (WTChangeOrder2) CoreMetaUtility.setType(ecn, id);
			}
			// 查询模板。 之后去固定相关ECN
			// ECA 直接CopyECN的即可
			// 之后创建eca 通过模板创建 // 没模板则不创建
			ecn = (WTChangeOrder2) ChangeHelper2.service.saveChangeOrder(ecn);
			// 设置ECA
			System.out.println("==end of creating ECN " + ecn);
			System.out.println("==end Time" + LocalDateTime.now());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ecn;
	}

	/**
	 * 根据容器得到容器的文件夹
	 *
	 * @return 产品库
	 */
	public static WTLibrary findWTLibraryByName(String cProductFamily) {
		WTLibrary library = null;
		boolean access = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			QuerySpec qs = new QuerySpec(WTLibrary.class);
			SearchCondition namecontainerinfo = new SearchCondition(
					new KeywordExpression("A0.NAMECONTAINERINFO"),
					SearchCondition.EQUAL,
					new KeywordExpression("'" + cProductFamily + "'"));
			qs.appendWhere(namecontainerinfo, new int[] { 0 });
			System.out.println("查询产品库 = " + qs);
			QueryResult qr = PersistenceHelper.manager.find(qs);
			if (qr.hasMoreElements()) {
				library = (WTLibrary) qr.nextElement();
			}
		} catch (WTException e) {
			e.printStackTrace();
		} finally {
			SessionServerHelper.manager.setAccessEnforced(access);
		}
		return library;
	}

	/**
	 * 创建受影响活动数据
	 *
	 * @param object
	 *            文档
	 * @param activity2
	 *            eca
	 * @throws WTException
	 *             WT异常
	 */
	public static void createAffectedActivityData(Object object, WTChangeActivity2 activity2) throws WTException {
		System.out.println("ExtWTChangeActivityHelper.createRecord2Link");
		AffectedActivityData affectedActivityData = validateExistActivityData(object, activity2);
		if (affectedActivityData != null) {
			throw new WTException("当前已经Eca关联了产生了同类型对象");
		}
		AffectedActivityData affectedData = null;
		if (object instanceof WTDocument) {
			affectedData = AffectedActivityData.newAffectedActivityData((WTDocument) object, activity2);
		} else if (object instanceof WTPart) {
			affectedData = AffectedActivityData.newAffectedActivityData((WTPart) object, activity2);
		}
		Vector vector = new Vector();
		vector.add(affectedData);
		ChangeHelper2.service.saveAffectedActivityData(vector);
	}

	/**
	 * 验证存在活动数据
	 *
	 * @param object
	 *            对象
	 * @param activity2
	 *            活动2
	 * @return {@link AffectedActivityData}
	 * @throws WTException
	 *             WT异常
	 */
	private static AffectedActivityData validateExistActivityData(Object object, WTChangeActivity2 activity2)
			throws WTException {
		QuerySpec querySpec = new QuerySpec(AffectedActivityData.class);
		String aliasAt = querySpec.getFromClause().getAliasAt(0);
		long activity2Id = activity2.getBranchIdentifier();
		long documentId;
		if (object instanceof WTPart) {
			documentId = ((WTPart) object).getPersistInfo().getObjectIdentifier().getId();
		} else {
			documentId = ((WTDocument) object).getPersistInfo().getObjectIdentifier().getId();
		}
		// 查询是否重复绑定
		querySpec.appendWhere(new SearchCondition(new TableColumn(aliasAt, "BRANCHIDA3A5"), SearchCondition.EQUAL,
				ConstantExpression.newExpression(activity2Id)), new int[] { 0 });
		querySpec.appendAnd();
		querySpec.appendWhere(new SearchCondition(new TableColumn(aliasAt, "IDA3B5"), SearchCondition.EQUAL,
				ConstantExpression.newExpression(documentId)), new int[] { 0 });
		System.out.println("querySpec = " + querySpec);
		QueryResult qr = PersistenceHelper.manager.find(querySpec);
		System.out.println("qr.size() = " + qr.size());
		if (qr.hasMoreElements()) {
			return (AffectedActivityData) qr.nextElement();
		}
		return null;
	}
}
