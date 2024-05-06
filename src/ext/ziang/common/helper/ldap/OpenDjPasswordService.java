package ext.ziang.common.helper.ldap;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import ext.ziang.common.config.PropertiesHelper;
import ext.ziang.common.util.CommonLog;

/**
 * 打开DJ密码服务
 *
 * @author anzhen
 * @date 2024/02/01
 *       ext.ziang.oauth.OpenDjPasswordService
 */
public class OpenDjPasswordService {
	private static PropertiesHelper helper = PropertiesHelper.getInstance();
	/**
	 * LDAP 上下文
	 */
	private LdapContext LDAP_CONTEXT = null;
	/**
	 * 连接 CTLS
	 */
	private final Control[] connCtls = null;
	/**
	 * LDAP 网址
	 */
	private static String LDAP_URL = helper.getValueByKey("ldap.url");
	/**
	 * LDAP 用户名
	 */
	public static String LDAP_USERNAME = helper.getValueByKey("ldap.username");
	/**
	 * LDAP 密码
	 */
	public static String LDAP_PASSWORD = helper.getValueByKey("ldap.password");
	/**
	 * 初始上下文工厂
	 */
	public static String INITIAL_CONTEXT_FACTORY = helper.getValueByKey("ldap.context.factory");
	/**
	 * 基本 DN
	 */
	public static String BASE_DN = helper.getValueByKey("ldap.dn");

	public static void main(String[] args) {
		OpenDjPasswordService service = new OpenDjPasswordService();
		service.initLDAPClient();
		if (service.authentication("wcadmin", "wcadmin")) {
			System.out.println("service = " + service);
		}
	}

	public OpenDjPasswordService() {
		this.initLDAPClient();
	}

	/**
	 * 初始化 ldap client
	 */
	private void initLDAPClient() {
		Hashtable<String, String> env = new Hashtable<>();
		CommonLog.log("LDAP_PASSWORD" + LDAP_PASSWORD);
		CommonLog.log("LDAP_URL" + LDAP_URL);
		CommonLog.log("LDAP_USERNAME" + LDAP_USERNAME);
		env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL, LDAP_URL);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, LDAP_USERNAME);
		env.put(Context.SECURITY_CREDENTIALS, LDAP_PASSWORD);
		try {
			LDAP_CONTEXT = new InitialLdapContext(env, null);
		} catch (AuthenticationException e) {
			System.out.println("验证失败：" + e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户 DN
	 *
	 * @param uid
	 *            uid
	 * @return {@link String}
	 */
	private String getUserDN(String uid) {
		String userDN = "";
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> en = LDAP_CONTEXT.search("", "uid=" + uid, constraints);
			if (en == null || !en.hasMoreElements()) {
				System.out.println("未找到该用户");
			}
			while (en != null && en.hasMoreElements()) {
				Object obj = en.nextElement();
				if (obj instanceof SearchResult) {
					SearchResult si = (SearchResult) obj;
					userDN += si.getName();
					System.out.println("si = " + si);
					System.out.println("si = " + si.getName());
					userDN += BASE_DN;
				} else {
					System.out.println(obj);
				}
			}
		} catch (Exception e) {
			System.out.println("查找用户时产生异常。");
			e.printStackTrace();
		}

		return userDN;
	}

	/**
	 * LDAP Authenticate 验证用户密码
	 *
	 * @param UID
	 *            uid
	 * @param password
	 *            密码
	 * @return boolean
	 */
	public boolean authentication(String UID, String password) {
		boolean valide;
		String userDN = getUserDN(UID);
		System.out.println("userDN = " + userDN);
		try {
			LDAP_CONTEXT.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN);
			LDAP_CONTEXT.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
			LDAP_CONTEXT.reconnect(connCtls);
			System.out.println(userDN + " 验证通过");
			valide = true;
		} catch (AuthenticationException e) {
			System.out.println(userDN + " 验证失败");
			System.out.println(e);
			valide = false;
		} catch (NamingException e) {
			System.out.println(userDN + " 验证失败");
			valide = false;
		}
		return valide;
	}
}
