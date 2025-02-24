package com.lawfirm.model.cases.entity.team;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.entity.base.Case;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.persistence.*;
import java.util.List;

/**
 * 案件团队实体
 */
@Data
@Entity
@Table(name = "case_team")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseTeam extends ModelBaseEntity {

    /**
     * 关联案件
     */
    @OneToOne
    @JoinColumn(name = "case_id", nullable = false)
    private Case case;

    /**
     * 主办律师ID
     */
    @Column(nullable = false)
    private Long leadLawyerId;

    /**
     * 协办律师ID列表
     */
    @ElementCollection
    @CollectionTable(name = "case_team_co_lawyers", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "lawyer_id")
    private List<Long> coLawyerIds;

    /**
     * 助理ID列表
     */
    @ElementCollection
    @CollectionTable(name = "case_team_assistants", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "assistant_id")
    private List<Long> assistantIds;

    /**
     * 实习生ID列表
     */
    @ElementCollection
    @CollectionTable(name = "case_team_interns", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "intern_id")
    private List<Long> internIds;

    /**
     * 团队负责人ID（通常是合伙人）
     */
    @Column(nullable = false)
    private Long supervisorId;

    /**
     * 团队说明
     */
    @Column(length = 500)
    private String description;

    /**
     * 是否需要定期汇报
     */
    private Boolean needRegularReport;

    /**
     * 汇报周期（天）
     */
    private Integer reportCycle;
} 