package ext.ziang.user.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.principal.user.processor.EditUserFormProcessor;

import ext.ziang.user.helper.UserExtendedInformationHelper;
import wt.org.WTUser;
import wt.util.WTException;

/**
 * ext edit 用户表单处理器
 *
 * @author anzhen
 * @date 2024/04/01
 */
public class ExtEditUserFormProcessor extends EditUserFormProcessor {
	private static final Logger logger = LoggerFactory.getLogger(ExtEditUserFormProcessor.class);


	@Override
	public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		FormResult formResult = super.doOperation(nmCommandBean, list);
		ObjectBean objectBean = (ObjectBean) list.get(0);
		WTUser user = (WTUser) objectBean.getObject();
		String authenticationName = user.getAuthenticationName();
		logger.debug("{}", "authenticationName = " + authenticationName);
		String password = (String) nmCommandBean.getText().get("password");
		String alternateUserName = (String) nmCommandBean.getText().get("alternateUserName1");
		if (StrUtil.isNotBlank(password)){
			UserExtendedInformationHelper.createAndUpdateUserExtendedInformation(alternateUserName, password);
		}
		return formResult;
	}
}
