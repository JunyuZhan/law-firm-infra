package com.lawfirm.api.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.flywaydb.core.api.FlywayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Flyway数据库迁移统一配置管理
 * 
 * 此配置类负责：
 * 1. 按照模块间依赖关系，有序执行迁移脚本
 * 2. 统一管理所有模块的Flyway配置
 * 3. 解决模块间的迁移脚本冲突和依赖问题
 * 
 * 版本号分配策略：
 * - API模块：V0000-V0999
 * - 系统模块：V1000-V1999
 * - 认证模块：V2000-V2999
 * - 人员模块：V3000-V3999
 * - 客户模块：V4000-V4999
 * - 文档模块：V5000-V5999
 * - 案件模块：V6000-V6999
 * - 合同模块：V7000-V7999
 * - 财务模块：V8000-V8999
 * - 知识库模块：V9000-V9899
 * - 跨模块约束：V9900-V9999
 * - 归档模块：V11000-V11999
 * - 任务模块：V12000-V12999
 * - 日程模块：V13000-V13999
 * - 分析模块：V16000-V16999
 */
@Slf4j
@Configuration
@EnableRetry
@ConditionalOnExpression("${spring.flyway.enabled:true} && '${spring.profiles.active}' != 'nodb'")
public class FlywayConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment environment;

    /**
     * 获取Flyway配置属性
     */
    private Map<String, String> getFlywayProperties() {
        Map<String, String> props = new HashMap<>();
        props.put("baseline-on-migrate", environment.getProperty("spring.flyway.baseline-on-migrate", "true"));
        props.put("out-of-order", environment.getProperty("spring.flyway.out-of-order", "true"));
        props.put("validate-on-migrate", environment.getProperty("spring.flyway.validate-on-migrate", "true"));
        props.put("table", environment.getProperty("spring.flyway.table", "flyway_schema_history"));
        props.put("ignore-migration-patterns", environment.getProperty("spring.flyway.ignore-migration-patterns", "*:missing"));
        props.put("schema", environment.getProperty("spring.flyway.placeholders.schema", "law_firm"));
        return props;
    }

    /**
     * 验证数据源配置
     */
    private void validateDataSource(DataSource dataSource) {
        if (dataSource == null) {
            throw new FlywayException("数据源不能为空");
        }
        
        if (dataSource instanceof HikariDataSource) {
            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
            String jdbcUrl = hikariDataSource.getJdbcUrl();
            String driverClassName = hikariDataSource.getDriverClassName();
            
            if (jdbcUrl == null || jdbcUrl.isEmpty()) {
                throw new FlywayException("数据源JDBC URL不能为空");
            }
            if (driverClassName == null || driverClassName.isEmpty()) {
                throw new FlywayException("数据源驱动类名不能为空");
            }
            
            log.info("数据源配置验证通过: URL={}, Driver={}", jdbcUrl, driverClassName);
        } else {
            log.warn("数据源类型不是HikariDataSource: {}", dataSource.getClass().getName());
        }
    }

    /**
     * 自定义Flyway实例，确保按照正确顺序执行迁移脚本
     */
    @Bean
    @Primary
    @Retryable(value = FlywayException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Flyway flyway() {
        log.info("开始初始化Flyway迁移配置...");
        
        try {
            // 验证数据源配置
            validateDataSource(dataSource);
        
        ClassicConfiguration configuration = new ClassicConfiguration();
        
        // 使用注入的数据源
        configuration.setDataSource(dataSource);
        
            // 获取配置属性
            Map<String, String> props = getFlywayProperties();
            log.debug("Flyway配置属性: {}", props);
            
            // 应用配置
            configuration.setBaselineOnMigrate(Boolean.parseBoolean(props.get("baseline-on-migrate")));
            configuration.setOutOfOrder(Boolean.parseBoolean(props.get("out-of-order")));
            configuration.setValidateOnMigrate(Boolean.parseBoolean(props.get("validate-on-migrate")));
            configuration.setTable(props.get("table"));
        
        // 执行顺序由数组顺序决定
        List<String> orderedModules = Arrays.asList(
            // 阶段1: 基础设施脚本
            "classpath:db/migration",                                // API模块基础表
            
            // 阶段2: 系统基础模块
            "classpath:com/lawfirm/system/db/migration",            // 系统模块
            "classpath:com/lawfirm/auth/db/migration",              // 认证模块
            
            // 阶段3: 基础业务模块
            "classpath:com/lawfirm/personnel/db/migration",         // 人员模块
            "classpath:com/lawfirm/client/db/migration",            // 客户模块
            
            // 阶段4: 核心业务模块
            "classpath:com/lawfirm/cases/db/migration",             // 案件模块
            "classpath:com/lawfirm/contract/db/migration",          // 合同模块
            "classpath:com/lawfirm/document/db/migration",          // 文档模块
            "classpath:com/lawfirm/finance/db/migration",           // 财务模块
            
            // 阶段5: 辅助业务模块
            "classpath:com/lawfirm/knowledge/db/migration",         // 知识库模块
            "classpath:com/lawfirm/schedule/db/migration",          // 日程模块
            "classpath:com/lawfirm/task/db/migration",              // 任务模块
            "classpath:com/lawfirm/archive/db/migration",           // 档案模块
            "classpath:com/lawfirm/analysis/db/migration"           // 分析模块
        );
            
            log.info("配置迁移脚本位置，共 {} 个模块", orderedModules.size());
        
        // 环境相关配置
            String ignoreMigrationPatterns = props.get("ignore-migration-patterns");
        if (ignoreMigrationPatterns != null && !ignoreMigrationPatterns.isEmpty()) {
            configuration.setIgnoreMigrationPatterns(ignoreMigrationPatterns.split(","));
                log.debug("设置忽略迁移模式: {}", ignoreMigrationPatterns);
        }
        
        // 设置检索和执行位置
        List<Location> locations = new ArrayList<>();
        for (String location : orderedModules) {
            locations.add(new Location(location));
                log.debug("添加迁移脚本位置: {}", location);
        }
        configuration.setLocations(locations.toArray(new Location[0]));
        
        // 配置SQL迁移文件分隔符
        configuration.setSqlMigrationSeparator("__");
        
        // 脚本编码
        configuration.setEncoding(StandardCharsets.UTF_8);
        
        // 允许占位符替换
        configuration.setPlaceholderReplacement(true);
            
            // 设置占位符
            String schema = props.get("schema");
            configuration.setPlaceholders(java.util.Collections.singletonMap("schema", schema));
            log.debug("设置数据库schema占位符: {}", schema);
            
            // 验证配置
            validateConfiguration(configuration);
        
        log.info("Flyway迁移配置完成，将按照模块依赖顺序执行迁移脚本");
        return new Flyway(configuration);
            
        } catch (Exception e) {
            log.error("Flyway配置初始化失败", e);
            throw new FlywayException("Flyway配置初始化失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 验证Flyway配置
     */
    private void validateConfiguration(ClassicConfiguration configuration) {
        if (configuration.getDataSource() == null) {
            throw new FlywayException("数据源配置不能为空");
        }
        if (configuration.getLocations().length == 0) {
            throw new FlywayException("至少需要配置一个迁移脚本位置");
        }
        log.info("Flyway配置验证通过");
    }
    
    /**
     * 创建Flyway迁移初始化器
     */
    @Bean
    @Primary
    public FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        log.info("创建Flyway迁移初始化器");
        return new FlywayMigrationInitializer(flyway, null);
    }
} 