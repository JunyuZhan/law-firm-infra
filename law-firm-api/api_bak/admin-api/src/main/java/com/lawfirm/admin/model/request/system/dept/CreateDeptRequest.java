package com.lawfirm.admin.model.request.system.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "创建部门请求")
public class CreateDeptRequest {

    @Schema(description = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    private String name;

    @Schema(description = "父部门ID")
    private Long parentId;

    @Schema(description = "显示顺序")
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    @Schema(description = "负责人")
    private String leader;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "部门状态（0正常 1停用）")
    private Integer status;
}