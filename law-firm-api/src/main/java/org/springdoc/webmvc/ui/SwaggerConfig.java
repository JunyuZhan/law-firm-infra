package org.springdoc.webmvc.ui;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;

/**
 * 空白的SwaggerConfig类
 * 仅用于解决Knife4j传递依赖问题
 * 
 * 通过条件注解确保此配置不会被激活
 * 通过高优先级确保在其他配置之前加载
 * 
 * @author LawFirm Dev Team
 */
@Slf4j
@Configuration
@Order(Integer.MIN_VALUE + 100) // 确保高优先级
@ConditionalOnProperty(name = {"springdoc.api-docs.enabled", "knife4j.enable"}, havingValue = "false", matchIfMissing = true)
public class SwaggerConfig {
    
    public SwaggerConfig() {
        log.info("初始化SwaggerConfig（空配置）- 用于解决Knife4j依赖问题");
    }
    
    // 空实现，不含任何Bean定义
} 