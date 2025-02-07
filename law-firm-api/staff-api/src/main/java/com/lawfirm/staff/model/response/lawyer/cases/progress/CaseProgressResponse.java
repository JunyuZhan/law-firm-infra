package com.lawfirm.staff.model.response.lawyer.cases.progress;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件进度响应
 */
@Data
@Schema(description = "案件进度响应")
public class CaseProgressResponse {
    
    @Schema(description = "进度ID")
    private Long id;
    
    @Schema(description = "案件ID")
    private Long caseId;
    
    @Schema(description = "进度内容")
    private String content;
    
    @Schema(description = "进度类型(1:立案 2:庭前准备 3:开庭 4:判决 5:执行 6:结案)")
    private Integer type;
    
    @Schema(description = "进度时间")
    private LocalDateTime progressTime;
    
    @Schema(description = "附件列表")
    private List<String> attachments;
    
    @Schema(description = "备注")
    private String remark;
    
    @Schema(description = "创建人")
    private String creator;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 