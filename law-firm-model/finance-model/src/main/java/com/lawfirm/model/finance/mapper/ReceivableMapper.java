package com.lawfirm.model.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.finance.entity.Receivable;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应收账款数据库操作接口
 */
@Mapper
public interface ReceivableMapper extends BaseMapper<Receivable> {
} 