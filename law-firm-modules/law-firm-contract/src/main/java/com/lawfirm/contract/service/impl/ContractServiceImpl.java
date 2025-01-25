package com.lawfirm.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.contract.constant.ContractConstant.ContractStatus;
import com.lawfirm.contract.entity.Contract;
import com.lawfirm.contract.exception.ContractException;
import com.lawfirm.contract.mapper.ContractMapper;
import com.lawfirm.contract.service.ContractApprovalService;
import com.lawfirm.contract.service.ContractService;
import com.lawfirm.contract.util.ContractNoGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 合同服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements ContractService {

    private final ContractNoGenerator contractNoGenerator;
    private final ContractApprovalService contractApprovalService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createContract(Contract contract) {
        // 生成合同编号
        String contractType = getContractTypeCode(contract.getType());
        contract.setContractNo(contractNoGenerator.generate(contractType));
        
        // 设置初始状态
        contract.setStatus(ContractStatus.DRAFT);
        
        // 保存合同信息
        save(contract);
        return contract.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateContract(Contract contract) {
        // 校验合同状态
        Contract oldContract = getById(contract.getId());
        if (oldContract == null) {
            throw new ContractException("合同不存在");
        }
        if (oldContract.getStatus() != ContractStatus.DRAFT) {
            throw new ContractException("只能修改草稿状态的合同");
        }
        
        // 更新合同信息
        updateById(contract);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteContract(Long id) {
        // 校验合同状态
        Contract contract = getById(id);
        if (contract == null) {
            throw new ContractException("合同不存在");
        }
        if (contract.getStatus() != ContractStatus.DRAFT) {
            throw new ContractException("只能删除草稿状态的合同");
        }
        
        // 删除合同
        removeById(id);
    }

    @Override
    public Contract getContractDetail(Long id) {
        return getById(id);
    }

    @Override
    public IPage<Contract> pageContracts(int page, int size, Integer type, Integer status, String keyword) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(type != null, Contract::getType, type)
                .eq(status != null, Contract::getStatus, status)
                .and(StringUtils.isNotBlank(keyword), w -> w
                        .like(Contract::getContractNo, keyword)
                        .or()
                        .like(Contract::getName, keyword)
                        .or()
                        .like(Contract::getClientName, keyword)
                        .or()
                        .like(Contract::getLawyerName, keyword))
                .orderByDesc(Contract::getCreateTime);
        
        return page(new Page<>(page, size), wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitApproval(Long id) {
        // 校验合同状态
        Contract contract = getById(id);
        if (contract == null) {
            throw new ContractException("合同不存在");
        }
        if (contract.getStatus() != ContractStatus.DRAFT) {
            throw new ContractException("只能提交草稿状态的合同");
        }
        
        // 更新合同状态
        Contract updateContract = new Contract();
        updateContract.setId(id);
        updateContract.setStatus(ContractStatus.APPROVING);
        updateById(updateContract);
        
        // 创建审批记录
        contractApprovalService.createApproval(id, 1, contract.getDepartmentId(), "部门负责人");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdrawApproval(Long id) {
        // 校验合同状态
        Contract contract = getById(id);
        if (contract == null) {
            throw new ContractException("合同不存在");
        }
        if (contract.getStatus() != ContractStatus.APPROVING) {
            throw new ContractException("只能撤回审核中的合同");
        }
        
        // 更新合同状态
        Contract updateContract = new Contract();
        updateContract.setId(id);
        updateContract.setStatus(ContractStatus.DRAFT);
        updateById(updateContract);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateContract(Long id, String reason) {
        // 校验合同状态
        Contract contract = getById(id);
        if (contract == null) {
            throw new ContractException("合同不存在");
        }
        if (contract.getStatus() != ContractStatus.EFFECTIVE) {
            throw new ContractException("只能终止已生效的合同");
        }
        
        // 更新合同状态
        Contract updateContract = new Contract();
        updateContract.setId(id);
        updateContract.setStatus(ContractStatus.TERMINATED);
        updateContract.setRemark(reason);
        updateById(updateContract);
    }

    /**
     * 获取合同类型编码
     */
    private String getContractTypeCode(Integer type) {
        switch (type) {
            case 1:
                return "CT"; // 常规合同
            case 2:
                return "ET"; // 委托合同
            case 3:
                return "CS"; // 顾问合同
            default:
                throw new ContractException("无效的合同类型");
        }
    }
} 