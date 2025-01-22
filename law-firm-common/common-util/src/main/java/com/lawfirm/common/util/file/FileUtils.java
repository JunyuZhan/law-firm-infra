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

@Slf4j
public class FileUtils {

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
} 