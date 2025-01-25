package com.lawfirm.document.service.impl;

import com.lawfirm.document.service.DocumentPreviewService;
import com.lawfirm.document.service.DocumentService;
import com.lawfirm.model.document.entity.Document;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Graphics2D;
import java.awt.image.RenderingHints;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentPreviewServiceImpl implements DocumentPreviewService {

    private final DocumentService documentService;
    private final OfficeDocumentConverter officeConverter;
    
    @Value("${document.preview.supported-types}")
    private String supportedTypes;
    
    @Value("${document.preview.url-prefix}")
    private String previewUrlPrefix;
    
    @Value("${document.preview.cache-enabled:true}")
    private boolean cacheEnabled;
    
    @Value("${document.preview.cache-max-size:1000}")
    private int cacheMaxSize;
    
    @Value("${document.preview.cache-ttl:3600}")
    private int cacheTtl;

    private static final Set<String> PREVIEW_SUPPORTED_TYPES = new HashSet<>(Arrays.asList(
        "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt", "jpg", "jpeg", "png"
    ));

    @Cacheable(value = "document_preview", key = "#id")
    @Override
    public String generatePreviewUrl(Long id) {
        Document document = documentService.getById(id);
        if (document == null) {
            throw new DocumentException(DocumentException.ERROR_DOCUMENT_NOT_FOUND, "文档不存在");
        }
        
        if (!isPreviewSupported(id)) {
            throw new DocumentException(DocumentException.ERROR_PREVIEW_NOT_SUPPORTED, "文档类型不支持预览");
        }
        
        return previewUrlPrefix + "/preview/" + id;
    }

    @Cacheable(value = "document_preview_content", key = "#id", 
              condition = "#root.target.cacheEnabled", 
              unless = "#result == null")
    @Override
    public byte[] getPreviewContent(Long id) {
        Document document = documentService.getById(id);
        if (document == null) {
            throw new DocumentException(DocumentException.ERROR_DOCUMENT_NOT_FOUND, "文档不存在");
        }
        
        try {
            Path filePath = Paths.get(document.getFilePath());
            String fileType = document.getFileType().toLowerCase();
            
            // 对于PDF文件，直接返回
            if ("pdf".equals(fileType)) {
                return Files.readAllBytes(filePath);
            }
            
            // 对于图片文件，直接返回
            if (isImageFile(fileType)) {
                return Files.readAllBytes(filePath);
            }
            
            // 对于Office文档，转换为PDF
            if (isOfficeFile(fileType)) {
                return officeConverter.convertToPdf(filePath.toFile(), fileType);
            }
            
            throw new DocumentException(DocumentException.ERROR_PREVIEW_NOT_SUPPORTED, "不支持的文件类型预览");
            
        } catch (IOException e) {
            log.error("获取预览内容失败", e);
            throw new DocumentException(DocumentException.ERROR_STORAGE_FAILED, "获取预览内容失败", e);
        }
    }

    @Cacheable(value = "document_preview_support", key = "#id")
    @Override
    public boolean isPreviewSupported(Long id) {
        Document document = documentService.getById(id);
        if (document == null) {
            return false;
        }
        
        String fileType = document.getFileType();
        if (fileType == null) {
            return false;
        }
        
        return PREVIEW_SUPPORTED_TYPES.contains(fileType.toLowerCase());
    }

    @Cacheable(value = "document_thumbnail", key = "#id + '_' + #width + '_' + #height",
              condition = "#root.target.cacheEnabled",
              unless = "#result == null")
    @Override
    public byte[] generateThumbnail(Long id, Integer width, Integer height) {
        Document document = documentService.getById(id);
        if (document == null) {
            throw new DocumentException(DocumentException.ERROR_DOCUMENT_NOT_FOUND, "文档不存在");
        }
        
        // 验证缩略图尺寸
        width = width == null ? DocumentConstant.Thumbnail.DEFAULT_WIDTH : width;
        height = height == null ? DocumentConstant.Thumbnail.DEFAULT_HEIGHT : height;
        
        if (width > DocumentConstant.Thumbnail.MAX_WIDTH || height > DocumentConstant.Thumbnail.MAX_HEIGHT) {
            throw new DocumentException(DocumentException.ERROR_PREVIEW_NOT_SUPPORTED, 
                String.format("缩略图尺寸超过限制：最大%dx%d", 
                    DocumentConstant.Thumbnail.MAX_WIDTH, 
                    DocumentConstant.Thumbnail.MAX_HEIGHT));
        }
        
        try {
            String fileType = document.getFileType().toLowerCase();
            Path filePath = Paths.get(document.getFilePath());
            
            // 处理PDF文件
            if ("pdf".equals(fileType)) {
                return generatePdfThumbnail(filePath.toFile(), width, height);
            }
            
            // 处理图片文件
            if (isImageFile(fileType)) {
                return generateImageThumbnail(filePath.toFile(), width, height);
            }
            
            // 处理Office文档
            if (isOfficeFile(fileType)) {
                // 先转换为PDF，再生成缩略图
                byte[] pdfContent = officeConverter.convertToPdf(filePath.toFile(), fileType);
                File tempPdf = File.createTempFile("preview_", ".pdf");
                try {
                    Files.write(tempPdf.toPath(), pdfContent);
                    return generatePdfThumbnail(tempPdf, width, height);
                } finally {
                    tempPdf.delete();
                }
            }
            
            throw new DocumentException(DocumentException.ERROR_PREVIEW_NOT_SUPPORTED, "不支持的文件类型缩略图生成");
            
        } catch (IOException e) {
            log.error("生成缩略图失败", e);
            throw new DocumentException(DocumentException.ERROR_STORAGE_FAILED, "生成缩略图失败", e);
        }
    }
    
    private boolean isImageFile(String fileType) {
        return Arrays.asList("jpg", "jpeg", "png", "gif").contains(fileType.toLowerCase());
    }
    
    private boolean isOfficeFile(String fileType) {
        return Arrays.asList("doc", "docx", "xls", "xlsx", "ppt", "pptx").contains(fileType.toLowerCase());
    }
    
    private byte[] generatePdfThumbnail(File pdfFile, Integer width, Integer height) throws IOException {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage image = pdfRenderer.renderImageWithDPI(0, 72); // 渲染第一页
            
            // 调整图片大小
            if (width != null && height != null) {
                image = resizeImage(image, width, height);
            }
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        }
    }
    
    private byte[] generateImageThumbnail(File imageFile, Integer width, Integer height) throws IOException {
        BufferedImage originalImage = ImageIO.read(imageFile);
        
        // 调整图片大小
        if (width != null && height != null) {
            originalImage = resizeImage(originalImage, width, height);
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", baos);
        return baos.toByteArray();
    }
    
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        double widthRatio = (double) targetWidth / originalImage.getWidth();
        double heightRatio = (double) targetHeight / originalImage.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalImage.getWidth() * ratio);
        int newHeight = (int) (originalImage.getHeight() * ratio);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        
        // 设置图片缩放算法
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制缩放后的图片
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        
        return resizedImage;
    }
} 