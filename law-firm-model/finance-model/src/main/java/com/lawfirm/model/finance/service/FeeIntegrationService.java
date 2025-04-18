package com.lawfirm.model.finance.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 费用集成服务接口
 * 
 * 该接口负责跨模块费用处理的集成，确保合同费用、案件费用和财务费用之间的一致性和数据流转
 * 实现了律师事务所"合同-案件-财务"的标准业务流程
 */
public interface FeeIntegrationService {
    
    /**
     * 创建合同相关的财务费用记录
     * 
     * @param contractFeeId 合同费用ID
     * @param contractId 合同ID
     * @param contractNo 合同编号
     * @param feeName 费用名称
     * @param feeType 费用类型
     * @param amount 费用金额
     * @param currency 币种
     * @param description 费用描述
     * @return 创建的财务费用ID
     */
    Long createContractFee(Long contractFeeId, Long contractId, String contractNo, 
                          String feeName, String feeType, BigDecimal amount, 
                          String currency, String description);
    
    /**
     * 从合同费用创建案件费用
     * 
     * @param contractFeeId 合同费用ID
     * @param caseId 案件ID
     * @param caseNumber 案件编号
     * @param amount 费用金额（可选，如果为null则使用合同费用金额）
     * @param description 费用描述
     * @return 创建的案件费用ID
     */
    Long createCaseFeeFromContract(Long contractFeeId, Long caseId, String caseNumber, 
                                   BigDecimal amount, String description);
    
    /**
     * 从案件费用创建财务费用记录
     * 
     * @param caseFeeId 案件费用ID
     * @param caseId 案件ID
     * @param caseNumber 案件编号
     * @param feeName 费用名称
     * @param feeType 费用类型
     * @param amount 费用金额
     * @param currency 币种
     * @param description 费用描述
     * @return 创建的财务费用ID
     */
    Long createFinanceFeeFromCase(Long caseFeeId, Long caseId, String caseNumber,
                                 String feeName, String feeType, BigDecimal amount,
                                 String currency, String description);
    
    /**
     * 更新合同费用并同步到相关的案件费用和财务费用
     * 
     * @param contractFeeId 合同费用ID
     * @param amount 更新后的金额
     * @param description 更新后的描述
     * @return 是否更新成功
     */
    boolean updateContractFeeAndSync(Long contractFeeId, BigDecimal amount, String description);
    
    /**
     * 更新案件费用并同步到财务费用
     * 
     * @param caseFeeId 案件费用ID
     * @param amount 更新后的金额
     * @param description 更新后的描述
     * @return 是否更新成功
     */
    boolean updateCaseFeeAndSync(Long caseFeeId, BigDecimal amount, String description);
    
    /**
     * 记录合同费用支付
     * 
     * @param contractFeeId 合同费用ID
     * @param amount 支付金额
     * @param paymentTime 支付时间
     * @param paymentMethod 支付方式
     * @param transactionNo 交易编号
     * @return 是否支付成功
     */
    boolean payContractFee(Long contractFeeId, BigDecimal amount, LocalDateTime paymentTime, 
                           String paymentMethod, String transactionNo);
    
    /**
     * 记录案件费用支付
     * 
     * @param caseFeeId 案件费用ID
     * @param amount 支付金额
     * @param paymentTime 支付时间
     * @param paymentMethod 支付方式
     * @param transactionNo 交易编号
     * @return 是否支付成功
     */
    boolean payCaseFee(Long caseFeeId, BigDecimal amount, LocalDateTime paymentTime, 
                       String paymentMethod, String transactionNo);
    
    /**
     * 获取合同的总费用金额
     * 
     * @param contractId 合同ID
     * @return 合同总费用金额
     */
    BigDecimal getTotalContractFeeAmount(Long contractId);
    
    /**
     * 获取案件的总费用金额
     * 
     * @param caseId 案件ID
     * @return 案件总费用金额
     */
    BigDecimal getTotalCaseFeeAmount(Long caseId);
    
    /**
     * 获取合同的已支付费用总额
     * 
     * @param contractId 合同ID
     * @return 已支付费用总额
     */
    BigDecimal getPaidContractFeeAmount(Long contractId);
    
    /**
     * 获取案件的已支付费用总额
     * 
     * @param caseId 案件ID
     * @return 已支付费用总额
     */
    BigDecimal getPaidCaseFeeAmount(Long caseId);
    
    /**
     * 根据财务费用ID查询关联的合同费用ID
     * 
     * @param financeFeeId 财务费用ID
     * @return 合同费用ID，如果不存在则返回null
     */
    Long getContractFeeIdByFinanceFeeId(Long financeFeeId);
    
    /**
     * 根据财务费用ID查询关联的案件费用ID
     * 
     * @param financeFeeId 财务费用ID
     * @return 案件费用ID，如果不存在则返回null
     */
    Long getCaseFeeIdByFinanceFeeId(Long financeFeeId);
    
    /**
     * 根据案件费用ID查询关联的合同费用ID
     * 
     * @param caseFeeId 案件费用ID
     * @return 合同费用ID，如果不存在则返回null
     */
    Long getContractFeeIdByCaseFeeId(Long caseFeeId);
} 