package com.lawfirm.model.contract.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同模板详情VO
 */
@Data
public class ContractTemplateDetailVO {
    
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
     * 模板内容
     */
    private String content;
    
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
    private LocalDateTime updateTime;
    
    /**
     * 模板变量列表
     */
    private List<TemplateVariableVO> variables;
    
    /**
     * 模板变量VO
     */
    @Data
    public static class TemplateVariableVO {
        
        /**
         * 变量ID
         */
        private Long id;
        
        /**
         * 变量名称
         */
        private String variableName;
        
        /**
         * 变量键
         */
        private String variableKey;
        
        /**
         * 变量类型（text文本/number数字/date日期/select选择）
         */
        private String variableType;
        
        /**
         * 变量类型名称
         */
        private String variableTypeName;
        
        /**
         * 默认值
         */
        private String defaultValue;
        
        /**
         * 是否必填（0否1是）
         */
        private Integer isRequired;
        
        /**
         * 是否必填名称
         */
        private String isRequiredName;
        
        /**
         * 选项（JSON格式，针对select类型）
         */
        private String options;
        
        /**
         * 变量描述
         */
        private String description;
        
        /**
         * 排序号
         */
        private Integer orderNum;
    }
} 