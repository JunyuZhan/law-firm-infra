package com.lawfirm.knowledge.controller;

import com.lawfirm.knowledge.entity.KnowledgeComment;
import com.lawfirm.knowledge.service.KnowledgeCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识评论管理
 */
@Tag(name = "知识评论管理", description = "提供知识评论的创建、删除、查询等功能，支持评论的树形结构和点赞操作")
@RestController
@RequestMapping("/knowledge/comment")
@RequiredArgsConstructor
@Validated
public class KnowledgeCommentController {

    private final KnowledgeCommentService commentService;

    @Operation(
        summary = "创建评论",
        description = "创建新的知识评论，支持回复其他评论，形成评论树结构"
    )
    @PostMapping
    public Long createComment(
            @Parameter(description = "评论创建参数，包括知识ID、评论内容、父评论ID等") @RequestBody KnowledgeComment comment) {
        return commentService.createComment(comment);
    }

    @Operation(
        summary = "删除评论",
        description = "删除指定的评论，如果有子评论则同时删除所有子评论"
    )
    @DeleteMapping("/{id}")
    public void deleteComment(
            @Parameter(description = "评论ID") @PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @Operation(
        summary = "获取评论树",
        description = "获取指定知识的所有评论，以树形结构返回，包含评论的层级关系"
    )
    @GetMapping("/tree/{knowledgeId}")
    public List<KnowledgeComment> getCommentTree(
            @Parameter(description = "知识ID") @PathVariable Long knowledgeId) {
        return commentService.getCommentTree(knowledgeId);
    }

    @Operation(
        summary = "获取子评论列表",
        description = "获取指定评论的直接回复列表，按时间倒序排列"
    )
    @GetMapping("/children/{parentId}")
    public List<KnowledgeComment> listChildren(
            @Parameter(description = "父评论ID") @PathVariable Long parentId) {
        return commentService.listChildren(parentId);
    }

    @Operation(
        summary = "点赞评论",
        description = "用户对评论进行点赞，同一用户对同一评论只能点赞一次"
    )
    @PostMapping("/{id}/like")
    public void likeComment(
            @Parameter(description = "评论ID") @PathVariable Long id,
            @Parameter(description = "点赞用户ID") @RequestParam Long userId) {
        commentService.likeComment(id, userId);
    }

    @Operation(
        summary = "取消点赞",
        description = "用户取消对评论的点赞"
    )
    @DeleteMapping("/{id}/like")
    public void unlikeComment(
            @Parameter(description = "评论ID") @PathVariable Long id,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        commentService.unlikeComment(id, userId);
    }
} 