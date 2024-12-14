package ext.ziang.oauth;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.sun.jndi.toolkit.chars.BASE64Encoder;

import cn.hutool.core.util.StrUtil;
import ext.ziang.common.config.PropertiesHelper;
import ext.ziang.common.helper.ldap.OpenDjPasswordService;
import wt.util.WTException;

/**
 * OAuth 索引页筛选器 ext.ziang.oauth.OAuthIndexPageFilter
 *
 *
 * @author anzhen
 * @date 2023/12/25
 */
public class OAuthIndexPageFilter implements Filter {

    PropertiesHelper instance = PropertiesHelper.getInstance();

    private static final Logger logger = LoggerFactory.getLogger(OAuthIndexPageFilter.class);
    /**
     * 白名单网址
     */
    public Set<String> WHITE_LIST_URLS = new HashSet<>();
    public Set<String> NO_SSO_URLS = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        Map<String, String> stringMap = instance.getAll();
        for (Map.Entry<String, String> entry : stringMap.entrySet()) {
            if (entry.getKey().contains("white.list")) {
                WHITE_LIST_URLS.add(entry.getValue());
            } else if (entry.getKey().contains("no.sso.list")) {
                NO_SSO_URLS.add(entry.getValue());
            }
        }
        logger.debug("NO_SSO_URLS {}", NO_SSO_URLS);
        logger.debug("WHITE_LIST_URLS {}", WHITE_LIST_URLS);
    }

    /**
     * 拦截Windchill 所有的请求
     *
     * @param servletRequest 请求
     * @param servletResponse 响应
     * @param filterChain 过滤链
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        if ((servletResponse instanceof HttpServletResponse) && (servletRequest instanceof HttpServletRequest)) {
            // 进入验证
            HttpServletResponse response = (HttpServletResponse)servletResponse;
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            String authorization = request.getHeader("Authorization");
            String cookiesToken = CookieUtils.getSSOTokenByCookies(request);
            String basicToken = CookieUtils.getSSOTokenByCookies(request, CookieUtils.BASIC_LOGIN);
            HttpSession session = request.getSession();
            String ssoAuth = (String)session.getAttribute(CookieUtils.SSO_AUTH);
            String requestURI = request.getRequestURI();
            String servletPath = request.getServletPath();
            logger.debug("authorization {}", authorization);
            logger.debug("cookiesToken {}", cookiesToken);
            logger.debug("ssoAuth {}", ssoAuth);
            logger.debug("remoteUser {}", request.getRemoteUser());
            logger.debug("requestURI {}", requestURI);
            logger.debug("servletPath {}", servletPath);
            logger.debug("basicToken {}", basicToken);
            if (validateContains(WHITE_LIST_URLS, requestURI)) {
                filterChain.doFilter(request, response);
                logger.debug("OAuthIndexPageFilter:: doFilter 处理耗时为{}ms", System.currentTimeMillis() - startTime);
                return;
            }
            // 先判断是否使用SSO登录过
            if (StringUtils.isNotBlank(cookiesToken)) {
                // 获取登录名称
                String loginName = getLoginName(cookiesToken, ssoAuth);
                if (StringUtils.isNotBlank(loginName)) {
                    logger.debug("当前用户已经登录过Windchill userName{}", loginName);
                    // 如果Token过期则直接返回为空
                    if (StringUtils.isBlank(ssoAuth)) {
                        request.getSession().setAttribute(CookieUtils.SSO_AUTH, loginName);
                    }
                    SSORequestWrap SSORequestWrap = newWrapRequest(request, loginName);
                    filterChain.doFilter(SSORequestWrap, response);
                    return;
                } else {
                    // 使用 token直接获取信息登录
                    try {
                        logger.debug("SSO 登录");
                        ssoLogin(request, response, filterChain, requestURI);
                        logger.debug("SSO 登录结束");
                    } catch (Exception e) {
                        logger.error("SSO登录失败 message" + e.getMessage(), e);
                        e.printStackTrace();
                    }
                }
            }

            // 自定义页面的登录
            if (StringUtils.isNotBlank(basicToken)) {
                // 获取登录名称
                String loginName = getBasicLoginName(basicToken, ssoAuth);
                if (StringUtils.isNotBlank(loginName)) {
                    logger.debug("当前用户已经登录过Windchill userName{}", loginName);
                    if (StringUtils.isBlank(ssoAuth)) {
                        request.getSession().setAttribute(CookieUtils.SSO_AUTH, loginName);
                    }
                    SSORequestWrap ssoRequestWrap = newWrapRequest(request, loginName, basicToken);
                    filterChain.doFilter(ssoRequestWrap, response);
                    return;
                } else {
                    // 使用 token直接获取信息登录
                    try {
                        logger.debug("SSO 登录");
                        ssoLogin(request, response, filterChain, requestURI);
                        logger.debug("SSO 登录结束");
                    } catch (Exception e) {
                        logger.error("SSO登录失败 message" + e.getMessage(), e);
                        e.printStackTrace();
                    }
                }
            }

            // 图纸端登录则使用默认的 Basic Auth
            if (StringUtils.isNotBlank(authorization) || validateContains(NO_SSO_URLS, requestURI)
                || isFormLogin(request)) {
                // 从Base中获取相关的用户名
                boolean loginSuccess = basicLogin(authorization, request, response, filterChain);
                if (!loginSuccess) {
                    redirectBasicLogin(response);
                }
                request.removeAttribute(CookieUtils.SSO_AUTH);
                CookieUtils.deleteSSOTokenByCookie(request, response);
                return;
            }

            // 使用 token直接获取信息登录
            try {
                logger.debug("SSO 登录");
                ssoLogin(request, response, filterChain, requestURI);
                logger.debug("SSO 登录结束");
            } catch (Exception e) {
                logger.error("SSO登录失败 message" + e.getMessage(), e);
            }
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 判断是否是表单登录的
     * 
     * @param request
     * @return
     */
    private boolean isFormLogin(HttpServletRequest request) {
        return StringUtils.isNotBlank(request.getRemoteUser());
    }

    /**
     * Basic Login
     * 
     * @param authorization 认证信息
     * @param request 请求
     * @param response 响应
     * @param filterChain 拦截
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private boolean basicLogin(String authorization, HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.isBlank(authorization)){
            return false;
        }
        String remoteUser = request.getRemoteUser();
        if (StringUtils.isNotBlank(remoteUser)) {
            // 采用其余的登录条件
            SSORequestWrap ssoRequestWrap = newWrapRequest(request, remoteUser);
            filterChain.doFilter(ssoRequestWrap, response);
            return true;
        }
        String[] strings = convertAuthHeader(authorization);
        String username = strings[0];
        logger.debug("username = {}", username);
        String password = strings[1];
        logger.debug("password = {}", password);
        if (StrUtil.isNotBlank(username) && StrUtil.isNotBlank(password)) {
            OpenDjPasswordService service = new OpenDjPasswordService();
            logger.error("登录成功 用户名{}, 密码{}", username, password);
            if (service.authentication(username, password)) {
                logger.error("登录成功 用户名{}, 密码{}", username, password);
                // 采用其余的登录条件
                SSORequestWrap ssoRequestWrap = newWrapRequest(request, username, authorization);
                filterChain.doFilter(ssoRequestWrap, response);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 登录SSO
     *
     * @param request 请求
     * @param response 响应
     * @param filterChain 拦截
     * @param requestURI 请求地址
     * @return 是否登录成功
     */
    private boolean ssoLogin(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain,
        String requestURI) throws WTException, ServletException, IOException {
        logger.debug("request = " + request + ", response = " + response + ", filterChain = " + filterChain
            + ", requestURI = " + requestURI);
        String code = request.getParameter("code");
        String mode = request.getParameter("MODE");
        logger.debug("request code{}", code);
        if (StrUtil.isNotBlank(code)) {
            String token = GithubOAuthProvider.getAccessTokenByCodeAndUrl(code, requestURI);
            logger.debug("token {} ", token);
            if (StrUtil.isNotBlank(token)) {
                JSONObject userInfo = GithubOAuthProvider.getUserInfo(token);
                logger.debug("userInfo = {}", userInfo);
                String loginUserName = userInfo.getString("login");
                if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(loginUserName)) {
                    response.addCookie(CookieUtils.createSSOTokenByCookie(token));
                    // 添加Session
                    request.getSession().setAttribute(CookieUtils.SSO_AUTH, loginUserName);
                    SSORequestWrap ssoRequestWrap = new SSORequestWrap(request);
                    filterChain.doFilter(ssoRequestWrap, response);
                    return true;
                } else {
                    logger.error("当前登录用户获取失败 token{}, code{}", token, code);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "获取登录用户失败");
                    return false;
                }
            } else {
                logger.error("获取Token失败 token{}, code{}", token, code);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "获取Token失败");
                return false;
            }
            // 用户使用账号密码登录
        } else if ("LOGIN".equals(mode)) {
            // 获取用户名和密码
            JSONObject requestBody = RequestBodyUtils.getRequestBody(request);
            String username = requestBody.getString("username");
            String password = requestBody.getString("password");
            String authorization = new BASE64Encoder().encode(StrUtil.format("{}:{}", username, password).getBytes());
            boolean isSuccess = basicLogin(username, password, request, response, authorization, filterChain);
            if (isSuccess) {
                return true;
            } else {
                logger.error("当前输入的用户名和密码错误 username{}, password{}", username, code);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "用户账号密码错误！");
                return false;
            }
        } else {
            // 获取令牌
            String token = CookieUtils.getSSOTokenByCookies(request);
            if (StringUtils.isNotBlank(token)) {
                HttpSession session = request.getSession();
                String ssoAuth = (String)session.getAttribute(CookieUtils.SSO_AUTH);
                String loginName = getLoginName(token, ssoAuth);
                if (StringUtils.isBlank(ssoAuth)) {
                    logger.debug("当前用户已经登录过Windchill userName{}", loginName);
                    request.getSession().setAttribute(CookieUtils.SSO_AUTH, loginName);
                }
                SSORequestWrap SSORequestWrap = newWrapRequest(request, loginName);
                filterChain.doFilter(SSORequestWrap, response);
                return true;
            }
        }
        // 发起重定向 重定向到登陆页面
        if (!requestURI.contains(OAuthConfigConstant.OAUTH2_LOGIN_PAGE_FILE)) {
            response.sendRedirect(OAuthConfigConstant.OAUTH2_LOGIN_PAGE);
            return false;
        } else {
            filterChain.doFilter(request, response);
            return true;
        }
    }

    /**
     * 获取当前登录的用户名
     * 
     * @param cookiesToken 当前令牌
     * @param ssoAuth 用户Auth
     * @return 登录用户名
     */
    private String getLoginName(String cookiesToken, String ssoAuth) {
        if (StringUtils.isBlank(cookiesToken)){
            return null;
        }
        if (StringUtils.isNotBlank(ssoAuth)) {
            return ssoAuth;
        } else {
            JSONObject userInfo = GithubOAuthProvider.getUserInfo(cookiesToken);
            logger.debug("userInfo = {}", userInfo);
            return userInfo.getString("login");
        }
    }

    /**
     * 获取当前登录的用户名
     *
     * @param cookiesToken 当前令牌
     * @param ssoAuth 用户Auth
     * @return 登录用户名
     */
    private String getBasicLoginName(String cookiesToken, String ssoAuth) {
        if (Objects.isNull(cookiesToken)) {
            return null;
        }
        if (StringUtils.isNotBlank(ssoAuth)) {
            return ssoAuth;
        } else {
            String[] strings = convertAuthHeader(cookiesToken);
            return strings[0];
        }
    }

    /**
     * 验证包含
     *
     * @param whiteListUrls 白名单网址
     * @param url 网址
     * @return boolean
     */
    private boolean validateContains(Set<String> whiteListUrls, String url) {
        for (String whiteListUrl : whiteListUrls) {
            if (url.contains(whiteListUrl)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 转换身份验证标头
     *
     * @param auth 认证
     * @return {@link String[]}
     */
    private static String[] convertAuthHeader(String auth) {
        logger.debug("auth = " + auth);
        if (auth.contains("Basic ")) {
            auth = auth.replace("Basic ", "");
        }
        String credentials = new String(Base64.getDecoder().decode(auth));
        return credentials.split(":", 2);
    }

    /**
     * 新包装请求
     *
     * @param request 请求
     * @param userName 用户名
     * @return {@link SSORequestWrap}
     */
    private SSORequestWrap newWrapRequest(HttpServletRequest request, String userName) {
        return newSSOWrapRequest(request, userName);
    }

    /**
     * 新包装请求
     *
     * @param request 请求
     * @param userName 用户名
     * @return {@link SSORequestWrap}
     */
    private SSORequestWrap newWrapRequest(HttpServletRequest request, String userName, String auth) {
        return newSSOWrapRequest(request, auth, userName);
    }

    /**
     * 无权限访问，重定向登录
     *
     * @param response 响应
     */
    private void redirectBasicLogin(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("WWW-Authenticate", "Basic realm=Windchill");
        response.setDateHeader("Expires", 0L);
        response.getWriter().write("401 Unauthorized: You must authenticate first.");
    }

    @Override
    public void destroy() {
        logger.debug("销毁首页拦截器");
    }

    public static boolean handlerBasicLogin(String authorization, HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String[] strings = convertAuthHeader(authorization);
        String username = strings[0];
        logger.debug("username = {}", username);
        String password = strings[1];
        logger.debug("password = {}", password);
        return basicLogin(username, password, request, response, authorization, filterChain);
    }

    /**
     * 基本账号密码登录
     * 
     * @param username 账号
     * @param password 密码
     * @param response 响应
     * @param authorization 认证信息
     * @return
     * @throws IOException
     */
    public static boolean basicLogin(String username, String password, HttpServletRequest request,
        HttpServletResponse response, String authorization, FilterChain filterChain)
        throws IOException, ServletException {
        if (StrUtil.isNotBlank(username) && StrUtil.isNotBlank(password)) {
            OpenDjPasswordService service = new OpenDjPasswordService();
            logger.error("登录成功 用户名{}, 密码{}", username, password);
            if (service.authentication(username, password)) {
                logger.error("登录成功 用户名{}, 密码{}", username, password);
                // 重定向到首页
                authorization = authorization.replace("Basic ", "");
                response.addCookie(CookieUtils.createSSOTokenByCookie(authorization, CookieUtils.BASIC_LOGIN));
                request.getSession().setAttribute(CookieUtils.SSO_AUTH, username);
                SSORequestWrap ssoRequestWrap = newSSOWrapRequest(request, username);
                filterChain.doFilter(ssoRequestWrap, response);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 新包装请求
     *
     * @param request 请求
     * @param token 令 牌
     * @param username 用户名
     * @return {@link SSORequestWrap}
     */
    private static SSORequestWrap newSSOWrapRequest(HttpServletRequest request, String token, String username) {
        SSORequestWrap newRequest = new SSORequestWrap(request);
        if (StringUtils.isNotBlank(token)) {
            if (!token.contains("Basic ")) {
                token = "Basic " + token;
            }
            newRequest.addHeader("Authorization", token);
        }
        newRequest.setRemoteUser(username);
        return newRequest;
    }

    /**
     * 新包装请求
     *
     * @param request 请求
     * @param username 用户名
     * @return {@link SSORequestWrap}
     */
    private static SSORequestWrap newSSOWrapRequest(HttpServletRequest request, String username) {
        return newSSOWrapRequest(request, null, username);
    }
}
