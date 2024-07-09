package ext.ziang.part.datautility;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import ext.ziang.common.helper.CommonGuiComponentHelper;
import wt.util.WTException;

import java.util.ArrayList;

/**
 * 属性数据实用程序
 *
 * @author anzhen
 * @date 2024/07/06
 */
public class AttributeDataUtility extends DefaultDataUtility {
    @Override
    public Object getDataValue(String column, Object object, ModelContext modelContext) throws WTException {
        ArrayList<String> list = new ArrayList<>();
        list.add("120");
        list.add("85");
        list.add("35");
        list.add("10");
        list.add("68");
        // 主炮
        if (column.equals("Caliber")) {
            // 采用炮弹
            String jsAction = "test(" + column + ")";
            return CommonGuiComponentHelper.newComboBox(column, jsAction, list, list);
        } else if (column.equals("")) {
        }
        return null;
    }
}
