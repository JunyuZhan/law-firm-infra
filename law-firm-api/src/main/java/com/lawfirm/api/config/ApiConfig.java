package com.lawfirm.api.config;

import com.lawfirm.common.cache.config.RedisTemplateConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * API层配置类
 * 用于明确导入可能存在冲突的配置类，避免自动扫描时的冲突
 * 
 * 注意：所有模块中的Bean应通过明确的命名方式避免冲突，例如：
 * - 使用@Service("moduleBeanName")进行明确命名
 * - 案例模块: caseDocumentServiceImpl, caseProcessServiceImpl, caseFeeServiceImpl
 * - 文档模块: documentServiceImpl
 * - 财务模块: financeServiceImpl (FeeServiceImpl类)
 * - 工作流模块: coreProcessServiceImpl (ProcessServiceImpl类)
 */
@Configuration
@Import({
    RedisTemplateConfig.class, // 明确导入缓存层的RedisTemplateConfig
    com.lawfirm.core.workflow.config.WorkflowConfig.class, // 明确导入工作流核心模块的WorkflowConfig
    com.lawfirm.finance.service.impl.FeeServiceImpl.class, // 明确导入财务模块的FeeServiceImpl (Bean名称为financeServiceImpl)
    com.lawfirm.document.service.impl.DocumentServiceImpl.class, // 明确导入文档模块的DocumentServiceImpl
    com.lawfirm.core.workflow.service.ProcessServiceImpl.class // 明确导入工作流模块的ProcessServiceImpl
})
public class ApiConfig {
    // 不需要方法体，此类只用于声明导入关系
} 