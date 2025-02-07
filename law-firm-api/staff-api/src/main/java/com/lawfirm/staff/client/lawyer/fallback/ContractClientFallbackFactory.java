package com.lawfirm.staff.client.lawyer.fallback;

import com.lawfirm.staff.client.lawyer.ContractClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContractClientFallbackFactory implements FallbackFactory<ContractClient> {

    private final ContractClientFallback fallback;

    public ContractClientFallbackFactory(ContractClientFallback fallback) {
        this.fallback = fallback;
    }

    @Override
    public ContractClient create(Throwable cause) {
        log.error("合同服务调用失败", cause);
        return fallback;
    }
} 