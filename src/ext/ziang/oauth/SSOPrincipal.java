package ext.ziang.oauth;

import java.security.Principal;

public class SSOPrincipal implements Principal {
    /**
     * 登录用户
     */
    private final String remoteUser;

    SSOPrincipal(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    @Override
    public String getName() {
        return this.remoteUser;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        Principal principal = (Principal)obj;
        if (principal != null) {
            return this.remoteUser.equals(principal.getName());
        } else {
            return false;
        }
    }
}
