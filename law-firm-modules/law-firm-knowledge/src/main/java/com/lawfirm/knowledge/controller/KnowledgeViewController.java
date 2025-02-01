package com.lawfirm.knowledge.controller;

import com.lawfirm.knowledge.entity.KnowledgeView;
import com.lawfirm.knowledge.service.KnowledgeViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
public class KnowledgeViewController {

    private final KnowledgeViewService viewService;

    @Operation(summary = "创建浏览记录")
    @PostMapping
    public Long createView(@RequestParam Long knowledgeId, @RequestParam Long userId, @RequestParam String ip) {
        return viewService.createView(knowledgeId, userId, ip);
    }

    @Operation(summary = "获取用户浏览记录")
    @GetMapping("/user/{userId}")
    public List<KnowledgeView> listByUser(@PathVariable Long userId) {
        return viewService.listByUser(userId);
    }

    @Operation(summary = "获取知识浏览记录")
    @GetMapping("/knowledge/{knowledgeId}")
    public List<KnowledgeView> listByKnowledge(@PathVariable Long knowledgeId) {
        return viewService.listByKnowledge(knowledgeId);
    }

    @Operation(summary = "统计时间段内的浏览量")
    @GetMapping("/count")
    public Integer countViews(
            @RequestParam Long knowledgeId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        return viewService.countViews(knowledgeId, startTime, endTime);
    }

    @Operation(summary = "统计用户浏览次数")
    @GetMapping("/count/user")
    public Integer countUserViews(@RequestParam Long knowledgeId, @RequestParam Long userId) {
        return viewService.countUserViews(knowledgeId, userId);
    }
} 