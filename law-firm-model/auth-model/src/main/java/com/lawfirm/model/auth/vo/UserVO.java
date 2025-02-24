package com.lawfirm.model.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserVO extends BaseVO {
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 真实姓名
     */
    private String realName;
    
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
     * 性别（0-未知，1-男，2-女）
     */
    private Integer gender;
    
    /**
     * 状态（0-正常，1-禁用）
     */
    private Integer status;
    
    /**
     * 部门ID
     */
    private Long departmentId;
    
    /**
     * 部门名称
     */
    private String departmentName;
    
    /**
     * 职位ID
     */
    private Long positionId;
    
    /**
     * 职位名称
     */
    private String positionName;
    
    /**
     * 角色ID列表
     */
    private List<Long> roleIds;
    
    /**
     * 角色名称列表
     */
    private List<String> roleNames;
    
    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;
    
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
} 