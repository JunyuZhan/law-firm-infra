package com.lawfirm.model.system.dto.dict;

import com.lawfirm.model.base.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DictQueryDTO extends PageDTO<DictQueryDTO> {

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
     * 状态（0-启用，1-禁用）
     */
    private Integer status;
} 