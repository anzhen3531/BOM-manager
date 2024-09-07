package ext.ziang.report.process;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.ziang.report.model.ReportFormConfig;
import wt.fc.PersistenceHelper;
import wt.session.SessionHelper;
import wt.util.WTException;

import java.util.HashMap;
import java.util.List;

public class CreateOrUpdateReportFromConfigProcessor extends DefaultObjectFormProcessor {
    public static final String CREATE_VIEW = "CREATE";
    public static final String EDIT_VIEW = "EDIT";

    @Override
    public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        // 获取对于的数据保存即可
        String view = nmCommandBean.getTextParameter("View");
        HashMap textArea = nmCommandBean.getTextArea();
        HashMap text = nmCommandBean.getText();
        switch (view) {
            case CREATE_VIEW:
                try {
                    String name = SessionHelper.getPrincipal().getName();
                    ReportFormConfig reportFormConfig = ReportFormConfig.newReportFormConfig();
                    reportFormConfig.setState(1);
                    reportFormConfig.setCreator(name);
                    reportFormConfig.setModifier(name);
                    reportFormConfig.setContent((String)textArea.get(""));
                    reportFormConfig.setDescription((String)text.get(""));
                    PersistenceHelper.manager.save(reportFormConfig);
                } catch (Exception e) {

                }

                break;
            case EDIT_VIEW:
                break;
        }
        return super.doOperation(nmCommandBean, list);

    }
}
