package com.lawfirm.contract.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 合同模块配置类
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ContractConfig {

    /**
     * 配置合同编号生成器
     */
    @Bean
    public ContractNumberGenerator contractNumberGenerator() {
        return new ContractNumberGenerator();
    }
    
    /**
     * 合同编号生成器
     */
    public static class ContractNumberGenerator {
        
        private static final String CONTRACT_PREFIX = "CT";
        
        /**
         * 生成合同编号
         * 格式: CT + 年月日 + 6位序列号
         *
         * @param contractType 合同类型代码
         * @return 合同编号
         */
        public String generateContractNumber(String contractType) {
            // 获取当前时间戳
            long timestamp = System.currentTimeMillis();
            
            // 获取随机序列号
            int sequence = (int) (Math.random() * 900000) + 100000;
            
            // 构建合同编号
            return CONTRACT_PREFIX 
                + contractType 
                + String.format("%tY%<tm%<td", timestamp) 
                + sequence;
        }
    }
} 