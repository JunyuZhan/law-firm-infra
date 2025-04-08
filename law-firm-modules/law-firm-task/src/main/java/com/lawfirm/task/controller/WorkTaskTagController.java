package com.lawfirm.task.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.task.dto.WorkTaskTagDTO;
import com.lawfirm.model.task.query.WorkTaskTagQuery;
import com.lawfirm.model.task.service.WorkTaskTagService;
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
 * 工作任务标签控制器
 */
@Slf4j
@Tag(name = "工作任务标签管理", description = "工作任务标签管理接口")
@RestController("workTaskTagController")
@RequestMapping(TaskBusinessConstants.Controller.API_TAG_PREFIX)
@RequiredArgsConstructor
public class WorkTaskTagController {

    private final WorkTaskTagService workTaskTagService;
    
    /**
     * 创建任务标签
     */
    @Operation(summary = "创建任务标签", description = "创建新的任务标签")
    @PostMapping
    public CommonResult<Long> createTag(
            @Parameter(description = "任务标签创建参数") 
            @RequestBody @Validated WorkTaskTagDTO tagDTO) {
        log.info("创建任务标签: {}", tagDTO);
        Long tagId = workTaskTagService.createTag(tagDTO);
        return CommonResult.success(tagId);
    }
    
    /**
     * 更新任务标签
     */
    @Operation(summary = "更新任务标签", description = "更新已有任务标签的信息")
    @PutMapping("/{tagId}")
    public CommonResult<Void> updateTag(
            @Parameter(description = "标签ID") 
            @PathVariable Long tagId,
            @Parameter(description = "任务标签更新参数") 
            @RequestBody @Validated WorkTaskTagDTO tagDTO) {
        log.info("更新任务标签: id={}, {}", tagId, tagDTO);
        tagDTO.setId(tagId);
        workTaskTagService.updateTag(tagDTO);
        return CommonResult.success();
    }
    
    /**
     * 删除任务标签
     */
    @Operation(summary = "删除任务标签", description = "删除指定的任务标签")
    @DeleteMapping("/{tagId}")
    public CommonResult<Void> deleteTag(
            @Parameter(description = "标签ID") 
            @PathVariable Long tagId) {
        log.info("删除任务标签: {}", tagId);
        workTaskTagService.deleteTag(tagId);
        return CommonResult.success();
    }
    
    /**
     * 获取任务标签详情
     */
    @Operation(summary = "获取任务标签详情", description = "获取任务标签的详细信息")
    @GetMapping("/{tagId}")
    public CommonResult<WorkTaskTagDTO> getTagDetail(
            @Parameter(description = "标签ID") 
            @PathVariable Long tagId) {
        log.info("获取任务标签详情: {}", tagId);
        return CommonResult.success(workTaskTagService.getTagDetail(tagId));
    }
    
    /**
     * 查询所有任务标签
     */
    @Operation(summary = "查询所有任务标签", description = "获取所有可用的任务标签")
    @GetMapping
    public CommonResult<List<WorkTaskTagDTO>> listAllTags() {
        log.info("查询所有任务标签");
        return CommonResult.success(workTaskTagService.listAllTags());
    }
    
    /**
     * 查询工作任务标签列表
     */
    @Operation(summary = "查询工作任务标签列表", description = "根据条件查询工作任务标签列表")
    @GetMapping("/query")
    public CommonResult<List<WorkTaskTagDTO>> queryTagList(
            @Parameter(description = "查询参数") 
            @Validated WorkTaskTagQuery query) {
        log.info("查询工作任务标签列表: {}", query);
        return CommonResult.success(workTaskTagService.queryTagList(query));
    }
    
    /**
     * 获取任务关联的标签列表
     */
    @Operation(summary = "获取任务关联的标签列表", description = "获取指定任务关联的所有标签")
    @GetMapping("/by-task/{taskId}")
    public CommonResult<List<WorkTaskTagDTO>> getTagsByTaskId(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("获取任务关联的标签列表: {}", taskId);
        return CommonResult.success(workTaskTagService.getTagsByTaskId(taskId));
    }
    
    /**
     * 获取热门标签列表
     */
    @Operation(summary = "获取热门标签列表", description = "获取使用频率最高的标签列表")
    @GetMapping("/popular")
    public CommonResult<List<WorkTaskTagDTO>> getPopularTags(
            @Parameter(description = "限制数量") 
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        log.info("获取热门标签列表: limit={}", limit);
        return CommonResult.success(workTaskTagService.getPopularTags(limit));
    }
} 