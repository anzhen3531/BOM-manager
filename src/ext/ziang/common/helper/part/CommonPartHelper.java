package ext.ziang.common.helper.part;

import ext.ziang.common.util.CustomCommonUtil;
import wt.fc.ObjectIdentifier;
import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.lifecycle._State;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartStandardConfigSpec;
import wt.part.WTPartUsageLink;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;

/**
 * 公共部件助手
 *
 * @author anzhen
 * @date 2024/04/20
 */
public class CommonPartHelper {
	/**
	 * 按号码获取 WTPART Master
	 *
	 * @param originNumber
	 *            产地编号
	 * @return {@link WTPartMaster}
	 */
	public static WTPartMaster getWTPartMasterByNumber(String originNumber) {
		return (WTPartMaster) CustomCommonUtil.findMasterByNumber(originNumber, WTPartMaster.class,
				WTPartMaster.NUMBER);
	}

	/**
	 * 通过 Master,视图 查找最新 WTPart
	 *
	 * @param partMaster
	 *            零件母版
	 * @param viewName
	 *            视图名称
	 * @return {@link WTPart}
	 * @throws WTException
	 *             WT异常
	 */
	public static WTPart findLatestWTPartByMasterAndView(WTPartMaster partMaster, String viewName) throws WTException {
		View view = ViewHelper.service.getView(viewName);
		WTPartStandardConfigSpec spec = WTPartStandardConfigSpec.newWTPartStandardConfigSpec(view, null);
		QueryResult queryResult = VersionControlHelper.service.allIterationsOf(partMaster);
		QueryResult process = spec.process(queryResult);
		if (process.hasMoreElements()) {
			return (WTPart) process.nextElement();
		}
		return null;
	}

	/**
	 * 通过 Master,视图,状态 查找最新 WTPart
	 *
	 * @param partMaster
	 *            部件主数据
	 * @param viewName
	 *            视图
	 * @param stateName
	 *            状态名称
	 * @return {@link WTPart}
	 * @throws WTException
	 *             WT异常
	 */
	public static WTPart findLatestWTPartByMasterAndViewAndState(WTPartMaster partMaster, String viewName,
			String stateName) throws WTException {
		View view = ViewHelper.service.getView(viewName);
		WTPartStandardConfigSpec spec = WTPartStandardConfigSpec.newWTPartStandardConfigSpec(view,
				_State.toState(stateName));
		QueryResult queryResult = VersionControlHelper.service.allIterationsOf(partMaster);
		QueryResult process = spec.process(queryResult);
		if (process.hasMoreElements()) {
			return (WTPart) process.nextElement();
		}
		return null;
	}

	/**
	 * 按对象 ID 获取 WTPartt
	 *
	 * @param partObjId
	 *            零件 obj ID
	 * @return {@link WTPart}
	 * @throws WTException
	 *             WT异常
	 */
	public static WTPart getWTPartByObjectId(String partObjId) throws WTException {
		ObjectIdentifier objectIdentifier = ObjectIdentifier.newObjectIdentifier(partObjId);
		ObjectReference objectReference = ObjectReference.newObjectReference(objectIdentifier);
		return (WTPart) objectReference.getObject();
	}

	/**
	 * 查找 WTPart 使用链接
	 *
	 * @param componentWorkCopy 组件工作副本
	 * @param originLatestPart  Origin 最新部分
	 * @return {@link WTPartUsageLink}
	 */
	public static WTPartUsageLink findWTPartUsageLink(WTPart componentWorkCopy, WTPart originLatestPart) {
		boolean flag = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			QueryResult result = PersistenceServerHelper.manager.query(WTPartUsageLink.class, componentWorkCopy,
					WTPartUsageLink.USES_ROLE, originLatestPart.getMaster());
			if (result.hasMoreElements()) {
				return (WTPartUsageLink) result.nextElement();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionServerHelper.manager.setAccessEnforced(flag);
		}
		return null;
	}
}
