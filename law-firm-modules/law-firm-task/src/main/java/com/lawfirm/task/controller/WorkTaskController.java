package com.lawfirm.task.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.task.dto.WorkTaskAttachmentDTO;
import com.lawfirm.model.task.dto.WorkTaskCommentDTO;
import com.lawfirm.model.task.dto.WorkTaskDTO;
import com.lawfirm.model.task.query.WorkTaskQuery;
import com.lawfirm.model.task.service.WorkTaskAttachmentService;
import com.lawfirm.model.task.service.WorkTaskCommentService;
import com.lawfirm.model.task.service.WorkTaskService;
import com.lawfirm.model.task.vo.WorkTaskVO;
import com.lawfirm.task.constant.TaskBusinessConstants;
import com.lawfirm.task.service.TaskAIManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lawfirm.model.auth.constant.PermissionConstants.*;

/**
 * 工作任务控制器
 */
@Slf4j
@Tag(name = "工作任务管理", description = "工作任务管理接口")
@RestController("workTaskController")
@RequestMapping(TaskBusinessConstants.Controller.API_PREFIX)
@RequiredArgsConstructor
public class WorkTaskController {

    private final WorkTaskService workTaskService;
    private final WorkTaskCommentService workTaskCommentService;
    private final WorkTaskAttachmentService workTaskAttachmentService;
    private final TaskAIManager taskAIManager;
    
    /**
     * 创建工作任务
     */
    @PreAuthorize("hasAuthority('" + TASK_CREATE + "')")
    @Operation(summary = "创建工作任务", description = "创建新的工作任务")
    @PostMapping
    public CommonResult<Long> createTask(
            @Parameter(description = "工作任务创建参数") 
            @RequestBody @Validated WorkTaskDTO taskDTO) {
        log.info("创建工作任务: {}", taskDTO);
        Long taskId = workTaskService.createTask(taskDTO);
        return CommonResult.success(taskId);
    }
    
    /**
     * 更新工作任务
     */
    @PreAuthorize("hasAuthority('" + TASK_EDIT + "')")
    @Operation(summary = "更新工作任务", description = "更新已有工作任务的基本信息")
    @PutMapping("/{taskId}")
    public CommonResult<Void> updateTask(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "工作任务更新参数") 
            @RequestBody @Validated WorkTaskDTO taskDTO) {
        log.info("更新工作任务: id={}, {}", taskId, taskDTO);
        taskDTO.setId(taskId);
        workTaskService.updateTask(taskDTO);
        return CommonResult.success();
    }
    
    /**
     * 删除工作任务
     */
    @PreAuthorize("hasAuthority('" + TASK_DELETE + "')")
    @Operation(summary = "删除工作任务", description = "删除指定的工作任务记录")
    @DeleteMapping("/{taskId}")
    public CommonResult<Void> deleteTask(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("删除工作任务: {}", taskId);
        workTaskService.deleteTask(taskId);
        return CommonResult.success();
    }
    
    /**
     * 获取工作任务详情
     */
    @PreAuthorize("hasAuthority('" + TASK_VIEW + "')")
    @Operation(summary = "获取工作任务详情", description = "获取工作任务的详细信息")
    @GetMapping("/{taskId}")
    public CommonResult<WorkTaskDTO> getTaskDetail(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("获取工作任务详情: {}", taskId);
        return CommonResult.success(workTaskService.getTaskDetail(taskId));
    }
    
    /**
     * 查询工作任务列表
     */
    @PreAuthorize("hasAuthority('" + TASK_VIEW + "')")
    @Operation(summary = "查询工作任务列表", description = "根据条件查询工作任务列表")
    @GetMapping
    public CommonResult<List<WorkTaskDTO>> queryTaskList(
            @Parameter(description = "查询参数") 
            @Validated WorkTaskQuery query) {
        log.info("查询工作任务列表: {}", query);
        return CommonResult.success(workTaskService.queryTaskList(query));
    }
    
    /**
     * 更新工作任务状态
     */
    @Operation(summary = "更新工作任务状态", description = "更新指定工作任务的状态")
    @PutMapping("/{taskId}/status/{status}")
    public CommonResult<Void> updateTaskStatus(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "目标状态") 
            @PathVariable Integer status) {
        log.info("更新工作任务状态: id={}, status={}", taskId, status);
        workTaskService.updateTaskStatus(taskId, status);
        return CommonResult.success();
    }
    
    /**
     * 更新工作任务优先级
     */
    @Operation(summary = "更新工作任务优先级", description = "更新指定工作任务的优先级")
    @PutMapping("/{taskId}/priority/{priority}")
    public CommonResult<Void> updateTaskPriority(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "优先级") 
            @PathVariable Integer priority) {
        log.info("更新工作任务优先级: id={}, priority={}", taskId, priority);
        workTaskService.updateTaskPriority(taskId, priority);
        return CommonResult.success();
    }
    
    /**
     * 分配工作任务
     */
    @Operation(summary = "分配工作任务", description = "将工作任务分配给指定负责人")
    @PutMapping("/{taskId}/assignee/{assigneeId}")
    public CommonResult<Void> assignTask(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "负责人ID") 
            @PathVariable Long assigneeId) {
        log.info("分配工作任务: id={}, assigneeId={}", taskId, assigneeId);
        workTaskService.assignTask(taskId, assigneeId);
        return CommonResult.success();
    }
    
    /**
     * 完成工作任务
     */
    @Operation(summary = "完成工作任务", description = "将工作任务标记为已完成")
    @PutMapping("/{taskId}/complete")
    public CommonResult<Void> completeTask(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("完成工作任务: {}", taskId);
        workTaskService.completeTask(taskId);
        return CommonResult.success();
    }
    
    /**
     * 取消工作任务
     */
    @Operation(summary = "取消工作任务", description = "取消指定的工作任务")
    @PutMapping("/{taskId}/cancel")
    public CommonResult<Void> cancelTask(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "取消原因") 
            @RequestParam(required = false) String reason) {
        log.info("取消工作任务: id={}, reason={}", taskId, reason);
        workTaskService.cancelTask(taskId, reason);
        return CommonResult.success();
    }
    
    /**
     * 获取任务评论列表
     */
    @Operation(summary = "获取任务评论列表", description = "获取指定任务的所有评论")
    @GetMapping("/{taskId}/comments")
    public CommonResult<List<WorkTaskCommentDTO>> getTaskComments(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("获取任务评论列表: taskId={}", taskId);
        return CommonResult.success(workTaskCommentService.getTaskComments(taskId));
    }
    
    /**
     * 获取任务附件列表
     */
    @Operation(summary = "获取任务附件列表", description = "获取指定任务的所有附件")
    @GetMapping("/{taskId}/attachments")
    public CommonResult<List<WorkTaskAttachmentDTO>> getTaskAttachments(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("获取任务附件列表: taskId={}", taskId);
        return CommonResult.success(workTaskAttachmentService.getAttachmentsByTaskId(taskId));
    }

    /**
     * AI任务智能摘要
     */
    @PostMapping("/ai/summary")
    public org.springframework.http.ResponseEntity<String> aiTaskSummary(@RequestBody java.util.Map<String, Object> body) {
        String content = (String) body.get("content");
        Integer maxLength = body.get("maxLength") != null ? (Integer) body.get("maxLength") : 200;
        return org.springframework.http.ResponseEntity.ok(taskAIManager.summarize(content, maxLength));
    }

    /**
     * AI任务智能分类
     */
    @PostMapping("/ai/classify")
    public org.springframework.http.ResponseEntity<java.util.Map<String, Double>> aiTaskClassify(@RequestBody java.util.Map<String, Object> body) {
        String content = (String) body.get("content");
        return org.springframework.http.ResponseEntity.ok(taskAIManager.classify(content));
    }

    /**
     * AI任务标签推荐
     */
    @PostMapping("/ai/tags")
    public org.springframework.http.ResponseEntity<java.util.List<String>> aiTaskTags(@RequestBody java.util.Map<String, Object> body) {
        String content = (String) body.get("content");
        Integer limit = body.get("limit") != null ? (Integer) body.get("limit") : 5;
        return org.springframework.http.ResponseEntity.ok(taskAIManager.recommendTags(content, limit));
    }

    /**
     * AI任务推荐
     */
    @PostMapping("/ai/recommend")
    public org.springframework.http.ResponseEntity<java.util.List<java.util.Map<String, Object>>> aiTaskRecommend(@RequestBody java.util.Map<String, Object> body) {
        String content = (String) body.get("content");
        Integer limit = body.get("limit") != null ? (Integer) body.get("limit") : 5;
        return org.springframework.http.ResponseEntity.ok(taskAIManager.recommendTasks(content, limit));
    }

    /**
     * AI任务智能问答
     */
    @PostMapping("/ai/qa")
    public org.springframework.http.ResponseEntity<String> aiTaskQA(@RequestBody java.util.Map<String, Object> body) {
        String question = (String) body.get("question");
        String context = (String) body.get("content");
        return org.springframework.http.ResponseEntity.ok(taskAIManager.qa(question, context));
    }

    /**
     * AI任务自动生成
     */
    @PostMapping("/ai/generate")
    public org.springframework.http.ResponseEntity<String> aiTaskGenerate(@RequestBody java.util.Map<String, Object> body) {
        return org.springframework.http.ResponseEntity.ok(taskAIManager.generate(body));
    }

    /**
     * AI任务多模型并发智能摘要
     */
    @PostMapping("/ai/summary/batch")
    public org.springframework.http.ResponseEntity<java.util.Map<String, Object>> aiTaskSummaryBatch(@RequestBody java.util.Map<String, Object> body) {
        String content = (String) body.get("content");
        java.util.List<String> modelNames = (java.util.List<String>) body.get("modelNames");
        java.util.Map<String, Object> results = new java.util.concurrent.ConcurrentHashMap<>();
        java.util.List<java.util.concurrent.CompletableFuture<Void>> futures = new java.util.ArrayList<>();
        for (String model : modelNames) {
            futures.add(java.util.concurrent.CompletableFuture.runAsync(() -> {
                String summary = taskAIManager.summarizeWithModel(content, model, 200);
                results.put(model, summary);
            }));
        }
        java.util.concurrent.CompletableFuture.allOf(futures.toArray(new java.util.concurrent.CompletableFuture[0])).join();
        return org.springframework.http.ResponseEntity.ok(results);
    }
} 