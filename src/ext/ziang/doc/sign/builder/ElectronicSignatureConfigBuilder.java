package ext.ziang.doc.sign.builder;

import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;

import ext.ziang.doc.sign.helper.ElectronicSignatureConfigHelper;
import ext.ziang.model.ElectronicSignatureConfig;
import wt.util.WTException;

/**
 * 电子签名配置生成器
 * 通用配置过滤器生成器
 *
 * @author anzhen
 * @date 2024/02/13
 */
@ComponentBuilder({ "ext.ziang.doc.sign.builder.ElectronicSignatureConfigBuilder" })
public class ElectronicSignatureConfigBuilder extends AbstractComponentBuilder {
	/**
	 * 构建组件配置
	 *
	 * @param componentparams
	 *            组件参数
	 * @return {@link ComponentConfig}
	 * @throws WTException
	 *             WT异常
	 */
	public ComponentConfig buildComponentConfig(ComponentParams componentparams) throws WTException {
		// 获取组件配置工厂
		ComponentConfigFactory factory = getComponentConfigFactory();
		TableConfig result = factory.newTableConfig();
		result.setLabel("查看当前电子签名配置");
		result.setSelectable(true);
		result.setSingleSelect(true);
		result.setId("ElectronicSignatureConfigBuilder");
		result.setShowCount(true);
		result.setConfigurable(true);
		createNewColumnConfig(ElectronicSignatureConfig.DOC_TYPE_NAME, "相关文档类型名称", result, factory);
		createNewColumnConfig(ElectronicSignatureConfig.CONTENT_TYPE, "内容类型", result, factory);
		createNewColumnConfig(ElectronicSignatureConfig.WORK_ITEM_NAME, "流程名称", result, factory);
		createNewColumnConfig(ElectronicSignatureConfig.SIGN_XINDEX, "X轴", result, factory);
		createNewColumnConfig(ElectronicSignatureConfig.SIGN_YINDEX, "Y轴", result, factory);
		createNewColumnConfig(ElectronicSignatureConfig.STATUS, "状态", result, factory);
		createNewColumnConfig(ElectronicSignatureConfig.EXTENDED_FIELD, "扩展字段", result, factory);
		createNewColumnConfig(ElectronicSignatureConfig.EXTENDED_FIELD1, "扩展字段1", result, factory);
		createNewColumnConfig(ElectronicSignatureConfig.EXTENDED_FIELD2, "扩展字段2", result, factory);
		return result;
	}

	/**
	 * 构建组件数据
	 *
	 * @param componentConfig
	 *            组件配置
	 * @param componentParams
	 *            组件参数
	 * @return {@link Object}
	 */
	@Override
	public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams)
			throws WTException {
		return ElectronicSignatureConfigHelper.findAllElectronicSignatureConfig();
	}

	/**
	 * 创建新列配置
	 *
	 * @param columnConfigName
	 *            列配置名称
	 * @param result
	 *            结果
	 * @param factory
	 *            厂
	 */
	public static void createNewColumnConfig(String columnConfigName, String displayName, TableConfig result,
			ComponentConfigFactory factory) {
		ColumnConfig modifyStamp = factory.newColumnConfig(columnConfigName, displayName, true);
		modifyStamp.setVariableHeight(true);
		// modifyStamp.setDataUtilityId("ElectronicSignatureConfigUtility");
		result.addComponent(modifyStamp);
	}
}