package com.lawfirm.knowledge.controller;

import com.lawfirm.knowledge.entity.KnowledgeFavorite;
import com.lawfirm.knowledge.service.KnowledgeFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识收藏管理
 */
@Tag(name = "知识收藏管理")
@RestController
@RequestMapping("/knowledge/favorite")
@RequiredArgsConstructor
@Validated
public class KnowledgeFavoriteController {

    private final KnowledgeFavoriteService favoriteService;

    @Operation(summary = "创建收藏")
    @PostMapping
    public Long createFavorite(@RequestBody KnowledgeFavorite favorite) {
        return favoriteService.createFavorite(favorite);
    }

    @Operation(summary = "删除收藏")
    @DeleteMapping("/{id}")
    public void deleteFavorite(@PathVariable Long id) {
        favoriteService.deleteFavorite(id);
    }

    @Operation(summary = "获取用户收藏列表")
    @GetMapping("/user/{userId}")
    public List<KnowledgeFavorite> listByUser(@PathVariable Long userId) {
        return favoriteService.listByUser(userId);
    }

    @Operation(summary = "获取知识收藏用户列表")
    @GetMapping("/knowledge/{knowledgeId}")
    public List<KnowledgeFavorite> listByKnowledge(@PathVariable Long knowledgeId) {
        return favoriteService.listByKnowledge(knowledgeId);
    }

    @Operation(summary = "检查是否已收藏")
    @GetMapping("/check")
    public boolean checkFavorite(
            @Parameter(description = "知识ID") @RequestParam Long knowledgeId,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        return favoriteService.checkFavorite(knowledgeId, userId);
    }
} 