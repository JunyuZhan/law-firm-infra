package com.lawfirm.model.finance.dto;

import java.math.BigDecimal;

/**
 * 合同收入统计数据
 */
public interface ContractIncomeStat {
    /**
     * 获取合同ID
     */
    Long getContractId();

    /**
     * 获取合同编号
     */
    String getContractNumber();

    /**
     * 获取合同名称
     */
    String getContractName();

    /**
     * 获取收入金额
     */
    BigDecimal getAmount();

    /**
     * 获取收入笔数
     */
    Integer getCount();
} 