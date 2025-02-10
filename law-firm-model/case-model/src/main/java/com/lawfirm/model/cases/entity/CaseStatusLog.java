package com.lawfirm.model.cases.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
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
@Table(name = "case_status_log")
public class CaseStatusLog extends ModelBaseEntity<CaseStatusLog> {

    @NotNull(message = "案件ID不能为空")
    @Column(nullable = false)
    @Comment("案件ID")
    private Long caseId;

    @NotNull(message = "原状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("原状态")
    private CaseStatusEnum fromStatus;

    @NotNull(message = "新状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("新状态")
    private CaseStatusEnum toStatus;

    @NotBlank(message = "操作人不能为空")
    @Size(max = 50, message = "操作人长度不能超过50个字符")
    @Column(nullable = false, length = 50)
    @Comment("操作人")
    private String operator;

    @Size(max = 500, message = "原因说明长度不能超过500个字符")
    @Column(length = 500)
    @Comment("状态变更原因")
    private String reason;

    @NotNull(message = "操作时间不能为空")
    @Column(nullable = false)
    @Comment("操作时间")
    private LocalDateTime operateTime;

    @Column(length = 32)
    @Comment("操作人IP")
    private String operatorIp;

    @Column(length = 128)
    @Comment("操作人位置")
    private String operatorLocation;

    @Column(length = 64)
    @Comment("操作人部门")
    private String operatorDepartment;

    @Column(length = 256)
    @Comment("相关文档ID列表，逗号分隔")
    private String relatedDocuments;

    @Column(nullable = false)
    @Comment("是否需要通知")
    private Boolean needNotify = false;

    @Column(length = 500)
    @Comment("通知对象，逗号分隔的用户ID")
    private String notifyTo;

    @Column
    @Comment("通知时间")
    private LocalDateTime notifyTime;

    @Column(length = 32)
    @Comment("状态变更类型（自动/手动）")
    private String changeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    @Comment("关联案件")
    private Case caseInfo;
} 