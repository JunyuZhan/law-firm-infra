package com.lawfirm.model.contract.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 更新合同的请求数据传输对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class ContractUpdateDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    
    private Long contractId;     // 合同ID
    private String contractNo;    // 合同编号
    private String contractName;   // 合同名称
    private String contractType;   // 合同类型
    private double amount;         // 合同金额
    private String signingDate;    // 签约日期
    private Long clientId;         // 客户ID
} 