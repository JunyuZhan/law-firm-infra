package com.lawfirm.document.service.impl;

import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.document.service.IDocumentConversionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 文档格式转换服务实现类
 */
@Slf4j
@Service
public class DocumentConversionServiceImpl implements IDocumentConversionService {

    private static final Set<String> SUPPORTED_FORMATS = new HashSet<>(Arrays.asList(
        "doc", "docx", "pdf", "txt", "html"
    ));

    @Override
    public InputStream convert(InputStream source, String sourceFormat, String targetFormat) {
        if (!isConversionSupported(sourceFormat, targetFormat)) {
            throw new BusinessException("不支持的格式转换: " + sourceFormat + " -> " + targetFormat);
        }

        try {
            // 根据不同的格式组合调用不同的转换方法
            if ("pdf".equals(targetFormat)) {
                return convertToPdf(source, sourceFormat);
            } else if ("html".equals(targetFormat)) {
                String html = generatePreview(source, sourceFormat);
                return new ByteArrayInputStream(html.getBytes());
            } else if ("txt".equals(targetFormat)) {
                String text = extractText(source, sourceFormat);
                return new ByteArrayInputStream(text.getBytes());
            }
            
            throw new BusinessException("暂不支持的转换类型");
        } catch (IOException e) {
            log.error("文档转换失败", e);
            throw new BusinessException("文档转换失败");
        }
    }

    @Override
    public String generatePreview(InputStream source, String sourceFormat) {
        try {
            String text = extractText(source, sourceFormat);
            // 将文本内容转换为简单的HTML预览
            return String.format(
                "<html><body><pre>%s</pre></body></html>",
                text.replace("<", "&lt;").replace(">", "&gt;")
            );
        } catch (IOException e) {
            log.error("生成预览失败", e);
            throw new BusinessException("生成预览失败");
        }
    }

    @Override
    public String extractText(InputStream source, String sourceFormat) throws IOException {
        switch (sourceFormat.toLowerCase()) {
            case "pdf":
                return extractPdfText(source);
            case "doc":
                return extractDocText(source);
            case "docx":
                return extractDocxText(source);
            case "txt":
                return new String(source.readAllBytes());
            default:
                throw new BusinessException("不支持的文件格式: " + sourceFormat);
        }
    }

    @Override
    public boolean isConversionSupported(String sourceFormat, String targetFormat) {
        return SUPPORTED_FORMATS.contains(sourceFormat.toLowerCase()) &&
               SUPPORTED_FORMATS.contains(targetFormat.toLowerCase()) &&
               !sourceFormat.equalsIgnoreCase(targetFormat);
    }

    private String extractPdfText(InputStream source) throws IOException {
        try (PDDocument document = PDDocument.load(source)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String extractDocText(InputStream source) throws IOException {
        try (HWPFDocument document = new HWPFDocument(source)) {
            WordExtractor extractor = new WordExtractor(document);
            return extractor.getText();
        }
    }

    private String extractDocxText(InputStream source) throws IOException {
        try (XWPFDocument document = new XWPFDocument(source)) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            return extractor.getText();
        }
    }

    private InputStream convertToPdf(InputStream source, String sourceFormat) throws IOException {
        // TODO: 实现文档转PDF的功能
        // 可以使用 Apache POI + iText 或其他工具实现
        throw new BusinessException("PDF转换功能尚未实现");
    }
} 