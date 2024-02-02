package ext.ziang.common.helper.ldap;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * 打开DJ密码服务
 *
 * @author anzhen
 * @date 2024/02/01
 * ext.trinasolar.oauth.OpenDjPasswordService
 */
public class OpenDjPasswordService {
    private LdapContext LDAP_CONTEXT = null;
    /**
     * 连接 CTLS
     */
    private final Control[] connCtls = null;
    /**
     * LDAP 网址
     */
    private static String LDAP_URL = "ldap://pdm-test.trinasolar.com:389/dc=windchill,dc=com";
    /**
     * LDAP 用户名
     */// 根据你的设置修改
    public static String LDAP_USERNAME = "cn=Manager";
    /**
     * LDAP 密码
     */// 根据你的设置修改
    public static String LDAP_PASSWORD = "ldapadmin";
    /**
     * 初始上下文工厂
     */
    public static String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    /**
     * 基本 DN
     */
    public static String BASE_DN = ",dc=windchill,dc=com";


    public static void main(String[] args) {
        OpenDjPasswordService service = new OpenDjPasswordService();
        if (service.authentication("283118", "{SSHA}rmXODCoEOb9g0Mu5EdvNEOVcurKRFriMH8qMLQ==")) {
            System.out.println("service = " + service);
        }
    }


    /**
     * 初始化 ldapclient
     */
    private void initLDAPClient() {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, LDAP_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, LDAP_USERNAME);
        env.put(Context.SECURITY_CREDENTIALS, LDAP_PASSWORD);
        try {
            LDAP_CONTEXT = new InitialLdapContext(env, null);
        } catch (javax.naming.AuthenticationException e) {
            System.out.println("验证失败：" + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户 DN
     *
     * @param uid uid
     * @return {@link String}
     */
    private String getUserDN(String uid) {
        String userDN = "";
        initLDAPClient();
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            // 编写查询文件
            Attributes attributes = new BasicAttributes();
            attributes.put("uid", uid);

            NamingEnumeration<SearchResult> en = LDAP_CONTEXT.search("", attributes);
            if (en == null || !en.hasMoreElements()) {
                System.out.println("未找到该用户");
            }

            while (en != null && en.hasMoreElements()) {
                Object obj = en.nextElement();
                if (obj instanceof SearchResult) {
                    SearchResult si = (SearchResult) obj;
                    System.out.println("si = " + si);
                    userDN += si.getName();
                    Attributes attrs = si.getAttributes();
                    Attribute attr = attrs.get("userPassword");
                    String password = new String((byte[]) attr.get());
                    System.out.println("password = " + password);
                    System.out.println("si = " + si);
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
     * LDAP  Authenricate
     * Authenricate 验证用户密码
     *
     * @param UID      uid
     * @param password 密码
     * @return boolean
     */
    public boolean authentication(String UID, String password) {
        boolean valide;
        String userDN = getUserDN(UID);
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
