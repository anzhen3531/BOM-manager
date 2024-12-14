package ext.ziang.option.helper;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.option.choicecomponent.SelectableChoiceBean;
import com.ptc.windchill.option.choicecomponent.builders.ChoiceDataFactory;

/**
 * 自定义选择助手类
 */
public class CustomOptionHelper {
    private static final Logger logger = LoggerFactory.getLogger(CustomOptionHelper.class);
    private static final String PARAMS = "panzerVersion";
    private static final String UPDATE_USER_SELECTIONS = "updateUserSelections";

    /**
     * 构建默认选择的JSON
     *
     * @param commandBean 命令 Bean
     * @return JSON对象
     * @throws Exception 例外
     */
    public static JSONObject customDefaultSelections(NmCommandBean commandBean) throws Exception {
        logger.debug("collecting initially selected choices.");
        JSONObject result = new JSONObject();
        List<SelectableChoiceBean> choices = ChoiceDataFactory.newInstance(commandBean).customInitSelectChoice(PARAMS);
        for (SelectableChoiceBean selectableChoiceBean : choices) {
            result.append(UPDATE_USER_SELECTIONS, selectableChoiceBean.toJSONObject());
        }
        return result;
    }
}
