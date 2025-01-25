package com.lawfirm.document.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Tag(name = "文档预览服务", description = "提供文档预览相关功能")
@Validated
public interface DocumentPreviewService {
    
    @Operation(summary = "生成预览URL", description = "根据文档ID生成预览URL")
    String generatePreviewUrl(
            @Parameter(description = "文档ID") @NotNull(message = "文档ID不能为空") Long id);
    
    @Operation(summary = "获取预览内容", description = "获取文档的预览内容")
    byte[] getPreviewContent(
            @Parameter(description = "文档ID") @NotNull(message = "文档ID不能为空") Long id);
            
    @Operation(summary = "检查预览支持", description = "检查文档是否支持预览")
    boolean isPreviewSupported(
            @Parameter(description = "文档ID") @NotNull(message = "文档ID不能为空") Long id);
            
    @Operation(summary = "生成缩略图", description = "生成文档的缩略图")
    byte[] generateThumbnail(
            @Parameter(description = "文档ID") @NotNull(message = "文档ID不能为空") Long id,
            @Parameter(description = "缩略图宽度") Integer width,
            @Parameter(description = "缩略图高度") Integer height);
} 