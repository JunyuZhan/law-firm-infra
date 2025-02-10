package com.lawfirm.mini.controller.document;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.document.model.vo.DocumentVO;
import com.lawfirm.document.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "小程序-文档管理")
@RestController
@RequestMapping("/mini/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "上传证据材料")
    @PostMapping("/upload/evidence")
    @PreAuthorize("hasAuthority('mini:document:upload')")
    @OperationLog("上传证据材料")
    public Result<DocumentVO> uploadEvidence(@RequestParam("file") MultipartFile file, @RequestParam Long caseId) {
        return Result.ok(documentService.uploadEvidence(file, caseId));
    }

    @Operation(summary = "下载文档")
    @GetMapping("/download/{id}")
    @PreAuthorize("hasAuthority('mini:document:download')")
    @OperationLog("下载文档")
    public void download(@PathVariable Long id) {
        documentService.downloadForClient(id);
    }

    @Operation(summary = "预览文档")
    @GetMapping("/preview/{id}")
    @PreAuthorize("hasAuthority('mini:document:preview')")
    public Result<String> preview(@PathVariable Long id) {
        return Result.ok(documentService.previewForClient(id));
    }
} 