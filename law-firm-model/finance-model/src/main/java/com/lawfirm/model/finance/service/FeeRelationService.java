package com.lawfirm.model.finance.service;

import com.lawfirm.model.finance.entity.FeeRelation;

/**
 * 费用关联服务接口
 * 管理财务费用与合同费用、案件费用之间的关联关系
 */
public interface FeeRelationService {
    
    /**
     * 保存财务费用与合同费用的关联关系
     *
     * @param financeFeeId 财务费用ID
     * @param contractFeeId 合同费用ID
     * @param contractId 合同ID
     * @param contractNumber 合同编号
     * @return 是否成功
     */
    boolean saveContractFeeRelation(Long financeFeeId, Long contractFeeId, Long contractId, String contractNumber);
    
    /**
     * 保存财务费用与案件费用的关联关系
     *
     * @param financeFeeId 财务费用ID
     * @param caseFeeId 案件费用ID
     * @param caseId 案件ID
     * @param caseNumber 案件编号
     * @return 是否成功
     */
    boolean saveCaseFeeRelation(Long financeFeeId, Long caseFeeId, Long caseId, String caseNumber);
    
    /**
     * 保存合同费用与案件费用的关联关系
     *
     * @param contractFeeId 合同费用ID
     * @param caseFeeId 案件费用ID
     * @param contractId 合同ID
     * @param contractNumber 合同编号
     * @param caseId 案件ID
     * @param caseNumber 案件编号
     * @return 是否成功
     */
    boolean saveContractCaseFeeRelation(Long contractFeeId, Long caseFeeId, Long contractId, 
                                        String contractNumber, Long caseId, String caseNumber);
    
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
     * 根据合同费用ID查询关联的财务费用ID
     *
     * @param contractFeeId 合同费用ID
     * @return 财务费用ID，如果不存在则返回null
     */
    Long getFinanceFeeIdByContractFeeId(Long contractFeeId);
    
    /**
     * 根据案件费用ID查询关联的财务费用ID
     *
     * @param caseFeeId 案件费用ID
     * @return 财务费用ID，如果不存在则返回null
     */
    Long getFinanceFeeIdByCaseFeeId(Long caseFeeId);
    
    /**
     * 根据案件费用ID查询关联的合同费用ID
     *
     * @param caseFeeId 案件费用ID
     * @return 合同费用ID，如果不存在则返回null
     */
    Long getContractFeeIdByCaseFeeId(Long caseFeeId);
    
    /**
     * 根据合同费用ID查询关联的案件费用ID
     *
     * @param contractFeeId 合同费用ID
     * @return 案件费用ID，如果不存在则返回null
     */
    Long getCaseFeeIdByContractFeeId(Long contractFeeId);
    
    /**
     * 删除财务费用关联关系
     *
     * @param financeFeeId 财务费用ID
     * @return 是否成功
     */
    boolean deleteByFinanceFeeId(Long financeFeeId);
    
    /**
     * 删除合同费用关联关系
     *
     * @param contractFeeId 合同费用ID
     * @return 是否成功
     */
    boolean deleteByContractFeeId(Long contractFeeId);
    
    /**
     * 删除案件费用关联关系
     *
     * @param caseFeeId 案件费用ID
     * @return 是否成功
     */
    boolean deleteByCaseFeeId(Long caseFeeId);
}