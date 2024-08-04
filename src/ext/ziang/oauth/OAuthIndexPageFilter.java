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
        logger.error("NO_SSO_URLS {}", NO_SSO_URLS);
        logger.error("WHITE_LIST_URLS {}", WHITE_LIST_URLS);
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
            HttpSession session = request.getSession();
            String ssoAuth = (String)session.getAttribute(SSOUtil.SSO_AUTH);
            String requestURI = request.getRequestURI();
            String servletPath = request.getServletPath();
            logger.error("authorization {}", authorization);
            logger.error("cookiesToken {}", cookiesToken);
            logger.error("ssoAuth {}", ssoAuth);
            logger.error("requestURI {}", requestURI);
            logger.error("servletPath {}", servletPath);
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
            if (StringUtils.isNotBlank(authorization) && validateContains(NO_SSO_URLS, requestURI)) {
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
                boolean ssoLogin = ssoLogin(request, response, filterChain, requestURI);
                if (ssoLogin) {
                    // 重定向回首页
                    response.sendRedirect(OAuthConfigConstant.OAUTH2_LOGIN_PAGE);
                }
                logger.debug("SSO 登录结束");
            } catch (Exception e) {
                logger.error("SSO登录失败 message" + e.getMessage(), e);
            }
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean basicLogin(String authorization, HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String[] strings = convertAuthHeader(authorization);
        String username = strings[0];
        logger.debug("username = {}", username);
        String password = strings[1];
        logger.debug("password = {}", password);
        if (StrUtil.isNotBlank(username) && StrUtil.isNotBlank(password)) {
            OpenDjPasswordService service = new OpenDjPasswordService();
            if (service.authentication(username, password)) {
                logger.debug("登录成功 用户名{}, 密码{}", username, password);
                // 采用其余的登录条件
                SSORequestWrap SSORequestWrap = newWrapRequest(request, username);
                filterChain.doFilter(SSORequestWrap, response);
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
        } else if (requestURI.contains("/gwt/login.jsp")) {
            String mode = request.getParameter("MODE");
            if ("login".equals(mode)) {
                // 获取请求主体数据
                BufferedReader reader = request.getReader();
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
                JSONObject body = null;
                if (StrUtil.isNotBlank(requestBody.toString())) {
                    body = JSON.parseObject(requestBody.toString());
                }
                // 验证code
                if (Objects.nonNull(body)) {
                    // 可以获取body进行验证
                    String username = body.getString("username");
                    logger.debug("username = " + username);
                    String password = body.getString("password");
                    logger.debug("password = " + password);
                    if (StrUtil.isNotBlank(username) && StrUtil.isNotBlank(password)) {
                        OpenDjPasswordService service = new OpenDjPasswordService();
                        if (service.authentication(username, password)) {
                            logger.debug("登录成功 用户名{}, 密码{}", username, password);
                            // 采用其余的登录条件
                            SSORequestWrap ssoRequestWrap = newWrapRequest(request, username);
                            filterChain.doFilter(ssoRequestWrap, response);
                            return true;
                        } else {
                            redirectBasicLogin(response);
                        }
                    }
                }
            } else {
                // 如果不是登录直接放行
                filterChain.doFilter(request, response);
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
        return false;
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
    private String[] convertAuthHeader(String auth) {
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

    // /**
    // * DO 过滤器
    // *
    // * @param request 请求
    // * @param response 响应
    // * @param filterChain 过滤链
    // * @throws IOException ioexception
    // * @throws ServletException Servlet 异常
    // */
    // public void doFilter2(ServletRequest request, ServletResponse response, FilterChain filterChain)
    // throws IOException, ServletException {
    // HttpServletResponse httpResponse = (HttpServletResponse)response;
    // HttpServletRequest httpServletRequest = (HttpServletRequest)request;
    // HttpSession session = httpServletRequest.getSession();
    // String auth = (String)session.getAttribute("auth");
    // LoggerHelper.log("auth =" + auth);
    // String remoteUser = httpServletRequest.getRemoteUser();
    // LoggerHelper.log("username = " + remoteUser);
    // String url = String.valueOf(httpServletRequest.getRequestURL());
    // LoggerHelper.log("url = ", httpServletRequest.getRequestURL());
    // String authorization = httpServletRequest.getHeader("Authorization");
    // LoggerHelper.log("authorization = " + authorization);
    //
    // // 可视化登录判断
    // if ((url.contains(SECURITYURL) || url.contains(VISLOGON) || url.contains(SIMPLETASKDISPATCHER)
    // || url.contains(CREOLOGINPAGE)) && StringUtils.isBlank(authorization)) {
    // // 无权限访问
    // redirectBasicLogin(httpResponse);
    // return;
    // }
    //
    // if (validateContains(WHITE_LIST_URLS, url)) {
    // LoggerHelper.log("url = " + url + " 放行");
    // filterChain.doFilter(request, httpResponse);
    // } else {
    // if (StrUtil.isNotBlank(auth)) {
    // SSORequestWrap SSORequestWrap = newWrapRequest(httpServletRequest, auth, session);
    // filterChain.doFilter(SSORequestWrap, httpResponse);
    // } else if (StrUtil.isNotBlank(authorization)) {
    // SSORequestWrap SSORequestWrap = newWrapRequest(httpServletRequest, authorization, session);
    // filterChain.doFilter(SSORequestWrap, httpResponse);
    // } else {
    // try {
    // String code = request.getParameter("code");
    // LoggerHelper.log("code = " + code);
    // // 获取请求主体数据
    // BufferedReader reader = request.getReader();
    // StringBuilder requestBody = new StringBuilder();
    // String line;
    // while ((line = reader.readLine()) != null) {
    // requestBody.append(line);
    // }
    // JSONObject body = null;
    // if (StrUtil.isNotBlank(requestBody.toString())) {
    // body = JSON.parseObject(requestBody.toString());
    // }
    // LoggerHelper.log("body = " + body);
    // // 验证code
    // if (StrUtil.isNotBlank(code)) {
    // String token = GithubOAuthProvider.getAccessTokenByCodeAndUrl(code, url);
    // LoggerHelper.log("token = " + token);
    // session.setAttribute("token", token);
    // if (StrUtil.isBlank(token)) {
    // throw new WTRuntimeException("获取登录Token失败!");
    // }
    // // JDBC 验证用户是否存在
    // JSONObject userInfo = GithubOAuthProvider.getUserInfo(token);
    // LoggerHelper.log("userInfo = ", userInfo);
    // String loginUserName = userInfo.getString("login");
    // String input = StrUtil.format("{}:{}", loginUserName, loginUserName);
    // String encoding = new BASE64Encoder().encode(input.getBytes());
    // SSORequestWrap SSORequestWrap = newWrapRequest(httpServletRequest, encoding, session);
    // httpResponse.sendRedirect(String.valueOf((SSORequestWrap.getRequestURL())));
    // return;
    // } else if (body != null) {
    // // 可以获取body进行验证
    // String username = body.getString("username");
    // LoggerHelper.log("username = " + username);
    // String password = body.getString("password");
    // LoggerHelper.log("password = " + password);
    // if (StrUtil.isNotBlank(username) && StrUtil.isNotBlank(password)) {
    // OpenDjPasswordService service = new OpenDjPasswordService();
    // if (service.authentication(username, password)) {
    // String input = StrUtil.format("{}:{}", username, password);
    // String encoding = new BASE64Encoder().encode(input.getBytes());
    // LoggerHelper.log("encoding = ", encoding);
    // SSORequestWrap SSORequestWrap = newWrapRequest(httpServletRequest, encoding, session);
    // httpResponse.sendRedirect(SSORequestWrap.getRequestURL().toString());
    // return;
    // } else {
    // redirectBasicLogin(httpResponse);
    // }
    // }
    // } else {
    // // 默认登录地址
    // httpResponse.sendRedirect(OAuthConfigConstant.OAUTH2_LOGIN_PAGE);
    // return;
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // throw new WTRuntimeException(e.getMessage());
    // }
    // }
    // }
    // }
}
