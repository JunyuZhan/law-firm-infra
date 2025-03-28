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
@Tag(name = "知识库管理", description = "提供知识的创建、查询、修改、删除等功能，支持知识的发布、浏览、点赞、收藏等操作")
@RestController
@RequestMapping("/knowledge")
@RequiredArgsConstructor
@Validated
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    @Operation(
        summary = "创建知识",
        description = "创建新的知识记录，包括知识标题、内容、分类、标签等信息"
    )
    @PostMapping
    public Long createKnowledge(
            @Parameter(description = "知识创建参数，包括标题、内容、分类、标签等") @RequestBody Knowledge knowledge) {
        return knowledgeService.createKnowledge(knowledge);
    }

    @Operation(
        summary = "更新知识",
        description = "更新已存在的知识信息，支持更新标题、内容、分类、标签等"
    )
    @PutMapping("/{id}")
    public void updateKnowledge(
            @Parameter(description = "知识ID") @PathVariable Long id,
            @Parameter(description = "知识更新参数，包括需要更新的字段") @RequestBody Knowledge knowledge) {
        knowledge.setId(id);
        knowledgeService.updateKnowledge(knowledge);
    }

    @Operation(
        summary = "删除知识",
        description = "根据ID删除知识，同时删除相关的浏览、点赞、收藏记录"
    )
    @DeleteMapping("/{id}")
    public void deleteKnowledge(
            @Parameter(description = "知识ID") @PathVariable Long id) {
        knowledgeService.deleteKnowledge(id);
    }

    @Operation(
        summary = "发布知识",
        description = "发布指定的知识，发布后其他用户可以查看和操作"
    )
    @PostMapping("/{id}/publish")
    public void publishKnowledge(
            @Parameter(description = "知识ID") @PathVariable Long id) {
        knowledgeService.publishKnowledge(id);
    }

    @Operation(
        summary = "分页查询知识列表",
        description = "根据条件分页查询知识列表，支持按类型、状态、关键字等条件筛选"
    )
    @GetMapping("/page")
    public IPage<Knowledge> pageKnowledge(
            @Parameter(description = "页码，从1开始") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页记录数") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "知识类型：1-经验分享，2-案例分析，3-法律研究") @RequestParam(required = false) Integer type,
            @Parameter(description = "知识状态：0-草稿，1-已发布，2-已下架") @RequestParam(required = false) Integer status,
            @Parameter(description = "搜索关键字，匹配标题和内容") @RequestParam(required = false) String keyword) {
        return knowledgeService.pageKnowledge(page, size, type, status, keyword);
    }

    @Operation(
        summary = "根据分类查询知识列表",
        description = "获取指定分类下的所有知识列表"
    )
    @GetMapping("/category/{categoryId}")
    public List<Knowledge> listByCategory(
            @Parameter(description = "分类ID") @PathVariable Long categoryId) {
        return knowledgeService.listByCategory(categoryId);
    }

    @Operation(
        summary = "根据标签查询知识列表",
        description = "获取包含指定标签的所有知识列表"
    )
    @GetMapping("/tag/{tag}")
    public List<Knowledge> listByTag(
            @Parameter(description = "标签名称") @PathVariable String tag) {
        return knowledgeService.listByTag(tag);
    }

    @Operation(
        summary = "根据作者查询知识列表",
        description = "获取指定作者创建的所有知识列表"
    )
    @GetMapping("/author/{authorId}")
    public List<Knowledge> listByAuthor(
            @Parameter(description = "作者ID") @PathVariable Long authorId) {
        return knowledgeService.listByAuthor(authorId);
    }

    @Operation(
        summary = "浏览知识",
        description = "记录用户浏览知识的行为，用于统计浏览量和推荐"
    )
    @PostMapping("/{id}/view")
    public void viewKnowledge(
            @Parameter(description = "知识ID") @PathVariable Long id,
            @Parameter(description = "浏览用户ID") @RequestParam Long userId,
            @Parameter(description = "浏览者IP地址") @RequestParam String ip) {
        knowledgeService.viewKnowledge(id, userId, ip);
    }

    @Operation(
        summary = "点赞知识",
        description = "用户对知识进行点赞，同一用户对同一知识只能点赞一次"
    )
    @PostMapping("/{id}/like")
    public void likeKnowledge(
            @Parameter(description = "知识ID") @PathVariable Long id,
            @Parameter(description = "点赞用户ID") @RequestParam Long userId) {
        knowledgeService.likeKnowledge(id, userId);
    }

    @Operation(
        summary = "取消点赞",
        description = "用户取消对知识的点赞"
    )
    @DeleteMapping("/{id}/like")
    public void unlikeKnowledge(
            @Parameter(description = "知识ID") @PathVariable Long id,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        knowledgeService.unlikeKnowledge(id, userId);
    }

    @Operation(
        summary = "收藏知识",
        description = "用户收藏知识，同一用户对同一知识只能收藏一次"
    )
    @PostMapping("/{id}/favorite")
    public void favoriteKnowledge(
            @Parameter(description = "知识ID") @PathVariable Long id,
            @Parameter(description = "收藏用户ID") @RequestParam Long userId) {
        knowledgeService.favoriteKnowledge(id, userId);
    }

    @Operation(
        summary = "取消收藏",
        description = "用户取消对知识的收藏"
    )
    @DeleteMapping("/{id}/favorite")
    public void unfavoriteKnowledge(
            @Parameter(description = "知识ID") @PathVariable Long id,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        knowledgeService.unfavoriteKnowledge(id, userId);
    }
} 