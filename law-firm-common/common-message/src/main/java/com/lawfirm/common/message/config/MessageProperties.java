package com.lawfirm.common.message.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * 消息配置属性
 */
@Data
@Validated
@ConfigurationProperties(prefix = "message")
public class MessageProperties {

    @Valid
    @NotNull
    private Processing processing = new Processing();

    @Data
    public static class Processing {
        @Valid
        @NotNull
        private Idempotent idempotent = new Idempotent();

        @Valid
        @NotNull
        private RateLimit rateLimit = new RateLimit();

        @Valid
        @NotNull
        private Batch batch = new Batch();

        @Valid
        @NotNull
        private Retry retry = new Retry();
    }

    @Data
    public static class Idempotent {
        private boolean enabled = true;

        @Min(1)
        @Max(720) // 最大30天
        private int expireHours = 24;
    }

    @Data
    public static class RateLimit {
        private boolean enabled = true;

        @Min(1)
        @Max(1000000)
        private long capacity = 1000;

        @Min(1)
        @Max(100000)
        private long rate = 100;
    }

    @Data
    public static class Batch {
        private boolean enabled = true;

        @Min(1)
        @Max(10000)
        private int maxSize = 100;

        @Min(100)
        @Max(60000)
        private int waitTimeout = 1000;
    }

    @Data
    public static class Retry {
        private boolean enabled = true;

        @Min(1)
        @Max(10)
        private int maxAttempts = 3;

        @Min(1000)
        @Max(60000)
        private long initialInterval = 1000;

        @Min(1)
        @Max(10)
        private double multiplier = 2.0;

        @Min(1000)
        @Max(300000)
        private long maxInterval = 10000;
    }
} 