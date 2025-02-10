package com.lawfirm.model.contract.dto.response;

import com.lawfirm.model.contract.enums.ContractPriorityEnum;
import com.lawfirm.model.contract.enums.ContractStatusEnum;
import com.lawfirm.model.contract.enums.ContractTypeEnum;
import com.lawfirm.model.contract.enums.PaymentTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同详情响应DTO
 */
@Data
public class ContractDetailResponse {

    private Long id;  // 合同ID
    private String contractNumber;  // 合同编号
    private String contractName;    // 合同名称
    private ContractTypeEnum contractType;  // 合同类型
    private ContractStatusEnum contractStatus;  // 合同状态
    private ContractPriorityEnum priority;  // 优先级
    private BigDecimal amount;  // 合同金额
    private PaymentTypeEnum paymentType;  // 付款方式

    // 关联信息
    private Long lawFirmId;      // 律所ID
    private String lawFirmName;  // 律所名称
    private Long caseId;         // 案件ID
    private String caseName;     // 案件名称
    private Long clientId;       // 客户ID
    private String clientName;   // 客户名称
    private Long templateId;     // 合同模板ID
    private String templateName; // 模板名称

    // 签约信息
    private LocalDateTime signDate;      // 签约日期
    private LocalDateTime effectiveDate; // 生效日期
    private LocalDateTime expiryDate;    // 到期日期
    private String remark;               // 备注

    // 审批信息
    private Long currentApproverId;      // 当前审批人ID
    private String currentApproverName;  // 当前审批人姓名
    private Integer currentApprovalStep; // 当前审批步骤
    private LocalDateTime lastApprovalTime;  // 最后审批时间

    // 统计信息
    private Integer clauseCount;     // 条款数量
    private Integer attachmentCount; // 附件数量
    private Integer partyCount;      // 相关方数量
    private BigDecimal paidAmount;   // 已付金额
    private BigDecimal unpaidAmount; // 未付金额

    // 时间信息
    private LocalDateTime createTime;    // 创建时间
    private String creatorName;          // 创建人
    private LocalDateTime updateTime;    // 更新时间
    private String updaterName;          // 更新人
    private LocalDateTime archiveTime;   // 归档时间

    // 相关列表
    private List<ContractPartyResponse> parties;         // 相关方列表
    private List<ContractClauseResponse> clauses;        // 条款列表
    private List<ContractPaymentResponse> payments;      // 付款计划列表
    private List<ContractAttachmentResponse> attachments;// 附件列表
    private List<ContractApprovalResponse> approvals;    // 审批记录列表

    /**
     * 合同相关方响应
     */
    @Data
    public static class ContractPartyResponse {
        private Long id;
        private String partyName;     // 参与方名称
        private String partyType;     // 参与方类型
        private Long partyId;         // 参与方ID
        private String partyRole;     // 参与方角色
        private String contactPerson; // 联系人
        private String contactPhone;  // 联系电话
        private String email;         // 电子邮箱
        private String address;       // 地址
        private Boolean isSignatory;  // 是否签约方
        private Integer signOrder;    // 签约顺序
        private String remark;        // 备注
    }

    /**
     * 合同条款响应
     */
    @Data
    public static class ContractClauseResponse {
        private Long id;
        private String title;         // 条款标题
        private String content;       // 条款内容
        private Integer clauseNumber; // 条款序号
        private String clauseType;    // 条款类型
        private Boolean isRequired;   // 是否必要条款
        private Boolean isStandard;   // 是否标准条款
        private String remark;        // 备注
        private Integer version;      // 版本号
    }

    /**
     * 合同付款计划响应
     */
    @Data
    public static class ContractPaymentResponse {
        private Long id;
        private Integer paymentNumber;    // 付款期数
        private String paymentStage;      // 付款阶段
        private PaymentTypeEnum paymentType;  // 付款类型
        private BigDecimal amount;        // 付款金额
        private BigDecimal paidAmount;    // 已付金额
        private BigDecimal paymentRatio;  // 付款比例
        private String paymentCondition;  // 付款条件
        private LocalDateTime planPaymentTime;   // 计划付款时间
        private LocalDateTime actualPaymentTime; // 实际付款时间
        private Boolean isCompleted;      // 是否已完成
        private String paymentStatus;     // 付款状态
        private String remark;            // 备注
    }

    /**
     * 合同附件响应
     */
    @Data
    public static class ContractAttachmentResponse {
        private Long id;
        private String attachmentName;  // 附件名称
        private String attachmentType;  // 附件类型
        private String fileType;        // 文件类型
        private Long fileSize;          // 文件大小
        private String filePath;        // 文件路径
        private String uploaderName;    // 上传人姓名
        private LocalDateTime uploadTime;  // 上传时间
        private Boolean isConfidential;  // 是否保密
        private String securityLevel;    // 安全级别
        private Boolean isRequired;      // 是否必要附件
        private String remark;           // 备注
    }

    /**
     * 合同审批记录响应
     */
    @Data
    public static class ContractApprovalResponse {
        private Long id;
        private Integer approvalStep;     // 审批步骤
        private String approvalNode;      // 审批节点
        private String approvalStatus;    // 审批状态
        private String approverName;      // 审批人姓名
        private String approverRole;      // 审批人角色
        private String approvalOpinion;   // 审批意见
        private LocalDateTime approvalTime;  // 审批时间
        private String remark;            // 备注
    }
}

