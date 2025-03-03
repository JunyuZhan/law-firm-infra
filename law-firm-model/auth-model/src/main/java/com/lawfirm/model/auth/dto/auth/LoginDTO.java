package com.lawfirm.model.auth.dto.auth;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 登录请求DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LoginDTO extends BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    
    /**
     * 验证码
     */
    private String captcha;
    
    /**
     * 验证码key
     */
    private String captchaKey;
} 