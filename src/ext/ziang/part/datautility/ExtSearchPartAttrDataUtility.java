package ext.ziang.part.datautility;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.AttributeDataUtilityHelper;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.GuiComponent;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.IconComponent;
import com.ptc.core.components.rendering.guicomponents.TextBox;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;
import com.ptc.netmarkets.util.beans.NmCommandBean;

import cn.hutool.core.util.StrUtil;
import wt.httpgw.URLFactory;
import wt.util.WTException;

/**
 * ext 搜索部件数据实用程序
 * 定制数据单元
 *
 * @author ander
 * @date 2024/03/06
 */
public class ExtSearchPartAttrDataUtility extends DefaultDataUtility {
    @Override
    public Object getDataValue(String componentId, Object obj, ModelContext modelContext) throws WTException {

        NmCommandBean nmCommandBean = modelContext.getNmCommandBean();
        HttpServletRequest request = nmCommandBean.getRequest();
        String parameter = request.getParameter("ContainerOid");
        ArrayList<GuiComponent> components = new ArrayList<>();
        if (StrUtil.isNotBlank(parameter)) {
            // 框
            TextBox textBox = new TextBox();
            textBox.setMaxLength(200);
            textBox.setWidth(50);
            textBox.setEnabled(true);
            textBox.setEditable(true);
            textBox.setReadOnly(true);
            textBox.setColumnName(AttributeDataUtilityHelper.getColumnName(componentId, obj, modelContext));
            textBox.setId(componentId);
            components.add(textBox);
            IconComponent icon = new IconComponent("netmarkets/images/search.gif");
            IconComponent iconClear = new IconComponent("netmarkets/images/clear_16x16.gif");

            icon.setName("CALL_QUOTATION_PICKER");
            icon.setId("CALL_QUOTATION_PICKER");

            iconClear.setName("CALL_QUOTATION_CLEAN");
            iconClear.setId("CALL_QUOTATION_CLEAN");

            URLFactory factory = new URLFactory();
            String host = factory.getBaseHREF();
            String jsScript = "javascript:window.open('" +
                    host +
                    "netmarkets/jsp/ext/soarwhale/part/materialAttr/searchPartMaterial.jsp?columnId=" +
                    componentId +
                    " ','','height=520,width=700,toolbar=no,menubar=no,location=no,status=no')";
            // 清空数据
            String url1Clear = "javascript:document.getElementById('" + componentId + "').value='';";

            icon.addJsAction("onClick", jsScript);
            iconClear.addJsAction("onClick", url1Clear);

            icon.setEditable(true);
            iconClear.setEditable(true);
            components.add(icon);
            components.add(iconClear);
            return new GUIComponentArray(components);
        } else {
            TextDisplayComponent textDisplayComponent = new TextDisplayComponent("");
            textDisplayComponent.setValue("测试");
            textDisplayComponent.setCheckXSS(false);
            components.add(textDisplayComponent);
            return new GUIComponentArray(components);
        }
    }
}
