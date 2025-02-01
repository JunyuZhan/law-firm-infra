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
import com.lawfirm.common.data.storage.StorageService;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements DocumentService {

    private final DocumentMapper documentMapper;
    private final StorageService storageService;

    @Value("${storage.bucket.document}")
    private String documentBucket;

    @Override
    @Transactional
    @OperationLog(description = "上传文档", operationType = "DOCUMENT_UPLOAD")
    public DocumentVO upload(MultipartFile file, Document document, String operator) {
        try {
            String fileName = file.getOriginalFilename();
            String fileId = UUID.randomUUID().toString();
            String filePath = storageService.uploadFile(documentBucket, fileId, file.getInputStream(), file.getSize(), file.getContentType());

            document.setDocumentName(fileName);
            document.setFilePath(filePath);
            document.setFileSize(file.getSize());
            document.setFileType(file.getContentType());
            document.setLastModifiedTime(LocalDateTime.now());
            document.setLastModifiedBy(operator);
            document.setStatus(StatusEnum.ENABLED);
            
            save(document);
            return convertToVO(document);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException("文件上传失败");
        }
    }

    @Override
    @Transactional
    @OperationLog(description = "批量上传文档", operationType = "DOCUMENT_BATCH_UPLOAD")
    public List<DocumentVO> batchUpload(List<MultipartFile> files, List<Document> documents, String operator) {
        if (files.size() != documents.size()) {
            throw new BusinessException("文件数量与文档信息数量不匹配");
        }

        List<DocumentVO> results = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            results.add(upload(files.get(i), documents.get(i), operator));
        }
        return results;
    }

    @Override
    @Transactional
    @OperationLog(description = "更新文档", operationType = "DOCUMENT_UPDATE")
    public DocumentVO update(Long id, MultipartFile file, Document document, String operator) {
        Document existingDoc = getById(id);
        if (existingDoc == null) {
            throw new BusinessException("文档不存在");
        }

        try {
            document.setId(id);
            if (file != null) {
                String fileName = file.getOriginalFilename();
                String fileId = UUID.randomUUID().toString();
                String filePath = storageService.uploadFile(documentBucket, fileId, file.getInputStream(), file.getSize(), file.getContentType());

                document.setDocumentName(fileName);
                document.setFilePath(filePath);
                document.setFileSize(file.getSize());
                document.setFileType(file.getContentType());
            }
            
            document.setLastModifiedTime(LocalDateTime.now());
            document.setLastModifiedBy(operator);
            document.setVersion(existingDoc.getVersion() + 1);
            
            updateById(document);
            return convertToVO(document);
        } catch (IOException e) {
            log.error("更新文档失败", e);
            throw new BusinessException("更新文档失败");
        }
    }

    @Override
    @OperationLog(description = "下载文档", operationType = "DOCUMENT_DOWNLOAD")
    public byte[] download(Long id, String version) {
        try {
            Document document = getById(id);
            if (document == null) {
                throw new BusinessException("文档不存在");
            }

            String objectName = version == null ? 
                document.getFilePath() : 
                getVersionFilePath(document.getDocumentNumber(), version);

            return storageService.downloadFile(documentBucket, objectName).readAllBytes();
        } catch (IOException e) {
            log.error("下载文档失败", e);
            throw new BusinessException("下载文档失败");
        }
    }

    @Override
    @Transactional
    @OperationLog(description = "删除文档", operationType = "DOCUMENT_DELETE")
    public void delete(Long id, String operator) {
        Document document = getById(id);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }

        document.setStatus(StatusEnum.DELETED);
        document.setLastModifiedTime(LocalDateTime.now());
        document.setLastModifiedBy(operator);
        updateById(document);

        storageService.deleteFile(documentBucket, document.getFilePath());
    }

    @Override
    @Transactional
    @OperationLog(description = "审核文档", operationType = "DOCUMENT_AUDIT")
    public DocumentVO audit(Long id, Integer status, String comment, String operator) {
        Document document = getById(id);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }

        StatusEnum newStatus;
        switch (status) {
            case 1:
                newStatus = StatusEnum.ENABLED;
                break;
            case 2:
                newStatus = StatusEnum.ENABLED;
                break;
            case 3:
                newStatus = StatusEnum.DISABLED;
                break;
            case 4:
                newStatus = StatusEnum.ENABLED;
                break;
            case 5:
                newStatus = StatusEnum.LOCKED;
                break;
            default:
                newStatus = StatusEnum.ENABLED;
        }

        document.setStatus(newStatus);
        document.setRemark(comment);
        document.setLastModifiedTime(LocalDateTime.now());
        document.setLastModifiedBy(operator);
        
        updateById(document);
        return convertToVO(document);
    }

    @Override
    @OperationLog(description = "获取文档版本历史", operationType = "DOCUMENT_VERSION_HISTORY")
    public List<DocumentVO> getVersionHistory(Long id) {
        Document document = getById(id);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }

        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Document::getDocumentNumber, document.getDocumentNumber())
               .orderByDesc(Document::getVersion);

        return list(wrapper).stream()
            .map(this::convertToVO)
            .collect(Collectors.toList());
    }

    @Override
    @OperationLog(description = "导出文档列表", operationType = "DOCUMENT_EXPORT")
    public byte[] exportToExcel(DocumentQuery query) {
        List<Document> documents = list(buildQueryWrapper(query));
        
        // TODO: 实现Excel导出逻辑
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        return outputStream.toByteArray();
    }

    @Override
    public List<DocumentVO> listByStatus(DocumentStatusEnum status) {
        StatusEnum baseStatus;
        switch (status) {
            case DRAFT:
            case REVIEWING:
            case APPROVED:
            case PUBLISHED:
                baseStatus = StatusEnum.ENABLED;
                break;
            case REJECTED:
                baseStatus = StatusEnum.DISABLED;
                break;
            case ARCHIVED:
                baseStatus = StatusEnum.LOCKED;
                break;
            case DELETED:
                baseStatus = StatusEnum.DELETED;
                break;
            default:
                baseStatus = StatusEnum.DISABLED;
        }

        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Document::getStatus, baseStatus);
        return list(wrapper).stream()
            .map(this::convertToVO)
            .collect(Collectors.toList());
    }

    private String generateDocumentNumber() {
        return "DOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String getVersionFilePath(String documentNumber, String version) {
        return String.format("%s/%s", documentNumber, version);
    }

    private LambdaQueryWrapper<Document> buildQueryWrapper(DocumentQuery query) {
        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.isNotBlank(query.getDocumentNumber())) {
            wrapper.eq(Document::getDocumentNumber, query.getDocumentNumber());
        }
        
        if (StringUtils.isNotBlank(query.getDocumentName())) {
            wrapper.like(Document::getDocumentName, query.getDocumentName());
        }
        
        if (StringUtils.isNotBlank(query.getFileType())) {
            wrapper.eq(Document::getFileType, query.getFileType());
        }
        
        if (StringUtils.isNotBlank(query.getVersion())) {
            wrapper.eq(Document::getVersion, Integer.valueOf(query.getVersion()));
        }
        
        if (query.getStatus() != null) {
            wrapper.eq(Document::getStatus, StatusEnum.values()[query.getStatus()]);
        }
        
        if (query.getUploadTimeStart() != null) {
            wrapper.ge(Document::getCreateTime, query.getUploadTimeStart());
        }
        
        if (query.getUploadTimeEnd() != null) {
            wrapper.le(Document::getCreateTime, query.getUploadTimeEnd());
        }
        
        if (StringUtils.isNotBlank(query.getUploadBy())) {
            wrapper.eq(Document::getCreateBy, query.getUploadBy());
        }
        
        if (query.getAuditTimeStart() != null) {
            wrapper.ge(Document::getLastModifiedTime, query.getAuditTimeStart());
        }
        
        if (query.getAuditTimeEnd() != null) {
            wrapper.le(Document::getLastModifiedTime, query.getAuditTimeEnd());
        }
        
        if (StringUtils.isNotBlank(query.getAuditBy())) {
            wrapper.eq(Document::getLastModifiedBy, query.getAuditBy());
        }
        
        if (StringUtils.isNotBlank(query.getDocumentType())) {
            wrapper.eq(Document::getDocumentType, DocumentTypeEnum.valueOf(query.getDocumentType()));
        }
        
        return wrapper;
    }

    private DocumentVO convertToVO(Document document) {
        if (document == null) {
            return null;
        }
        DocumentVO vo = new DocumentVO();
        BeanUtils.copyProperties(document, vo);
        vo.setStatus(document.getStatus().ordinal());
        vo.setStatusName(document.getStatus().getDescription());
        vo.setDocumentType(document.getDocumentType().getValue());
        vo.setVersion(String.valueOf(document.getVersion()));
        return vo;
    }

    @Override
    public DocumentVO create(DocumentVO dto) {
        Document entity = toEntity(dto);
        save(entity);
        return convertToVO(entity);
    }

    @Override
    public DocumentVO update(DocumentVO dto) {
        Document entity = getById(dto.getId());
        if (entity == null) {
            throw new BusinessException("文档不存在");
        }
        BeanUtils.copyProperties(dto, entity);
        if (dto.getStatus() != null) {
            entity.setStatus(StatusEnum.values()[dto.getStatus()]);
        }
        if (dto.getVersion() != null) {
            entity.setVersion(Integer.valueOf(dto.getVersion()));
        }
        if (dto.getDocumentType() != null) {
            entity.setDocumentType(DocumentTypeEnum.valueOf(dto.getDocumentType()));
        }
        updateById(entity);
        return convertToVO(entity);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        removeByIds(ids);
    }

    @Override
    public DocumentVO findById(Long id) {
        return convertToVO(getById(id));
    }

    @Override
    public PageResult<DocumentVO> pageDocuments(Page<Document> page, QueryWrapper<Document> wrapper) {
        Page<Document> documentPage = super.page(page, wrapper);
        List<DocumentVO> records = documentPage.getRecords().stream()
            .map(this::convertToVO)
            .collect(Collectors.toList());
        
        return new PageResult<>(records, documentPage.getTotal());
    }

    @Override
    public List<DocumentVO> listDocuments(QueryWrapper<Document> wrapper) {
        return super.list(wrapper).stream()
            .map(this::convertToVO)
            .collect(Collectors.toList());
    }

    private Document toEntity(DocumentVO dto) {
        if (dto == null) {
            return null;
        }
        Document entity = new Document();
        BeanUtils.copyProperties(dto, entity);
        if (dto.getStatus() != null) {
            entity.setStatus(StatusEnum.values()[dto.getStatus()]);
        }
        if (dto.getVersion() != null) {
            entity.setVersion(Integer.valueOf(dto.getVersion()));
        }
        if (dto.getDocumentType() != null) {
            entity.setDocumentType(DocumentTypeEnum.valueOf(dto.getDocumentType()));
        }
        return entity;
    }
} 