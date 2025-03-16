package com.lawfirm.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.contract.util.ContractConverter;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.contract.dto.ContractCreateDTO;
import com.lawfirm.model.contract.dto.ContractQueryDTO;
import com.lawfirm.model.contract.dto.ContractUpdateDTO;
import com.lawfirm.model.contract.entity.Contract;
import com.lawfirm.model.contract.mapper.ContractMapper;
import com.lawfirm.model.contract.service.ContractService;
import com.lawfirm.model.contract.vo.ContractVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 合同服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContractServiceImpl extends BaseServiceImpl<ContractMapper, Contract> implements ContractService {

    private final ContractMapper contractMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createContract(ContractCreateDTO createDTO) {
        log.info("创建合同: {}", createDTO.getContractName());
        
        // 转换DTO为实体
        Contract contract = ContractConverter.toEntity(createDTO);
        
        // 保存合同
        save(contract);
        
        return contract.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateContract(ContractUpdateDTO updateDTO) {
        log.info("更新合同: {}", updateDTO.getId());
        
        // 获取合同
        Contract contract = getById(updateDTO.getId());
        if (contract == null) {
            log.error("合同不存在: {}", updateDTO.getId());
            return false;
        }
        
        // 更新合同信息
        ContractConverter.updateEntity(contract, updateDTO);
        
        // 保存更新
        return updateById(contract);
    }

    @Override
    public List<ContractVO> listContracts(ContractQueryDTO queryDTO) {
        log.info("查询合同列表");
        
        // 构建查询条件
        LambdaQueryWrapper<Contract> wrapper = buildQueryWrapper(queryDTO);
        
        // 查询合同列表
        List<Contract> contracts = list(wrapper);
        
        // 转换为VO
        return contracts.stream()
                .map(ContractConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public IPage<ContractVO> pageContracts(IPage<ContractVO> page, ContractQueryDTO queryDTO) {
        log.info("分页查询合同列表: 页码={}, 页大小={}", page.getCurrent(), page.getSize());
        
        // 构建查询条件
        LambdaQueryWrapper<Contract> wrapper = buildQueryWrapper(queryDTO);
        
        // 分页查询
        IPage<Contract> contractPage = page(page.convert(c -> new Contract()), wrapper);
        
        // 转换为VO
        return contractPage.convert(ContractConverter::toVO);
    }
    
    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<Contract> buildQueryWrapper(ContractQueryDTO queryDTO) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        
        // 合同编号
        if (queryDTO.getContractNo() != null) {
            wrapper.like(Contract::getContractNo, queryDTO.getContractNo());
        }
        
        // 合同名称
        if (queryDTO.getContractName() != null) {
            wrapper.like(Contract::getContractName, queryDTO.getContractName());
        }
        
        // 合同类型
        if (queryDTO.getContractType() != null) {
            wrapper.eq(Contract::getContractType, queryDTO.getContractType());
        }
        
        // 合同状态
        if (queryDTO.getStatus() != null) {
            wrapper.eq(Contract::getStatus, queryDTO.getStatus());
        }
        
        // 客户ID
        if (queryDTO.getClientId() != null) {
            wrapper.eq(Contract::getClientId, queryDTO.getClientId());
        }
        
        // 排序
        wrapper.orderByDesc(Contract::getUpdateTime);
        
        return wrapper;
    }
} 