package com.lawfirm.model.document.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 模板视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TemplateVO extends BaseDTO {

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板类型
     */
    private String templateType;

    /**
     * 模板类型名称
     */
    private String templateTypeName;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务类型名称
     */
    private String businessTypeName;

    /**
     * 适用范围
     */
    private String scope;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 模板参数（JSON格式）
     */
    private String parameters;

    /**
     * 是否系统内置
     */
    private Boolean isSystem;

    /**
     * 是否默认模板
     */
    private Boolean isDefault;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 使用次数
     */
    private Long useCount;

    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 是否最新版本
     */
    private Boolean isLatest;

    /**
     * 状态（0-启用 1-禁用）
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 创建人名称
     */
    private String createByName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新人ID
     */
    private Long updateBy;

    /**
     * 更新人名称
     */
    private String updateByName;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
} 