package com.lawfirm.model.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.personnel.entity.Contract;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 人事合同数据访问接口
 * 重命名为PersonnelContractMapper避免与合同模块中的同名接口冲突
 */
@Mapper
public interface PersonnelContractMapper extends BaseMapper<Contract> {

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