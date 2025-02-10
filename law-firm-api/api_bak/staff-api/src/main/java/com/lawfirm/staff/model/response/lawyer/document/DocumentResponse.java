package com.lawfirm.staff.model.response.lawyer.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "文档响应")
public class DocumentResponse {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "文档名称")
    private String name;

    @Schema(description = "文档类型")
    private Integer type;

    @Schema(description = "文档类型名称")
    private String typeName;

    @Schema(description = "文件大小")
    private Long size;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "状态名称")
    private String statusName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建人")
    private String creator;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;
} 