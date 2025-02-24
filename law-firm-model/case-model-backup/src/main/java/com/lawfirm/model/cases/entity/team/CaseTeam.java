package com.lawfirm.model.cases.entity.team;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.entity.base.Case;
import jakarta.persistence.*;
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
@Table(name = "case_team")
public class CaseTeam extends ModelBaseEntity {

    @NotNull(message = "案件ID不能为空")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    @Comment("所属案件")
    private Case caseInfo;

    @NotNull(message = "律师ID不能为空")
    @Column(nullable = false)
    @Comment("律师ID")
    private Long lawyerId;

    @NotNull(message = "团队角色不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Comment("团队角色")
    private CaseTeamRole role = CaseTeamRole.ASSISTANT;

    @Size(max = 200, message = "职责描述长度不能超过200个字符")
    @Column(length = 200)
    @Comment("职责描述")
    private String responsibilities;

    @Column(nullable = false)
    @Comment("加入时间")
    private LocalDateTime joinTime = LocalDateTime.now();

    @Column
    @Comment("退出时间")
    private LocalDateTime leaveTime;

    @Column(length = 500)
    @Comment("退出原因")
    private String leaveReason;

    @Column(nullable = false)
    @Comment("工作量占比（%）")
    private Integer workloadPercentage = 0;

    @Column(nullable = false)
    @Comment("是否参与分成")
    private Boolean shareProfit = false;

    @Column(nullable = false)
    @Comment("分成比例（%）")
    private Integer profitPercentage = 0;

    @Column(length = 64)
    @Comment("所属部门")
    private String department;

    @Column(length = 32)
    @Comment("职级")
    private String level;

    @Column(nullable = false)
    @Comment("是否需要考核")
    private Boolean needAssessment = true;

    @Column(length = 500)
    @Comment("考核指标")
    private String assessmentCriteria;

    @Column(length = 500)
    @Comment("备注")
    private String remark;

    public enum CaseTeamRole {
        LEADER("主办律师"),     
        ASSISTANT("协办律师"),  
        CONSULTANT("顾问律师"),
        INTERN("实习律师"),
        PARALEGAL("律师助理");

        private final String description;

        CaseTeamRole(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
} 