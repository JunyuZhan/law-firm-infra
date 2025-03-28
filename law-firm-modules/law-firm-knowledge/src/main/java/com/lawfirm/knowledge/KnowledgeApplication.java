package com.lawfirm.knowledge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 知识管理模块启动类
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableConfigurationProperties
@MapperScan({"com.lawfirm.model.knowledge.mapper"})
public class KnowledgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgeApplication.class, args);
    }
} 