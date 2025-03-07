package com.lawfirm.auth.controller;

import com.lawfirm.common.cache.annotation.RateLimiter;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import com.lawfirm.model.auth.service.AuthService;
import com.lawfirm.model.auth.vo.LoginVO;
import com.wf.captcha.SpecCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RateIntervalUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RedisTemplate<String, String> redisTemplate;
    private final AuthService authService;
    private static final String CAPTCHA_PREFIX = "captcha:";
    private static final long CAPTCHA_EXPIRATION = 5; // 验证码5分钟有效

    /**
     * 获取验证码
     */
    @RateLimiter(rate = 10, rateInterval = 60, rateIntervalUnit = RateIntervalUnit.SECONDS, message = "验证码获取频率超限")
    @GetMapping("/captcha")
    public CommonResult<Map<String, String>> captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String captchaKey = UUID.randomUUID().toString();
        String captchaValue = specCaptcha.text().toLowerCase();

        // 将验证码存入Redis
        redisTemplate.opsForValue().set(
            CAPTCHA_PREFIX + captchaKey,
            captchaValue,
            CAPTCHA_EXPIRATION,
            TimeUnit.MINUTES
        );

        Map<String, String> result = new HashMap<>();
        result.put("captchaKey", captchaKey);
        result.put("captchaImage", specCaptcha.toBase64());

        return CommonResult.success(result);
    }
    
    /**
     * 用户登录
     */
    @RateLimiter(rate = 5, rateInterval = 60, rateIntervalUnit = RateIntervalUnit.SECONDS, message = "登录请求频率超限")
    @PostMapping("/login")
    public CommonResult<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        LoginVO loginVO = authService.login(loginDTO);
        return CommonResult.success(loginVO);
    }
    
    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public CommonResult<Void> logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            authService.logout(userDetails.getUsername());
        }
        return CommonResult.success();
    }
    
    /**
     * 刷新令牌
     */
    @RateLimiter(rate = 30, rateInterval = 60, rateIntervalUnit = RateIntervalUnit.SECONDS, message = "刷新令牌请求频率超限")
    @PostMapping("/refresh")
    public CommonResult<TokenDTO> refreshToken(@RequestBody TokenDTO tokenDTO) {
        TokenDTO newToken = authService.refreshToken(tokenDTO.getRefreshToken());
        return CommonResult.success(newToken);
    }
}