package com.lawfirm.document.service;

import com.lawfirm.document.exception.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 文档预览服务接口
 */
@Tag(name = "文档预览服务", description = "提供文档预览相关功能")
@Validated
public interface DocumentPreviewService {
    
    /**
     * 生成文档预览URL
     *
     * @param id 文档ID
     * @return 预览URL
     * @throws DocumentException 文档异常
     */
    @Operation(summary = "生成预览URL", description = "根据文档ID生成预览URL")
    String generatePreviewUrl(Long id) throws DocumentException;
    
    /**
     * 获取预览内容
     *
     * @param id 文档ID
     * @return 预览内容字节数组
     * @throws DocumentException 文档异常
     */
    @Operation(summary = "获取预览内容", description = "获取文档的预览内容")
    byte[] getPreviewContent(Long id) throws DocumentException;
    
    /**
     * 判断文档是否支持预览
     *
     * @param id 文档ID
     * @return 是否支持预览
     */
    @Operation(summary = "检查预览支持", description = "检查文档是否支持预览")
    boolean isPreviewSupported(Long id);
    
    /**
     * 生成缩略图
     *
     * @param id 文档ID
     * @param width 宽度
     * @param height 高度
     * @return 缩略图字节数组
     * @throws DocumentException 文档异常
     */
    @Operation(summary = "生成缩略图", description = "生成文档的缩略图")
    byte[] generateThumbnail(Long id, Integer width, Integer height) throws DocumentException;
    
    /**
     * 将PDF转换为图片
     *
     * @param pdfBytes PDF文件字节数组
     * @param pageNumber 页码
     * @return 图片字节数组
     * @throws DocumentException 文档异常
     */
    byte[] convertPdfToImage(byte[] pdfBytes, int pageNumber) throws DocumentException;
} 