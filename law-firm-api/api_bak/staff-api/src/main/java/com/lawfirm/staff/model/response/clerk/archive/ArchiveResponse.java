package com.lawfirm.staff.model.response.clerk.archive;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "档案响应")
public class ArchiveResponse {

    @Schema(description = "档案ID")
    private Long id;

    @Schema(description = "档案编号")
    private String archiveNo;

    @Schema(description = "档案名称")
    private String name;

    @Schema(description = "档案类型")
    private Integer type;

    @Schema(description = "档案类型名称")
    private String typeName;

    @Schema(description = "档案状态")
    private Integer status;

    @Schema(description = "档案状态名称")
    private String statusName;

    @Schema(description = "档案描述")
    private String description;

    @Schema(description = "存放位置")
    private String location;

    @Schema(description = "关键词")
    private String keywords;

    @Schema(description = "当前借阅人")
    private String currentBorrower;

    @Schema(description = "预计归还时间")
    private LocalDateTime expectedReturnTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "备注")
    private String remark;
} 