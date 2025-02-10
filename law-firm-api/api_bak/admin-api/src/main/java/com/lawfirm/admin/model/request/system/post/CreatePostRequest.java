package com.lawfirm.admin.model.request.system.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "创建岗位请求")
public class CreatePostRequest {

    @Schema(description = "岗位编码")
    @NotBlank(message = "岗位编码不能为空")
    private String code;

    @Schema(description = "岗位名称")
    @NotBlank(message = "岗位名称不能为空")
    private String name;

    @Schema(description = "显示顺序")
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    @Schema(description = "岗位状态（0正常 1停用）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
} 