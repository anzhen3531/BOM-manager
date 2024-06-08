package ext.ziang.common.helper.part;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import com.ptc.windchill.uwgm.common.associate.AssociationType;

import ext.ziang.common.helper.query.CommonQueryHelper;
import wt.epm.EPMDocument;
import wt.epm.build.EPMBuildRule;
import wt.fc.ObjectIdentifier;
import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
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
public class PartHelper {
	/**
	 * 按号码获取 WTPART Master
	 *
	 * @param originNumber
	 *            产地编号
	 * @return {@link WTPartMaster}
	 */
	public static WTPartMaster getWTPartMasterByNumber(String originNumber)
			throws RemoteException, InvocationTargetException {
		return (WTPartMaster) CommonQueryHelper.findMasterByColumn(originNumber, WTPartMaster.class,
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
	 * @param componentWorkCopy
	 *            组件工作副本
	 * @param originLatestPart
	 *            Origin 最新部分
	 * @return {@link WTPartUsageLink}
	 */
	public static WTPartUsageLink findWTPartUsageLink(WTPart componentWorkCopy, WTPart originLatestPart) {
		boolean flag = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			QueryResult result = PersistenceHelper.manager.find(WTPartUsageLink.class, componentWorkCopy,
					WTPartUsageLink.USED_BY_ROLE, originLatestPart.getMaster());
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

	/**
	 * 获取与部件相关活动 EPM 模型
	 *
	 * @param part
	 *            部分
	 * @return {@link EPMDocument }
	 */
	public static EPMDocument getPartRelatedActiveEPM3D(WTPart part) throws WTException {
		EPMDocument epm3D = null;
		QueryResult qr = PersistenceHelper.manager.navigate(part, EPMBuildRule.BUILD_SOURCE_ROLE, EPMBuildRule.class,
				false);
		// 过滤所有者关系
		while (qr.hasMoreElements()) {
			EPMBuildRule rule = (EPMBuildRule) qr.nextElement();
			// 查询关联规则
			AssociationType type = AssociationType.getAssociationType(rule, true);
			if ("ACTIVE".equals(type.name())) {
				EPMDocument epm = (EPMDocument) rule.getBuildSource();
				// 判断文档类型为CAD装配和 CAD组件
				if (epm.getDocType().toString().equals("CADASSEMBLY")
						|| epm.getDocType().toString().equals("CADCOMPONENT")) {
					epm3D = epm;
				}
				break;
			}
		}
		return epm3D;
	}
}
