package ext.ziang.part.datautility;

import java.util.Map;

import com.ptc.core.components.descriptor.ComponentDescriptor;
import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.PickerRenderConfigs;
import com.ptc.core.components.rendering.guicomponents.PickerInputComponent;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.netmarkets.util.beans.NmCommandBean;

import ext.ziang.part.pciker.StandardPickerConfig;
import wt.util.WTException;

/**
 * 实验原材料数据单元
 *
 * @author anzhen
 * @date 2024/02/20
 */
public class StandardPickerDataUtility extends DefaultDataUtility {
	/**
	 * @param componentId
	 *            表格列id
	 * @param obj
	 *            当前的表格对象
	 * @param modelContext
	 *            上下文
	 * @return
	 * @throws WTException
	 */
	@Override
	public Object getDataValue(String componentId, Object obj, ModelContext modelContext) throws WTException {
		ComponentMode descriptorMode = modelContext.getDescriptorMode();
		if (descriptorMode == ComponentMode.EDIT || descriptorMode == ComponentMode.CREATE) {
			setPickerConfig(componentId, modelContext);
			return createPickerComponent(componentId, obj, modelContext);
		} else if (descriptorMode == ComponentMode.VIEW) {
			return createDisplayComponent(componentId, obj, modelContext);
		} else {
			return "";
		}
	}

	/**
	 * 创建显示组件
	 *
	 * @param componentId
	 *            组件 ID
	 * @param obj
	 *            OBJ系列
	 * @param modelContext
	 *            模型上下文
	 * @return {@link Object}
	 */
	private Object createDisplayComponent(String componentId, Object obj, ModelContext modelContext) {
		return null;
	}

	/**
	 * 创建选取器组件
	 *
	 * @param componentId
	 *            组件 ID
	 * @param obj
	 *            OBJ系列
	 * @param modelContext
	 *            模型上下文
	 * @return {@link Object}
	 */
	private Object createPickerComponent(String componentId, Object obj, ModelContext modelContext) {
		PickerInputComponent pickerComponent = new PickerInputComponent();
		StandardPickerConfig pickerConfig = new StandardPickerConfig();
		pickerComponent.setEditable(false);
	}

	/**
	 * 设置选取器配置
	 *
	 * @param params
	 *            组件 ID
	 * @param modelContext
	 *            模型上下文
	 */
	private void setPickerConfig(String params, ModelContext modelContext) {
		NmCommandBean nmCommandBean = modelContext.getNmCommandBean();
		ComponentDescriptor descriptor = modelContext.getDescriptor();
		Map<Object, Object> properties = descriptor.getProperties();
		StandardPickerConfig.setPickerProperties(params, nmCommandBean, properties);
	}
}
