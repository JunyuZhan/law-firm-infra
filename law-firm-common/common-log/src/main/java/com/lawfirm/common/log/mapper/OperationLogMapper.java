package com.lawfirm.common.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.common.log.domain.OperationLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志Mapper接口
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLogDO> {
} 