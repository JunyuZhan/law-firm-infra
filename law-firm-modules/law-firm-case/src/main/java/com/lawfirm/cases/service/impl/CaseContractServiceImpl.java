package com.lawfirm.cases.service.impl;

import com.lawfirm.cases.exception.CaseException;
import com.lawfirm.cases.service.CaseContractService;
import com.lawfirm.model.cases.entity.CaseContractRelation;
import com.lawfirm.model.cases.mapper.CaseContractRelationMapper;
import com.lawfirm.model.cases.service.base.CaseService;
import com.lawfirm.model.cases.vo.business.CaseContractVO;
import com.lawfirm.model.contract.enums.ContractStatusEnum;
import com.lawfirm.model.contract.service.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 案件合同关联服务实现类
 * 负责处理案件与合同之间的关联关系，不直接管理合同
 */
@Slf4j
@Service("caseContractService")
@Primary
@ConditionalOnProperty(name = "law-firm.module.case", havingValue = "true", matchIfMissing = false)
public class CaseContractServiceImpl implements CaseContractService {

    @Autowired
    private CaseService caseService;
    
    @Autowired
    private ContractService contractService;
    
    @Autowired
    private CaseContractRelationMapper caseContractRelationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean associateContractWithCase(Long caseId, Long contractId) {
        log.info("关联合同到案件，案件ID：{}，合同ID：{}", caseId, contractId);
        
        // 验证案件是否存在
        if (!caseExists(caseId)) {
            throw CaseException.caseNotFound(caseId);
        }
        
        // 验证合同是否存在
        if (!contractExists(contractId)) {
            log.error("合同不存在，ID：{}", contractId);
            return false;
        }
        
        // 验证是否已关联
        CaseContractRelation existingRelation = caseContractRelationMapper.selectByCaseIdAndContractId(caseId, contractId);
        if (existingRelation != null) {
            log.info("合同已关联到案件，案件ID：{}，合同ID：{}", caseId, contractId);
            return true;
        }
        
        // 创建关联
        CaseContractRelation relation = new CaseContractRelation();
        relation.setCaseId(caseId);
        relation.setContractId(contractId);
        relation.setCreateTime(new Date());
        
        return caseContractRelationMapper.insert(relation) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disassociateContractFromCase(Long caseId, Long contractId) {
        log.info("解除案件与合同的关联，案件ID：{}，合同ID：{}", caseId, contractId);
        
        // 验证案件是否存在
        if (!caseExists(caseId)) {
            throw CaseException.caseNotFound(caseId);
        }
        
        // 删除关联
        return caseContractRelationMapper.deleteByCaseIdAndContractId(caseId, contractId) > 0;
    }

    @Override
    public List<CaseContractVO> listCaseContracts(Long caseId) {
        log.info("获取案件关联的所有合同，案件ID：{}", caseId);
        
        // 验证案件是否存在
        if (!caseExists(caseId)) {
            throw CaseException.caseNotFound(caseId);
        }
        
        // 获取案件关联的所有合同ID
        List<Long> contractIds = caseContractRelationMapper.selectContractIdsByCaseId(caseId);
        if (contractIds.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 查询合同详情
        return contractIds.stream()
                .map(this::convertToVO)
                .filter(vo -> vo != null)
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkCaseContractStatus(Long caseId) {
        log.info("检查案件是否有关联的有效合同，案件ID：{}", caseId);
        
        // 验证案件是否存在
        if (!caseExists(caseId)) {
            throw CaseException.caseNotFound(caseId);
        }
        
        // 获取案件关联的所有合同ID
        List<Long> contractIds = caseContractRelationMapper.selectContractIdsByCaseId(caseId);
        if (contractIds.isEmpty()) {
            return false;
        }
        
        // 检查是否有有效的合同
        for (Long contractId : contractIds) {
            try {
                var contractDetail = contractService.getContractDetail(contractId);
                if (contractDetail != null && 
                    contractDetail.getStatus() == ContractStatusEnum.EFFECTIVE.getCode()) {
                    return true;
                }
            } catch (Exception e) {
                log.error("获取合同状态失败，合同ID：{}", contractId, e);
            }
        }
        
        return false;
    }

    @Override
    public CaseContractVO getCaseContractDetail(Long caseId, Long contractId) {
        log.info("获取案件关联的特定合同详情，案件ID：{}，合同ID：{}", caseId, contractId);
        
        // 验证案件是否存在
        if (!caseExists(caseId)) {
            throw CaseException.caseNotFound(caseId);
        }
        
        // 验证合同是否关联到案件
        CaseContractRelation relation = caseContractRelationMapper.selectByCaseIdAndContractId(caseId, contractId);
        if (relation == null) {
            log.error("合同未关联到案件，案件ID：{}，合同ID：{}", caseId, contractId);
            throw CaseException.caseContractInvalid(caseId, contractId);
        }
        
        // 返回合同详情
        return convertToVO(contractId);
    }
    
    /**
     * 检查案件是否存在
     */
    private boolean caseExists(Long caseId) {
        if (caseId == null) {
            return false;
        }
        return caseService.getById(caseId) != null;
    }
    
    /**
     * 检查合同是否存在
     */
    private boolean contractExists(Long contractId) {
        try {
            return contractService.getById(contractId) != null;
        } catch (Exception e) {
            log.error("检查合同是否存在时发生异常，合同ID：{}", contractId, e);
            return false;
        }
    }
    
    /**
     * 将合同信息转换为VO对象
     */
    private CaseContractVO convertToVO(Long contractId) {
        // 从合同模块获取详情
        try {
            // 这里需要合同模块提供转换接口，临时使用简单转换
            var contractDetail = contractService.getContractDetail(contractId);
            if (contractDetail == null) {
                return null;
            }
            
            CaseContractVO vo = new CaseContractVO();
            vo.setId(contractId);
            vo.setContractTitle(contractDetail.getContractName());
            vo.setContractStatus(contractDetail.getStatus());
            // 设置其他字段...
            
            return vo;
        } catch (Exception e) {
            log.error("获取合同详情失败，ID：{}", contractId, e);
            return null;
        }
    }
} 