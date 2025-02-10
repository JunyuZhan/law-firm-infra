package com.lawfirm.document.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.document.dto.request.DocumentVersionAddRequest;
import com.lawfirm.document.dto.response.DocumentVersionResponse;
import com.lawfirm.document.entity.DocumentVersion;
import java.util.List;

/**
 * 文档版本服务接口
 */
public interface IDocumentVersionService extends IService<DocumentVersion> {

    /**
     * 添加文档版本
     *
     * @param request 添加请求
     * @return 版本ID
     */
    Long addVersion(DocumentVersionAddRequest request);

    /**
     * 获取文档的所有版本
     *
     * @param docId 文档ID
     * @return 版本列表
     */
    List<DocumentVersionResponse> getVersionsByDocId(Long docId);

    /**
     * 获取文档的指定版本
     *
     * @param docId 文档ID
     * @param versionNo 版本号
     * @return 版本信息
     */
    DocumentVersionResponse getVersion(Long docId, Integer versionNo);

    /**
     * 回滚到指定版本
     *
     * @param docId 文档ID
     * @param versionNo 版本号
     */
    void rollbackToVersion(Long docId, Integer versionNo);
} 