package com.lawfirm.staff.client.clerk.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.staff.client.clerk.ScheduleClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduleClientFallback extends FeignFallbackConfig implements ScheduleClient {

    private static final String SERVICE_NAME = "日程服务";

    // 实现ScheduleClient的所有方法
    // 每个方法返回服务降级的默认响应
} 