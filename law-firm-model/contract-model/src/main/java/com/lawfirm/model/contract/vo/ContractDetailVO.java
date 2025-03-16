package com.lawfirm.model.contract.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 合同详情视图对象，展示合同的详细信息
 * 用于律师事务所法律服务委托合同详情展示
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class ContractDetailVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    
    // 基本信息
    private String contractNo;      // 合同编号
    private String contractName;    // 合同名称
    private String contractType;    // 合同类型
    private String contractTypeName; // 合同类型名称
    private Integer status;         // 合同状态
    private String statusName;      // 合同状态名称
    private Double amount;          // 合同基础金额
    private Date signingDate;       // 签约日期
    private Date effectiveDate;     // 生效日期
    private Date expiryDate;        // 到期日期
    
    // 委托方信息
    private Long clientId;          // 客户ID
    private String clientName;      // 客户名称
    private String clientContact;   // 客户联系人
    private String clientPhone;     // 客户联系电话
    private String clientEmail;     // 客户邮箱
    
    // 案件信息
    private Long caseId;            // 关联案件ID
    private String caseName;        // 关联案件名称
    private String caseNo;          // 案件编号
    
    // 服务信息
    private String serviceScope;    // 服务范围
    private String delegatePower;   // 授权范围
    private String feeType;         // 收费类型
    private String paymentTerms;    // 付款条件
    
    // 承办信息
    private Long leadAttorneyId;    // 主办律师ID
    private String leadAttorneyName; // 主办律师姓名
    private Long departmentId;      // 承办部门ID
    private String departmentName;  // 承办部门名称
    
    // 合同内容
    private String contractText;    // 合同正文内容
    private Integer confidentialityLevel; // 保密级别
    private String remarks;         // 备注说明
    
    // 收费信息
    private Double totalAmount;     // 总金额(包括所有收费项)
    private Double currentPaidAmount; // 当前已收款金额
    private Double remainingAmount; // 剩余应收金额
    
    // 审批记录
    private List<ContractReviewVO> reviewRecords; // 审批记录
    
    // 变更记录
    private List<ContractChangeVO> changeRecords; // 变更记录
    
    // 团队成员
    private List<ContractTeamMemberVO> teamMembers; // 团队成员
    
    // 收费项目
    private List<ContractFeeVO> feeItems; // 收费项目
    
    // 里程碑
    private List<ContractMilestoneVO> milestones; // 里程碑
    
    // 附件
    private List<ContractAttachmentVO> attachments; // 附件列表
    
    /**
     * 合同团队成员VO
     */
    @Data
    public static class ContractTeamMemberVO extends BaseVO {
        private Long attorneyId;        // 律师ID
        private String attorneyName;    // 律师姓名
        private String roleType;        // 角色类型
        private String roleName;        // 角色名称
        private String responsibility;  // 负责内容
        private Double hourlyRate;      // 小时费率
        private Integer workloadPercent; // 工作量占比
        private Double billableHours;   // 已记录工时
    }
    
    /**
     * 合同收费项VO
     */
    @Data
    public static class ContractFeeVO extends BaseVO {
        private String feeType;         // 收费类型
        private String feeName;         // 费用名称
        private Double feeAmount;       // 费用金额
        private String currency;        // 币种
        private Double rate;            // 费率(风险代理)
        private String calculationMethod; // 计算方式
        private Date dueDate;           // 应付日期
        private Integer paymentStatus;  // 支付状态
        private String paymentStatusName; // 支付状态名称
        private Double paidAmount;      // 已支付金额
        private Boolean isTaxable;      // 是否含税
        private Double taxRate;         // 税率
        private String conditionDesc;   // 收费条件
    }
    
    /**
     * 合同里程碑VO
     */
    @Data
    public static class ContractMilestoneVO extends BaseVO {
        private String milestoneName;   // 里程碑名称
        private String description;     // 描述
        private Date planDate;          // 计划日期
        private Date actualDate;        // 实际日期
        private Integer status;         // 状态
        private String statusName;      // 状态名称
        private Integer completionPercent; // 完成百分比
        private Long responsibleId;     // 负责人ID
        private String responsibleName; // 负责人姓名
        private Boolean isKeyPoint;     // 是否关键点
    }
    
    /**
     * 合同附件VO
     */
    @Data
    public static class ContractAttachmentVO extends BaseVO {
        private String fileName;        // 文件名
        private String fileType;        // 文件类型
        private Long fileSize;          // 文件大小
        private String filePath;        // 文件路径
        private String uploadUserName;  // 上传人姓名
    }
} 