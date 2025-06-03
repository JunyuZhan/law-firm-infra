package com.lawfirm.document.service.ocr;

import com.lawfirm.model.document.vo.OcrResultVO;

/**
 * OCR识别引擎接口
 */
public interface OcrEngine {
    
    /**
     * 执行OCR识别
     * 
     * @param imageData 图像数据
     * @param language 识别语言
     * @param documentType 文档类型
     * @return 识别结果
     */
    OcrResultVO recognize(byte[] imageData, String language, String documentType);
    
    /**
     * 获取引擎类型
     * 
     * @return 引擎类型标识
     */
    String getEngineType();
    
    /**
     * 检查引擎是否可用
     * 
     * @return 是否可用
     */
    boolean isAvailable();
} 