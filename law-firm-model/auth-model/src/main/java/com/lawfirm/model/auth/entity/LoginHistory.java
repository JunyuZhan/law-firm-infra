package com.lawfirm.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 登录历史实体
 */
@Data
@TableName("auth_login_history")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LoginHistory extends TenantEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 用户名
     */
    @TableField("username")
    private String username;
    
    /**
     * 登录时间
     */
    @TableField("login_time")
    private LocalDateTime loginTime;
    
    /**
     * 登出时间
     */
    @TableField("logout_time")
    private LocalDateTime logoutTime;
    
    /**
     * 登录IP
     */
    @TableField("login_ip")
    private String loginIp;
    
    /**
     * 登录地点
     */
    @TableField("login_location")
    private String loginLocation;
    
    /**
     * 浏览器
     */
    @TableField("browser")
    private String browser;
    
    /**
     * 操作系统
     */
    @TableField("os")
    private String os;
    
    /**
     * 登录状态（0-成功，1-失败）
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 提示消息
     */
    @TableField("msg")
    private String msg;
    
    /**
     * 登录类型（用户名密码、手机号、微信等）
     */
    @TableField("login_type")
    private String loginType;
}