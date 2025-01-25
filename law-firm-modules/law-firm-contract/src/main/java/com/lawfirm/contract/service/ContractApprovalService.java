package com.lawfirm.contract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.contract.entity.ContractApproval;

/**
 * 合同审批服务接口
 */
public interface ContractApprovalService extends IService<ContractApproval> {

    /**
     * 创建审批记录
     *
     * @param contractId   合同ID
     * @param node        审批节点
     * @param approverId  审批人ID
     * @param approverName 审批人姓名
     */
    void createApproval(Long contractId, Integer node, Long approverId, String approverName);

    /**
     * 审批通过
     *
     * @param id      审批记录ID
     * @param comment 审批意见
     */
    void approve(Long id, String comment);

    /**
     * 审批拒绝
     *
     * @param id      审批记录ID
     * @param comment 审批意见
     */
    void reject(Long id, String comment);

    /**
     * 获取合同的审批记录
     *
     * @param contractId 合同ID
     * @return 审批记录列表
     */
    java.util.List<ContractApproval> getContractApprovals(Long contractId);

    /**
     * 获取待审批记录
     *
     * @param approverId 审批人ID
     * @return 待审批记录列表
     */
    java.util.List<ContractApproval> getPendingApprovals(Long approverId);
} 