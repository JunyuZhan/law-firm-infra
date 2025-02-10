package com.lawfirm.document.service.impl;

import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.document.service.IDocumentStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 本地文档存储服务实现
 */
@Slf4j
@Service
public class LocalDocumentStorageServiceImpl implements IDocumentStorageService {

    @Value("${document.storage.base-path}")
    private String baseStoragePath;

    @Override
    public String uploadFile(MultipartFile file, String path) {
        try {
            // 生成存储路径
            String storagePath = generateStoragePath(path, FilenameUtils.getExtension(file.getOriginalFilename()));
            
            // 确保目录存在
            Path dirPath = Paths.get(baseStoragePath, FilenameUtils.getPath(storagePath));
            Files.createDirectories(dirPath);
            
            // 保存文件
            File targetFile = new File(baseStoragePath, storagePath);
            FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
            
            return storagePath;
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException("文件上传失败");
        }
    }

    @Override
    public InputStream downloadFile(String path) {
        try {
            File file = new File(baseStoragePath, path);
            if (!file.exists()) {
                throw new BusinessException("文件不存在");
            }
            return new FileInputStream(file);
        } catch (IOException e) {
            log.error("文件下载失败", e);
            throw new BusinessException("文件下载失败");
        }
    }

    @Override
    public void deleteFile(String path) {
        File file = new File(baseStoragePath, path);
        if (file.exists() && !file.delete()) {
            log.error("文件删除失败: {}", path);
            throw new BusinessException("文件删除失败");
        }
    }

    @Override
    public String convertFormat(String sourcePath, String targetFormat) {
        // TODO: 实现文件格式转换，可以使用Apache POI、iText等工具
        throw new BusinessException("暂不支持文件格式转换");
    }

    /**
     * 生成存储路径
     */
    private String generateStoragePath(String path, String extension) {
        // 生成日期目录
        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        
        // 生成唯一文件名
        String fileName = UUID.randomUUID().toString() + "." + extension;
        
        return path + "/" + dateDir + "/" + fileName;
    }
} 