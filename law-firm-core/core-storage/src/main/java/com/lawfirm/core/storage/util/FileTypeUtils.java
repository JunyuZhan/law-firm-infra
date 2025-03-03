package com.lawfirm.core.storage.util;

import org.apache.tika.Tika;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件类型工具类
 */
public class FileTypeUtils {

    private static final Tika tika = new Tika();
    
    /**
     * 文件类型映射
     */
    private static final Map<String, String> MIME_TYPE_MAP = new HashMap<>();
    
    static {
        // 图片类型
        MIME_TYPE_MAP.put("image/jpeg", "jpg");
        MIME_TYPE_MAP.put("image/png", "png");
        MIME_TYPE_MAP.put("image/gif", "gif");
        MIME_TYPE_MAP.put("image/bmp", "bmp");
        MIME_TYPE_MAP.put("image/webp", "webp");
        MIME_TYPE_MAP.put("image/svg+xml", "svg");
        
        // 文档类型
        MIME_TYPE_MAP.put("application/pdf", "pdf");
        MIME_TYPE_MAP.put("application/msword", "doc");
        MIME_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
        MIME_TYPE_MAP.put("application/vnd.ms-excel", "xls");
        MIME_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
        MIME_TYPE_MAP.put("application/vnd.ms-powerpoint", "ppt");
        MIME_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");
        
        // 压缩文件
        MIME_TYPE_MAP.put("application/zip", "zip");
        MIME_TYPE_MAP.put("application/x-rar-compressed", "rar");
        MIME_TYPE_MAP.put("application/x-7z-compressed", "7z");
        MIME_TYPE_MAP.put("application/x-tar", "tar");
        MIME_TYPE_MAP.put("application/gzip", "gz");
        
        // 音频文件
        MIME_TYPE_MAP.put("audio/mpeg", "mp3");
        MIME_TYPE_MAP.put("audio/wav", "wav");
        MIME_TYPE_MAP.put("audio/ogg", "ogg");
        MIME_TYPE_MAP.put("audio/midi", "midi");
        
        // 视频文件
        MIME_TYPE_MAP.put("video/mp4", "mp4");
        MIME_TYPE_MAP.put("video/x-msvideo", "avi");
        MIME_TYPE_MAP.put("video/quicktime", "mov");
        MIME_TYPE_MAP.put("video/x-matroska", "mkv");
        MIME_TYPE_MAP.put("video/webm", "webm");
    }
    
    /**
     * 检测文件类型
     *
     * @param file 文件对象
     * @return MIME类型
     */
    public static String detectMimeType(File file) {
        try {
            return tika.detect(file);
        } catch (Exception e) {
            return "application/octet-stream";
        }
    }
    
    /**
     * 检测文件类型
     *
     * @param data 文件数据
     * @return MIME类型
     */
    public static String detectMimeType(byte[] data) {
        try {
            return tika.detect(data);
        } catch (Exception e) {
            return "application/octet-stream";
        }
    }
    
    /**
     * 检测文件类型
     *
     * @param inputStream 文件输入流
     * @return MIME类型
     */
    public static String detectMimeType(InputStream inputStream) {
        try {
            return tika.detect(inputStream);
        } catch (Exception e) {
            return "application/octet-stream";
        }
    }
    
    /**
     * 根据文件名获取扩展名
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getExtension(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return "";
        }
        
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > -1 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
    
    /**
     * 根据MIME类型获取扩展名
     *
     * @param mimeType MIME类型
     * @return 扩展名
     */
    public static String getExtensionByMimeType(String mimeType) {
        if (!StringUtils.hasText(mimeType)) {
            return "";
        }
        
        return MIME_TYPE_MAP.getOrDefault(mimeType.toLowerCase(), "");
    }
    
    /**
     * 判断是否为图片文件
     *
     * @param mimeType MIME类型
     * @return 是否为图片
     */
    public static boolean isImage(String mimeType) {
        if (!StringUtils.hasText(mimeType)) {
            return false;
        }
        
        return mimeType.startsWith("image/");
    }
    
    /**
     * 判断是否为视频文件
     *
     * @param mimeType MIME类型
     * @return 是否为视频
     */
    public static boolean isVideo(String mimeType) {
        if (!StringUtils.hasText(mimeType)) {
            return false;
        }
        
        return mimeType.startsWith("video/");
    }
    
    /**
     * 判断是否为音频文件
     *
     * @param mimeType MIME类型
     * @return 是否为音频
     */
    public static boolean isAudio(String mimeType) {
        if (!StringUtils.hasText(mimeType)) {
            return false;
        }
        
        return mimeType.startsWith("audio/");
    }
    
    /**
     * 判断是否为文档文件
     *
     * @param mimeType MIME类型
     * @return 是否为文档
     */
    public static boolean isDocument(String mimeType) {
        if (!StringUtils.hasText(mimeType)) {
            return false;
        }
        
        return mimeType.startsWith("application/") && 
                (mimeType.contains("pdf") || 
                 mimeType.contains("word") || 
                 mimeType.contains("excel") || 
                 mimeType.contains("powerpoint") || 
                 mimeType.contains("text"));
    }
} 