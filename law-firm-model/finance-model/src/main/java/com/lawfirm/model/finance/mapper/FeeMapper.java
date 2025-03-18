package com.lawfirm.model.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.finance.entity.Fee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 费用Mapper接口
 */
@Mapper
public interface FeeMapper extends BaseMapper<Fee> {
} 