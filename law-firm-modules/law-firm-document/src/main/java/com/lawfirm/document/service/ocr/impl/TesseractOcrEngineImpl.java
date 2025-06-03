package com.lawfirm.document.service.ocr.impl;

import com.lawfirm.document.service.ocr.OcrEngine;
import com.lawfirm.model.document.vo.OcrResultVO;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Tesseract开源OCR引擎实现
 * 完全免费，本地识别，无API调用限制
 */
@Slf4j
@Service("tesseractOcrEngine")  // 指定bean名称
public class TesseractOcrEngineImpl implements OcrEngine {
    
    @Value("${ocr.tesseract.data-path:#{null}}")
    private String dataPath;
    
    @Value("${ocr.tesseract.language:chi_sim+eng}")
    private String defaultLanguage;
    
    private ITesseract tesseract;
    
    @PostConstruct
    public void init() {
        try {
            tesseract = new Tesseract();
            
            // 设置训练数据路径（如果指定）
            if (dataPath != null && !dataPath.trim().isEmpty()) {
                tesseract.setDatapath(dataPath);
            }
            
            // 设置默认语言
            tesseract.setLanguage(defaultLanguage);
            
            // 设置OCR引擎模式
            tesseract.setOcrEngineMode(1);  // LSTM模式，较好的准确性
            tesseract.setPageSegMode(3);    // 自动页面分割
            
            log.info("Tesseract OCR引擎初始化成功，语言: {}", defaultLanguage);
            
        } catch (Exception e) {
            log.error("Tesseract OCR引擎初始化失败", e);
            tesseract = null;
        }
    }
    
    @Override
    public OcrResultVO recognize(byte[] imageData, String language, String documentType) {
        if (!isAvailable()) {
            throw new IllegalStateException("Tesseract OCR引擎不可用");
        }
        
        try {
            // 读取图像
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
            if (image == null) {
                throw new IllegalArgumentException("无法读取图像数据");
            }
            
            // 设置识别语言
            String ocrLanguage = mapLanguage(language);
            tesseract.setLanguage(ocrLanguage);
            
            // 执行OCR识别
            String rawText = tesseract.doOCR(image);
            
            // 构建结果
            OcrResultVO result = new OcrResultVO();
            result.setRawText(rawText != null ? rawText.trim() : "");
            result.setStatus("SUCCESS");
            result.setConfidence(0.8); // Tesseract不提供详细置信度，使用默认值
            
            // 简单的文字块处理
            List<OcrResultVO.TextBlock> textBlocks = createTextBlocks(rawText);
            result.setTextBlocks(textBlocks);
            
            // 设置图片信息
            OcrResultVO.ImageInfo imageInfo = new OcrResultVO.ImageInfo();
            imageInfo.setWidth(image.getWidth());
            imageInfo.setHeight(image.getHeight());
            imageInfo.setSize((long) imageData.length);
            imageInfo.setFormat("IMAGE");
            result.setImageInfo(imageInfo);
            
            return result;
            
        } catch (TesseractException e) {
            log.error("Tesseract OCR识别失败", e);
            OcrResultVO errorResult = new OcrResultVO();
            errorResult.setStatus("FAILED");
            errorResult.setErrorMessage("Tesseract OCR识别失败: " + e.getMessage());
            return errorResult;
            
        } catch (IOException e) {
            log.error("图像读取失败", e);
            OcrResultVO errorResult = new OcrResultVO();
            errorResult.setStatus("FAILED");
            errorResult.setErrorMessage("图像读取失败: " + e.getMessage());
            return errorResult;
        }
    }
    
    @Override
    public String getEngineType() {
        return "tesseract";
    }
    
    @Override
    public boolean isAvailable() {
        return tesseract != null;
    }
    
    /**
     * 映射语言代码
     */
    private String mapLanguage(String language) {
        if (language == null) {
            return defaultLanguage;
        }
        
        return switch (language) {
            case "zh" -> "chi_sim";
            case "en" -> "eng";
            case "zh_en" -> "chi_sim+eng";
            default -> defaultLanguage;
        };
    }
    
    /**
     * 创建简单的文字块
     */
    private List<OcrResultVO.TextBlock> createTextBlocks(String text) {
        List<OcrResultVO.TextBlock> blocks = new ArrayList<>();
        
        if (text != null && !text.trim().isEmpty()) {
            String[] lines = text.split("\n");
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    OcrResultVO.TextBlock block = new OcrResultVO.TextBlock();
                    block.setText(line.trim());
                    block.setConfidence(0.8); // 默认置信度
                    blocks.add(block);
                }
            }
        }
        
        return blocks;
    }
} 