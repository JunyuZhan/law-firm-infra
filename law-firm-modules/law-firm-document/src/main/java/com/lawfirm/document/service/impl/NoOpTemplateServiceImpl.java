package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.template.TemplateCreateDTO;
import com.lawfirm.model.document.dto.template.TemplateUpdateDTO;
import com.lawfirm.model.document.entity.template.TemplateDocument;
import com.lawfirm.model.document.service.TemplateService;
import com.lawfirm.model.document.vo.TemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 文档模板服务空实现类，当存储功能禁用时使用
 */
@Slf4j
@Service("documentTemplateServiceImpl")
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "false", matchIfMissing = false)
public class NoOpTemplateServiceImpl extends BaseServiceImpl<BaseMapper<TemplateDocument>, TemplateDocument> implements TemplateService {

    @Override
    public Long createTemplate(TemplateCreateDTO createDTO) {
        log.warn("存储功能已禁用，忽略创建模板操作");
        return null;
    }

    @Override
    public void updateTemplate(Long id, TemplateUpdateDTO updateDTO) {
        log.warn("存储功能已禁用，忽略更新模板操作: {}", id);
    }

    @Override
    public void deleteTemplate(Long id) {
        log.warn("存储功能已禁用，忽略删除模板操作: {}", id);
    }

    @Override
    public void deleteTemplates(List<Long> ids) {
        log.warn("存储功能已禁用，忽略批量删除模板操作: {}", ids);
    }

    @Override
    public TemplateVO getTemplateById(Long id) {
        log.warn("存储功能已禁用，忽略获取模板详情操作: {}", id);
        return null;
    }

    @Override
    public TemplateVO getTemplateByCode(String templateCode) {
        log.warn("存储功能已禁用，忽略根据编码获取模板操作: {}", templateCode);
        return null;
    }

    @Override
    public Page<TemplateVO> pageTemplates(Page<TemplateDocument> page, TemplateDocument template) {
        log.warn("存储功能已禁用，忽略分页查询模板操作");
        return new Page<>();
    }

    @Override
    public List<TemplateVO> listAllTemplates() {
        log.warn("存储功能已禁用，忽略获取所有模板操作");
        return Collections.emptyList();
    }

    @Override
    public List<TemplateVO> listTemplatesByType(String templateType) {
        log.warn("存储功能已禁用，忽略根据类型获取模板列表操作: {}", templateType);
        return Collections.emptyList();
    }

    @Override
    public List<TemplateVO> listTemplatesByBusinessType(String businessType) {
        log.warn("存储功能已禁用，忽略根据业务类型获取模板列表操作: {}", businessType);
        return Collections.emptyList();
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        log.warn("存储功能已禁用，忽略更新模板状态操作: {}, {}", id, status);
    }

    @Override
    public void setDefault(Long id, Boolean isDefault) {
        log.warn("存储功能已禁用，忽略设置默认模板操作: {}, {}", id, isDefault);
    }

    @Override
    public Long generateDocument(Long templateId, Map<String, Object> parameters) {
        log.warn("存储功能已禁用，忽略生成文档操作: {}", templateId);
        return null;
    }

    @Override
    public String previewTemplate(Long id, Map<String, Object> parameters) {
        log.warn("存储功能已禁用，忽略预览模板操作: {}", id);
        return "模板预览功能已禁用";
    }

    @Override
    public void refreshCache() {
        log.warn("存储功能已禁用，忽略刷新模板缓存操作");
    }
} 