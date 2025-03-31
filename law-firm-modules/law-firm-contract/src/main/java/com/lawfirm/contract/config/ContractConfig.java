package com.lawfirm.contract.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import com.lawfirm.contract.util.ContractNumberGenerator;

/**
 * 合同模块配置类
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ContractConfig {

    /**
     * 配置合同编号生成器
     * 
     * 内部类已删除，使用util包中的ContractNumberGenerator类
     */
    @Bean("contractNumberGeneratorConfig")
    @ConditionalOnMissingBean(ContractNumberGenerator.class)
    public ContractNumberGenerator contractNumberGeneratorConfig() {
        return new ContractNumberGenerator();
    }
} 