package com.lawfirm.api.config.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    
    @NotBlank(message = "应用名称不能为空")
    private String name;
    
    @NotBlank(message = "应用版本不能为空")
    private String version;
    
    private String description;
} 