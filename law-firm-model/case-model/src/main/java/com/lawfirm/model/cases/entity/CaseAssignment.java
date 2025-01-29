package com.lawfirm.model.cases.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Data
@TableName("law_case_assignment")
@EqualsAndHashCode(callSuper = true)
public class CaseAssignment extends ModelBaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    @NotNull(message = "案件ID不能为空")
    @TableField("case_id")
    @Comment("案件ID")
    private Long caseId;

    @NotNull(message = "受理律师不能为空")
    @TableField("to_lawyer")
    @Comment("受理律师")
    private String toLawyer;

    @TableField("reason")
    @Comment("分配原因")
    private String reason;

    @TableField("assign_time")
    @Comment("分配时间")
    private LocalDateTime assignTime;

    @TableField("expected_handover_time")
    @Comment("预计交接时间")
    private LocalDateTime expectedHandoverTime;

    @TableField("actual_handover_time")
    @Comment("实际交接时间")
    private LocalDateTime actualHandoverTime;

    @TableField("assignment_status")
    @Comment("分配状态")
    private String assignmentStatus;

    @TableField("operator")
    @Comment("操作人")
    private String operator;
} 