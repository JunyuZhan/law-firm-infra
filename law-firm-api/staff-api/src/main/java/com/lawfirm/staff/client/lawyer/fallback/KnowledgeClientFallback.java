package com.lawfirm.staff.client.lawyer.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.lawyer.KnowledgeClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.lawyer.knowledge.*;
import com.lawfirm.staff.model.response.lawyer.knowledge.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 知识库服务降级实现
 */
@Slf4j
@Component
public class KnowledgeClientFallback extends FeignFallbackConfig implements KnowledgeClient {
    
    private static final String SERVICE_NAME = "知识库服务";

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<KnowledgeResponse>> page(Integer pageNum, Integer pageSize, String keyword) {
        Type type = createParameterizedType(PageResult.class, KnowledgeResponse.class);
        return (Result<PageResult<KnowledgeResponse>>) handleFallback(SERVICE_NAME, "page", 
            String.format("参数：pageNum=%d, pageSize=%d, keyword=%s", pageNum, pageSize, keyword),
            type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<KnowledgeResponse> create(KnowledgeCreateRequest request) {
        return (Result<KnowledgeResponse>) handleFallback(SERVICE_NAME, "create", 
            String.format("参数：request=%s", request),
            KnowledgeResponse.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<String> upload(MultipartFile file) {
        return (Result<String>) handleFallback(SERVICE_NAME, "upload", 
            String.format("文件名：%s", file.getOriginalFilename()),
            String.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> update(KnowledgeUpdateRequest request) {
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
    public Result<KnowledgeResponse> getById(Long id) {
        return (Result<KnowledgeResponse>) handleFallback(SERVICE_NAME, "getById", 
            String.format("参数：id=%d", id),
            KnowledgeResponse.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<KnowledgeResponse>> getMyKnowledge(KnowledgePageRequest request) {
        Type type = createParameterizedType(PageResult.class, KnowledgeResponse.class);
        return (Result<PageResult<KnowledgeResponse>>) handleFallback(SERVICE_NAME, "getMyKnowledge", 
            String.format("参数：request=%s", request),
            type);
    }

    @Override
    public void export(KnowledgePageRequest request) {
        log.error("{}服务的export方法调用失败，参数：request={}", SERVICE_NAME, request);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> share(KnowledgeShareRequest request) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "share", 
            String.format("参数：request=%s", request),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> favorite(Long id) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "favorite", 
            String.format("参数：id=%d", id),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> unfavorite(Long id) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "unfavorite", 
            String.format("参数：id=%d", id),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<KnowledgeResponse>> getFavorites(KnowledgePageRequest request) {
        Type type = createParameterizedType(PageResult.class, KnowledgeResponse.class);
        return (Result<PageResult<KnowledgeResponse>>) handleFallback(SERVICE_NAME, "getFavorites", 
            String.format("参数：request=%s", request),
            type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> like(Long id) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "like", 
            String.format("参数：id=%d", id),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> unlike(Long id) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "unlike", 
            String.format("参数：id=%d", id),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<KnowledgeCategoryResponse>> getCategories() {
        Type type = createParameterizedType(List.class, KnowledgeCategoryResponse.class);
        return (Result<List<KnowledgeCategoryResponse>>) handleFallback(SERVICE_NAME, "getCategories", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<String>> getTags() {
        Type type = createParameterizedType(List.class, String.class);
        return (Result<List<String>>) handleFallback(SERVICE_NAME, "getTags", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<KnowledgeResponse>> getHotKnowledge() {
        Type type = createParameterizedType(List.class, KnowledgeResponse.class);
        return (Result<List<KnowledgeResponse>>) handleFallback(SERVICE_NAME, "getHotKnowledge", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<KnowledgeResponse>> getRecommendKnowledge() {
        Type type = createParameterizedType(List.class, KnowledgeResponse.class);
        return (Result<List<KnowledgeResponse>>) handleFallback(SERVICE_NAME, "getRecommendKnowledge", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<KnowledgeStatisticsResponse> getStatistics() {
        return (Result<KnowledgeStatisticsResponse>) handleFallback(SERVICE_NAME, "getStatistics", 
            KnowledgeStatisticsResponse.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> importKnowledge(MultipartFile file) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "importKnowledge", 
            String.format("文件名：%s", file.getOriginalFilename()),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<KnowledgeResponse>> search(String keyword) {
        Type type = createParameterizedType(PageResult.class, KnowledgeResponse.class);
        return (Result<PageResult<KnowledgeResponse>>) handleFallback(SERVICE_NAME, "search", 
            String.format("参数：keyword=%s", keyword),
            type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> comment(Long id, KnowledgeCommentRequest request) {
        return (Result<Void>) handleFallback(SERVICE_NAME, "comment", 
            String.format("参数：id=%d, request=%s", id, request),
            Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<KnowledgeCommentResponse>> getComments(Long id) {
        Type type = createParameterizedType(List.class, KnowledgeCommentResponse.class);
        return (Result<List<KnowledgeCommentResponse>>) handleFallback(SERVICE_NAME, "getComments", 
            String.format("参数：id=%d", id),
            type);
    }
} 