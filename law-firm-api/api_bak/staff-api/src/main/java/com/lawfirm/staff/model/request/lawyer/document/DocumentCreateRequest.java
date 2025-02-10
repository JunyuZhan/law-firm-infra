package com.lawfirm.staff.model.request.lawyer.document;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "创建文档请求")
public class DocumentCreateRequest {

    @NotBlank(message = "文档名称不能为空")
    @Schema(description = "文档名称")
    private String name;

    @NotNull(message = "文档类型不能为空")
    @Schema(description = "文档类型")
    private Integer type;

    @Schema(description = "备注")
    private String remark;
} 