package com.lawfirm.api.adaptor.contract;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.contract.dto.ContractCreateDTO;
import com.lawfirm.model.contract.dto.ContractUpdateDTO;
import com.lawfirm.model.contract.dto.ContractQueryDTO;
import com.lawfirm.model.contract.service.ContractService;
import com.lawfirm.model.contract.vo.ContractVO;
import com.lawfirm.model.contract.vo.ContractDetailVO;
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
    public Long createContract(ContractCreateDTO createDTO) {
        log.info("创建合同: {}", createDTO);
        return contractService.createContract(createDTO);
    }

    /**
     * 更新合同
     */
    public boolean updateContract(ContractUpdateDTO updateDTO) {
        log.info("更新合同: {}", updateDTO);
        return contractService.updateContract(updateDTO);
    }

    /**
     * 获取合同详情
     */
    public ContractDetailVO getContractDetail(Long contractId) {
        log.info("获取合同详情: {}", contractId);
        return contractService.getContractDetail(contractId);
    }

    /**
     * 查询合同列表
     */
    public List<ContractVO> listContracts(ContractQueryDTO queryDTO) {
        log.info("查询合同列表: {}", queryDTO);
        return contractService.listContracts(queryDTO);
    }

    /**
     * 分页查询合同
     */
    public IPage<ContractVO> pageContracts(IPage<ContractVO> page, ContractQueryDTO queryDTO) {
        log.info("分页查询合同: page={}, queryDTO={}", page, queryDTO);
        return contractService.pageContracts(page, queryDTO);
    }
}