package com.lawfirm.model.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
    @Select("SELECT case_id, SUM(amount) as amount, COUNT(1) as count FROM fin_income GROUP BY case_id")
    List<Map<String, Object>> statisticIncomeByCase();
} 