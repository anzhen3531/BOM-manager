package ext.ziang.report.datautility;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;

import ext.ziang.common.constants.StateEnum;
import ext.ziang.report.builder.ReportFormBuilder;
import ext.ziang.report.model.ReportFormConfig;
import wt.util.WTException;

import java.util.Objects;

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
                    String value = Objects.equals(state, StateEnum.START.getValue()) ? StateEnum.START.getName()
                        : StateEnum.STOP.getName();
                    textDisplayComponent.setValue(value);
                    break;
            }
        }
        GUIComponentArray array = new GUIComponentArray();
        array.addGUIComponent(textDisplayComponent);
        return array;
    }
}
