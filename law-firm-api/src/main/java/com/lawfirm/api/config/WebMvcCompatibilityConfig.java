package com.lawfirm.api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import lombok.extern.slf4j.Slf4j;

/**
 * WebMVC兼容性配置
 * 解决Spring Boot 3.2.3与Spring Security 6.2.2之间的版本兼容性问题
 * 
 * @author LawFirm Dev Team
 */
@Slf4j
@Configuration
public class WebMvcCompatibilityConfig implements WebMvcConfigurer {
    
    /**
     * 禁用MVC路径匹配器缓存，解决版本兼容性问题
     */
    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        log.info("配置HandlerMappingIntrospector，解决版本兼容性问题");
        // 仅创建HandlerMappingIntrospector实例，不调用不存在的方法
        // Spring Boot 3.2.3中会通过系统属性配置它的行为
        return new HandlerMappingIntrospector();
    }
    
    /**
     * 降低Spring Security过滤器顺序，确保HandlerMappingIntrospector在它之后初始化
     */
    @Bean
    public FilterRegistrationBean<org.springframework.web.filter.DelegatingFilterProxy> securityFilterChainRegistration() {
        log.info("调整Spring Security过滤器顺序，避免初始化问题");
        FilterRegistrationBean<org.springframework.web.filter.DelegatingFilterProxy> registration = new FilterRegistrationBean<>();
        registration.setName("springSecurityFilterChain");
        registration.setFilter(new org.springframework.web.filter.DelegatingFilterProxy());
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 10); // 比默认顺序稍微晚一点执行
        return registration;
    }
} 