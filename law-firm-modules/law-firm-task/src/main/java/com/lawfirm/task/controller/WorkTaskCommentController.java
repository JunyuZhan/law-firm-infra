package com.lawfirm.task.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.task.dto.WorkTaskCommentDTO;
import com.lawfirm.model.task.service.WorkTaskCommentService;
import com.lawfirm.task.constant.TaskBusinessConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作任务评论控制器
 */
@Slf4j
@Tag(name = "工作任务评论管理", description = "工作任务评论管理接口")
@RestController("workTaskCommentController")
@RequestMapping(TaskBusinessConstants.Controller.API_COMMENT_PREFIX)
@RequiredArgsConstructor
public class WorkTaskCommentController {

    private final WorkTaskCommentService workTaskCommentService;
    
    /**
     * 添加任务评论
     */
    @Operation(summary = "添加任务评论", description = "为工作任务添加评论")
    @PostMapping
    public CommonResult<Long> addComment(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "任务评论创建参数") 
            @RequestBody @Validated WorkTaskCommentDTO commentDTO) {
        log.info("添加任务评论: taskId={}, {}", taskId, commentDTO);
        commentDTO.setTaskId(taskId);
        Long commentId = workTaskCommentService.addComment(commentDTO);
        return CommonResult.success(commentId);
    }
    
    /**
     * 删除任务评论
     */
    @Operation(summary = "删除任务评论", description = "删除指定的任务评论")
    @DeleteMapping("/{commentId}")
    public CommonResult<Void> deleteComment(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "评论ID") 
            @PathVariable Long commentId) {
        log.info("删除任务评论: taskId={}, commentId={}", taskId, commentId);
        workTaskCommentService.deleteComment(commentId);
        return CommonResult.success();
    }
    
    /**
     * 获取任务评论详情
     */
    @Operation(summary = "获取任务评论详情", description = "获取任务评论的详细信息")
    @GetMapping("/{commentId}")
    public CommonResult<WorkTaskCommentDTO> getCommentDetail(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "评论ID") 
            @PathVariable Long commentId) {
        log.info("获取任务评论详情: taskId={}, commentId={}", taskId, commentId);
        return CommonResult.success(workTaskCommentService.getCommentDetail(commentId));
    }
    
    /**
     * 获取任务评论列表
     */
    @Operation(summary = "获取任务评论列表", description = "获取指定任务的所有评论")
    @GetMapping
    public CommonResult<List<WorkTaskCommentDTO>> getTaskComments(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("获取任务评论列表: taskId={}", taskId);
        return CommonResult.success(workTaskCommentService.getTaskComments(taskId));
    }
    
    /**
     * 回复任务评论
     */
    @Operation(summary = "回复任务评论", description = "回复指定的任务评论")
    @PostMapping("/{parentId}/reply")
    public CommonResult<Long> replyComment(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "父评论ID") 
            @PathVariable Long parentId,
            @Parameter(description = "评论内容") 
            @RequestBody @Validated WorkTaskCommentDTO commentDTO) {
        log.info("回复任务评论: taskId={}, parentId={}, {}", taskId, parentId, commentDTO);
        commentDTO.setTaskId(taskId);
        commentDTO.setParentId(parentId);
        Long commentId = workTaskCommentService.addComment(commentDTO);
        return CommonResult.success(commentId);
    }
} 