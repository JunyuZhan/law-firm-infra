package com.lawfirm.model.search.dto.search;

import com.lawfirm.model.base.query.BaseQuery;
import com.lawfirm.model.search.enums.SearchTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 搜索请求DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SearchRequestDTO extends BaseQuery {

    /**
     * 索引名称
     */
    @NotBlank(message = "索引名称不能为空")
    private String indexName;

    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 搜索类型
     */
    private SearchTypeEnum searchType = SearchTypeEnum.MATCH;

    /**
     * 搜索字段
     */
    private List<String> fields = new ArrayList<>();

    /**
     * 过滤条件
     */
    private Map<String, Object> filters;

    /**
     * 排序字段
     */
    private Map<String, String> sorts;

    /**
     * 高亮字段
     */
    private List<String> highlights = new ArrayList<>();

    /**
     * 聚合配置
     */
    private Map<String, Object> aggregations;

    /**
     * 是否返回源文档
     */
    private Boolean fetchSource = true;

    /**
     * 包含的源字段
     */
    private List<String> includes = new ArrayList<>();

    /**
     * 排除的源字段
     */
    private List<String> excludes = new ArrayList<>();

    /**
     * 最小得分
     */
    private Float minScore;

    /**
     * 添加搜索字段
     */
    public SearchRequestDTO addField(String field) {
        fields.add(field);
        return this;
    }

    /**
     * 添加高亮字段
     */
    public SearchRequestDTO addHighlight(String field) {
        highlights.add(field);
        return this;
    }

    /**
     * 添加包含字段
     */
    public SearchRequestDTO addInclude(String field) {
        includes.add(field);
        return this;
    }

    /**
     * 添加排除字段
     */
    public SearchRequestDTO addExclude(String field) {
        excludes.add(field);
        return this;
    }
} 