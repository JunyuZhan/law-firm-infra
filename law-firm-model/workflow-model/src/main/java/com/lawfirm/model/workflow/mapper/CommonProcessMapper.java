package com.lawfirm.model.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.workflow.entity.common.CommonProcess;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通用流程Mapper接口
 * 负责CommonProcess实体的数据访问操作
 */
@Mapper
public interface CommonProcessMapper extends BaseMapper<CommonProcess> {
    // 可以添加自定义查询方法
} 