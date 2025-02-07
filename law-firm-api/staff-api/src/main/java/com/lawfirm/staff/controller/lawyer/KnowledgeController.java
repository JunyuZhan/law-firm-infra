package com.lawfirm.staff.controller.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.staff.client.lawyer.KnowledgeClient;
import com.lawfirm.staff.model.request.lawyer.knowledge.*;
import com.lawfirm.staff.model.response.lawyer.knowledge.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 知识库管理
 */
@Tag(name = "知识库管理")
@RestController
@RequestMapping("/lawyer/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {

    private final KnowledgeClient knowledgeClient;

    @Operation(summary = "分页查询知识库")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('knowledge:query')")
    public Result<PageResult<KnowledgeResponse>> page(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "关键词") @RequestParam(required = false) String keyword) {
        return knowledgeClient.page(pageNum, pageSize, keyword);
    }

    @Operation(summary = "创建知识")
    @PostMapping
    @PreAuthorize("hasAuthority('knowledge:create')")
    @OperationLog(description = "创建知识", operationType = "CREATE")
    public Result<KnowledgeResponse> create(@RequestBody @Validated KnowledgeCreateRequest request) {
        return knowledgeClient.create(request);
    }

    @Operation(summary = "上传附件")
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('knowledge:upload')")
    @OperationLog(description = "上传知识附件", operationType = "UPLOAD")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        return knowledgeClient.upload(file);
    }

    @Operation(summary = "修改知识")
    @PutMapping
    @PreAuthorize("hasAuthority('knowledge:update')")
    @OperationLog(description = "修改知识", operationType = "UPDATE")
    public Result<Void> update(@RequestBody @Validated KnowledgeUpdateRequest request) {
        return knowledgeClient.update(request);
    }

    @Operation(summary = "删除知识")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('knowledge:delete')")
    @OperationLog(description = "删除知识", operationType = "DELETE")
    public Result<Void> delete(@Parameter(description = "知识ID") @PathVariable Long id) {
        return knowledgeClient.delete(id);
    }

    @Operation(summary = "获取知识详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('knowledge:query')")
    public Result<KnowledgeResponse> getById(@Parameter(description = "知识ID") @PathVariable Long id) {
        return knowledgeClient.getById(id);
    }

    @Operation(summary = "查询我的知识")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('knowledge:query')")
    public Result<PageResult<KnowledgeResponse>> getMyKnowledge(@Validated KnowledgePageRequest request) {
        return knowledgeClient.getMyKnowledge(request);
    }

    @Operation(summary = "导出知识")
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('knowledge:export')")
    @OperationLog(description = "导出知识", operationType = "EXPORT")
    public void export(@Validated KnowledgePageRequest request) {
        knowledgeClient.export(request);
    }

    @Operation(summary = "分享知识")
    @PostMapping("/share")
    @PreAuthorize("hasAuthority('knowledge:share')")
    @OperationLog(description = "分享知识", operationType = "SHARE")
    public Result<Void> share(@RequestBody @Validated KnowledgeShareRequest request) {
        return knowledgeClient.share(request);
    }

    @Operation(summary = "收藏知识")
    @PostMapping("/{id}/favorite")
    @PreAuthorize("hasAuthority('knowledge:favorite')")
    @OperationLog(description = "收藏知识", operationType = "FAVORITE")
    public Result<Void> favorite(@Parameter(description = "知识ID") @PathVariable Long id) {
        return knowledgeClient.favorite(id);
    }

    @Operation(summary = "取消收藏")
    @DeleteMapping("/{id}/favorite")
    @PreAuthorize("hasAuthority('knowledge:favorite')")
    @OperationLog(description = "取消收藏", operationType = "UNFAVORITE")
    public Result<Void> unfavorite(@Parameter(description = "知识ID") @PathVariable Long id) {
        return knowledgeClient.unfavorite(id);
    }

    @Operation(summary = "查询收藏列表")
    @GetMapping("/favorites")
    @PreAuthorize("hasAuthority('knowledge:query')")
    public Result<PageResult<KnowledgeResponse>> getFavorites(@Validated KnowledgePageRequest request) {
        return knowledgeClient.getFavorites(request);
    }

    @Operation(summary = "点赞知识")
    @PostMapping("/{id}/like")
    @PreAuthorize("hasAuthority('knowledge:like')")
    @OperationLog(description = "点赞知识", operationType = "LIKE")
    public Result<Void> like(@Parameter(description = "知识ID") @PathVariable Long id) {
        return knowledgeClient.like(id);
    }

    @Operation(summary = "取消点赞")
    @DeleteMapping("/{id}/like")
    @PreAuthorize("hasAuthority('knowledge:like')")
    @OperationLog(description = "取消点赞", operationType = "UNLIKE")
    public Result<Void> unlike(@Parameter(description = "知识ID") @PathVariable Long id) {
        return knowledgeClient.unlike(id);
    }

    @Operation(summary = "获取知识分类")
    @GetMapping("/categories")
    @PreAuthorize("hasAuthority('knowledge:query')")
    public Result<List<KnowledgeCategoryResponse>> getCategories() {
        return knowledgeClient.getCategories();
    }

    @Operation(summary = "获取知识标签")
    @GetMapping("/tags")
    @PreAuthorize("hasAuthority('knowledge:query')")
    public Result<List<String>> getTags() {
        return knowledgeClient.getTags();
    }

    @Operation(summary = "获取热门知识")
    @GetMapping("/hot")
    @PreAuthorize("hasAuthority('knowledge:query')")
    public Result<List<KnowledgeResponse>> getHotKnowledge() {
        return knowledgeClient.getHotKnowledge();
    }

    @Operation(summary = "获取推荐知识")
    @GetMapping("/recommend")
    @PreAuthorize("hasAuthority('knowledge:query')")
    public Result<List<KnowledgeResponse>> getRecommendKnowledge() {
        return knowledgeClient.getRecommendKnowledge();
    }

    @Operation(summary = "获取知识统计")
    @GetMapping("/statistics")
    @PreAuthorize("hasAuthority('knowledge:query')")
    public Result<KnowledgeStatisticsResponse> getStatistics() {
        return knowledgeClient.getStatistics();
    }

    @Operation(summary = "导入知识")
    @PostMapping("/import")
    @PreAuthorize("hasAuthority('knowledge:import')")
    @OperationLog(description = "导入知识", operationType = "IMPORT")
    public Result<Void> importKnowledge(@RequestParam("file") MultipartFile file) {
        return knowledgeClient.importKnowledge(file);
    }

    @Operation(summary = "搜索知识")
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('knowledge:query')")
    public Result<PageResult<KnowledgeResponse>> search(
            @Parameter(description = "关键词") @RequestParam String keyword) {
        return knowledgeClient.search(keyword);
    }

    @Operation(summary = "评论知识")
    @PostMapping("/{id}/comment")
    @PreAuthorize("hasAuthority('knowledge:comment')")
    @OperationLog(description = "评论知识", operationType = "COMMENT")
    public Result<Void> comment(
            @Parameter(description = "知识ID") @PathVariable Long id,
            @RequestBody @Validated KnowledgeCommentRequest request) {
        return knowledgeClient.comment(id, request);
    }

    @Operation(summary = "获取评论列表")
    @GetMapping("/{id}/comments")
    @PreAuthorize("hasAuthority('knowledge:query')")
    public Result<List<KnowledgeCommentResponse>> getComments(
            @Parameter(description = "知识ID") @PathVariable Long id) {
        return knowledgeClient.getComments(id);
    }
} 