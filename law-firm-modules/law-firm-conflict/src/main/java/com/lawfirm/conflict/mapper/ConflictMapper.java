package com.lawfirm.conflict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.conflict.entity.Conflict;
import org.apache.ibatis.annotations.Mapper;

/**
 * 利益冲突Mapper接口
 */
@Mapper
public interface ConflictMapper extends BaseMapper<Conflict> {
}
