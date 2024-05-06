package ext.ziang.change.handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ptc.core.components.beans.TreeHandlerAdapter;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.netmarkets.model.NmSimpleOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import ext.ziang.change.entity.CorrectBomEntity;
import ext.ziang.common.helper.part.CommonPartHelper;
import ext.ziang.common.util.CommonLog;
import wt.change2.AffectedActivityData;
import wt.change2.ChangeHelper2;
import wt.change2.WTChangeActivity2;
import wt.fc.ObjectReference;
import wt.fc.ObjectToObjectLink;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.fc.collections.WTCollection;
import wt.part.WTPart;
import wt.part.WTPartHelper;
import wt.part.WTPartMaster;
import wt.part.WTPartSubstituteLink;
import wt.part.WTPartUsageLink;
import wt.session.SessionServerHelper;
import wt.util.LocalizableMessage;
import wt.util.WTException;
import wt.vc.VersionControlHelper;

/**
 * 修改BOM树结构处理 异步
 *
 * @author anzhen
 * @date 2024/02/21
 */
public class AsyncCorrectBomBuilderHandler extends TreeHandlerAdapter {
	/**
	 * 正确 BOM 生成器处理程序
	 */
	public AsyncCorrectBomBuilderHandler() {
	}

	private NmCommandBean nmcommandbean;

	public AsyncCorrectBomBuilderHandler(ComponentParams params) throws WTException {
		this.nmcommandbean = ((JcaComponentParams) params).getHelperBean().getNmCommandBean();
	}

	/**
	 * 获取节点
	 *
	 * @param list
	 *            列表
	 * @return {@link Map}<{@link Object}, {@link List}>
	 * @throws WTException
	 *             WT异常
	 */
	@Override
	public Map<Object, List> getNodes(List list) {
		CommonLog.log("CorrectBomBuilderHandler.getNodes");
		CommonLog.log("GetNodes LocalDateTime.now() = ", LocalDateTime.now());
		CommonLog.log("list = ", list);
		HashMap hashMap = null;
		boolean bool = SessionServerHelper.manager.isAccessEnforced();
		try {
			hashMap = new HashMap();
			for (Object object : list) {
				CommonLog.log("object = ", object);
				CommonLog.log("object = ", object instanceof String);
				if (object instanceof CorrectBomEntity) {
					List<CorrectBomEntity> correctBomEntities = new ArrayList<>();
					CorrectBomEntity entity = (CorrectBomEntity) object;
					String oid = entity.getOid();
					hashMap = handlerChildNode(oid, correctBomEntities, entity);
				} else if (object instanceof NmSimpleOid) {
					NmSimpleOid nmSimpleOid = (NmSimpleOid) object;
					String internalName = nmSimpleOid.getInternalName();
					List<CorrectBomEntity> correctBomEntities = new ArrayList<>();
					CommonLog.log("nmSimpleOid.getProcess() = " + nmSimpleOid.getProcess());
					CommonLog.log("nmSimpleOid.getRef() = " + nmSimpleOid.getRef());
					hashMap = handlerChildNode(internalName, correctBomEntities, nmSimpleOid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionServerHelper.manager.setAccessEnforced(bool);
		}
		CommonLog.log("hashMap = " + hashMap);
		return hashMap;
	}

	/**
	 * 处理程序替换部件
	 *
	 * @param part
	 *            部分
	 * @param parentPart
	 *            父部分
	 * @param correctBomEntities
	 *            正确物料清单实体
	 */
	private static void handlerSubstitutePart(WTPart part, WTPart parentPart,
			List<CorrectBomEntity> correctBomEntities) {
		CommonLog.log("CorrectBomBuilderHandler.handlerSubstitutePart");
		if (parentPart == null || part == null) {
			return;
		}
		boolean flag = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			// 先查询替代件
			WTPartUsageLink link = CommonPartHelper.findWTPartUsageLink(parentPart, part);
			CommonLog.log("parentPart = " + parentPart.getNumber() + "=>  part" + part.getNumber());
			if (link == null) {
				return;
			}
			WTCollection links = WTPartHelper.service.getSubstituteLinks(link);
			CommonLog.log(String.format("当前物料{%s}替代件数量{%d}", part.getNumber(), links.size()));
			if (!links.isEmpty()) {
				// 遍历所有的替代件
				for (Object substitute : links) {
					ObjectReference reference = (ObjectReference) substitute;
					WTPartSubstituteLink substituteLink = (WTPartSubstituteLink) reference
							.getObject();
					WTPartMaster substituteMaster = (WTPartMaster) substituteLink.getRoleBObject();
					WTPart substitutePart = CommonPartHelper.findLatestWTPartByMasterAndView(
							substituteMaster, part.getViewName());
					CommonLog.log("substitutePart = ", substitutePart);
					correctBomEntities.add(convertBomEntity(substitutePart, substituteLink, part));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionServerHelper.manager.setAccessEnforced(flag);
		}
	}

	/**
	 * 获取根节点
	 *
	 * @return {@link List}<{@link Object}>
	 * @throws WTException
	 *             WT异常
	 */
	@Override
	public List getRootNodes() throws WTException {
		CommonLog.log("CorrectBomBuilderHandler.getRootNodes");
		ArrayList<CorrectBomEntity> beanList = new ArrayList<>();
		ArrayList<WTPart> partList = new ArrayList<>();
		boolean bool = SessionServerHelper.manager.isAccessEnforced();
		try {
			SessionServerHelper.manager.setAccessEnforced(false);
			Object primaryObj = nmcommandbean.getPrimaryOid().getRefObject();
			if (primaryObj instanceof WTChangeActivity2) {
				WTChangeActivity2 wtChangeActivity2 = (WTChangeActivity2) primaryObj;
				// 获取受影响对象
				QueryResult changeablesBefore = ChangeHelper2.service.getChangeablesAfter(wtChangeActivity2);
				CommonLog.log("changeablesBefore.size() = " + changeablesBefore.size());
				while (changeablesBefore.hasMoreElements()) {
					Object object = changeablesBefore.nextElement();
					CommonLog.log("object = " + object);
					if (object instanceof AffectedActivityData) {
						AffectedActivityData affectedActivityData = (AffectedActivityData) object;
						Persistable roleBObject = affectedActivityData.getRoleBObject();
						if (roleBObject instanceof WTPart) {
							WTPart part = (WTPart) roleBObject;
							LocalizableMessage iterationDisplayIdentifier = VersionControlHelper
									.getIterationDisplayIdentifier(part);
							CommonLog.log("iterationDisplayIdentifier = " + iterationDisplayIdentifier);
							partList.add(part);
							beanList.add(convertBomEntity(part, false));
						}
					} else if (object instanceof WTPart) {
						WTPart part = (WTPart) object;
						LocalizableMessage iterationDisplayIdentifier = VersionControlHelper
								.getIterationDisplayIdentifier(part);
						CommonLog.log("iterationDisplayIdentifier = " + iterationDisplayIdentifier);
						partList.add(part);
						beanList.add(convertBomEntity(part, false));
					}
				}
				CommonLog.log("partList = " + beanList.size());
				return beanList;
			} else {
				return new ArrayList<>();
			}
		} finally {
			SessionServerHelper.manager.setAccessEnforced(bool);
		}
	}

	/**
	 * 转换物料清单实体
	 *
	 * @param part
	 *            部分
	 * @return {@link CorrectBomEntity}
	 */
	public static CorrectBomEntity convertBomEntity(WTPart part, boolean flag) {
		CorrectBomEntity entity = new CorrectBomEntity();
		try {
			entity.setOid(part.getPersistInfo().getObjectIdentifier().toString());
			entity.setNumber(part.getNumber());
			entity.setName(part.getName());
			// entity.setDescription(IBAUtils.getIBAValue(part,
			// AttributeConstants.DESCRIPTION));
			entity.setModifier(part.getModifierFullName());
			entity.setCreator(part.getCreatorFullName());
			entity.setVersion(VersionControlHelper.getIterationDisplayIdentifier(part).toString());
			entity.setDefaultUnit(part.getDefaultUnit().getDisplay());
			entity.setModifyStamp(new Date(part.getModifyTimestamp().getTime()).toString());
			entity.setCreateStamp(new Date(part.getCreateTimestamp().getTime()).toString());
			entity.setSelect(flag);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换物料清单实体
	 *
	 * @param part
	 *            部分
	 * @return {@link CorrectBomEntity}
	 */
	public static CorrectBomEntity convertBomEntity(WTPart part, boolean flag, WTPart parentPart) {
		CorrectBomEntity entity = new CorrectBomEntity();
		try {
			entity.setOid(part.getPersistInfo().getObjectIdentifier().toString() + "|"
					+ parentPart.getPersistInfo().getObjectIdentifier().toString());
			entity.setNumber(part.getNumber());
			entity.setName(part.getName());
			// entity.setDescription(IBAUtils.getIBAValue(part,
			// AttributeConstants.DESCRIPTION));
			entity.setModifier(part.getModifierFullName());
			entity.setCreator(part.getCreatorFullName());
			entity.setVersion(VersionControlHelper.getIterationDisplayIdentifier(part).toString());
			entity.setDefaultUnit(part.getDefaultUnit().getDisplay());
			entity.setModifyStamp(new Date(part.getModifyTimestamp().getTime()).toString());
			entity.setCreateStamp(new Date(part.getCreateTimestamp().getTime()).toString());
			entity.setSelect(flag);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换物料清单实体
	 *
	 * @param part
	 *            部分
	 * @return {@link CorrectBomEntity}
	 */
	public static CorrectBomEntity convertBomEntity(WTPart part, ObjectToObjectLink objectLink, WTPart parentPart) {
		CorrectBomEntity entity;
		if (parentPart == null) {
			entity = convertBomEntity(part, true);
		} else {
			entity = convertBomEntity(part, true, parentPart);
		}
		if (entity == null) {
			return null;
		}
		if (objectLink instanceof WTPartUsageLink) {
			WTPartUsageLink link = (WTPartUsageLink) objectLink;
			if (link.getQuantity() != null) {
				if (link.getQuantity().getUnit() != null) {
					entity.setDefaultUnit(link.getQuantity().getUnit().toString());
				}
				entity.setAmount(String.valueOf(link.getQuantity().getAmount()));
			}
		} else if (objectLink instanceof WTPartSubstituteLink) {
			WTPartSubstituteLink link = (WTPartSubstituteLink) objectLink;
			if (link.getQuantity() != null) {
				if (link.getQuantity().getUnit() != null) {
					entity.setDefaultUnit(link.getQuantity().getUnit().toString());
				}
				entity.setAmount(String.valueOf(link.getQuantity().getAmount()));
				entity.setIsSubstitute("true");
				entity.setSubstitutePart(parentPart.getNumber());
			}
			entity.setIsSubstitute("true");
			entity.setSubstitutePart(parentPart.getNumber());
		}
		return entity;
	}

	/**
	 * 处理程序子节点
	 *
	 * @param oid
	 *            oid
	 * @param correctBomEntities
	 *            正确物料清单实体
	 * @param parentObj
	 *            父 OBJ
	 * @return {@link HashMap}<{@link Object}, {@link Object}>
	 * @throws WTException
	 *             WT异常
	 */
	public static HashMap<Object, Object> handlerChildNode(String oid, List<CorrectBomEntity> correctBomEntities,
			Object parentObj) throws WTException {
		HashMap<Object, Object> hashMap = new HashMap<>();
		CommonLog.log("oid = " + oid);
		String partObjId;
		String parentObjId = null;
		// 表示是传递上下文的情况
		if (oid.contains("^")) {
			oid = oid.replace("!*", "");
			String[] context = oid.split("\\$");
			String partInfo = context[context.length - 1];
			CommonLog.log("partInfo = " + partInfo);
			String[] split = partInfo.split("\\^");
			CommonLog.log("split = " + Arrays.toString(split));
			partObjId = split[split.length - 1];
			String[] customGeneralInfo = partObjId.split("\\|");
			CommonLog.log("customGeneralInfo = " + Arrays.toString(customGeneralInfo));
			partObjId = customGeneralInfo[0];
			if (customGeneralInfo.length == 2) {
				partObjId = customGeneralInfo[0];
				parentObjId = customGeneralInfo[1];
			}
		} else {
			String[] split = oid.split("\\|");
			partObjId = split[0];
			parentObjId = null;
			if (split.length == 2) {
				partObjId = split[0];
				parentObjId = split[1];
			}
		}
		CommonLog.log("parentObjId = " + parentObjId);
		CommonLog.log("partObjId = " + partObjId);

		WTPart part = CommonPartHelper.getWTPartByObjectId(partObjId);
		WTPart parentPart = null;
		if (StrUtil.isNotBlank(parentObjId)) {
			parentPart = CommonPartHelper.getWTPartByObjectId(parentObjId);
			CommonLog.log("parentPart.getName() = " + parentPart.getName());
			CommonLog.log("parentPart.getNumber() = " + parentPart.getNumber());
		}
		QueryResult qr = WTPartHelper.service.getUsesWTPartMasters(part);
		// 查询到剩余部件
		WTPartUsageLink link;
		WTPart latestPart;
		CommonLog.log("findChildNodeByRoot current Start =============> ");
		CommonLog.log("part.getName() = " + part.getName());
		CommonLog.log("part.getNumber() = " + part.getNumber());
		CommonLog.log("part.viewName() = " + part.getViewName());
		CommonLog.log("findChildNodeByRoot current End  =============> ");
		CommonLog.log("qr  = " + qr.size());
		handlerSubstitutePart(part, parentPart, correctBomEntities);
		while (qr.hasMoreElements()) {
			link = (WTPartUsageLink) qr.nextElement();
			// 构建一个子对象
			Persistable persistable = link.getRoleBObject();
			CommonLog.log("persistable = " + persistable);
			if (persistable instanceof WTPartMaster) {
				// 查询使用部件
				WTPartMaster master = (WTPartMaster) persistable;
				CommonLog.log("master = " + master);
				latestPart = CommonPartHelper.findLatestWTPartByMasterAndView(master, part.getViewName());
				if (latestPart == null) {
					continue;
				}
				CommonLog.log("findChildNodeByRoot current Start =============> ");
				CommonLog.log("child part.getName() = " + latestPart.getName());
				CommonLog.log("child part.getNumber() = " + latestPart.getNumber());
				CommonLog.log("child part.viewName() = " + latestPart.getViewName());
				CommonLog.log("findChildNodeByRoot current End  =============> ");
				correctBomEntities.add(convertBomEntity(latestPart, link, part));
			}
		}
		if (CollUtil.isNotEmpty(correctBomEntities)) {
			hashMap.put(parentObj, correctBomEntities);
		}
		return hashMap;
	}
}
