package com.lawfirm.model.auth.dto.auth;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 验证码DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaptchaDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    
    /**
     * 验证码key
     */
    private String captchaKey;
    
    /**
     * 验证码图片（Base64编码）
     */
    private String captchaImage;
    
    /**
     * 过期时间（秒）
     */
    private Integer expiresIn;
} 