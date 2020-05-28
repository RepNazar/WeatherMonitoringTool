package ua.Nazar.Rep.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, MAIN_ADMIN;

    /**
     * @return Role
     */
    @Override
    public String getAuthority() {
        return name();
    }
}
