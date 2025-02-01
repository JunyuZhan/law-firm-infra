package com.lawfirm.knowledge.controller;

import com.lawfirm.knowledge.entity.KnowledgeFavorite;
import com.lawfirm.knowledge.service.KnowledgeFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识收藏管理
 */
@Tag(name = "知识收藏")
@RestController
@RequestMapping("/knowledge/favorite")
@RequiredArgsConstructor
public class KnowledgeFavoriteController {

    private final KnowledgeFavoriteService favoriteService;

    @Operation(summary = "收藏")
    @PostMapping
    public Long createFavorite(@RequestParam Long knowledgeId, @RequestParam Long userId) {
        return favoriteService.createFavorite(knowledgeId, userId);
    }

    @Operation(summary = "取消收藏")
    @DeleteMapping
    public void deleteFavorite(@RequestParam Long knowledgeId, @RequestParam Long userId) {
        favoriteService.deleteFavorite(knowledgeId, userId);
    }

    @Operation(summary = "获取用户收藏列表")
    @GetMapping("/user/{userId}")
    public List<KnowledgeFavorite> listByUser(@PathVariable Long userId) {
        return favoriteService.listByUser(userId);
    }

    @Operation(summary = "获取知识收藏列表")
    @GetMapping("/knowledge/{knowledgeId}")
    public List<KnowledgeFavorite> listByKnowledge(@PathVariable Long knowledgeId) {
        return favoriteService.listByKnowledge(knowledgeId);
    }

    @Operation(summary = "检查是否已收藏")
    @GetMapping("/check")
    public boolean checkFavorite(@RequestParam Long knowledgeId, @RequestParam Long userId) {
        return favoriteService.checkFavorite(knowledgeId, userId);
    }
} 