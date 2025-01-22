package com.lawfirm.common.security.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类
 */
@Slf4j
@Component
public class QrCodeUtils {

    /**
     * 默认宽度
     */
    private static final int DEFAULT_WIDTH = 300;

    /**
     * 默认高度
     */
    private static final int DEFAULT_HEIGHT = 300;

    /**
     * 默认边距
     */
    private static final int DEFAULT_MARGIN = 1;

    /**
     * 生成二维码
     *
     * @param content 内容
     * @return 二维码图片的字节数组
     */
    public byte[] generateQrCode(String content) {
        return generateQrCode(content, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param width   宽度
     * @param height  高度
     * @return 二维码图片的字节数组
     */
    public byte[] generateQrCode(String content, int width, int height) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            // 设置纠错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 设置编码格式
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 设置边距
            hints.put(EncodeHintType.MARGIN, DEFAULT_MARGIN);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            
            return outputStream.toByteArray();
        } catch (WriterException | IOException e) {
            log.error("生成二维码失败", e);
            throw new RuntimeException("生成二维码失败", e);
        }
    }

    /**
     * 生成带Logo的二维码
     *
     * @param content  内容
     * @param logoPath Logo图片路径
     * @return 二维码图片的字节数组
     */
    public byte[] generateQrCodeWithLogo(String content, String logoPath) {
        // TODO: 实现带Logo的二维码生成
        return generateQrCode(content);
    }
} 