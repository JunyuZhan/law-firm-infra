package com.lawfirm.model.auth.dto.department;

import lombok.Data;

/**
 * 部门查询DTO
 */
@Data
public class DepartmentQueryDTO {
    
    /**
     * 部门名称
     */
    private String name;
    
    /**
     * 上级部门ID
     */
    private Long parentId;
    
    /**
     * 部门状态（0正常 1停用）
     */
    private Integer status;
} 