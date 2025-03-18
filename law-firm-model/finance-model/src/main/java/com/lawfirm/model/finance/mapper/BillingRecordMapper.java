package com.lawfirm.model.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.finance.entity.BillingRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账单记录Mapper接口
 */
@Mapper
public interface BillingRecordMapper extends BaseMapper<BillingRecord> {
} 