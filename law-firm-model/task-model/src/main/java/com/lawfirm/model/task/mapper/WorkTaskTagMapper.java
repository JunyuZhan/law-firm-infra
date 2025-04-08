package com.lawfirm.model.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.task.constants.WorkTaskSqlConstants;
import com.lawfirm.model.task.entity.WorkTaskTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 任务标签 Mapper 接口
 */
@Mapper
public interface WorkTaskTagMapper extends BaseMapper<WorkTaskTag> {

    /**
     * 根据任务ID查询标签列表
     *
     * @param taskId 任务ID
     * @return 标签列表
     */
    @Select(WorkTaskSqlConstants.Tag.SELECT_TASK_TAGS)
    List<WorkTaskTag> selectByTaskId(Long taskId);

    /**
     * 查询热门标签
     *
     * @param limit 数量限制
     * @return 标签列表
     */
    @Select("SELECT * FROM work_task_tag ORDER BY usage_count DESC LIMIT #{limit}")
    List<WorkTaskTag> selectHotTags(Integer limit);

    /**
     * 根据名称查询标签
     */
    @Select("SELECT * FROM work_task_tag WHERE name = #{name}")
    WorkTaskTag selectByName(String name);

    /**
     * 查询最近使用的标签
     */
    @Select("SELECT * FROM work_task_tag ORDER BY update_time DESC LIMIT #{limit}")
    List<WorkTaskTag> selectRecent(Integer limit);
} 