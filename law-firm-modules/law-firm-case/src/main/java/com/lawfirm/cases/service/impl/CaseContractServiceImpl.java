package com.lawfirm.cases.service.impl;

import com.lawfirm.cases.exception.CaseException;
import com.lawfirm.cases.service.CaseContractService;
import com.lawfirm.model.cases.dto.business.CaseContractDTO;
import com.lawfirm.model.cases.vo.business.CaseContractVO;
import com.lawfirm.model.contract.enums.ContractStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 案件合同服务实现类
 */
@Slf4j
@Service("caseContractService")
public class CaseContractServiceImpl implements com.lawfirm.cases.service.CaseContractService {

    @Autowired
    private com.lawfirm.model.cases.service.business.CaseContractService modelCaseContractService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCaseContract(Long caseId, CaseContractDTO contractDTO) {
        log.info("创建案件合同，案件ID：{}，合同信息：{}", caseId, contractDTO);
        
        // 验证案件是否存在
        if (!modelCaseContractService.checkContractExists(caseId)) {
            throw CaseException.caseNotFound(caseId);
        }

        // 设置案件ID
        contractDTO.setCaseId(caseId);
        
        // 创建合同
        return modelCaseContractService.createContract(contractDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCaseContract(Long caseId, Long contractId, CaseContractDTO contractDTO) {
        log.info("更新案件合同，案件ID：{}，合同ID：{}，合同信息：{}", caseId, contractId, contractDTO);
        
        // 验证合同是否属于该案件
        CaseContractVO contract = modelCaseContractService.getContractDetail(contractId);
        if (contract == null || !contract.getCaseId().equals(caseId)) {
            throw CaseException.caseContractInvalid(caseId, contractId);
        }

        // 更新合同
        return modelCaseContractService.updateContract(contractDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCaseContract(Long caseId, Long contractId) {
        log.info("删除案件合同，案件ID：{}，合同ID：{}", caseId, contractId);
        
        // 验证合同是否属于该案件
        CaseContractVO contract = modelCaseContractService.getContractDetail(contractId);
        if (contract == null || !contract.getCaseId().equals(caseId)) {
            throw CaseException.caseContractInvalid(caseId, contractId);
        }

        // 删除合同
        return modelCaseContractService.deleteContract(contractId);
    }

    @Override
    public CaseContractVO getCaseContractDetail(Long caseId, Long contractId) {
        log.info("获取案件合同详情，案件ID：{}，合同ID：{}", caseId, contractId);
        
        // 验证合同是否属于该案件
        CaseContractVO contract = modelCaseContractService.getContractDetail(contractId);
        if (contract == null || !contract.getCaseId().equals(caseId)) {
            throw CaseException.caseContractInvalid(caseId, contractId);
        }

        return contract;
    }

    @Override
    public List<CaseContractVO> listCaseContracts(Long caseId) {
        log.info("获取案件的所有合同，案件ID：{}", caseId);
        return modelCaseContractService.listCaseContracts(caseId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean signCaseContract(Long caseId, Long contractId, Long signerId, String signatureData) {
        log.info("签署案件合同，案件ID：{}，合同ID：{}，签署人ID：{}", caseId, contractId, signerId);
        
        // 验证合同是否属于该案件
        CaseContractVO contract = modelCaseContractService.getContractDetail(contractId);
        if (contract == null || !contract.getCaseId().equals(caseId)) {
            throw CaseException.caseContractInvalid(caseId, contractId);
        }

        // 签署合同
        return modelCaseContractService.signContract(contractId, signerId, signatureData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reviewCaseContract(Long caseId, Long contractId, boolean approved, String opinion) {
        log.info("审核案件合同，案件ID：{}，合同ID：{}，审核结果：{}，审核意见：{}", 
                caseId, contractId, approved, opinion);
        
        // 验证合同是否属于该案件
        CaseContractVO contract = modelCaseContractService.getContractDetail(contractId);
        if (contract == null || !contract.getCaseId().equals(caseId)) {
            throw CaseException.caseContractInvalid(caseId, contractId);
        }

        // 审核合同
        return modelCaseContractService.reviewContract(contractId, approved, opinion);
    }

    @Override
    public boolean checkCaseContractStatus(Long caseId) {
        log.info("检查案件合同状态，案件ID：{}", caseId);
        
        // 获取案件的所有合同
        List<CaseContractVO> contracts = modelCaseContractService.listCaseContracts(caseId);
        
        // 检查是否有有效的合同
        return contracts.stream()
                .anyMatch(contract -> contract.getContractStatus() == ContractStatusEnum.EFFECTIVE.getCode());
    }
} 