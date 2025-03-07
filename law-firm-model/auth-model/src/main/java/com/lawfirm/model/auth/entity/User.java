package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lawfirm.model.auth.enums.GenderEnum;
import com.lawfirm.model.auth.enums.UserTypeEnum;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户实体
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
     * 昵称
     */
    @TableField("nickname")
    private String nickname;
    
    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;
    
    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;
    
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
     * 性别
     */
    @TableField("gender")
    private GenderEnum gender;
    
    /**
     * 状态（0-正常，1-禁用）
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 职位ID
     */
    @TableField("position_id")
    private Long positionId;
    
    /**
     * 用户类型
     */
    @TableField("user_type")
    private UserTypeEnum userType;
    
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
} 