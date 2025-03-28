package com.lawfirm.knowledge.controller;

import com.lawfirm.knowledge.entity.KnowledgeView;
import com.lawfirm.knowledge.service.KnowledgeViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识浏览记录管理
 */
@Tag(name = "知识浏览记录管理", description = "提供知识浏览记录的创建、查询和统计功能，包括用户浏览历史、知识浏览量统计等")
@RestController
@RequestMapping("/knowledge/view")
@RequiredArgsConstructor
public class KnowledgeViewController {

    private final KnowledgeViewService viewService;

    @Operation(
        summary = "创建浏览记录",
        description = "记录用户浏览知识的行为，包括浏览时间、IP地址等信息，用于统计分析"
    )
    @PostMapping
    public Long createView(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "浏览用户ID") @RequestParam Long userId,
            @Parameter(description = "用户IP地址") @RequestParam String ip) {
        return viewService.createView(knowledgeId, userId, ip);
    }

    @Operation(
        summary = "获取用户浏览记录",
        description = "获取指定用户的所有浏览记录，包括浏览时间、浏览知识等信息"
    )
    @GetMapping("/user/{userId}")
    public List<KnowledgeView> listByUser(
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        return viewService.listByUser(userId);
    }

    @Operation(
        summary = "获取知识浏览记录",
        description = "获取指定知识的所有浏览记录，包括浏览用户、浏览时间等信息"
    )
    @GetMapping("/knowledge/{knowledgeId}")
    public List<KnowledgeView> listByKnowledge(
            @Parameter(description = "知识ID") @PathVariable Long knowledgeId) {
        return viewService.listByKnowledge(knowledgeId);
    }

    @Operation(
        summary = "统计时间段内的浏览量",
        description = "统计指定知识在给定时间段内的总浏览次数，用于分析知识热度"
    )
    @GetMapping("/count")
    public Integer countViews(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "统计开始时间") @RequestParam LocalDateTime startTime,
            @Parameter(description = "统计结束时间") @RequestParam LocalDateTime endTime) {
        return viewService.countViews(knowledgeId, startTime, endTime);
    }

    @Operation(
        summary = "统计用户浏览次数",
        description = "统计指定用户对指定知识的浏览次数，用于分析用户兴趣"
    )
    @GetMapping("/count/user")
    public Integer countUserViews(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        return viewService.countUserViews(knowledgeId, userId);
    }
} 