package com.lawfirm.finance.service.impl;

import com.lawfirm.model.finance.entity.FeeRelation;
import com.lawfirm.model.finance.mapper.FeeRelationMapper;
import com.lawfirm.model.finance.service.FeeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * 费用关联服务实现类
 */
@Slf4j
@Service("feeRelationService")
public class FeeRelationServiceImpl implements FeeRelationService {

    @Autowired
    private FeeRelationMapper feeRelationMapper;

    @Override
    @Transactional
    public boolean saveContractFeeRelation(Long financeFeeId, Long contractFeeId, Long contractId, String contractNumber) {
        log.info("保存财务费用与合同费用关联关系: financeFeeId={}, contractFeeId={}", financeFeeId, contractFeeId);
        
        try {
            // 创建关联记录
            FeeRelation relation = new FeeRelation()
                    .setFinanceFeeId(financeFeeId)
                    .setSourceType("CONTRACT")
                    .setSourceId(contractFeeId)
                    .setContractId(contractId);
            
            // 保存关联记录
            return feeRelationMapper.insert(relation) > 0;
        } catch (Exception e) {
            log.error("保存财务费用与合同费用关联关系失败", e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean saveCaseFeeRelation(Long financeFeeId, Long caseFeeId, Long caseId, String caseNumber) {
        log.info("保存财务费用与案件费用关联关系: financeFeeId={}, caseFeeId={}", financeFeeId, caseFeeId);
        
        try {
            // 创建关联记录
            FeeRelation relation = new FeeRelation()
                    .setFinanceFeeId(financeFeeId)
                    .setSourceType("CASE")
                    .setSourceId(caseFeeId)
                    .setCaseId(caseId);
            
            // 保存关联记录
            return feeRelationMapper.insert(relation) > 0;
        } catch (Exception e) {
            log.error("保存财务费用与案件费用关联关系失败", e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean saveContractCaseFeeRelation(Long contractFeeId, Long caseFeeId, Long contractId, 
                                              String contractNumber, Long caseId, String caseNumber) {
        log.info("保存合同费用与案件费用关联关系: contractFeeId={}, caseFeeId={}", contractFeeId, caseFeeId);
        
        try {
            // 查询合同费用关联的财务费用
            Long financeFeeId = getFinanceFeeIdByContractFeeId(contractFeeId);
            if (financeFeeId == null) {
                log.warn("未找到合同费用对应的财务费用: contractFeeId={}", contractFeeId);
                return false;
            }
            
            // 创建关联记录
            FeeRelation relation = new FeeRelation()
                    .setFinanceFeeId(financeFeeId)
                    .setSourceType("CASE")
                    .setSourceId(caseFeeId)
                    .setContractId(contractId)
                    .setCaseId(caseId);
            
            // 保存关联记录
            return feeRelationMapper.insert(relation) > 0;
        } catch (Exception e) {
            log.error("保存合同费用与案件费用关联关系失败", e);
            return false;
        }
    }

    @Override
    public Long getContractFeeIdByFinanceFeeId(Long financeFeeId) {
        log.debug("根据财务费用ID查询关联的合同费用ID: financeFeeId={}", financeFeeId);
        
        try {
            FeeRelation relation = feeRelationMapper.selectByFinanceFeeId(financeFeeId);
            if (relation != null && "CONTRACT".equals(relation.getSourceType())) {
                return relation.getSourceId();
            }
            return null;
        } catch (Exception e) {
            log.error("查询关联的合同费用ID失败", e);
            return null;
        }
    }

    @Override
    public Long getCaseFeeIdByFinanceFeeId(Long financeFeeId) {
        log.debug("根据财务费用ID查询关联的案件费用ID: financeFeeId={}", financeFeeId);
        
        try {
            FeeRelation relation = feeRelationMapper.selectByFinanceFeeId(financeFeeId);
            if (relation != null && "CASE".equals(relation.getSourceType())) {
                return relation.getSourceId();
            }
            return null;
        } catch (Exception e) {
            log.error("查询关联的案件费用ID失败", e);
            return null;
        }
    }

    @Override
    public Long getFinanceFeeIdByContractFeeId(Long contractFeeId) {
        log.debug("根据合同费用ID查询关联的财务费用ID: contractFeeId={}", contractFeeId);
        
        try {
            FeeRelation relation = feeRelationMapper.selectByContractFeeId(contractFeeId);
            if (relation != null) {
                return relation.getFinanceFeeId();
            }
            return null;
        } catch (Exception e) {
            log.error("查询关联的财务费用ID失败", e);
            return null;
        }
    }

    @Override
    public Long getFinanceFeeIdByCaseFeeId(Long caseFeeId) {
        log.debug("根据案件费用ID查询关联的财务费用ID: caseFeeId={}", caseFeeId);
        
        try {
            FeeRelation relation = feeRelationMapper.selectByCaseFeeId(caseFeeId);
            if (relation != null) {
                return relation.getFinanceFeeId();
            }
            return null;
        } catch (Exception e) {
            log.error("查询关联的财务费用ID失败", e);
            return null;
        }
    }

    @Override
    public Long getContractFeeIdByCaseFeeId(Long caseFeeId) {
        log.debug("根据案件费用ID查询关联的合同费用ID: caseFeeId={}", caseFeeId);
        
        try {
            // 先获取财务费用ID
            Long financeFeeId = getFinanceFeeIdByCaseFeeId(caseFeeId);
            if (financeFeeId == null) {
                return null;
            }
            
            // 通过财务费用ID获取合同费用ID
            return getContractFeeIdByFinanceFeeId(financeFeeId);
        } catch (Exception e) {
            log.error("查询关联的合同费用ID失败", e);
            return null;
        }
    }

    @Override
    public Long getCaseFeeIdByContractFeeId(Long contractFeeId) {
        log.debug("根据合同费用ID查询关联的案件费用ID: contractFeeId={}", contractFeeId);
        
        try {
            // 先获取财务费用ID
            Long financeFeeId = getFinanceFeeIdByContractFeeId(contractFeeId);
            if (financeFeeId == null) {
                return null;
            }
            
            // 通过财务费用ID获取案件费用ID
            return getCaseFeeIdByFinanceFeeId(financeFeeId);
        } catch (Exception e) {
            log.error("查询关联的案件费用ID失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public boolean deleteByFinanceFeeId(Long financeFeeId) {
        log.info("删除财务费用关联关系: financeFeeId={}", financeFeeId);
        
        try {
            // 使用MyBatis-Plus的逻辑删除功能
            FeeRelation relation = new FeeRelation();
            relation.setFinanceFeeId(financeFeeId);
            relation.setDeleted(1);
            
            return feeRelationMapper.updateById(relation) > 0;
        } catch (Exception e) {
            log.error("删除财务费用关联关系失败", e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteByContractFeeId(Long contractFeeId) {
        log.info("删除合同费用关联关系: contractFeeId={}", contractFeeId);
        
        try {
            // 先查询关联的财务费用ID
            Long financeFeeId = getFinanceFeeIdByContractFeeId(contractFeeId);
            if (financeFeeId == null) {
                return false;
            }
            
            // 删除关联关系
            return deleteByFinanceFeeId(financeFeeId);
        } catch (Exception e) {
            log.error("删除合同费用关联关系失败", e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteByCaseFeeId(Long caseFeeId) {
        log.info("删除案件费用关联关系: caseFeeId={}", caseFeeId);
        
        try {
            // 先查询关联的财务费用ID
            Long financeFeeId = getFinanceFeeIdByCaseFeeId(caseFeeId);
            if (financeFeeId == null) {
                return false;
            }
            
            // 删除关联关系
            return deleteByFinanceFeeId(financeFeeId);
        } catch (Exception e) {
            log.error("删除案件费用关联关系失败", e);
            return false;
        }
    }
} 