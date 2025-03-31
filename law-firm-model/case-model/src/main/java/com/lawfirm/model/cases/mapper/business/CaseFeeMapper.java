package com.lawfirm.model.cases.mapper.business;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.business.CaseFee;
import com.lawfirm.model.cases.constants.CaseSqlConstants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * 案件费用Mapper接口
 */
public interface CaseFeeMapper extends BaseMapper<CaseFee> {
    
    /**
     * 根据案件ID查询费用
     *
     * @param caseId 案件ID
     * @return 费用列表
     */
    @Select(CaseSqlConstants.Fee.SELECT_BY_CASE_ID)
    List<CaseFee> selectByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据费用类型查询
     *
     * @param caseId 案件ID
     * @param feeType 费用类型
     * @return 费用列表
     */
    @Select(CaseSqlConstants.Fee.SELECT_BY_TYPE)
    List<CaseFee> selectByType(@Param("caseId") Long caseId, @Param("feeType") Integer feeType);
    
    /**
     * 统计案件费用总额
     *
     * @param caseId 案件ID
     * @return 费用总额
     */
    @Select(CaseSqlConstants.Fee.SUM_AMOUNT_BY_CASE_ID)
    BigDecimal sumAmountByCaseId(@Param("caseId") Long caseId);
} 