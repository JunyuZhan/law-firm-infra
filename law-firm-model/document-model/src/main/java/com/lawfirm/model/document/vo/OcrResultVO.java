package com.lawfirm.model.document.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * OCR识别结果VO
 */
@Data
public class OcrResultVO {
    
    /**
     * 识别ID
     */
    private String recognitionId;
    
    /**
     * 原始文本内容
     */
    private String rawText;
    
    /**
     * 结构化数据（如果启用了结构化处理）
     */
    private Map<String, Object> structuredData;
    
    /**
     * 识别置信度 (0-1)
     */
    private Double confidence;
    
    /**
     * 文档类型
     */
    private String documentType;
    
    /**
     * 使用的OCR引擎
     */
    private String engineType;
    
    /**
     * 处理状态
     * SUCCESS-成功, FAILED-失败, PARTIAL-部分成功
     */
    private String status;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    /**
     * 文字块信息
     */
    private List<TextBlock> textBlocks;
    
    /**
     * 识别时间
     */
    private LocalDateTime recognitionTime;
    
    /**
     * 处理耗时（毫秒）
     */
    private Long processingTime;
    
    /**
     * 原图片信息
     */
    private ImageInfo imageInfo;
    
    /**
     * 文字块信息
     */
    @Data
    public static class TextBlock {
        /**
         * 文字内容
         */
        private String text;
        
        /**
         * 置信度
         */
        private Double confidence;
        
        /**
         * 位置坐标 (x, y, width, height)
         */
        private int[] position;
        
        /**
         * 文字方向角度
         */
        private Double angle;
    }
    
    /**
     * 图片信息
     */
    @Data
    public static class ImageInfo {
        /**
         * 图片宽度
         */
        private Integer width;
        
        /**
         * 图片高度
         */
        private Integer height;
        
        /**
         * 图片大小（字节）
         */
        private Long size;
        
        /**
         * 图片格式
         */
        private String format;
    }
} 