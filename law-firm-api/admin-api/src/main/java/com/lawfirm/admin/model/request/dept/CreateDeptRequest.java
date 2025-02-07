package com.lawfirm.admin.model.request.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Schema(description = "创建部门请求")
public class CreateDeptRequest {
    
    @Schema(description = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 50, message = "部门名称长度不能超过50个字符")
    private String deptName;
    
    @Schema(description = "父部门ID")
    private Long parentId;
    
    @Schema(description = "显示顺序")
    private Integer orderNum;
    
    @Schema(description = "负责人")
    private String leader;
    
    @Schema(description = "联系电话")
    private String phone;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status;
} 