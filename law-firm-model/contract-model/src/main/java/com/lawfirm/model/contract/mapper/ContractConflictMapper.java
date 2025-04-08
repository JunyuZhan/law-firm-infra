package com.lawfirm.model.contract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.contract.entity.ContractConflict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 合同冲突Mapper接口
 */
@Mapper
public interface ContractConflictMapper extends BaseMapper<ContractConflict> {
    
    /**
     * 查找客户冲突
     */
    List<ContractConflict> findClientConflicts(@Param("clientIds") List<Long> clientIds, @Param("contractId") Long contractId);
    
    /**
     * 查找律师冲突
     */
    List<ContractConflict> findLawyerConflicts(@Param("lawyerIds") List<Long> lawyerIds, @Param("contractId") Long contractId);
    
    /**
     * 查找案件冲突
     */
    List<ContractConflict> findCaseConflicts(@Param("caseIds") List<Long> caseIds, @Param("contractId") Long contractId);
    
    /**
     * 查找合同关联的客户ID列表
     */
    List<Long> findContractClientIds(@Param("contractId") Long contractId);
    
    /**
     * 查找合同关联的律师ID列表
     */
    List<Long> findContractLawyerIds(@Param("contractId") Long contractId);
    
    /**
     * 查找合同关联的案件ID列表
     */
    List<Long> findContractCaseIds(@Param("contractId") Long contractId);
} 