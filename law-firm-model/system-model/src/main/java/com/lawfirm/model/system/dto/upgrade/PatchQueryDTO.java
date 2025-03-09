package com.lawfirm.model.system.dto.upgrade;

import com.lawfirm.model.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统补丁查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PatchQueryDTO extends BaseQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 所属升级ID
     */
    private Long upgradeId;

    /**
     * 补丁名称
     */
    private String name;
    
    /**
     * 补丁编号
     */
    private String code;
    
    /**
     * 补丁状态
     */
    private String status;
    
    /**
     * 补丁类型
     */
    private String type;
    
    /**
     * 是否必须安装
     */
    private Boolean required;
    
    /**
     * 最小优先级
     */
    private Integer minPriority;
    
    /**
     * 最大优先级
     */
    private Integer maxPriority;

    /**
     * 补丁类型：SQL-SQL补丁，SCRIPT-脚本补丁，FILE-文件补丁
     */
    private String patchType;
} 