package com.lawfirm.document.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.lawfirm.core.storage.model.FileMetadata;
import com.lawfirm.core.storage.service.StorageService;
import com.lawfirm.document.constant.DocumentConstant;
import com.lawfirm.document.exception.DocumentException;
import com.lawfirm.document.service.DocumentStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class LocalDocumentStorageServiceImpl implements DocumentStorageService {

    private final StorageService storageService;
    
    @Value("${document.storage.root-path}")
    private String rootPath;
    
    public LocalDocumentStorageServiceImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public String store(MultipartFile file, String documentNumber, String version) throws IOException {
        validateFile(file);
        
        String fileName = buildFileName(file.getOriginalFilename(), documentNumber, version);
        String relativePath = buildRelativePath(documentNumber, fileName);
        
        // 使用底层存储服务保存文件
        FileMetadata metadata = storageService.upload(file, "document", documentNumber);
        return metadata.getPath();
    }

    @Override
    public void delete(String filePath) throws IOException {
        storageService.delete(filePath);
    }

    @Override
    public void deleteAllVersions(String documentNumber) throws IOException {
        List<FileMetadata> files = storageService.listByBusiness("document", documentNumber);
        for (FileMetadata file : files) {
            storageService.delete(file.getId());
        }
    }

    @Override
    public Path getFilePath(String documentNumber, String version) {
        String fileName = buildVersionedFileName(documentNumber, version);
        return Paths.get(rootPath, documentNumber, fileName);
    }

    @Override
    public byte[] getContent(String filePath) throws IOException {
        try (InputStream is = storageService.download(filePath)) {
            return is.readAllBytes();
        }
    }

    @Override
    public boolean exists(String filePath) {
        try {
            FileMetadata metadata = storageService.getMetadata(filePath);
            return metadata != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public long getSize(String filePath) throws IOException {
        FileMetadata metadata = storageService.getMetadata(filePath);
        return metadata != null ? metadata.getSize() : 0L;
    }

    @Override
    public String calculateHash(String filePath) throws IOException {
        return DigestUtil.sha256Hex(new File(filePath));
    }
    
    @Override
    public String uploadFile(String documentNumber, InputStream inputStream, long size, String contentType) {
        try {
            String fileName = buildFileName(documentNumber + ".tmp", documentNumber, "1.0");
            FileMetadata metadata = storageService.upload(inputStream, fileName, contentType, "document", documentNumber);
            return metadata.getPath();
        } catch (Exception e) {
            log.error("上传文件失败: documentNumber={}, size={}, contentType={}", documentNumber, size, contentType, e);
            throw new DocumentException(DocumentException.ERROR_FILE_UPLOAD_FAILED, "上传文件失败");
        }
    }
    
    @Override
    public boolean deleteFile(String filePath) {
        try {
            storageService.delete(filePath);
            return true;
        } catch (Exception e) {
            log.error("删除文件失败: filePath={}", filePath, e);
            return false;
        }
    }
    
    @Override
    public String getFileUrl(String filePath) {
        try {
            return storageService.getUrl(filePath);
        } catch (Exception e) {
            log.error("获取文件URL失败: filePath={}", filePath, e);
            throw new DocumentException(DocumentException.ERROR_FILE_URL_FAILED, "获取文件URL失败");
        }
    }
    
    private void validateFile(MultipartFile file) {
        // 检查文件大小
        if (file.getSize() > DocumentConstant.MAX_FILE_SIZE) {
            throw new DocumentException(
                DocumentException.ERROR_FILE_SIZE_EXCEEDED,
                String.format("文件大小超过限制：%d bytes", DocumentConstant.MAX_FILE_SIZE)
            );
        }
        
        // 检查文件类型
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!Arrays.asList(DocumentConstant.SUPPORTED_FILE_TYPES).contains(extension.toLowerCase())) {
            throw new DocumentException(
                DocumentException.ERROR_FILE_TYPE_NOT_SUPPORTED,
                String.format("不支持的文件类型：%s", extension)
            );
        }
        
        // 检查文件内容类型
        String contentType = file.getContentType();
        if (contentType == null || !isSafeContentType(contentType)) {
            throw new DocumentException(
                DocumentException.ERROR_FILE_TYPE_NOT_SUPPORTED,
                String.format("不安全的文件类型：%s", contentType)
            );
        }
    }
    
    private boolean isSafeContentType(String contentType) {
        // 定义安全的MIME类型列表
        return contentType.startsWith("application/") ||
               contentType.startsWith("text/") ||
               contentType.startsWith("image/");
    }
    
    private String buildFileName(String originalFilename, String documentNumber, String version) {
        String extension = FilenameUtils.getExtension(originalFilename);
        return String.format("%s_%s.%s", documentNumber, version, extension);
    }
    
    private String buildVersionedFileName(String documentNumber, String version) {
        return documentNumber + "_" + version;
    }
    
    private String buildRelativePath(String documentNumber, String fileName) {
        return documentNumber + "/" + fileName;
    }
    
    private String buildDocumentPath(String documentNumber) {
        return documentNumber;
    }
} 