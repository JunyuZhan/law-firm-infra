package com.lawfirm.model.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.workflow.entity.base.ProcessTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务Mapper接口
 * 负责ProcessTask实体的数据访问操作
 */
@Mapper
public interface TaskMapper extends BaseMapper<ProcessTask> {
    // 可以添加自定义查询方法
} 