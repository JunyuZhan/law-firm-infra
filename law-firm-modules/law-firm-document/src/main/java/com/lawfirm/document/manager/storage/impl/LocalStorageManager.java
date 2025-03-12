package com.lawfirm.document.manager.storage.impl;

import com.lawfirm.document.config.properties.DocumentProperties;
import com.lawfirm.document.manager.storage.DocumentStorageManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 本地存储管理器实现
 */
@Slf4j
public class LocalStorageManager implements DocumentStorageManager {

    private final DocumentProperties properties;
    private final String basePath;

    public LocalStorageManager(DocumentProperties properties) {
        this.properties = properties;
        this.basePath = properties.getStorage().getLocalBasePath();
        initStorageDirectory();
    }

    @Override
    public String uploadDocument(MultipartFile file, String path) throws IOException {
        // 1. 生成存储路径
        String storagePath = generateStoragePath(file.getOriginalFilename());
        File targetFile = new File(basePath + File.separator + storagePath);

        // 2. 确保目录存在
        FileUtils.forceMkdirParent(targetFile);

        // 3. 保存文件
        file.transferTo(targetFile);

        // 4. 如果启用了文件去重，进行文件指纹检查
        if (properties.getStorage().isEnableDeduplication()) {
            String fileHash = calculateFileHash(targetFile);
            String existingPath = findDuplicateFile(fileHash);
            if (existingPath != null) {
                // 删除新上传的文件，返回已存在的文件路径
                FileUtils.deleteQuietly(targetFile);
                return existingPath;
            }
        }

        return storagePath;
    }

    @Override
    public void deleteDocument(String path) {
        File file = new File(basePath + File.separator + path);
        FileUtils.deleteQuietly(file);
    }

    @Override
    public InputStream getDocument(String path) throws IOException {
        File file = new File(basePath + File.separator + path);
        return new FileInputStream(file);
    }

    @Override
    public String getDocumentUrl(String path) {
        // 本地存储返回相对路径
        return "/documents/" + path;
    }

    @Override
    public String getDocumentUrl(String path, Long expireTime) {
        // 本地存储不支持临时URL，直接返回普通URL
        return getDocumentUrl(path);
    }

    @Override
    public boolean exists(String path) {
        File file = new File(basePath + File.separator + path);
        return file.exists() && file.isFile();
    }

    @Override
    public void copyDocument(String sourcePath, String targetPath) throws IOException {
        File sourceFile = new File(basePath + File.separator + sourcePath);
        File targetFile = new File(basePath + File.separator + targetPath);
        FileUtils.copyFile(sourceFile, targetFile);
    }

    @Override
    public void moveDocument(String sourcePath, String targetPath) throws IOException {
        File sourceFile = new File(basePath + File.separator + sourcePath);
        File targetFile = new File(basePath + File.separator + targetPath);
        FileUtils.moveFile(sourceFile, targetFile);
    }

    @Override
    public long getSize(String path) {
        File file = new File(basePath + File.separator + path);
        return file.length();
    }

    @Override
    public long getLastModified(String path) {
        File file = new File(basePath + File.separator + path);
        return file.lastModified();
    }

    /**
     * 初始化存储目录
     */
    private void initStorageDirectory() {
        try {
            Path path = Paths.get(basePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            log.error("初始化存储目录失败", e);
            throw new RuntimeException("初始化存储目录失败", e);
        }
    }

    /**
     * 生成存储路径
     */
    private String generateStoragePath(String originalFilename) {
        // 生成日期路径：yyyy/MM/dd
        String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        
        // 生成文件名：时间戳 + 随机数 + 扩展名
        String extension = FilenameUtils.getExtension(originalFilename);
        String filename = System.currentTimeMillis() + "_" + ((int) (Math.random() * 10000)) 
            + "." + extension;
        
        return datePath + File.separator + filename;
    }

    /**
     * 计算文件哈希值
     */
    private String calculateFileHash(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return DigestUtils.md5DigestAsHex(fis);
        }
    }

    /**
     * 查找重复文件
     */
    private String findDuplicateFile(String fileHash) {
        // TODO: 实现文件去重查找逻辑
        return null;
    }
} 