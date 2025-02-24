package com.lawfirm.model.auth.dto.user;

import com.lawfirm.common.data.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryDTO extends PageQuery {
    
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
     * 手机号
     */
    private String mobile;
    
    /**
     * 邮箱
     */
    private String email;
    
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
     * 职位ID
     */
    private Long positionId;
    
    /**
     * 角色ID
     */
    private Long roleId;
} 