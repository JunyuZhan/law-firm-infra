package com.lawfirm.model.cases.entity.team;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.entity.base.Case;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 案件分配实体
 */
@Data
@Entity
@Table(name = "case_assignment")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseAssignment extends ModelBaseEntity {

    /**
     * 关联案件
     */
    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private Case case;

    /**
     * 原律师ID
     */
    @Column(nullable = false)
    private Long originalLawyerId;

    /**
     * 新律师ID
     */
    @Column(nullable = false)
    private Long newLawyerId;

    /**
     * 分配类型（转办、协办、临时代理等）
     */
    @Column(length = 32, nullable = false)
    private String assignmentType;

    /**
     * 分配原因
     */
    @Column(length = 500)
    private String reason;

    /**
     * 工作交接说明
     */
    @Column(length = 1000)
    private String handoverNotes;

    /**
     * 预计交接时间
     */
    private LocalDateTime expectedHandoverTime;

    /**
     * 实际交接时间
     */
    private LocalDateTime actualHandoverTime;

    /**
     * 是否需要客户确认
     */
    private Boolean needClientConfirmation;

    /**
     * 客户确认时间
     */
    private LocalDateTime clientConfirmationTime;

    /**
     * 客户确认意见
     */
    @Column(length = 500)
    private String clientOpinion;

    /**
     * 审批人ID
     */
    private Long approverId;

    /**
     * 审批时间
     */
    private LocalDateTime approvalTime;

    /**
     * 审批意见
     */
    @Column(length = 500)
    private String approvalOpinion;

    /**
     * 状态（待审批、已审批、已确认、已完成等）
     */
    @Column(length = 32, nullable = false)
    private String status;

    /**
     * 是否已通知当事人
     */
    private Boolean isClientNotified;

    /**
     * 通知时间
     */
    private LocalDateTime notificationTime;

    /**
     * 通知方式
     */
    @Column(length = 32)
    private String notificationMethod;

    /**
     * 通知内容
     */
    @Column(length = 500)
    private String notificationContent;
}