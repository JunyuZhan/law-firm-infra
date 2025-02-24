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
 * 登录历史实体
 */
@Data
@Entity
@Table(name = "auth_login_history")
@TableName("auth_login_history")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LoginHistory extends TenantEntity {
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    @TableField("user_id")
    private Long userId;
    
    /**
     * 用户名
     */
    @Column(name = "username", nullable = false, length = 50)
    @TableField("username")
    private String username;
    
    /**
     * IP地址
     */
    @Column(name = "ip", length = 50)
    @TableField("ip")
    private String ip;
    
    /**
     * 登录地点
     */
    @Column(name = "location", length = 255)
    @TableField("location")
    private String location;
    
    /**
     * 浏览器
     */
    @Column(name = "browser", length = 50)
    @TableField("browser")
    private String browser;
    
    /**
     * 操作系统
     */
    @Column(name = "os", length = 50)
    @TableField("os")
    private String os;
    
    /**
     * 登录状态（0-成功，1-失败）
     */
    @Column(name = "status", nullable = false)
    @TableField("status")
    private Integer status;
    
    /**
     * 提示消息
     */
    @Column(name = "msg", length = 255)
    @TableField("msg")
    private String msg;
    
    /**
     * 登录时间
     */
    @Column(name = "login_time", nullable = false)
    @TableField("login_time")
    private LocalDateTime loginTime;
}