package com.lawfirm.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.finance.entity.ExpenseRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支出记录Mapper接口
 */
@Mapper
public interface ExpenseRecordMapper extends BaseMapper<ExpenseRecord> {
} 