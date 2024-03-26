package ext.ziang.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ext.ziang.common.util.IDUtil;
import ext.ziang.common.util.JdbcTemplateOracleHelper;
import ext.ziang.user.entity.UserExtendedInformation;
import wt.session.SessionHelper;

/**
 * 用户扩展信息 DAO
 *
 * @author anzhen
 * @date 2024/03/26
 */
public class UserExtendedInformationDao {

	/**
	 * 按用户名查找用户扩展信息
	 *
	 * @param username
	 *            用户名
	 * @return {@link UserExtendedInformation}
	 */
	public UserExtendedInformation findUserExtendedInformationByUserName(String username) {
		String sql = "SELECT * FROM USEREXTENDEDINFORMATION WHERE USERNAME = ?";
		Connection connection = JdbcTemplateOracleHelper.getConnection(false);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				UserExtendedInformation userExtendedInformation = new UserExtendedInformation();
				userExtendedInformation.setId(resultSet.getLong("ID"));
				userExtendedInformation.setUsername(resultSet.getString("USERNAME"));
				userExtendedInformation.setPassword(resultSet.getString("PASSWORD"));
				userExtendedInformation.setState(resultSet.getInt("STATE"));
				userExtendedInformation.setCreatedBy(resultSet.getString("CREATED_BY"));
				userExtendedInformation.setCreatedTime(resultSet.getDate("CREATED_TIME"));
				userExtendedInformation.setModifyBy(resultSet.getString("MODIFY_BY"));
				userExtendedInformation.setModifyTime(resultSet.getDate("MODIFY_TIME"));
				return userExtendedInformation;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplateOracleHelper.closeResAll(rs, statement, connection);
		}
		return null;
	}

	/**
	 * 创建用户扩展信息
	 *
	 * @param userExtendedInformation
	 *            用户扩展信息
	 */
	public void createUserExtendedInformation(UserExtendedInformation userExtendedInformation) {
		System.out.println("UserExtendedInformationDao.createUserExtendedInformation ====================> ");
		System.out.println("createUserExtendedInformation param userExtendedInformation = " + userExtendedInformation);
		String sql = "INSERT INTO USEREXTENDEDINFORMATION (ID,USERNAME, PASSWORD, STATE, CREATED_BY, MODIFY_BY) VALUE(?,?,?,?,?,?)";
		Connection connection = JdbcTemplateOracleHelper.getConnection(false);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setLong(1, IDUtil.getNextId());
			statement.setString(2, userExtendedInformation.getUsername());
			statement.setString(3, userExtendedInformation.getPassword());
			statement.setInt(4, 1);
			statement.setString(5, SessionHelper.manager.getPrincipal().getName());
			statement.setString(6, SessionHelper.manager.getPrincipal().getName());
			int flag = statement.executeUpdate();
			if (flag > 0) {
				System.out.println("创建用户扩展信息成功");
				connection.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplateOracleHelper.closeResAll(rs, statement, connection);
		}
	}

	public void updateUserExtendedInformation(UserExtendedInformation userExtendedInformation) {
		System.out.println("UserExtendedInformationDao.updateUserExtendedInformation ====================> ");
		System.out.println("updateUserExtendedInformation param userExtendedInformation = " + userExtendedInformation);
		// 先查询后更新
		String sql = "UPDATE USEREXTENDEDINFORMATION SET USERNAME= ?,PASSWORD= ? WHERE ID = ?";
		Connection connection = JdbcTemplateOracleHelper.getConnection(false);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userExtendedInformation.getUsername());
			statement.setString(2, userExtendedInformation.getPassword());
			statement.setLong(3, userExtendedInformation.getId());
			int flag = statement.executeUpdate();
			if (flag > 0) {
				System.out.println("创建用户扩展信息成功");
				connection.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplateOracleHelper.closeResAll(rs, statement, connection);
		}

	}

	/**
	 * 删除用户扩展信息
	 *
	 * @param username
	 *            用户名
	 */
	public void deleteUserExtendedInformation(String username) {
		System.out.println("UserExtendedInformationDao.deleteUserExtendedInformation ====================> ");
		System.out.println("deleteUserExtendedInformation param username = " + username);
		String sql = "DELETE USEREXTENDEDINFORMATION WHERE USERNAME = ?";
		Connection connection = JdbcTemplateOracleHelper.getConnection(false);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			int flag = statement.executeUpdate();
			if (flag != 0) {
				connection.commit();
				return;
			}
			System.out.println("执行删除失败 ！ 表示为: flag = " + flag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplateOracleHelper.closeResAll(rs, statement, connection);
		}
	}
}
