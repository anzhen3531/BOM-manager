package ext.ziang.change.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.htmlcomp.components.AbstractConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.ComponentResultProcessor;
import com.ptc.mvc.components.TreeConfig;
import com.ptc.mvc.components.TreeDataBuilderAsync;
import com.ptc.mvc.components.TreeNode;

import ext.ziang.change.handler.CorrectBomBuilderHandler;
import wt.util.WTException;

/**
 * 执行批改builder 异步
 *
 * @author anzhen
 * @date 2024/02/20
 */

@ComponentBuilder({ "ext.ziang.change.builder.SingleCorrectBomBuilder" })
public class SingleCorrectBomBuilder extends AbstractConfigurableTableBuilder implements TreeDataBuilderAsync {

	/**
	 * 处理器
	 */
	private CorrectBomBuilderHandler handler = null;

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
		ComponentConfigFactory factory = super.getComponentConfigFactory();
		TreeConfig result = factory.newTreeConfig();
		result.setLabel("批量修改BOM");
		result.setSelectable(true);
		result.setSingleSelect(false);
		result.setId("SingleCorrectBomBuilder");
		// 设置展示数量
		result.setShowCount(true);
		// 设置展示树行数
		result.setShowTreeLines(true);
		// 设置展示级别
		result.setExpansionLevel(DescriptorConstants.TableTreeProperties.FULL_EXPAND);
		result.setConfigurable(true);
		result.setNodeColumn("number");
		result.setActionModel("CorrectBomModel");
		createNewColumnConfig("number", result, factory, true);
		createNewColumnConfig("name", result, factory, true);
		createNewColumnConfig("substitutePart", "替代的物料", result, factory, false);
		createNewColumnConfig("description", result, factory, true);
		createNewColumnConfig("version", result, factory, false);
		createNewColumnConfig("defaultUnit", result, factory, false);
		createNewColumnConfig("amount", result, factory, false);
		createNewColumnConfig("creator", result, factory, false);
		createNewColumnConfig("modifier", result, factory, false);
		ColumnConfig isSelect = factory.newColumnConfig("isSelect", true);
		isSelect.setDataUtilityId("CorrectBomDataUtility");
		result.addComponent(isSelect);
		result.setNonSelectableColumn(isSelect);
		return result;
	}

	@Override
	public void buildNodeData(Object node, ComponentResultProcessor resultProcessor) throws Exception {
		System.out.println("SingleCorrectBomBuilder.buildNodeData");
		System.out.println("node = " + node + ", resultProcessor = " + resultProcessor);
		if (node == TreeNode.RootNode) {
			// 实例化
			handler = new CorrectBomBuilderHandler(resultProcessor.getParams());
			List<Object> objects = handler.getRootNodes();
			System.out.println("objects = " + objects);
			resultProcessor.addElements(objects);
		} else {
			List nodeList = new ArrayList();
			nodeList.add(node);
			Map<Object, List> map = handler.getNodes(nodeList);
			Set keySet = map.keySet();
			for (Object key : keySet) {
				resultProcessor.addElements(map.get(key));
			}
		}
	}

	/**
	 * 构建组件数据
	 *
	 * @param componentconfig
	 *            组件配置
	 * @param componentparams
	 *            组件参数
	 * @return {@link CorrectBomBuilderHandler}
	 * @throws WTException
	 *             WT异常
	 */
	public CorrectBomBuilderHandler buildComponentData(ComponentConfig componentconfig,
			ComponentParams componentparams) throws WTException {
		return new CorrectBomBuilderHandler();
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
	public static void createNewColumnConfig(String columnConfigName, TreeConfig result,
			ComponentConfigFactory factory, boolean flag) {
		ColumnConfig modifyStamp = factory.newColumnConfig(columnConfigName, true);
		modifyStamp.setVariableHeight(true);
		if (flag) {
			modifyStamp.setDataUtilityId("CorrectBomDataUtility");
		}
		result.addComponent(modifyStamp);
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
	public static void createNewColumnConfig(String columnConfigName, String columnConfigLabel, TreeConfig result,
			ComponentConfigFactory factory, boolean flag) {
		ColumnConfig modifyStamp = factory.newColumnConfig(columnConfigName, columnConfigLabel, true);
		modifyStamp.setVariableHeight(true);
		if (flag) {
			modifyStamp.setDataUtilityId("CorrectBomDataUtility");
		}
		result.addComponent(modifyStamp);
	}

	@Override
	public ConfigurableTable buildConfigurableTable(String s) throws WTException {
		return null;
	}

}