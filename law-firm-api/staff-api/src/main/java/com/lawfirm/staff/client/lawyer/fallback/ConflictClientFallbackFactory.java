package com.lawfirm.staff.client.lawyer.fallback;

import com.lawfirm.staff.client.lawyer.ConflictClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConflictClientFallbackFactory implements FallbackFactory<ConflictClient> {

    private final ConflictClientFallback fallback;

    public ConflictClientFallbackFactory(ConflictClientFallback fallback) {
        this.fallback = fallback;
    }

    @Override
    public ConflictClient create(Throwable cause) {
        log.error("冲突检查服务调用失败", cause);
        return fallback;
    }
} 