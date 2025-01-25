package com.lawfirm.document.service.impl;

import com.lawfirm.common.storage.LocalStorageService;
import com.lawfirm.common.util.HashUtil;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Slf4j
@Service
public class LocalDocumentStorageServiceImpl implements DocumentStorageService {

    private final LocalStorageService storageService;
    
    @Value("${document.storage.root-path}")
    private String rootPath;
    
    public LocalDocumentStorageServiceImpl(LocalStorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public String store(MultipartFile file, String documentNumber, String version) throws IOException {
        validateFile(file);
        
        String fileName = buildFileName(file.getOriginalFilename(), documentNumber, version);
        String relativePath = buildRelativePath(documentNumber, fileName);
        
        // 使用底层存储服务保存文件
        return storageService.store(file.getInputStream(), relativePath);
    }

    @Override
    public void delete(String filePath) throws IOException {
        storageService.delete(filePath);
    }

    @Override
    public void deleteAllVersions(String documentNumber) throws IOException {
        String dirPath = buildDocumentPath(documentNumber);
        storageService.deleteDirectory(dirPath);
    }

    @Override
    public Path getFilePath(String documentNumber, String version) {
        String fileName = buildVersionedFileName(documentNumber, version);
        return Paths.get(rootPath, documentNumber, fileName);
    }

    @Override
    public byte[] getContent(String filePath) throws IOException {
        return storageService.getContent(filePath);
    }

    @Override
    public boolean exists(String filePath) {
        return storageService.exists(filePath);
    }

    @Override
    public long getSize(String filePath) throws IOException {
        return storageService.getSize(filePath);
    }

    @Override
    public String calculateHash(String filePath) throws IOException {
        return HashUtil.calculateSHA256(new File(filePath));
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
        return documentNumber + DocumentConstant.Storage.PATH_SEPARATOR + fileName;
    }
    
    private String buildDocumentPath(String documentNumber) {
        return documentNumber;
    }
} 