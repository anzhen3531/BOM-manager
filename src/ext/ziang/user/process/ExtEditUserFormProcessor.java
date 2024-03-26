package ext.ziang.user.process;

import java.util.List;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.principal.user.processor.EditUserFormProcessor;

import ext.ziang.user.helper.UserExtendedInformationHelper;
import wt.org.WTUser;
import wt.util.WTException;

public class ExtEditUserFormProcessor extends EditUserFormProcessor {

	@Override
	public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
		FormResult formResult = super.doOperation(nmCommandBean, list);
		ObjectBean objectBean = (ObjectBean) list.get(0);
		WTUser user = (WTUser) objectBean.getObject();
		String password = (String) nmCommandBean.getText().get("password");
		String name = user.getName();
		UserExtendedInformationHelper.createAndUpdateUserExtendedInformation(name, password);
		return formResult;
	}
}
