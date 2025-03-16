package com.lawfirm.model.contract.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 合同模板列表VO
 */
@Data
public class ContractTemplateVO {
    
    /**
     * 模板ID
     */
    private Long id;
    
    /**
     * 模板名称
     */
    private String templateName;
    
    /**
     * 模板编码
     */
    private String templateCode;
    
    /**
     * 模板类别（1-标准合同 2-定制合同 3-框架协议）
     */
    private Integer category;
    
    /**
     * 模板类别名称
     */
    private String categoryName;
    
    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusName;
    
    /**
     * 模板描述
     */
    private String description;
    
    /**
     * 模板版本
     */
    private String version;
    
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
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 变量数量
     */
    private Integer variableCount;
} 