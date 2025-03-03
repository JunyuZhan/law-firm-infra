package com.lawfirm.model.log.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.log.entity.base.AuditableLog;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 登录日志实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_login_log")
public class LoginLog extends AuditableLog {

    private static final long serialVersionUID = 1L;

    /**
     * 登录账号
     */
    @TableField("username")
    private String username;

    /**
     * 登录状态（0成功 1失败）
     */
    @TableField("login_status")
    private Integer loginStatus;

    /**
     * 登录信息
     */
    @TableField("login_msg")
    private String loginMsg;

    /**
     * 登录时间
     */
    @TableField("login_time")
    private String loginTime;

    /**
     * 退出时间
     */
    @TableField("logout_time")
    private String logoutTime;

    /**
     * 在线时长（分钟）
     */
    @TableField("online_time")
    private Long onlineTime;

    /**
     * 会话ID
     */
    @TableField("session_id")
    private String sessionId;

    /**
     * 登录类型（如：账号密码、手机验证码、微信等）
     */
    @TableField("login_type")
    private String loginType;
} 