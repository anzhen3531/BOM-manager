package ext.ziang.doc.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(ExtCreateDocFormProcessor.class);


	/**
	 * 后处理
	 *
	 * @param nmCommandBean
	 *            nm 命令 bean
	 * @param list
	 *            列表
	 * @return {@link FormResult}
	 * @throws WTException
	 *             WT异常
	 */
	@Override
	public FormResult postProcess(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		FormResult rs = super.postProcess(nmCommandBean, list);
		ObjectBean objectbean = list.get(0);
		HashMap text = nmCommandBean.getText();
		logger.debug("{}", "text = " + text);
		HashMap textArea = nmCommandBean.getTextArea();
		logger.debug("{}", "textArea = " + textArea);
		HttpServletRequest request = nmCommandBean.getRequest();
		String partOid = request.getParameter("partOid");// 部件的oid
		logger.debug("{}", "partOid = " + partOid);
		logger.debug("{}", "request.getAttribute(\"partNumber\") = " + request.getAttribute("partNumber"));
		Object obj = objectbean.getObject();
		if (obj instanceof WTDocument) {
			// 获取主内容
			//
		}
		return rs;
	}
}
