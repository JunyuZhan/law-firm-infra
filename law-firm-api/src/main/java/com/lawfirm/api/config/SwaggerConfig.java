package com.lawfirm.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger API文档配置
 * 通过springdoc.api-docs.enabled属性控制是否启用
 */
@Configuration
@ConditionalOnProperty(name = "springdoc.api-docs.enabled", havingValue = "true", matchIfMissing = false)
public class SwaggerConfig {

    @Value("${app.name:法律事务所管理系统}")
    private String appName;
    
    @Value("${app.version:1.0.0}")
    private String appVersion;
    
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(appName + " API文档")
                        .description("法律事务所管理系统API接口文档")
                        .version(appVersion)
                        .contact(new Contact()
                                .name("开发团队")
                                .email("dev@lawfirm.com")));
    }
} 