package ext.ziang.change.datautility;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;

import ext.ziang.change.entity.CorrectBomEntity;

/**
 * 修改物料清单数据实用程序
 *
 * @author anzhen
 * @date 2024/04/20
 */
public class CorrectBomDataUtility extends DefaultDataUtility {
	/**
	 * 获取数据类型
	 *
	 * @param componentId
	 *            列id
	 * @param obj
	 *            当前对象
	 * @param modelContext
	 *            上下文
	 * @return {@link Object}
	 */
	@Override
	public Object getDataValue(String componentId, Object obj, ModelContext modelContext) {
		GUIComponentArray guicomponentarray = new GUIComponentArray();
		TextDisplayComponent textDisplayComponent = new TextDisplayComponent("");
		if (obj instanceof CorrectBomEntity) {
			if ("number".equals(componentId)) {
				textDisplayComponent.setCheckXSS(false);
				if (((CorrectBomEntity) obj).getIsSubstitute().equals("true")) {
					textDisplayComponent
							.setValue("<span style='color:red'>" + ((CorrectBomEntity) obj).getNumber() + "</span>");
				} else {
					textDisplayComponent.setValue(((CorrectBomEntity) obj).getNumber());
				}
			} else if ("name".equals(componentId)) {
				textDisplayComponent.setValue(((CorrectBomEntity) obj).getName());
			} else if ("description".equals(componentId)) {
				textDisplayComponent.setValue(((CorrectBomEntity) obj).getDescription());
			} else if ("isSubstitute".equals(componentId)) {
				textDisplayComponent.setValue(((CorrectBomEntity) obj).getIsSubstitute());
			} else if ("substitutePart".equals(componentId)) {
				textDisplayComponent.setValue(((CorrectBomEntity) obj).getSubstitutePart());
			} else if ("isSelect".equals(componentId)) {
				textDisplayComponent.setValue("" + ((CorrectBomEntity) obj).isSelect());
			}
		}
		guicomponentarray.addGUIComponent(textDisplayComponent);
		return guicomponentarray;
	}
}