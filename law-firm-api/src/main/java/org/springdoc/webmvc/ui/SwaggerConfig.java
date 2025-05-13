package org.springdoc.webmvc.ui;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * 空白的SwaggerConfig类
 * 仅用于解决Knife4j传递依赖问题
 * 
 * 通过条件注解确保此配置不会被激活
 * 
 * @author LawFirm Dev Team
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "springdoc.api-docs.enabled", havingValue = "false", matchIfMissing = false)
public class SwaggerConfig {
    // 空实现，通过设置matchIfMissing=false确保不会被激活
} 