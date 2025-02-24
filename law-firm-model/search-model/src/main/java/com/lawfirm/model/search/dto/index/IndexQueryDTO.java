package com.lawfirm.model.search.dto.index;

import com.lawfirm.model.base.query.BaseQuery;
import com.lawfirm.model.search.enums.IndexTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 索引查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class IndexQueryDTO extends BaseQuery {

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 索引类型
     */
    private IndexTypeEnum indexType;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
} 