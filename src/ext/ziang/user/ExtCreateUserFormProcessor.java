package ext.ziang.user;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.principal.user.processor.CreateUserFormProcessor;
import wt.util.WTException;

import java.util.List;

/**
 * ext 创建用户表单处理器
 *
 * @author anzhen
 * @date 2024/03/26
 */
public class ExtCreateUserFormProcessor extends CreateUserFormProcessor {
    @Override
    public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        // 获取页面中的密码保存即可
        // 之后通过相关的密文进行验证
        // 调用ldap服务器验证
		return super.doOperation(nmCommandBean, list);
    }
}
