package ext.ziang.oauth;

import ext.ziang.common.config.PropertiesHelper;
import ext.ziang.common.constants.CommonConfigConstants;

/**
 * OAuth 配置常量
 *
 * @author anzhen
 * @date 2023/12/28
 */
public interface OAuthConfigConstant {
	PropertiesHelper instance = PropertiesHelper.getInstance();
	/**
	 * 客户端 ID
	 */
	String CLIENT_ID = instance.getValueByKey("oauth.client.id");
	/**
	 * 访问类型
	 */
	String GRANT_TYPE = "code";
	/**
	 * 客户端密码
	 */
	String CLIENT_SECRET = instance.getValueByKey("oauth.client.secret");

	/**
	 * 重定向页面 URI
	 */
	String REDIRECT_PAGE_URI = instance.getValueByKey("oauth.redirect.uri");
	/**
	 * 重定向 URI
	 */
	String REDIRECT_URI = CommonConfigConstants.HOST_URL + REDIRECT_PAGE_URI;
	/**
	 * 访问令牌 URL github
	 */
	String ACCESS_TOKEN_URL = instance.getValueByKey("oauth.access.token.url");
	/**
	 * 获取用户信息 URL
	 */
	String GET_USER_INFO_URL = instance.getValueByKey("oauth.access.user.info.url");

	/**
	 * OAuth2 登录页面
	 */
	String OAUTH2_LOGIN_PAGE = CommonConfigConstants.HOST_URL + "/Windchill/netmarkets/jsp/gwt/login.jsp";
}
