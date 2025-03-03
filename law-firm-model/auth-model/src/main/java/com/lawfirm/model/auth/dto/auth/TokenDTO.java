package com.lawfirm.model.auth.dto.auth;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 令牌信息DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TokenDTO extends BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 访问令牌
     */
    private String accessToken;
    
    /**
     * 刷新令牌
     */
    private String refreshToken;
    
    /**
     * 令牌类型
     */
    private String tokenType;
    
    /**
     * 过期时间（秒）
     */
    private Long expiresIn;
} 