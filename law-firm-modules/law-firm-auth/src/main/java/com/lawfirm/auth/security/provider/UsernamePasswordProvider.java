package com.lawfirm.auth.security.provider;

import com.lawfirm.auth.security.details.SecurityUserDetails;
import com.lawfirm.auth.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernamePasswordProvider implements AuthenticationProvider {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        SecurityUserDetails userDetails = (SecurityUserDetails) userService.loadUserByUsername(username);
        
        if (userDetails == null) {
            throw new BadCredentialsException("用户不存在");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }

        if (!userDetails.isEnabled()) {
            throw new BadCredentialsException("账户已被禁用");
        }

        if (!userDetails.isAccountNonLocked()) {
            throw new BadCredentialsException("账户已被锁定");
        }

        if (!userDetails.isAccountNonExpired()) {
            throw new BadCredentialsException("账户已过期");
        }

        if (!userDetails.isCredentialsNonExpired()) {
            throw new BadCredentialsException("凭证已过期");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
