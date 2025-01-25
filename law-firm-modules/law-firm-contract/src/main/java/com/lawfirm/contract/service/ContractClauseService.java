package com.lawfirm.contract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.contract.entity.ContractClause;
import java.util.List;

/**
 * 合同条款服务接口
 */
public interface ContractClauseService extends IService<ContractClause> {

    /**
     * 创建合同条款
     *
     * @param contractClause 条款信息
     * @return 条款ID
     */
    Long createClause(ContractClause contractClause);

    /**
     * 批量创建合同条款
     *
     * @param contractId 合同ID
     * @param clauses   条款列表
     */
    void createClauses(Long contractId, List<ContractClause> clauses);

    /**
     * 更新合同条款
     *
     * @param contractClause 条款信息
     */
    void updateClause(ContractClause contractClause);

    /**
     * 删除合同条款
     *
     * @param id 条款ID
     */
    void deleteClause(Long id);

    /**
     * 获取合同条款列表
     *
     * @param contractId 合同ID
     * @return 条款列表
     */
    List<ContractClause> getContractClauses(Long contractId);

    /**
     * 调整条款顺序
     *
     * @param id       条款ID
     * @param orderNum 新的序号
     */
    void adjustClauseOrder(Long id, Integer orderNum);

    /**
     * 验证合同必要条款
     *
     * @param contractId 合同ID
     * @return 是否包含所有必要条款
     */
    boolean validateRequiredClauses(Long contractId);
} 