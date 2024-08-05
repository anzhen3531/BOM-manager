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

    /**
     * 头
     */
    private Map headers;

    /**
     * 标头名称
     */
    private ArrayList headerNames;

    /**
     * 远程用户
     */
    private String remoteUser;

    /**
     * 用户
     */
    private Principal principal;

    /**
     * 
     * @param request
     * @param userName
     */
    public SSORequestWrap(HttpServletRequest request, String userName) {
        super(request);
        headerNames = new ArrayList();
        headers = new HashMap();
        this.remoteUser = userName;
        this.principal = new SSOPrincipal(this.remoteUser);
        Enumeration enumeration = request.getHeaderNames();
        if (enumeration != null)
            do {
                if (!enumeration.hasMoreElements())
                    break;
                String s = (String) enumeration.nextElement();
                headerNames.add(s);
                Enumeration enumeration1 = request.getHeaders(s);
                if (enumeration1 != null) {
                    ArrayList arraylist = new ArrayList();
                    headers.put(s.toLowerCase(), arraylist);
                    for (; enumeration1.hasMoreElements(); arraylist.add(enumeration1.nextElement()))
                        ;
                    arraylist.trimToSize();
                }
            } while (true);
        headerNames.trimToSize();
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    @Override
    public String getRemoteUser() {
        return remoteUser;
    }

    public void addHeader(String name, String value) {
        headerNames.add(name);
        headers.put(name, value);
    }

    @Override
    public String getHeader(String s) {
        if (headers == null)
            return null;
        Collection collection = (Collection)headers.get(s == null ? null : s.toLowerCase());
        if (collection == null)
            return null;
        if (collection.isEmpty())
            return null;
        else
            return (String)collection.iterator().next();
    }

    @Override
    public Enumeration getHeaderNames() {
        if (headerNames == null)
            return getEmptyStringEnumeration();
        else
            return Collections.enumeration(headerNames);
    }

    /**
     * 获取标头
     *
     * @param s s
     * @return {@link Enumeration}
     */
    @Override
    public Enumeration getHeaders(String s) {
        if (headers == null)
            return getEmptyStringEnumeration();
        Collection collection = (Collection)headers.get(s == null ? null : s.toLowerCase());
        if (collection == null)
            return getEmptyStringEnumeration();
        else
            return Collections.enumeration(collection);
    }

    /**
     * 获取空字符串枚举
     *
     * @return {@link Enumeration}
     */
    private static Enumeration getEmptyStringEnumeration() {
        java.util.Set set = Collections.emptySet();
        return Collections.enumeration(set);
    }

}
