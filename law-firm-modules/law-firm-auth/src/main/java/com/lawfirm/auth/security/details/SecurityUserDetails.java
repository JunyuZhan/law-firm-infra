package com.lawfirm.auth.security.details;

import com.lawfirm.model.auth.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 安全用户详情
 */
public class SecurityUserDetails implements UserDetails {
    
    private static final long serialVersionUID = 1L;
    
    @Getter
    private final transient User user;
    
    private final transient List<String> permissions;
    
    public SecurityUserDetails(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }
    
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
    
    /**
     * 获取用户ID
     * 
     * @return 用户ID
     */
    public Long getUserId() {
        return user.getId();
    }
    
    /**
     * 获取租户ID
     * 
     * @return 租户ID
     */
    public Long getTenantId() {
        return user.getTenantId();
    }
}

