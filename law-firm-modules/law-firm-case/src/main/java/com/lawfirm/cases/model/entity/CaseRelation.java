package com.lawfirm.cases.model.entity;

import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.model.cases.entity.Case;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

@Data
@Entity
@Table(name = "case_relation")
@EqualsAndHashCode(callSuper = true)
public class CaseRelation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("源案件ID")
    @Column(nullable = false)
    private Long sourceCaseId;

    @Comment("目标案件ID")
    @Column(nullable = false)
    private Long targetCaseId;

    @Comment("关联类型(PARENT-父案件/CHILD-子案件/RELATED-关联案件/CONFLICT-冲突案件)")
    @Column(nullable = false, length = 32)
    private String relationType;

    @Comment("关联说明")
    @Column(length = 512)
    private String description;

    @Comment("是否双向关联")
    @Column(nullable = false)
    private Boolean isBidirectional = false;

    @Comment("关联优先级(1-最高/5-最低)")
    @Column(nullable = false)
    private Integer priority = 3;

    @Comment("备注")
    @Column(length = 512)
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_case_id", insertable = false, updatable = false)
    private Case sourceCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_case_id", insertable = false, updatable = false)
    private Case targetCase;
} 