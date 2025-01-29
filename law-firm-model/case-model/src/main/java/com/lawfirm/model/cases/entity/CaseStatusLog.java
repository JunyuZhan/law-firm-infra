package com.lawfirm.model.cases.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "case_status_log")
@EqualsAndHashCode(callSuper = true)
public class CaseStatusLog extends ModelBaseEntity {

    @Column(nullable = false)
    private Long caseId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaseStatusEnum fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaseStatusEnum toStatus;

    @Column(nullable = false, length = 50)
    private String operator;

    @Column(length = 500)
    private String reason;

    private LocalDateTime operateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    private Case caseInfo;
} 