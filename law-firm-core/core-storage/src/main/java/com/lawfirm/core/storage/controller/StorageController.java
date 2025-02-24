package com.lawfirm.core.storage.controller;

import com.lawfirm.model.base.storage.model.FileMetadata;
import com.lawfirm.model.base.storage.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件存储控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/storage")
@Tag(name = "文件存储", description = "文件存储相关接口")
public class StorageController {

    @Autowired
    private StorageService storageService;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public FileMetadata upload(@RequestParam("file") MultipartFile file) {
        return storageService.uploadFile(file);
    }

    /**
     * 下载文件
     */
    @GetMapping("/download/{path}")
    @Operation(summary = "下载文件")
    public ResponseEntity<InputStreamResource> download(@PathVariable String path) {
        byte[] fileData = storageService.downloadFile(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(path, StandardCharsets.UTF_8));
        headers.setContentLength(fileData.length);
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(new ByteArrayInputStream(fileData)));
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/{path}")
    @Operation(summary = "删除文件")
    public void delete(@PathVariable String path) {
        storageService.deleteFile(path);
    }

    /**
     * 获取文件访问URL
     */
    @GetMapping("/url/{path}")
    @Operation(summary = "获取文件URL")
    public String getUrl(@PathVariable String path) {
        return storageService.getFileUrl(path);
    }

    /**
     * 检查文件是否存在
     */
    @GetMapping("/exist/{path}")
    @Operation(summary = "检查文件是否存在")
    public boolean exists(@PathVariable String path) {
        return storageService.isFileExist(path);
    }
} 