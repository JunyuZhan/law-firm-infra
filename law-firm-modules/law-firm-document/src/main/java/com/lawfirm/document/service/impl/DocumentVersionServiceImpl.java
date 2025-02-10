package com.lawfirm.modules.document.service.impl;

import com.lawfirm.model.document.dto.response.DocumentVersionResponse;
import com.lawfirm.model.document.entity.DocumentStorage;
import com.lawfirm.model.document.entity.DocumentVersion;
import com.lawfirm.model.document.repository.DocumentStorageRepository;
import com.lawfirm.model.document.repository.DocumentVersionRepository;
import com.lawfirm.model.document.service.DocumentVersionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 文档版本服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentVersionServiceImpl implements DocumentVersionService {

    private final DocumentVersionRepository documentVersionRepository;
    private final DocumentStorageRepository documentStorageRepository;

    @Override
    @Transactional
    public DocumentVersion createVersion(Long documentId, MultipartFile file, String changeLog) {
        try {
            // 获取当前最新版本号
            DocumentVersion latestVersion = getLatestVersion(documentId);
            int newVersion = latestVersion != null ? latestVersion.getVersion() + 1 : 1;

            // 创建存储记录
            DocumentStorage storage = new DocumentStorage()
                    .setDocumentId(documentId)
                    .setFileSize(file.getSize())
                    .setFileType(file.getContentType())
                    .setFileName(file.getOriginalFilename())
                    .setCreateTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now())
                    .setIsDeleted(false);
            
            // TODO: 调用存储服务保存文件
            storage = documentStorageRepository.save(storage);

            // 创建版本记录
            DocumentVersion version = new DocumentVersion()
                    .setDocumentId(documentId)
                    .setVersion(newVersion)
                    .setStorageId(storage.getId())
                    .setFileSize(file.getSize())
                    .setFileName(file.getOriginalFilename())
                    .setChangeLog(changeLog)
                    .setCreateTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now())
                    .setIsDeleted(false);

            return documentVersionRepository.save(version);
        } catch (Exception e) {
            throw new RuntimeException("创建版本失败", e);
        }
    }

    @Override
    public DocumentVersion getVersion(Long versionId) {
        return documentVersionRepository.findById(versionId)
                .filter(v -> !Boolean.TRUE.equals(v.getIsDeleted()))
                .orElse(null);
    }

    @Override
    public List<DocumentVersion> getDocumentVersions(Long documentId) {
        return documentVersionRepository.findByDocumentIdOrderByVersionDesc(documentId);
    }

    @Override
    public DocumentVersion getLatestVersion(Long documentId) {
        return documentVersionRepository.findFirstByDocumentIdOrderByVersionDesc(documentId);
    }

    @Override
    @Transactional
    public DocumentVersion rollbackVersion(Long documentId, Integer version) {
        // 获取目标版本
        DocumentVersion targetVersion = documentVersionRepository.findByDocumentIdAndVersion(documentId, version);
        if (targetVersion == null) {
            throw new IllegalArgumentException("目标版本不存在");
        }

        // 获取存储记录
        DocumentStorage storage = documentStorageRepository.findById(targetVersion.getStorageId())
                .orElseThrow(() -> new IllegalStateException("版本存储记录不存在"));

        // 创建新的存储记录
        DocumentStorage newStorage = new DocumentStorage()
                .setDocumentId(documentId)
                .setFileSize(storage.getFileSize())
                .setFileType(storage.getFileType())
                .setFileName(storage.getFileName())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setIsDeleted(false);
        
        // TODO: 调用存储服务复制文件
        newStorage = documentStorageRepository.save(newStorage);

        // 创建新版本记录
        return createVersion(documentId, null, "回滚到版本 " + version);
    }

    @Override
    @Transactional
    public void deleteVersion(Long versionId) {
        DocumentVersion version = getVersion(versionId);
        if (version == null) {
            return;
        }

        // 检查是否是最新版本
        DocumentVersion latestVersion = getLatestVersion(version.getDocumentId());
        if (latestVersion != null && latestVersion.getId().equals(versionId)) {
            throw new IllegalStateException("不能删除最新版本");
        }

        // 逻辑删除版本记录
        version.setIsDeleted(true)
                .setDeleteTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        documentVersionRepository.save(version);

        // 删除存储记录
        DocumentStorage storage = documentStorageRepository.findById(version.getStorageId()).orElse(null);
        if (storage != null) {
            storage.setIsDeleted(true)
                    .setDeleteTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now());
            documentStorageRepository.save(storage);
            // TODO: 调用存储服务删除文件
        }
    }

    @Override
    public Page<DocumentVersion> listVersions(Long documentId, Pageable pageable) {
        return documentVersionRepository.findByDocumentIdOrderByVersionDesc(documentId, pageable);
    }

    @Override
    public String compareVersions(Long documentId, Integer version1, Integer version2) {
        DocumentVersion v1 = documentVersionRepository.findByDocumentIdAndVersion(documentId, version1);
        DocumentVersion v2 = documentVersionRepository.findByDocumentIdAndVersion(documentId, version2);
        if (v1 == null || v2 == null) {
            throw new IllegalArgumentException("版本不存在");
        }

        // TODO: 实现文件比较功能
        return "版本比较功能待实现";
    }

    @Override
    public byte[] getVersionFile(Long versionId) {
        DocumentVersion version = getVersion(versionId);
        if (version == null) {
            throw new IllegalArgumentException("版本不存在");
        }

        DocumentStorage storage = documentStorageRepository.findById(version.getStorageId())
                .orElseThrow(() -> new IllegalStateException("版本存储记录不存在"));

        // TODO: 调用存储服务获取文件内容
        return new byte[0];
    }

    @Override
    public String getVersionFileUrl(Long versionId) {
        DocumentVersion version = getVersion(versionId);
        if (version == null) {
            throw new IllegalArgumentException("版本不存在");
        }

        DocumentStorage storage = documentStorageRepository.findById(version.getStorageId())
                .orElseThrow(() -> new IllegalStateException("版本存储记录不存在"));

        // TODO: 调用存储服务获取文件URL
        return null;
    }

    @Override
    @Transactional
    public void cleanHistoryVersions(Long documentId, Integer keepVersions) {
        List<DocumentVersion> versionsToClean = documentVersionRepository.findVersionsToClean(documentId, keepVersions);
        for (DocumentVersion version : versionsToClean) {
            deleteVersion(version.getId());
        }
    }
} 