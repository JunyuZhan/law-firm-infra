package com.lawfirm.auth.service.impl;

import com.lawfirm.auth.config.LoginConfig;
import com.lawfirm.auth.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现类
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private LoginConfig loginConfig;

    private static final String CAPTCHA_CODE_KEY = "captcha:code:";
    private static final String CAPTCHA_CHARS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final int WIDTH = 100;
    private static final int HEIGHT = 40;

    @Override
    public CaptchaResult generateCaptcha() {
        // 生成随机验证码
        String code = generateCode();
        
        // 生成验证码图片
        BufferedImage image = generateImage(code);
        
        // 生成唯一key
        String key = UUID.randomUUID().toString();
        
        // 将验证码保存到Redis
        redisTemplate.opsForValue().set(
            CAPTCHA_CODE_KEY + key,
            code,
            loginConfig.getCaptchaExpire(),
            TimeUnit.MINUTES
        );
        
        return new CaptchaResult(image, code, key);
    }

    @Override
    public boolean validateCaptcha(String key, String code) {
        if (key == null || code == null) {
            return false;
        }
        
        String cacheCode = (String) redisTemplate.opsForValue().get(CAPTCHA_CODE_KEY + key);
        if (cacheCode == null) {
            return false;
        }
        
        // 验证后删除验证码
        redisTemplate.delete(CAPTCHA_CODE_KEY + key);
        
        return code.equalsIgnoreCase(cacheCode);
    }

    /**
     * 生成随机验证码
     */
    private String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(CAPTCHA_CHARS.charAt(random.nextInt(CAPTCHA_CHARS.length())));
        }
        return code.toString();
    }

    /**
     * 生成验证码图片
     */
    private BufferedImage generateImage(String code) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        
        // 设置背景色
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        
        // 设置字体
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        
        // 添加干扰线
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            g2.setColor(getRandColor(160, 200));
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g2.drawLine(x1, y1, x2, y2);
        }
        
        // 添加噪点
        for (int i = 0; i < 60; i++) {
            g2.setColor(getRandColor(160, 200));
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            g2.drawOval(x, y, 1, 1);
        }
        
        // 绘制验证码
        for (int i = 0; i < code.length(); i++) {
            g2.setColor(getRandColor(0, 60));
            g2.drawString(String.valueOf(code.charAt(i)), 20 * i + 10, 30);
        }
        
        g2.dispose();
        return image;
    }

    /**
     * 获取随机颜色
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
} 