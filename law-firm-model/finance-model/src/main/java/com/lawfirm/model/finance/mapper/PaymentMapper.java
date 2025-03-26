package com.lawfirm.model.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.finance.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 付款数据访问层
 */
@Mapper
public interface PaymentMapper extends BaseMapper<Payment> {
    // 继承自BaseMapper的基础方法已经满足大部分需求
    // 如需自定义方法，可在此添加
} 