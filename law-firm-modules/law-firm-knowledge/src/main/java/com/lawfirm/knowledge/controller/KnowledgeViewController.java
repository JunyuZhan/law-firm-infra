package com.lawfirm.knowledge.controller;

import com.lawfirm.knowledge.entity.KnowledgeView;
import com.lawfirm.knowledge.service.KnowledgeViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识浏览记录管理
 */
@Tag(name = "知识浏览记录管理")
@RestController
@RequestMapping("/knowledge/view")
@RequiredArgsConstructor
@Validated
public class KnowledgeViewController {

    private final KnowledgeViewService viewService;

    @Operation(summary = "创建浏览记录")
    @PostMapping
    public Long createView(@RequestBody KnowledgeView view) {
        return viewService.createView(view);
    }

    @Operation(summary = "获取用户浏览记录")
    @GetMapping("/user/{userId}")
    public List<KnowledgeView> listByUser(
            @PathVariable Long userId,
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime) {
        return viewService.listByUser(userId, startTime, endTime);
    }

    @Operation(summary = "获取知识浏览记录")
    @GetMapping("/knowledge/{knowledgeId}")
    public List<KnowledgeView> listByKnowledge(
            @PathVariable Long knowledgeId,
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime) {
        return viewService.listByKnowledge(knowledgeId, startTime, endTime);
    }

    @Operation(summary = "统计浏览量")
    @GetMapping("/count")
    public Long countViews(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime) {
        return viewService.countViews(knowledgeId, startTime, endTime);
    }

    @Operation(summary = "统计用户浏览量")
    @GetMapping("/count/user")
    public Long countUserViews(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime) {
        return viewService.countUserViews(knowledgeId, startTime, endTime);
    }
} 