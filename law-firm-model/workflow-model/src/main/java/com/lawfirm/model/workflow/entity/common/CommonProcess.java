package com.lawfirm.model.workflow.entity.common;

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

    /**
     * 流程分类
     */
    private String category;

    /**
     * 流程标签
     */
    private String tags;

    /**
     * 流程模板ID
     */
    private Long templateId;

    /**
     * 流程模板版本
     */
    private String templateVersion;

    /**
     * 流程变量（JSON格式）
     */
    private String variables;

    /**
     * 流程表单（JSON格式）
     */
    private String formData;

    /**
     * 流程附件IDs
     */
    private String attachmentIds;

    /**
     * 流程备注
     */
    private String notes;
} 