package com.lawfirm.model.system.vo.upgrade;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 系统补丁VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PatchVO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 补丁ID
     */
    private Long id;

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
     * 补丁描述
     */
    private String description;
    
    /**
     * 补丁文件路径
     */
    private String filePath;
    
    /**
     * 补丁类型
     */
    private String type;
    
    /**
     * 补丁类型名称
     */
    private String typeName;
    
    /**
     * 补丁优先级
     */
    private Integer priority;
    
    /**
     * 是否必须安装
     */
    private Boolean required;
    
    /**
     * 补丁状态
     */
    private String status;
    
    /**
     * 补丁状态名称
     */
    private String statusName;
    
    /**
     * 安装时间
     */
    private LocalDateTime installTime;
    
    /**
     * 安装结果
     */
    private String installResult;
    
    /**
     * 创建者
     */
    private String createBy;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新者
     */
    private String updateBy;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 