package com.lawfirm.core.workflow.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

/**
 * 工作流禁用自动配置类
 * <p>
 * 用于禁用工作流功能的场景
 * </p>
 */
@AutoConfiguration
@Import({
    WorkflowDisableConfig.class
})
@ConditionalOnProperty(prefix = "lawfirm", name = "workflow.enabled", havingValue = "false", matchIfMissing = true)
public class WorkflowDisableAutoConfiguration {
    // 通过导入完成自动配置
} 