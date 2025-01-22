package com.lawfirm.common.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("律师事务所管理系统API文档")
                        .version("1.0")
                        .description("律师事务所管理系统接口文档")
                        .termsOfService("http://doc.xiaominfo.com")
                        .contact(new Contact()
                                .name("law-firm")
                                .url("https://gitee.com/law-firm")
                                .email("law-firm@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://doc.xiaominfo.com")));
    }
} 