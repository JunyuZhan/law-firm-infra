package com.lawfirm.api.adaptor.contract;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.contract.dto.business.ContractTemplateDTO;
import com.lawfirm.model.contract.service.business.ContractTemplateService;
import com.lawfirm.model.contract.vo.business.ContractTemplateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 合同模板适配器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TemplateAdaptor extends BaseAdaptor {

    private final ContractTemplateService templateService;

    /**
     * 创建模板
     */
    public Long createTemplate(ContractTemplateDTO templateDTO) {
        log.info("创建模板: {}", templateDTO);
        return templateService.createTemplate(templateDTO);
    }

    /**
     * 更新模板
     */
    public boolean updateTemplate(ContractTemplateDTO templateDTO) {
        log.info("更新模板: {}", templateDTO);
        return templateService.updateTemplate(templateDTO);
    }

    /**
     * 删除模板
     */
    public boolean deleteTemplate(Long templateId) {
        log.info("删除模板: {}", templateId);
        return templateService.deleteTemplate(templateId);
    }

    /**
     * 获取模板详情
     */
    public ContractTemplateVO getTemplateDetail(Long templateId) {
        log.info("获取模板详情: {}", templateId);
        return templateService.getTemplateDetail(templateId);
    }

    /**
     * 获取所有模板
     */
    public List<ContractTemplateVO> listTemplates() {
        log.info("获取所有模板");
        return templateService.listTemplates();
    }

    /**
     * 分页查询模板
     */
    public IPage<ContractTemplateVO> pageTemplates(Integer templateType, Integer pageNum, Integer pageSize) {
        log.info("分页查询模板: templateType={}, pageNum={}, pageSize={}", templateType, pageNum, pageSize);
        return templateService.pageTemplates(templateType, pageNum, pageSize);
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

    /**
     * 复制模板
     */
    public Long copyTemplate(Long templateId, String newName) {
        log.info("复制模板: templateId={}, newName={}", templateId, newName);
        return templateService.copyTemplate(templateId, newName);
    }

    /**
     * 设置模板为默认
     */
    public boolean setTemplateAsDefault(Long templateId) {
        log.info("设置模板为默认: {}", templateId);
        return templateService.setTemplateAsDefault(templateId);
    }

    /**
     * 检查模板是否存在
     */
    public boolean checkTemplateExists(Long templateId) {
        log.info("检查模板是否存在: {}", templateId);
        return templateService.checkTemplateExists(templateId);
    }

    /**
     * 统计模板数量
     */
    public int countTemplates(Integer templateType) {
        log.info("统计模板数量: templateType={}", templateType);
        return templateService.countTemplates(templateType);
    }
} 