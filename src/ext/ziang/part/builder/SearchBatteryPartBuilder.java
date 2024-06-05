package ext.ziang.part.builder;

import java.util.HashMap;

import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;

import wt.util.WTException;

@ComponentBuilder("ext.trinasolar.eccb.builder.SearchBatteryPartBuilder")
public class SearchBatteryPartBuilder extends AbstractComponentBuilder {
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
		result.setLabel("搜索相关的5A");
		result.setSelectable(true);
		result.setSingleSelect(true);
		result.setId("SearchBatteryPartBuilder");
		// 设置展示数量
		result.setShowCount(true);
		result.setConfigurable(true);
		createNewColumnConfig("number", result, factory, false);
		createNewColumnConfig("name", result, factory, false);
		createNewColumnConfig("description", result, factory, true);
		createNewColumnConfig("version", result, factory, false);
		createNewColumnConfig("defaultUnit", result, factory, false);
		createNewColumnConfig("creator", result, factory, false);
		createNewColumnConfig("modifier", result, factory, false);
		createNewColumnConfig("thePersistInfo.createStamp", result, factory, false);
		createNewColumnConfig("thePersistInfo.modifyStamp", result, factory, false);
		return result;
	}

	@Override
	public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams)
			throws Exception {
		// 如果获取到参数则，进行设置
		// 通过查询提交 获取相关的参数
		NmHelperBean helperBean = ((JcaComponentParams) componentParams).getHelperBean();
		NmCommandBean commandBean = helperBean.getNmCommandBean();
		HashMap changedComboBox = commandBean.getChangedComboBox();
		System.out.println("changedComboBox = " + changedComboBox);
		// {nameproductType=[156.75], nametype=[P TYPE], nameproductClassify=[Backsheet], namefragment=[]}
		Object object = changedComboBox.get("nameproductType");
		Object object1 = changedComboBox.get("nametype");
		Object object2 = changedComboBox.get("nameproductClassify");
		Object object3 = changedComboBox.get("namefragment");
		changedComboBox.get("");

		// 编写查询

		// 编写内部控制相关
		return null;
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
	public static void createNewColumnConfig(String columnConfigName, TableConfig result,
			ComponentConfigFactory factory, boolean flag) {
		ColumnConfig modifyStamp = factory.newColumnConfig(columnConfigName, true);
		modifyStamp.setVariableHeight(true);
		if (flag) {
			modifyStamp.setDataUtilityId("CorrectBomDataUtility");
		}
		result.addComponent(modifyStamp);
	}

}
