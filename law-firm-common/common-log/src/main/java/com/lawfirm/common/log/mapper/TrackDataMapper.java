package com.lawfirm.common.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.common.log.domain.TrackData;
import org.apache.ibatis.annotations.Mapper;

/**
 * 行为跟踪数据Mapper接口
 */
@Mapper
public interface TrackDataMapper extends BaseMapper<TrackData> {
} 