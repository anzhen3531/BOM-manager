package ext.ziang.common.helper.mpm;

import cn.hutool.core.util.StrUtil;
import com.ptc.core.meta.common.impl.TypeIdentifierUtilityHelper;
import com.ptc.windchill.mpml.MPMDocumentDescribeLink;
import com.ptc.windchill.mpml.MPMDocumentManageable;
import com.ptc.windchill.mpml.processplan.MPMProcessPlan;
import com.ptc.windchill.mpml.processplan.operation.MPMOperation;
import com.ptc.windchill.mpml.processplan.operation.MPMOperationMaster;
import com.ptc.windchill.mpml.processplan.operation.MPMOperationUsageLink;
import ext.ziang.common.helper.query.CommonQueryHelper;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.config.LatestConfigSpec;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * mpm custom 帮助程序
 *
 * @author anzhen
 * @date 2024/05/25
 */
public class MPMCustomHelper {

	/**
	 * 通过 MPMOBJECT 获取 MPMDOC 描述
	 *
	 * @param manageable
	 *            管理
	 * @return {@code List<WTDocument> }
	 * @throws WTException
	 *             WT异常
	 */
	public static List<WTDocument> getMPMDocDescribeByMPMObject(MPMDocumentManageable manageable) throws WTException {
		List<WTDocument> docList = new ArrayList<>();
		LatestConfigSpec latestConfigSpec = new LatestConfigSpec();
		QueryResult result = latestConfigSpec
				.process(CommonQueryHelper.findLink(MPMDocumentDescribeLink.class,
						manageable, MPMDocumentDescribeLink.DESCRIBED_BY_ROLE, true));
		if (result != null) {
			while (result.hasMoreElements()) {
				Object obj = result.nextElement();
				if (obj instanceof WTDocument) {
					WTDocument doc = (WTDocument) obj;
					docList.add(doc);
				}
			}
		}
		return docList;
	}

	/**
	 * 获取流程计划文档描述
	 *
	 * @param plan
	 *            计划
	 * @param docType
	 *            文档类型
	 * @return {@code WTDocument }
	 * @throws WTException
	 *             WT异常
	 * @throws RemoteException
	 *             远程异常
	 */
	public static WTDocument getDocDescribeOfProcessPlan(MPMProcessPlan plan, String docType)
			throws WTException, RemoteException {
		List<WTDocument> docList = getMPMDocDescribeByMPMObject(plan);
		WTDocument docDescribe = null;
		for (WTDocument doc : docList) {
			// 获取文档的类型进行判断
			String type = TypeIdentifierUtilityHelper.service.getTypeIdentifier(doc).toString();
			if (StrUtil.contains(type, docType)) {
				docDescribe = doc;
				break;
			}
		}
		return docDescribe;
	}

	/**
	 * 查找子操作列表
	 *
	 * @param mpmProcessPlan
	 *            MPM工艺计划
	 * @return {@code List<MPMOperation> }
	 *
	 */
	public static List<MPMOperationUsageLink> findChildOperationList(MPMProcessPlan mpmProcessPlan) throws WTException {
		QueryResult linkQr = CommonQueryHelper.findLink(MPMOperationUsageLink.class, mpmProcessPlan,
				MPMOperationUsageLink.USES_ROLE, false);
		List<MPMOperationUsageLink> operationList = new ArrayList<>();
		while (linkQr.hasMoreElements()) {
			Object object = linkQr.nextElement();
			if (object instanceof MPMOperationUsageLink) {
				operationList.add((MPMOperationUsageLink) object);
			}
		}
		return operationList;
	}
}
