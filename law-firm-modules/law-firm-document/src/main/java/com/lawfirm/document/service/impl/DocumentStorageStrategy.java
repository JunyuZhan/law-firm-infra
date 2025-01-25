package com.lawfirm.document.service.impl;

import com.lawfirm.document.constant.DocumentConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 文档存储策略
 * 负责生成文件存储路径和文件名
 */
@Slf4j
@Component
public class DocumentStorageStrategy {

    @Value("${document.storage.root-path}")
    private String rootPath;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");

    /**
     * 生成文件存储路径
     * 使用日期目录结构：/年/月/日/文档编号/版本/
     */
    public Path generateStoragePath(String documentNumber, String version) {
        LocalDateTime now = LocalDateTime.now();
        String datePath = now.format(DATE_FORMATTER);
        String timePath = now.format(TIME_FORMATTER);
        
        return Paths.get(rootPath)
            .resolve(datePath)
            .resolve(documentNumber)
            .resolve(version);
    }

    /**
     * 生成文件名
     * 格式：文档编号_版本号_时间戳.扩展名
     */
    public String generateFileName(String documentNumber, String version, String originalFilename) {
        String timestamp = LocalDateTime.now().format(TIME_FORMATTER);
        String extension = getFileExtension(originalFilename);
        
        return String.format("%s_%s_%s.%s",
            documentNumber,
            version,
            timestamp,
            extension
        );
    }

    /**
     * 生成缩略图路径
     */
    public Path generateThumbnailPath(Path originalPath) {
        String fileName = originalPath.getFileName().toString();
        String thumbnailName = addSuffix(fileName, DocumentConstant.Storage.THUMBNAIL_SUFFIX);
        return originalPath.resolveSibling(thumbnailName);
    }

    /**
     * 生成预览文件路径
     */
    public Path generatePreviewPath(Path originalPath) {
        String fileName = originalPath.getFileName().toString();
        String previewName = addSuffix(fileName, "_preview");
        return originalPath.resolveSibling(previewName);
    }

    /**
     * 获取文档的所有版本路径
     */
    public Path getVersionsPath(String documentNumber) {
        return Paths.get(rootPath)
            .resolve(documentNumber);
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }

    private String addSuffix(String fileName, String suffix) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return fileName + suffix;
        }
        return fileName.substring(0, dotIndex) + suffix + fileName.substring(dotIndex);
    }
} 