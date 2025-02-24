package com.lawfirm.common.util.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.io.FileTypeUtil;
import lombok.extern.slf4j.Slf4j;

import com.lawfirm.common.util.BaseUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 文件工具类
 */
@Slf4j
public class FileUtils extends BaseUtils {

    private static final String[] ALLOWED_IMAGE_TYPES = {"jpg", "jpeg", "png", "gif"};
    private static final String[] ALLOWED_DOC_TYPES = {"doc", "docx", "xls", "xlsx", "pdf"};
    
    public static String generateUniqueFileName(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        return UUID.randomUUID().toString() + "." + extension;
    }
    
    public static boolean isValidImageFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.stream(ALLOWED_IMAGE_TYPES)
                .anyMatch(type -> type.equalsIgnoreCase(extension));
    }
    
    public static boolean isValidDocFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.stream(ALLOWED_DOC_TYPES)
                .anyMatch(type -> type.equalsIgnoreCase(extension));
    }
    
    public static void createDirectoryIfNotExists(String directoryPath) throws IOException {
        Files.createDirectories(Paths.get(directoryPath));
    }
    
    public static String getFileType(Path path) {
        try {
            return FileTypeUtil.getType(path.toFile());
        } catch (Exception e) {
            log.error("Failed to get file type", e);
            return null;
        }
    }
    
    public static String getFileType(File file) {
        return getFileType(file.toPath());
    }
    
    public static long getFileSizeInMB(Path path) {
        try {
            return Files.size(path) / (1024 * 1024);
        } catch (IOException e) {
            log.error("Failed to get file size", e);
            return -1;
        }
    }
    
    public static long getFileSizeInMB(File file) {
        return getFileSizeInMB(file.toPath());
    }
    
    public static void copyFile(Path source, Path target) throws IOException {
        Files.copy(source, target);
    }
    
    public static void moveFile(Path source, Path target) throws IOException {
        Files.move(source, target);
    }
    
    public static void deleteFile(Path path) throws IOException {
        Files.deleteIfExists(path);
    }
    
    public static boolean exists(Path path) {
        return Files.exists(path);
    }
    
    public static boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }
    
    public static boolean isRegularFile(Path path) {
        return Files.isRegularFile(path);
    }

    /**
     * 读取文件内容
     */
    public static String readFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("读取文件失败: {}", filePath, e);
            return null;
        }
    }

    /**
     * 写入文件内容
     */
    public static boolean writeFile(String filePath, String content) {
        try {
            Files.writeString(Paths.get(filePath), content, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            log.error("写入文件失败: {}", filePath, e);
            return false;
        }
    }

    /**
     * 复制文件
     */
    public static boolean copyFile(String sourcePath, String targetPath) {
        try {
            Files.copy(Paths.get(sourcePath), Paths.get(targetPath));
            return true;
        } catch (IOException e) {
            log.error("复制文件失败: {} -> {}", sourcePath, targetPath, e);
            return false;
        }
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String filePath) {
        try {
            return Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            log.error("删除文件失败: {}", filePath, e);
            return false;
        }
    }

    /**
     * 创建目录
     */
    public static boolean createDirectory(String dirPath) {
        try {
            Files.createDirectories(Paths.get(dirPath));
            return true;
        } catch (IOException e) {
            log.error("创建目录失败: {}", dirPath, e);
            return false;
        }
    }

    /**
     * 获取文件扩展名
     */
    public static String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    /**
     * 压缩文件
     */
    public static boolean zipFiles(List<String> srcFiles, String zipFile) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (String srcFile : srcFiles) {
                File file = new File(srcFile);
                if (!file.exists()) continue;

                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zos.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zos.write(bytes, 0, length);
                    }
                }
            }
            return true;
        } catch (IOException e) {
            log.error("压缩文件失败: {}", zipFile, e);
            return false;
        }
    }
} 