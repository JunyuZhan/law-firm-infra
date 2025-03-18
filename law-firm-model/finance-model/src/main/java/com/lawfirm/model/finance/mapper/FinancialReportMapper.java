package com.lawfirm.model.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.finance.entity.FinancialReport;
import org.apache.ibatis.annotations.Mapper;

/**
 * 财务报表Mapper接口
 */
@Mapper
public interface FinancialReportMapper extends BaseMapper<FinancialReport> {
}