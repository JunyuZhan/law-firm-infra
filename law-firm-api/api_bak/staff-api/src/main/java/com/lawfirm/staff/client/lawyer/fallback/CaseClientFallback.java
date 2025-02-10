package com.lawfirm.staff.client.lawyer.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.lawyer.CaseClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.lawyer.cases.*;
import com.lawfirm.staff.model.request.lawyer.cases.document.CaseDocumentCreateRequest;
import com.lawfirm.staff.model.request.lawyer.cases.progress.CaseProgressCreateRequest;
import com.lawfirm.staff.model.request.lawyer.cases.relation.CaseRelationCreateRequest;
import com.lawfirm.staff.model.response.lawyer.cases.*;
import com.lawfirm.staff.model.response.lawyer.cases.document.CaseDocumentResponse;
import com.lawfirm.staff.model.response.lawyer.cases.progress.CaseProgressResponse;
import com.lawfirm.staff.model.response.lawyer.cases.relation.CaseRelationResponse;
import com.lawfirm.staff.model.response.lawyer.cases.statistics.CaseStatisticsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 案件服务降级实现
 */
@Slf4j
@Component
public class CaseClientFallback extends FeignFallbackConfig implements CaseClient {
    
    private static final String SERVICE_NAME = "案件服务";

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<CaseResponse>> page(Integer pageNum, Integer pageSize, String keyword, String orderBy, String orderType) {
        Type type = createParameterizedType(PageResult.class, CaseResponse.class);
        return (Result<PageResult<CaseResponse>>) handleFallback(SERVICE_NAME, "page", 
            String.format("参数：pageNum=%d, pageSize=%d, keyword=%s", pageNum, pageSize, keyword),
            type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> create(CaseCreateRequest request) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "create", 
            String.format("参数：request=%s", request),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> update(CaseUpdateRequest request) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "update", 
            String.format("参数：request=%s", request),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> delete(Long id) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "delete", 
            String.format("参数：id=%d", id),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<CaseResponse> get(Long id) {
        return (Result<CaseResponse>) handleFallback(SERVICE_NAME, "get", 
            String.format("参数：id=%d", id),
            CaseResponse.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<CaseResponse>> getMyCases(CasePageRequest request) {
        Type type = createParameterizedType(PageResult.class, CaseResponse.class);
        return (Result<PageResult<CaseResponse>>) handleFallback(SERVICE_NAME, "getMyCases", 
            String.format("参数：request=%s", request),
            type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<CaseResponse>> getDepartmentCases(CasePageRequest request) {
        Type type = createParameterizedType(PageResult.class, CaseResponse.class);
        return (Result<PageResult<CaseResponse>>) handleFallback(SERVICE_NAME, "getDepartmentCases", 
            String.format("参数：request=%s", request),
            type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> addProgress(Long id, CaseProgressCreateRequest request) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "addProgress", 
            String.format("参数：id=%d, request=%s", id, request),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<CaseProgressResponse>> getProgressList(Long id) {
        Type type = createParameterizedType(List.class, CaseProgressResponse.class);
        return (Result<List<CaseProgressResponse>>) handleFallback(SERVICE_NAME, "getProgressList", 
            String.format("参数：id=%d", id),
            type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> uploadDocument(Long id, CaseDocumentCreateRequest request) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "uploadDocument", 
            String.format("参数：id=%d, request=%s", id, request),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<CaseDocumentResponse>> getDocumentList(Long id) {
        Type type = createParameterizedType(List.class, CaseDocumentResponse.class);
        return (Result<List<CaseDocumentResponse>>) handleFallback(SERVICE_NAME, "getDocumentList", 
            String.format("参数：id=%d", id),
            type);
    }

    @Override
    public void downloadDocument(Long documentId) {
        log.error("{}服务的downloadDocument方法调用失败，参数：documentId={}", SERVICE_NAME, documentId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> addRelation(Long id, CaseRelationCreateRequest request) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "addRelation", 
            String.format("参数：id=%d, request=%s", id, request),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<CaseRelationResponse>> getRelationList(Long id) {
        Type type = createParameterizedType(List.class, CaseRelationResponse.class);
        return (Result<List<CaseRelationResponse>>) handleFallback(SERVICE_NAME, "getRelationList", 
            String.format("参数：id=%d", id),
            type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> deleteRelation(Long relationId) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "deleteRelation", 
            String.format("参数：relationId=%d", relationId),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<CaseStatisticsResponse> getStatistics() {
        return (Result<CaseStatisticsResponse>) handleFallback(SERVICE_NAME, "getStatistics", 
            CaseStatisticsResponse.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<CaseStatisticsResponse.ProgressDistribution>> getProgressDistribution() {
        Type type = createParameterizedType(List.class, CaseStatisticsResponse.ProgressDistribution.class);
        return (Result<List<CaseStatisticsResponse.ProgressDistribution>>) handleFallback(SERVICE_NAME, 
            "getProgressDistribution", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<CaseStatisticsResponse.TypeDistribution>> getTypeDistribution() {
        Type type = createParameterizedType(List.class, CaseStatisticsResponse.TypeDistribution.class);
        return (Result<List<CaseStatisticsResponse.TypeDistribution>>) handleFallback(SERVICE_NAME, 
            "getTypeDistribution", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<CaseStatisticsResponse.SourceDistribution>> getSourceDistribution() {
        Type type = createParameterizedType(List.class, CaseStatisticsResponse.SourceDistribution.class);
        return (Result<List<CaseStatisticsResponse.SourceDistribution>>) handleFallback(SERVICE_NAME, 
            "getSourceDistribution", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> batchDelete(List<Long> ids) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "batchDelete", 
            String.format("参数：ids=%s", ids),
            Void.class);
    }
} 