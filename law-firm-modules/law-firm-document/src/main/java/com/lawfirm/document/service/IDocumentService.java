package com.lawfirm.document.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.document.dto.request.DocumentAddRequest;
import com.lawfirm.document.dto.request.DocumentQueryRequest;
import com.lawfirm.document.dto.request.DocumentUpdateRequest;
import com.lawfirm.document.dto.response.DocumentResponse;
import com.lawfirm.document.entity.Document;

/**
 * 文档服务接口
 */
public interface IDocumentService extends IService<Document> {

    /**
     * 添加文档
     *
     * @param request 添加请求
     * @return 文档ID
     */
    Long addDocument(DocumentAddRequest request);

    /**
     * 更新文档
     *
     * @param request 更新请求
     */
    void updateDocument(DocumentUpdateRequest request);

    /**
     * 删除文档
     *
     * @param id 文档ID
     */
    void deleteDocument(Long id);

    /**
     * 分页查询文档
     *
     * @param request 查询请求
     * @return 分页结果
     */
    IPage<DocumentResponse> pageDocuments(DocumentQueryRequest request);

    /**
     * 获取文档详情
     *
     * @param id 文档ID
     * @return 文档详情
     */
    DocumentResponse getDocumentById(Long id);
} 