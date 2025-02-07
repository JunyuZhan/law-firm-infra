package com.lawfirm.staff.model.response.lawyer.conflict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "冲突检查响应")
public class ConflictResponse {

    @Schema(description = "冲突ID")
    private Long id;

    @Schema(description = "案件ID")
    private Long caseId;

    @Schema(description = "案件名称")
    private String caseName;

    @Schema(description = "冲突类型")
    private String type;

    @Schema(description = "冲突描述")
    private String description;

    @Schema(description = "处理状态（0未处理 1已处理）")
    private Integer status;

    @Schema(description = "处理结果")
    private String result;

    @Schema(description = "处理人")
    private String handler;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 