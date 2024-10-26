package ext.ziang.part.process;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormProcessingStatus;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.part.forms.CreatePartAndCADDocFormProcessor;

import ext.ziang.common.constants.AttributeConstants;
import ext.ziang.common.helper.query.CommonMethodHelper;
import ext.ziang.common.util.IBAUtils;
import ext.ziang.part.model.derive.PartDeriveLink;
import wt.fc.PersistenceHelper;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * 处理衍生物料
 * <p>
 * <p>
 * ext.ziang.part.process.ExtCreatePartAndCADDocFormProcessor
 *
 * @author anzhen
 * @date 2024/07/07
 */
public class DerivedPartProcessor extends CreatePartAndCADDocFormProcessor {
    public static final Logger logger = LoggerFactory.getLogger(DerivedPartProcessor.class);

    @Override
    public FormResult postProcess(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        logger.debug("into postProcess {} ", nmCommandBean);
        // 处理衍生物料关联关系
        NmOid primaryOid = nmCommandBean.getPrimaryOid();
        Object object = list.get(0).getObject();
        logger.debug("object {}", object);
        Object refObject = primaryOid.getRefObject();
        logger.debug("refObject {}", refObject);
        if (object instanceof WTPart && refObject instanceof WTPart) {
            WTPart derivePart = (WTPart)object;
            WTPart deriveForPart = (WTPart)refObject;
            try {
                PartDeriveLink partDeriveLink = PartDeriveLink.newPartDeriveLink();
                partDeriveLink.setDerives(derivePart.getMaster());
                partDeriveLink.setDeriveFor(deriveForPart.getMaster());
                partDeriveLink.setState("ENABLE");
                PersistenceHelper.manager.save(partDeriveLink);
            } catch (WTPropertyVetoException e) {
                logger.error("DerivedPartProcessor postProcess create partDeriveLink", e);
            }
        }
        return super.postProcess(nmCommandBean, list);
    }

    /**
     * 设置结果下一个操作
     *
     * @param formResult 表单结果
     * @param nmCommandBean nm 命令 bean
     * @param list 列表
     * @return {@link FormResult }
     * @throws WTException WTException
     */
    @Override
    public FormResult setResultNextAction(FormResult formResult, NmCommandBean nmCommandBean, List<ObjectBean> list)
        throws WTException {
        if (!formResult.getStatus().equals(FormProcessingStatus.SUCCESS)) {
            return formResult;
        } else {
            formResult = super.setResultNextAction(formResult, nmCommandBean, list);
            logger.debug("list = " + list);
            Object object = list.get(0).getObject();
            if (object instanceof WTPart) {
                WTPart part = (WTPart)object;
                String classify;
                try {
                    classify = IBAUtils.getIBAValue(part, AttributeConstants.CLASSIFY.getInnerName());
                    logger.error("classify is {}", classify);
                    CommonMethodHelper.updateNameAndNumberByObject(part.getMaster(), classify, part.getNumber(),
                        part.getOrganization());
                    throw new WTException("123");
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new WTException(e);
                }
            }
            return formResult;
        }
    }
}
