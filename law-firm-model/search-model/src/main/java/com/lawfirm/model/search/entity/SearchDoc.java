package com.lawfirm.model.search.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 搜索文档实体
 */
@Data
@TableName("search_doc")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SearchDoc extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 索引ID
     */
    @TableField("index_id")
    private Long indexId;

    /**
     * 文档ID
     */
    @TableField("doc_id")
    private String docId;

    /**
     * 业务ID
     */
    @TableField("biz_id")
    private String bizId;

    /**
     * 业务类型
     */
    @TableField("biz_type")
    private String bizType;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 状态，0-待索引，1-索引成功，2-索引失败
     */
    @TableField("status")
    private Integer status = 0;

    /**
     * 错误信息
     */
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 重试次数
     */
    @TableField("retry_count")
    private Integer retryCount = 0;

    /**
     * 最后重试时间
     */
    @TableField("last_retry_time")
    private Long lastRetryTime;

    /**
     * 索引时间
     */
    @TableField("index_time")
    private Long indexTime;
} 