package com.lawfirm.common.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("律师事务所管理系统 API")
                        .description("律师事务所管理系统接口文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("lawfirm")
                                .email("support@lawfirm.com")
                                .url("https://www.lawfirm.com")));
    }
} 