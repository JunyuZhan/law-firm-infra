package com.lawfirm.document.service.ocr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 图像预处理器
 * 对图像进行预处理以提高OCR识别精度
 */
@Slf4j
@Component("imagePreprocessor")  // 指定bean名称
public class ImagePreprocessor {
    
    /**
     * 预处理图像
     * 
     * @param imageData 原始图像数据
     * @param documentType 文档类型
     * @return 预处理后的图像数据
     */
    public byte[] preprocess(byte[] imageData, String documentType) {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
            if (image == null) {
                log.warn("无法读取图像数据，返回原始数据");
                return imageData;
            }
            
            // 根据文档类型选择不同的预处理策略
            BufferedImage processedImage = switch (documentType) {
                case "id_card", "business_license" -> preprocessIdCard(image);
                case "contract", "court_document" -> preprocessDocument(image);
                case "evidence" -> preprocessEvidence(image);
                default -> preprocessGeneral(image);
            };
            
            // 转换回字节数组
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(processedImage, "PNG", baos);
            return baos.toByteArray();
            
        } catch (IOException e) {
            log.error("图像预处理失败", e);
            return imageData; // 返回原始数据
        }
    }
    
    /**
     * 身份证/营业执照预处理
     */
    private BufferedImage preprocessIdCard(BufferedImage image) {
        // 1. 调整对比度
        image = adjustContrast(image, 1.2f);
        
        // 2. 去噪
        image = denoise(image);
        
        // 3. 锐化
        image = sharpen(image);
        
        return image;
    }
    
    /**
     * 文档预处理
     */
    private BufferedImage preprocessDocument(BufferedImage image) {
        // 1. 转为灰度图
        image = toGrayscale(image);
        
        // 2. 调整亮度和对比度
        image = adjustBrightness(image, 10);
        image = adjustContrast(image, 1.1f);
        
        // 3. 去噪
        image = denoise(image);
        
        return image;
    }
    
    /**
     * 证据材料预处理
     */
    private BufferedImage preprocessEvidence(BufferedImage image) {
        // 1. 自适应阈值处理
        image = adaptiveThreshold(image);
        
        // 2. 形态学处理
        image = morphologyProcess(image);
        
        return image;
    }
    
    /**
     * 通用预处理
     */
    private BufferedImage preprocessGeneral(BufferedImage image) {
        // 1. 调整对比度
        image = adjustContrast(image, 1.1f);
        
        // 2. 轻微锐化
        image = sharpen(image);
        
        return image;
    }
    
    /**
     * 转为灰度图
     */
    private BufferedImage toGrayscale(BufferedImage image) {
        BufferedImage grayImage = new BufferedImage(
            image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = grayImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return grayImage;
    }
    
    /**
     * 调整亮度
     */
    private BufferedImage adjustBrightness(BufferedImage image, int brightness) {
        BufferedImage brightImage = new BufferedImage(
            image.getWidth(), image.getHeight(), image.getType());
        
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                int r = Math.min(255, Math.max(0, color.getRed() + brightness));
                int g = Math.min(255, Math.max(0, color.getGreen() + brightness));
                int b = Math.min(255, Math.max(0, color.getBlue() + brightness));
                brightImage.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return brightImage;
    }
    
    /**
     * 调整对比度
     */
    private BufferedImage adjustContrast(BufferedImage image, float contrast) {
        BufferedImage contrastImage = new BufferedImage(
            image.getWidth(), image.getHeight(), image.getType());
        
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                int r = (int) Math.min(255, Math.max(0, (color.getRed() - 128) * contrast + 128));
                int g = (int) Math.min(255, Math.max(0, (color.getGreen() - 128) * contrast + 128));
                int b = (int) Math.min(255, Math.max(0, (color.getBlue() - 128) * contrast + 128));
                contrastImage.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return contrastImage;
    }
    
    /**
     * 简单去噪（中值滤波）
     */
    private BufferedImage denoise(BufferedImage image) {
        // 简化实现，实际可以使用更复杂的滤波算法
        return image;
    }
    
    /**
     * 锐化
     */
    private BufferedImage sharpen(BufferedImage image) {
        // 简化实现，实际可以使用卷积核进行锐化
        return image;
    }
    
    /**
     * 自适应阈值处理
     */
    private BufferedImage adaptiveThreshold(BufferedImage image) {
        // 简化实现
        return toGrayscale(image);
    }
    
    /**
     * 形态学处理
     */
    private BufferedImage morphologyProcess(BufferedImage image) {
        // 简化实现
        return image;
    }
} 