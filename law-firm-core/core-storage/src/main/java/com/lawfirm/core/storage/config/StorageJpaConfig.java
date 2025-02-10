package com.lawfirm.core.storage.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 存储模块JPA配置
 */
@Configuration
@EntityScan(basePackages = "com.lawfirm.model.base.storage.entity")
@EnableJpaRepositories(basePackages = "com.lawfirm.model.base.storage.repository")
public class StorageJpaConfig {
} 