package com.lawfirm.archive.controller;

import com.lawfirm.archive.service.FileService;
import com.lawfirm.common.core.model.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件管理控制器
 */
@Tag(name = "文件管理")
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public Result<String> uploadFile(
            @Parameter(description = "文件") @RequestParam("file") MultipartFile file) {
        String filePath = fileService.uploadFile(file);
        return Result.ok(filePath);
    }

    @GetMapping("/download/{filePath}")
    @Operation(summary = "下载文件")
    public ResponseEntity<InputStreamResource> downloadFile(
            @Parameter(description = "文件路径") @PathVariable String filePath) {
        InputStream inputStream = fileService.downloadFile(filePath);
        InputStreamResource resource = new InputStreamResource(inputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @DeleteMapping("/{filePath}")
    @Operation(summary = "删除文件")
    public Result<Void> deleteFile(
            @Parameter(description = "文件路径") @PathVariable String filePath) {
        fileService.deleteFile(filePath);
        return Result.ok();
    }
} 