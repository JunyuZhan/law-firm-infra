package com.lawfirm.model.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.finance.entity.Expense;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支出Mapper接口
 */
@Mapper
public interface ExpenseMapper extends BaseMapper<Expense> {
} 