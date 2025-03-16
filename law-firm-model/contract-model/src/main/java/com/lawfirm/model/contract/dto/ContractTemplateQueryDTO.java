package com.lawfirm.model.contract.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lawfirm.model.base.query.BaseQuery;

/**
 * 合同模板查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContractTemplateQueryDTO extends BaseQuery {
    
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
     * 状态（0-禁用 1-启用）
     */
    private Integer status;
    
    /**
     * 创建人ID
     */
    private Long createBy;
    
    /**
     * 模板版本
     */
    private String version;
} 