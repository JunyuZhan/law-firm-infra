package com.lawfirm.model.workflow.entity.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lawfirm.model.workflow.entity.base.BaseProcess;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 通用流程实现
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CommonProcess extends BaseProcess {

    private static final long serialVersionUID = 1L;

    /**
     * 流程分类
     */
    @TableField("category")
    private String category;

    /**
     * 流程标签
     */
    @TableField("tags")
    private String tags;

    /**
     * 流程模板ID
     */
    @TableField("template_id")
    private Long templateId;

    /**
     * 流程模板版本
     */
    @TableField("template_version")
    private String templateVersion;

    /**
     * 流程变量（JSON格式）
     */
    @TableField("variables")
    private String variables;

    /**
     * 流程表单（JSON格式）
     */
    @TableField("form_data")
    private String formData;

    /**
     * 流程附件IDs
     */
    @TableField("attachment_ids")
    private String attachmentIds;

    /**
     * 流程备注
     */
    @TableField("notes")
    private String notes;
} 