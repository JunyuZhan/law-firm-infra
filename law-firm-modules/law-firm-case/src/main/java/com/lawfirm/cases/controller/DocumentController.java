package com.lawfirm.cases.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.cases.constant.CaseBusinessConstants;
import com.lawfirm.model.cases.dto.business.CaseDocumentDTO;
import com.lawfirm.model.cases.service.business.CaseDocumentService;
import com.lawfirm.model.cases.vo.business.CaseDocumentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 案件文档控制器
 */
@RestController("caseDocumentController")
@RequestMapping(CaseBusinessConstants.Controller.API_DOCUMENT_PREFIX)
@RequiredArgsConstructor
@Tag(name = "案件文档管理", description = "提供案件文档管理功能，包括文档上传、下载、分类、版本控制、共享等操作")
public class DocumentController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DocumentController.class);

    private final CaseDocumentService documentService;

    @Operation(
        summary = "上传文档",
        description = "上传案件相关的文档，支持多种文档格式，自动提取文档元数据"
    )
    @PostMapping
    public Long uploadDocument(
            @Parameter(description = "文档信息，包括文档名称、类型、内容等") 
            @RequestBody @Validated CaseDocumentDTO documentDTO) {
        log.info("上传文档: {}", documentDTO);
        return documentService.uploadDocument(documentDTO);
    }

    @Operation(
        summary = "批量上传文档",
        description = "批量上传多个案件文档，支持多种文档格式，自动提取文档元数据"
    )
    @PostMapping("/batch")
    public boolean batchUploadDocuments(
            @Parameter(description = "文档信息列表，每个文档包括名称、类型、内容等") 
            @RequestBody @Validated List<CaseDocumentDTO> documentDTOs) {
        log.info("批量上传文档: {}", documentDTOs);
        return documentService.batchUploadDocuments(documentDTOs);
    }

    @Operation(
        summary = "更新文档信息",
        description = "更新已有文档的基本信息，包括文档名称、描述、标签等"
    )
    @PutMapping
    public boolean updateDocument(
            @Parameter(description = "更新的文档信息") 
            @RequestBody @Validated CaseDocumentDTO documentDTO) {
        log.info("更新文档信息: {}", documentDTO);
        return documentService.updateDocument(documentDTO);
    }

    @Operation(
        summary = "删除文档",
        description = "删除指定的文档，如果文档已被引用则不允许删除"
    )
    @DeleteMapping("/{documentId}")
    public boolean deleteDocument(
            @Parameter(description = "文档ID") 
            @PathVariable("documentId") Long documentId) {
        log.info("删除文档: {}", documentId);
        return documentService.deleteDocument(documentId);
    }

    @Operation(
        summary = "批量删除文档",
        description = "批量删除多个文档，如果任一文档已被引用则不允许删除"
    )
    @DeleteMapping("/batch")
    public boolean batchDeleteDocuments(
            @Parameter(description = "文档ID列表") 
            @RequestBody List<Long> documentIds) {
        log.info("批量删除文档: {}", documentIds);
        return documentService.batchDeleteDocuments(documentIds);
    }

    @Operation(
        summary = "获取文档详情",
        description = "获取文档的详细信息，包括基本信息、元数据、标签等"
    )
    @GetMapping("/{documentId}")
    public CaseDocumentVO getDocumentDetail(
            @Parameter(description = "文档ID") 
            @PathVariable("documentId") Long documentId) {
        log.info("获取文档详情: {}", documentId);
        return documentService.getDocumentDetail(documentId);
    }

    @Operation(
        summary = "获取案件的所有文档",
        description = "获取指定案件的所有相关文档列表"
    )
    @GetMapping("/cases/{caseId}")
    public List<CaseDocumentVO> listCaseDocuments(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("获取案件的所有文档: caseId={}", caseId);
        return documentService.listCaseDocuments(caseId);
    }

    @Operation(
        summary = "分页查询文档",
        description = "分页查询案件的文档列表，支持按文档类型和安全级别筛选"
    )
    @GetMapping("/cases/{caseId}/page")
    public IPage<CaseDocumentVO> pageDocuments(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "文档类型：1-合同，2-协议，3-诉讼文书，4-证据材料，5-其他") 
            @RequestParam(required = false) Integer documentType,
            @Parameter(description = "安全级别：1-公开，2-内部，3-保密，4-机密") 
            @RequestParam(required = false) Integer securityLevel,
            @Parameter(description = "页码，从1开始") 
            @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页显示记录数") 
            @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询文档: caseId={}, documentType={}, securityLevel={}, pageNum={}, pageSize={}", 
                caseId, documentType, securityLevel, pageNum, pageSize);
        return documentService.pageDocuments(caseId, documentType, securityLevel, pageNum, pageSize);
    }

    @Operation(
        summary = "下载文档",
        description = "下载指定的文档，支持断点续传和文档格式转换"
    )
    @GetMapping("/{documentId}/download")
    public ResponseEntity<byte[]> downloadDocument(
            @Parameter(description = "文档ID") 
            @PathVariable("documentId") Long documentId) {
        log.info("下载文档: {}", documentId);
        byte[] document = documentService.downloadDocument(documentId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.pdf")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(document);
    }

    @Operation(
        summary = "预览文档",
        description = "在线预览文档内容，支持多种文档格式的在线预览"
    )
    @GetMapping("/{documentId}/preview")
    public String previewDocument(
            @Parameter(description = "文档ID") 
            @PathVariable("documentId") Long documentId) {
        log.info("预览文档: {}", documentId);
        return documentService.previewDocument(documentId);
    }

    @Operation(
        summary = "分享文档",
        description = "生成文档分享链接，支持设置分享类型和有效期"
    )
    @PostMapping("/{documentId}/share")
    public String shareDocument(
            @Parameter(description = "文档ID") 
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "分享类型：1-公开分享，2-密码分享，3-指定用户分享") 
            @RequestParam Integer shareType,
            @Parameter(description = "分享链接有效期（分钟），0表示永久有效") 
            @RequestParam Integer expireTime) {
        log.info("分享文档: documentId={}, shareType={}, expireTime={}", documentId, shareType, expireTime);
        return documentService.shareDocument(documentId, shareType, expireTime);
    }

    @Operation(
        summary = "设置文档安全级别",
        description = "设置文档的安全级别，用于控制文档的访问权限"
    )
    @PutMapping("/{documentId}/security-level")
    public boolean setSecurityLevel(
            @Parameter(description = "文档ID") 
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "安全级别：1-公开，2-内部，3-保密，4-机密") 
            @RequestParam Integer securityLevel) {
        log.info("设置文档安全级别: documentId={}, securityLevel={}", documentId, securityLevel);
        return documentService.setSecurityLevel(documentId, securityLevel);
    }

    @Operation(
        summary = "添加文档标签",
        description = "为文档添加标签，用于文档分类和快速检索"
    )
    @PostMapping("/{documentId}/tags")
    public boolean addDocumentTag(
            @Parameter(description = "文档ID") 
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "标签名称") 
            @RequestParam String tag) {
        log.info("添加文档标签: documentId={}, tag={}", documentId, tag);
        return documentService.addDocumentTag(documentId, tag);
    }

    @Operation(
        summary = "移除文档标签",
        description = "移除文档已有的标签"
    )
    @DeleteMapping("/{documentId}/tags")
    public boolean removeDocumentTag(
            @Parameter(description = "文档ID") 
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "要移除的标签名称") 
            @RequestParam String tag) {
        log.info("移除文档标签: documentId={}, tag={}", documentId, tag);
        return documentService.removeDocumentTag(documentId, tag);
    }

    @Operation(
        summary = "添加文档备注",
        description = "为文档添加备注信息，记录文档的重要说明"
    )
    @PostMapping("/{documentId}/notes")
    public boolean addDocumentNote(
            @Parameter(description = "文档ID") 
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "备注内容") 
            @RequestParam String note) {
        log.info("添加文档备注: documentId={}, note={}", documentId, note);
        return documentService.addDocumentNote(documentId, note);
    }

    @Operation(
        summary = "移动文档",
        description = "将文档移动到指定的文件夹中"
    )
    @PutMapping("/{documentId}/move")
    public boolean moveDocument(
            @Parameter(description = "文档ID") 
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "目标文件夹ID，0表示根目录") 
            @RequestParam Long targetFolderId) {
        log.info("移动文档: documentId={}, targetFolderId={}", documentId, targetFolderId);
        return documentService.moveDocument(documentId, targetFolderId);
    }

    @Operation(
        summary = "复制文档",
        description = "将文档复制到指定的文件夹中，创建文档副本"
    )
    @PostMapping("/{documentId}/copy")
    public boolean copyDocument(
            @Parameter(description = "文档ID") 
            @PathVariable("documentId") Long documentId,
            @Parameter(description = "目标文件夹ID，0表示根目录") 
            @RequestParam Long targetFolderId) {
        log.info("复制文档: documentId={}, targetFolderId={}", documentId, targetFolderId);
        return documentService.copyDocument(documentId, targetFolderId);
    }

    @Operation(
        summary = "创建文件夹",
        description = "在案件文档目录中创建新的文件夹"
    )
    @PostMapping("/folders")
    public Long createFolder(
            @Parameter(description = "案件ID") 
            @RequestParam Long caseId,
            @Parameter(description = "父文件夹ID，null表示在根目录创建") 
            @RequestParam(required = false) Long parentFolderId,
            @Parameter(description = "文件夹名称") 
            @RequestParam String folderName) {
        log.info("创建文件夹: caseId={}, parentFolderId={}, folderName={}", caseId, parentFolderId, folderName);
        return documentService.createFolder(caseId, parentFolderId, folderName);
    }

    @Operation(
        summary = "重命名文件夹",
        description = "修改文件夹的名称"
    )
    @PutMapping("/folders/{folderId}/rename")
    public boolean renameFolder(
            @Parameter(description = "文件夹ID") 
            @PathVariable("folderId") Long folderId,
            @Parameter(description = "文件夹新名称") 
            @RequestParam String newName) {
        log.info("重命名文件夹: folderId={}, newName={}", folderId, newName);
        return documentService.renameFolder(folderId, newName);
    }

    @Operation(
        summary = "删除文件夹",
        description = "删除指定的文件夹，如果文件夹不为空则不允许删除"
    )
    @DeleteMapping("/folders/{folderId}")
    public boolean deleteFolder(
            @Parameter(description = "文件夹ID") 
            @PathVariable("folderId") Long folderId) {
        log.info("删除文件夹: {}", folderId);
        return documentService.deleteFolder(folderId);
    }

    @Operation(
        summary = "获取文件夹树",
        description = "获取案件文档的文件夹树形结构"
    )
    @GetMapping("/cases/{caseId}/folder-tree")
    public List<CaseDocumentVO> getFolderTree(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("获取文件夹树: caseId={}", caseId);
        return documentService.getFolderTree(caseId);
    }

    @Operation(
        summary = "检查文档是否存在",
        description = "检查指定ID的文档是否存在"
    )
    @GetMapping("/{documentId}/exists")
    public boolean checkDocumentExists(
            @Parameter(description = "文档ID") 
            @PathVariable("documentId") Long documentId) {
        log.info("检查文档是否存在: {}", documentId);
        return documentService.checkDocumentExists(documentId);
    }

    @Operation(
        summary = "统计案件文档数量",
        description = "统计案件的文档数量，支持按文档类型和安全级别统计"
    )
    @GetMapping("/cases/{caseId}/count")
    public int countDocuments(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "文档类型：1-合同，2-协议，3-诉讼文书，4-证据材料，5-其他") 
            @RequestParam(required = false) Integer documentType,
            @Parameter(description = "安全级别：1-公开，2-内部，3-保密，4-机密") 
            @RequestParam(required = false) Integer securityLevel) {
        log.info("统计案件文档数量: caseId={}, documentType={}, securityLevel={}", 
                caseId, documentType, securityLevel);
        return documentService.countDocuments(caseId, documentType, securityLevel);
    }
} 