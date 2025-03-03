package com.lawfirm.model.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 字典视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DictVO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典类型名称
     */
    private String dictTypeName;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 状态（0-启用，1-禁用）
     */
    private Integer status;

    /**
     * 字典项列表
     */
    private transient List<DictItemVO> items;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
} 