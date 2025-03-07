package com.lawfirm.auth.security.details;

import com.lawfirm.model.auth.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 扩展的用户详情接口
 * 提供额外的用户信息访问方法
 */
public interface SecurityUserDetails extends UserDetails {
    
    /**
     * 获取用户实体
     * @return 用户实体
     */
    User getUser();
    
    /**
     * 获取用户权限列表
     * @return 权限列表
     */
    List<String> getPermissions();
} 