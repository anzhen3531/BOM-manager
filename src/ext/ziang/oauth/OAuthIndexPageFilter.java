package ext.ziang.oauth;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jndi.toolkit.chars.BASE64Encoder;
import wt.util.WTRuntimeException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * OAuth 索引页筛选器
 * ext.ziang.oauth.OAuthIndexPageFilter
 *
 * @author ander
 * @date 2023/12/25
 */
public class OAuthIndexPageFilter implements Filter {
    /**
     * 白名单网址
     */
    public List<String> WHITE_LIST_URLS = new ArrayList<>();
    /**
     * Windchill 命令行免密登录
     */
    public static String COMMON_PATH = "Windchill/servlet/WindchillAuthGW/wt.httpgw.HTTPAuthentication/login";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 配置文件中的拦截器
        WHITE_LIST_URLS.add("/Windchill/wtcore/getWtProperties.jsp");
        WHITE_LIST_URLS.add("/Windchill/servlet/WindchillGW/wt.httpgw.HTTPServer/ping");
        WHITE_LIST_URLS.add("/Windchill/netmarkets/register/");
        WHITE_LIST_URLS.add("/Windchill/infoengine/verifyCredentials.html");
        WHITE_LIST_URLS.add("/Windchill/protocolAuth/");
        WHITE_LIST_URLS.add("/Windchill/servlet/WindchillGW/");
        WHITE_LIST_URLS.add("/Windchill/servlet/ProwtGW");
        WHITE_LIST_URLS.add("/Windchill/servlet/InterSiteJmxProxy");
        WHITE_LIST_URLS.add("/Windchill/servlet/JNLPGeneratorServlet");
        WHITE_LIST_URLS.add("/Windchill/servlet/XML4Cognos");
        WHITE_LIST_URLS.add("/Windchill/wt.properties");
        WHITE_LIST_URLS.add("/netmarkets/login/login.jsp");
        WHITE_LIST_URLS.add("/Windchill/netmarkets/jsp/gwt/login.jsp");
        WHITE_LIST_URLS.add("/Windchill/lib/");
        WHITE_LIST_URLS.add("/Windchill/wt/security/");
        WHITE_LIST_URLS.add(".jar");
        System.out.println("初始化首页拦截器");
    }

    /**
     * DO 过滤器
     *
     * @param request     请求
     * @param response    响应
     * @param filterChain 过滤链
     * @throws IOException      ioexception
     * @throws ServletException Servlet 异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        String auth = (String) session.getAttribute("auth");
        System.out.println("auth = " + auth);
        String remoteUser = httpServletRequest.getRemoteUser();
        System.out.println("username = " + remoteUser);
        String url = String.valueOf(httpServletRequest.getRequestURL());
        System.out.println("url = " + httpServletRequest.getRequestURL());
        String authorization = httpServletRequest.getHeader("Authorization");
        System.out.println("authorization = " + authorization);
        filterChain.doFilter(request, httpResponse);
        if (validateContains(WHITE_LIST_URLS, url) || StrUtil.isNotBlank(remoteUser)) {
            filterChain.doFilter(request, httpResponse);
        } else {
            if (StrUtil.isNotBlank(auth)) {
                RequestWrap requestWrap = newWrapRequest(httpServletRequest, auth, session);
                filterChain.doFilter(requestWrap, httpResponse);
            } else if (StrUtil.isNotBlank(authorization)) {
                RequestWrap requestWrap = newWrapRequest(httpServletRequest, authorization, session);
                filterChain.doFilter(requestWrap, httpResponse);
            } else {
                try {
                    String code = request.getParameter("code");
                    System.out.println("code = " + code);
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
                    System.out.println("body = " + body);
                    // 验证code
                    if (StrUtil.isNotBlank(code)) {
                        String token = GithubOAuthProvider.getAccessTokenByCodeAndUrl(code, url);
                        System.out.println("token = " + token);
                        session.setAttribute("token", token);
                        if (StrUtil.isBlank(token)) {
                            throw new WTRuntimeException("获取登录Token失败!");
                        }
                        // JDBC 验证用户是否存在
                        JSONObject userInfo = GithubOAuthProvider.getUserInfo(token);
                        System.out.println("userInfo = " + userInfo);
                        String loginUserName = userInfo.getString("login");
                        String input = StrUtil.format("{}:{}", loginUserName, loginUserName);
                        String encoding = new BASE64Encoder().encode(input.getBytes());
                        RequestWrap requestWrap = newWrapRequest(httpServletRequest, encoding, session);
                        httpResponse.sendRedirect(String.valueOf((requestWrap.getRequestURL())));
                        return;
                    } else if (body != null) {
                        // 可以获取body进行验证
                        String username = body.getString("username");
                        System.out.println("username = " + username);
                        String password = body.getString("password");
                        System.out.println("password = " + password);
                        if (StrUtil.isNotBlank(username) && StrUtil.isNotBlank(password)) {
                            OpenDjPasswordService service = new OpenDjPasswordService();
                            if (service.authentication(username, password)) {
                                String input = StrUtil.format("{}:{}", username, password);
                                String encoding = new BASE64Encoder().encode(input.getBytes());
                                System.out.println("encoding = " + encoding);
                                RequestWrap requestWrap = newWrapRequest(httpServletRequest, encoding, session);
                                httpResponse.sendRedirect(requestWrap.getRequestURL().toString());
                                return;
                            } else {
                                throw new WTRuntimeException("当前用户账号密码错误！");
                            }
                        }
                    } else {
                        // 默认登录地址
                        httpResponse.sendRedirect("http://win-fv1tfp5mpk5.ziang.com/Windchill/netmarkets/jsp/gwt/login.jsp");
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new WTRuntimeException(e.getMessage());
                }
            }
        }
    }

    /**
     * 验证包含
     *
     * @param whiteListUrls 白名单网址
     * @param url           网址
     * @return boolean
     */
    private boolean validateContains(List<String> whiteListUrls, String url) {
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
        System.out.println("auth = " + auth);
        if (auth.contains("Basic ")) {
            auth = auth.replace("Basic ", "");
        }
        String credentials = new String(Base64.getDecoder().decode(auth));
        return credentials.split(":", 2);
    }

    /**
     * 新包装请求
     *
     * @param request  请求
     * @param encoding 编码
     * @param session  会期
     * @return {@link RequestWrap}
     */
    private RequestWrap newWrapRequest(HttpServletRequest request, String encoding, HttpSession session) {
        RequestWrap newRequest = new RequestWrap(request);
        if (!encoding.contains("Basic ")) {
            encoding = "Basic " + encoding;
        }
        newRequest.addHeader("Authorization", encoding);
        String[] usernamepsw = convertAuthHeader(encoding);
        newRequest.setRemoteUser(usernamepsw[0]);
        session.setAttribute("auth", encoding);
        return newRequest;
    }

    @Override
    public void destroy() {
        System.out.println("销毁首页拦截器");
    }

}
