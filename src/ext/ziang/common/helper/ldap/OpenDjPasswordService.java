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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ext.ziang.common.config.PropertiesHelper;
import ext.ziang.common.util.LoggerHelper;

/**
 * 打开DJ密码服务
 *
 * @author anzhen
 * @date 2024/02/01
 *       <p/>
 *       ext.ziang.oauth.OpenDjPasswordService
 */
public class OpenDjPasswordService {
    private static final Logger logger = LoggerFactory.getLogger(OpenDjPasswordService.class);
    private static final PropertiesHelper helper = PropertiesHelper.getInstance();
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
    private static final String LDAP_URL = helper.getValueByKey("ldap.url");
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
            logger.debug("service = " + service);
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
        LoggerHelper.log("LDAP_PASSWORD" + LDAP_PASSWORD);
        LoggerHelper.log("LDAP_URL" + LDAP_URL);
        LoggerHelper.log("LDAP_USERNAME" + LDAP_USERNAME);
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, LDAP_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, LDAP_USERNAME);
        env.put(Context.SECURITY_CREDENTIALS, LDAP_PASSWORD);
        try {
            LDAP_CONTEXT = new InitialLdapContext(env, null);
        } catch (AuthenticationException e) {
            logger.debug("验证失败：" + e);
        } catch (Exception e) {
            logger.error("initLDAPClient Exception", e);
        }
    }

    /**
     * 获取用户 DN
     *
     * @param uid uid
     * @return {@link String}
     */
    private String getUserDN(String uid) {
        StringBuilder userDN = new StringBuilder();
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> en = LDAP_CONTEXT.search("", "uid=" + uid, constraints);
            if (en == null || !en.hasMoreElements()) {
                logger.debug("未找到该用户");
            }
            while (en != null && en.hasMoreElements()) {
                SearchResult obj = en.nextElement();
                if (obj != null) {
                    userDN.append(obj.getName());
                    logger.debug("SearchResult obj {} ", obj);
                    logger.debug("SearchResult obj name {} ", obj.getName());
                    userDN.append(BASE_DN);
                } else {
                    logger.debug("getUserDN {}", obj);
                }
            }
        } catch (Exception e) {
            logger.debug("查找用户时产生异常。", e);
        }
        return userDN.toString();
    }

    /**
     * LDAP Authenticate 验证用户密码
     *
     * @param UID uid
     * @param password 密码
     * @return boolean
     */
    public boolean authentication(String UID, String password) {
        boolean valide;
        String userDN = getUserDN(UID);
        logger.debug("userDN = " + userDN);
        try {
            LDAP_CONTEXT.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN);
            LDAP_CONTEXT.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
            LDAP_CONTEXT.reconnect(connCtls);
            logger.debug(userDN + " 验证通过");
            valide = true;
        } catch (AuthenticationException e) {
            logger.error("authentication AuthenticationException error:", e);
            valide = false;
        } catch (NamingException e) {
            logger.error("authentication  NamingException error:", e);
            valide = false;
        }
        return valide;
    }
}
