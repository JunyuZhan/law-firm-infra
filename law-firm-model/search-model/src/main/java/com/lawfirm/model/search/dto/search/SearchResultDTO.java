package com.lawfirm.model.search.dto.search;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 搜索结果DTO
 */
@Data
@Accessors(chain = true)
public class SearchResultDTO {

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 最大得分
     */
    private Float maxScore;

    /**
     * 是否超时
     */
    private Boolean timedOut;

    /**
     * 耗时（毫秒）
     */
    private Long took;

    /**
     * 命中记录
     */
    private List<Hit> hits = new ArrayList<>();

    /**
     * 聚合结果
     */
    private Map<String, Object> aggregations;

    /**
     * 命中记录
     */
    @Data
    @Accessors(chain = true)
    public static class Hit {
        /**
         * 索引名称
         */
        private String index;

        /**
         * 文档ID
         */
        private String id;

        /**
         * 得分
         */
        private Float score;

        /**
         * 源文档
         */
        private Map<String, Object> source;

        /**
         * 高亮内容
         */
        private Map<String, List<String>> highlight;
    }
} 