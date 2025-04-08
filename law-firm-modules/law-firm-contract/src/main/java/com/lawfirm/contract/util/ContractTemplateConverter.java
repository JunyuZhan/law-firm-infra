package com.lawfirm.contract.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.model.contract.dto.ContractTemplateCreateDTO;
import com.lawfirm.model.contract.dto.ContractTemplateUpdateDTO;
import com.lawfirm.model.contract.entity.ContractTemplate;
import com.lawfirm.model.contract.vo.ContractTemplateDetailVO;
import com.lawfirm.model.contract.vo.ContractTemplateVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 合同模板对象转换工具类
 * 用于实现不同合同模板相关对象之间的转换
 */
@Slf4j
public class ContractTemplateConverter {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将创建DTO转换为实体
     * @param createDTO 创建DTO
     * @return 合同模板实体
     */
    public static ContractTemplate toEntity(ContractTemplateCreateDTO createDTO) {
        ContractTemplate template = new ContractTemplate();
        template.setTemplateCode(createDTO.getTemplateCode());
        template.setTemplateName(createDTO.getTemplateName());
        template.setCategory(createDTO.getCategory().toString());
        template.setContent(createDTO.getContent());
        template.setStatus(createDTO.getStatus());
        template.setTemplateVersion(createDTO.getVersion());
        template.setDescription(createDTO.getDescription());
        template.setUsageCount(0); // 初始使用次数为0
        
        // 处理变量列表
        if (createDTO.getVariables() != null && !createDTO.getVariables().isEmpty()) {
            try {
                template.setVariables(objectMapper.writeValueAsString(createDTO.getVariables()));
            } catch (JsonProcessingException e) {
                log.error("转换变量列表失败", e);
            }
        }
        
        return template;
    }
    
    /**
     * 使用更新DTO更新实体
     * @param entity 实体
     * @param updateDTO 更新DTO
     */
    public static void updateEntity(ContractTemplate entity, ContractTemplateUpdateDTO updateDTO) {
        if (updateDTO.getTemplateName() != null) {
            entity.setTemplateName(updateDTO.getTemplateName());
        }
        
        if (updateDTO.getContent() != null) {
            entity.setContent(updateDTO.getContent());
        }
        
        if (updateDTO.getStatus() != null) {
            entity.setStatus(updateDTO.getStatus());
        }
        
        if (updateDTO.getVersion() != null) {
            entity.setTemplateVersion(updateDTO.getVersion());
        }
        
        if (updateDTO.getDescription() != null) {
            entity.setDescription(updateDTO.getDescription());
        }
        
        // 处理变量列表
        if (updateDTO.getVariables() != null && !updateDTO.getVariables().isEmpty()) {
            try {
                entity.setVariables(objectMapper.writeValueAsString(updateDTO.getVariables()));
            } catch (JsonProcessingException e) {
                log.error("转换变量列表失败", e);
            }
        }
    }
    
    /**
     * 将实体转换为VO
     * @param entity 实体
     * @return 合同模板VO
     */
    public static ContractTemplateVO toVO(ContractTemplate entity) {
        if (entity == null) {
            return null;
        }
        
        ContractTemplateVO vo = new ContractTemplateVO();
        vo.setId(entity.getId());
        vo.setTemplateCode(entity.getTemplateCode());
        vo.setTemplateName(entity.getTemplateName());
        vo.setCategory(Integer.parseInt(entity.getCategory()));
        vo.setStatus(entity.getStatus());
        vo.setDescription(entity.getDescription());
        vo.setVersion(entity.getTemplateVersion());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        
        // 设置状态名称
        vo.setStatusName(getStatusName(entity.getStatus()));
        // 设置类别名称
        vo.setCategoryName(getCategoryName(Integer.parseInt(entity.getCategory())));
        // 设置变量数量
        if (entity.getVariables() != null) {
            try {
                vo.setVariableCount(objectMapper.readTree(entity.getVariables()).size());
            } catch (JsonProcessingException e) {
                log.error("解析变量数量失败", e);
                vo.setVariableCount(0);
            }
        }
        
        return vo;
    }
    
    /**
     * 将实体转换为详情VO
     * @param entity 实体
     * @return 合同模板详情VO
     */
    public static ContractTemplateDetailVO toDetailVO(ContractTemplate entity) {
        if (entity == null) {
            return null;
        }
        
        ContractTemplateDetailVO detailVO = new ContractTemplateDetailVO();
        
        // 基本信息
        detailVO.setId(entity.getId());
        detailVO.setTemplateCode(entity.getTemplateCode());
        detailVO.setTemplateName(entity.getTemplateName());
        detailVO.setCategory(Integer.parseInt(entity.getCategory()));
        detailVO.setStatus(entity.getStatus());
        detailVO.setDescription(entity.getDescription());
        detailVO.setVersion(entity.getTemplateVersion());
        
        // 设置状态名称
        detailVO.setStatusName(getStatusName(entity.getStatus()));
        // 设置类别名称
        detailVO.setCategoryName(getCategoryName(Integer.parseInt(entity.getCategory())));
        
        // 模板内容
        detailVO.setContent(entity.getContent());
        
        // 处理变量列表
        if (entity.getVariables() != null) {
            try {
                detailVO.setVariables(objectMapper.readValue(entity.getVariables(), 
                    objectMapper.getTypeFactory().constructCollectionType(
                        java.util.List.class, 
                        com.lawfirm.model.contract.vo.ContractTemplateDetailVO.TemplateVariableVO.class
                    )
                ));
            } catch (JsonProcessingException e) {
                log.error("解析变量列表失败", e);
            }
        }
        
        // 基础时间信息
        detailVO.setCreateTime(entity.getCreateTime());
        detailVO.setUpdateTime(entity.getUpdateTime());
        
        return detailVO;
    }
    
    /**
     * 获取状态名称
     */
    private static String getStatusName(Integer status) {
        if (status == null) {
            return "未知";
        }
        switch (status) {
            case 0:
                return "禁用";
            case 1:
                return "启用";
            default:
                return "未知";
        }
    }
    
    /**
     * 获取类别名称
     */
    private static String getCategoryName(Integer category) {
        if (category == null) {
            return "未知";
        }
        switch (category) {
            case 1:
                return "标准合同";
            case 2:
                return "定制合同";
            case 3:
                return "框架协议";
            default:
                return "未知";
        }
    }
} 