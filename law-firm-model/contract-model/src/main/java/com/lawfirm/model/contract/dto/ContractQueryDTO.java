package com.lawfirm.model.contract.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 查询合同的请求数据传输对象
 * 用于查询律师事务所法律服务委托合同
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class ContractQueryDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    
    private String contractNo;     // 合同编号
    private String contractName;   // 合同名称
    private String contractType;   // 合同类型
    private Long clientId;         // 客户ID
    private Long caseId;           // 关联案件ID
    private Integer status;        // 合同状态
    private Long leadAttorneyId;   // 主办律师ID
    private Long departmentId;     // 承办部门ID
    private Date startDate;        // 开始日期
    private Date endDate;          // 结束日期
    private String keyword;        // 关键字(用于搜索合同名称、编号等)
    private List<Integer> statusList;  // 状态列表
    private List<Long> clientIds;  // 客户ID列表
    private List<String> contractTypes; // 合同类型列表
    private Double minAmount;      // 最小金额
    private Double maxAmount;      // 最大金额
    private Boolean isExpiring;    // 是否即将到期的合同
    private Integer expiringDays;  // 到期天数(用于查询即将到期的合同)
    private String sortField;      // 排序字段
    private String sortOrder;      // 排序方式(asc/desc)
} 