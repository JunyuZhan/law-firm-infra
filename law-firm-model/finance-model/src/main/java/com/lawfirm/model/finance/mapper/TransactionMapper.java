package com.lawfirm.model.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.finance.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;

/**
 * 交易Mapper接口
 */
@Mapper
public interface TransactionMapper extends BaseMapper<Transaction> {
} 