package com.lawfirm.api.config;

import com.lawfirm.api.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/error")
            // 排除API文档相关路径
            .excludePathPatterns("/doc.html", "/swagger-ui.html", "/swagger-ui/**", 
                "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**", 
                "/knife4j/**", "/v3/api-docs-ext/**");
    }
} 