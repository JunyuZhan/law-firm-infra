package com.lawfirm.knowledge.controller;

import com.lawfirm.knowledge.entity.KnowledgeLike;
import com.lawfirm.knowledge.service.KnowledgeLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识点赞管理
 */
@Tag(name = "知识点赞管理")
@RestController
@RequestMapping("/knowledge/like")
@RequiredArgsConstructor
@Validated
public class KnowledgeLikeController {

    private final KnowledgeLikeService likeService;

    @Operation(summary = "创建点赞")
    @PostMapping
    public Long createLike(@RequestBody KnowledgeLike like) {
        return likeService.createLike(like);
    }

    @Operation(summary = "删除点赞")
    @DeleteMapping("/{id}")
    public void deleteLike(@PathVariable Long id) {
        likeService.deleteLike(id);
    }

    @Operation(summary = "获取用户点赞列表")
    @GetMapping("/user/{userId}")
    public List<KnowledgeLike> listByUser(@PathVariable Long userId) {
        return likeService.listByUser(userId);
    }

    @Operation(summary = "获取知识点赞用户列表")
    @GetMapping("/knowledge/{knowledgeId}")
    public List<KnowledgeLike> listByKnowledge(@PathVariable Long knowledgeId) {
        return likeService.listByKnowledge(knowledgeId);
    }

    @Operation(summary = "检查是否已点赞")
    @GetMapping("/check")
    public boolean checkLike(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        return likeService.checkLike(knowledgeId, userId);
    }
} 