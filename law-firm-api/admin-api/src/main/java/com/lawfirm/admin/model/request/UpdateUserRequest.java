package com.lawfirm.admin.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Size;

@Data
@Schema(description = "更新用户请求")
public class UpdateUserRequest {
    
    @Schema(description = "真实姓名")
    @Size(max = 50, message = "真实姓名长度不能超过50个字符")
    private String realName;
    
    @Schema(description = "部门ID")
    private Long deptId;
    
    @Schema(description = "手机号")
    private String mobile;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status;
    
    @Schema(description = "备注")
    private String remark;
} 