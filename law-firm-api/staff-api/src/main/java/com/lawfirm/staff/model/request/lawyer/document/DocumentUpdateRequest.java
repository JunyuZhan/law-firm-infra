package com.lawfirm.staff.model.request.lawyer.document;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "更新文档请求")
public class DocumentUpdateRequest {

    @NotNull(message = "文档ID不能为空")
    @Schema(description = "文档ID")
    private Long id;

    @Schema(description = "文档名称")
    private String name;

    @Schema(description = "文档类型")
    private Integer type;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
} 