package com.lawfirm.staff.client.lawyer.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.staff.client.lawyer.CaseMaterialClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CaseMaterialClientFallback extends FeignFallbackConfig implements CaseMaterialClient {

    private static final String SERVICE_NAME = "案件材料服务";
    
    // 实现CaseMaterialClient的所有方法
    // 每个方法返回服务降级的默认响应
} 