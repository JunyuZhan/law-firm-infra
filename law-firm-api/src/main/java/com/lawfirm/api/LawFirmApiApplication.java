package com.lawfirm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * API层启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lawfirm"})
public class LawFirmApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(LawFirmApiApplication.class, args);
    }
} 