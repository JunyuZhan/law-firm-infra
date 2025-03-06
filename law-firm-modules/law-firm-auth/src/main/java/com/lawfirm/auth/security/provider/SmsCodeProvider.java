package com.lawfirm.auth.security.provider;

import com.lawfirm.auth.security.details.SecurityUserDetails;
import com.lawfirm.auth.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsCodeProvider implements AuthenticationProvider {

    private final UserServiceImpl userService;
    private final StringRedisTemplate redisTemplate;
    private static final String SMS_CODE_PREFIX = "sms_code:";
    private static final long SMS_CODE_EXPIRE_TIME = 300; // 5分钟过期

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String mobile = authentication.getName();
        String code = authentication.getCredentials().toString();

        // 验证码校验
        String cacheCode = redisTemplate.opsForValue().get(SMS_CODE_PREFIX + mobile);
        if (cacheCode == null) {
            throw new BadCredentialsException("验证码已过期");
        }

        if (!code.equals(cacheCode)) {
            throw new BadCredentialsException("验证码错误");
        }

        // 验证码使用后立即删除
        redisTemplate.delete(SMS_CODE_PREFIX + mobile);

        // 获取用户信息
        SecurityUserDetails userDetails = (SecurityUserDetails) userService.loadUserByMobile(mobile);
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
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
