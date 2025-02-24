package com.lawfirm.model.cases.entity.business;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.entity.base.Case;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "case_workload")
public class CaseWorkload extends ModelBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("案件ID")
    @Column(nullable = false)
    private Long caseId;

    @Comment("律师")
    @Column(nullable = false, length = 64)
    private String lawyer;

    @Comment("工作类型")
    @Column(nullable = false, length = 32)
    private String workType;

    @Comment("工作内容")
    @Column(nullable = false, length = 512)
    private String workContent;

    @Comment("开始时间")
    @Column(nullable = false)
    private LocalDateTime startTime;

    @Comment("结束时间")
    @Column(nullable = false)
    private LocalDateTime endTime;

    @Comment("工作时长(小时)")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal duration;

    @Comment("工作地点")
    @Column(length = 128)
    private String location;

    @Comment("是否需要报销")
    @Column(nullable = false)
    private Boolean needReimbursement = false;

    @Comment("报销金额")
    @Column(precision = 10, scale = 2)
    private BigDecimal reimbursementAmount;

    @Comment("报销说明")
    @Column(length = 256)
    private String reimbursementDesc;

    @Comment("备注")
    @Column(length = 512)
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    private Case lawCase;
} 