package com.lawfirm.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 资产管理模块启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AssetApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AssetApplication.class, args);
    }
}
