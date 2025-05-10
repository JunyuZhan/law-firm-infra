package com.lawfirm.common.util.config;

import com.lawfirm.common.util.SpringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 工具模块自动配置类
 * 
 * 提供各种工具类的自动配置，包括Spring上下文工具等
 */
@Configuration("commonUtilAutoConfiguration")
@ConditionalOnProperty(prefix = "law-firm.common.util", name = "enabled", matchIfMissing = true)
public class UtilAutoConfiguration {

    /**
     * Spring工具类
     * 用于在非Spring管理的类中获取Spring容器中的Bean
     * 
     * @param applicationContext Spring应用上下文
     * @return Spring工具类实例
     */
    @Bean(name = "commonSpringUtils")
    @ConditionalOnMissingBean(SpringUtils.class)
    public SpringUtils springUtils(ApplicationContext applicationContext) {
        SpringUtils springUtils = new SpringUtils();
        springUtils.setApplicationContext(applicationContext);
        return springUtils;
    }
    
    /**
     * ID生成器配置类
     * 
     * @return ID生成器配置
     */
    @Bean(name = "commonIdGeneratorConfig")
    public IdGeneratorConfig idGeneratorConfig() {
        return new IdGeneratorConfig();
    }
} 