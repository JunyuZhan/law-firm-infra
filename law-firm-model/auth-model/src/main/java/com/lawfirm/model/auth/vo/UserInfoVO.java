package com.lawfirm.model.auth.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户认证信息视图对象
 * 用于前端获取当前用户认证信息，适配vue-vben-admin
 */
@Data
public class UserInfoVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 同步时间
     */
    private LocalDateTime lastUpdateTime;
    
    /**
     * 角色列表
     */
    private transient List<String> roles;
    
    /**
     * 角色列表（数组形式）
     */
    private String[] roleList;
    
    /**
     * 权限列表
     */
    private transient List<String> permissions;
    
    /**
     * 权限列表（数组形式）
     */
    private String[] permissionList;
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 用户状态（0-正常，1-禁用）
     */
    private Integer status;
} 