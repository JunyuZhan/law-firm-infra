package com.lawfirm.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * 空DDL处理器配置
 * 解决启动时缺少ddlApplicationRunner导致的异常问题
 */
@Configuration
@ConditionalOnProperty(name = "spring.sql.init.enabled", havingValue = "false", matchIfMissing = true)
public class NullDdlHandler implements ApplicationListener<ContextRefreshedEvent> {
    
    /**
     * 应用上下文刷新时，尝试拦截Runners的执行
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        System.out.println("应用上下文刷新完成，拦截Runner执行");
        
        try {
            // 通过反射访问SpringApplication.callRunners方法内的runners列表
            // 注意：这是一个黑科技，仅用于解决特定问题
            Class<?> appClass = Class.forName("org.springframework.boot.SpringApplication");
            Field runnersField = appClass.getDeclaredField("runners");
            runnersField.setAccessible(true);
            
            // 尝试获取runners并清空与DDL相关的runner
            Object runners = runnersField.get(null);
            if (runners instanceof List) {
                @SuppressWarnings("unchecked")
                List<?> runnerList = (List<?>) runners;
                
                // 使用迭代器安全删除
                Iterator<?> it = runnerList.iterator();
                while (it.hasNext()) {
                    Object runner = it.next();
                    if (runner != null && runner.toString().contains("ddlApplicationRunner")) {
                        it.remove();
                        System.out.println("已移除ddlApplicationRunner");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("无法拦截Runner执行: " + e.getMessage());
        }
    }
    
    /**
     * 创建一个空的CommandLineRunner作为备用
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "ddlApplicationRunner")
    public CommandLineRunner ddlApplicationRunner() {
        return args -> {
            System.out.println("空DDL Runner执行");
        };
    }
}