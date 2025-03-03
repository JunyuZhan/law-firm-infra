package com.lawfirm.model.contract.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 合同汇总视图对象，展示合同的汇总信息
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class ContractSummaryVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    
    private Long totalContracts;   // 合同总数
    private double totalAmount;     // 合同总金额
    private Long activeContracts;   // 活跃合同数量
    private Long expiredContracts;   // 已到期合同数量
} 