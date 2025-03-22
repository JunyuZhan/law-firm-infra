package com.lawfirm.api.adaptor.document;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.document.dto.document.DocumentCreateDTO;
import com.lawfirm.model.document.dto.document.DocumentQueryDTO;
import com.lawfirm.model.document.dto.document.DocumentUpdateDTO;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.model.document.dto.DocumentDTO;
import com.lawfirm.model.document.dto.DocumentUploadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 文档管理适配器
 * <p>
 * 该适配器负责处理与文档管理相关的所有操作，包括文档的创建、更新、删除、查询和下载等功能。
 * 作为API层与服务层之间的桥梁，转换请求参数并调用DocumentService提供的服务。
 * </p>
 * 
 * @author lawfirm-dev
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component("documentsAdaptor")
public class DocumentAdaptor extends BaseAdaptor {

    @Autowired
    private DocumentService documentService;

    /**
     * 创建文档
     * 
     * @param createDTO 文档创建数据传输对象
     * @param inputStream 文档内容输入流
     * @return 创建的文档ID
     */
    public Long createDocument(DocumentCreateDTO createDTO, InputStream inputStream) {
        log.info("创建文档: {}", createDTO);
        return documentService.createDocument(createDTO, inputStream);
    }

    /**
     * 更新文档
     * 
     * @param id 文档ID
     * @param updateDTO 文档更新数据传输对象
     */
    public void updateDocument(Long id, DocumentUpdateDTO updateDTO) {
        log.info("更新文档: id={}, {}", id, updateDTO);
        documentService.updateDocument(id, updateDTO);
    }

    /**
     * 更新文档内容
     * 
     * @param id 文档ID
     * @param inputStream 新的文档内容输入流
     */
    public void updateDocumentContent(Long id, InputStream inputStream) {
        log.info("更新文档内容: id={}", id);
        documentService.updateDocumentContent(id, inputStream);
    }

    /**
     * 获取文档详情
     * 
     * @param id 文档ID
     * @return 文档视图对象
     */
    public DocumentVO getDocument(Long id) {
        log.info("获取文档详情: id={}", id);
        return documentService.getDocumentById(id);
    }

    /**
     * 删除文档
     * 
     * @param id 文档ID
     * @return 删除操作是否成功
     */
    public boolean deleteDocument(Long id) {
        log.info("删除文档: id={}", id);
        return documentService.deleteDocument(id);
    }

    /**
     * 批量删除文档
     * 
     * @param ids 文档ID列表
     */
    public void deleteDocuments(List<Long> ids) {
        log.info("批量删除文档: ids={}", ids);
        documentService.deleteDocuments(ids);
    }

    /**
     * 分页查询文档
     * 
     * @param page 分页参数
     * @param queryDTO 查询条件
     * @return 文档分页结果
     */
    public Page<DocumentVO> pageDocuments(Page<BaseDocument> page, DocumentQueryDTO queryDTO) {
        log.info("分页查询文档: page={}, {}", page, queryDTO);
        return documentService.pageDocuments(page, queryDTO);
    }

    /**
     * 根据业务类型和ID获取文档列表
     * 
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @return 文档视图对象列表
     */
    public List<DocumentVO> listDocumentsByBusiness(Long businessId, String businessType) {
        log.info("获取业务相关文档: businessId={}, businessType={}", businessId, businessType);
        return documentService.listDocumentsByBusiness(businessId, businessType);
    }

    /**
     * 根据文档类型获取文档列表
     * 
     * @param docType 文档类型
     * @return 文档视图对象列表
     */
    public List<DocumentVO> listDocumentsByType(String docType) {
        log.info("根据类型获取文档列表: docType={}", docType);
        return documentService.listDocumentsByType(docType);
    }

    /**
     * 上传文档
     * 
     * @param file 上传的文件
     * @param uploadDTO 上传参数
     * @return 上传的文档ID
     */
    public Long uploadDocument(MultipartFile file, DocumentUploadDTO uploadDTO) {
        log.info("上传文档: {}", uploadDTO);
        return documentService.uploadDocument(file, uploadDTO);
    }

    /**
     * 下载文档
     * 
     * @param id 文档ID
     * @return 包含文档内容的HTTP响应
     */
    public ResponseEntity<byte[]> downloadDocument(Long id) {
        log.info("下载文档: id={}", id);
        InputStream inputStream = documentService.downloadDocument(id);
        DocumentVO documentVO = documentService.getDocumentById(id);
        
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            byte[] bytes = outputStream.toByteArray();
            
            String fileName = URLEncoder.encode(documentVO.getFileName(), StandardCharsets.UTF_8.toString())
                    .replaceAll("\\+", "%20");
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(bytes.length);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(bytes);
            
        } catch (IOException e) {
            log.error("文档下载失败: id={}, error={}", id, e.getMessage(), e);
            throw new RuntimeException("文档下载失败: " + e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.warn("关闭文档流失败: {}", e.getMessage());
            }
        }
    }

    /**
     * 预览文档
     * 
     * @param id 文档ID
     * @return 预览URL
     */
    public String previewDocument(Long id) {
        log.info("预览文档: id={}", id);
        return documentService.previewDocument(id);
    }

    /**
     * 更新文档状态
     * 
     * @param id 文档ID
     * @param status 状态
     */
    public void updateStatus(Long id, String status) {
        log.info("更新文档状态: id={}, status={}", id, status);
        documentService.updateStatus(id, status);
    }

    /**
     * 获取文档访问URL
     * 
     * @param id 文档ID
     * @return 访问URL
     */
    public String getDocumentUrl(Long id) {
        log.info("获取文档访问URL: id={}", id);
        return documentService.getDocumentUrl(id);
    }

    /**
     * 获取文档访问URL（带有效期）
     * 
     * @param id 文档ID
     * @param expireTime 过期时间（毫秒）
     * @return 访问URL
     */
    public String getDocumentUrl(Long id, Long expireTime) {
        log.info("获取文档访问URL(带有效期): id={}, expireTime={}", id, expireTime);
        return documentService.getDocumentUrl(id, expireTime);
    }

    /**
     * 获取业务相关文档列表
     * 
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 文档DTO列表
     */
    public List<DocumentDTO> getBusinessDocuments(String businessType, Long businessId) {
        log.info("获取业务相关文档列表: businessType={}, businessId={}", businessType, businessId);
        return documentService.getBusinessDocuments(businessType, businessId);
    }

    /**
     * 获取文档详情
     * 
     * @param documentId 文档ID
     * @return 文档DTO
     */
    public DocumentDTO getDocumentDetail(Long documentId) {
        log.info("获取文档详情: documentId={}", documentId);
        return documentService.getDocumentDetail(documentId);
    }

    /**
     * 设置文档标签
     * 
     * @param documentId 文档ID
     * @param tags 标签列表
     * @return 操作是否成功
     */
    public boolean setDocumentTags(Long documentId, List<String> tags) {
        log.info("设置文档标签: documentId={}, tags={}", documentId, tags);
        return documentService.setDocumentTags(documentId, tags);
    }

    /**
     * 刷新文档缓存
     */
    public void refreshCache() {
        log.info("刷新文档缓存");
        documentService.refreshCache();
    }
} 