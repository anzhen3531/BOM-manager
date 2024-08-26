package ext.ziang.part.datautility;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;

import ext.ziang.part.builder.DerivedPartBuilder;
import ext.ziang.part.model.derive.PartDeriveLink;

/**
 * 派生数据实用程序
 *
 * @author anzhen
 * @date 2024/08/25
 */
public class DerivedDataUtility extends DefaultDataUtility {
    @Override
    public Object getDataValue(String column, Object currentObj, ModelContext modelContext) {
        System.out.println("column = " + column + ", currentObj = " + currentObj + ", modelContext = " + modelContext);
        TextDisplayComponent textDisplayComponent = new TextDisplayComponent(column);
        // 设置衍生料号字段和衍生出的料号字段
        // 设置状态等信息
        if (currentObj instanceof PartDeriveLink) {
            PartDeriveLink link = (PartDeriveLink)currentObj;
            System.out.println("link = " + link);
            switch (column) {
                case DerivedPartBuilder.DERIVED_FOR_NAME:
                    textDisplayComponent.setValue(link.getDeriveFor().getName());
                    break;
                case DerivedPartBuilder.DERIVED_FOR_NUMBER:
                    textDisplayComponent.setValue(link.getDeriveFor().getNumber());
                    break;
                case DerivedPartBuilder.DERIVES_NUMBER:
                    textDisplayComponent.setValue(link.getDerives().getNumber());
                    break;
                case DerivedPartBuilder.DERIVES_NAME:
                    textDisplayComponent.setValue(link.getDerives().getName());
                    break;
                case DerivedPartBuilder.STATE:
                    textDisplayComponent.setValue(link.getState());
                    break;
            }
        }
        GUIComponentArray array = new GUIComponentArray();
        array.addGUIComponent(textDisplayComponent);
        return array;
    }
}
