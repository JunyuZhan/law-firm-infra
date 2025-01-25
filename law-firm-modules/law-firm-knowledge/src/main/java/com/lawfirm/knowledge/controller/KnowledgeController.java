package com.lawfirm.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.knowledge.entity.Knowledge;
import com.lawfirm.knowledge.service.KnowledgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识库管理
 */
@Tag(name = "知识库管理")
@RestController
@RequestMapping("/knowledge")
@RequiredArgsConstructor
@Validated
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    @Operation(summary = "创建知识")
    @PostMapping
    public Long createKnowledge(@RequestBody Knowledge knowledge) {
        return knowledgeService.createKnowledge(knowledge);
    }

    @Operation(summary = "更新知识")
    @PutMapping("/{id}")
    public void updateKnowledge(@PathVariable Long id, @RequestBody Knowledge knowledge) {
        knowledge.setId(id);
        knowledgeService.updateKnowledge(knowledge);
    }

    @Operation(summary = "删除知识")
    @DeleteMapping("/{id}")
    public void deleteKnowledge(@PathVariable Long id) {
        knowledgeService.deleteKnowledge(id);
    }

    @Operation(summary = "发布知识")
    @PostMapping("/{id}/publish")
    public void publishKnowledge(@PathVariable Long id) {
        knowledgeService.publishKnowledge(id);
    }

    @Operation(summary = "分页查询知识列表")
    @GetMapping("/page")
    public IPage<Knowledge> pageKnowledge(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "类型") @RequestParam(required = false) Integer type,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status,
            @Parameter(description = "关键字") @RequestParam(required = false) String keyword) {
        return knowledgeService.pageKnowledge(page, size, type, status, keyword);
    }

    @Operation(summary = "根据分类查询知识列表")
    @GetMapping("/category/{categoryId}")
    public List<Knowledge> listByCategory(@PathVariable Long categoryId) {
        return knowledgeService.listByCategory(categoryId);
    }

    @Operation(summary = "根据标签查询知识列表")
    @GetMapping("/tag/{tag}")
    public List<Knowledge> listByTag(@PathVariable String tag) {
        return knowledgeService.listByTag(tag);
    }

    @Operation(summary = "根据作者查询知识列表")
    @GetMapping("/author/{authorId}")
    public List<Knowledge> listByAuthor(@PathVariable Long authorId) {
        return knowledgeService.listByAuthor(authorId);
    }

    @Operation(summary = "浏览知识")
    @PostMapping("/{id}/view")
    public void viewKnowledge(
            @PathVariable Long id,
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "IP地址") @RequestParam String ip) {
        knowledgeService.viewKnowledge(id, userId, ip);
    }

    @Operation(summary = "点赞知识")
    @PostMapping("/{id}/like")
    public void likeKnowledge(
            @PathVariable Long id,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        knowledgeService.likeKnowledge(id, userId);
    }

    @Operation(summary = "取消点赞")
    @DeleteMapping("/{id}/like")
    public void unlikeKnowledge(
            @PathVariable Long id,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        knowledgeService.unlikeKnowledge(id, userId);
    }

    @Operation(summary = "收藏知识")
    @PostMapping("/{id}/favorite")
    public void favoriteKnowledge(
            @PathVariable Long id,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        knowledgeService.favoriteKnowledge(id, userId);
    }

    @Operation(summary = "取消收藏")
    @DeleteMapping("/{id}/favorite")
    public void unfavoriteKnowledge(
            @PathVariable Long id,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        knowledgeService.unfavoriteKnowledge(id, userId);
    }
} 