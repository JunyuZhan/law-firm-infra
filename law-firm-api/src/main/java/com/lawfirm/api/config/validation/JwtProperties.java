package com.lawfirm.api.config.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    
    @NotBlank(message = "JWT密钥不能为空")
    private String secret;
    
    @NotNull(message = "JWT过期时间不能为空")
    @Positive(message = "JWT过期时间必须大于0")
    private Long expiration;
    
    @NotBlank(message = "JWT请求头不能为空")
    private String header;
    
    @NotBlank(message = "JWT令牌前缀不能为空")
    private String tokenPrefix;
} 