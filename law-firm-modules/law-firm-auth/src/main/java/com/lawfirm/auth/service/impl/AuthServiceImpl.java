package com.lawfirm.auth.service.impl;

import com.lawfirm.auth.exception.AuthException;
import com.lawfirm.auth.security.details.SecurityUserDetails;
import com.lawfirm.auth.security.provider.JwtTokenProvider;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import com.lawfirm.model.auth.service.AuthService;
import com.lawfirm.model.auth.vo.LoginVO;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.message.entity.system.SystemMessage;
import com.lawfirm.core.message.service.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现类
 */
@Slf4j
@Service("authServiceImpl")
public class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    
    // 添加审计服务和消息服务
    private final AuditService auditService;
    private final MessageSender messageSender;
    
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            JwtTokenProvider tokenProvider,
            @Qualifier("commonCacheRedisTemplate") RedisTemplate<String, Object> redisTemplate,
            @Qualifier("coreAuditServiceImpl") AuditService auditService,
            @Qualifier("messageSender") MessageSender messageSender) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.redisTemplate = redisTemplate;
        this.auditService = auditService;
        this.messageSender = messageSender;
    }
    
    private static final String CAPTCHA_KEY_PREFIX = "auth:captcha:";
    private static final String TOKEN_KEY_PREFIX = "auth:token:";
    
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 验证验证码
        if (StringUtils.hasText(loginDTO.getCaptchaKey()) && StringUtils.hasText(loginDTO.getCaptcha())) {
            boolean valid = validateCaptcha(loginDTO.getCaptcha(), loginDTO.getCaptchaKey());
            if (!valid) {
                throw new AuthException("验证码错误或已过期");
            }
        }
        
        try {
            // 认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
            
            // 设置认证信息到上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 获取用户详情
            SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
            
            // 生成令牌
            TokenDTO tokenDTO = tokenProvider.createToken(userDetails.getUsername(), userDetails.getAuthorities());
            
            // 存储令牌到Redis
            String tokenKey = TOKEN_KEY_PREFIX + userDetails.getUsername();
            redisTemplate.opsForValue().set(tokenKey, tokenDTO.getAccessToken(), tokenDTO.getExpiresIn(), TimeUnit.SECONDS);
            
            // 构建登录响应
            LoginVO loginVO = new LoginVO();
            loginVO.setUserId(userDetails.getUserId());
            loginVO.setUsername(userDetails.getUsername());
            loginVO.setToken(tokenDTO);
            
            // 记录审计日志
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule("auth");
            auditLog.setDescription("用户登录: " + userDetails.getUsername());
            auditLog.setOperateType(OperateTypeEnum.OTHER);
            auditLog.setBusinessType(BusinessTypeEnum.USER);
            auditLog.setOperatorName(userDetails.getUsername());
            auditLog.setStatus(0); // 正常状态
            auditService.logAsync(auditLog);
            
            log.info("用户 {} 登录成功", userDetails.getUsername());
            return loginVO;
        } catch (AuthenticationException e) {
            log.error("用户 {} 登录失败: {}", loginDTO.getUsername(), e.getMessage());
            
            // 记录失败的审计日志
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule("auth");
            auditLog.setDescription("用户登录失败: " + loginDTO.getUsername());
            auditLog.setOperateType(OperateTypeEnum.OTHER);
            auditLog.setBusinessType(BusinessTypeEnum.USER);
            auditLog.setOperatorName(loginDTO.getUsername());
            auditLog.setStatus(1); // 异常状态
            auditService.logAsync(auditLog);
            
            throw new AuthException("用户名或密码错误");
        }
    }
    
    @Override
    public void logout(String username) {
        // 清除Redis中的令牌
        String tokenKey = TOKEN_KEY_PREFIX + username;
        redisTemplate.delete(tokenKey);
        
        // 清除安全上下文
        SecurityContextHolder.clearContext();
        
        // 记录审计日志
        AuditLogDTO auditLog = new AuditLogDTO();
        auditLog.setModule("auth");
        auditLog.setDescription("用户登出: " + username);
        auditLog.setOperateType(OperateTypeEnum.OTHER);
        auditLog.setBusinessType(BusinessTypeEnum.USER);
        auditLog.setOperatorName(username);
        auditLog.setStatus(0); // 正常状态
        auditService.logAsync(auditLog);
        
        // 可以选择发送系统消息
        SystemMessage message = new SystemMessage();
        message.setTitle("安全提醒");
        message.setContent("您的账号已安全退出系统");
        message.setReceivers(Arrays.asList(username));
        messageSender.send(message);
        
        log.info("用户 {} 登出成功", username);
    }
    
    @Override
    public TokenDTO refreshToken(String refreshToken) {
        if (!StringUtils.hasText(refreshToken)) {
            throw new AuthException("刷新令牌不能为空");
        }
        
        try {
            // 验证并刷新令牌
            TokenDTO tokenDTO = tokenProvider.refreshToken(refreshToken);
            
            // 获取用户名
            String username = tokenProvider.getUsername(refreshToken);
            
            // 更新Redis中的令牌
            String tokenKey = TOKEN_KEY_PREFIX + username;
            redisTemplate.opsForValue().set(tokenKey, tokenDTO.getAccessToken(), tokenDTO.getExpiresIn(), TimeUnit.SECONDS);
            
            // 记录审计日志
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule("auth");
            auditLog.setDescription("刷新令牌: " + username);
            auditLog.setOperateType(OperateTypeEnum.OTHER);
            auditLog.setBusinessType(BusinessTypeEnum.USER);
            auditLog.setOperatorName(username);
            auditLog.setStatus(0); // 正常状态
            auditService.logAsync(auditLog);
            
            log.info("用户 {} 刷新令牌成功", username);
            return tokenDTO;
        } catch (Exception e) {
            log.error("刷新令牌失败: {}", e.getMessage());
            throw new AuthException("刷新令牌失败");
        }
    }
    
    @Override
    public boolean validateCaptcha(String captcha, String captchaKey) {
        if (!StringUtils.hasText(captcha) || !StringUtils.hasText(captchaKey)) {
            return false;
        }
        
        String key = CAPTCHA_KEY_PREFIX + captchaKey;
        String storedCaptcha = (String) redisTemplate.opsForValue().get(key);
        
        if (!StringUtils.hasText(storedCaptcha)) {
            return false;
        }
        
        // 验证成功后删除验证码
        redisTemplate.delete(key);
        
        return captcha.equalsIgnoreCase(storedCaptcha);
    }
}

