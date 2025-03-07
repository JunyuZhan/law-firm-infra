package com.lawfirm.auth.security.details;

import com.lawfirm.model.auth.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SecurityUser implements SecurityUserDetails {
    private final User user;
    private final List<String> permissions;

    public SecurityUser(User user, List<String> permissions) {
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
        LocalDateTime expireTime = user.getAccountExpireTime();
        return expireTime == null || 
               expireTime.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        LocalDateTime expireTime = user.getPasswordExpireTime();
        return expireTime == null || 
               expireTime.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        // 假设0为正常状态，其他为禁用
        return user.getStatus() != null && user.getStatus() == 0;
    }
} 