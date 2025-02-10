package com.lawfirm.staff.model.response.lawyer.conflict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "冲突检查响应")
public class ConflictCheckResponse {
    
    @Schema(description = "冲突检查ID")
    private Long id;
    
    @Schema(description = "案件ID")
    private Long matterId;
    
    @Schema(description = "案件名称")
    private String matterName;
    
    @Schema(description = "检查类型")
    private Integer type;
    
    @Schema(description = "检查状态")
    private Integer status;
    
    @Schema(description = "检查结果")
    private Boolean hasConflict;
    
    @Schema(description = "冲突描述")
    private String conflictDescription;
    
    @Schema(description = "检查人ID")
    private Long checkerId;
    
    @Schema(description = "检查人姓名")
    private String checkerName;
    
    @Schema(description = "检查时间")
    private LocalDateTime checkTime;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 