package com.lawfirm.core.storage.controller;

import com.lawfirm.core.storage.model.FileMetadata;
import com.lawfirm.core.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 存储控制器
 */
@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {
    
    private final StorageService storageService;
    
    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public FileMetadata upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("businessType") String businessType,
            @RequestParam("businessId") String businessId) {
        return storageService.upload(file, businessType, businessId);
    }
    
    /**
     * 删除文件
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        storageService.delete(id);
    }
    
    /**
     * 批量删除文件
     */
    @DeleteMapping("/batch")
    public void deleteBatch(@RequestBody List<String> ids) {
        storageService.deleteBatch(ids);
    }
    
    /**
     * 获取文件元数据
     */
    @GetMapping("/{id}/metadata")
    public FileMetadata getMetadata(@PathVariable String id) {
        return storageService.getMetadata(id);
    }
    
    /**
     * 获取文件访问URL
     */
    @GetMapping("/{id}/url")
    public String getUrl(
            @PathVariable String id,
            @RequestParam(required = false) Long expireSeconds) {
        return expireSeconds != null ? 
                storageService.getUrl(id, expireSeconds) : 
                storageService.getUrl(id);
    }
    
    /**
     * 下载文件
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> download(@PathVariable String id) {
        FileMetadata metadata = storageService.getMetadata(id);
        if (metadata == null) {
            return ResponseEntity.notFound().build();
        }
        
        InputStream inputStream = storageService.download(id);
        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }
        
        String encodedFilename = URLEncoder.encode(metadata.getFilename(), StandardCharsets.UTF_8);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(metadata.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                .body(new InputStreamResource(inputStream));
    }
    
    /**
     * 根据业务类型和业务ID获取文件列表
     */
    @GetMapping("/list")
    public List<FileMetadata> listByBusiness(
            @RequestParam String businessType,
            @RequestParam String businessId) {
        return storageService.listByBusiness(businessType, businessId);
    }
} 