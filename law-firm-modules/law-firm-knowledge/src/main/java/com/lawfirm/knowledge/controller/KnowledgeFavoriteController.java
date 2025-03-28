package com.lawfirm.knowledge.controller;

import com.lawfirm.knowledge.entity.KnowledgeFavorite;
import com.lawfirm.knowledge.service.KnowledgeFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识收藏管理
 */
@Tag(name = "知识收藏管理", description = "提供知识收藏相关的功能，包括收藏、取消收藏、查询用户收藏列表、查询知识收藏列表等操作")
@RestController
@RequestMapping("/knowledge/favorite")
@RequiredArgsConstructor
public class KnowledgeFavoriteController {

    private final KnowledgeFavoriteService favoriteService;

    @Operation(
        summary = "收藏知识",
        description = "用户收藏指定的知识，同一用户对同一知识只能收藏一次"
    )
    @PostMapping
    public Long createFavorite(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "收藏用户ID") @RequestParam Long userId) {
        return favoriteService.createFavorite(knowledgeId, userId);
    }

    @Operation(
        summary = "取消收藏",
        description = "用户取消对知识的收藏，如果未收藏则不进行操作"
    )
    @DeleteMapping
    public void deleteFavorite(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        favoriteService.deleteFavorite(knowledgeId, userId);
    }

    @Operation(
        summary = "获取用户收藏列表",
        description = "获取指定用户的所有收藏记录，包括收藏时间等信息，按收藏时间倒序排列"
    )
    @GetMapping("/user/{userId}")
    public List<KnowledgeFavorite> listByUser(
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        return favoriteService.listByUser(userId);
    }

    @Operation(
        summary = "获取知识收藏列表",
        description = "获取指定知识的所有收藏记录，包括收藏用户和时间等信息"
    )
    @GetMapping("/knowledge/{knowledgeId}")
    public List<KnowledgeFavorite> listByKnowledge(
            @Parameter(description = "知识ID") @PathVariable Long knowledgeId) {
        return favoriteService.listByKnowledge(knowledgeId);
    }

    @Operation(
        summary = "检查是否已收藏",
        description = "检查指定用户是否已收藏指定知识"
    )
    @GetMapping("/check")
    public boolean checkFavorite(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        return favoriteService.checkFavorite(knowledgeId, userId);
    }
} 