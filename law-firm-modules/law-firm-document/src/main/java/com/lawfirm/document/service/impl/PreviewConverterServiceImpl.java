package com.lawfirm.model.document.service.impl;

import com.lawfirm.model.document.service.PreviewConverterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDImageXObject;
import org.apache.pdfbox.pdmodel.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.stereotype.Service;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.pdfbox.pdmodel.PdfOptions;
import org.apache.pdfbox.pdmodel.PdfConverter;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDImageXObject;
import org.apache.pdfbox.pdmodel.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.geom.Matrix;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 预览格式转换服务实现类
 */
@Slf4j
@Service
public class PreviewConverterServiceImpl implements PreviewConverterService {

    private static final Set<String> SUPPORTED_SOURCE_TYPES = new HashSet<>(Arrays.asList(
            "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "jpg", "jpeg", "png"
    ));

    private static final Set<String> SUPPORTED_TARGET_TYPES = new HashSet<>(Arrays.asList(
            "pdf", "html", "image"
    ));

    @Override
    public void convertToPdf(InputStream input, OutputStream output, String sourceType) {
        try {
            switch (sourceType.toLowerCase()) {
                case "doc":
                case "docx":
                    convertOfficeToPdf(input, output);
                    break;
                case "jpg":
                case "jpeg":
                case "png":
                    convertImageToPdf(input, output);
                    break;
                case "pdf":
                    // 直接复制
                    input.transferTo(output);
                    break;
                default:
                    throw new UnsupportedOperationException("不支持的源文件类型：" + sourceType);
            }
        } catch (Exception e) {
            throw new RuntimeException("转换PDF失败", e);
        }
    }

    @Override
    public void convertToHtml(InputStream input, OutputStream output, String sourceType) {
        try {
            // 先转换为PDF
            ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
            convertToPdf(input, pdfOutput, sourceType);

            // 将PDF转换为HTML
            try (PDDocument pdf = PDDocument.load(pdfOutput.toByteArray())) {
                Writer writer = new OutputStreamWriter(output);
                new PDFDomTree().writeText(pdf, writer);
            }
        } catch (Exception e) {
            throw new RuntimeException("转换HTML失败", e);
        }
    }

    @Override
    public void convertToImage(InputStream input, OutputStream output, String sourceType, Integer pageNumber) {
        try {
            // 先转换为PDF
            ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
            convertToPdf(input, pdfOutput, sourceType);

            // 将PDF转换为图片
            try (PDDocument pdf = PDDocument.load(pdfOutput.toByteArray())) {
                PDFRenderer renderer = new PDFRenderer(pdf);
                BufferedImage image = renderer.renderImageWithDPI(pageNumber - 1, 150);
                ImageIO.write(image, "PNG", output);
            }
        } catch (Exception e) {
            throw new RuntimeException("转换图片失败", e);
        }
    }

    @Override
    public void generateThumbnail(InputStream input, OutputStream output, String sourceType, Integer width, Integer height) {
        try {
            // 如果是PDF，先转换为图片
            if ("pdf".equalsIgnoreCase(sourceType)) {
                ByteArrayOutputStream imageOutput = new ByteArrayOutputStream();
                convertToImage(input, imageOutput, sourceType, 1);
                input = new ByteArrayInputStream(imageOutput.toByteArray());
            }

            // 生成缩略图
            Thumbnails.of(input)
                    .size(width, height)
                    .toOutputStream(output);
        } catch (Exception e) {
            throw new RuntimeException("生成缩略图失败", e);
        }
    }

    @Override
    public void addWatermark(InputStream input, OutputStream output, String watermark) {
        try {
            PDDocument document = PDDocument.load(input);
            
            // 遍历所有页面添加水印
            for (PDPage page : document.getPages()) {
                PDPageContentStream contentStream = new PDPageContentStream(
                    document, page, PDPageContentStream.AppendMode.APPEND, true, true);
                
                // 设置水印文字属性
                contentStream.setFont(PDType1Font.HELVETICA, 30);
                contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
                
                // 获取页面尺寸
                PDRectangle mediaBox = page.getMediaBox();
                float pageWidth = mediaBox.getWidth();
                float pageHeight = mediaBox.getHeight();
                
                // 计算水印位置（对角线放置）
                contentStream.beginText();
                contentStream.setTextMatrix(Matrix.getRotateInstance(
                    Math.PI / 4, pageWidth / 2, pageHeight / 2));
                contentStream.showText(watermark);
                contentStream.endText();
                
                contentStream.close();
            }
            
            document.save(output);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("添加水印失败", e);
        }
    }

    @Override
    public Integer getPageCount(InputStream input, String sourceType) {
        try {
            // 先转换为PDF
            ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
            convertToPdf(input, pdfOutput, sourceType);

            // 获取页数
            try (PDDocument pdf = PDDocument.load(pdfOutput.toByteArray())) {
                return pdf.getNumberOfPages();
            }
        } catch (Exception e) {
            throw new RuntimeException("获取页数失败", e);
        }
    }

    @Override
    public boolean isSupported(String sourceType, String targetType) {
        return SUPPORTED_SOURCE_TYPES.contains(sourceType.toLowerCase()) &&
                SUPPORTED_TARGET_TYPES.contains(targetType.toLowerCase());
    }

    @Override
    public String[] getSupportedSourceTypes() {
        return SUPPORTED_SOURCE_TYPES.toArray(new String[0]);
    }

    @Override
    public String[] getSupportedTargetTypes() {
        return SUPPORTED_TARGET_TYPES.toArray(new String[0]);
    }

    private void convertOfficeToPdf(InputStream input, OutputStream output) {
        try {
            XWPFDocument document = new XWPFDocument(input);
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, output, options);
        } catch (Exception e) {
            throw new RuntimeException("Office文档转PDF失败", e);
        }
    }

    private void convertImageToPdf(InputStream input, OutputStream output) {
        try {
            // 读取图片
            BufferedImage image = ImageIO.read(input);
            if (image == null) {
                throw new IOException("无法读取图片");
            }

            // 创建PDF文档
            try (PDDocument document = new PDDocument()) {
                // 创建PDF页面
                PDPage page = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
                document.addPage(page);

                // 将图片写入PDF
                PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, 
                    toByteArray(input), "image");
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.drawImage(pdImage, 0, 0, image.getWidth(), image.getHeight());
                contentStream.close();

                // 保存PDF
                document.save(output);
            }
        } catch (Exception e) {
            throw new RuntimeException("图片转PDF失败", e);
        }
    }

    private byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n;
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
} 