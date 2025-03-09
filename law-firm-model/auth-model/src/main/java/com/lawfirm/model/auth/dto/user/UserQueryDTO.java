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
     * 状态（0-禁用，1-启用）
     */
    private Integer status;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 当前页码
     */
    private Long current;
    
    /**
     * 每页大小
     */
    private Long size;
    
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
    
    /**
     * 获取页码
     */
    public Long getPageNum() {
        return getCurrent();
    }
    
    /**
     * 获取每页大小
     */
    public Long getPageSize() {
        return getSize();
    }
    
    /**
     * 获取手机号
     */
    public String getPhone() {
        return mobile;
    }
} 