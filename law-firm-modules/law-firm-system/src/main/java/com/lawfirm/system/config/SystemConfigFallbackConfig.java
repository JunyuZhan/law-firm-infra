package com.lawfirm.system.config;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.model.system.mapper.SysConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 系统配置模块无数据库模式回退配置
 * 提供在数据库功能禁用情况下的系统配置服务实现
 */
@Slf4j
@Configuration
public class SystemConfigFallbackConfig {

    /**
     * 创建一个内存版的SysConfigMapper代理
     * 当数据库功能禁用时使用
     */
    @Bean
    @Primary
    @ConditionalOnProperty(name = "law-firm.database.enabled", havingValue = "false")
    public SysConfigMapper noopSysConfigMapper() {
        log.info("数据库功能已禁用，使用动态代理创建SysConfigMapper的空实现");
        
        // 使用JDK动态代理创建一个SysConfigMapper接口的代理实现
        return (SysConfigMapper) Proxy.newProxyInstance(
            SysConfigMapper.class.getClassLoader(),
            new Class<?>[] { SysConfigMapper.class },
            new ConfigMapperInvocationHandler()
        );
    }
    
    /**
     * SysConfigMapper接口的动态代理处理器
     * 使用内存Map存储配置信息
     */
    static class ConfigMapperInvocationHandler implements InvocationHandler {
        // 存储Key -> Config的映射
        private final Map<String, SysConfig> configMap = new HashMap<>();
        // 存储ID -> Config的映射
        private final Map<Long, SysConfig> idMap = new HashMap<>();
        // ID生成器
        private long idSequence = 1L;
        
        public ConfigMapperInvocationHandler() {
            // 初始化默认配置
            initDefaultConfigs();
        }
        
        /**
         * 初始化默认配置
         */
        private void initDefaultConfigs() {
            // 系统配置
            addConfig("sys.name", "律师事务所管理系统", "系统名称", "system");
            addConfig("sys.version", "1.0.0", "系统版本", "system");
            addConfig("sys.copyright", "©2025 律师事务所", "版权信息", "system");
            
            // 业务配置
            addConfig("case.auto.number", "true", "案件自动编号", "business");
            addConfig("contract.remind.days", "7", "合同到期提醒天数", "business");
            
            // 界面配置
            addConfig("ui.theme", "light", "界面主题", "ui");
            addConfig("ui.sidebar", "true", "显示侧边栏", "ui");
            
            log.info("初始化了{}个默认配置项", configMap.size());
        }
        
        /**
         * 添加一个配置项
         */
        private void addConfig(String key, String value, String name, String type) {
            SysConfig config = new SysConfig();
            config.setId(idSequence++);
            config.setConfigKey(key);
            config.setConfigValue(value);
            config.setConfigName(name);
            config.setConfigType(type);
            config.setCreateTime(LocalDateTime.now());
            config.setUpdateTime(LocalDateTime.now());
            
            configMap.put(key, config);
            idMap.put(config.getId(), config);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            
            // 接口特定方法处理
            if ("selectByKey".equals(methodName) && args != null && args.length == 1) {
                return configMap.get(args[0]);
            }
            
            if ("selectByGroup".equals(methodName) && args != null && args.length == 1) {
                String group = (String) args[0];
                return configMap.values().stream()
                    .filter(cfg -> Objects.equals(cfg.getConfigType(), group))
                    .toList();
            }
            
            // BaseMapper接口通用方法处理
            if ("insert".equals(methodName) && args != null && args.length == 1) {
                SysConfig entity = (SysConfig) args[0];
                if (entity.getId() == null) {
                    entity.setId(idSequence++);
                }
                configMap.put(entity.getConfigKey(), entity);
                idMap.put(entity.getId(), entity);
                return 1;
            }
            
            if ("selectById".equals(methodName) && args != null && args.length == 1) {
                return idMap.get(args[0]);
            }
            
            if ("updateById".equals(methodName) && args != null && args.length == 1) {
                SysConfig entity = (SysConfig) args[0];
                SysConfig existing = idMap.get(entity.getId());
                if (existing != null) {
                    configMap.remove(existing.getConfigKey());
                    configMap.put(entity.getConfigKey(), entity);
                    idMap.put(entity.getId(), entity);
                    return 1;
                }
                return 0;
            }
            
            if ("deleteById".equals(methodName)) {
                if (args != null && args.length == 1) {
                    Serializable id;
                    if (args[0] instanceof SysConfig) {
                        id = ((SysConfig) args[0]).getId();
                    } else {
                        id = (Serializable) args[0];
                    }
                    
                    SysConfig config = idMap.remove(id);
                    if (config != null) {
                        configMap.remove(config.getConfigKey());
                        return 1;
                    }
                }
                return 0;
            }
            
            if ("selectList".equals(methodName)) {
                return new ArrayList<>(configMap.values());
            }
            
            if ("selectCount".equals(methodName)) {
                return (long) configMap.size();
            }
            
            // 通用的未处理方法
            Class<?> returnType = method.getReturnType();
            
            if (int.class.isAssignableFrom(returnType) || Integer.class.isAssignableFrom(returnType)) {
                return 0;
            } else if (long.class.isAssignableFrom(returnType) || Long.class.isAssignableFrom(returnType)) {
                return 0L;
            } else if (boolean.class.isAssignableFrom(returnType) || Boolean.class.isAssignableFrom(returnType)) {
                return false;
            } else if (Collection.class.isAssignableFrom(returnType)) {
                if (List.class.isAssignableFrom(returnType)) {
                    return Collections.emptyList();
                } else if (Set.class.isAssignableFrom(returnType)) {
                    return Collections.emptySet();
                } else {
                    return Collections.emptyList();
                }
            } else if (Map.class.isAssignableFrom(returnType)) {
                return Collections.emptyMap();
            } else if (void.class.equals(returnType)) {
                return null;
            } else if (IPage.class.isAssignableFrom(returnType) && args != null && args.length > 0 && args[0] instanceof IPage) {
                @SuppressWarnings("unchecked")
                IPage<Object> page = (IPage<Object>) args[0];
                page.setRecords(Collections.emptyList());
                page.setTotal(0);
                return page;
            } else {
                return null;
            }
        }
    }
} 