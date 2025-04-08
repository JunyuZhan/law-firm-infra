package com.lawfirm.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.task.dto.WorkTaskCommentDTO;
import com.lawfirm.model.task.entity.WorkTask;
import com.lawfirm.model.task.entity.WorkTaskComment;
import com.lawfirm.model.task.mapper.WorkTaskCommentMapper;
import com.lawfirm.model.task.service.WorkTaskCommentService;
import com.lawfirm.model.task.service.WorkTaskService;
import com.lawfirm.task.converter.WorkTaskCommentConverter;
import com.lawfirm.task.exception.TaskException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 工作任务评论服务实现类
 */
@Service("workTaskCommentService")
public class WorkTaskCommentServiceImpl extends ServiceImpl<WorkTaskCommentMapper, WorkTaskComment> implements WorkTaskCommentService {

    @Autowired
    private WorkTaskService workTaskService;
    
    @Autowired
    private WorkTaskCommentConverter workTaskCommentConverter;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addComment(WorkTaskCommentDTO commentDTO) {
        // 直接调用现有的createComment方法
        return createComment(commentDTO);
    }
    
    @Override
    public List<WorkTaskCommentDTO> getTaskComments(Long taskId) {
        // 直接调用现有的queryCommentList方法
        return queryCommentList(taskId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createComment(WorkTaskCommentDTO commentDTO) {
        // 校验参数
        validateCommentParams(commentDTO);
        
        // 检查任务是否存在
        WorkTask workTask = workTaskService.getById(commentDTO.getTaskId());
        if (workTask == null) {
            throw new TaskException("任务不存在");
        }
        
        // 转换为实体
        WorkTaskComment comment = workTaskCommentConverter.dtoToEntity(commentDTO);
        
        // 设置评论人
        comment.setCommenterId(getCurrentUserId());
        comment.setCreateBy(getCurrentUsername());
        comment.setUpdateBy(getCurrentUsername());
        
        // 保存评论
        save(comment);
        
        return comment.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateComment(WorkTaskCommentDTO commentDTO) {
        // 校验参数
        if (commentDTO.getId() == null) {
            throw new TaskException("评论ID不能为空");
        }
        validateCommentParams(commentDTO);
        
        // 检查评论是否存在
        WorkTaskComment existingComment = getById(commentDTO.getId());
        if (existingComment == null) {
            throw new TaskException("评论不存在");
        }
        
        // 检查是否有权限修改评论（只能修改自己的评论）
        if (!Objects.equals(existingComment.getCommenterId(), getCurrentUserId())) {
            throw new TaskException("无权修改该评论");
        }
        
        // 转换为实体
        WorkTaskComment comment = workTaskCommentConverter.dtoToEntity(commentDTO);
        comment.setUpdateBy(getCurrentUsername());
        
        // 更新评论
        updateById(comment);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long commentId) {
        // 检查评论是否存在
        WorkTaskComment comment = getById(commentId);
        if (comment == null) {
            throw new TaskException("评论不存在");
        }
        
        // 检查是否有权限删除评论（只能删除自己的评论）
        if (!Objects.equals(comment.getCommenterId(), getCurrentUserId())) {
            throw new TaskException("无权删除该评论");
        }
        
        // 删除评论
        removeById(commentId);
        
        // 删除该评论的所有回复
        LambdaQueryWrapper<WorkTaskComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkTaskComment::getParentId, commentId);
        remove(wrapper);
    }
    
    @Override
    public WorkTaskCommentDTO getCommentDetail(Long commentId) {
        // 获取评论
        WorkTaskComment comment = getById(commentId);
        if (comment == null) {
            throw new TaskException("评论不存在");
        }
        
        // 转换为DTO
        WorkTaskCommentDTO dto = workTaskCommentConverter.entityToDto(comment);
        
        // 获取回复列表
        List<WorkTaskCommentDTO> replies = getCommentReplies(commentId);
        dto.setReplies(replies);
        
        return dto;
    }
    
    @Override
    public List<WorkTaskCommentDTO> queryCommentList(Long taskId) {
        // 查询任务的顶级评论（没有父评论的）
        LambdaQueryWrapper<WorkTaskComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkTaskComment::getTaskId, taskId)
                .isNull(WorkTaskComment::getParentId)
                .orderByDesc(WorkTaskComment::getCreateTime);
        
        List<WorkTaskComment> comments = list(wrapper);
        
        // 转换为DTO
        List<WorkTaskCommentDTO> dtoList = comments.stream()
                .map(comment -> {
                    WorkTaskCommentDTO dto = workTaskCommentConverter.entityToDto(comment);
                    // 获取回复列表
                    List<WorkTaskCommentDTO> replies = getCommentReplies(comment.getId());
                    dto.setReplies(replies);
                    return dto;
                })
                .collect(Collectors.toList());
        
        return dtoList;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long replyComment(Long parentId, WorkTaskCommentDTO commentDTO) {
        // 检查父评论是否存在
        WorkTaskComment parentComment = getById(parentId);
        if (parentComment == null) {
            throw new TaskException("父评论不存在");
        }
        
        // 设置父评论ID和任务ID
        commentDTO.setParentId(parentId);
        commentDTO.setTaskId(parentComment.getTaskId());
        
        // 创建回复评论
        return createComment(commentDTO);
    }
    
    @Override
    public List<WorkTaskCommentDTO> getCommentReplies(Long parentId) {
        // 查询评论的回复
        LambdaQueryWrapper<WorkTaskComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkTaskComment::getParentId, parentId)
                .orderByAsc(WorkTaskComment::getCreateTime);
        
        List<WorkTaskComment> replies = list(wrapper);
        
        // 转换为DTO
        return replies.stream()
                .map(workTaskCommentConverter::entityToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * 校验评论参数
     */
    private void validateCommentParams(WorkTaskCommentDTO commentDTO) {
        if (commentDTO.getTaskId() == null) {
            throw new TaskException("任务ID不能为空");
        }
        
        if (!StringUtils.hasText(commentDTO.getContent())) {
            throw new TaskException("评论内容不能为空");
        }
    }
    
    @Override
    public Long getCurrentUserId() {
        return 1L; // TODO: 从安全上下文获取当前用户ID
    }
    
    @Override
    public String getCurrentUsername() {
        return "admin"; // TODO: 从安全上下文获取当前用户名
    }
    
    @Override
    public Long getCurrentTenantId() {
        return 1L; // TODO: 从安全上下文获取当前租户ID
    }
    
    @Override
    public boolean exists(QueryWrapper<WorkTaskComment> queryWrapper) {
        return baseMapper.exists(queryWrapper);
    }
    
    @Override
    public WorkTaskComment getById(Long id) {
        return baseMapper.selectById(id);
    }
    
    @Override
    public boolean update(WorkTaskComment entity) {
        return updateById(entity);
    }
    
    @Override
    public boolean removeBatch(List<Long> ids) {
        return removeByIds(ids);
    }
    
    @Override
    public boolean remove(Long id) {
        return removeById(id);
    }
    
    @Override
    public long count(QueryWrapper<WorkTaskComment> queryWrapper) {
        return baseMapper.selectCount(queryWrapper);
    }
    
    @Override
    public List<WorkTaskComment> list(QueryWrapper<WorkTaskComment> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public Page<WorkTaskComment> page(Page<WorkTaskComment> page, QueryWrapper<WorkTaskComment> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public boolean save(WorkTaskComment entity) {
        return super.save(entity);
    }
    
    @Override
    public boolean saveBatch(List<WorkTaskComment> entityList) {
        return super.saveBatch(entityList);
    }
    
    @Override
    public boolean updateBatch(List<WorkTaskComment> entityList) {
        return super.updateBatchById(entityList);
    }
} 