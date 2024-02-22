package ext.ziang.common.util;

import com.ptc.core.components.forms.FormProcessingStatus;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import wt.session.SessionHelper;
import wt.util.WTException;

public class CustomCommonUtil {


    /**
     * 处理程序进程消息
     *
     * @param result    结果
     * @param msg       信息
     * @param isSuccess
     * @throws WTException WTException
     */
    private void handlerProcessMessage(FormResult result, String msg, boolean isSuccess) throws WTException {
        if (!isSuccess) {
            result.addFeedbackMessage(
                    new FeedbackMessage(FeedbackType.FAILURE, SessionHelper.getLocale(), msg, null, null, null));
            result.setStatus(FormProcessingStatus.FAILURE);
        } else {
            result.addFeedbackMessage(
                    new FeedbackMessage(FeedbackType.SUCCESS, SessionHelper.getLocale(), msg, null, null, null));
            result.setStatus(FormProcessingStatus.SUCCESS);
        }
    }
}
