package ext.ziang.report.datautility;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;

import ext.ziang.report.builder.ReportFormBuilder;
import ext.ziang.report.model.ReportFormConfig;
import wt.util.WTException;

public class ReportFormDataUtility extends DefaultDataUtility {
    @Override
    public Object getDataValue(String column, Object object, ModelContext modelContext) throws WTException {
        TextDisplayComponent textDisplayComponent = new TextDisplayComponent(column);
        if (object instanceof ReportFormConfig) {
            ReportFormConfig formConfig = (ReportFormConfig)object;
            switch (column) {
                case ReportFormBuilder.ID:
                    textDisplayComponent
                        .setValue(String.valueOf(formConfig.getPersistInfo().getObjectIdentifier().getId()));
                    break;
                case ReportFormBuilder.STATE:
                    Integer state = formConfig.getState();
                    String value = state == 0 ? "开启" : "关闭";
                    textDisplayComponent.setValue(value);
                    break;
            }
        }
        GUIComponentArray array = new GUIComponentArray();
        array.addGUIComponent(textDisplayComponent);
        return array;
    }
}
