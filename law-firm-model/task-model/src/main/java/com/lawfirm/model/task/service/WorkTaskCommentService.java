package com.lawfirm.model.task.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.task.dto.WorkTaskCommentDTO;
import com.lawfirm.model.task.entity.WorkTaskComment;

import java.util.List;

/**
 * 工作任务评论服务接口
 */
public interface WorkTaskCommentService extends BaseService<WorkTaskComment> {
    
    /**
     * 添加任务评论
     *
     * @param commentDTO 任务评论DTO
     * @return 创建的任务评论ID
     */
    Long addComment(WorkTaskCommentDTO commentDTO);
    
    /**
     * 创建工作任务评论
     *
     * @param commentDTO 工作任务评论DTO
     * @return 创建的工作任务评论ID
     */
    Long createComment(WorkTaskCommentDTO commentDTO);
    
    /**
     * 更新工作任务评论
     *
     * @param commentDTO 工作任务评论DTO
     */
    void updateComment(WorkTaskCommentDTO commentDTO);
    
    /**
     * 删除工作任务评论
     *
     * @param commentId 工作任务评论ID
     */
    void deleteComment(Long commentId);
    
    /**
     * 获取工作任务评论详情
     *
     * @param commentId 工作任务评论ID
     * @return 工作任务评论DTO
     */
    WorkTaskCommentDTO getCommentDetail(Long commentId);
    
    /**
     * 获取任务评论列表
     *
     * @param taskId 任务ID
     * @return 任务评论列表
     */
    List<WorkTaskCommentDTO> getTaskComments(Long taskId);
    
    /**
     * 查询工作任务评论列表
     *
     * @param taskId 工作任务ID
     * @return 工作任务评论列表
     */
    List<WorkTaskCommentDTO> queryCommentList(Long taskId);
    
    /**
     * 回复工作任务评论
     *
     * @param parentId 父评论ID
     * @param commentDTO 回复内容
     * @return 创建的评论ID
     */
    Long replyComment(Long parentId, WorkTaskCommentDTO commentDTO);
    
    /**
     * 获取评论的回复列表
     *
     * @param parentId 父评论ID
     * @return 回复列表
     */
    List<WorkTaskCommentDTO> getCommentReplies(Long parentId);
} 