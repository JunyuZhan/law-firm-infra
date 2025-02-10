package com.lawfirm.admin.model.request.system.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "更新模板请求")
public class UpdateTemplateRequest {

    @Schema(description = "模板名称")
    @NotBlank(message = "模板名称不能为空")
    private String name;

    @Schema(description = "模板编码")
    @NotBlank(message = "模板编码不能为空")
    private String code;

    @Schema(description = "模板内容")
    @NotBlank(message = "模板内容不能为空")
    private String content;

    @Schema(description = "模板类型")
    @NotBlank(message = "模板类型不能为空")
    private String type;

    @Schema(description = "模板状态（0正常 1停用）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
} 