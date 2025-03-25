package com.lawfirm.api.config.validation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPoolProperties {
    
    @NotNull(message = "核心线程数不能为空")
    @Min(value = 1, message = "核心线程数必须大于0")
    @Max(value = 100, message = "核心线程数不能超过100")
    private Integer coreSize;
    
    @NotNull(message = "最大线程数不能为空")
    @Min(value = 1, message = "最大线程数必须大于0")
    @Max(value = 200, message = "最大线程数不能超过200")
    private Integer maxSize;
    
    @NotNull(message = "队列容量不能为空")
    @Positive(message = "队列容量必须大于0")
    private Integer queueCapacity;
    
    @NotNull(message = "线程存活时间不能为空")
    @Positive(message = "线程存活时间必须大于0")
    private Integer keepAliveSeconds;
} 