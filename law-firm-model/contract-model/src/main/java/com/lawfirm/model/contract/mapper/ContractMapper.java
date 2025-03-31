package com.lawfirm.model.contract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.contract.entity.Contract;
import com.lawfirm.model.contract.constant.ContractSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 合同Mapper接口
 */
@Mapper
public interface ContractMapper extends BaseMapper<Contract> {
    
    /**
     * 根据合同编号查询合同
     *
     * @param contractNo 合同编号
     * @return 合同信息
     */
    @Select(ContractSqlConstants.Contract.SELECT_BY_CONTRACT_NO)
    Contract selectByContractNo(@Param("contractNo") String contractNo);
    
    /**
     * 根据客户ID查询合同列表
     *
     * @param clientId 客户ID
     * @return 合同列表
     */
    @Select(ContractSqlConstants.Contract.SELECT_BY_CLIENT_ID)
    List<Contract> selectByClientId(@Param("clientId") Long clientId);
    
    /**
     * 根据案件ID查询合同列表
     *
     * @param caseId 案件ID
     * @return 合同列表
     */
    @Select(ContractSqlConstants.Contract.SELECT_BY_CASE_ID)
    List<Contract> selectByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据状态查询合同列表
     *
     * @param status 状态
     * @return 合同列表
     */
    @Select(ContractSqlConstants.Contract.SELECT_BY_STATUS)
    List<Contract> selectByStatus(@Param("status") Integer status);
} 