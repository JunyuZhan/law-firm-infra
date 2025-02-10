package com.lawfirm.document.service.impl;

import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.document.service.IDocumentConversionService;
import com.lawfirm.model.document.enums.ConversionTaskStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文档转换服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentConversionServiceImpl implements IDocumentConversionService {
    
    private final Map<String, ConversionTask> taskMap = new ConcurrentHashMap<>();
    private final Set<String> supportedSourceFormats = new HashSet<>(Arrays.asList(
            "DOC", "DOCX", "XLS", "XLSX", "PPT", "PPTX", "PDF", "TXT", "RTF"));
    private final Set<String> supportedTargetFormats = new HashSet<>(Arrays.asList(
            "PDF", "HTML", "TXT", "PNG", "JPG"));
    
    @Override
    @Async("conversionTaskExecutor")
    public CompletableFuture<String> convertAsync(Long documentId, String sourceFormat, 
            String targetFormat) {
        if (!isConversionSupported(sourceFormat, targetFormat)) {
            throw new IllegalArgumentException("不支持的转换格式");
        }
        
        String taskId = UUID.randomUUID().toString();
        ConversionTask task = new ConversionTask(documentId, sourceFormat, targetFormat);
        taskMap.put(taskId, task);
        
        try {
            // TODO: 调用具体的转换实现
            task.setStatus(ConversionTaskStatus.CONVERTING);
            Thread.sleep(1000); // 模拟转换过程
            task.setProgress(100);
            task.setStatus(ConversionTaskStatus.COMPLETED);
            
            return CompletableFuture.completedFuture(taskId);
        } catch (Exception e) {
            task.setStatus(ConversionTaskStatus.FAILED);
            task.setErrorMessage(e.getMessage());
            log.error("文档转换失败: documentId={}, sourceFormat={}, targetFormat={}", 
                    documentId, sourceFormat, targetFormat, e);
            return CompletableFuture.failedFuture(e);
        }
    }
    
    @Override
    public double getConversionProgress(String taskId) {
        ConversionTask task = taskMap.get(taskId);
        return task != null ? task.getProgress() : 0;
    }
    
    @Override
    public void cancelConversion(String taskId) {
        ConversionTask task = taskMap.get(taskId);
        if (task != null && task.getStatus() == ConversionTaskStatus.CONVERTING) {
            task.setStatus(ConversionTaskStatus.CANCELLED);
            // TODO: 实现具体的取消逻辑
        }
    }
    
    @Override
    public List<String> getSupportedSourceFormats() {
        return new ArrayList<>(supportedSourceFormats);
    }
    
    @Override
    public List<String> getSupportedTargetFormats() {
        return new ArrayList<>(supportedTargetFormats);
    }
    
    @Override
    public boolean isConversionSupported(String sourceFormat, String targetFormat) {
        return supportedSourceFormats.contains(sourceFormat.toUpperCase()) &&
                supportedTargetFormats.contains(targetFormat.toUpperCase());
    }
    
    @Override
    public ConversionTaskStatus getTaskStatus(String taskId) {
        ConversionTask task = taskMap.get(taskId);
        return task != null ? task.getStatus() : null;
    }
    
    @Override
    public void cleanExpiredTasks(int days) {
        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(days);
        taskMap.entrySet().removeIf(entry -> {
            ConversionTask task = entry.getValue();
            return task.getCreateTime().isBefore(cutoffTime) &&
                    task.getStatus() != ConversionTaskStatus.CONVERTING;
        });
    }
    
    @Data
    @Accessors(chain = true)
    private static class ConversionTask {
        private final Long documentId;
        private final String sourceFormat;
        private final String targetFormat;
        private final LocalDateTime createTime;
        private ConversionTaskStatus status;
        private double progress;
        private String errorMessage;
        
        public ConversionTask(Long documentId, String sourceFormat, String targetFormat) {
            this.documentId = documentId;
            this.sourceFormat = sourceFormat;
            this.targetFormat = targetFormat;
            this.createTime = LocalDateTime.now();
            this.status = ConversionTaskStatus.PENDING;
            this.progress = 0;
        }
    }

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