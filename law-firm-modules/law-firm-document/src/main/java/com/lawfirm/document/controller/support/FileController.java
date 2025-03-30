package com.lawfirm.document.controller.support;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.document.manager.storage.StorageManager;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.entity.file.FileObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文档文件处理控制器
 */
@Slf4j
@Tag(name = "文件操作", description = "文件上传下载接口")
@RestController
@RequestMapping("/document/files")
@RequiredArgsConstructor
public class FileController {

    private final DocumentService documentService;
    private final BucketService bucketService;
    private final StorageManager storageManager;

    /**
     * 上传文档文件
     */
    @PostMapping("/upload")
    @Operation(
        summary = "上传文档文件",
        description = "上传文档文件到指定的存储桶，支持多种文件格式，返回文件对象信息，包含文件ID、URL等"
    )
    public CommonResult<FileObject> uploadFile(
            @Parameter(description = "文件") @RequestPart("file") MultipartFile file,
            @Parameter(description = "存储桶ID") @RequestParam(required = false, defaultValue = "1") Long bucketId) throws IOException {
        
        // 获取存储桶
        StorageBucket bucket = bucketService.getById(bucketId);
        if (bucket == null) {
            return CommonResult.error("存储桶不存在");
        }
        
        // 上传文件
        FileObject fileObject = storageManager.uploadDocument(file, bucket);
        return CommonResult.success(fileObject);
    }

    /**
     * 下载文档
     */
    @GetMapping("/download/{id}")
    @Operation(
        summary = "下载文档",
        description = "根据文档ID下载文件，支持所有文件格式，返回文件流。自动设置文件名和相关响应头"
    )
    public ResponseEntity<InputStreamResource> downloadFile(
            @Parameter(description = "文档ID") @PathVariable Long id) throws IOException {
        // 获取文档输入流
        InputStream inputStream = documentService.downloadDocument(id);
        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 获取文档详情
        String fileName = documentService.getDocumentById(id).getFileName();
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
        
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName);
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
        
        // 返回文件流
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }

    /**
     * 获取文档访问URL
     */
    @GetMapping("/url/{id}")
    @Operation(
        summary = "获取文档访问URL",
        description = "获取文档的访问URL，可用于在线预览或下载。URL的有效期和访问权限由存储系统控制"
    )
    public CommonResult<String> getDocumentUrl(
            @Parameter(description = "文档ID") @PathVariable Long id) {
        String url = documentService.getDocumentUrl(id);
        return CommonResult.success(url);
    }
}
