package com.lawfirm.core.search.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索响应
 */
@Data
@Accessors(chain = true)
public class SearchResponse<T> {
    
    /**
     * 总记录数
     */
    private long total;
    
    /**
     * 是否命中
     */
    private boolean hits;
    
    /**
     * 最大评分
     */
    private float maxScore;
    
    /**
     * 耗时（毫秒）
     */
    private long took;
    
    /**
     * 是否超时
     */
    private boolean timedOut;
    
    /**
     * 分片信息
     */
    private ShardInfo shardInfo;
    
    /**
     * 搜索结果
     */
    private List<SearchHit<T>> searchHits = new ArrayList<>();
    
    /**
     * 聚合结果
     */
    private Map<String, AggregationResult> aggregations = new HashMap<>();
    
    /**
     * 建议结果
     */
    private Map<String, List<SuggestionResult>> suggestions = new HashMap<>();
    
    @Data
    @Accessors(chain = true)
    public static class ShardInfo {
        /**
         * 总分片数
         */
        private int total;
        
        /**
         * 成功分片数
         */
        private int successful;
        
        /**
         * 跳过分片数
         */
        private int skipped;
        
        /**
         * 失败分片数
         */
        private int failed;
    }
    
    @Data
    @Accessors(chain = true)
    public static class SearchHit<T> {
        /**
         * 文档ID
         */
        private String id;
        
        /**
         * 索引名称
         */
        private String index;
        
        /**
         * 评分
         */
        private float score;
        
        /**
         * 排序值
         */
        private Object[] sortValues;
        
        /**
         * 源文档
         */
        private T source;
        
        /**
         * 高亮结果
         */
        private Map<String, List<String>> highlights = new HashMap<>();
    }
    
    @Data
    @Accessors(chain = true)
    public static class AggregationResult {
        /**
         * 聚合类型
         */
        private String type;
        
        /**
         * 聚合值
         */
        private Object value;
        
        /**
         * 聚合桶
         */
        private List<Bucket> buckets = new ArrayList<>();
    }
    
    @Data
    @Accessors(chain = true)
    public static class Bucket {
        /**
         * 桶键
         */
        private Object key;
        
        /**
         * 文档数
         */
        private long docCount;
        
        /**
         * 子聚合
         */
        private Map<String, AggregationResult> subAggregations = new HashMap<>();
    }
    
    @Data
    @Accessors(chain = true)
    public static class SuggestionResult {
        /**
         * 建议文本
         */
        private String text;
        
        /**
         * 偏移量
         */
        private int offset;
        
        /**
         * 长度
         */
        private int length;
        
        /**
         * 评分
         */
        private float score;
    }
} 