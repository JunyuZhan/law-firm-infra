package com.lawfirm.api.config;

import com.lawfirm.common.data.config.RedisConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * API层配置类
 * 用于明确导入可能存在冲突的配置类，避免自动扫描时的冲突
 */
@Configuration
@Import({
    RedisConfig.class, // 明确导入数据层的RedisConfig
    com.lawfirm.core.workflow.config.WorkflowConfig.class, // 明确导入工作流核心模块的WorkflowConfig
    com.lawfirm.finance.service.impl.FeeServiceImpl.class, // 明确导入财务模块的FeeServiceImpl
    com.lawfirm.document.service.impl.DocumentServiceImpl.class, // 明确导入文档模块的DocumentServiceImpl
    com.lawfirm.core.workflow.service.ProcessServiceImpl.class // 明确导入工作流模块的ProcessServiceImpl
})
public class ApiConfig {
    // 不需要方法体，此类只用于声明导入关系
} 