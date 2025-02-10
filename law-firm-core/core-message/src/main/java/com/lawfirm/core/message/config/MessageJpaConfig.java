package com.lawfirm.core.message.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 消息JPA配置
 */
@Configuration
@EntityScan(basePackages = "com.lawfirm.model.base.message.entity")
@EnableJpaRepositories(basePackages = "com.lawfirm.model.base.message.repository")
public class MessageJpaConfig {
} 