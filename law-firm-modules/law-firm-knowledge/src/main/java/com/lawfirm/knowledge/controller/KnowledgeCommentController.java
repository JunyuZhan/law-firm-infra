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
@Tag(name = "知识评论管理")
@RestController
@RequestMapping("/knowledge/comment")
@RequiredArgsConstructor
@Validated
public class KnowledgeCommentController {

    private final KnowledgeCommentService commentService;

    @Operation(summary = "创建评论")
    @PostMapping
    public Long createComment(@RequestBody KnowledgeComment comment) {
        return commentService.createComment(comment);
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @Operation(summary = "获取评论树")
    @GetMapping("/tree/{knowledgeId}")
    public List<KnowledgeComment> getCommentTree(@PathVariable Long knowledgeId) {
        return commentService.getCommentTree(knowledgeId);
    }

    @Operation(summary = "获取子评论列表")
    @GetMapping("/children/{parentId}")
    public List<KnowledgeComment> listChildren(@PathVariable Long parentId) {
        return commentService.listChildren(parentId);
    }

    @Operation(summary = "点赞评论")
    @PostMapping("/{id}/like")
    public void likeComment(
            @PathVariable Long id,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        commentService.likeComment(id, userId);
    }

    @Operation(summary = "取消点赞")
    @DeleteMapping("/{id}/like")
    public void unlikeComment(
            @PathVariable Long id,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        commentService.unlikeComment(id, userId);
    }
} 