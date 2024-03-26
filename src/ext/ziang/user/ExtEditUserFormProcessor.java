package ext.ziang.user;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.principal.user.processor.EditUserFormProcessor;
import wt.util.WTException;

import java.util.List;

public class ExtEditUserFormProcessor extends EditUserFormProcessor {

    @Override
    public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        return super.doOperation(nmCommandBean, list);
    }
}
