package com.lawfirm.analysis.model.vo;

import lombok.Data;
import java.util.Map;
import java.util.List;

/**
 * 分析结果VO
 */
@Data
public class AnalysisResultVO {
    
    /**
     * 分析维度
     */
    private List<String> dimensions;
    
    /**
     * 分析指标
     */
    private List<String> metrics;
    
    /**
     * 数据系列
     */
    private List<Series> series;
    
    /**
     * 汇总数据
     */
    private Map<String, Object> summary;
    
    @Data
    public static class Series {
        /**
         * 系列名称
         */
        private String name;
        
        /**
         * 系列数据
         */
        private List<Object> data;
        
        /**
         * 系列类型
         */
        private String type;
    }
} 