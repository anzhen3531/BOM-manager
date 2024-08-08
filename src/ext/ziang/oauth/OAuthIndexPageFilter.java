package ext.ziang.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ext.ziang.common.config.PropertiesHelper;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jndi.toolkit.chars.BASE64Encoder;

import cn.hutool.core.util.StrUtil;
import ext.ziang.common.helper.ldap.OpenDjPasswordService;
import ext.ziang.common.util.LoggerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.method.P;
import wt.util.WTException;
import wt.util.WTRuntimeException;

/**
 * OAuth 索引页筛选器 ext.ziang.oauth.OAuthIndexPageFilter
 *
 * @author ander
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
            String cookiesToken = SSOUtil.getSSOTokenByCookies(request);
            String basicToken = SSOUtil.getSSOTokenByCookies(request, SSOUtil.BASIC_LOGIN);
            HttpSession session = request.getSession();
            String ssoAuth = (String)session.getAttribute(SSOUtil.SSO_AUTH);
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
                    if (StringUtils.isNotBlank(ssoAuth)) {
                        request.setAttribute(SSOUtil.SSO_AUTH, loginName);
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
            // 图纸端登录则使用默认的 Basic Auth
            if (StringUtils.isNotBlank(authorization) || validateContains(NO_SSO_URLS, requestURI)
                || isFormLogin(request)) {
                // 从Base中获取相关的用户名
                boolean loginSuccess = basicLogin(authorization, request, response, filterChain);
                if (!loginSuccess) {
                    redirectBasicLogin(response);
                }
                request.removeAttribute(SSOUtil.SSO_AUTH);
                SSOUtil.deleteSSOTokenByCookie(request, response);
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
     * @param authorization
     * @param request
     * @param response
     * @param filterChain
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private boolean basicLogin(String authorization, HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
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
     * @param request
     * @param response
     * @param filterChain
     * @param requestURI
     * @return
     * @throws WTException
     * @throws ServletException
     * @throws IOException
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
                    response.addCookie(SSOUtil.createSSOTokenByCookie(token));
                    request.setAttribute(SSOUtil.SSO_AUTH, loginUserName);
                    SSORequestWrap ssoRequestWrap = new SSORequestWrap(request, loginUserName);
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
            String tokenByCookies = SSOUtil.getSSOTokenByCookies(request, SSOUtil.BASIC_LOGIN);
            if (StringUtils.isNotBlank(tokenByCookies)) {
                return handlerBasicLogin(tokenByCookies, request, response, filterChain);
            } else {
                String authorization = request.getHeader("Authorization");
                if (StringUtils.isNotBlank(authorization)) {
                    return handlerBasicLogin(tokenByCookies, request, response, filterChain);
                }
                return false;
            }
        } else {
            // 获取令牌
            String token = SSOUtil.getSSOTokenByCookies(request);
            if (StringUtils.isNotBlank(token)) {
                HttpSession session = request.getSession();
                String ssoAuth = (String)session.getAttribute(SSOUtil.SSO_AUTH);
                String loginName = getLoginName(token, ssoAuth);
                if (StringUtils.isNotBlank(ssoAuth)) {
                    logger.debug("当前用户已经登录过Windchill userName{}", loginName);
                    request.setAttribute(SSOUtil.SSO_AUTH, loginName);
                }
                SSORequestWrap SSORequestWrap = newWrapRequest(request, loginName);
                filterChain.doFilter(SSORequestWrap, response);
                return true;
            }
        }
        // TODO 发起重定向 重定向到登陆页面
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
     * @return
     */
    private String getLoginName(String cookiesToken, String ssoAuth) {
        if (Objects.isNull(cookiesToken) && Objects.isNull(ssoAuth)) {
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
        SSORequestWrap newRequest = new SSORequestWrap(request, userName);
        return newRequest;
    }

    /**
     * 新包装请求
     *
     * @param request 请求
     * @param userName 用户名
     * @return {@link SSORequestWrap}
     */
    private SSORequestWrap newWrapRequest(HttpServletRequest request, String userName, String auth) {
        SSORequestWrap newRequest = new SSORequestWrap(request, userName);
        newRequest.addHeader("Authorization", auth);
        return newRequest;
    }

    /**
     * 无权限访问，重定向登录
     *
     * @param response
     * @throws IOException
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
     * 
     * @param username
     * @param password
     * @param response
     * @param authorization
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
                response.addCookie(SSOUtil.createSSOTokenByCookie(authorization, SSOUtil.BASIC_LOGIN));
                request.setAttribute(SSOUtil.SSO_AUTH, username);
                SSORequestWrap ssoRequestWrap = new SSORequestWrap(request, username);
                filterChain.doFilter(ssoRequestWrap, response);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
