package com.lawfirm.model.contract.dto.request;

import com.lawfirm.model.contract.constants.ContractConstants;
import com.lawfirm.model.contract.enums.ContractPriorityEnum;
import com.lawfirm.model.contract.enums.ContractTypeEnum;
import com.lawfirm.model.contract.enums.PaymentTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同创建请求DTO
 */
@Data
public class ContractCreateRequest {

    @NotBlank(message = "合同名称不能为空")
    @Size(max = ContractConstants.FieldLength.CONTRACT_NAME, message = "合同名称长度不能超过{max}个字符")
    private String contractName;    // 合同名称

    @NotNull(message = "合同类型不能为空")
    private ContractTypeEnum contractType;  // 合同类型

    private ContractPriorityEnum priority;  // 优先级

    @NotNull(message = "合同金额不能为空")
    private BigDecimal amount;  // 合同金额

    private PaymentTypeEnum paymentType;  // 付款方式

    // 关联信息
    private Long lawFirmId;  // 律所ID
    private Long caseId;     // 案件ID
    private Long clientId;   // 客户ID
    private Long templateId; // 合同模板ID

    // 签约信息
    private LocalDateTime signDate;      // 签约日期
    private LocalDateTime effectiveDate; // 生效日期
    private LocalDateTime expiryDate;    // 到期日期

    @Size(max = ContractConstants.FieldLength.REMARK, message = "备注长度不能超过{max}个字符")
    private String remark;  // 备注

    // 合同相关方信息
    private List<ContractPartyCreateRequest> parties;

    // 合同条款信息
    private List<ContractClauseCreateRequest> clauses;

    // 合同付款计划
    private List<ContractPaymentCreateRequest> payments;

    // 合同附件信息
    private List<ContractAttachmentCreateRequest> attachments;

    /**
     * 合同相关方创建请求
     */
    @Data
    public static class ContractPartyCreateRequest {
        @NotBlank(message = "参与方名称不能为空")
        @Size(max = ContractConstants.FieldLength.PARTY_NAME, message = "参与方名称长度不能超过{max}个字符")
        private String partyName;  // 参与方名称

        @NotBlank(message = "参与方类型不能为空")
        private String partyType;  // 参与方类型

        private Long partyId;      // 参与方ID
        private String partyRole;  // 参与方角色
        private String contactPerson;  // 联系人
        private String contactPhone;   // 联系电话
        private String email;          // 电子邮箱
        private String address;        // 地址
        private Boolean isSignatory;   // 是否签约方
        private Integer signOrder;     // 签约顺序
        private String remark;         // 备注
    }

    /**
     * 合同条款创建请求
     */
    @Data
    public static class ContractClauseCreateRequest {
        @NotBlank(message = "条款标题不能为空")
        @Size(max = ContractConstants.FieldLength.CLAUSE_TITLE, message = "条款标题长度不能超过{max}个字符")
        private String title;  // 条款标题

        @NotBlank(message = "条款内容不能为空")
        @Size(max = ContractConstants.FieldLength.CLAUSE_CONTENT, message = "条款内容长度不能超过{max}个字符")
        private String content;  // 条款内容

        private Integer clauseNumber;  // 条款序号
        private String clauseType;     // 条款类型
        private Boolean isRequired;    // 是否必要条款
        private Boolean isStandard;    // 是否标准条款
        private String remark;         // 备注
    }

    /**
     * 合同付款计划创建请求
     */
    @Data
    public static class ContractPaymentCreateRequest {
        private Integer paymentNumber;  // 付款期数
        private String paymentStage;    // 付款阶段
        private PaymentTypeEnum paymentType;  // 付款类型
        private BigDecimal amount;      // 付款金额
        private BigDecimal paymentRatio;  // 付款比例
        private String paymentCondition;   // 付款条件
        private LocalDateTime planPaymentTime;  // 计划付款时间
        private String paymentMethod;    // 付款方式
        private String remark;           // 备注
    }

    /**
     * 合同附件创建请求
     */
    @Data
    public static class ContractAttachmentCreateRequest {
        @NotBlank(message = "附件名称不能为空")
        @Size(max = ContractConstants.FieldLength.ATTACHMENT_NAME, message = "附件名称长度不能超过{max}个字符")
        private String attachmentName;  // 附件名称

        private String attachmentType;  // 附件类型
        private String fileType;        // 文件类型
        private Long fileSize;          // 文件大小
        private String filePath;        // 文件路径
        private Boolean isConfidential; // 是否保密
        private String securityLevel;   // 安全级别
        private Boolean isRequired;     // 是否必要附件
        private String remark;          // 备注
    }
}

