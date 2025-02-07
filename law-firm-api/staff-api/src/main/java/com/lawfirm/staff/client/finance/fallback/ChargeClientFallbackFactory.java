package com.lawfirm.staff.client.finance.fallback;

import com.lawfirm.staff.client.finance.ChargeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChargeClientFallbackFactory implements FallbackFactory<ChargeClient> {

    private final ChargeClientFallback fallback;

    public ChargeClientFallbackFactory(ChargeClientFallback fallback) {
        this.fallback = fallback;
    }

    @Override
    public ChargeClient create(Throwable cause) {
        log.error("收费服务调用失败", cause);
        return fallback;
    }
} 