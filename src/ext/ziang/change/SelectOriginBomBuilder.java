package ext.ziang.change;

import java.util.ArrayList;

import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;
import com.ptc.netmarkets.util.beans.NmHelperBean;

import ext.ziang.common.util.CommonLogPrintUtil;
import ext.ziang.common.util.ToolUtils;
import wt.part.WTPart;
import wt.util.WTException;

/**
 * 执行批改builder
 *
 * @author anzhen
 * @date 2024/02/20
 */

@ComponentBuilder({ "ext.ziang.change.SelectOriginBomBuilder" })
public class SelectOriginBomBuilder extends AbstractComponentBuilder {
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
		result.setLabel("选择产生对象BOM");
		result.setSelectable(true);
		result.setSingleSelect(true);
		result.setId("ext.ziang.change.SelectOriginBomBuilder");
		// 设置展示数量
		result.setShowCount(true);
		result.setConfigurable(true);
		result.setActionModel("commonOperationModels");

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
	public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams) throws WTException {
		CommonLogPrintUtil.printLog("SelectOriginBomBuilder buildComponentData");
		Object oidList = componentParams.getParameter("data");
		JcaComponentParams jcaComponentParams = (JcaComponentParams) componentParams;
		NmHelperBean helperBean = jcaComponentParams.getHelperBean();
		System.out.println("helperBean.getRequest().getParameterMap() = "
				+ helperBean.getRequest().getParameterMap());
		System.out.println("oidList = " + oidList);
		ArrayList<WTPart> returnList = new ArrayList<>();
		if (oidList == null) {
			return returnList;
		} else {
			return ToolUtils.getObjectByOid((String) oidList);
		}
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