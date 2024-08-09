//package ext.ziang.part.datautility;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import com.ptc.core.components.descriptor.ModelContext;
//import com.ptc.core.components.factory.dataUtilities.AttributeDataUtilityHelper;
//import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
//import com.ptc.core.components.rendering.GuiComponent;
//import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
//import com.ptc.core.components.rendering.guicomponents.IconComponent;
//import com.ptc.core.components.rendering.guicomponents.TextBox;
//import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
//import com.ptc.netmarkets.util.beans.NmCommandBean;
//
//import cn.hutool.core.util.StrUtil;
//import wt.httpgw.URLFactory;
//import wt.util.WTException;
//
///**
// * ext 搜索部件数据实用程序
// * 定制数据单元
// *
// * @author anzhen
// * @date 2024/03/06
// */
//public class ExtSearchPartAttrDataUtility extends DefaultDataUtility {
//	@Override
//	public Object getDataValue(String componentId, Object obj, ModelContext modelContext) throws WTException {
//		NmCommandBean nmCommandBean = modelContext.getNmCommandBean();
//		HttpServletRequest request = nmCommandBean.getRequest();
//		String parameter = request.getParameter("ContainerOid");
//		ArrayList<GuiComponent> components = new ArrayList<>();
//		if (StrUtil.isNotBlank(parameter)) {
//			TextBox textBox = new TextBox();
//			textBox.setMaxLength(200);
//			textBox.setWidth(50);
//			textBox.setEnabled(true);
//			textBox.setEditable(true);
//			textBox.setReadOnly(true);
//			textBox.setColumnName(AttributeDataUtilityHelper.getColumnName(componentId, obj, modelContext));
//			textBox.setId(componentId);
//			components.add(textBox);
//			IconComponent icon = new IconComponent("netmarkets/images/search.gif");
//			IconComponent iconClear = new IconComponent("netmarkets/images/clear_16x16.gif");
//			icon.setName("CALL_QUOTATION_PICKER");
//			icon.setId("CALL_QUOTATION_PICKER");
//			iconClear.setName("CALL_QUOTATION_CLEAN");
//			iconClear.setId("CALL_QUOTATION_CLEAN");
//			URLFactory factory = new URLFactory();
//			String host = factory.getBaseHREF();
//			String jsScript = "javascript:window.open('" +
//					host +
//					"netmarkets/jsp/ext/soarwhale/part/materialAttr/searchPartMaterial.jsp?columnId=" +
//					componentId +
//					" ','','height=520,width=700,toolbar=no,menubar=no,location=no,status=no')";
//			// 清空数据
//			String url1Clear = "javascript:document.getElementById('" + componentId + "').value='';";
//			icon.addJsAction("onClick", jsScript);
//			iconClear.addJsAction("onClick", url1Clear);
//			icon.setEditable(true);
//			iconClear.setEditable(true);
//			components.add(icon);
//			components.add(iconClear);
//			return new GUIComponentArray(components);
//		} else {
//			// 判断是否是相关的信息
//			TextDisplayComponent textDisplayComponent = new TextDisplayComponent("");
//			textDisplayComponent.setValue("测试");
//			textDisplayComponent.setCheckXSS(false);
//			components.add(textDisplayComponent);
//			return new GUIComponentArray(components);
//		}
//	}
//
//	/**
//	 * 获取实验申请单内容
//	 *
//	 * @param excelFilePath
//	 */
//	public static List<String> getMaterialFormContent(String excelFilePath) {
//		List<String> result = new ArrayList<>();
//		try (FileInputStream inputStream = new FileInputStream(excelFilePath);
//				Workbook workbook = new XSSFWorkbook(inputStream)) {
//			Sheet sheet = workbook.getSheetAt(0);
//			// 处理下
//			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
//				Row row = sheet.getRow(i);
//				Cell cell = row.getCell(4);
//				String stringCellValue = cell.getStringCellValue();
//				if (StrUtil.isNotBlank(stringCellValue)) {
//					result.add(stringCellValue);
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//}
