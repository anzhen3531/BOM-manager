package ext.ziang.part.process;

import java.util.ArrayList;
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
	/**
	 * 预处理
	 *
	 * @param nmCommandBean nm 命令 bean
	 * @param list          列表
	 * @return {@link FormResult}
	 * @throws WTException WT异常
	 */
	@Override
	public FormResult preProcess(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		System.out.println("OnceCopyBomProcessor.preProcess");
		ArrayList selected = nmCommandBean.getSelected();
		System.out.println("selected = " + selected);
		return super.preProcess(nmCommandBean, list);
	}

	/**
	 * 执行操作
	 *
	 * @param nmCommandBean nm 命令 bean
	 * @param list          列表
	 * @return {@link FormResult}
	 * @throws WTException WT异常
	 */
	@Override
	public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		System.out.println("OnceCopyBomProcessor.doOperation");
		ArrayList selected = nmCommandBean.getSelected();
		System.out.println("selected = " + selected);
		return super.doOperation(nmCommandBean, list);
	}

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
		System.out.println("OnceCopyBomProcessor.postProcess");
		ArrayList selected = nmCommandBean.getSelected();
		System.out.println("selected = " + selected);
		return super.postProcess(nmCommandBean, list);
	}
}