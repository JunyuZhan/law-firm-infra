package com.lawfirm.knowledge.utils;

import com.lawfirm.common.util.file.FileUtils;
import com.lawfirm.common.util.date.DateUtils;
import com.lawfirm.knowledge.config.KnowledgeConfig;
import com.lawfirm.model.knowledge.entity.Knowledge;
import com.lawfirm.model.knowledge.entity.KnowledgeAttachment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 知识模块专用工具类
 * 仅包含特定于知识模块的工具方法，通用功能应使用common层的工具类
 */
@Slf4j
@Component("knowledgeUtils")
public class KnowledgeUtils {

    @Autowired
    @Qualifier("knowledgeConfig")
    private KnowledgeConfig knowledgeConfig;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    
    /**
     * 验证文件扩展名是否在允许列表中
     * 
     * @param fileName 文件名
     * @param allowedExtensions 允许的扩展名列表，逗号分隔
     * @return 是否允许
     */
    public boolean isExtensionAllowed(String fileName, String allowedExtensions) {
        String[] extensions = allowedExtensions.split(",");
        String extension = FileUtils.getExtension(fileName);
        
        for (String allowed : extensions) {
            if (allowed.startsWith(".")) {
                allowed = allowed.substring(1);
            }
            if (allowed.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 生成知识文档存储路径
     * 特定于知识模块的路径生成规则
     * 
     * @param knowledge 知识文档
     * @return 存储路径
     */
    public String generateKnowledgePath(Knowledge knowledge) {
        StringBuilder pathBuilder = new StringBuilder();
        
        // 基础路径
        pathBuilder.append(knowledgeConfig.getStorage().getBasePath());
        pathBuilder.append(File.separator);
        
        // 年月日子目录
        LocalDateTime createTime = knowledge.getCreateTime();
        pathBuilder.append(createTime.format(DATE_FORMATTER));
        pathBuilder.append(File.separator);
        
        // 分类子目录
        pathBuilder.append("category_").append(knowledge.getCategoryId());
        pathBuilder.append(File.separator);
        
        // 确保目录存在
        String dirPath = pathBuilder.toString();
        FileUtils.createDirectory(dirPath);
        
        return dirPath;
    }
    
    /**
     * 生成附件存储文件名
     * 
     * @param originalFilename 原始文件名
     * @return 存储文件名
     */
    public String generateAttachmentFilename(String originalFilename) {
        String extension = FileUtils.getExtension(originalFilename);
        String timestamp = LocalDateTime.now().format(DATETIME_FORMATTER);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        
        return timestamp + "_" + uuid + extension;
    }
    
    /**
     * 解析配置的文件大小限制
     * 
     * @param sizeLimit 大小限制字符串，如 "10MB"
     * @return 字节数
     */
    public long parseFileSizeLimit(String sizeLimit) {
        if (sizeLimit == null || sizeLimit.isEmpty()) {
            return 0;
        }
        
        sizeLimit = sizeLimit.toUpperCase();
        long multiplier = 1;
        
        if (sizeLimit.endsWith("KB")) {
            multiplier = 1024;
            sizeLimit = sizeLimit.substring(0, sizeLimit.length() - 2);
        } else if (sizeLimit.endsWith("MB")) {
            multiplier = 1024 * 1024;
            sizeLimit = sizeLimit.substring(0, sizeLimit.length() - 2);
        } else if (sizeLimit.endsWith("GB")) {
            multiplier = 1024 * 1024 * 1024;
            sizeLimit = sizeLimit.substring(0, sizeLimit.length() - 2);
        }
        
        try {
            return Long.parseLong(sizeLimit.trim()) * multiplier;
        } catch (NumberFormatException e) {
            log.error("解析文件大小限制失败: {}", sizeLimit, e);
            return 0;
        }
    }
} 