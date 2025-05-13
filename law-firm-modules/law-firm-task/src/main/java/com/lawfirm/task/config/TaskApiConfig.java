package com.lawfirm.task.config;

import com.lawfirm.task.constant.TaskBusinessConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 任务模块API文档配置
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(OpenAPI.class)
@ConditionalOnProperty(prefix = "springdoc.api-docs", name = "enabled", havingValue = "true", matchIfMissing = false)
public class TaskApiConfig {

    /**
     * 任务模块API分组
     */
    @Bean
    public GroupedOpenApi taskGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("任务管理")
                .pathsToMatch(
                    TaskBusinessConstants.Controller.API_PREFIX + "/**", 
                    TaskBusinessConstants.Controller.API_TAG_PREFIX + "/**",
                    TaskBusinessConstants.Controller.API_CASE_TASKS_PREFIX + "/**",
                    TaskBusinessConstants.Controller.API_CLIENT_TASKS_PREFIX + "/**",
                    TaskBusinessConstants.Controller.API_PERSONNEL_TASKS_PREFIX + "/**",
                    TaskBusinessConstants.Controller.API_DEPARTMENT_TASKS_PREFIX + "/**"
                )
                .build();
    }
} 