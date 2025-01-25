package com.lawfirm.client.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.lawfirm.client.mapper")
public class ClientConfig {
} 