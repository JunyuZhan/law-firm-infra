package com.lawfirm.model.auth.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
     * 用户真实姓名
     */
    private String realName;
    
    /**
     * 用户头像
     */
    private String avatar;
    
    /**
     * 用户描述
     */
    private String desc;
    
    /**
     * 同步时间
     */
    private LocalDateTime lastUpdateTime;
    
    /**
     * 角色列表 - vue-vben-admin格式
     * 格式: [{"roleName": "管理员", "value": "admin"}]
     */
    private transient List<Map<String, String>> roles;
    
    /**
     * 角色编码列表
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
    
    /**
     * 令牌信息
     */
    private String token;
    
    /**
     * 刷新令牌
     */
    private String refreshToken;
    
    /**
     * 主题配置
     */
    private String themeConfig;
} 