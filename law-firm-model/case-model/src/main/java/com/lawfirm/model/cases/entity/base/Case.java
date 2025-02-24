package com.lawfirm.model.cases.entity.base;

import com.lawfirm.model.base.entity.TenantEntity;
import com.lawfirm.model.cases.entity.business.*;
import com.lawfirm.model.cases.entity.team.*;
import com.lawfirm.model.cases.enums.base.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 案件实体类
 */
@Data
@Entity
@Table(name = "case_info")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Case extends TenantEntity {

    /**
     * 案件编号
     */
    @Column(length = 32, unique = true, nullable = false)
    private String caseNumber;

    /**
     * 案件名称
     */
    @Column(length = 128, nullable = false)
    private String caseName;

    /**
     * 案件描述
     */
    @Column(length = 2000)
    private String description;

    /**
     * 案件类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaseTypeEnum caseType;

    /**
     * 案件状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaseStatusEnum status;

    /**
     * 案件阶段
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaseStageEnum stage;

    /**
     * 案件进展
     */
    @Enumerated(EnumType.STRING)
    private CaseProgressEnum progress;

    /**
     * 优先级
     */
    @Enumerated(EnumType.STRING)
    private CasePriorityEnum priority;

    /**
     * 重要性
     */
    @Enumerated(EnumType.STRING)
    private CaseImportanceEnum importance;

    /**
     * 复杂度
     */
    @Enumerated(EnumType.STRING)
    private CaseComplexityEnum complexity;

    /**
     * 案件标签
     */
    @ElementCollection
    @CollectionTable(name = "case_tags", joinColumns = @JoinColumn(name = "case_id"))
    @Column(name = "tag")
    @Enumerated(EnumType.STRING)
    private Set<CaseTagEnum> tags;

    /**
     * 案件结果
     */
    @Enumerated(EnumType.STRING)
    private CaseResultEnum result;

    /**
     * 主办律师ID
     */
    @Column(nullable = false)
    private Long lawyerId;

    /**
     * 客户ID
     */
    @Column(nullable = false)
    private Long clientId;

    /**
     * 对方当事人
     */
    @Column(length = 128)
    private String opposingParty;

    /**
     * 收费类型
     */
    @Enumerated(EnumType.STRING)
    private CaseFeeTypeEnum feeType;

    /**
     * 预估金额
     */
    @Column(precision = 19, scale = 2)
    private BigDecimal estimatedAmount;

    /**
     * 实际收费
     */
    @Column(precision = 19, scale = 2)
    private BigDecimal actualAmount;

    /**
     * 立案时间
     */
    private LocalDateTime filingTime;

    /**
     * 结案时间
     */
    private LocalDateTime closingTime;

    /**
     * 代理期限
     */
    private LocalDateTime agencyDeadline;

    /**
     * 委托人联系方式
     */
    @Column(length = 32)
    private String clientContact;

    /**
     * 案件来源
     */
    @Enumerated(EnumType.STRING)
    private CaseSourceEnum source;

    /**
     * 承办部门ID
     */
    private Long departmentId;

    /**
     * 法院信息
     */
    @Column(length = 64)
    private String court;

    /**
     * 法庭信息
     */
    @Column(length = 32)
    private String tribunal;

    /**
     * 主审法官
     */
    @Column(length = 16)
    private String judge;

    /**
     * 是否公开
     */
    private Boolean isPublic;

    /**
     * 是否有利益冲突
     */
    private Boolean hasConflict;

    /**
     * 利益冲突说明
     */
    @Column(length = 500)
    private String conflictDescription;

    /**
     * 备注
     */
    @Column(length = 500)
    private String remarks;

    /**
     * 案件团队
     */
    @OneToOne(mappedBy = "case", cascade = CascadeType.ALL, orphanRemoval = true)
    private CaseTeam team;

    /**
     * 案件参与方列表
     */
    @OneToMany(mappedBy = "case", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaseParticipant> participants;

    /**
     * 案件分配记录
     */
    @OneToMany(mappedBy = "case", cascade = CascadeType.ALL)
    private List<CaseAssignment> assignments;

    /**
     * 案件文档列表
     */
    @OneToMany(mappedBy = "case", cascade = CascadeType.ALL)
    private List<CaseDocument> documents;

    /**
     * 案件文件列表
     */
    @OneToMany(mappedBy = "case", cascade = CascadeType.ALL)
    private List<CaseFile> files;

    /**
     * 案件提醒列表
     */
    @OneToMany(mappedBy = "case", cascade = CascadeType.ALL)
    private List<CaseReminder> reminders;

    /**
     * 状态变更记录
     */
    @OneToMany(mappedBy = "case", cascade = CascadeType.ALL)
    private List<CaseStatusLog> statusLogs;

    /**
     * 工作量记录
     */
    @OneToMany(mappedBy = "case", cascade = CascadeType.ALL)
    private List<CaseWorkload> workloads;

    /**
     * 案件类别（例如：民事、刑事、商事等）
     */
    @Column(length = 32)
    private String category;

    /**
     * 案由
     */
    @Column(length = 128)
    private String causeOfAction;

    /**
     * 代理类型（例如：一审、二审、再审等）
     */
    @Column(length = 32)
    private String representationType;

    /**
     * 代理权限
     */
    @Column(length = 500)
    private String representationAuthority;

    /**
     * 是否需要风险提示
     */
    private Boolean needRiskWarning;

    /**
     * 风险等级
     */
    @Column(length = 16)
    private String riskLevel;

    /**
     * 风险描述
     */
    @Column(length = 500)
    private String riskDescription;

    /**
     * 是否有时效要求
     */
    private Boolean hasTimeLimit;

    /**
     * 时效截止日期
     */
    private LocalDateTime timeLimitDeadline;

    /**
     * 时效描述
     */
    @Column(length = 200)
    private String timeLimitDescription;

    /**
     * 关联案件ID列表
     */
    @ElementCollection
    @CollectionTable(name = "case_related", joinColumns = @JoinColumn(name = "case_id"))
    @Column(name = "related_case_id")
    private Set<Long> relatedCaseIds;

    /**
     * 是否为重大案件
     */
    private Boolean isMajorCase;

    /**
     * 重大案件说明
     */
    @Column(length = 500)
    private String majorCaseDescription;

    /**
     * 是否为群体性案件
     */
    private Boolean isGroupCase;

    /**
     * 群体性案件说明
     */
    @Column(length = 500)
    private String groupCaseDescription;

    /**
     * 是否涉及舆情
     */
    private Boolean hasPublicOpinion;

    /**
     * 舆情说明
     */
    @Column(length = 500)
    private String publicOpinionDescription;

    /**
     * 案件归属部门ID列表
     */
    @ElementCollection
    @CollectionTable(name = "case_departments", joinColumns = @JoinColumn(name = "case_id"))
    @Column(name = "department_id")
    private Set<Long> departmentIds;

    /**
     * 案件标签
     */
    @ElementCollection
    @CollectionTable(name = "case_labels", joinColumns = @JoinColumn(name = "case_id"))
    @Column(name = "label")
    private Set<String> labels;

    /**
     * 案件评分
     */
    @Column(precision = 3, scale = 1)
    private BigDecimal rating;

    /**
     * 评分说明
     */
    @Column(length = 500)
    private String ratingDescription;

    /**
     * 归档编号
     */
    @Column(length = 32)
    private String archiveNumber;

    /**
     * 归档位置
     */
    @Column(length = 128)
    private String archiveLocation;

    /**
     * 归档说明
     */
    @Column(length = 500)
    private String archiveDescription;
} 