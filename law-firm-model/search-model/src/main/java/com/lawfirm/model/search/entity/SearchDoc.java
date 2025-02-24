package com.lawfirm.model.search.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 搜索文档实体
 */
@Data
@Entity
@Table(name = "search_doc")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SearchDoc extends ModelBaseEntity {

    /**
     * 索引ID
     */
    @Column(name = "index_id", nullable = false)
    private Long indexId;

    /**
     * 文档ID
     */
    @Column(name = "doc_id", nullable = false)
    private String docId;

    /**
     * 业务ID
     */
    @Column(name = "biz_id")
    private String bizId;

    /**
     * 业务类型
     */
    @Column(name = "biz_type")
    private String bizType;

    /**
     * 文档内容（JSON格式）
     */
    @Column(name = "content", columnDefinition = "text")
    private String content;

    /**
     * 文档状态（0：待索引，1：已索引，2：索引失败）
     */
    @Column(name = "status", nullable = false)
    private Integer status = 0;

    /**
     * 错误信息
     */
    @Column(name = "error_msg")
    private String errorMsg;

    /**
     * 重试次数
     */
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 0;

    /**
     * 最后重试时间
     */
    @Column(name = "last_retry_time")
    private Long lastRetryTime;

    /**
     * 索引时间
     */
    @Column(name = "index_time")
    private Long indexTime;
} 