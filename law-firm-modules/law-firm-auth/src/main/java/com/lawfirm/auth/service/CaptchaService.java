package com.lawfirm.auth.service;

import java.awt.image.BufferedImage;

/**
 * 验证码服务接口
 */
public interface CaptchaService {

    /**
     * 生成验证码
     *
     * @return 验证码图片和验证码文本
     */
    CaptchaResult generateCaptcha();

    /**
     * 验证验证码
     *
     * @param key 验证码key
     * @param code 用户输入的验证码
     * @return 是否验证通过
     */
    boolean validateCaptcha(String key, String code);

    /**
     * 验证码结果类
     */
    class CaptchaResult {
        private final BufferedImage image;
        private final String text;
        private final String key;

        public CaptchaResult(BufferedImage image, String text, String key) {
            this.image = image;
            this.text = text;
            this.key = key;
        }

        public BufferedImage getImage() {
            return image;
        }

        public String getText() {
            return text;
        }

        public String getKey() {
            return key;
        }
    }
} 