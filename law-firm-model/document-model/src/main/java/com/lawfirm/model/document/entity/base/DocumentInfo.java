package com.lawfirm.model.document.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档信息实体
 * 存储文档的元数据信息
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("doc_info")
public class DocumentInfo extends ModelBaseEntity {

    /**
     * 文档ID
     */
    @TableField("doc_id")
    private Long docId;

    /**
     * 作者
     */
    @TableField("author")
    private String author;

    /**
     * 所有者
     */
    @TableField("owner")
    private String owner;

    /**
     * 所属部门ID
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 文档分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 标签，多个标签用逗号分隔
     */
    @TableField("tags")
    private String tags;

    /**
     * 源文件路径
     */
    @TableField("source_path")
    private String sourcePath;

    /**
     * 文档语言
     */
    @TableField("language")
    private String language;

    /**
     * 页数
     */
    @TableField("page_count")
    private Integer pageCount;

    /**
     * 字数
     */
    @TableField("word_count")
    private Integer wordCount;

    /**
     * 最后访问时间
     */
    @TableField("last_access_time")
    private LocalDateTime lastAccessTime;

    /**
     * 最后修改时间
     */
    @TableField("last_modify_time")
    private LocalDateTime lastModifyTime;

    /**
     * 文档来源
     */
    @TableField("source")
    private String source;

    /**
     * 文档格式
     */
    @TableField("format")
    private String format;

    /**
     * 文档编码
     */
    @TableField("encoding")
    private String encoding;

    /**
     * 文档摘要
     */
    @TableField("summary")
    private String summary;

    /**
     * 相关文档ID，多个用逗号分隔
     */
    @TableField("related_docs")
    private String relatedDocs;

    /**
     * 审核人
     */
    @TableField("reviewer")
    private String reviewer;

    /**
     * 审核时间
     */
    @TableField("review_time")
    private LocalDateTime reviewTime;

    /**
     * 审核意见
     */
    @TableField("review_comment")
    private String reviewComment;

    /**
     * 扩展属性（JSON格式）
     */
    @TableField("attributes")
    private String attributes;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
} 