package com.lawfirm.document.service;

import java.io.InputStream;

/**
 * 文档格式转换服务接口
 */
public interface IDocumentConversionService {

    /**
     * 转换文档格式
     *
     * @param source 源文件输入流
     * @param sourceFormat 源文件格式
     * @param targetFormat 目标格式
     * @return 转换后的文件输入流
     */
    InputStream convert(InputStream source, String sourceFormat, String targetFormat);

    /**
     * 生成文档预览
     *
     * @param source 源文件输入流
     * @param sourceFormat 源文件格式
     * @return HTML格式的预览内容
     */
    String generatePreview(InputStream source, String sourceFormat);

    /**
     * 提取文档文本内容
     *
     * @param source 源文件输入流
     * @param sourceFormat 源文件格式
     * @return 提取的文本内容
     */
    String extractText(InputStream source, String sourceFormat);

    /**
     * 检查格式是否支持转换
     *
     * @param sourceFormat 源文件格式
     * @param targetFormat 目标格式
     * @return 是否支持转换
     */
    boolean isConversionSupported(String sourceFormat, String targetFormat);
} 