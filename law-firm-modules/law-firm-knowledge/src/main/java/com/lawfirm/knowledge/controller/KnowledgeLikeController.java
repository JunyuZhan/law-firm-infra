package com.lawfirm.knowledge.controller;

import com.lawfirm.knowledge.entity.KnowledgeLike;
import com.lawfirm.knowledge.service.KnowledgeLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识点赞管理
 */
@Tag(name = "知识点赞管理", description = "提供知识点赞相关的功能，包括点赞、取消点赞、查询用户点赞列表、查询知识点赞列表等操作")
@RestController
@RequestMapping("/knowledge/like")
@RequiredArgsConstructor
public class KnowledgeLikeController {

    private final KnowledgeLikeService likeService;

    @Operation(
        summary = "点赞知识",
        description = "用户对知识进行点赞，同一用户对同一知识只能点赞一次"
    )
    @PostMapping
    public Long createLike(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "点赞用户ID") @RequestParam Long userId) {
        return likeService.createLike(knowledgeId, userId);
    }

    @Operation(
        summary = "取消点赞",
        description = "用户取消对知识的点赞，如果未点赞则不进行操作"
    )
    @DeleteMapping
    public void deleteLike(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        likeService.deleteLike(knowledgeId, userId);
    }

    @Operation(
        summary = "获取用户点赞列表",
        description = "获取指定用户的所有点赞记录，包括点赞时间等信息"
    )
    @GetMapping("/user/{userId}")
    public List<KnowledgeLike> listByUser(
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        return likeService.listByUser(userId);
    }

    @Operation(
        summary = "获取知识点赞列表",
        description = "获取指定知识的所有点赞记录，包括点赞用户和时间等信息"
    )
    @GetMapping("/knowledge/{knowledgeId}")
    public List<KnowledgeLike> listByKnowledge(
            @Parameter(description = "知识ID") @PathVariable Long knowledgeId) {
        return likeService.listByKnowledge(knowledgeId);
    }

    @Operation(
        summary = "检查是否已点赞",
        description = "检查指定用户是否已对指定知识进行点赞"
    )
    @GetMapping("/check")
    public boolean checkLike(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        return likeService.checkLike(knowledgeId, userId);
    }
} 