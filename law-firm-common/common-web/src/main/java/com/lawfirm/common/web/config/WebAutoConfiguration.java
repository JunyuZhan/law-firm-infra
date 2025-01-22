package com.lawfirm.common.web.config;

import com.lawfirm.common.web.filter.XssFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Web自动配置
 */
@Configuration
@ConditionalOnWebApplication
@Import({
    SwaggerConfig.class,
    WebConfig.class,
    UploadConfig.class
})
public class WebAutoConfiguration {

    /**
     * XSS过滤器
     */
    @Bean
    @ConditionalOnMissingBean
    public XssFilter xssFilter() {
        return new XssFilter();
    }
} 