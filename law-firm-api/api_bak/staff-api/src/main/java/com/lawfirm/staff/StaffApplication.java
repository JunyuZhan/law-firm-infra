package com.lawfirm.staff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 员工端API启动类
 *
 * @author weidi
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lawfirm"})
@SpringBootApplication(scanBasePackages = {"com.lawfirm"})
@EnableHystrix
@EnableHystrixDashboard
public class StaffApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffApplication.class, args);
    }
} 