package com.lawfirm.model.contract.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 合同列表视图对象，展示合同的简要信息
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class ContractListVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    private String contractNo;  // 合同编号
    private String contractName; // 合同名称
    private Integer status;      // 合同状态
    private Long clientId;       // 客户ID
    private String signingDate;  // 签约日期
} 