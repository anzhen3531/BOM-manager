package ext.ziang.oauth;

import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 相关工具
 */
public class CookieUtils {

    public static final String COOKIE_ID = "github_ssoid";
    public static final String BASIC_LOGIN = "basic_ssoid";
    public static final String SSO_AUTH = "sso.auth";

    /**
     * 通过Cookies 获取 SSO token
     *
     * @param request 请求
     * @return {@link String }
     */
    public static String getSSOTokenByCookies(HttpServletRequest request) {
        return getSSOTokenByCookies(request, COOKIE_ID);
    }

    /**
     * 通过Cookies获取SSO token
     *
     * @param request 请求
     * @return {@link String }
     */
    public static String getSSOTokenByCookies(HttpServletRequest request, String cookieId) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Objects.nonNull(cookie) && cookie.getName().contains(cookieId)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 通过Cookies获取SSO-Token
     *
     * @param token 令 牌
     * @return {@link String }
     */
    public static Cookie createSSOTokenByCookie(String token) {
        Cookie cookie = new Cookie(COOKIE_ID, token);
        cookie.setPath("/");
        cookie.setMaxAge(14 * 24 * 60 * 60);
        return cookie;
    }

    /**
     * 通过Cookies获取SSO-Token
     *
     * @param token 令 牌
     * @return {@link String }
     */
    public static Cookie createSSOTokenByCookie(String token, String cookieName) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setPath("/");
        cookie.setMaxAge(14 * 24 * 60 * 60);
        return cookie;
    }

    /**
     * 通过Cookies获取SSO token
     *
     * @param request 请求
     * @param response 响应
     */
    public static void deleteSSOTokenByCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().contains(COOKIE_ID)) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
}
