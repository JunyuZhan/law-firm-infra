package com.lawfirm.staff.model.response.lawyer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "文档响应")
public class DocumentResponse {

    @Schema(description = "文档ID")
    private Long id;

    @Schema(description = "文档标题")
    private String title;

    @Schema(description = "文档类型")
    private Integer type;

    @Schema(description = "文档内容")
    private String content;

    @Schema(description = "关联案件ID")
    private Long caseId;

    @Schema(description = "关联案件名称")
    private String caseName;

    @Schema(description = "关联客户ID")
    private Long clientId;

    @Schema(description = "关联客户名称")
    private String clientName;

    @Schema(description = "文档描述")
    private String description;

    @Schema(description = "标签")
    private String[] tags;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "更新人")
    private String updateBy;
} 