package com.lawfirm.contract;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 合同管理模块启动类
 */
@SpringBootApplication
@MapperScan("com.lawfirm.contract.mapper")
public class ContractApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ContractApplication.class, args);
    }
} 