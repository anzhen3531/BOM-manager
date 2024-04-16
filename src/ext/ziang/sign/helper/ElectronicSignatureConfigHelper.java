package ext.ziang.sign.helper;

import ext.ziang.common.util.CommonLog;
import ext.ziang.model.ElectronicSignatureConfig;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;

/**
 * 通用配置筛选器帮助程序
 *
 * @author anzhen
 * @date 2024/02/13
 */
public class ElectronicSignatureConfigHelper {

	/**
	 * 查找电子签名配置
	 *
	 * @param persistable
	 *            持久性
	 * @return {@link ElectronicSignatureConfig}
	 * @throws WTException
	 *             WT异常
	 */
	public static ElectronicSignatureConfig findElectronicSignatureConfig(Persistable persistable)
			throws WTException {
		QuerySpec querySpec = new QuerySpec(ElectronicSignatureConfig.class);
		SearchCondition searchCondition = new SearchCondition(ElectronicSignatureConfig.class,
				ElectronicSignatureConfig.OBJECT_TYPE,
				SearchCondition.EQUAL, persistable.getPersistInfo().getObjectIdentifier());
		querySpec.appendWhere(searchCondition, new int[] { 0 });
		CommonLog.printLog("querySpec = ", querySpec);
		QueryResult queryResult = PersistenceHelper.manager.find(querySpec);
		if (queryResult.hasMoreElements()) {
			return (ElectronicSignatureConfig) queryResult.nextElement();
		}
		return null;
	}

	// 保存

	// 修改

	// 创建

}
