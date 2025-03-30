package com.lawfirm.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import lombok.extern.slf4j.Slf4j;

/**
 * Knife4j配置类
 * <p>
 * 提供Knife4j文档入口配置
 * </p>
 */
@Slf4j
@Configuration
public class Knife4jConfig implements WebMvcConfigurer {

    /**
     * 添加文档入口视图
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("添加Knife4j文档入口地址映射");
        // 备用入口
        registry.addViewController("/api/docs").setViewName("forward:/doc.html");
        registry.addViewController("/docs").setViewName("forward:/doc.html");
        registry.addViewController("/knife4j").setViewName("forward:/doc.html");
    }
} 