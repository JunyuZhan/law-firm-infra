package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.document.mapper.DocumentMapper;
import com.lawfirm.document.service.DocumentService;
import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.document.query.DocumentQuery;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.model.document.enums.DocumentStatusEnum;
import com.lawfirm.model.document.enums.DocumentTypeEnum;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.base.storage.model.FileMetadata;
import com.lawfirm.model.base.storage.service.StorageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 文档服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements DocumentService {

    private final DocumentMapper documentMapper;
    private final StorageService storageService;

    @Value("${storage.bucket.document}")
    private String documentBucket;

    private static final String BUSINESS_TYPE = "document";

    @Override
    @Transactional
    @OperationLog(description = "创建文档", operationType = "DOCUMENT_CREATE")
    public DocumentVO createDocument(MultipartFile file, Document document, String operator) {
        // 1. 上传文件到存储服务
        String businessKey = String.format("%s/%s", document.getDocumentType().getValue(), document.getCaseId());
        FileMetadata fileMetadata = storageService.upload(file, BUSINESS_TYPE, businessKey);
        
        // 2. 保存文档记录
        document.setDocumentName(file.getOriginalFilename());
        document.setFileSize(fileMetadata.getSize());
        document.setFileType(fileMetadata.getContentType());
        document.setFilePath(fileMetadata.getPath());
        document.setFileHash(fileMetadata.getMd5());
        document.setStatusEnum(DocumentStatusEnum.DRAFT);
        document.setVersion(1);
        document.setDocumentNumber(generateDocumentNumber());
        document.setLastModifiedTime(LocalDateTime.now());
        document.setLastModifiedBy(operator);
        document.setCreateBy(operator);
        
        save(document);
        
        // 3. 转换并返回结果
        return convertToVO(document);
    }

    @Override
    @Transactional
    @OperationLog(description = "批量创建文档", operationType = "DOCUMENT_BATCH_CREATE")
    public List<DocumentVO> batchCreateDocuments(List<MultipartFile> files, List<Document> documents, String operator) {
        if (files.size() != documents.size()) {
            throw new BusinessException("文件数量与文档信息数量不匹配");
        }
        return files.stream()
                .map(file -> createDocument(file, documents.get(files.indexOf(file)), operator))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @OperationLog(description = "更新文档", operationType = "DOCUMENT_UPDATE")
    public DocumentVO updateDocument(Long id, MultipartFile file, Document document, String operator) {
        // 1. 检查文档是否存在
        Document existingDoc = getById(id);
        if (existingDoc == null) {
            throw new BusinessException("文档不存在");
        }

        // 2. 如果有新文件，先上传
        if (file != null) {
            String businessKey = String.format("%s/%s", existingDoc.getDocumentType().getValue(), existingDoc.getCaseId());
            FileMetadata fileMetadata = storageService.upload(file, BUSINESS_TYPE, businessKey);
            
            document.setDocumentName(file.getOriginalFilename());
            document.setFileSize(fileMetadata.getSize());
            document.setFileType(fileMetadata.getContentType());
            document.setFilePath(fileMetadata.getPath());
            document.setFileHash(fileMetadata.getMd5());
        }
        
        // 3. 更新文档记录
        document.setId(id);
        document.setVersion(existingDoc.getVersion() + 1);
        document.setLastModifiedTime(LocalDateTime.now());
        document.setLastModifiedBy(operator);
        
        updateById(document);
        
        // 4. 转换并返回结果
        return convertToVO(document);
    }

    @Override
    @Transactional
    @OperationLog(description = "更新文档信息", operationType = "DOCUMENT_INFO_UPDATE")
    public DocumentVO updateDocumentInfo(DocumentVO documentVO, String operator) {
        // 1. 检查文档是否存在
        Document document = getById(documentVO.getId());
        if (document == null) {
            throw new BusinessException("文档不存在");
        }
        
        // 2. 更新基本信息
        Document updateDoc = convertToEntity(documentVO);
        updateDoc.setLastModifiedTime(LocalDateTime.now());
        updateDoc.setLastModifiedBy(operator);
        
        updateById(updateDoc);
        
        // 3. 返回更新后的信息
        return convertToVO(updateDoc);
    }

    @Override
    @Transactional
    @OperationLog(description = "删除文档", operationType = "DOCUMENT_DELETE")
    public void deleteDocument(Long id, String operator) {
        // 1. 检查文档是否存在
        Document document = getById(id);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }
        
        // 2. 更新文档状态
        document.setStatusEnum(DocumentStatusEnum.DELETED);
        document.setLastModifiedTime(LocalDateTime.now());
        document.setLastModifiedBy(operator);
        
        updateById(document);
    }

    @Override
    @Transactional
    @OperationLog(description = "批量删除文档", operationType = "DOCUMENT_BATCH_DELETE")
    public void batchDeleteDocuments(List<Long> ids, String operator) {
        // 1. 查询所有文档
        List<Document> documents = listByIds(ids);
        
        // 2. 批量更新文档状态
        documents.forEach(doc -> {
            doc.setStatusEnum(DocumentStatusEnum.DELETED);
            doc.setLastModifiedTime(LocalDateTime.now());
            doc.setLastModifiedBy(operator);
        });
        
        updateBatchById(documents);
    }

    @Override
    @OperationLog(description = "下载文档", operationType = "DOCUMENT_DOWNLOAD")
    public byte[] downloadDocument(Long id, String version) {
        // 1. 检查文档是否存在
        Document document = getById(id);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }
        
        try (InputStream is = storageService.download(document.getFilePath())) {
            return is.readAllBytes();
        } catch (Exception e) {
            log.error("下载文档失败", e);
            throw new BusinessException("下载文档失败");
        }
    }

    @Override
    @OperationLog(description = "获取文档预览地址", operationType = "DOCUMENT_PREVIEW")
    public String getDocumentPreviewUrl(Long id, String version) {
        // 1. 检查文档是否存在
        Document document = getById(id);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }
        
        // 2. 获取预览地址
        FileMetadata metadata = storageService.getMetadata(document.getFilePath());
        return metadata.getUrl();
    }

    @Override
    @Transactional
    @OperationLog(description = "审核文档", operationType = "DOCUMENT_AUDIT")
    public DocumentVO auditDocument(Long id, Integer status, String comment, String operator) {
        // 1. 检查文档是否存在
        Document document = getById(id);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }
        
        // 2. 更新审核状态
        document.setStatusEnum(DocumentStatusEnum.values()[status]);
        document.setRemark(comment);
        document.setLastModifiedTime(LocalDateTime.now());
        document.setLastModifiedBy(operator);
        
        updateById(document);
        
        // 3. 返回更新后的信息
        return convertToVO(document);
    }

    @Override
    @OperationLog(description = "获取文档版本历史", operationType = "DOCUMENT_VERSION_HISTORY")
    public List<DocumentVO> getDocumentVersions(Long id) {
        // 1. 检查文档是否存在
        Document document = getById(id);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }
        
        // 2. 查询所有版本
        return lambdaQuery()
                .eq(Document::getDocumentNumber, document.getDocumentNumber())
                .orderByDesc(Document::getVersion)
                .list()
                .stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @OperationLog(description = "根据ID查询文档", operationType = "DOCUMENT_GET")
    public DocumentVO getDocumentById(Long id) {
        Document document = getById(id);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }
        return convertToVO(document);
    }

    @Override
    @OperationLog(description = "根据状态查询文档", operationType = "DOCUMENT_LIST_BY_STATUS")
    public List<DocumentVO> getDocumentsByStatus(DocumentStatusEnum status) {
        return lambdaQuery()
                .eq(Document::getStatusEnum, status)
                .list()
                .stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @OperationLog(description = "分页查询文档", operationType = "DOCUMENT_PAGE")
    public PageResult<DocumentVO> pageDocuments(Page<Document> page, QueryWrapper<Document> wrapper) {
        Page<Document> result = page(page, wrapper);
        List<DocumentVO> records = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        return new PageResult<>(records, result.getTotal());
    }

    @Override
    @OperationLog(description = "导出文档列表", operationType = "DOCUMENT_EXPORT")
    public byte[] exportDocuments(DocumentQuery query) {
        // TODO: 实现Excel导出逻辑
        throw new UnsupportedOperationException("未实现");
    }

    @Override
    public FileMetadata uploadFile(MultipartFile file, String documentType, String businessId) {
        String businessKey = String.format("%s/%s", documentType, businessId);
        return storageService.upload(file, BUSINESS_TYPE, businessKey);
    }

    @Override
    public InputStream downloadFile(String fileId) {
        return storageService.download(fileId);
    }

    @Override
    public void deleteFile(String fileId) {
        storageService.delete(fileId);
    }

    @Override
    public List<FileMetadata> listFiles(String documentType, String businessId) {
        String businessKey = String.format("%s/%s", documentType, businessId);
        return storageService.listByBusiness(BUSINESS_TYPE, businessKey);
    }

    private DocumentVO convertToVO(Document document) {
        if (document == null) {
            return null;
        }
        DocumentVO vo = new DocumentVO();
        BeanUtils.copyProperties(document, vo);
        
        // 设置扩展字段
        vo.setVersion(String.valueOf(document.getVersion()));
        vo.setStatus(document.getStatusEnum().ordinal());
        vo.setStatusName(document.getStatusEnum().getDescription());
        vo.setDocumentType(document.getDocumentType().getValue());
        vo.setUploadTime(document.getLastModifiedTime());
        vo.setUploadBy(document.getLastModifiedBy());
        vo.setAuditTime(document.getLastModifiedTime());
        vo.setAuditBy(document.getLastModifiedBy());
        vo.setAuditComment(document.getRemark());
        
        return vo;
    }

    private Document convertToEntity(DocumentVO vo) {
        if (vo == null) {
            return null;
        }
        Document document = new Document();
        BeanUtils.copyProperties(vo, document);
        
        // 转换枚举类型
        if (vo.getStatus() != null) {
            document.setStatusEnum(DocumentStatusEnum.values()[vo.getStatus()]);
        }
        if (StringUtils.isNotBlank(vo.getDocumentType())) {
            document.setDocumentType(DocumentTypeEnum.valueOf(vo.getDocumentType()));
        }
        
        return document;
    }

    private String generateDocumentNumber() {
        return "DOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 