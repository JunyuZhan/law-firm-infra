package com.lawfirm.model.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.task.constants.WorkTaskSqlConstants;
import com.lawfirm.model.task.entity.WorkTaskTagRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 工作任务与标签关联关系Mapper接口
 */
@Mapper
public interface WorkTaskTagRelationMapper extends BaseMapper<WorkTaskTagRelation> {
    
    /**
     * 根据任务ID获取标签ID列表
     *
     * @param taskId 任务ID
     * @return 标签ID列表
     */
    @Select(WorkTaskSqlConstants.TagRelation.SELECT_TAG_IDS_BY_TASK_ID)
    List<Long> selectTagIdsByTaskId(Long taskId);
    
    /**
     * 根据标签ID获取任务ID列表
     *
     * @param tagId 标签ID
     * @return 任务ID列表
     */
    @Select(WorkTaskSqlConstants.TagRelation.SELECT_TASK_IDS_BY_TAG_ID)
    List<Long> selectTaskIdsByTagId(Long tagId);
} 