package com.lawfirm.model.document.entity.business;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.document.entity.base.BaseDocument;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 合同文档实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("doc_contract")
public class ContractDocument extends BaseDocument {

    /**
     * 合同ID
     */
    @TableField("contract_id")
    private Long contractId;

    /**
     * 合同编号
     */
    @TableField("contract_code")
    private String contractCode;

    /**
     * 合同类型
     */
    @TableField("contract_type")
    private String contractType;

    /**
     * 合同状态
     */
    @TableField("contract_status")
    private String contractStatus;

    /**
     * 签约方A
     */
    @TableField("party_a")
    private String partyA;

    /**
     * 签约方B
     */
    @TableField("party_b")
    private String partyB;

    /**
     * 签约日期
     */
    @TableField("sign_date")
    private LocalDateTime signDate;

    /**
     * 生效日期
     */
    @TableField("effective_date")
    private LocalDateTime effectiveDate;

    /**
     * 到期日期
     */
    @TableField("expiry_date")
    private LocalDateTime expiryDate;

    /**
     * 合同金额
     */
    @TableField("amount")
    private Double amount;

    /**
     * 币种
     */
    @TableField("currency")
    private String currency;

    /**
     * 是否需要盖章
     */
    @TableField("need_seal")
    private Boolean needSeal;

    /**
     * 盖章状态
     */
    @TableField("seal_status")
    private String sealStatus;

    /**
     * 审批流程ID
     */
    @TableField("workflow_id")
    private Long workflowId;

    /**
     * 审批状态
     */
    @TableField("approval_status")
    private String approvalStatus;
} 