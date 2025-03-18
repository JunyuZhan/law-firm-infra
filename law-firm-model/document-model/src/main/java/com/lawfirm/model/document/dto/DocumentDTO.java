package com.lawfirm.model.document.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文档数据传输对象
 */
@Data
@Accessors(chain = true)
public class DocumentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文档ID
     */
    private Long id;

    /**
     * 文档名称
     */
    private String name;

    /**
     * 文档类型
     */
    private String type;

    /**
     * 文档大小（字节）
     */
    private Long size;

    /**
     * 文档路径
     */
    private String path;

    /**
     * 文档状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 更新人ID
     */
    private Long updateBy;
} 