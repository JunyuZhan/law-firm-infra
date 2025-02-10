package com.lawfirm.staff.client.lawyer.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.staff.client.lawyer.ClientClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientClientFallback extends FeignFallbackConfig implements ClientClient {

    private static final String SERVICE_NAME = "客户服务";
    
    // 实现ClientClient的所有方法
    // 每个方法返回服务降级的默认响应
} 