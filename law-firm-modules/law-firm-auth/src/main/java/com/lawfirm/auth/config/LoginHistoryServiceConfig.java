package com.lawfirm.auth.config;

import com.lawfirm.model.auth.entity.LoginHistory;
import com.lawfirm.model.auth.service.LoginHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 登录历史服务配置类
 * 用于在数据库服务不可用时提供一个不依赖数据库的LoginHistoryService实现
 */
@Configuration
@Slf4j
public class LoginHistoryServiceConfig {

    /**
     * 当数据库服务禁用时，提供一个不依赖数据库的LoginHistoryService实现
     * 该实现不会真正保存登录历史，只会在日志中记录登录事件
     */
    @Bean(name = "noopLoginHistoryService")
    @Primary
    @ConditionalOnProperty(name = "lawfirm.database.enabled", havingValue = "false")
    public LoginHistoryService noopLoginHistoryService() {
        log.info("数据库服务禁用，创建NoopLoginHistoryService");
        return new NoopLoginHistoryService();
    }

    /**
     * 不依赖数据库的LoginHistoryService实现
     * 用于在数据库服务禁用时避免依赖问题
     */
    @Slf4j
    static class NoopLoginHistoryService implements LoginHistoryService {
        
        @Override
        public boolean saveLoginHistory(Long userId, String username, String ip, String location,
                                      String browser, String os, Integer status, String msg) {
            if (log.isDebugEnabled()) {
                log.debug("模拟保存登录历史: 用户={}, IP={}, 状态={}, 消息={}", username, ip, status, msg);
            }
            return true;
        }

        @Override
        public List<LoginHistory> getLoginHistory(Long userId) {
            if (log.isDebugEnabled()) {
                log.debug("模拟获取用户ID={} 的登录历史", userId);
            }
            return Collections.emptyList();
        }

        @Override
        public LoginHistory getLastLogin(Long userId) {
            if (log.isDebugEnabled()) {
                log.debug("模拟获取用户ID={} 的最后登录记录", userId);
            }
            LoginHistory history = new LoginHistory();
            history.setUserId(userId);
            history.setUsername("admin");
            history.setLoginTime(LocalDateTime.now());
            history.setStatus(0);
            history.setMsg("模拟登录记录");
            return history;
        }
    }
} 