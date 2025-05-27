package com.lawfirm.auth.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;

/**
 * 验证码工具类
 */
@Slf4j
public class CaptchaUtils {
    
    private static final String CHARS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ"; // 排除容易混淆的字符
    private static final int DEFAULT_WIDTH = 120;
    private static final int DEFAULT_HEIGHT = 40;
    private static final int DEFAULT_LENGTH = 4;
    
    /**
     * 生成随机验证码文本
     * 
     * @param length 验证码长度
     * @return 验证码文本
     */
    public static String generateRandomText(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
    
    /**
     * 生成默认长度的验证码文本
     * 
     * @return 验证码文本
     */
    public static String generateRandomText() {
        return generateRandomText(DEFAULT_LENGTH);
    }
    
    /**
     * 创建验证码图片
     * 
     * @param text 验证码文本
     * @param width 图片宽度
     * @param height 图片高度
     * @return 验证码图片
     */
    public static BufferedImage createCaptchaImage(String text, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        // 设置抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 填充背景色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        // 设置字体
        Font font = new Font("Arial", Font.BOLD, height * 3 / 5);
        g2d.setFont(font);
        
        // 绘制验证码文字
        Random random = new Random();
        int x = width / (text.length() + 1);
        for (int i = 0; i < text.length(); i++) {
            // 随机颜色
            g2d.setColor(new Color(random.nextInt(150), random.nextInt(150), random.nextInt(150)));
            
            // 随机旋转角度
            double angle = (random.nextDouble() - 0.5) * Math.PI / 6;
            int charX = x * (i + 1);
            int charY = height / 2 + 8;
            
            g2d.rotate(angle, charX, charY);
            g2d.drawString(String.valueOf(text.charAt(i)), charX - font.getSize() / 4, charY);
            g2d.rotate(-angle, charX, charY);
        }
        
        // 添加干扰线
        for (int i = 0; i < 5; i++) {
            g2d.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2d.drawLine(x1, y1, x2, y2);
        }
        
        // 添加噪点
        for (int i = 0; i < 50; i++) {
            g2d.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            int dotX = random.nextInt(width);
            int dotY = random.nextInt(height);
            g2d.drawOval(dotX, dotY, 1, 1);
        }
        
        g2d.dispose();
        return image;
    }
    
    /**
     * 创建默认尺寸的验证码图片
     * 
     * @param text 验证码文本
     * @return 验证码图片
     */
    public static BufferedImage createCaptchaImage(String text) {
        return createCaptchaImage(text, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    /**
     * 将图片转换为Base64编码
     * 
     * @param image 图片
     * @param format 图片格式（如"png", "jpg"）
     * @return Base64编码的图片数据
     */
    public static String imageToBase64(BufferedImage image, String format) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, format, baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            log.error("图片转Base64失败", e);
            throw new RuntimeException("图片转换失败", e);
        }
    }
    
    /**
     * 将图片转换为Base64编码（PNG格式）
     * 
     * @param image 图片
     * @return Base64编码的图片数据
     */
    public static String imageToBase64(BufferedImage image) {
        return imageToBase64(image, "png");
    }
    
    /**
     * 生成完整的验证码数据URL
     * 
     * @param text 验证码文本
     * @return 验证码图片的data URL
     */
    public static String generateCaptchaDataUrl(String text) {
        BufferedImage image = createCaptchaImage(text);
        String base64 = imageToBase64(image);
        return "data:image/png;base64," + base64;
    }
} 