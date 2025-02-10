package com.lawfirm.document.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.document.query.DocumentQuery;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.model.document.enums.DocumentStatusEnum;
import com.lawfirm.model.base.storage.model.FileMetadata;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 文档服务接口
 */
@Tag(name = "文档管理服务", description = "提供文档的基本操作和版本管理功能")
@Validated
public interface DocumentService extends IService<Document> {
    
    // =============== 文档基础操作 ===============
    @Operation(summary = "创建文档", description = "创建新文档")
    DocumentVO createDocument(
            @Parameter(description = "文档文件") @NotNull MultipartFile file,
            @Parameter(description = "文档信息") @Valid Document document,
            @Parameter(description = "操作人") @NotBlank String operator);
    
    @Operation(summary = "批量创建文档", description = "批量创建新文档")
    List<DocumentVO> batchCreateDocuments(
            @Parameter(description = "文档文件列表") @NotEmpty List<MultipartFile> files,
            @Parameter(description = "文档信息列表") @NotEmpty List<Document> documents,
            @Parameter(description = "操作人") @NotBlank String operator);
    
    @Operation(summary = "更新文档", description = "更新文档信息和内容")
    DocumentVO updateDocument(
            @Parameter(description = "文档ID") @NotNull Long id,
            @Parameter(description = "文档文件") MultipartFile file,
            @Parameter(description = "文档信息") @Valid Document document,
            @Parameter(description = "操作人") @NotBlank String operator);
    
    @Operation(summary = "更新文档信息", description = "仅更新文档基本信息")
    DocumentVO updateDocumentInfo(
            @Parameter(description = "文档信息") @Valid DocumentVO documentVO,
            @Parameter(description = "操作人") @NotBlank String operator);
    
    @Operation(summary = "删除文档", description = "删除文档及其所有版本")
    void deleteDocument(
            @Parameter(description = "文档ID") @NotNull Long id,
            @Parameter(description = "操作人") @NotBlank String operator);
    
    @Operation(summary = "批量删除文档", description = "批量删除文档及其版本")
    void batchDeleteDocuments(
            @Parameter(description = "文档ID列表") @NotEmpty List<Long> ids,
            @Parameter(description = "操作人") @NotBlank String operator);
    
    // =============== 文档内容操作 ===============
    @Operation(summary = "下载文档", description = "下载指定版本的文档")
    byte[] downloadDocument(
            @Parameter(description = "文档ID") @NotNull Long id,
            @Parameter(description = "版本号") String version);
    
    @Operation(summary = "获取文档预览地址", description = "获取文档的在线预览地址")
    String getDocumentPreviewUrl(
            @Parameter(description = "文档ID") @NotNull Long id,
            @Parameter(description = "版本号") String version);
    
    // =============== 文档状态管理 ===============
    @Operation(summary = "审核文档", description = "更新文档的审核状态")
    DocumentVO auditDocument(
            @Parameter(description = "文档ID") @NotNull Long id,
            @Parameter(description = "审核状态") @NotNull Integer status,
            @Parameter(description = "审核意见") String comment,
            @Parameter(description = "操作人") @NotBlank String operator);
    
    @Operation(summary = "获取文档版本历史", description = "获取文档的所有版本信息")
    List<DocumentVO> getDocumentVersions(
            @Parameter(description = "文档ID") @NotNull Long id);
    
    // =============== 文档查询 ===============
    @Operation(summary = "根据ID查询文档", description = "查询文档详细信息")
    DocumentVO getDocumentById(
            @Parameter(description = "文档ID") @NotNull Long id);
    
    @Operation(summary = "根据状态查询文档", description = "查询指定状态的文档列表")
    List<DocumentVO> getDocumentsByStatus(
            @Parameter(description = "文档状态") @NotNull DocumentStatusEnum status);
    
    @Operation(summary = "分页查询文档", description = "分页查询文档列表")
    PageResult<DocumentVO> pageDocuments(
            @Parameter(description = "分页参数") Page<Document> page,
            @Parameter(description = "查询条件") QueryWrapper<Document> wrapper);
    
    @Operation(summary = "导出文档列表", description = "导出文档列表为Excel")
    byte[] exportDocuments(
            @Parameter(description = "查询条件") @Valid DocumentQuery query);
    
    // =============== 存储服务集成 ===============
    @Operation(summary = "上传文件", description = "上传文件到存储服务")
    FileMetadata uploadFile(
            @Parameter(description = "文件") @NotNull MultipartFile file,
            @Parameter(description = "文档类型") @NotBlank String documentType,
            @Parameter(description = "业务ID") @NotBlank String businessId);
    
    @Operation(summary = "下载文件", description = "从存储服务下载文件")
    InputStream downloadFile(
            @Parameter(description = "文件ID") @NotBlank String fileId);
    
    @Operation(summary = "删除文件", description = "从存储服务删除文件")
    void deleteFile(
            @Parameter(description = "文件ID") @NotBlank String fileId);
    
    @Operation(summary = "获取文件列表", description = "从存储服务获取文件列表")
    List<FileMetadata> listFiles(
            @Parameter(description = "文档类型") @NotBlank String documentType,
            @Parameter(description = "业务ID") @NotBlank String businessId);
} 