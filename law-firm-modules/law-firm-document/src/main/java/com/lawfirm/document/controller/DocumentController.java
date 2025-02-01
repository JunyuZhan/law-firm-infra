package com.lawfirm.document.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.document.service.DocumentService;
import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.document.enums.DocumentStatusEnum;
import com.lawfirm.model.document.enums.DocumentTypeEnum;
import com.lawfirm.model.document.query.DocumentQuery;
import com.lawfirm.model.document.vo.DocumentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "文档管理")
@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
@Validated
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    @Operation(summary = "上传文档")
    @OperationLog(description = "上传文档", operationType = "DOCUMENT_UPLOAD")
    public Result<DocumentVO> upload(@RequestParam("file") MultipartFile file, @RequestBody Document document) {
        return Result.ok(documentService.upload(file, document, "system"));
    }

    @PostMapping("/batch-upload")
    @Operation(summary = "批量上传文档")
    @OperationLog(description = "批量上传文档", operationType = "DOCUMENT_BATCH_UPLOAD")
    public Result<List<DocumentVO>> batchUpload(@RequestParam("files") List<MultipartFile> files, @RequestBody List<Document> documents) {
        return Result.ok(documentService.batchUpload(files, documents, "system"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新文档")
    @OperationLog(description = "更新文档", operationType = "DOCUMENT_UPDATE")
    public Result<DocumentVO> update(@PathVariable Long id, @RequestParam(value = "file", required = false) MultipartFile file, @RequestBody Document document) {
        return Result.ok(documentService.update(id, file, document, "system"));
    }

    @GetMapping("/download/{id}")
    @Operation(summary = "下载文档")
    @OperationLog(description = "下载文档", operationType = "DOCUMENT_DOWNLOAD")
    public ResponseEntity<byte[]> download(@PathVariable Long id, @RequestParam(required = false) String version) {
        byte[] content = documentService.download(id, version);
        Document document = documentService.getById(id);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getDocumentName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(content);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文档")
    @OperationLog(description = "删除文档", operationType = "DOCUMENT_DELETE")
    public Result<Void> delete(@PathVariable Long id) {
        documentService.delete(id, "system");
        return Result.ok();
    }

    @PutMapping("/{id}/audit")
    @Operation(summary = "审核文档")
    @OperationLog(description = "审核文档", operationType = "DOCUMENT_AUDIT")
    public Result<DocumentVO> audit(@PathVariable Long id, @RequestParam Integer status, @RequestParam(required = false) String comment) {
        return Result.ok(documentService.audit(id, status, comment, "system"));
    }

    @GetMapping("/{id}/versions")
    @Operation(summary = "获取文档版本历史")
    @OperationLog(description = "获取文档版本历史", operationType = "DOCUMENT_VERSION_HISTORY")
    public Result<List<DocumentVO>> getVersionHistory(@PathVariable Long id) {
        return Result.ok(documentService.getVersionHistory(id));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询文档")
    @OperationLog(description = "分页查询文档", operationType = "DOCUMENT_PAGE")
    public Result<PageResult<DocumentVO>> page(@Valid DocumentQuery query) {
        Page<Document> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper<Document> wrapper = new QueryWrapper<>();
        
        // 构建查询条件
        if (StringUtils.hasText(query.getDocumentNumber())) {
            wrapper.like("document_number", query.getDocumentNumber());
        }
        if (StringUtils.hasText(query.getDocumentName())) {
            wrapper.like("document_name", query.getDocumentName());
        }
        if (StringUtils.hasText(query.getFileType())) {
            wrapper.eq("file_type", query.getFileType());
        }
        if (StringUtils.hasText(query.getVersion())) {
            wrapper.eq("version", Integer.valueOf(query.getVersion()));
        }
        if (query.getStatus() != null) {
            wrapper.eq("status", query.getStatus());
        }
        if (query.getUploadTimeStart() != null) {
            wrapper.ge("last_modified_time", query.getUploadTimeStart());
        }
        if (query.getUploadTimeEnd() != null) {
            wrapper.le("last_modified_time", query.getUploadTimeEnd());
        }
        if (StringUtils.hasText(query.getUploadBy())) {
            wrapper.eq("last_modified_by", query.getUploadBy());
        }
        if (query.getAuditTimeStart() != null) {
            wrapper.ge("last_modified_time", query.getAuditTimeStart());
        }
        if (query.getAuditTimeEnd() != null) {
            wrapper.le("last_modified_time", query.getAuditTimeEnd());
        }
        if (StringUtils.hasText(query.getAuditBy())) {
            wrapper.eq("last_modified_by", query.getAuditBy());
        }
        if (StringUtils.hasText(query.getDocumentType())) {
            wrapper.eq("document_type", query.getDocumentType());
        }
        if (StringUtils.hasText(query.getTags())) {
            wrapper.like("keywords", query.getTags());
        }
        
        return Result.ok(documentService.pageDocuments(page, wrapper));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文档详情")
    @OperationLog(description = "获取文档详情", operationType = "DOCUMENT_DETAIL")
    public Result<DocumentVO> detail(@PathVariable Long id) {
        return Result.ok(documentService.findById(id));
    }
} 