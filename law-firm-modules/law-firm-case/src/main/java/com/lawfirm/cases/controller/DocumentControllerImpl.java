package com.lawfirm.cases.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseDocumentDTO;
import com.lawfirm.model.cases.service.business.CaseDocumentService;
import com.lawfirm.model.cases.vo.business.CaseDocumentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 案件文档管理控制器实现
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/cases/documents")
@RequiredArgsConstructor
@Tag(name = "案件文档管理", description = "案件文档管理相关接口")
public class DocumentControllerImpl {

    private final CaseDocumentService documentService;

    @PostMapping
    @Operation(summary = "上传文档")
    public Long uploadDocument(@RequestBody @Validated CaseDocumentDTO documentDTO) {
        log.info("上传文档: {}", documentDTO);
        return documentService.uploadDocument(documentDTO);
    }

    @PostMapping("/batch")
    @Operation(summary = "批量上传文档")
    public boolean batchUploadDocuments(@RequestBody @Validated List<CaseDocumentDTO> documentDTOs) {
        log.info("批量上传文档: {}", documentDTOs);
        return documentService.batchUploadDocuments(documentDTOs);
    }

    @PutMapping
    @Operation(summary = "更新文档信息")
    public boolean updateDocument(@RequestBody @Validated CaseDocumentDTO documentDTO) {
        log.info("更新文档信息: {}", documentDTO);
        return documentService.updateDocument(documentDTO);
    }

    @DeleteMapping("/{documentId}")
    @Operation(summary = "删除文档")
    public boolean deleteDocument(@PathVariable("documentId") Long documentId) {
        log.info("删除文档: {}", documentId);
        return documentService.deleteDocument(documentId);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除文档")
    public boolean batchDeleteDocuments(@RequestBody List<Long> documentIds) {
        log.info("批量删除文档: {}", documentIds);
        return documentService.batchDeleteDocuments(documentIds);
    }

    @GetMapping("/{documentId}")
    @Operation(summary = "获取文档详情")
    public CaseDocumentVO getDocumentDetail(@PathVariable("documentId") Long documentId) {
        log.info("获取文档详情: {}", documentId);
        return documentService.getDocumentDetail(documentId);
    }

    @GetMapping("/cases/{caseId}")
    @Operation(summary = "获取案件的所有文档")
    public List<CaseDocumentVO> listCaseDocuments(@PathVariable("caseId") Long caseId) {
        log.info("获取案件的所有文档: caseId={}", caseId);
        return documentService.listCaseDocuments(caseId);
    }

    @GetMapping("/cases/{caseId}/page")
    @Operation(summary = "分页查询文档")
    public IPage<CaseDocumentVO> pageDocuments(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "文档类型") @RequestParam(required = false) Integer documentType,
            @Parameter(description = "安全级别") @RequestParam(required = false) Integer securityLevel,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询文档: caseId={}, documentType={}, securityLevel={}, pageNum={}, pageSize={}", 
                caseId, documentType, securityLevel, pageNum, pageSize);
        return documentService.pageDocuments(caseId, documentType, securityLevel, pageNum, pageSize);
    }

    @GetMapping("/{documentId}/download")
    @Operation(summary = "下载文档")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable("documentId") Long documentId) {
        log.info("下载文档: {}", documentId);
        byte[] document = documentService.downloadDocument(documentId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.pdf")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(document);
    }

    @GetMapping("/{documentId}/preview")
    @Operation(summary = "预览文档")
    public String previewDocument(@PathVariable("documentId") Long documentId) {
        log.info("预览文档: {}", documentId);
        return documentService.previewDocument(documentId);
    }

    @PostMapping("/{documentId}/share")
    @Operation(summary = "分享文档")
    public String shareDocument(
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "分享类型") @RequestParam Integer shareType,
            @Parameter(description = "过期时间（分钟）") @RequestParam Integer expireTime) {
        log.info("分享文档: documentId={}, shareType={}, expireTime={}", documentId, shareType, expireTime);
        return documentService.shareDocument(documentId, shareType, expireTime);
    }

    @PutMapping("/{documentId}/security-level")
    @Operation(summary = "设置文档安全级别")
    public boolean setSecurityLevel(
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "安全级别") @RequestParam Integer securityLevel) {
        log.info("设置文档安全级别: documentId={}, securityLevel={}", documentId, securityLevel);
        return documentService.setSecurityLevel(documentId, securityLevel);
    }

    @PostMapping("/{documentId}/tags")
    @Operation(summary = "添加文档标签")
    public boolean addDocumentTag(
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "标签") @RequestParam String tag) {
        log.info("添加文档标签: documentId={}, tag={}", documentId, tag);
        return documentService.addDocumentTag(documentId, tag);
    }

    @DeleteMapping("/{documentId}/tags")
    @Operation(summary = "移除文档标签")
    public boolean removeDocumentTag(
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "标签") @RequestParam String tag) {
        log.info("移除文档标签: documentId={}, tag={}", documentId, tag);
        return documentService.removeDocumentTag(documentId, tag);
    }

    @PostMapping("/{documentId}/notes")
    @Operation(summary = "添加文档备注")
    public boolean addDocumentNote(
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "备注") @RequestParam String note) {
        log.info("添加文档备注: documentId={}, note={}", documentId, note);
        return documentService.addDocumentNote(documentId, note);
    }

    @PutMapping("/{documentId}/move")
    @Operation(summary = "移动文档")
    public boolean moveDocument(
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "目标文件夹ID") @RequestParam Long targetFolderId) {
        log.info("移动文档: documentId={}, targetFolderId={}", documentId, targetFolderId);
        return documentService.moveDocument(documentId, targetFolderId);
    }

    @PostMapping("/{documentId}/copy")
    @Operation(summary = "复制文档")
    public boolean copyDocument(
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "目标文件夹ID") @RequestParam Long targetFolderId) {
        log.info("复制文档: documentId={}, targetFolderId={}", documentId, targetFolderId);
        return documentService.copyDocument(documentId, targetFolderId);
    }

    @PostMapping("/folders")
    @Operation(summary = "创建文件夹")
    public Long createFolder(
            @Parameter(description = "案件ID") @RequestParam Long caseId,
            @Parameter(description = "父文件夹ID") @RequestParam(required = false) Long parentFolderId,
            @Parameter(description = "文件夹名称") @RequestParam String folderName) {
        log.info("创建文件夹: caseId={}, parentFolderId={}, folderName={}", caseId, parentFolderId, folderName);
        return documentService.createFolder(caseId, parentFolderId, folderName);
    }

    @PutMapping("/folders/{folderId}/rename")
    @Operation(summary = "重命名文件夹")
    public boolean renameFolder(
            @PathVariable("folderId") Long folderId,
            @Parameter(description = "新名称") @RequestParam String newName) {
        log.info("重命名文件夹: folderId={}, newName={}", folderId, newName);
        return documentService.renameFolder(folderId, newName);
    }

    @DeleteMapping("/folders/{folderId}")
    @Operation(summary = "删除文件夹")
    public boolean deleteFolder(@PathVariable("folderId") Long folderId) {
        log.info("删除文件夹: {}", folderId);
        return documentService.deleteFolder(folderId);
    }

    @GetMapping("/cases/{caseId}/folder-tree")
    @Operation(summary = "获取文件夹树")
    public List<CaseDocumentVO> getFolderTree(@PathVariable("caseId") Long caseId) {
        log.info("获取文件夹树: caseId={}", caseId);
        return documentService.getFolderTree(caseId);
    }

    @GetMapping("/{documentId}/exists")
    @Operation(summary = "检查文档是否存在")
    public boolean checkDocumentExists(@PathVariable("documentId") Long documentId) {
        log.info("检查文档是否存在: {}", documentId);
        return documentService.checkDocumentExists(documentId);
    }

    @GetMapping("/cases/{caseId}/count")
    @Operation(summary = "统计案件文档数量")
    public int countDocuments(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "文档类型") @RequestParam(required = false) Integer documentType,
            @Parameter(description = "安全级别") @RequestParam(required = false) Integer securityLevel) {
        log.info("统计案件文档数量: caseId={}, documentType={}, securityLevel={}", 
                caseId, documentType, securityLevel);
        return documentService.countDocuments(caseId, documentType, securityLevel);
    }
} 