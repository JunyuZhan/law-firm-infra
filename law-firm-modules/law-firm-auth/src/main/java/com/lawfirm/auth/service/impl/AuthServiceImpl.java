package com.lawfirm.auth.service.impl;

import com.lawfirm.auth.security.token.JwtTokenProvider;
import com.lawfirm.auth.security.token.TokenStore;
import com.lawfirm.auth.service.AuthService;
import com.lawfirm.auth.service.UserService;
import com.lawfirm.common.exception.BusinessException;
import com.lawfirm.common.utils.SecurityUtils;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import com.lawfirm.model.auth.entity.User;
import com.lawfirm.model.auth.vo.LoginVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 认证服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenStore tokenStore;
    private final UserService userService;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-expiration:86400}")
    private Long refreshTokenExpiration;

    private static final String CAPTCHA_PREFIX = "captcha:";

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 1. 验证验证码
        if (StringUtils.isNotBlank(loginDTO.getCaptcha()) && StringUtils.isNotBlank(loginDTO.getCaptchaKey())) {
            if (!validateCaptcha(loginDTO.getCaptcha(), loginDTO.getCaptchaKey())) {
                throw new BusinessException("验证码错误或已过期");
            }
        }

        try {
            // 2. 执行认证
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            // 3. 认证成功，设置认证信息到上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 4. 生成访问令牌
            String accessToken = jwtTokenProvider.createToken(authentication);

            // 5. 生成刷新令牌
            String refreshToken = UUID.randomUUID().toString();
            tokenStore.storeRefreshToken(loginDTO.getUsername(), refreshToken, refreshTokenExpiration, TimeUnit.SECONDS);

            // 6. 构建令牌DTO
            TokenDTO tokenDTO = new TokenDTO()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setTokenType("Bearer")
                .setExpiresIn(accessTokenExpiration);

            // 7. 获取用户信息
            User user = userService.getByUsername(loginDTO.getUsername());
            
            // 8. 构建登录响应VO
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(role -> role.substring(5))
                .collect(Collectors.toList());
                
            List<String> permissions = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> !auth.startsWith("ROLE_"))
                .collect(Collectors.toList());

            LoginVO loginVO = new LoginVO()
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setRealName(user.getRealName())
                .setAvatar(user.getAvatar())
                .setMobile(user.getMobile())
                .setEmail(user.getEmail())
                .setDeptId(user.getDeptId())
                .setDeptName(user.getDeptName())
                .setRoles(roles)
                .setPermissions(permissions)
                .setToken(tokenDTO);

            return loginVO;
        } catch (AuthenticationException e) {
            log.error("用户登录失败: {}", e.getMessage());
            throw new BusinessException("用户名或密码错误");
        }
    }

    @Override
    public void logout(String username) {
        // 1. 清除访问令牌
        tokenStore.removeToken(username);
        
        // 2. 清除刷新令牌
        tokenStore.removeRefreshToken(username);
        
        // 3. 清除安全上下文
        SecurityContextHolder.clearContext();
    }

    @Override
    public TokenDTO refreshToken(String refreshToken) {
        // 1. 验证刷新令牌
        String username = tokenStore.getUsernameByRefreshToken(refreshToken);
        if (StringUtils.isBlank(username)) {
            throw new BusinessException("刷新令牌无效或已过期");
        }
        
        // 2. 加载用户信息
        UserDetails userDetails = userService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 3. 创建新的认证对象
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        
        // 4. 生成新的访问令牌
        String accessToken = jwtTokenProvider.createToken(authentication);
        
        // 5. 生成新的刷新令牌
        String newRefreshToken = UUID.randomUUID().toString();
        tokenStore.storeRefreshToken(username, newRefreshToken, refreshTokenExpiration, TimeUnit.SECONDS);
        
        // 6. 移除旧的刷新令牌
        tokenStore.removeRefreshToken(username, refreshToken);
        
        // 7. 构建令牌DTO
        return new TokenDTO()
            .setAccessToken(accessToken)
            .setRefreshToken(newRefreshToken)
            .setTokenType("Bearer")
            .setExpiresIn(accessTokenExpiration);
    }

    @Override
    public boolean validateCaptcha(String captcha, String captchaKey) {
        if (StringUtils.isBlank(captcha) || StringUtils.isBlank(captchaKey)) {
            return false;
        }
        
        String key = CAPTCHA_PREFIX + captchaKey;
        String value = redisTemplate.opsForValue().get(key);
        
        if (StringUtils.isBlank(value)) {
            return false;
        }
        
        // 验证码使用后立即删除
        redisTemplate.delete(key);
        
        return captcha.equalsIgnoreCase(value);
    }
} 