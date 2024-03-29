package ext.ziang.doc;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.doc.forms.CreateDocFormProcessor;
import wt.doc.WTDocument;
import wt.util.WTException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


/**
 * ext create doc 表单处理器
 *
 * @author anzhen
 * @date 2024/03/01
 */
public class ExtCreateDocFormProcessor extends CreateDocFormProcessor {

    /**
     * 后处理
     *
     * @param nmCommandBean nm 命令 bean
     * @param list          列表
     * @return {@link FormResult}
     * @throws WTException WT异常
     */
    @Override
    public FormResult postProcess(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        FormResult rs = super.postProcess(nmCommandBean, list);
        ObjectBean objectbean = list.get(0);
        HashMap text = nmCommandBean.getText();
        System.out.println("text = " + text);
        HashMap textArea = nmCommandBean.getTextArea();
        System.out.println("textArea = " + textArea);
        HttpServletRequest request = nmCommandBean.getRequest();
        String partOid = request.getParameter("partOid");//部件的oid
        System.out.println("partOid = " + partOid);
        System.out.println("request.getAttribute(\"partNumber\") = " + request.getAttribute("partNumber"));
        Object obj = objectbean.getObject();
        if (obj instanceof WTDocument) {
        }
        return rs;
    }
}
