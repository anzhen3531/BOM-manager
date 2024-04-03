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

import cn.hutool.core.util.StrUtil;
import ext.ziang.common.util.CommonLog;
import ext.ziang.common.util.ToolUtils;
import wt.fc.Persistable;

/**
 * 执行批改builder
 *
 * @author anzhen
 * @date 2024/02/20
 */

@ComponentBuilder({ "ext.ziang.change.SelectTargetBomBuilder" })
public class SelectTargetBomBuilder extends AbstractComponentBuilder {
	/**
	 * 构建组件配置
	 *
	 * @param componentparams
	 *            组件参数
	 * @return {@link ComponentConfig}
	 */
	public ComponentConfig buildComponentConfig(ComponentParams componentparams) {
		// 获取组件配置工厂
		ComponentConfigFactory factory = getComponentConfigFactory();
		TableConfig result = factory.newTableConfig();
		result.setLabel("选择需要复制的BOM");
		result.setSelectable(true);
		result.setSingleSelect(false);
		result.setId("SelectTargetBomBuilder");
		// 设置展示数量
		result.setShowCount(true);
		result.setConfigurable(true);
		result.setActionModel("commonOperationModels");

		createNewColumnConfig("number", result, factory, false);
		createNewColumnConfig("name", result, factory, false);
		createNewColumnConfig("description", result, factory, true);
		createNewColumnConfig("version", result, factory, false);
		createNewColumnConfig("defaultUnit", result, factory, false);
		createNewColumnConfig("iterationInfo.creator", result, factory, false);
		createNewColumnConfig("iterationInfo.modifier", result, factory, false);
		createNewColumnConfig("thePersistInfo.createStamp", result, factory, false);
		createNewColumnConfig("thePersistInfo.modifyStamp", result, factory, false);

		return result;
	}

	@Override
	public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams)
			throws Exception {
		CommonLog.printLog("SelectOriginBomBuilder buildComponentData");
		String oidList = (String) componentParams.getParameter("oidList");
		JcaComponentParams jcaComponentParams = (JcaComponentParams) componentParams;
		NmHelperBean helperBean = jcaComponentParams.getHelperBean();
		System.out.println("helperBean.getRequest().getParameterMap() = "
				+ helperBean.getRequest().getParameterMap());
		System.out.println("oidList = " + oidList);
		ArrayList<Persistable> returnList = new ArrayList<>();
		if (StrUtil.isBlank(oidList)) {
			return returnList;
		} else {
			if (StrUtil.isNotBlank(oidList)) {
				String[] split = oidList.split(",");
				for (String oid : split) {
					returnList.add(ToolUtils.getObjectByOid(oid));
				}
			}
			return returnList;
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