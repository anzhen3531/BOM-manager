package ext.ziang.part.datautility;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;

import ext.ziang.part.builder.PartDerivedBuilder;
import ext.ziang.part.model.derive.PartDeriveLink;

/**
 * 派生数据实用程序
 *
 * @author anzhen
 * @date 2024/08/25
 */
public class PartDerivedDataUtility extends DefaultDataUtility {
    @Override
    public Object getDataValue(String column, Object currentObj, ModelContext modelContext) {
        TextDisplayComponent textDisplayComponent = new TextDisplayComponent(column);
        // 设置衍生料号字段和衍生出的料号字段
        // 设置状态等信息
        if (currentObj instanceof PartDeriveLink) {
            PartDeriveLink link = (PartDeriveLink)currentObj;
            switch (column) {
                case PartDerivedBuilder.DERIVED_FOR_NAME:
                    textDisplayComponent.setValue(link.getDeriveFor().getName());
                    break;
                case PartDerivedBuilder.DERIVED_FOR_NUMBER:
                    textDisplayComponent.setValue(link.getDeriveFor().getNumber());
                    break;
                case PartDerivedBuilder.DERIVES_NUMBER:
                    textDisplayComponent.setValue(link.getDerives().getNumber());
                    break;
                case PartDerivedBuilder.DERIVES_NAME:
                    textDisplayComponent.setValue(link.getDerives().getName());
                    break;
                case PartDerivedBuilder.STATE:
                    textDisplayComponent.setValue(link.getState());
                    break;
            }
        }
        GUIComponentArray array = new GUIComponentArray();
        array.addGUIComponent(textDisplayComponent);
        return array;
    }
}
