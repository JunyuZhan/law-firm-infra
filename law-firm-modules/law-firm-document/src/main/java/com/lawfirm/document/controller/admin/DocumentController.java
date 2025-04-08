package com.lawfirm.document.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.document.dto.document.DocumentCreateDTO;
import com.lawfirm.model.document.dto.document.DocumentQueryDTO;
import com.lawfirm.model.document.dto.document.DocumentUpdateDTO;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.document.constant.DocumentConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 文档管理控制器
 */
@Slf4j
@Tag(name = "文档管理", description = "文档管理接口")
@RestController("documentController")
@RequestMapping(DocumentConstants.API_PREFIX)
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    /**
     * 创建文档
     */
    @PostMapping
    @Operation(
        summary = "创建文档",
        description = "创建新的文档，需要同时上传文档元数据和文件内容。文档元数据包含标题、描述等基本信息，文件支持多种格式"
    )
    public CommonResult<Long> createDocument(
            @Parameter(description = "文档元数据") @RequestPart("metadata") @Validated DocumentCreateDTO createDTO,
            @Parameter(description = "文档文件") @RequestPart("file") MultipartFile file) throws IOException {
        Long documentId = documentService.createDocument(createDTO, file.getInputStream());
        return CommonResult.success(documentId);
    }

    /**
     * 更新文档信息
     */
    @PutMapping("/{id}")
    @Operation(
        summary = "更新文档信息",
        description = "更新已存在文档的元数据信息，包括标题、描述等，但不更新文档内容"
    )
    public CommonResult<Void> updateDocument(
            @Parameter(description = "文档ID") @PathVariable Long id,
            @Parameter(description = "更新参数") @RequestBody @Validated DocumentUpdateDTO updateDTO) {
        documentService.updateDocument(id, updateDTO);
        return CommonResult.success();
    }

    /**
     * 更新文档内容
     */
    @PutMapping("/{id}/content")
    @Operation(
        summary = "更新文档内容",
        description = "更新已存在文档的文件内容，不修改文档的元数据信息"
    )
    public CommonResult<Void> updateDocumentContent(
            @Parameter(description = "文档ID") @PathVariable Long id,
            @Parameter(description = "文档文件") @RequestPart("file") MultipartFile file) throws IOException {
        documentService.updateDocumentContent(id, file.getInputStream());
        return CommonResult.success();
    }

    /**
     * 删除文档
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = "删除文档",
        description = "根据ID删除单个文档，同时删除文档元数据和文件内容"
    )
    public CommonResult<Void> deleteDocument(
            @Parameter(description = "文档ID") @PathVariable Long id) {
        documentService.deleteDocument(id);
        return CommonResult.success();
    }

    /**
     * 批量删除文档
     */
    @DeleteMapping("/batch")
    @Operation(
        summary = "批量删除文档",
        description = "批量删除多个文档，支持同时删除多个文档的元数据和文件内容"
    )
    public CommonResult<Void> deleteDocuments(
            @Parameter(description = "文档ID列表") @RequestBody List<Long> ids) {
        documentService.deleteDocuments(ids);
        return CommonResult.success();
    }

    /**
     * 获取文档详情
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "获取文档详情",
        description = "根据ID获取文档的详细信息，包括文档的元数据，但不包含文件内容"
    )
    public CommonResult<DocumentVO> getDocument(
            @Parameter(description = "文档ID") @PathVariable Long id) {
        DocumentVO document = documentService.getDocumentById(id);
        return CommonResult.success(document);
    }

    /**
     * 分页查询文档
     */
    @GetMapping
    @Operation(
        summary = "分页查询文档",
        description = "根据查询条件分页获取文档列表，支持按标题、创建时间等条件筛选"
    )
    public CommonResult<Page<DocumentVO>> pageDocuments(
            @Parameter(description = "查询参数") DocumentQueryDTO queryDTO,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<BaseDocument> page = new Page<>(pageNum, pageSize);
        Page<DocumentVO> result = documentService.pageDocuments(page, queryDTO);
        return CommonResult.success(result);
    }
}
