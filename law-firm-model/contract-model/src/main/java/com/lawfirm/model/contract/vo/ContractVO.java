package com.lawfirm.model.contract.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 合同视图对象，展示合同的基本信息
 * 用于律师事务所法律服务委托合同列表展示
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class ContractVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    
    private String contractNo;      // 合同编号
    private String contractName;    // 合同名称
    private String contractType;    // 合同类型
    private String contractTypeName; // 合同类型名称
    private Integer status;         // 合同状态
    private String statusName;      // 合同状态名称
    private Long clientId;          // 客户ID
    private String clientName;      // 客户名称
    private Long caseId;            // 关联案件ID
    private String caseName;        // 关联案件名称
    private Double amount;          // 合同金额
    private Date signingDate;       // 签约日期
    private Date effectiveDate;     // 生效日期
    private Date expiryDate;        // 到期日期
    private String serviceScope;    // 服务范围
    private Long leadAttorneyId;    // 主办律师ID
    private String leadAttorneyName; // 主办律师姓名
    private Long departmentId;      // 承办部门ID
    private String departmentName;  // 承办部门名称
    private Integer teamMemberCount; // 团队成员数量
    private Double currentPaidAmount; // 当前已收款金额
    private Double totalAmount;     // 总金额(包括所有收费项)
    private Boolean isExpiring;     // 是否即将到期
    private Integer remainingDays;  // 剩余天数
} 