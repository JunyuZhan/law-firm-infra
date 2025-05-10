package com.lawfirm.common.web.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import jakarta.servlet.MultipartConfigElement;

/**
 * 文件上传配置
 */
@Configuration("commonUploadConfig")
public class UploadConfig {

    /**
     * 文件上传配置
     */
    @Bean(name = "commonMultipartConfigElement")
    public MultipartConfigElement commonMultipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(10));
        // 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(20));
        return factory.createMultipartConfig();
    }
} 