package com.lawfirm.staff.model.request.clerk.archive;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "档案更新请求")
public class ArchiveUpdateRequest {

    @NotNull(message = "档案ID不能为空")
    @Schema(description = "档案ID")
    private Long id;

    @Schema(description = "档案名称")
    private String name;

    @Schema(description = "档案类型")
    private Integer type;

    @Schema(description = "档案描述")
    private String description;

    @Schema(description = "存放位置")
    private String location;

    @Schema(description = "关键词")
    private String keywords;

    @Schema(description = "备注")
    private String remark;
} 