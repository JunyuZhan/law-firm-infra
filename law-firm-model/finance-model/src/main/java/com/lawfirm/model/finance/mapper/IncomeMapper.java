package com.lawfirm.model.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.finance.constant.FinanceSqlConstants;
import com.lawfirm.model.finance.entity.Income;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 收入Mapper接口
 */
@Mapper
public interface IncomeMapper extends BaseMapper<Income> {

    /**
     * 按案件ID分组统计收入
     * @return 案件收入统计结果
     */
    @Select(FinanceSqlConstants.Income.STATISTIC_INCOME_BY_CASE)
    List<Map<String, Object>> statisticIncomeByCase();
} 