package com.lawfirm.model.document.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.document.dto.document.DocumentCreateDTO;
import com.lawfirm.model.document.dto.document.DocumentQueryDTO;
import com.lawfirm.model.document.dto.document.DocumentUpdateDTO;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.model.document.dto.DocumentDTO;
import com.lawfirm.model.document.dto.DocumentUploadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 文档服务接口
 */
public interface DocumentService extends BaseService<BaseDocument> {

    /**
     * 创建文档
     *
     * @param createDTO 创建参数
     * @param inputStream 文件输入流
     * @return 文档ID
     */
    Long createDocument(DocumentCreateDTO createDTO, InputStream inputStream);

    /**
     * 更新文档
     *
     * @param id 文档ID
     * @param updateDTO 更新参数
     */
    void updateDocument(Long id, DocumentUpdateDTO updateDTO);

    /**
     * 更新文档内容
     *
     * @param id 文档ID
     * @param inputStream 文件输入流
     */
    void updateDocumentContent(Long id, InputStream inputStream);

    /**
     * 批量删除文档
     *
     * @param ids 文档ID列表
     */
    void deleteDocuments(List<Long> ids);

    /**
     * 获取文档详情
     *
     * @param id 文档ID
     * @return 文档详情
     */
    DocumentVO getDocumentById(Long id);

    /**
     * 分页查询文档
     *
     * @param page 分页参数
     * @param queryDTO 查询条件
     * @return 文档列表
     */
    Page<DocumentVO> pageDocuments(Page<BaseDocument> page, DocumentQueryDTO queryDTO);

    /**
     * 下载文档
     *
     * @param id 文档ID
     * @return 文件输入流
     */
    InputStream downloadDocument(Long id);

    /**
     * 预览文档
     *
     * @param id 文档ID
     * @return 预览URL
     */
    String previewDocument(Long id);

    /**
     * 获取文档访问URL
     *
     * @param id 文档ID
     * @return 访问URL
     */
    String getDocumentUrl(Long id);

    /**
     * 获取文档访问URL（带有效期）
     *
     * @param id 文档ID
     * @param expireTime 有效期（秒）
     * @return 访问URL
     */
    String getDocumentUrl(Long id, Long expireTime);

    /**
     * 更新文档状态
     *
     * @param id 文档ID
     * @param status 状态
     */
    void updateStatus(Long id, String status);

    /**
     * 根据业务ID和类型获取文档列表
     *
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @return 文档列表
     */
    List<DocumentVO> listDocumentsByBusiness(Long businessId, String businessType);

    /**
     * 根据文档类型获取文档列表
     *
     * @param docType 文档类型
     * @return 文档列表
     */
    List<DocumentVO> listDocumentsByType(String docType);

    /**
     * 刷新文档缓存
     */
    void refreshCache();

    /**
     * 上传文档
     *
     * @param file 文件
     * @param uploadDTO 上传参数
     * @return 文档ID
     */
    Long uploadDocument(MultipartFile file, DocumentUploadDTO uploadDTO);

    /**
     * 获取业务相关文档列表
     *
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 文档列表
     */
    List<DocumentDTO> getBusinessDocuments(String businessType, Long businessId);

    /**
     * 获取文档详情
     *
     * @param documentId 文档ID
     * @return 文档详情
     */
    DocumentDTO getDocumentDetail(Long documentId);

    /**
     * 删除文档
     *
     * @param documentId 文档ID
     * @return 是否成功
     */
    boolean deleteDocument(Long documentId);

    /**
     * 设置文档标签
     *
     * @param documentId 文档ID
     * @param tags 标签列表
     * @return 是否成功
     */
    boolean setDocumentTags(Long documentId, List<String> tags);
} 