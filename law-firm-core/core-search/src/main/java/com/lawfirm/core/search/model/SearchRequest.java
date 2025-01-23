package com.lawfirm.core.search.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索请求
 */
@Data
@Accessors(chain = true)
public class SearchRequest {
    
    /**
     * 索引名称
     */
    private String index;
    
    /**
     * 关键词
     */
    private String keyword;
    
    /**
     * 搜索字段及权重
     */
    private Map<String, Float> fields = new HashMap<>();
    
    /**
     * 过滤条件
     */
    private List<Filter> filters = new ArrayList<>();
    
    /**
     * 排序字段
     */
    private List<Sort> sorts = new ArrayList<>();
    
    /**
     * 高亮字段
     */
    private List<String> highlightFields = new ArrayList<>();
    
    /**
     * 聚合配置
     */
    private List<Aggregation> aggregations = new ArrayList<>();
    
    /**
     * 建议配置
     */
    private List<Suggestion> suggestions = new ArrayList<>();
    
    /**
     * 分页参数
     */
    private int page = 1;
    private int size = 10;
    
    /**
     * 最小匹配度
     */
    private float minimumShouldMatch = 0.3f;
    
    /**
     * 是否返回评分
     */
    private boolean fetchScore = true;
    
    /**
     * 是否返回源文档
     */
    private boolean fetchSource = true;
    
    @Data
    @Accessors(chain = true)
    public static class Filter {
        /**
         * 字段名称
         */
        private String field;
        
        /**
         * 操作类型（eq/ne/gt/gte/lt/lte/in/between/exists）
         */
        private String operation;
        
        /**
         * 字段值
         */
        private Object value;
    }
    
    @Data
    @Accessors(chain = true)
    public static class Sort {
        /**
         * 字段名称
         */
        private String field;
        
        /**
         * 排序方向（asc/desc）
         */
        private String direction = "desc";
    }
    
    @Data
    @Accessors(chain = true)
    public static class Aggregation {
        /**
         * 聚合名称
         */
        private String name;
        
        /**
         * 聚合类型（terms/range/date_histogram等）
         */
        private String type;
        
        /**
         * 聚合字段
         */
        private String field;
        
        /**
         * 聚合参数
         */
        private Map<String, Object> params = new HashMap<>();
    }
    
    @Data
    @Accessors(chain = true)
    public static class Suggestion {
        /**
         * 建议名称
         */
        private String name;
        
        /**
         * 建议字段
         */
        private String field;
        
        /**
         * 建议类型（term/phrase/completion）
         */
        private String type;
        
        /**
         * 建议参数
         */
        private Map<String, Object> params = new HashMap<>();
    }
} 