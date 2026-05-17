package ext.ziang.part.process;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormProcessingStatus;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.part.forms.CreatePartAndCADDocFormProcessor;
import ext.ziang.common.constants.AttributeConstants;
import ext.ziang.common.helper.query.CommonMethodHelper;
import ext.ziang.common.util.IBAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartMasterIdentity;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
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
    public static final Logger logger = LoggerFactory.getLogger(ExtCreatePartAndCADDocFormProcessor.class);

    @Override
    public FormResult postProcess(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        logger.debug("into postProcess {} ", nmCommandBean);
        FormResult formResult = super.postProcess(nmCommandBean, list);
        return formResult;
    }

    /**
     * 设置结果下一个操作
     *
     * @param formResult    表单结果
     * @param nmCommandBean nm 命令 bean
     * @param list          列表
     * @return {@link FormResult }
     * @throws WTException WTException
     */
    @Override
    public FormResult setResultNextAction(FormResult formResult, NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        if (!formResult.getStatus().equals(FormProcessingStatus.SUCCESS)) {
            return formResult;
        } else {
            formResult = super.setResultNextAction(formResult, nmCommandBean, list);
            logger.debug("list = " + list);
            Object object = list.get(0).getObject();
            if (object instanceof WTPart) {
                WTPart part = (WTPart) object;
                String classify;
                try {
                    classify = IBAUtils.getIBAValue(part, AttributeConstants.CLASSIFY.getInnerName());
                    logger.error("classify is {}", classify);
                    CommonMethodHelper.updateNameAndNumberByObject(part.getMaster(), classify, part.getNumber(), part.getOrganization());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new WTException(e);
                }
            }
            return formResult;
        }
    }

    public static boolean findPartByName(String name) throws WTException {
        QuerySpec querySpec = new QuerySpec(WTPartMaster.class);
        // select * from WTPartMaster;
        querySpec.appendWhere(
                new SearchCondition(WTPartMaster.class, WTPartMaster.NAME, SearchCondition.EQUAL, name),
                new int[]{0});
        // select * from WTPartMaster where name = ''
        QueryResult queryResult = PersistenceHelper.manager.find(querySpec);
        return queryResult.hasMoreElements();
    }
}
