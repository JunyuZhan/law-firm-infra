package com.lawfirm.document.controller.admin;

import com.lawfirm.common.core.response.ResponseResult;
import com.lawfirm.model.document.dto.document.DocumentCreateDTO;
import com.lawfirm.model.document.dto.document.DocumentQueryDTO;
import com.lawfirm.model.document.dto.document.DocumentUpdateDTO;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.document.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 文档管理后台控制器
 */
@Tag(name = "文档管理后台接口")
@RestController
@RequestMapping("/admin/document")
@RequiredArgsConstructor
public class DocumentAdminController {

    private final DocumentService documentService;

    @Operation(summary = "创建文档")
    @PostMapping
    @PreAuthorize("hasAuthority('document:create')")
    public ResponseResult<Long> createDocument(@RequestBody DocumentCreateDTO createDTO) {
        Long documentId = documentService.createDocument(createDTO, null);
        return ResponseResult.success(documentId);
    }

    @Operation(summary = "上传文档")
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('document:upload')")
    public ResponseResult<Long> uploadDocument(@RequestParam("file") MultipartFile file, 
                                             @RequestParam("docType") String docType) throws IOException {
        DocumentCreateDTO createDTO = new DocumentCreateDTO();
        createDTO.setDocType(docType);
        createDTO.setFileName(file.getOriginalFilename());
        Long documentId = documentService.createDocument(createDTO, file.getInputStream());
        return ResponseResult.success(documentId);
    }

    @Operation(summary = "更新文档")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('document:update')")
    public ResponseResult<Void> updateDocument(@PathVariable Long id, @RequestBody DocumentUpdateDTO updateDTO) {
        documentService.updateDocument(id, updateDTO);
        return ResponseResult.success();
    }

    @Operation(summary = "更新文档内容")
    @PutMapping("/{id}/content")
    @PreAuthorize("hasAuthority('document:update')")
    public ResponseResult<Void> updateDocumentContent(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        documentService.updateDocumentContent(id, file.getInputStream());
        return ResponseResult.success();
    }

    @Operation(summary = "删除文档")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('document:delete')")
    public ResponseResult<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseResult.success();
    }

    @Operation(summary = "批量删除文档")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('document:delete')")
    public ResponseResult<Void> deleteDocuments(@RequestBody List<Long> ids) {
        documentService.deleteDocuments(ids);
        return ResponseResult.success();
    }

    @Operation(summary = "获取文档详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('document:view')")
    public ResponseResult<DocumentVO> getDocument(@PathVariable Long id) {
        DocumentVO document = documentService.getDocumentById(id);
        return ResponseResult.success(document);
    }

    @Operation(summary = "分页查询文档")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('document:view')")
    public ResponseResult<List<DocumentVO>> pageDocuments(DocumentQueryDTO queryDTO) {
        List<DocumentVO> documents = documentService.pageDocuments(null, queryDTO).getRecords();
        return ResponseResult.success(documents);
    }

    @Operation(summary = "更新文档状态")
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('document:update')")
    public ResponseResult<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        documentService.updateStatus(id, status);
        return ResponseResult.success();
    }

    @Operation(summary = "获取文档预览URL")
    @GetMapping("/{id}/preview")
    @PreAuthorize("hasAuthority('document:view')")
    public ResponseResult<String> previewDocument(@PathVariable Long id) {
        String previewUrl = documentService.previewDocument(id);
        return ResponseResult.success(previewUrl);
    }

    @Operation(summary = "获取文档下载URL")
    @GetMapping("/{id}/download")
    @PreAuthorize("hasAuthority('document:download')")
    public ResponseResult<String> getDocumentUrl(@PathVariable Long id) {
        String downloadUrl = documentService.getDocumentUrl(id);
        return ResponseResult.success(downloadUrl);
    }
}
