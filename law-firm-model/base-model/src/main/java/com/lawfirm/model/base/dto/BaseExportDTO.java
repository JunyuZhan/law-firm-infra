package com.lawfirm.model.base.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 通用导出DTO基类
 * 提供所有模块导出功能的基础属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BaseExportDTO extends BaseDTO {

    /**
     * 导出文件名
     */
    private String fileName;

    /**
     * 导出文件类型（如：EXCEL、CSV、PDF等）
     */
    private String fileType;

    /**
     * 导出字段列表
     */
    @NotEmpty(message = "导出字段不能为空")
    private List<String> fields;

    /**
     * 是否导出全部数据
     */
    private Boolean exportAll = false;

    /**
     * 最大导出数量
     */
    private Integer maxExportSize = 10000;

    /**
     * 是否异步导出
     */
    private Boolean async = false;

    /**
     * 是否压缩
     */
    private Boolean compressed = false;

    /**
     * 导出模板ID
     */
    private String templateId;

    /**
     * 导出备注
     */
    private String exportRemark;
} 