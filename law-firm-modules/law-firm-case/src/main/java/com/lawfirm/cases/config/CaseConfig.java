package com.lawfirm.cases.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.lawfirm.cases.mapper")
public class CaseConfig {
} 