package com.lawfirm.document.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.document.query.DocumentQuery;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.model.document.enums.DocumentStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "文档管理服务", description = "提供文档的基本操作和版本管理功能")
@Validated
public interface DocumentService extends IService<Document> {
    
    @Operation(summary = "上传文档", description = "上传新文档，自动生成版本号")
    DocumentVO upload(
            @Parameter(description = "文档文件") @NotNull(message = "文档文件不能为空") MultipartFile file,
            @Parameter(description = "文档信息") @Valid Document document,
            @Parameter(description = "操作人") @NotBlank(message = "操作人不能为空") String operator);
    
    @Operation(summary = "批量上传文档", description = "批量上传新文档，自动生成版本号")
    List<DocumentVO> batchUpload(
            @Parameter(description = "文档文件列表") @NotEmpty(message = "文档文件列表不能为空") List<MultipartFile> files,
            @Parameter(description = "文档信息列表") @NotEmpty(message = "文档信息列表不能为空") List<Document> documents,
            @Parameter(description = "操作人") @NotBlank(message = "操作人不能为空") String operator);
    
    @Operation(summary = "更新文档", description = "更新文档信息和内容，自动生成新版本")
    DocumentVO update(
            @Parameter(description = "文档ID") @NotNull(message = "文档ID不能为空") Long id,
            @Parameter(description = "文档文件") MultipartFile file,
            @Parameter(description = "文档信息") @Valid Document document,
            @Parameter(description = "操作人") @NotBlank(message = "操作人不能为空") String operator);
    
    @Operation(summary = "下载文档", description = "下载指定版本的文档")
    byte[] download(
            @Parameter(description = "文档ID") @NotNull(message = "文档ID不能为空") Long id,
            @Parameter(description = "版本号") String version);
    
    @Operation(summary = "删除文档", description = "删除文档及其所有版本")
    void delete(
            @Parameter(description = "文档ID") @NotNull(message = "文档ID不能为空") Long id,
            @Parameter(description = "操作人") @NotBlank(message = "操作人不能为空") String operator);
    
    @Operation(summary = "审核文档", description = "更新文档的审核状态")
    DocumentVO audit(
            @Parameter(description = "文档ID") @NotNull(message = "文档ID不能为空") Long id,
            @Parameter(description = "审核状态") @NotNull(message = "审核状态不能为空") Integer status,
            @Parameter(description = "审核意见") String comment,
            @Parameter(description = "操作人") @NotBlank(message = "操作人不能为空") String operator);
    
    @Operation(summary = "获取文档版本历史", description = "获取文档的所有版本信息")
    List<DocumentVO> getVersionHistory(
            @Parameter(description = "文档ID") @NotNull(message = "文档ID不能为空") Long id);
    
    @Operation(summary = "导出文档列表", description = "将文档列表导出为Excel")
    byte[] exportToExcel(
            @Parameter(description = "查询条件") @Valid DocumentQuery query);

    @Operation(summary = "根据状态查询文档", description = "根据文档状态查询文档列表")
    List<DocumentVO> listByStatus(
            @Parameter(description = "文档状态") @NotNull(message = "文档状态不能为空") DocumentStatusEnum status);

    @Operation(summary = "分页查询文档", description = "分页查询文档列表")
    PageResult<DocumentVO> pageDocuments(Page<Document> page, QueryWrapper<Document> wrapper);

    @Operation(summary = "查询文档列表", description = "查询文档列表")
    List<DocumentVO> listDocuments(QueryWrapper<Document> wrapper);

    @Operation(summary = "根据ID查询文档", description = "根据ID查询文档")
    DocumentVO findById(Long id);

    @Operation(summary = "创建文档", description = "创建文档")
    DocumentVO create(DocumentVO dto);

    @Operation(summary = "更新文档", description = "更新文档")
    DocumentVO update(DocumentVO dto);

    @Operation(summary = "批量删除文档", description = "批量删除文档")
    void deleteBatch(List<Long> ids);
} 