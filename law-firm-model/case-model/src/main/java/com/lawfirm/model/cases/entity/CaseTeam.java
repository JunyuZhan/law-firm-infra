package com.lawfirm.model.cases.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "case_team")
@EqualsAndHashCode(callSuper = true)
public class CaseTeam extends ModelBaseEntity {

    @NotNull(message = "案件ID不能为空")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    private Case caseInfo;

    @NotNull(message = "律师ID不能为空")
    @Column(nullable = false)
    private Long lawyerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CaseTeamRole role = CaseTeamRole.ASSISTANT;  // 团队角色

    @Column(length = 200)
    private String responsibilities;  // 职责描述

    public enum CaseTeamRole {
        LEADER,     // 主办律师
        ASSISTANT,  // 协办律师
        CONSULTANT  // 顾问律师
    }
} 