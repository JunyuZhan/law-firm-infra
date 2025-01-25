package com.lawfirm.cases.model.entity;

import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.model.cases.entity.Case;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "case_progress")
@EqualsAndHashCode(callSuper = true)
public class CaseProgress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("案件ID")
    @Column(nullable = false)
    private Long caseId;

    @Comment("进度类型")
    @Column(nullable = false, length = 32)
    private String progressType;

    @Comment("进度标题")
    @Column(nullable = false, length = 128)
    private String title;

    @Comment("进度描述")
    @Column(nullable = false, length = 512)
    private String description;

    @Comment("计划开始时间")
    @Column(nullable = false)
    private LocalDateTime plannedStartTime;

    @Comment("计划完成时间")
    @Column(nullable = false)
    private LocalDateTime plannedEndTime;

    @Comment("实际开始时间")
    private LocalDateTime actualStartTime;

    @Comment("实际完成时间")
    private LocalDateTime actualEndTime;

    @Comment("负责人")
    @Column(nullable = false, length = 64)
    private String owner;

    @Comment("参与人")
    @Column(length = 256)
    private String participants;

    @Comment("进度状态(NOT_STARTED-未开始/IN_PROGRESS-进行中/COMPLETED-已完成/DELAYED-已延期)")
    @Column(nullable = false, length = 32)
    private String progressStatus;

    @Comment("完成百分比")
    @Column(nullable = false)
    private Integer completionPercentage = 0;

    @Comment("是否关键节点")
    @Column(nullable = false)
    private Boolean isKeyNode = false;

    @Comment("前置节点ID")
    @Column(length = 256)
    private String previousNodeIds;

    @Comment("备注")
    @Column(length = 512)
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    private Case lawCase;
} 