package com.lawfirm.document.controller.admin;

import com.lawfirm.document.dto.request.DocumentVersionAddRequest;
import com.lawfirm.document.dto.response.DocumentVersionResponse;
import com.lawfirm.document.service.IDocumentVersionService;
import com.lawfirm.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档版本管理接口
 */
@Tag(name = "文档版本管理接口")
@RestController
@RequestMapping("/admin/document/version")
@RequiredArgsConstructor
public class DocumentVersionController {

    private final IDocumentVersionService documentVersionService;

    @Operation(summary = "添加文档版本")
    @PostMapping
    public Result<Long> addVersion(@Validated @RequestBody DocumentVersionAddRequest request) {
        return Result.success(documentVersionService.addVersion(request));
    }

    @Operation(summary = "获取文档所有版本")
    @GetMapping("/list/{docId}")
    public Result<List<DocumentVersionResponse>> getVersions(@PathVariable Long docId) {
        return Result.success(documentVersionService.getVersionsByDocId(docId));
    }

    @Operation(summary = "获取文档指定版本")
    @GetMapping("/{docId}/{versionNo}")
    public Result<DocumentVersionResponse> getVersion(@PathVariable Long docId, @PathVariable Integer versionNo) {
        return Result.success(documentVersionService.getVersion(docId, versionNo));
    }

    @Operation(summary = "回滚到指定版本")
    @PostMapping("/rollback/{docId}/{versionNo}")
    public Result<Void> rollbackToVersion(@PathVariable Long docId, @PathVariable Integer versionNo) {
        documentVersionService.rollbackToVersion(docId, versionNo);
        return Result.success();
    }
} 