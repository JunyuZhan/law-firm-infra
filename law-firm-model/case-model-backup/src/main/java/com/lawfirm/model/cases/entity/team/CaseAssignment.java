package com.lawfirm.model.cases.entity.team;

import com.baomidou.mybatisplus.annotation.*;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.entity.base.Case;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "case_assignment")
public class CaseAssignment extends ModelBaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    @NotNull(message = "案件ID不能为空")
    @Column(nullable = false)
    @Comment("案件ID")
    private Long caseId;

    @NotNull(message = "原律师不能为空")
    @Column(nullable = false, length = 64)
    @Comment("原律师")
    private String fromLawyer;

    @NotNull(message = "受理律师不能为空")
    @Column(nullable = false, length = 64)
    @Comment("受理律师")
    private String toLawyer;

    @NotNull(message = "分配类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    @Comment("分配类型")
    private AssignmentType assignmentType;

    @Size(max = 500, message = "分配原因长度不能超过500个字符")
    @Column(length = 500)
    @Comment("分配原因")
    private String reason;

    @NotNull(message = "分配时间不能为空")
    @Column(nullable = false)
    @Comment("分配时间")
    private LocalDateTime assignTime = LocalDateTime.now();

    @Column
    @Comment("预计交接时间")
    private LocalDateTime expectedHandoverTime;

    @Column
    @Comment("实际交接时间")
    private LocalDateTime actualHandoverTime;

    @NotNull(message = "分配状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    @Comment("分配状态")
    private AssignmentStatus assignmentStatus = AssignmentStatus.PENDING;

    @NotBlank(message = "操作人不能为空")
    @Column(nullable = false, length = 64)
    @Comment("操作人")
    private String operator;

    @Column(length = 64)
    @Comment("操作人部门")
    private String operatorDepartment;

    @Column(nullable = false)
    @Comment("是否需要交接")
    private Boolean needHandover = true;

    @Column(length = 500)
    @Comment("交接说明")
    private String handoverNote;

    @Column(nullable = false)
    @Comment("是否需要审批")
    private Boolean needApproval = false;

    @Column(nullable = false)
    @Comment("是否已审批")
    private Boolean approved = false;

    @Column(length = 64)
    @Comment("审批人")
    private String approver;

    @Column
    @Comment("审批时间")
    private LocalDateTime approvalTime;

    @Column(length = 500)
    @Comment("审批意见")
    private String approvalComment;

    @Column(nullable = false)
    @Comment("是否通知当事人")
    private Boolean notifyClient = false;

    @Column
    @Comment("通知时间")
    private LocalDateTime notifyTime;

    @Column(length = 500)
    @Comment("通知内容")
    private String notifyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    @Comment("所属案件")
    private Case caseInfo;

    public enum AssignmentType {
        TRANSFER("转办"),
        ASSISTANCE("协办"),
        TEMPORARY("临时代理"),
        HANDOVER("工作交接");

        private final String description;

        AssignmentType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum AssignmentStatus {
        PENDING("待处理"),
        ACCEPTED("已接受"),
        REJECTED("已拒绝"),
        IN_PROGRESS("交接中"),
        COMPLETED("已完成"),
        CANCELLED("已取消");

        private final String description;

        AssignmentStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
} 