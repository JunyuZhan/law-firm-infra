package com.lawfirm.model.auth.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息视图对象
 * 用于前端获取当前用户信息，适配vue-vben-admin
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
     * 真实姓名
     */
    private String realName;
    
    /**
     * 头像
     */
    private String avatar;
    
    /**
     * 介绍
     */
    private String desc;
    
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
     * 部门ID
     */
    private Long deptId;
    
    /**
     * 部门名称
     */
    private String deptName;
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 性别（0-男，1-女）
     */
    private Integer gender;
    
    /**
     * 用户状态（0-正常，1-禁用）
     */
    private Integer status;
} 