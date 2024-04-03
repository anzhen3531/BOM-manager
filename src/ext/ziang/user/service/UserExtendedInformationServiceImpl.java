package ext.ziang.user.service;

import ext.ziang.user.dao.UserExtendedInformationDao;
import ext.ziang.user.entity.UserExtendedInformation;

/**
 * 用户扩展信息服务 impl
 *
 * @author anzhen
 * @date 2024/03/26
 */
public class UserExtendedInformationServiceImpl implements UserExtendedInformationService {

	/**
	 * 数据库服务
	 */
	private UserExtendedInformationDao dbService;

	/**
	 * 用户扩展信息服务 impl
	 */
	public UserExtendedInformationServiceImpl() {
		dbService = new UserExtendedInformationDao();
	}

	@Override
	public UserExtendedInformation findUserExtendedInformationByUserName(String username) {
		return dbService.findUserExtendedInformationByUserName(username);
	}

	@Override
	public void createUserExtendedInformation(UserExtendedInformation userExtendedInformation) {
		dbService.createUserExtendedInformation(userExtendedInformation);
	}

	@Override
	public void updateUserExtendedInformation(UserExtendedInformation userExtendedInformation) {
		dbService.updateUserExtendedInformation(userExtendedInformation);
	}

	@Override
	public void deleteUserExtendedInformationByUserName(String username) {
		dbService.deleteUserExtendedInformation(username);
	}
}
