package ext.ziang.user.helper;

import ext.ziang.user.entity.UserExtendedInformation;
import ext.ziang.user.service.UserExtendedInformationService;
import ext.ziang.user.service.UserExtendedInformationServiceImpl;

/**
 * 用户扩展信息帮助程序
 *
 * @author anzhen
 * @date 2024/03/27
 */
public class UserExtendedInformationHelper {
	/**
	 * 创建和更新用户扩展信息
	 *
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	public static void createAndUpdateUserExtendedInformation(String username, String password) {
		UserExtendedInformationService service = new UserExtendedInformationServiceImpl();
		UserExtendedInformation userInfo = service.findUserExtendedInformationByUserName(username);
		if (userInfo == null) {
			userInfo = new UserExtendedInformation();
			userInfo.setUsername(username);
			userInfo.setPassword(password);
			service.createUserExtendedInformation(userInfo);
		} else {
			userInfo.setPassword(password);
			service.updateUserExtendedInformation(userInfo);
		}
	}
}
