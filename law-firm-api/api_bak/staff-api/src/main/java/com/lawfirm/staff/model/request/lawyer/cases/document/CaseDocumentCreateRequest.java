package com.lawfirm.staff.model.request.lawyer.cases.document;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 案件文档创建请求
 */
@Data
@Schema(description = "案件文档创建请求")
public class CaseDocumentCreateRequest {

    @Schema(description = "文档名称")
    @NotBlank(message = "文档名称不能为空")
    private String name;

    @Schema(description = "文档类型(1:起诉状 2:答辩状 3:证据材料 4:判决书 5:其他)")
    @NotNull(message = "文档类型不能为空")
    private Integer type;

    @Schema(description = "文档路径")
    @NotBlank(message = "文档路径不能为空")
    private String path;

    @Schema(description = "文档大小(字节)")
    private Long size;

    @Schema(description = "文档格式")
    private String format;

    @Schema(description = "备注")
    private String remark;
} 