package ext.ziang.user.process;

import java.util.List;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.principal.user.processor.CreateUserFormProcessor;

import ext.ziang.user.helper.UserExtendedInformationHelper;
import wt.org.WTUser;
import wt.util.WTException;

/**
 * ext 创建用户表单处理器
 *
 * @author anzhen
 * @date 2024/03/26
 */
public class ExtCreateUserFormProcessor extends CreateUserFormProcessor {
	/**
	 * 执行操作
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
	public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		FormResult formResult = super.doOperation(nmCommandBean, list);
		ObjectBean objectBean = (ObjectBean) list.get(0);
		WTUser user = (WTUser) objectBean.getObject();
		String password = (String) nmCommandBean.getText().get("password");
		String name = user.getAuthenticationName();
		UserExtendedInformationHelper.createAndUpdateUserExtendedInformation(name, password);
		return formResult;
	}
}
