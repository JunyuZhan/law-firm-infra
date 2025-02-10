package com.lawfirm.finance.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI financeOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("律师事务所财务管理系统 API")
                .description("提供收费记录、支出记录、发票管理等财务相关功能的API接口")
                .version("1.0.0")
                .contact(new Contact()
                    .name("技术支持团队")
                    .email("support@lawfirm.com")
                    .url("https://www.lawfirm.com"))
                .license(new License()
                    .name("私有软件许可")
                    .url("https://www.lawfirm.com/licenses")));
    }
} 