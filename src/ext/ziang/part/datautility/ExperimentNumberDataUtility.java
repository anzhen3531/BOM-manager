//package ext.ziang.part.datautility;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.ptc.core.components.descriptor.ModelContext;
//import com.ptc.core.components.factory.dataUtilities.AttributeDataUtilityHelper;
//import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
//import com.ptc.core.components.rendering.GuiComponent;
//import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
//import com.ptc.core.components.rendering.guicomponents.TextBox;
//import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
//import com.ptc.netmarkets.util.beans.NmCommandBean;
//
//import cn.hutool.core.util.StrUtil;
//import ext.ziang.common.util.ToolUtils;
//import wt.fc.Persistable;
//import wt.part.WTPart;
//import wt.util.WTException;
//
///**
// * 实验原材料数据单元
// *
// * @author anzhen
// * @date 2024/02/20
// */
//public class ExperimentNumberDataUtility extends DefaultDataUtility {
//	/**
//	 * @param componentId
//	 *            表格列id
//	 * @param obj
//	 *            当前的表格对象
//	 * @param modelContext
//	 *            上下文
//	 * @return
//	 * @throws WTException
//	 */
//	@Override
//	public Object getDataValue(String componentId, Object obj, ModelContext modelContext) throws WTException {
//
//		GUIComponentArray guicomponentarray = new GUIComponentArray();
//		System.out.println("====================》进入getDataValue");
//		NmCommandBean nmCommandBean = modelContext.getNmCommandBean();
//		HttpServletRequest request = nmCommandBean.getRequest();
//		HashMap text = nmCommandBean.getText();
//		System.out.println("text = " + text);
//
//		String partOid = request.getParameter("partOid");// 部件的oid
//		ArrayList<GuiComponent> components = new ArrayList<>(1);
//		TextBox textBox = new TextBox();
//		textBox.setMaxLength(200);
//		textBox.setWidth(50);
//		textBox.setEnabled(true);
//		textBox.setEditable(true);
//		textBox.setReadOnly(false);
//		textBox.setRequired(true);
//		textBox.setColumnName(AttributeDataUtilityHelper.getColumnName(componentId, obj, modelContext));
//		textBox.setId(componentId);
//		components.add(textBox);
//		if (StrUtil.isEmpty(partOid)) {
//			return new GUIComponentArray(components);
//		}
//		Persistable objectByOid = ToolUtils.getObjectByOid(partOid);
//		WTPart part = (WTPart) objectByOid;
//		if (part == null) {
//			return new GUIComponentArray(components);
//		}
//		// 获取总成料号 并填充
//		TextDisplayComponent textDisplayComponent = new TextDisplayComponent("");
//		// 编写表格
//		textDisplayComponent.setValue(part.getNumber());
//		textDisplayComponent.setCheckXSS(false);
//		request.setAttribute("partNumber", part.getNumber());
//		guicomponentarray.addGUIComponent(textDisplayComponent);
//		return guicomponentarray;
//	}
//}
