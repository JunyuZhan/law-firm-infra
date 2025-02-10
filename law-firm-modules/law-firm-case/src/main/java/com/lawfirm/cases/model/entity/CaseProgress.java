package com.lawfirm.cases.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * 案件进度实体类
 */
@Data
@Entity
@Table(name = "case_progress")
@EqualsAndHashCode(callSuper = true)
@TableName("case_progress")
public class CaseProgress extends ModelBaseEntity<CaseProgress> {

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
} 