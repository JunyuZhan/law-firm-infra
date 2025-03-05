package com.lawfirm.model.auth.dto.auth;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 密码重置DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PasswordResetDTO extends BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
    
    /**
     * 确认新密码
     */
    @NotBlank(message = "确认新密码不能为空")
    private String confirmPassword;
    
    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String verificationCode;
    
    /**
     * 重置类型（email/sms）
     */
    @NotBlank(message = "重置类型不能为空")
    private String resetType;
} 