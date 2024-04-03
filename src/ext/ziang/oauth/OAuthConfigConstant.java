package ext.ziang.oauth;

import ext.ziang.common.constants.CommonConfigConstants;

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
	public static String GRANT_TYPE = "code";
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
	public static String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
	/**
	 * 获取用户信息 URL
	 */
	public static String GET_USER_INFO_URL = "https://api.github.com/user";

	static {
		CLIENT_ID = "6b4ecccee521e3c0ada6";
		CLIENT_SECRET = "3af56830138359d761d3868df61712db1ab36815";
		// 地址需要更换
		REDIRECT_URI = CommonConfigConstants.HOST_URL + REDIRECT_PAGE_URI;
	}

	/**
	 * OAuth2 登录页面
	 */
	public static String OAUTH2_LOGIN_PAGE = CommonConfigConstants.HOST_URL + "/Windchill/netmarkets/jsp/gwt/login.jsp";
}
