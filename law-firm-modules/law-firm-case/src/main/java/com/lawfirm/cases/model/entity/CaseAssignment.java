package com.lawfirm.cases.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "case_assignment")
@EqualsAndHashCode(callSuper = true)
@TableName("case_assignment")
public class CaseAssignment extends ModelBaseEntity<CaseAssignment> {

    @Comment("案件ID")
    @Column(nullable = false)
    private Long caseId;

    @Comment("原律师")
    @Column(nullable = false, length = 64)
    private String fromLawyer;

    @Comment("新律师")
    @Column(nullable = false, length = 64)
    private String toLawyer;

    @Comment("分配原因")
    @Column(length = 512)
    private String reason;

    @Comment("分配时间")
    @Column(nullable = false)
    private LocalDateTime assignTime;

    @Comment("预计交接完成时间")
    private LocalDateTime expectedHandoverTime;

    @Comment("实际交接完成时间")
    private LocalDateTime actualHandoverTime;

    @Comment("分配状态(PENDING-待交接/IN_PROGRESS-交接中/COMPLETED-已完成)")
    @Column(nullable = false, length = 32)
    private String assignmentStatus;

    @Comment("操作人")
    @Column(nullable = false, length = 64)
    private String operator;

    @Comment("备注")
    @Column(length = 512)
    private String remarks;
} 