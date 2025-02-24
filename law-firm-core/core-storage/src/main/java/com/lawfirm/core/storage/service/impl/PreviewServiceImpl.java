package com.lawfirm.core.storage.service.impl;

import com.lawfirm.model.base.storage.model.FileMetadata;
import com.lawfirm.model.base.storage.model.PreviewInfo;
import com.lawfirm.model.base.storage.service.PreviewService;
import com.lawfirm.model.base.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 预览服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PreviewServiceImpl implements PreviewService {

    private final StorageService storageService;

    @Override
    public PreviewInfo generatePreview(String fileId, String previewType, String params) {
        log.info("开始生成预览, fileId={}, previewType={}, params={}", fileId, previewType, params);
        try {
            byte[] fileData = storageService.downloadFile(fileId);
            PreviewInfo previewInfo = new PreviewInfo()
                .setFileId(fileId)
                .setPreviewType(previewType)
                .setParams(params)
                .setStatus("GENERATING");

            switch (previewType.toUpperCase()) {
                case "PDF":
                    return convertToPdf(fileId);
                case "IMAGE":
                    return generateThumbnail(fileId, 800, 600);
                default:
                    throw new IllegalArgumentException("不支持的预览类型: " + previewType);
            }
        } catch (Exception e) {
            log.error("生成预览失败, fileId=" + fileId, e);
            return new PreviewInfo()
                .setFileId(fileId)
                .setStatus("FAILED")
                .setError(e.getMessage());
        }
    }

    @Override
    public PreviewInfo getPreviewInfo(String fileId) {
        log.info("获取预览信息, fileId={}", fileId);
        try {
            // 从存储中获取预览信息
            return new PreviewInfo()
                .setFileId(fileId)
                .setStatus("READY");
        } catch (Exception e) {
            log.error("获取预览信息失败, fileId=" + fileId, e);
            return new PreviewInfo()
                .setFileId(fileId)
                .setStatus("FAILED")
                .setError(e.getMessage());
        }
    }

    @Override
    public PreviewInfo generateThumbnail(String fileId, Integer width, Integer height) {
        log.info("生成缩略图, fileId={}, width={}, height={}", fileId, width, height);
        try {
            byte[] fileData = storageService.downloadFile(fileId);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(fileData));
            
            // 缩放图片
            BufferedImage thumbnail = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            thumbnail.getGraphics().drawImage(image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, "jpg", baos);
            byte[] thumbnailData = baos.toByteArray();
            
            FileMetadata thumbnailMetadata = storageService.uploadFile("thumbnail.jpg", 
                new ByteArrayInputStream(thumbnailData), 
                thumbnailData.length, 
                "image/jpeg");
            
            return new PreviewInfo()
                .setFileId(fileId)
                .setThumbnailUrl(thumbnailMetadata.getPath())
                .setWidth(width)
                .setHeight(height)
                .setStatus("READY");
        } catch (Exception e) {
            log.error("生成缩略图失败, fileId=" + fileId, e);
            return new PreviewInfo()
                .setFileId(fileId)
                .setStatus("FAILED")
                .setError(e.getMessage());
        }
    }

    @Override
    public PreviewInfo addWatermark(String fileId, String watermark) {
        log.info("添加水印, fileId={}, watermark={}", fileId, watermark);
        try {
            // TODO: 实现水印添加逻辑
            return new PreviewInfo()
                .setFileId(fileId)
                .setWatermark(watermark)
                .setStatus("READY");
        } catch (Exception e) {
            log.error("添加水印失败, fileId=" + fileId, e);
            return new PreviewInfo()
                .setFileId(fileId)
                .setStatus("FAILED")
                .setError(e.getMessage());
        }
    }

    @Override
    public PreviewInfo convertToPdf(String fileId) {
        log.info("转换为PDF, fileId={}", fileId);
        try {
            byte[] fileData = storageService.downloadFile(fileId);
            // TODO: 实现PDF转换逻辑
            return new PreviewInfo()
                .setFileId(fileId)
                .setPreviewType("PDF")
                .setStatus("READY");
        } catch (Exception e) {
            log.error("转换PDF失败, fileId=" + fileId, e);
            return new PreviewInfo()
                .setFileId(fileId)
                .setStatus("FAILED")
                .setError(e.getMessage());
        }
    }

    @Override
    public Integer getPageCount(String fileId) {
        log.info("获取文档页数, fileId={}", fileId);
        try {
            byte[] fileData = storageService.downloadFile(fileId);
            try (PDDocument document = PDDocument.load(new ByteArrayInputStream(fileData))) {
                return document.getNumberOfPages();
            }
        } catch (Exception e) {
            log.error("获取页数失败, fileId=" + fileId, e);
            return 0;
        }
    }

    @Override
    public PreviewInfo getPagePreview(String fileId, Integer pageNumber) {
        log.info("获取页面预览, fileId={}, pageNumber={}", fileId, pageNumber);
        try {
            byte[] fileData = storageService.downloadFile(fileId);
            try (PDDocument document = PDDocument.load(new ByteArrayInputStream(fileData))) {
                PDFRenderer renderer = new PDFRenderer(document);
                BufferedImage image = renderer.renderImageWithDPI(pageNumber - 1, 150);
                
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                byte[] previewData = baos.toByteArray();
                
                FileMetadata previewMetadata = storageService.uploadFile("preview.png", 
                    new ByteArrayInputStream(previewData), 
                    previewData.length, 
                    "image/png");
                
                return new PreviewInfo()
                    .setFileId(fileId)
                    .setPageNumber(pageNumber)
                    .setPreviewUrl(previewMetadata.getPath())
                    .setStatus("READY");
            }
        } catch (Exception e) {
            log.error("获取页面预览失败, fileId=" + fileId, e);
            return new PreviewInfo()
                .setFileId(fileId)
                .setStatus("FAILED")
                .setError(e.getMessage());
        }
    }
} 