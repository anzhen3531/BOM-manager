package ext.ziang.part.datautility;

import java.util.HashMap;

import com.google.common.collect.Maps;
import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.*;
import com.ptc.core.ui.resources.ComponentMode;

import ext.ziang.common.helper.CommonGuiComponentHelper;
import wt.part.WTPart;
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
		if (obj instanceof WTPart) {
			WTPart wtPart = (WTPart) obj;
			return wtPart.getNumber();
		}
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
	private Object createPickerComponent(String componentId, Object obj, ModelContext modelContext) throws WTException {
		Object localObject = super.getDataValue(componentId, obj, modelContext);
		if (localObject instanceof AttributeInputCompositeComponent) {
			AttributeInputCompositeComponent inputCompositeComponent = (AttributeInputCompositeComponent) localObject;
			AttributeInputComponent inputComponent = inputCompositeComponent.getValueInputComponent();
			GUIComponentArray guiArray = new GUIComponentArray();
			IconComponent clearIconComponent = new IconComponent();
			clearIconComponent.setSrc("netmarkets/images/clear_16x16.gif");
			clearIconComponent.setTooltip("清除");
			StringBuffer bufferJs = new StringBuffer();
			String[] clearElements = new String[] { "number" };
			for (String elementId : clearElements) {
				bufferJs.append("var element = window.parent.document.getElementById('" + elementId + "');");
				bufferJs.append("if(element != null) {element.value = ''};");
				bufferJs.append("if(element != null) {element.readOnly = false};");
			}
			clearIconComponent.addJsAction("onClick", bufferJs.toString());
			HashMap<String, Object> maps = Maps.newHashMap();
			maps.put("wtPartTypeName", "112321");
			// 创建选取器
			SuggestTextBox suggestTextBom = CommonGuiComponentHelper.newSuggestTextBox(
					inputComponent.getId(), inputComponent.getName(),
					inputComponent.getColumnName(), inputComponent.isRequired(),
					inputComponent.isReadOnly(), inputComponent.isEditable(), inputComponent.isEnabled(), 1,
					"StandardPartPickerSuggestable", maps, 40, "");
			guiArray.addGUIComponent(suggestTextBom);
			guiArray.addGUIComponent(clearIconComponent);
			return guiArray;
		}
		return localObject;
	}

}
