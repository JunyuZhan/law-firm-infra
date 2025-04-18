package com.lawfirm.common.data.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import lombok.extern.slf4j.Slf4j;

/**
 * MyBatis-Plus通用配置
 * 提供全局共享的拦截器、分页和防止全表更新删除等功能
 * 只有在lawfirm.database.enabled=true时才启用
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@ConditionalOnProperty(name = "lawfirm.database.enabled", havingValue = "true", matchIfMissing = true)
public class MybatisPlusConfig {

    /**
     * 新的分页插件
     * 设置为Primary，确保在有多个MybatisPlusInterceptor时优先使用此Bean
     */
    @Bean(name = "commonMybatisPlusInterceptor")
    @Primary
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        log.info("创建MyBatis-Plus拦截器");
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
} 