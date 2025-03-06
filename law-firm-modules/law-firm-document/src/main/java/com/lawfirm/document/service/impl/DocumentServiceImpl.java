package com.lawfirm.modules.document.service.impl;

import com.lawfirm.modules.document.entity.Document;
import com.lawfirm.modules.document.entity.DocumentStorage;
import com.lawfirm.model.document.repository.DocumentRepository;
import com.lawfirm.model.document.repository.DocumentStorageRepository;
import com.lawfirm.modules.document.service.DocumentService;
import com.lawfirm.modules.document.vo.DocumentVO;
import com.lawfirm.core.storage.service.StorageService;
import com.lawfirm.core.storage.model.FileMetadata;
import com.lawfirm.model.base.StatusEnum;
import com.lawfirm.model.base.DocumentStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentStorageRepository documentStorageRepository;
    private final StorageService storageService;

    @Override
    @Transactional
    public Document createDocument(Document document) {
        // 设置初始状态
        document.setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setIsDeleted(false);
        
        return documentRepository.save(document);
    }

    @Override
    @Transactional
    public Document updateDocument(Document document) {
        Document existingDocument = getDocument(document.getId());
        if (existingDocument == null) {
            throw new IllegalArgumentException("文档不存在");
        }

        // 更新基本信息
        document.setUpdateTime(LocalDateTime.now());
        return documentRepository.save(document);
    }

    @Override
    @Transactional
    public void deleteDocument(Long id) {
        Document document = getDocument(id);
        if (document == null) {
            return;
        }

        // 逻辑删除
        document.setIsDeleted(true)
                .setDeleteTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        documentRepository.save(document);

        // 标记存储记录为已删除
        List<DocumentStorage> storageList = documentStorageRepository.findByDocumentIdOrderByCreateTimeDesc(id);
        for (DocumentStorage storage : storageList) {
            storage.setIsDeleted(true)
                    .setDeleteTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now());
            documentStorageRepository.save(storage);
        }
    }

    @Override
    public Document getDocument(Long id) {
        return documentRepository.findById(id)
                .filter(doc -> !Boolean.TRUE.equals(doc.getIsDeleted()))
                .orElse(null);
    }

    @Override
    public Page<Document> listDocuments(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

    @Override
    public List<Document> listByCategory(Long categoryId) {
        return documentRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Document> listByCase(Long caseId) {
        return documentRepository.findByCaseId(caseId);
    }

    @Override
    public List<Document> listByContract(Long contractId) {
        return documentRepository.findByContractId(contractId);
    }

    @Override
    public List<Document> listByClient(Long clientId) {
        return documentRepository.findByClientId(clientId);
    }

    @Override
    @Transactional
    public void moveToCategory(Long documentId, Long categoryId) {
        Document document = getDocument(documentId);
        if (document == null) {
            throw new IllegalArgumentException("文档不存在");
        }

        document.setCategoryId(categoryId)
                .setUpdateTime(LocalDateTime.now());
        documentRepository.save(document);
    }

    @Override
    @Transactional
    public Document copyDocument(Long sourceId, String newName) {
        Document source = getDocument(sourceId);
        if (source == null) {
            throw new IllegalArgumentException("源文档不存在");
        }

        // 复制文档基本信息
        Document newDocument = new Document();
        BeanUtils.copyProperties(source, newDocument, "id", "createTime", "updateTime", "documentNumber");
        newDocument.setDocumentName(newName);
        
        // 创建新文档
        return createDocument(newDocument);
    }

    @Override
    @Transactional
    public void archiveDocument(Long id) {
        Document document = getDocument(id);
        if (document == null) {
            throw new IllegalArgumentException("文档不存在");
        }

        document.setIsArchived(true)
                .setArchiveTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        documentRepository.save(document);
    }

    @Override
    @Transactional
    public void unarchiveDocument(Long id) {
        Document document = getDocument(id);
        if (document == null) {
            throw new IllegalArgumentException("文档不存在");
        }

        document.setIsArchived(false)
                .setArchiveTime(null)
                .setUpdateTime(LocalDateTime.now());
        documentRepository.save(document);
    }

    @Override
    public boolean hasPermission(Long documentId, Long userId, String permission) {
        // TODO: 调用权限服务检查权限
        return true;
    }

    @Override
    public DocumentVO getDocumentVO(Long id) {
        Document document = getDocument(id);
        if (document == null) {
            return null;
        }

        DocumentVO vo = new DocumentVO();
        BeanUtils.copyProperties(document, vo);
        
        // 设置存储信息
        DocumentStorage storage = documentStorageRepository.findFirstByDocumentIdOrderByCreateTimeDesc(id);
        if (storage != null) {
            vo.setFileSize(storage.getFileSize())
                    .setFileType(storage.getFileType())
                    .setStorageType(storage.getStorageType());
        }
        
        return vo;
    }

    @Override
    public Page<DocumentVO> listDocumentVOs(Pageable pageable) {
        return listDocuments(pageable).map(this::convertToVO);
    }

    private DocumentVO convertToVO(Document document) {
        DocumentVO vo = new DocumentVO();
        BeanUtils.copyProperties(document, vo);
        
        // 设置存储信息
        DocumentStorage storage = documentStorageRepository.findFirstByDocumentIdOrderByCreateTimeDesc(document.getId());
        if (storage != null) {
            vo.setFileSize(storage.getFileSize())
                    .setFileType(storage.getFileType())
                    .setStorageType(storage.getStorageType());
        }
        
        return vo;
    }
} 