package com.lawfirm.core.storage.service.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.repository.FileObjectRepository;
import com.lawfirm.model.storage.enums.FileTypeEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件下载器，支持多种下载方式
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileDownloader {
    
    private final FileOperator fileOperator;
    private final FileObjectRepository fileObjectRepository;
    
    /**
     * 下载文件并返回字节数组
     * 
     * @param fileId 文件ID
     * @return 文件内容字节数组
     */
    public byte[] downloadAsBytes(Long fileId) throws IOException {
        // 获取文件输入流
        InputStream inputStream = fileOperator.downloadFile(fileId);
        if (inputStream == null) {
            throw new IOException("文件不存在或无法访问");
        }
        
        // 读取文件内容
        try (InputStream is = inputStream) {
            return readInputStream(is);
        }
    }
    
    /**
     * 下载文件并返回HTTP响应
     * 
     * @param fileId 文件ID
     * @return HTTP响应
     */
    public ResponseEntity<byte[]> downloadAsResponse(Long fileId) throws IOException {
        FileObject fileObject = fileObjectRepository.findById(fileId);
        if (fileObject == null) {
            throw new IOException("文件不存在");
        }
        
        byte[] fileContent = downloadAsBytes(fileId);
        
        // 构建HTTP响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(getMimeTypeForFileType(fileObject.getFileType()));
        
        // 设置文件名，支持中文
        String encodedFileName = URLEncoder.encode(fileObject.getFileName(), StandardCharsets.UTF_8.name())
                .replace("+", "%20");
        
        // 兼容不同浏览器的下载文件名设置
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName);
        headers.setContentLength(fileContent.length);
        
        log.info("文件下载成功: {}", fileObject.getFileName());
        return ResponseEntity.ok().headers(headers).body(fileContent);
    }
    
    /**
     * 下载文件并返回输入流
     * 
     * @param fileId 文件ID
     * @return 文件输入流
     */
    public InputStream downloadAsStream(Long fileId) {
        return fileOperator.downloadFile(fileId);
    }
    
    /**
     * 获取文件访问URL
     * 
     * @param fileId 文件ID
     * @param expireSeconds 过期时间(秒)
     * @return 访问URL
     */
    public String getFileUrl(Long fileId, int expireSeconds) {
        return fileOperator.getFileUrl(fileId, expireSeconds);
    }
    
    /**
     * 读取输入流内容为字节数组
     */
    private byte[] readInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        
        while ((bytesRead = is.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        
        return baos.toByteArray();
    }
    
    /**
     * 根据文件类型获取对应的MIME类型
     * 
     * @param fileType 文件类型枚举
     * @return MIME类型
     */
    private MediaType getMimeTypeForFileType(FileTypeEnum fileType) {
        if (fileType == null) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
        
        return switch (fileType) {
            case IMAGE -> MediaType.IMAGE_JPEG;
            case DOCUMENT -> MediaType.APPLICATION_PDF;
            case VIDEO -> MediaType.parseMediaType("video/mp4");
            case AUDIO -> MediaType.parseMediaType("audio/mpeg");
            case ARCHIVE -> MediaType.parseMediaType("application/zip");
            case OTHER -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }
} 