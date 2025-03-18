package com.lawfirm.model.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.finance.entity.PaymentPlan;
import org.apache.ibatis.annotations.Mapper;

/**
 * 付款计划Mapper接口
 */
@Mapper
public interface PaymentPlanMapper extends BaseMapper<PaymentPlan> {
} 