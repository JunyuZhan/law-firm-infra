package com.lawfirm.common.util.image;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ImageUtils {
    
    // 缩放图片
    public static void scale(File sourceFile, File destFile, int width, int height) throws IOException {
        Thumbnails.of(sourceFile)
                .size(width, height)
                .toFile(destFile);
    }
    
    // 按比例缩放
    public static void scaleByRatio(File sourceFile, File destFile, double ratio) throws IOException {
        Thumbnails.of(sourceFile)
                .scale(ratio)
                .toFile(destFile);
    }
    
    // 压缩图片
    public static void compress(File sourceFile, File destFile, float quality) throws IOException {
        Thumbnails.of(sourceFile)
                .scale(1f)
                .outputQuality(quality)
                .toFile(destFile);
    }
    
    // 裁剪图片
    public static void crop(File sourceFile, File destFile, int x, int y, int width, int height) throws IOException {
        Thumbnails.of(sourceFile)
                .sourceRegion(x, y, width, height)
                .scale(1.0)
                .toFile(destFile);
    }
    
    // 旋转图片
    public static void rotate(File sourceFile, File destFile, double angle) throws IOException {
        Thumbnails.of(sourceFile)
                .rotate(angle)
                .scale(1.0)
                .toFile(destFile);
    }
    
    // 添加水印
    public static void addWatermark(File sourceFile, File destFile, String text, Color color, float alpha) throws IOException {
        BufferedImage image = ImageIO.read(sourceFile);
        Graphics2D g2d = image.createGraphics();
        
        // 设置水印文字样式
        g2d.setColor(color);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 30));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        
        // 添加水印文字
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int x = (image.getWidth() - fontMetrics.stringWidth(text)) / 2;
        int y = (image.getHeight() + fontMetrics.getHeight()) / 2;
        g2d.drawString(text, x, y);
        
        g2d.dispose();
        ImageIO.write(image, FileUtil.extName(destFile), destFile);
    }
    
    // 添加图片水印
    public static void addImageWatermark(File sourceFile, File destFile, File watermarkFile, Positions position, float opacity) throws IOException {
        Thumbnails.of(sourceFile)
                .watermark(position, ImageIO.read(watermarkFile), opacity)
                .scale(1.0)
                .toFile(destFile);
    }
    
    // 转换图片格式
    public static void convert(File sourceFile, File destFile) throws IOException {
        String formatName = FileUtil.extName(destFile);
        Thumbnails.of(sourceFile)
                .scale(1.0)
                .outputFormat(formatName)
                .toFile(destFile);
    }
} 