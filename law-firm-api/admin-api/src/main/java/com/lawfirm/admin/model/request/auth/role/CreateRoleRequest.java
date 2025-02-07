package com.lawfirm.admin.model.request.auth.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "创建角色请求")
public class CreateRoleRequest {

    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称")
    private String roleName;

    @NotBlank(message = "角色编码不能为空")
    @Schema(description = "角色编码")
    private String roleCode;

    @NotNull(message = "显示顺序不能为空")
    @Schema(description = "显示顺序")
    private Integer orderNum;

    @Schema(description = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private Integer dataScope;

    @Schema(description = "菜单树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示）")
    private Boolean menuCheckStrictly;

    @Schema(description = "部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示）")
    private Boolean deptCheckStrictly;

    @Schema(description = "状态（0正常 1停用）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}