package com.lawfirm.common.security.authorization;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * 授权接口的空实现
 * 
 * <p>当存储功能被禁用时使用此实现，所有权限和角色相关操作都返回默认值或空结果。</p>
 * 
 * @author JunyuZhan
 * @since 1.0.0
 */
@Component("jdbcAuthorization")
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "false", matchIfMissing = false)
public class NoOpAuthorization implements Authorization {

    /**
     * {@inheritDoc}
     * 
     * <p>始终返回空集合</p>
     */
    @Override
    public Set<String> getPermissions() {
        return Collections.emptySet();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>始终返回空集合</p>
     */
    @Override
    public Set<String> getRoles() {
        return Collections.emptySet();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>始终返回false，表示没有任何权限</p>
     */
    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>始终返回false，表示没有任何角色</p>
     */
    @Override
    public boolean hasRole(String role) {
        return false;
    }
} 