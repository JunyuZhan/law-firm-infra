package com.lawfirm.admin.model.request.auth.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "更新用户请求")
public class UpdateUserRequest {

    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long id;

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "性别(0-未知 1-男 2-女)")
    private Integer sex;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "部门ID")
    @NotNull(message = "部门不能为空")
    private Long deptId;

    @Schema(description = "岗位ID列表")
    private List<Long> postIds;

    @Schema(description = "角色ID列表")
    @NotNull(message = "角色不能为空")
    private List<Long> roleIds;

    @Schema(description = "状态(0-正常 1-停用)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
} 
import com.lawfirm.model.base.enums.BaseEnum  
