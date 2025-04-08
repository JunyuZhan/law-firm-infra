package com.lawfirm.contract.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.beans.factory.annotation.Value;

import com.lawfirm.contract.util.ContractNumberGenerator;

/**
 * 合同模块配置类
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ContractConfig {

    /**
     * 合同默认过期天数
     */
    @Value("${contract.default-expire-days:365}")
    private int defaultExpireDays;

    /**
     * 合同编号前缀
     */
    @Value("${contract.number-prefix:CONTRACT}")
    private String numberPrefix;

    /**
     * 配置合同编号生成器
     */
    @Bean("contractNumberGeneratorConfig")
    @ConditionalOnMissingBean(ContractNumberGenerator.class)
    public ContractNumberGenerator contractNumberGeneratorConfig() {
        return new ContractNumberGenerator();
    }
} 