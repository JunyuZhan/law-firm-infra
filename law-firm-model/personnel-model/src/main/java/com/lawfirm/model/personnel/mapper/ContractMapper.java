package com.lawfirm.model.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.personnel.entity.Contract;
import java.util.List;

/**
 * 合同数据访问接口
 */
public interface ContractMapper extends BaseMapper<Contract> {

    /**
     * 根据员工ID查询合同列表
     *
     * @param employeeId 员工ID
     * @return 合同列表
     */
    List<Contract> selectByEmployeeId(Long employeeId);

    /**
     * 查询员工当前生效的合同
     *
     * @param employeeId 员工ID
     * @return 合同信息
     */
    Contract selectActiveContract(Long employeeId);

    /**
     * 根据合同编号查询
     *
     * @param contractNumber 合同编号
     * @return 合同信息
     */
    Contract selectByContractNumber(String contractNumber);
} 