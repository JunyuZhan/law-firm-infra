package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户实体 - 仅包含认证授权相关的字段
 * 不包含人员和组织相关字段，这些应该放在personnel-model和organization-model中
 * 通过employeeId与personnel-model中的Employee实体建立关联
 */
@Data
@TableName("auth_user")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户名
     */
    @TableField("username")
    private String username;
    
    /**
     * 密码
     */
    @JsonIgnore
    @TableField("password")
    private String password;
    
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    
    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;
    
    /**
     * 状态（0-正常，1-禁用）
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;
    
    /**
     * 最后登录IP
     */
    @TableField("last_login_ip")
    private String lastLoginIp;
    
    /**
     * 账号过期时间
     */
    @TableField("account_expire_time")
    private LocalDateTime accountExpireTime;
    
    /**
     * 密码过期时间
     */
    @TableField("password_expire_time")
    private LocalDateTime passwordExpireTime;
    
    /**
     * 关联的员工ID
     * 建立与personnel-model中Employee实体的一对一关联
     * 通过该字段实现认证模块与人事模块的松耦合
     */
    @TableField("employee_id")
    private Long employeeId;
    
    /**
     * 获取用户是否启用（兼容性方法，status=0表示启用）
     */
    public Boolean getEnabled() {
        return status != null && status == 0;
    }
    
    /**
     * 设置用户是否启用（兼容性方法，true设置status=0，false设置status=1）
     */
    public void setEnabled(Boolean enabled) {
        this.status = enabled ? 0 : 1;
    }
    
    /**
     * 判断账号是否未过期
     */
    public boolean isAccountNonExpired() {
        return accountExpireTime == null || accountExpireTime.isAfter(LocalDateTime.now());
    }
    
    /**
     * 判断账号是否未锁定
     */
    public boolean isAccountNonLocked() {
        // 状态为0表示正常，非0表示锁定
        return status != null && status == 0;
    }
    
    /**
     * 判断凭证是否未过期
     */
    public boolean isCredentialsNonExpired() {
        return passwordExpireTime == null || passwordExpireTime.isAfter(LocalDateTime.now());
    }
} 