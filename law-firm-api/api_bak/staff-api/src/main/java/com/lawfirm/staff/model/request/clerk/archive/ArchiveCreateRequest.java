package com.lawfirm.staff.model.request.clerk.archive;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "档案创建请求")
public class ArchiveCreateRequest {

    @NotBlank(message = "档案编号不能为空")
    @Schema(description = "档案编号")
    private String archiveNo;

    @NotBlank(message = "档案名称不能为空")
    @Schema(description = "档案名称")
    private String name;

    @NotNull(message = "档案类型不能为空")
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