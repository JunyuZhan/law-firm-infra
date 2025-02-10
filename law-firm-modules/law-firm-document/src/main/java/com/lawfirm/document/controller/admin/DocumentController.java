package com.lawfirm.document.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.document.dto.request.DocumentAddRequest;
import com.lawfirm.document.dto.request.DocumentQueryRequest;
import com.lawfirm.document.dto.request.DocumentUpdateRequest;
import com.lawfirm.document.dto.response.DocumentResponse;
import com.lawfirm.document.service.IDocumentService;
import com.lawfirm.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文档管理接口
 */
@Tag(name = "文档管理接口")
@RestController
@RequestMapping("/admin/document")
@RequiredArgsConstructor
public class DocumentController {

    private final IDocumentService documentService;

    @Operation(summary = "添加文档")
    @PostMapping
    public Result<Long> addDocument(@Validated @RequestBody DocumentAddRequest request) {
        return Result.success(documentService.addDocument(request));
    }

    @Operation(summary = "更新文档")
    @PutMapping
    public Result<Void> updateDocument(@Validated @RequestBody DocumentUpdateRequest request) {
        documentService.updateDocument(request);
        return Result.success();
    }

    @Operation(summary = "删除文档")
    @DeleteMapping("/{id}")
    public Result<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return Result.success();
    }

    @Operation(summary = "分页查询文档")
    @GetMapping("/page")
    public Result<IPage<DocumentResponse>> pageDocuments(@Validated DocumentQueryRequest request) {
        return Result.success(documentService.pageDocuments(request));
    }

    @Operation(summary = "获取文档详情")
    @GetMapping("/{id}")
    public Result<DocumentResponse> getDocument(@PathVariable Long id) {
        return Result.success(documentService.getDocumentById(id));
    }
} 