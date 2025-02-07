package com.lawfirm.mini.controller.sign;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.document.model.vo.DocumentVO;
import com.lawfirm.document.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "小程序-签署管理")
@RestController
@RequestMapping("/mini/sign")
@RequiredArgsConstructor
public class SignController {

    private final DocumentService documentService;

    @Operation(summary = "获取待签署文件")
    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('mini:sign:query')")
    public Result<List<DocumentVO>> getPendingDocuments() {
        return Result.ok(documentService.getPendingSignDocuments());
    }

    @Operation(summary = "获取已签署文件")
    @GetMapping("/signed")
    @PreAuthorize("hasAuthority('mini:sign:query')")
    public Result<List<DocumentVO>> getSignedDocuments() {
        return Result.ok(documentService.getSignedDocuments());
    }

    @Operation(summary = "签署文件")
    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('mini:sign:sign')")
    @OperationLog("签署文件")
    public Result<Void> sign(@PathVariable Long id) {
        documentService.signDocument(id);
        return Result.ok();
    }

    @Operation(summary = "拒绝签署")
    @PostMapping("/reject/{id}")
    @PreAuthorize("hasAuthority('mini:sign:reject')")
    @OperationLog("拒绝签署")
    public Result<Void> reject(@PathVariable Long id, @RequestParam String reason) {
        documentService.rejectSign(id, reason);
        return Result.ok();
    }

    @Operation(summary = "预览签署文件")
    @GetMapping("/preview/{id}")
    @PreAuthorize("hasAuthority('mini:sign:query')")
    public Result<String> preview(@PathVariable Long id) {
        return Result.ok(documentService.previewSignDocument(id));
    }
} 