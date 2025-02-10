package com.lawfirm.staff.client.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.lawyer.fallback.KnowledgeClientFallback;
import com.lawfirm.staff.model.request.lawyer.knowledge.*;
import com.lawfirm.staff.model.response.lawyer.knowledge.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 知识库服务Feign客户端
 */
@FeignClient(
    name = "law-firm-core", 
    contextId = "knowledgeClient", 
    path = "/knowledge",
    fallback = KnowledgeClientFallback.class
)
public interface KnowledgeClient {
    
    @GetMapping("/page")
    Result<PageResult<KnowledgeResponse>> page(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword);
    
    @PostMapping
    Result<KnowledgeResponse> create(@RequestBody KnowledgeCreateRequest request);
    
    @PostMapping("/upload")
    Result<String> upload(@RequestParam("file") MultipartFile file);
    
    @PutMapping
    Result<Void> update(@RequestBody KnowledgeUpdateRequest request);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<KnowledgeResponse> getById(@PathVariable("id") Long id);
    
    @GetMapping("/my")
    Result<PageResult<KnowledgeResponse>> getMyKnowledge(@SpringQueryMap KnowledgePageRequest request);
    
    @GetMapping("/export")
    void export(@SpringQueryMap KnowledgePageRequest request);

    @PostMapping("/share")
    Result<Void> share(@RequestBody KnowledgeShareRequest request);

    @PostMapping("/{id}/favorite")
    Result<Void> favorite(@PathVariable Long id);

    @DeleteMapping("/{id}/favorite")
    Result<Void> unfavorite(@PathVariable Long id);

    @GetMapping("/favorites")
    Result<PageResult<KnowledgeResponse>> getFavorites(@SpringQueryMap KnowledgePageRequest request);

    @PostMapping("/{id}/like")
    Result<Void> like(@PathVariable Long id);

    @DeleteMapping("/{id}/like")
    Result<Void> unlike(@PathVariable Long id);

    @GetMapping("/categories")
    Result<List<KnowledgeCategoryResponse>> getCategories();

    @GetMapping("/tags")
    Result<List<String>> getTags();

    @GetMapping("/hot")
    Result<List<KnowledgeResponse>> getHotKnowledge();

    @GetMapping("/recommend")
    Result<List<KnowledgeResponse>> getRecommendKnowledge();

    @GetMapping("/statistics")
    Result<KnowledgeStatisticsResponse> getStatistics();

    @PostMapping("/import")
    Result<Void> importKnowledge(@RequestParam("file") MultipartFile file);

    @GetMapping("/search")
    Result<PageResult<KnowledgeResponse>> search(@RequestParam("keyword") String keyword);

    @PostMapping("/{id}/comment")
    Result<Void> comment(@PathVariable Long id, @RequestBody KnowledgeCommentRequest request);

    @GetMapping("/{id}/comments")
    Result<List<KnowledgeCommentResponse>> getComments(@PathVariable Long id);
} 