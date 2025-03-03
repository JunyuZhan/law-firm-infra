package com.lawfirm.core.storage.util;

import org.springframework.util.StringUtils;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 文件路径工具类
 */
public class FilePathUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    
    /**
     * 生成文件路径
     * 
     * @param originalFilename 原始文件名
     * @return 存储路径
     */
    public static String generatePath(String originalFilename) {
        // 按日期生成目录
        String datePath = LocalDate.now().format(DATE_FORMATTER);
        
        // 生成唯一文件名
        String uniqueFileName = generateUniqueFileName(originalFilename);
        
        return datePath + "/" + uniqueFileName;
    }
    
    /**
     * 生成唯一文件名
     * 
     * @param originalFilename 原始文件名
     * @return 唯一文件名
     */
    public static String generateUniqueFileName(String originalFilename) {
        // 生成UUID
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        
        // 获取文件扩展名
        String extension = getExtension(originalFilename);
        
        // 拼接唯一文件名
        if (StringUtils.hasText(extension)) {
            return uuid + "." + extension;
        } else {
            return uuid;
        }
    }
    
    /**
     * 获取文件扩展名
     * 
     * @param filename 文件名
     * @return 扩展名
     */
    public static String getExtension(String filename) {
        if (!StringUtils.hasText(filename)) {
            return "";
        }
        
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
            return filename.substring(lastDotIndex + 1).toLowerCase();
        }
        
        return "";
    }
    
    /**
     * 规范化路径
     * 
     * @param path 路径
     * @return 规范化后的路径
     */
    public static String normalizePath(String path) {
        if (!StringUtils.hasText(path)) {
            return "";
        }
        
        // 将反斜杠替换为正斜杠
        String normalizedPath = path.replace('\\', '/');
        
        // 去除多余的斜杠
        while (normalizedPath.contains("//")) {
            normalizedPath = normalizedPath.replace("//", "/");
        }
        
        // 去除开头的斜杠
        if (normalizedPath.startsWith("/")) {
            normalizedPath = normalizedPath.substring(1);
        }
        
        // 去除结尾的斜杠
        if (normalizedPath.endsWith("/")) {
            normalizedPath = normalizedPath.substring(0, normalizedPath.length() - 1);
        }
        
        return normalizedPath;
    }
    
    /**
     * 连接路径
     * 
     * @param basePath 基础路径
     * @param relativePath 相对路径
     * @return 连接后的路径
     */
    public static String joinPath(String basePath, String relativePath) {
        if (!StringUtils.hasText(basePath)) {
            return normalizePath(relativePath);
        }
        
        if (!StringUtils.hasText(relativePath)) {
            return normalizePath(basePath);
        }
        
        String normalizedBasePath = normalizePath(basePath);
        String normalizedRelativePath = normalizePath(relativePath);
        
        return normalizedBasePath + "/" + normalizedRelativePath;
    }
    
    /**
     * 创建目录
     * 
     * @param dirPath 目录路径
     * @return 是否创建成功
     */
    public static boolean createDirectory(String dirPath) {
        if (!StringUtils.hasText(dirPath)) {
            return false;
        }
        
        File dir = new File(dirPath);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        
        return true;
    }
    
    /**
     * 获取父目录路径
     * 
     * @param path 路径
     * @return 父目录路径
     */
    public static String getParentPath(String path) {
        if (!StringUtils.hasText(path)) {
            return "";
        }
        
        String normalizedPath = normalizePath(path);
        int lastSlashIndex = normalizedPath.lastIndexOf('/');
        
        if (lastSlashIndex > 0) {
            return normalizedPath.substring(0, lastSlashIndex);
        }
        
        return "";
    }
    
    /**
     * 获取文件名
     * 
     * @param path 路径
     * @return 文件名
     */
    public static String getFileName(String path) {
        if (!StringUtils.hasText(path)) {
            return "";
        }
        
        String normalizedPath = normalizePath(path);
        int lastSlashIndex = normalizedPath.lastIndexOf('/');
        
        if (lastSlashIndex >= 0 && lastSlashIndex < normalizedPath.length() - 1) {
            return normalizedPath.substring(lastSlashIndex + 1);
        }
        
        return normalizedPath;
    }
} 