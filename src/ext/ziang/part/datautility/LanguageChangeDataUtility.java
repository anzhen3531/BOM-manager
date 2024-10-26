package ext.ziang.part.datautility;

import java.util.List;
import java.util.Locale;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.AttributeDisplayCompositeComponent;
import com.ptc.core.components.rendering.guicomponents.GUIComponentArray;
import com.ptc.core.components.rendering.guicomponents.TextDisplayComponent;

import ext.ziang.common.util.IbaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wt.iba.value.IBAHolder;
import wt.session.SessionHelper;
import wt.util.WTException;

/**
 * 文本字符 英文描述自定义展示
 */
public class LanguageChangeDataUtility extends DefaultDataUtility {
    public static final Logger log = LoggerFactory.getLogger(LanguageChangeDataUtility.class);
    @Override
    public Object getDataValue(String column, Object object, ModelContext modelContext) throws WTException {
        Object dataValue = super.getDataValue(column, object, modelContext);
        if (dataValue instanceof AttributeDisplayCompositeComponent) {
            GUIComponentArray array = new GUIComponentArray();
            // 修改展示
            TextDisplayComponent displayComponent;
            List<String> ibaValueList = IbaUtil.getStringIBAValueList((IBAHolder) object, column, true);
            log.info("ibaValueList {}", ibaValueList);
            log.info("SessionHelper.getLocale() {}", SessionHelper.getLocale());
            if (SessionHelper.getLocale().equals(Locale.CHINA)) {
                displayComponent = new TextDisplayComponent("中文描述");
                displayComponent.setValue(ibaValueList.get(0));
            } else {
                displayComponent = new TextDisplayComponent("english description");
                displayComponent.setValue(ibaValueList.get(1));
            }
            displayComponent.setColumnName(column);
            array.addGUIComponent(displayComponent);
            return array;
        }
        return dataValue;
    }
}
