package com.lawfirm.model.contract.dto;

import lombok.Data;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 合同模板创建DTO
 */
@Data
public class ContractTemplateCreateDTO {
    
    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不能为空")
    @Size(max = 100, message = "模板名称长度不能超过100个字符")
    private String templateName;
    
    /**
     * 模板编码
     */
    @NotBlank(message = "模板编码不能为空")
    @Size(max = 50, message = "模板编码长度不能超过50个字符")
    private String templateCode;
    
    /**
     * 模板类别（1-标准合同 2-定制合同 3-框架协议）
     */
    @NotNull(message = "模板类别不能为空")
    private Integer category;
    
    /**
     * 模板内容
     */
    @NotBlank(message = "模板内容不能为空")
    private String content;
    
    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status = 1;
    
    /**
     * 模板描述
     */
    @Size(max = 500, message = "模板描述长度不能超过500个字符")
    private String description;
    
    /**
     * 模板版本
     */
    @NotBlank(message = "模板版本不能为空")
    @Size(max = 20, message = "模板版本长度不能超过20个字符")
    private String version = "1.0";
    
    /**
     * 模板变量列表
     */
    private List<TemplateVariableDTO> variables;
    
    /**
     * 模板变量DTO
     */
    @Data
    public static class TemplateVariableDTO {
        
        /**
         * 变量名称
         */
        @NotBlank(message = "变量名称不能为空")
        @Size(max = 50, message = "变量名称长度不能超过50个字符")
        private String variableName;
        
        /**
         * 变量键
         */
        @NotBlank(message = "变量键不能为空")
        @Size(max = 50, message = "变量键长度不能超过50个字符")
        private String variableKey;
        
        /**
         * 变量类型（text文本/number数字/date日期/select选择）
         */
        @NotBlank(message = "变量类型不能为空")
        private String variableType;
        
        /**
         * 默认值
         */
        private String defaultValue;
        
        /**
         * 是否必填（0否1是）
         */
        private Integer isRequired = 0;
        
        /**
         * 选项（JSON格式，针对select类型）
         */
        private String options;
        
        /**
         * 变量描述
         */
        @Size(max = 200, message = "变量描述长度不能超过200个字符")
        private String description;
        
        /**
         * 排序号
         */
        private Integer orderNum = 0;
    }
} 