package com.lawfirm.model.auth.service;

import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import com.lawfirm.model.auth.vo.LoginVO;

/**
 * 认证服务接口
 */
public interface AuthService {
    
    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    LoginVO login(LoginDTO loginDTO);
    
    /**
     * 用户登出
     *
     * @param username 用户名
     */
    void logout(String username);
    
    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 新的令牌
     */
    TokenDTO refreshToken(String refreshToken);
    
    /**
     * 验证验证码
     *
     * @param captcha 验证码
     * @param captchaKey 验证码键
     * @return 是否有效
     */
    boolean validateCaptcha(String captcha, String captchaKey);
    
    /**
     * 生成验证码
     *
     * @return 验证码信息（包含验证码key和图片base64编码）
     */
    java.util.Map<String, Object> generateCaptcha();
} 