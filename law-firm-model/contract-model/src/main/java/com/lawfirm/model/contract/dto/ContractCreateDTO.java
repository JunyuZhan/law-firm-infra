package com.lawfirm.model.contract.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 创建合同的请求数据传输对象
 * 用于创建律师事务所法律服务委托合同
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class ContractCreateDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    
    private String contractNo;      // 合同编号
    private String contractName;    // 合同名称
    private String contractType;    // 合同类型(诉讼代理、非诉讼法律服务、法律顾问等)
    private Double amount;          // 合同基础金额
    private Date signingDate;       // 签约日期
    private Long clientId;          // 客户ID
    private Long caseId;            // 关联案件ID
    private Date effectiveDate;     // 生效日期
    private Date expiryDate;        // 到期日期
    private String serviceScope;    // 服务范围
    private String delegatePower;   // 授权范围
    private String feeType;         // 收费类型
    private String paymentTerms;    // 付款条件
    private Long leadAttorneyId;    // 主办律师ID
    private Long departmentId;      // 承办部门ID
    private String contractText;    // 合同正文内容
    private Integer confidentialityLevel; // 保密级别
    private String remarks;         // 备注说明
    private Long templateId;        // 合同模板ID
    
    // 团队成员列表
    private transient List<ContractTeamMemberDTO> teamMembers;
    
    // 收费结构列表
    private transient List<ContractFeeItemDTO> feeItems;
    
    // 里程碑列表
    private transient List<ContractMilestoneDTO> milestones;
    
    /**
     * 合同团队成员DTO
     */
    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class ContractTeamMemberDTO implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        
        private Long attorneyId;        // 律师ID
        private String roleType;        // 角色类型
        private String responsibility;  // 负责内容
        private Double hourlyRate;      // 小时费率
        private Integer workloadPercent; // 工作量占比
    }
    
    /**
     * 合同收费项DTO
     */
    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class ContractFeeItemDTO implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        
        private String feeType;         // 收费类型
        private String feeName;         // 费用名称
        private Double feeAmount;       // 费用金额
        private String currency;        // 币种
        private Double rate;            // 费率(风险代理)
        private String calculationMethod; // 计算方式
        private Date dueDate;           // 应付日期
        private Boolean isTaxable;      // 是否含税
        private Double taxRate;         // 税率
        private String conditionDesc;   // 收费条件
    }
    
    /**
     * 合同里程碑DTO
     */
    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class ContractMilestoneDTO implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        
        private String milestoneName;   // 里程碑名称
        private String description;     // 描述
        private Date planDate;          // 计划日期
        private Long responsibleId;     // 负责人ID
        private Boolean isKeyPoint;     // 是否关键点
        private Integer reminderDays;   // 提醒天数
        private Integer sequence;       // 序号
    }
} 