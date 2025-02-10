package com.lawfirm.admin.model.request.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Size;

@Data
@Schema(description = "更新模板请求")
public class UpdateTemplateRequest {
    
    @Schema(description = "模板名称")
    @Size(max = 100, message = "模板名称长度不能超过100个字符")
    private String templateName;
    
    @Schema(description = "模板类型")
    private String templateType;
    
    @Schema(description = "模板内容")
    private String content;
    
    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status;
    
    @Schema(description = "备注")
    private String remark;
} 