package com.lawfirm.model.cases.dto.business;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件合同数据传输对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseContractDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 合同标题
     */
    private String contractTitle;

    /**
     * 合同类型
     */
    private Integer contractType;

    /**
     * 合同状态
     */
    private Integer contractStatus;

    /**
     * 合同金额
     */
    private BigDecimal contractAmount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 签约主体（我方）
     */
    private String partyA;

    /**
     * 签约主体（对方）
     */
    private String partyB;

    /**
     * 签约日期
     */
    private transient LocalDateTime signDate;

    /**
     * 生效日期
     */
    private transient LocalDateTime effectiveDate;

    /**
     * 到期日期
     */
    private transient LocalDateTime expiryDate;

    /**
     * 合同内容
     */
    private String contractContent;

    /**
     * 合同条款（JSON格式）
     */
    private String contractTerms;

    /**
     * 付款条件
     */
    private String paymentTerms;

    /**
     * 付款计划（JSON格式）
     */
    private String paymentSchedule;

    /**
     * 已付金额
     */
    private BigDecimal paidAmount;

    /**
     * 未付金额
     */
    private BigDecimal unpaidAmount;

    /**
     * 负责人ID
     */
    private Long managerId;

    /**
     * 负责人姓名
     */
    private String managerName;

    /**
     * 签约人ID
     */
    private Long signerId;

    /**
     * 签约人姓名
     */
    private String signerName;

    /**
     * 审核人ID
     */
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    private String reviewerName;

    /**
     * 审核意见
     */
    private String reviewOpinion;

    /**
     * 审核状态
     */
    private Integer reviewStatus;

    /**
     * 关联文档IDs（逗号分隔）
     */
    private String documentIds;

    /**
     * 附件IDs（逗号分隔）
     */
    private String attachmentIds;

    /**
     * 标签（逗号分隔）
     */
    private String tags;

    /**
     * 备注
     */
    private String remarks;
} 