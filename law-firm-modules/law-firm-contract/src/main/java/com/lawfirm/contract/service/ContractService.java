package com.lawfirm.contract.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.contract.entity.Contract;

/**
 * 合同服务接口
 */
public interface ContractService extends IService<Contract> {

    /**
     * 创建合同
     *
     * @param contract 合同信息
     * @return 合同ID
     */
    Long createContract(Contract contract);

    /**
     * 更新合同
     *
     * @param contract 合同信息
     */
    void updateContract(Contract contract);

    /**
     * 删除合同
     *
     * @param id 合同ID
     */
    void deleteContract(Long id);

    /**
     * 获取合同详情
     *
     * @param id 合同ID
     * @return 合同信息
     */
    Contract getContractDetail(Long id);

    /**
     * 分页查询合同列表
     *
     * @param page    页码
     * @param size    每页大小
     * @param type    合同类型
     * @param status  合同状态
     * @param keyword 关键字
     * @return 合同列表
     */
    IPage<Contract> pageContracts(int page, int size, Integer type, Integer status, String keyword);

    /**
     * 提交合同审批
     *
     * @param id 合同ID
     */
    void submitApproval(Long id);

    /**
     * 撤回合同审批
     *
     * @param id 合同ID
     */
    void withdrawApproval(Long id);

    /**
     * 终止合同
     *
     * @param id     合同ID
     * @param reason 终止原因
     */
    void terminateContract(Long id, String reason);
} 