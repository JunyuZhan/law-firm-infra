package com.lawfirm.admin.model.request.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Schema(description = "创建模板请求")
public class CreateTemplateRequest {
    
    @Schema(description = "模板名称")
    @NotBlank(message = "模板名称不能为空")
    @Size(max = 100, message = "模板名称长度不能超过100个字符")
    private String templateName;
    
    @Schema(description = "模板类型")
    @NotBlank(message = "模板类型不能为空")
    private String templateType;
    
    @Schema(description = "模板内容")
    private String content;
    
    @Schema(description = "备注")
    private String remark;
} 