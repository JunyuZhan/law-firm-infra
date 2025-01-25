package com.lawfirm.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.contract.entity.ContractApproval;
import com.lawfirm.contract.mapper.ContractApprovalMapper;
import com.lawfirm.contract.service.ContractApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同审批服务实现类
 */
@Service
@RequiredArgsConstructor
public class ContractApprovalServiceImpl extends ServiceImpl<ContractApprovalMapper, ContractApproval> implements ContractApprovalService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createApproval(Long contractId, Integer node, Long approverId, String approverName) {
        ContractApproval approval = new ContractApproval();
        approval.setContractId(contractId);
        approval.setNode(node);
        approval.setApproverId(approverId);
        approval.setApproverName(approverName);
        approval.setStatus(0); // 待审批
        save(approval);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id, String comment) {
        ContractApproval approval = getById(id);
        if (approval == null) {
            throw new RuntimeException("审批记录不存在");
        }
        if (!approval.getStatus().equals(0)) {
            throw new RuntimeException("该记录已审批");
        }

        approval.setStatus(1); // 通过
        approval.setComment(comment);
        approval.setApprovalTime(LocalDateTime.now());
        updateById(approval);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reject(Long id, String comment) {
        ContractApproval approval = getById(id);
        if (approval == null) {
            throw new RuntimeException("审批记录不存在");
        }
        if (!approval.getStatus().equals(0)) {
            throw new RuntimeException("该记录已审批");
        }

        approval.setStatus(2); // 驳回
        approval.setComment(comment);
        approval.setApprovalTime(LocalDateTime.now());
        updateById(approval);
    }

    @Override
    public List<ContractApproval> getContractApprovals(Long contractId) {
        return list(new LambdaQueryWrapper<ContractApproval>()
                .eq(ContractApproval::getContractId, contractId)
                .orderByAsc(ContractApproval::getNode));
    }

    @Override
    public List<ContractApproval> getPendingApprovals(Long approverId) {
        return list(new LambdaQueryWrapper<ContractApproval>()
                .eq(ContractApproval::getApproverId, approverId)
                .eq(ContractApproval::getStatus, 0)
                .orderByDesc(ContractApproval::getCreateTime));
    }
} 