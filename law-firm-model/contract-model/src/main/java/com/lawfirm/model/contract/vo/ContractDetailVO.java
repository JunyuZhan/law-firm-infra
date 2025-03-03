package com.lawfirm.model.contract.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 合同详情视图对象，展示合同的详细信息
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class ContractDetailVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    
    private String contractNo;  // 合同编号
    private String contractName; // 合同名称
    private String contractType; // 合同类型
    private Integer status;      // 合同状态
    private double amount;       // 合同金额
    private String signingDate;  // 签约日期
    private Long clientId;       // 客户ID
} 