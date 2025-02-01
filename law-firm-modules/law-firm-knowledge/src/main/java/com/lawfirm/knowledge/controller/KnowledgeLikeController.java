package com.lawfirm.knowledge.controller;

import com.lawfirm.knowledge.entity.KnowledgeLike;
import com.lawfirm.knowledge.service.KnowledgeLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识点赞管理
 */
@Tag(name = "知识点赞")
@RestController
@RequestMapping("/knowledge/like")
@RequiredArgsConstructor
public class KnowledgeLikeController {

    private final KnowledgeLikeService likeService;

    @Operation(summary = "点赞")
    @PostMapping
    public Long createLike(@RequestParam Long knowledgeId, @RequestParam Long userId) {
        return likeService.createLike(knowledgeId, userId);
    }

    @Operation(summary = "取消点赞")
    @DeleteMapping
    public void deleteLike(@RequestParam Long knowledgeId, @RequestParam Long userId) {
        likeService.deleteLike(knowledgeId, userId);
    }

    @Operation(summary = "获取用户点赞列表")
    @GetMapping("/user/{userId}")
    public List<KnowledgeLike> listByUser(@PathVariable Long userId) {
        return likeService.listByUser(userId);
    }

    @Operation(summary = "获取知识点赞列表")
    @GetMapping("/knowledge/{knowledgeId}")
    public List<KnowledgeLike> listByKnowledge(@PathVariable Long knowledgeId) {
        return likeService.listByKnowledge(knowledgeId);
    }

    @Operation(summary = "检查是否已点赞")
    @GetMapping("/check")
    public boolean checkLike(@RequestParam Long knowledgeId, @RequestParam Long userId) {
        return likeService.checkLike(knowledgeId, userId);
    }
} 