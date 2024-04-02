package ext.ziang.part.process;

import java.util.List;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;

import wt.util.WTException;

/**
 * 一次复制 BOM 处理器
 *
 * @author anzhen
 * @date 2024/04/03
 */
public class OnceCopyBomProcessor extends DefaultObjectFormProcessor {
	@Override
	public FormResult preProcess(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		System.out.println("OnceCopyBomProcessor.preProcess");
		return super.preProcess(nmCommandBean, list);
	}

	@Override
	public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		System.out.println("OnceCopyBomProcessor.doOperation");
		return super.doOperation(nmCommandBean, list);
	}

	@Override
	public FormResult postProcess(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		System.out.println("OnceCopyBomProcessor.postProcess");
		return super.postProcess(nmCommandBean, list);
	}
}