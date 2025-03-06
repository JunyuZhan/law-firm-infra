package com.lawfirm.auth.security.provider;

import com.lawfirm.auth.security.details.SecurityUserDetails;
import com.lawfirm.auth.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LdapProvider implements AuthenticationProvider {

    private final LdapTemplate ldapTemplate;
    private final UserServiceImpl userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            // LDAP认证
            Filter filter = new EqualsFilter("uid", username);
            boolean authenticated = ldapTemplate.authenticate("", filter.encode(), password);

            if (!authenticated) {
                throw new BadCredentialsException("LDAP认证失败");
            }

            // 获取用户信息
            SecurityUserDetails userDetails = (SecurityUserDetails) userService.loadUserByUsername(username);
            if (userDetails == null) {
                throw new BadCredentialsException("用户不存在");
            }

            if (!userDetails.isEnabled()) {
                throw new BadCredentialsException("账户已被禁用");
            }

            return new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
        } catch (Exception e) {
            throw new BadCredentialsException("LDAP认证失败: " + e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
