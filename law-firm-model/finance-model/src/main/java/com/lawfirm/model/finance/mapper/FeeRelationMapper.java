package com.lawfirm.model.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.finance.entity.FeeRelation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 费用关联Mapper接口
 */
public interface FeeRelationMapper extends BaseMapper<FeeRelation> {

    /**
     * 根据财务费用ID查询关联关系
     *
     * @param financeFeeId 财务费用ID
     * @return 费用关联实体
     */
    @Select("SELECT * FROM fin_fee_relation WHERE finance_fee_id = #{financeFeeId} AND deleted = 0")
    FeeRelation selectByFinanceFeeId(@Param("financeFeeId") Long financeFeeId);
    
    /**
     * 根据合同费用ID查询关联关系
     *
     * @param contractFeeId 合同费用ID
     * @return 费用关联实体
     */
    @Select("SELECT * FROM fin_fee_relation WHERE source_type = 'CONTRACT' AND source_id = #{contractFeeId} AND deleted = 0")
    FeeRelation selectByContractFeeId(@Param("contractFeeId") Long contractFeeId);
    
    /**
     * 根据案件费用ID查询关联关系
     *
     * @param caseFeeId 案件费用ID
     * @return 费用关联实体
     */
    @Select("SELECT * FROM fin_fee_relation WHERE source_type = 'CASE' AND source_id = #{caseFeeId} AND deleted = 0")
    FeeRelation selectByCaseFeeId(@Param("caseFeeId") Long caseFeeId);
    
    /**
     * 根据合同ID查询关联的费用关系列表
     *
     * @param contractId 合同ID
     * @return 费用关联实体列表
     */
    @Select("SELECT * FROM fin_fee_relation WHERE contract_id = #{contractId} AND deleted = 0")
    java.util.List<FeeRelation> selectByContractId(@Param("contractId") Long contractId);
    
    /**
     * 根据案件ID查询关联的费用关系列表
     *
     * @param caseId 案件ID
     * @return 费用关联实体列表
     */
    @Select("SELECT * FROM fin_fee_relation WHERE case_id = #{caseId} AND deleted = 0")
    java.util.List<FeeRelation> selectByCaseId(@Param("caseId") Long caseId);
} 