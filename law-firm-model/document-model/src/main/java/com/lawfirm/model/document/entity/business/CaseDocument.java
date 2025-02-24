package com.lawfirm.model.document.entity.business;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.document.entity.base.BaseDocument;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 案件文档实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("doc_case")
public class CaseDocument extends BaseDocument {

    /**
     * 案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 案件编号
     */
    @TableField("case_code")
    private String caseCode;

    /**
     * 文档阶段（立案、庭审、结案等）
     */
    @TableField("doc_stage")
    private String docStage;

    /**
     * 文档分类（起诉书、判决书、证据等）
     */
    @TableField("doc_category")
    private String docCategory;

    /**
     * 是否为机密文件
     */
    @TableField("is_confidential")
    private Boolean isConfidential;

    /**
     * 是否需要签名
     */
    @TableField("need_signature")
    private Boolean needSignature;

    /**
     * 签名状态
     */
    @TableField("signature_status")
    private String signatureStatus;

    /**
     * 文档提交人
     */
    @TableField("submitter")
    private String submitter;

    /**
     * 文档审核人
     */
    @TableField("reviewer")
    private String reviewer;

    /**
     * 相关方（多个用逗号分隔）
     */
    @TableField("related_parties")
    private String relatedParties;
} 