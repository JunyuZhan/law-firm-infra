package com.lawfirm.staff.client.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.lawyer.fallback.CaseClientFallback;
import com.lawfirm.staff.model.request.lawyer.cases.*;
import com.lawfirm.staff.model.request.lawyer.cases.document.CaseDocumentCreateRequest;
import com.lawfirm.staff.model.request.lawyer.cases.progress.CaseProgressCreateRequest;
import com.lawfirm.staff.model.request.lawyer.cases.relation.CaseRelationCreateRequest;
import com.lawfirm.staff.model.response.lawyer.cases.*;
import com.lawfirm.staff.model.response.lawyer.cases.document.CaseDocumentResponse;
import com.lawfirm.staff.model.response.lawyer.cases.progress.CaseProgressResponse;
import com.lawfirm.staff.model.response.lawyer.cases.relation.CaseRelationResponse;
import com.lawfirm.staff.model.response.lawyer.cases.statistics.CaseStatisticsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 案件服务Feign客户端
 */
@FeignClient(
    name = "law-firm-case",
    contextId = "caseClient",
    path = "/case",
    fallback = CaseClientFallback.class
)
public interface CaseClient {
    
    @GetMapping("/page")
    Result<PageResult<CaseResponse>> page(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "orderType", required = false) String orderType);
    
    @PostMapping
    Result<Void> create(@RequestBody CaseCreateRequest request);
    
    @PutMapping
    Result<Void> update(@RequestBody CaseUpdateRequest request);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);

    @PostMapping("/batch/delete")
    Result<Void> batchDelete(@RequestBody List<Long> ids);
    
    @GetMapping("/{id}")
    Result<CaseResponse> get(@PathVariable("id") Long id);
    
    @GetMapping("/my")
    Result<PageResult<CaseResponse>> getMyCases(@SpringQueryMap CasePageRequest request);
    
    @GetMapping("/department")
    Result<PageResult<CaseResponse>> getDepartmentCases(@SpringQueryMap CasePageRequest request);
    
    @PostMapping("/{id}/progress")
    Result<Void> addProgress(@PathVariable("id") Long id, @RequestBody CaseProgressCreateRequest request);
    
    @GetMapping("/{id}/progress")
    Result<List<CaseProgressResponse>> getProgressList(@PathVariable("id") Long id);
    
    @PostMapping("/{id}/document")
    Result<Void> uploadDocument(@PathVariable("id") Long id, @RequestBody CaseDocumentCreateRequest request);
    
    @GetMapping("/{id}/document")
    Result<List<CaseDocumentResponse>> getDocumentList(@PathVariable("id") Long id);
    
    @GetMapping("/document/{documentId}")
    void downloadDocument(@PathVariable("documentId") Long documentId);
    
    @PostMapping("/{id}/relation")
    Result<Void> addRelation(@PathVariable("id") Long id, @RequestBody CaseRelationCreateRequest request);
    
    @GetMapping("/{id}/relation")
    Result<List<CaseRelationResponse>> getRelationList(@PathVariable("id") Long id);
    
    @DeleteMapping("/relation/{relationId}")
    Result<Void> deleteRelation(@PathVariable("relationId") Long relationId);
    
    @GetMapping("/statistics")
    Result<CaseStatisticsResponse> getStatistics();
    
    @GetMapping("/progress/distribution")
    Result<List<CaseStatisticsResponse.ProgressDistribution>> getProgressDistribution();
    
    @GetMapping("/type/distribution")
    Result<List<CaseStatisticsResponse.TypeDistribution>> getTypeDistribution();
    
    @GetMapping("/source/distribution")
    Result<List<CaseStatisticsResponse.SourceDistribution>> getSourceDistribution();
} 