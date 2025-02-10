package com.lawfirm.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 系统用户实体类
 */
@Getter
@Setter
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends ModelBaseEntity<SysUser> {
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 头像
     */
    private String avatar;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 性别（0:未知 1:男 2:女）
     */
    private Integer sex;
    
    /**
     * 部门ID
     */
    private Long deptId;
    
    /**
     * 部门名称
     */
    private String deptName;
    
    /**
     * 最后登录IP
     */
    private String loginIp;
    
    /**
     * 最后登录时间
     */
    private LocalDateTime loginTime;
} 