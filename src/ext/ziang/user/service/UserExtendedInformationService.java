package ext.ziang.user.service;

import ext.ziang.user.entity.UserExtendedInformation;

public interface UserExtendedInformationService {

	/**
	 * 按用户名查找用户扩展信息
	 * 获取用户扩展信息
	 *
	 * @param username 用户名
	 * @return {@link UserExtendedInformation}
	 */
	UserExtendedInformation findUserExtendedInformationByUserName(String username);

	/**
	 * 创建用户扩展信息
	 *
	 * @param userExtendedInformation
	 *            用户扩展信息
	 */
	void createUserExtendedInformation(UserExtendedInformation userExtendedInformation);

	/**
	 * 更新用户扩展信息
	 *
	 * @param userExtendedInformation
	 *            用户扩展信息
	 */
	void updateUserExtendedInformation(UserExtendedInformation userExtendedInformation);

	/**
	 * 删除用户扩展信息
	 *
	 * @param username 用户名
	 */
	void deleteUserExtendedInformationByUserName(String username);
}
