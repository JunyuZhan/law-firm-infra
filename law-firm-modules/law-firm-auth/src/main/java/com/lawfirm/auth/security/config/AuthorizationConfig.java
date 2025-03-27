package com.lawfirm.auth.security.config;

import com.lawfirm.common.security.authorization.Authorization;
import com.lawfirm.common.security.authorization.JdbcAuthorization;
import com.lawfirm.common.security.dao.AuthorizationDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 授权配置类
 */
@Configuration
public class AuthorizationConfig {

    /**
     * 创建基于数据库的授权实现
     *
     * @param authorizationDao 授权数据访问对象
     * @return 数据库授权实现
     */
    @Bean(name = "jdbcAuthorization")
    @Primary
    public Authorization jdbcAuthorization(AuthorizationDao authorizationDao) {
        return new JdbcAuthorization(authorizationDao);
    }
} 