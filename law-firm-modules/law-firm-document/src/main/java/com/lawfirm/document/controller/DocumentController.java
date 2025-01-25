package com.lawfirm.document.controller;

import com.lawfirm.common.data.Result;
import com.lawfirm.document.service.DocumentService;
import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.document.query.DocumentQuery;
import com.lawfirm.model.document.vo.DocumentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "文档管理", description = "提供文档上传、下载、版本管理等功能")
@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
@Validated
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "上传文档")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<DocumentVO> upload(
            @Parameter(description = "文档文件") @RequestPart("file") @NotNull MultipartFile file,
            @Parameter(description = "文档信息") @RequestPart("document") @Valid Document document,
            @Parameter(description = "操作人") @RequestParam @NotBlank String operator) {
        return Result.success(documentService.upload(file, document, operator));
    }

    @Operation(summary = "批量上传文档")
    @PostMapping(value = "/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<List<DocumentVO>> batchUpload(
            @Parameter(description = "文档文件列表") @RequestPart("files") @NotEmpty List<MultipartFile> files,
            @Parameter(description = "文档信息列表") @RequestPart("documents") @NotEmpty List<Document> documents,
            @Parameter(description = "操作人") @RequestParam @NotBlank String operator) {
        return Result.success(documentService.batchUpload(files, documents, operator));
    }

    @Operation(summary = "更新文档")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<DocumentVO> update(
            @Parameter(description = "文档ID") @PathVariable @NotNull Long id,
            @Parameter(description = "文档文件") @RequestPart(value = "file", required = false) MultipartFile file,
            @Parameter(description = "文档信息") @RequestPart("document") @Valid Document document,
            @Parameter(description = "操作人") @RequestParam @NotBlank String operator) {
        return Result.success(documentService.update(id, file, document, operator));
    }

    @Operation(summary = "下载文档")
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(
            @Parameter(description = "文档ID") @PathVariable @NotNull Long id,
            @Parameter(description = "版本号") @RequestParam(required = false) String version) {
        Document document = documentService.getById(id);
        byte[] content = documentService.download(id, version);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                    "attachment; filename=\"" + document.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(content);
    }

    @Operation(summary = "删除文档")
    @DeleteMapping("/{id}")
    public Result<Void> delete(
            @Parameter(description = "文档ID") @PathVariable @NotNull Long id,
            @Parameter(description = "操作人") @RequestParam @NotBlank String operator) {
        documentService.delete(id, operator);
        return Result.success();
    }

    @Operation(summary = "审核文档")
    @PostMapping("/{id}/audit")
    public Result<DocumentVO> audit(
            @Parameter(description = "文档ID") @PathVariable @NotNull Long id,
            @Parameter(description = "审核状态") @RequestParam @NotNull Integer status,
            @Parameter(description = "审核意见") @RequestParam(required = false) String comment,
            @Parameter(description = "操作人") @RequestParam @NotBlank String operator) {
        return Result.success(documentService.audit(id, status, comment, operator));
    }

    @Operation(summary = "获取文档版本历史")
    @GetMapping("/{id}/versions")
    public Result<List<DocumentVO>> getVersionHistory(
            @Parameter(description = "文档ID") @PathVariable @NotNull Long id) {
        return Result.success(documentService.getVersionHistory(id));
    }

    @Operation(summary = "导出文档列表")
    @PostMapping("/export")
    public ResponseEntity<byte[]> exportToExcel(@RequestBody @Valid DocumentQuery query) {
        byte[] content = documentService.exportToExcel(query);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                    "attachment; filename=\"documents.xlsx\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(content);
    }

    @Operation(summary = "分页查询文档")
    @PostMapping("/page")
    public Result<List<DocumentVO>> page(@RequestBody @Valid DocumentQuery query) {
        return Result.success(documentService.page(query));
    }

    @Operation(summary = "获取文档详情")
    @GetMapping("/{id}")
    public Result<DocumentVO> get(@PathVariable @NotNull Long id) {
        return Result.success(documentService.get(id));
    }
} 