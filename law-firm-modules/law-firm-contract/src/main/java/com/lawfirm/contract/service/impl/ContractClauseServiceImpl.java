package com.lawfirm.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.contract.constant.ContractConstant.ClauseType;
import com.lawfirm.contract.entity.Contract;
import com.lawfirm.contract.entity.ContractClause;
import com.lawfirm.contract.exception.ContractException;
import com.lawfirm.contract.mapper.ContractClauseMapper;
import com.lawfirm.contract.service.ContractClauseService;
import com.lawfirm.contract.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 合同条款服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContractClauseServiceImpl extends ServiceImpl<ContractClauseMapper, ContractClause> implements ContractClauseService {

    private final ContractService contractService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createClause(ContractClause contractClause) {
        // 校验合同状态
        Contract contract = contractService.getById(contractClause.getContractId());
        if (contract == null) {
            throw new ContractException("合同不存在");
        }

        // 设置条款序号
        if (contractClause.getOrderNum() == null) {
            Integer maxOrderNum = getMaxOrderNum(contractClause.getContractId());
            contractClause.setOrderNum(maxOrderNum + 1);
        }

        // 保存条款
        save(contractClause);
        return contractClause.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createClauses(Long contractId, List<ContractClause> clauses) {
        // 校验合同状态
        Contract contract = contractService.getById(contractId);
        if (contract == null) {
            throw new ContractException("合同不存在");
        }

        // 设置合同ID和序号
        Integer maxOrderNum = getMaxOrderNum(contractId);
        for (int i = 0; i < clauses.size(); i++) {
            ContractClause clause = clauses.get(i);
            clause.setContractId(contractId);
            clause.setOrderNum(maxOrderNum + i + 1);
        }

        // 批量保存条款
        saveBatch(clauses);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateClause(ContractClause contractClause) {
        // 校验条款是否存在
        ContractClause oldClause = getById(contractClause.getId());
        if (oldClause == null) {
            throw new ContractException("条款不存在");
        }

        // 校验合同状态
        Contract contract = contractService.getById(oldClause.getContractId());
        if (contract == null) {
            throw new ContractException("合同不存在");
        }

        // 更新条款
        updateById(contractClause);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteClause(Long id) {
        // 校验条款是否存在
        ContractClause clause = getById(id);
        if (clause == null) {
            throw new ContractException("条款不存在");
        }

        // 校验合同状态
        Contract contract = contractService.getById(clause.getContractId());
        if (contract == null) {
            throw new ContractException("合同不存在");
        }

        // 删除条款
        removeById(id);
    }

    @Override
    public List<ContractClause> getContractClauses(Long contractId) {
        LambdaQueryWrapper<ContractClause> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContractClause::getContractId, contractId)
                .orderByAsc(ContractClause::getOrderNum);
        return list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adjustClauseOrder(Long id, Integer orderNum) {
        // 校验条款是否存在
        ContractClause clause = getById(id);
        if (clause == null) {
            throw new ContractException("条款不存在");
        }

        // 更新序号
        ContractClause updateClause = new ContractClause();
        updateClause.setId(id);
        updateClause.setOrderNum(orderNum);
        updateById(updateClause);
    }

    @Override
    public boolean validateRequiredClauses(Long contractId) {
        // 获取所有必要条款
        LambdaQueryWrapper<ContractClause> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContractClause::getContractId, contractId)
                .eq(ContractClause::getRequired, true);
        List<ContractClause> requiredClauses = list(wrapper);

        // 检查基本条款是否存在
        List<ContractClause> basicClauses = requiredClauses.stream()
                .filter(clause -> clause.getType().equals(ClauseType.BASIC))
                .collect(Collectors.toList());
        if (basicClauses.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * 获取最大序号
     *
     * @param contractId 合同ID
     * @return 最大序号
     */
    private Integer getMaxOrderNum(Long contractId) {
        LambdaQueryWrapper<ContractClause> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContractClause::getContractId, contractId)
                .orderByDesc(ContractClause::getOrderNum)
                .last("LIMIT 1");
        ContractClause clause = getOne(wrapper);
        return clause != null ? clause.getOrderNum() : 0;
    }
} 