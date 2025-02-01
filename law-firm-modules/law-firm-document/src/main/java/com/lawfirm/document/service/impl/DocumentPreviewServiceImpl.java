package com.lawfirm.document.service.impl;

import com.lawfirm.document.service.DocumentPreviewService;
import com.lawfirm.document.service.DocumentStorageService;
import com.lawfirm.document.service.DocumentService;
import com.lawfirm.document.exception.DocumentException;
import com.lawfirm.model.document.entity.Document;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DocumentPreviewServiceImpl implements DocumentPreviewService {

    private final DocumentStorageService documentStorageService;
    private final OfficeDocumentConverter officeDocumentConverter;
    private final DocumentService documentService;

    @Value("${document.preview.url-prefix:http://localhost:8080}")
    private String previewUrlPrefix;

    @Value("${document.preview.supported-types:pdf,doc,docx,xls,xlsx,ppt,pptx,jpg,jpeg,png}")
    private String supportedTypes;

    public DocumentPreviewServiceImpl(DocumentStorageService documentStorageService,
                                    OfficeDocumentConverter officeDocumentConverter,
                                    DocumentService documentService) {
        this.documentStorageService = documentStorageService;
        this.officeDocumentConverter = officeDocumentConverter;
        this.documentService = documentService;
    }

    private byte[] generatePdfPreview(byte[] pdfContent, int pageNumber) throws IOException {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfContent))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage image = pdfRenderer.renderImageWithDPI(pageNumber - 1, 150);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", outputStream);
            return outputStream.toByteArray();
        }
    }

    private byte[] generateWordPreview(byte[] wordContent, int pageNumber) throws IOException {
        byte[] pdfContent = officeDocumentConverter.convertWordToPdf(wordContent);
        return generatePdfPreview(pdfContent, pageNumber);
    }

    private byte[] generateExcelPreview(byte[] excelContent, int pageNumber) throws IOException {
        byte[] pdfContent = officeDocumentConverter.convertExcelToPdf(excelContent);
        return generatePdfPreview(pdfContent, pageNumber);
    }

    private byte[] generatePptPreview(byte[] pptContent, int pageNumber) throws IOException {
        byte[] pdfContent = officeDocumentConverter.convertPptToPdf(pptContent);
        return generatePdfPreview(pdfContent, pageNumber);
    }

    private byte[] generateImagePreview(byte[] imageContent) throws IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(imageContent)) {
            BufferedImage originalImage = ImageIO.read(inputStream);
            
            // 如果图片太大，进行缩放
            if (originalImage.getWidth() > 1920 || originalImage.getHeight() > 1080) {
                BufferedImage scaledImage = scaleImage(originalImage, 1920, 1080);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(scaledImage, "PNG", outputStream);
                return outputStream.toByteArray();
            }
            
            return imageContent;
        }
    }

    private BufferedImage scaleImage(BufferedImage originalImage, int maxWidth, int maxHeight) {
        double widthRatio = (double) maxWidth / originalImage.getWidth();
        double heightRatio = (double) maxHeight / originalImage.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalImage.getWidth() * ratio);
        int newHeight = (int) (originalImage.getHeight() * ratio);

        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        scaledImage.createGraphics().drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        return scaledImage;
    }

    private String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return filePath.substring(lastDotIndex + 1);
        }
        throw new DocumentException(DocumentException.ERROR_FILE_TYPE_NOT_SUPPORTED,
                "无法识别文件类型：" + filePath);
    }

    @Override
    public byte[] convertPdfToImage(byte[] pdfContent, int pageNumber) throws DocumentException {
        try {
            return generatePdfPreview(pdfContent, pageNumber);
        } catch (IOException e) {
            log.error("PDF转图片失败", e);
            throw new DocumentException(DocumentException.ERROR_PREVIEW_NOT_SUPPORTED, "PDF转图片失败：" + e.getMessage());
        }
    }

    @Override
    public byte[] generateThumbnail(Long id, Integer width, Integer height) throws DocumentException {
        try {
            // 获取文档内容
            Document document = documentService.getById(id);
            if (document == null) {
                throw new DocumentException(DocumentException.ERROR_DOCUMENT_NOT_FOUND, "文档不存在：" + id);
            }

            byte[] content = documentStorageService.getContent(document.getFilePath());
            String fileExtension = getFileExtension(document.getFilePath()).toLowerCase();

            // 根据文件类型生成缩略图
            byte[] previewContent;
            switch (fileExtension) {
                case "pdf":
                    previewContent = generatePdfPreview(content, 1); // 使用第一页作为缩略图
                    break;
                case "doc":
                case "docx":
                    previewContent = generateWordPreview(content, 1);
                    break;
                case "xls":
                case "xlsx":
                    previewContent = generateExcelPreview(content, 1);
                    break;
                case "ppt":
                case "pptx":
                    previewContent = generatePptPreview(content, 1);
                    break;
                case "jpg":
                case "jpeg":
                case "png":
                    previewContent = content;
                    break;
                default:
                    throw new DocumentException(DocumentException.ERROR_PREVIEW_NOT_SUPPORTED,
                            "不支持的文件类型：" + fileExtension);
            }

            // 生成指定大小的缩略图
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(previewContent)) {
                BufferedImage originalImage = ImageIO.read(inputStream);
                if (originalImage == null) {
                    throw new DocumentException(DocumentException.ERROR_PREVIEW_NOT_SUPPORTED,
                            "无法读取图片内容");
                }

                BufferedImage thumbnail = scaleImage(originalImage, width, height);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(thumbnail, "PNG", outputStream);
                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            log.error("生成缩略图失败: id={}, width={}, height={}", id, width, height, e);
            throw new DocumentException(DocumentException.ERROR_PREVIEW_NOT_SUPPORTED,
                    "生成缩略图失败：" + e.getMessage());
        }
    }

    @Override
    public String generatePreviewUrl(Long id) throws DocumentException {
        Document document = documentService.getById(id);
        if (document == null) {
            throw new DocumentException(DocumentException.ERROR_DOCUMENT_NOT_FOUND, "文档不存在：" + id);
        }
        return previewUrlPrefix + "/preview/" + id;
    }

    @Override
    public byte[] getPreviewContent(Long id) throws DocumentException {
        Document document = documentService.getById(id);
        if (document == null) {
            throw new DocumentException(DocumentException.ERROR_DOCUMENT_NOT_FOUND, "文档不存在：" + id);
        }
        try {
            String fileExtension = getFileExtension(document.getFilePath()).toLowerCase();
            byte[] fileContent = documentStorageService.getContent(document.getFilePath());

            switch (fileExtension) {
                case "pdf":
                    return generatePdfPreview(fileContent, 1);
                case "doc":
                case "docx":
                    return generateWordPreview(fileContent, 1);
                case "xls":
                case "xlsx":
                    return generateExcelPreview(fileContent, 1);
                case "ppt":
                case "pptx":
                    return generatePptPreview(fileContent, 1);
                case "jpg":
                case "jpeg":
                case "png":
                    return generateImagePreview(fileContent);
                default:
                    throw new DocumentException(DocumentException.ERROR_PREVIEW_NOT_SUPPORTED,
                            "不支持的文件类型：" + fileExtension);
            }
        } catch (Exception e) {
            log.error("生成预览失败: id={}", id, e);
            throw new DocumentException(DocumentException.ERROR_PREVIEW_NOT_SUPPORTED,
                    "生成预览失败：" + e.getMessage());
        }
    }

    @Override
    public boolean isPreviewSupported(Long id) {
        Document document = documentService.getById(id);
        if (document == null) {
            return false;
        }
        String fileExtension = getFileExtension(document.getFilePath()).toLowerCase();
        return supportedTypes.contains(fileExtension);
    }
} 