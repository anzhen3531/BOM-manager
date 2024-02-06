package ext.ziang.oauth;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * OAuth 配置常量
 *
 * @author anzhen
 * @date 2023/12/28
 */
public class OAuthConfigConstant {
    /**
     * 客户端 ID
     */
    public static String CLIENT_ID;
    /**
     * 访问类型
     */
    public static String GRANT_TYPE = "authorization_code";
    /**
     * 客户端密码
     */
    public static String CLIENT_SECRET;
    /**
     * 重定向 URI
     */
    public static String REDIRECT_URI;
    /**
     * 重定向页面 URI
     */
    public static String REDIRECT_PAGE_URI = "/Windchill/app/";
    /**
     * 访问令牌 URL github
     */
    public static String ACCESS_TOKEN_URL = "https://iam.trinasolar.com/mga/sps/oauth/oauth20/token";
    /**
     * 获取用户信息 URL
     */
    public static String GET_USER_INFO_URL = "https://iam.trinasolar.com/mga/sps/oauth/oauth20/userinfo";

    static {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        CLIENT_ID = "6b4ecccee521e3c0ada6";
        CLIENT_SECRET = "3af56830138359d761d3868df61712db1ab36815";
        // 地址需要更换
        REDIRECT_URI = "http://pdm-test.trinasolar.com" + REDIRECT_PAGE_URI;
    }

    /**
     * OAuth2 登录页面
     */
    public static String OAUTH2_LOGIN_PAGE = "http://win-fv1tfp5mpk5.ziang.com/Windchill/oauth2/git/login";
}
