package ext.ziang.report.process;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.ziang.common.constants.StateEnum;
import ext.ziang.report.builder.ReportFormBuilder;
import ext.ziang.report.model.ReportFormConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wt.fc.PersistenceHelper;
import wt.session.SessionHelper;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateOrUpdateReportFromConfigProcessor extends DefaultObjectFormProcessor {
    public static final Logger logger = LoggerFactory.getLogger(CreateOrUpdateReportFromConfigProcessor.class);
    public static final String CREATE_VIEW = "CREATE";
    public static final String EDIT_VIEW = "EDIT";

    @Override
    public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        // 获取对于的数据保存即可
        String view = nmCommandBean.getTextParameter("View");
        HashMap textArea = nmCommandBean.getTextArea();
        HashMap text = nmCommandBean.getText();
        FormResult formResult = super.doOperation(nmCommandBean, list);
        String description = (String)text.get(ReportFormBuilder.DESCRIPTION);
        String content = (String)textArea.get(ReportFormBuilder.CONTENT);
        ReportFormConfig reportFormConfig;
        switch (view) {
            case CREATE_VIEW:
                reportFormConfig = ReportFormConfig.newReportFormConfig();
                saveConfig(reportFormConfig, StateEnum.START.getValue(), content, description);
                break;
            case EDIT_VIEW:
                ArrayList selectedOidForPopup = nmCommandBean.getSelectedOidForPopup();
                NmOid nmOid = (NmOid)selectedOidForPopup.get(0);
                reportFormConfig = (ReportFormConfig)nmOid.getRefObject();
                saveConfig(reportFormConfig, StateEnum.START.getValue(), content, description);
                break;
        }
        return formResult;

    }

    /**
     * 存储配置
     * 
     * @param config 配置
     * @param state 状态
     * @param content 内容
     * @param description 描述
     * @throws WTException
     */
    public static void saveConfig(ReportFormConfig config, Integer state, String content, String description)
        throws WTException {
        try {
            String name = SessionHelper.getPrincipal().getName();
            config.setState(state);
            config.setCreator(name);
            config.setModifier(name);
            config.setContent(content);
            config.setDescription(description);
            logger.info("reportFormConfig {}", config);
            PersistenceHelper.manager.save(config);
        } catch (Exception e) {
            logger.error("CreateOrUpdateReportFromConfigProcessor save ReportFormConfig Exception ", e);
            throw new WTException(e);
        }
    }
}
