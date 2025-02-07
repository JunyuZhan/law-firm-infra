package com.lawfirm.staff.controller.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.annotation.OperationLog;
import com.lawfirm.staff.annotation.OperationType;
import com.lawfirm.staff.client.DocumentClient;
import com.lawfirm.staff.model.request.lawyer.CreateDocumentRequest;
import com.lawfirm.staff.model.request.lawyer.DocumentPageRequest;
import com.lawfirm.staff.model.request.lawyer.UpdateDocumentRequest;
import com.lawfirm.staff.model.response.lawyer.DocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文档管理", description = "文档管理相关接口")
@RestController
@RequestMapping("/lawyer/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentClient documentClient;

    @Operation(summary = "分页查询文档")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('lawyer:document:query')")
    @OperationLog(value = "分页查询文档", type = OperationType.QUERY)
    public Result<PageResult<DocumentResponse>> page(DocumentPageRequest request) {
        return documentClient.page(request);
    }

    @Operation(summary = "添加文档")
    @PostMapping
    @PreAuthorize("hasAuthority('lawyer:document:add')")
    @OperationLog(value = "添加文档", type = OperationType.CREATE)
    public Result<Void> add(@RequestBody @Validated CreateDocumentRequest request) {
        return documentClient.add(request);
    }

    @Operation(summary = "修改文档")
    @PutMapping
    @PreAuthorize("hasAuthority('lawyer:document:update')")
    @OperationLog(value = "修改文档", type = OperationType.UPDATE)
    public Result<Void> update(@RequestBody @Validated UpdateDocumentRequest request) {
        return documentClient.update(request);
    }

    @Operation(summary = "删除文档")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('lawyer:document:delete')")
    @OperationLog(value = "删除文档", type = OperationType.DELETE)
    public Result<Void> delete(@PathVariable Long id) {
        return documentClient.delete(id);
    }

    @Operation(summary = "获取文档详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('lawyer:document:query')")
    @OperationLog(value = "获取文档详情", type = OperationType.QUERY)
    public Result<DocumentResponse> get(@PathVariable Long id) {
        return documentClient.get(id);
    }

    @Operation(summary = "获取我的文档")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('lawyer:document:query')")
    @OperationLog(value = "获取我的文档", type = OperationType.QUERY)
    public Result<PageResult<DocumentResponse>> getMyDocuments(DocumentPageRequest request) {
        return documentClient.getMyDocuments(request);
    }

    @Operation(summary = "获取部门文档")
    @GetMapping("/department")
    @PreAuthorize("hasAuthority('lawyer:document:query')")
    @OperationLog(value = "获取部门文档", type = OperationType.QUERY)
    public Result<PageResult<DocumentResponse>> getDepartmentDocuments(DocumentPageRequest request) {
        return documentClient.getDepartmentDocuments(request);
    }

    @Operation(summary = "上传文档")
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('lawyer:document:upload')")
    @OperationLog(value = "上传文档", type = OperationType.CREATE)
    public Result<String> upload(@RequestPart("file") MultipartFile file) {
        return documentClient.upload(file);
    }

    @Operation(summary = "下载文档")
    @GetMapping("/download/{id}")
    @PreAuthorize("hasAuthority('lawyer:document:download')")
    @OperationLog(value = "下载文档", type = OperationType.EXPORT)
    public void download(@PathVariable Long id) {
        documentClient.download(id);
    }

    @Operation(summary = "预览文档")
    @PostMapping("/preview/{id}")
    @PreAuthorize("hasAuthority('lawyer:document:preview')")
    @OperationLog(value = "预览文档", type = OperationType.QUERY)
    public Result<String> preview(@PathVariable Long id) {
        return documentClient.preview(id);
    }

    @Operation(summary = "分享文档")
    @PostMapping("/share")
    @PreAuthorize("hasAuthority('lawyer:document:share')")
    @OperationLog(value = "分享文档", type = OperationType.CREATE)
    public Result<Void> share(@RequestBody @Validated CreateDocumentRequest request) {
        return documentClient.share(request);
    }
} 