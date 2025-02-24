package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户实体
 */
@Data
@Entity
@Table(name = "auth_user")
@TableName("auth_user")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends TenantEntity {
    
    /**
     * 用户名
     */
    @Column(name = "username", nullable = false, length = 50)
    @TableField("username")
    private String username;
    
    /**
     * 密码
     */
    @Column(name = "password", nullable = false, length = 100)
    @TableField("password")
    private String password;
    
    /**
     * 昵称
     */
    @Column(name = "nickname", length = 50)
    @TableField("nickname")
    private String nickname;
    
    /**
     * 真实姓名
     */
    @Column(name = "real_name", length = 50)
    @TableField("real_name")
    private String realName;
    
    /**
     * 头像
     */
    @Column(name = "avatar", length = 255)
    @TableField("avatar")
    private String avatar;
    
    /**
     * 邮箱
     */
    @Column(name = "email", length = 100)
    @TableField("email")
    private String email;
    
    /**
     * 手机号
     */
    @Column(name = "mobile", length = 20)
    @TableField("mobile")
    private String mobile;
    
    /**
     * 性别（0-未知，1-男，2-女）
     */
    @Column(name = "gender", nullable = false)
    @TableField("gender")
    private Integer gender;
    
    /**
     * 状态（0-正常，1-禁用）
     */
    @Column(name = "status", nullable = false)
    @TableField("status")
    private Integer status;
    
    /**
     * 部门ID
     */
    @Column(name = "department_id")
    @TableField("department_id")
    private Long departmentId;
    
    /**
     * 职位ID
     */
    @Column(name = "position_id")
    @TableField("position_id")
    private Long positionId;
    
    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;
    
    /**
     * 最后登录IP
     */
    @Column(name = "last_login_ip", length = 50)
    @TableField("last_login_ip")
    private String lastLoginIp;
} 