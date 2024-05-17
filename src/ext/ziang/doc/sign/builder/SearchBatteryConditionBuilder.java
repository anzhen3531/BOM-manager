package ext.ziang.doc.sign.builder;

import java.util.HashMap;

import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.AttributeConfig;
import com.ptc.mvc.components.AttributePanelConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.GroupConfig;

import wt.util.WTException;

/**
 * 搜索电池状态生成器
 *
 * @author anzhen
 * @date 2024/04/16
 */
@ComponentBuilder("ext.trinasolar.eccb.builder.SearchBatteryConditionBuilder")
public class SearchBatteryConditionBuilder extends AbstractComponentBuilder {
	public SearchBatteryConditionBuilder() {
	}

	public Object buildComponentData(ComponentConfig componentconfig, ComponentParams componentparams)
			throws Exception {
		HashMap<String, Object> workAttInfo = new HashMap();
		workAttInfo.put("productType", "");
		workAttInfo.put("type", "");
		workAttInfo.put("productClassify", "");
		workAttInfo.put("fragment", "");
		workAttInfo.put("workspace", "");
		workAttInfo.put("SearchCommand", "");
		return workAttInfo;
	}

	/**
	 * 构建组件配置
	 *
	 * @param componentparams
	 *            componentparams
	 * @return {@link AttributePanelConfig}
	 * @throws WTException
	 *             WT异常
	 */
	public AttributePanelConfig buildComponentConfig(ComponentParams componentparams) throws WTException {
		ComponentConfigFactory componentconfigfactory = this.getComponentConfigFactory();
		AttributePanelConfig config = componentconfigfactory.newAttributePanelConfig();
		GroupConfig groupconfig = componentconfigfactory.newGroupConfig("basicInfo", "搜索条件", 1);
		config.addComponent(groupconfig);
		AttributeConfig productType = this.getComponentConfigFactory().newAttributeConfig("productType", "产品系列", 0, 0);
		productType.setDataUtilityId("SearchConditionDataUtility");
		groupconfig.addComponent(productType);
		AttributeConfig type = this.getComponentConfigFactory().newAttributeConfig("type", "电池类型", 1, 0);
		type.setDataUtilityId("SearchConditionDataUtility");
		groupconfig.addComponent(type);
		AttributeConfig productClassify = this.getComponentConfigFactory().newAttributeConfig("productClassify", "单双玻",
				0, 1);
		productClassify.setDataUtilityId("SearchConditionDataUtility");
		groupconfig.addComponent(productClassify);
		AttributeConfig fragment = this.getComponentConfigFactory().newAttributeConfig("fragment", "分片方式", 1, 1);
		fragment.setDataUtilityId("SearchConditionDataUtility");
		groupconfig.addComponent(fragment);
		AttributeConfig workspace = this.getComponentConfigFactory().newAttributeConfig("workspace", "车间", 2, 2);
		workspace.setDataUtilityId("SearchConditionDataUtility");
		groupconfig.addComponent(workspace);
		AttributeConfig SerchCommand = this.getComponentConfigFactory().newAttributeConfig("SearchCommand", "", 2, 2);
		SerchCommand.setDataUtilityId("SearchConditionDataUtility");
		groupconfig.addComponent(SerchCommand);
		return config;
	}
}
