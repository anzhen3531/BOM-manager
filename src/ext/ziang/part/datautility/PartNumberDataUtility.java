//package ext.ziang.part.datautility;
//
//
//import java.util.HashMap;
//
//import wt.part.WTPart;
//import wt.util.WTException;
//import cn.witsoft.guicomponent.service.StandarGuiComponentServiceImpl;
//
//import com.google.common.collect.Maps;
//import com.ptc.core.components.descriptor.ModelContext;
//import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
//import com.ptc.core.components.rendering.guicomponents.AttributeInputComponent;
//import com.ptc.core.components.rendering.guicomponents.AttributeInputCompositeComponent;
//import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
//import com.ptc.core.components.rendering.guicomponents.IconComponent;
//import com.ptc.core.components.rendering.guicomponents.SuggestTextBox;
//import com.ptc.core.ui.resources.ComponentMode;
//
//public class PartNumberDataUtility extends DefaultDataUtility {
//
//	/**
//	 *
//	 * 描述:重写DefaultDataUtility中的getDataValue方法
//	 * 对编码增加清除、动态搜索功能
//	 * @return
//	 * @throws WTException
//	 * @author wq.Ran
//	 * @Date 2019年8月15日 下午3:51:22
//	 * @version 1.0.0
//	 */
//	@Override
//	public Object getDataValue(String paramStr, Object paramObject, ModelContext paramModelContext) throws WTException {
//		Object localObject = super.getDataValue(paramStr, paramObject, paramModelContext);
//		ComponentMode componentMode = paramModelContext.getDescriptorMode();
//		String wtPartTypeName = "WCTYPE|wt.part.WTPart|com.sxqc.WTPartK";
//		/**
//		 * 判断当前页面的视图类型，如果是浏览视图则返回属性值
//		 */
//		if (ComponentMode.VIEW.equals(componentMode)) {
//			if (paramObject instanceof WTPart) {
//				WTPart wtPart = (WTPart) paramObject;
//				return wtPart.getNumber();
//			}
//			/**如果对象类型为属性控件类型定义，则进行控件的业务逻辑处理**/
//		} else if (localObject instanceof AttributeInputCompositeComponent) {
//			AttributeInputCompositeComponent inputCompositeComponent = (AttributeInputCompositeComponent) localObject;
//			AttributeInputComponent inputComponent = inputCompositeComponent.getValueInputComponent();
//			if (ComponentMode.CREATE.equals(componentMode)) {
//				GUIComponentArray guiArray = new GUIComponentArray();
//				/**
//				 * 增加清除编码值的按钮
//				 */
//				IconComponent clearIconComponent = new IconComponent();
//				clearIconComponent.setSrc("netmarkets/images/clear_16x16.gif");
//				clearIconComponent.setTooltip("清除");
//				StringBuffer bufferJs = new StringBuffer();
//				String[] clearElements = new String[] {"number"};
//				for (String elementId : clearElements) {
//					bufferJs.append("var element = window.parent.document.getElementById('" + elementId + "');");
//					bufferJs.append("if(element != null) {element.value = ''};");
//					bufferJs.append("if(element != null) {element.readOnly = false};");
//				}
//				clearIconComponent.addJsAction("onClick", bufferJs.toString());
//				HashMap<Object,Object> maps = Maps.newHashMap();
//				maps.put("wtPartTypeName", wtPartTypeName);
//				/**
//				 * 增加模糊查询编号功能和控件
//				 */
//				SuggestTextBox suggestTextBom = StandarGuiComponentServiceImpl.newStandarGuiComponentServiceImpl()
//						.createSuggestTextBox(inputComponent.getId(), inputComponent.getName(),
//								inputComponent.getColumnName(), inputComponent.isRequired(),
//								inputComponent.isReadOnly(), inputComponent.isEditable(), inputComponent.isEnabled(), 1,
//								"standarNumberSuggestable", maps, 40, "");
//				guiArray.addGUIComponent(suggestTextBom);
//				guiArray.addGUIComponent(clearIconComponent);
//				return guiArray;
//			}
//		}
//		return localObject;
//	}
//
//}
