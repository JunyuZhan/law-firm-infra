package com.lawfirm.model.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.task.constants.WorkTaskSqlConstants;
import com.lawfirm.model.task.entity.WorkTaskComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 任务评论 Mapper 接口
 */
@Mapper
public interface WorkTaskCommentMapper extends BaseMapper<WorkTaskComment> {

    /**
     * 根据任务ID查询评论列表
     *
     * @param taskId 任务ID
     * @return 评论列表
     */
    @Select(WorkTaskSqlConstants.Comment.SELECT_TASK_COMMENTS)
    List<WorkTaskComment> selectByTaskId(Long taskId);

    /**
     * 查询评论回复列表
     *
     * @param commentId 评论ID
     * @return 回复列表
     */
    @Select("SELECT * FROM work_task_comment WHERE parent_id = #{commentId} ORDER BY create_time")
    List<WorkTaskComment> selectReplies(Long commentId);

    /**
     * 统计任务的评论数量
     */
    @Select("SELECT COUNT(*) FROM work_task_comment WHERE task_id = #{taskId}")
    Integer countByTaskId(Long taskId);

    /**
     * 查询最近的评论
     */
    @Select("SELECT * FROM work_task_comment ORDER BY create_time DESC LIMIT #{limit}")
    List<WorkTaskComment> selectRecent(Integer limit);
} 