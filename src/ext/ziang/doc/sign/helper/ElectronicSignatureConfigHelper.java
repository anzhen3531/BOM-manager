package ext.ziang.doc.sign.helper;

import java.util.ArrayList;
import java.util.List;

import ext.ziang.common.util.CommonLog;
import ext.ziang.model.ElectronicSignatureConfig;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtility;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

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
	 * @return {@link ElectronicSignatureConfig}
	 * @throws WTException
	 *             WT异常
	 */
	public static List<ElectronicSignatureConfig> findAllElectronicSignatureConfig()
			throws WTException {
		List<ElectronicSignatureConfig> electronicSignatureConfigs = new ArrayList<>();
		QuerySpec querySpec = new QuerySpec(ElectronicSignatureConfig.class);
		CommonLog.log("findAllElectronicSignatureConfig querySpec = ", querySpec);
		QueryResult queryResult = PersistenceHelper.manager.find(querySpec);
		while (queryResult.hasMoreElements()) {
			electronicSignatureConfigs.add((ElectronicSignatureConfig) queryResult.nextElement());
		}
		return electronicSignatureConfigs;
	}

	/**
	 * 查找电子签名配置
	 *
	 * @param docType 文档类型
	 * @return {@link ElectronicSignatureConfig}
	 * @throws WTException WT异常
	 */
	public static ElectronicSignatureConfig findElectronicSignatureConfig(String docType)
			throws WTException {
		QuerySpec querySpec = new QuerySpec(ElectronicSignatureConfig.class);
		SearchCondition searchCondition = new SearchCondition(ElectronicSignatureConfig.class,
				ElectronicSignatureConfig.DOC_TYPE,
				SearchCondition.EQUAL, docType);
		querySpec.appendWhere(searchCondition, new int[] { 0 });
		CommonLog.log("querySpec = ", querySpec);
		QueryResult queryResult = PersistenceHelper.manager.find(querySpec);
		if (queryResult.hasMoreElements()) {
			return (ElectronicSignatureConfig) queryResult.nextElement();
		}
		return null;
	}

	/**
	 * 更新电子签名配置
	 * 查找电子签名配置
	 *
	 * @param config
	 *            配置
	 * @return {@link ElectronicSignatureConfig}
	 * @throws WTException
	 *             WT异常
	 */
	public static ElectronicSignatureConfig createOrUpdate(ElectronicSignatureConfig config)
			throws WTException, WTPropertyVetoException {
		TypeDefinitionReference objectType = TypedUtility.getTypeDefinitionReference(config.getDocType());
		if (objectType == null) {
			throw new WTException("当前签名对象没有配置对应的文档类型");
		}
		ElectronicSignatureConfig electronicSignatureConfig = findElectronicSignatureConfig(config.getDocType());
		if (electronicSignatureConfig != null) {
			// copy属性
			config.setContentType(electronicSignatureConfig.getContentType());
			config.setWorkItemName(electronicSignatureConfig.getWorkItemName());
			config.setSignXIndex(electronicSignatureConfig.getSignXIndex());
			config.setSignYIndex(electronicSignatureConfig.getSignYIndex());
			config.setStatus(electronicSignatureConfig.getStatus());
			config.setExtendedField(electronicSignatureConfig.getExtendedField());
			config.setExtendedField1(electronicSignatureConfig.getExtendedField1());
			config.setExtendedField2(electronicSignatureConfig.getExtendedField2());
		}
		return (ElectronicSignatureConfig) PersistenceHelper.manager.save(config);
	}
}
