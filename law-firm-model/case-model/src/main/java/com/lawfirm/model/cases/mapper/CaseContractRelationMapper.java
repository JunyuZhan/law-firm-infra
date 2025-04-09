package com.lawfirm.model.cases.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.CaseContractRelation;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 案件合同关联Mapper接口
 */
public interface CaseContractRelationMapper extends BaseMapper<CaseContractRelation> {
    
    /**
     * 根据案件ID和合同ID查询关联关系
     *
     * @param caseId 案件ID
     * @param contractId 合同ID
     * @return 关联关系实体
     */
    @Select("SELECT * FROM case_contract_relation WHERE case_id = #{caseId} AND contract_id = #{contractId} AND deleted = 0")
    CaseContractRelation selectByCaseIdAndContractId(@Param("caseId") Long caseId, @Param("contractId") Long contractId);
    
    /**
     * 根据案件ID查询所有关联的合同ID
     *
     * @param caseId 案件ID
     * @return 合同ID列表
     */
    @Select("SELECT contract_id FROM case_contract_relation WHERE case_id = #{caseId} AND deleted = 0")
    List<Long> selectContractIdsByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据合同ID查询所有关联的案件ID
     *
     * @param contractId 合同ID
     * @return 案件ID列表
     */
    @Select("SELECT case_id FROM case_contract_relation WHERE contract_id = #{contractId} AND deleted = 0")
    List<Long> selectCaseIdsByContractId(@Param("contractId") Long contractId);
    
    /**
     * 根据案件ID和合同ID删除关联关系（逻辑删除）
     *
     * @param caseId 案件ID
     * @param contractId 合同ID
     * @return 影响的行数
     */
    @Update("UPDATE case_contract_relation SET deleted = 1, update_time = NOW() WHERE case_id = #{caseId} AND contract_id = #{contractId} AND deleted = 0")
    int deleteByCaseIdAndContractId(@Param("caseId") Long caseId, @Param("contractId") Long contractId);
} 