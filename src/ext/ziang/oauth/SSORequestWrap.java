package ext.ziang.oauth;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 请求换行
 *
 * @author anzhen
 * @date 2024/01/02
 */
public class SSORequestWrap extends HttpServletRequestWrapper {

    private final String remoteUser;

    private Principal principal;

    public SSORequestWrap(HttpServletRequest request, String userName) {
        super(request);
        this.remoteUser = userName;
        this.principal = new SSOPrincipal(this.remoteUser);
    }

    @Override
    public String getRemoteUser() {
        return this.remoteUser;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }
}
