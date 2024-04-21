package ext.ziang.common.helper.user;

import java.util.Enumeration;

import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.org.OrganizationServicesHelper;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;

/**
 * wt principal 实用程序
 *
 * @author anzhen
 * @date 2023/12/30
 *       WTPrincipalUtil.getContextUserLast()
 */
public class CommonWTPrincipalHelper {
	/**
	 * 获取上下文用户 Last
	 *
	 * @return {@link String}
	 * @throws WTException
	 *             WTException
	 */
	public static String getContextUserEMail() {
		try {
			WTPrincipal principal = SessionHelper.getPrincipal();
			WTUser wtUser = (WTUser) principal;
			return wtUser.getEMail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据full name查询user
	 *
	 * @param name
	 * @return
	 */
	public static WTUser getUserByFullName(String name) throws WTException {
		Enumeration enumUser = OrganizationServicesHelper.manager.findUser(
				WTUser.NAME, name);
		WTUser user = null;
		if (enumUser.hasMoreElements()) {
			user = (WTUser) enumUser.nextElement();
		}
		if (user == null) {
			enumUser = OrganizationServicesHelper.manager.findUser(
					WTUser.FULL_NAME, name);
			if (enumUser.hasMoreElements()) {
				user = (WTUser) enumUser.nextElement();
			}
		}

		if (user == null) {
			throw new WTException("系统中不存在用户名为'" + name + "'的用户！");
		}

		return user;
	}

	/**
	 * 按姓氏获取用户名
	 *
	 * @param name
	 *            名字
	 * @return {@link WTUser} 当前用户
	 */
	public static WTUser getUserNameByEmail(String name) {
		try {
			QuerySpec querySpec = new QuerySpec(WTUser.class);
			querySpec.appendWhere(new SearchCondition(WTUser.class, WTUser.E_MAIL, SearchCondition.EQUAL, name),
					new int[] { 0 });
			QueryResult query = PersistenceServerHelper.manager.query(querySpec);
			if (query.hasMoreElements()) {
				return (WTUser) query.nextElement();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
