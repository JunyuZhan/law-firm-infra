package com.lawfirm.knowledge.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 知识管理模块资源配置
 */
@Slf4j
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Autowired
    private KnowledgeProperties knowledgeProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置知识文档静态资源访问
        String basePath = knowledgeProperties.getStorage().getBasePath();
        registry.addResourceHandler("/knowledge/files/**")
                .addResourceLocations("file:" + basePath + "/");
        log.info("配置知识文档静态资源访问路径: /knowledge/files/** -> {}", basePath);
        
        // 配置知识文档临时资源访问
        String tempPath = knowledgeProperties.getStorage().getTempPath();
        registry.addResourceHandler("/knowledge/temp/**")
                .addResourceLocations("file:" + tempPath + "/");
        log.info("配置知识文档临时资源访问路径: /knowledge/temp/** -> {}", tempPath);
    }
} 