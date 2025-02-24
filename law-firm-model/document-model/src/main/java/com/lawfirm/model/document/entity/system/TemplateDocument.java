package com.lawfirm.model.document.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.document.entity.base.BaseDocument;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 模板文档实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("doc_template")
public class TemplateDocument extends BaseDocument {

    /**
     * 模板编码
     */
    @TableField("template_code")
    private String templateCode;

    /**
     * 模板类型
     */
    @TableField("template_type")
    private String templateType;

    /**
     * 业务类型
     */
    @TableField("business_type")
    private String businessType;

    /**
     * 适用范围
     */
    @TableField("scope")
    private String scope;

    /**
     * 模板参数（JSON格式）
     */
    @TableField("parameters")
    private String parameters;

    /**
     * 模板内容
     */
    @TableField("content")
    private String content;

    /**
     * 是否系统内置
     */
    @TableField("is_system")
    private Boolean isSystem;

    /**
     * 是否默认模板
     */
    @TableField("is_default")
    private Boolean isDefault;

    /**
     * 使用次数
     */
    @TableField("use_count")
    private Long useCount;

    /**
     * 版本号
     */
    @TableField("version_no")
    private String versionNo;

    /**
     * 是否最新版本
     */
    @TableField("is_latest")
    private Boolean isLatest;

    /**
     * 审核状态
     */
    @TableField("review_status")
    private String reviewStatus;

    /**
     * 审核人
     */
    @TableField("reviewer")
    private String reviewer;

    /**
     * 审核意见
     */
    @TableField("review_comment")
    private String reviewComment;
} 