package com.lawfirm.api.adaptor.contract;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.contract.dto.ContractTemplateCreateDTO;
import com.lawfirm.model.contract.dto.ContractTemplateUpdateDTO;
import com.lawfirm.model.contract.dto.ContractTemplateQueryDTO;
import com.lawfirm.model.contract.service.ContractTemplateService;
import com.lawfirm.model.contract.vo.ContractTemplateVO;
import com.lawfirm.model.contract.vo.ContractTemplateDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 合同模板适配器
 */
@Slf4j
@Component
public class TemplateAdaptor extends BaseAdaptor {

    private final ContractTemplateService templateService;

    public TemplateAdaptor(@Qualifier("contractTemplateService") ContractTemplateService templateService) {
        this.templateService = templateService;
    }

    /**
     * 创建模板
     */
    public Long createTemplate(ContractTemplateCreateDTO createDTO) {
        log.info("创建模板: {}", createDTO);
        return templateService.createTemplate(createDTO);
    }

    /**
     * 更新模板
     */
    public boolean updateTemplate(ContractTemplateUpdateDTO updateDTO) {
        log.info("更新模板: {}", updateDTO);
        return templateService.updateTemplate(updateDTO);
    }

    /**
     * 获取模板详情
     */
    public ContractTemplateDetailVO getTemplateDetail(Long templateId) {
        log.info("获取模板详情: {}", templateId);
        return templateService.getTemplateDetail(templateId);
    }

    /**
     * 获取所有模板
     */
    public List<ContractTemplateVO> listTemplates(ContractTemplateQueryDTO queryDTO) {
        log.info("获取所有模板: {}", queryDTO);
        return templateService.listTemplates(queryDTO);
    }

    /**
     * 分页查询模板
     */
    public IPage<ContractTemplateVO> pageTemplates(IPage<ContractTemplateVO> page, ContractTemplateQueryDTO queryDTO) {
        log.info("分页查询模板: page={}, queryDTO={}", page, queryDTO);
        return templateService.pageTemplates(page, queryDTO);
    }

    /**
     * 启用模板
     */
    public boolean enableTemplate(Long templateId) {
        log.info("启用模板: {}", templateId);
        return templateService.enableTemplate(templateId);
    }

    /**
     * 禁用模板
     */
    public boolean disableTemplate(Long templateId) {
        log.info("禁用模板: {}", templateId);
        return templateService.disableTemplate(templateId);
    }
}