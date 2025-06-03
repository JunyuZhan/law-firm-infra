package com.lawfirm.document.service.ocr.impl;

import com.baidu.aip.ocr.AipOcr;
import com.lawfirm.document.service.ocr.OcrEngine;
import com.lawfirm.model.document.vo.OcrResultVO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 百度OCR引擎实现
 */
@Slf4j
@Service("baiduOcrEngine")  // 指定bean名称
public class BaiduOcrEngineImpl implements OcrEngine {
    
    @Value("${ocr.baidu.app-id:}")
    private String appId;
    
    @Value("${ocr.baidu.api-key:}")
    private String apiKey;
    
    @Value("${ocr.baidu.secret-key:}")
    private String secretKey;
    
    private AipOcr client;
    
    @PostConstruct
    public void init() {
        if (isConfigured()) {
            client = new AipOcr(appId, apiKey, secretKey);
            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);
            log.info("百度OCR引擎初始化成功");
        } else {
            log.warn("百度OCR配置不完整，引擎不可用");
        }
    }
    
    @Override
    public OcrResultVO recognize(byte[] imageData, String language, String documentType) {
        if (!isAvailable()) {
            throw new IllegalStateException("百度OCR引擎不可用");
        }
        
        try {
            // 根据文档类型选择不同的识别接口
            JSONObject result = switch (documentType) {
                case "id_card" -> client.idcard(imageData, "front", new HashMap<>());
                case "business_license" -> client.businessLicense(imageData, new HashMap<>());
                case "bank_card" -> client.bankcard(imageData, new HashMap<>());
                default -> client.basicGeneral(imageData, new HashMap<>());
            };
            
            return parseResult(result);
            
        } catch (Exception e) {
            log.error("百度OCR识别失败", e);
            OcrResultVO errorResult = new OcrResultVO();
            errorResult.setStatus("FAILED");
            errorResult.setErrorMessage("百度OCR识别失败: " + e.getMessage());
            return errorResult;
        }
    }
    
    @Override
    public String getEngineType() {
        return "baidu";
    }
    
    @Override
    public boolean isAvailable() {
        return client != null && isConfigured();
    }
    
    /**
     * 检查配置是否完整
     */
    private boolean isConfigured() {
        return appId != null && !appId.trim().isEmpty() &&
               apiKey != null && !apiKey.trim().isEmpty() &&
               secretKey != null && !secretKey.trim().isEmpty();
    }
    
    /**
     * 解析百度OCR返回结果
     */
    private OcrResultVO parseResult(JSONObject result) {
        OcrResultVO ocrResult = new OcrResultVO();
        
        if (result.has("error_code")) {
            ocrResult.setStatus("FAILED");
            ocrResult.setErrorMessage("百度OCR错误: " + result.optString("error_msg"));
            return ocrResult;
        }
        
        // 提取文字内容
        StringBuilder rawText = new StringBuilder();
        List<OcrResultVO.TextBlock> textBlocks = new ArrayList<>();
        
        if (result.has("words_result")) {
            JSONArray wordsResult = result.getJSONArray("words_result");
            
            for (int i = 0; i < wordsResult.length(); i++) {
                JSONObject word = wordsResult.getJSONObject(i);
                String text = word.optString("words");
                
                if (!text.isEmpty()) {
                    rawText.append(text).append("\n");
                    
                    // 创建文字块
                    OcrResultVO.TextBlock textBlock = new OcrResultVO.TextBlock();
                    textBlock.setText(text);
                    
                    // 如果有位置信息
                    if (word.has("location")) {
                        JSONObject location = word.getJSONObject("location");
                        textBlock.setPosition(new int[]{
                            location.optInt("left"),
                            location.optInt("top"),
                            location.optInt("width"),
                            location.optInt("height")
                        });
                    }
                    
                    // 如果有置信度信息
                    if (word.has("probability")) {
                        JSONObject probability = word.getJSONObject("probability");
                        textBlock.setConfidence(probability.optDouble("average"));
                    }
                    
                    textBlocks.add(textBlock);
                }
            }
        }
        
        ocrResult.setRawText(rawText.toString().trim());
        ocrResult.setTextBlocks(textBlocks);
        ocrResult.setStatus("SUCCESS");
        
        // 计算平均置信度
        double avgConfidence = textBlocks.stream()
                .mapToDouble(block -> block.getConfidence() != null ? block.getConfidence() : 0.0)
                .average()
                .orElse(0.0);
        ocrResult.setConfidence(avgConfidence);
        
        return ocrResult;
    }
} 