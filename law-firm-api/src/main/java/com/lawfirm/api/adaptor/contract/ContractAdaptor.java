package com.lawfirm.api.adaptor.contract;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.contract.dto.business.ContractDTO;
import com.lawfirm.model.contract.service.business.ContractService;
import com.lawfirm.model.contract.vo.business.ContractVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 合同适配器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ContractAdaptor extends BaseAdaptor {

    private final ContractService contractService;

    /**
     * 创建合同
     */
    public Long createContract(ContractDTO contractDTO) {
        log.info("创建合同: {}", contractDTO);
        return contractService.createContract(contractDTO);
    }

    /**
     * 更新合同
     */
    public boolean updateContract(ContractDTO contractDTO) {
        log.info("更新合同: {}", contractDTO);
        return contractService.updateContract(contractDTO);
    }

    /**
     * 删除合同
     */
    public boolean deleteContract(Long contractId) {
        log.info("删除合同: {}", contractId);
        return contractService.deleteContract(contractId);
    }

    /**
     * 获取合同详情
     */
    public ContractVO getContractDetail(Long contractId) {
        log.info("获取合同详情: {}", contractId);
        return contractService.getContractDetail(contractId);
    }

    /**
     * 获取客户的所有合同
     */
    public List<ContractVO> listClientContracts(Long clientId) {
        log.info("获取客户的所有合同: clientId={}", clientId);
        return contractService.listClientContracts(clientId);
    }

    /**
     * 分页查询合同
     */
    public IPage<ContractVO> pageContracts(Long clientId, Integer contractType, Integer contractStatus, Integer pageNum, Integer pageSize) {
        log.info("分页查询合同: clientId={}, contractType={}, contractStatus={}, pageNum={}, pageSize={}", 
                clientId, contractType, contractStatus, pageNum, pageSize);
        return contractService.pageContracts(clientId, contractType, contractStatus, pageNum, pageSize);
    }

    /**
     * 变更合同状态
     */
    public boolean changeContractStatus(Long contractId, Integer targetStatus, String reason) {
        log.info("变更合同状态: contractId={}, targetStatus={}, reason={}", contractId, targetStatus, reason);
        return contractService.changeContractStatus(contractId, targetStatus, reason);
    }

    /**
     * 终止合同
     */
    public boolean terminateContract(Long contractId, String reason) {
        log.info("终止合同: contractId={}, reason={}", contractId, reason);
        return contractService.terminateContract(contractId, reason);
    }

    /**
     * 续签合同
     */
    public boolean renewContract(Long contractId, ContractDTO newContractDTO) {
        log.info("续签合同: contractId={}, newContractDTO={}", contractId, newContractDTO);
        return contractService.renewContract(contractId, newContractDTO);
    }

    /**
     * 检查合同是否存在
     */
    public boolean checkContractExists(Long contractId) {
        log.info("检查合同是否存在: {}", contractId);
        return contractService.checkContractExists(contractId);
    }

    /**
     * 统计客户合同数量
     */
    public int countContracts(Long clientId, Integer contractType, Integer contractStatus) {
        log.info("统计客户合同数量: clientId={}, contractType={}, contractStatus={}", 
                clientId, contractType, contractStatus);
        return contractService.countContracts(clientId, contractType, contractStatus);
    }
} 