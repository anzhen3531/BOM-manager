package ext.ziang.part.process;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.tml.utils.IBAUtils;
import com.ptc.windchill.enterprise.part.forms.CreatePartAndCADDocFormProcessor;
import ext.ziang.common.constants.AttributeConstants;
import ext.ziang.common.helper.query.CommonQueryHelper;
import org.apache.log4j.Logger;
import wt.part.WTPart;
import wt.util.WTException;

import java.util.List;

/**
 * ext create 零件和 CAD Doc 表单处理器
 * ext.ziang.part.process.ExtCreatePartAndCADDocFormProcessor
 *
 * @author anzhen
 * @date 2024/07/07
 */
public class ExtCreatePartAndCADDocFormProcessor extends CreatePartAndCADDocFormProcessor {
    Logger logger = Logger.getLogger(ExtCreatePartAndCADDocFormProcessor.class);

    @Override
    public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        FormResult formResult = super.doOperation(nmCommandBean, list);
        logger.debug("list = " + list);
        Object object = list.get(0).getObject();
        if (object instanceof WTPart) {
            WTPart part = (WTPart) object;
            String classify = IBAUtils.getStringIBAValue(part, AttributeConstants.CLASSIFY.getInnerName());
            CommonQueryHelper.updateNameAndNumberByObject(part.getMaster(), classify, part.getNumber(), part.getOrganizationUniqueIdentifier());
        }
        return formResult;
    }
}
