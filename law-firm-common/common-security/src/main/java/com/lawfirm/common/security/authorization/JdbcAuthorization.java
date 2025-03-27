package com.lawfirm.common.security.authorization;

import org.springframework.stereotype.Component;
import com.lawfirm.common.security.dao.AuthorizationDao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import java.util.Set;

/**
 * 基于数据库的授权实现类
 * 
 * <p>该实现通过数据库查询用户的权限和角色信息，提供了授权接口的具体实现。
 * 主要依赖于{@link AuthorizationDao}接口获取实际的权限和角色数据。</p>
 * 
 * @author JunyuZhan
 * @since 1.0.0
 */
// 移除@Component注解，通过配置类显式创建bean
// @Component("jdbcAuthorization")
// @ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "true", matchIfMissing = true)
public class JdbcAuthorization implements Authorization {
    
    /**
     * 授权数据访问对象
     */
    private final AuthorizationDao authorizationDao;
    
    /**
     * 构造函数
     * 
     * @param authorizationDao 授权数据访问对象
     */
    public JdbcAuthorization(AuthorizationDao authorizationDao) {
        this.authorizationDao = authorizationDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getPermissions() {
        return authorizationDao.findCurrentUserPermissions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getRoles() {
        return authorizationDao.findCurrentUserRoles();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(String permission) {
        if (permission == null || permission.isEmpty()) {
            return false;
        }
        Set<String> permissions = getPermissions();
        return permissions != null && permissions.contains(permission);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasRole(String role) {
        if (role == null || role.isEmpty()) {
            return false;
        }
        Set<String> roles = getRoles();
        return roles != null && roles.contains(role);
    }
}
