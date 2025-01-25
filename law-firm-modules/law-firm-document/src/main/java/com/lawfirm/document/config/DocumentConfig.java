package com.lawfirm.document.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@MapperScan("com.lawfirm.document.mapper")
@PropertySource("classpath:document.properties")
public class DocumentConfig {
    // 配置类暂时为空，后续可以添加其他配置
} 