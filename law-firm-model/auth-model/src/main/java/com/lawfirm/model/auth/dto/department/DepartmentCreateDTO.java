package com.lawfirm.model.auth.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 部门创建DTO
 */
@Data
public class DepartmentCreateDTO {
    
    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 50, message = "部门名称长度不能超过50个字符")
    private String name;
    
    /**
     * 上级部门ID
     */
    private Long parentId;
    
    /**
     * 显示顺序
     */
    private Integer orderNum;
    
    /**
     * 负责人ID
     */
    private Long leaderId;
    
    /**
     * 联系电话
     */
    @Size(max = 20, message = "联系电话长度不能超过20个字符")
    private String phone;
    
    /**
     * 邮箱
     */
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;
    
    /**
     * 部门状态（0正常 1停用）
     */
    private Integer status;
    
    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
} 