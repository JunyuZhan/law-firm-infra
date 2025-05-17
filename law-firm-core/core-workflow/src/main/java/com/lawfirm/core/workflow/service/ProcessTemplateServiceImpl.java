package com.lawfirm.core.workflow.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.workflow.mapper.ProcessTemplateMapper;
import com.lawfirm.model.workflow.entity.ProcessTemplate;
import com.lawfirm.model.workflow.service.ProcessTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import com.lawfirm.common.security.context.SecurityContextHolder;
import java.io.IOException;
import java.util.UUID;

/**
 * 流程模板服务实现类
 * 
 * @author JunyuZhan
 */
@Slf4j
@Component("coreProcessTemplateServiceImpl")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplateMapper, ProcessTemplate> implements ProcessTemplateService {

    private final RepositoryService repositoryService;

    @Override
    public String deployProcessTemplate(String name, String key, String category, MultipartFile file) {
        // 最小可用实现：保存模板元数据，模拟部署
        ProcessTemplate template = new ProcessTemplate();
        template.setName(name)
                .setKey(key)
                .setCategory(category)
                .setCreatorName(SecurityContextHolder.getContext().getCurrentUsername())
                .setTenantId(SecurityContextHolder.getContext().getCurrentTenantId());
        // 生成唯一版本号和部署ID（实际可用BPMN解析/部署）
        template.setTemplateVersion(UUID.randomUUID().toString());
        template.setDeploymentId(UUID.randomUUID().toString());
        // 可根据file内容进一步处理
        this.save(template);
        return template.getId() != null ? template.getId().toString() : null;
    }

    @Override
    public String updateProcessTemplate(String id, String name, String category, MultipartFile file) {
        // 最小可用实现：更新模板元数据
        ProcessTemplate template = this.getById(id);
        if (template == null) {
            throw new RuntimeException("流程模板不存在");
        }
        template.setName(name)
                .setCategory(category)
                .setUpdaterName(SecurityContextHolder.getContext().getCurrentUsername());
        // 可根据file内容进一步处理
        this.updateById(template);
        return template.getId() != null ? template.getId().toString() : null;
    }

    @Override
    public Object getProcessTemplate(String id) {
        return getById(id);
    }

    @Override
    public Page<?> getProcessTemplatePage(String key, String name, String category, int current, int size) {
        Page<ProcessTemplate> page = new Page<>(current, size);
        LambdaQueryWrapper<ProcessTemplate> wrapper = new LambdaQueryWrapper<>();
        
        if (key != null) {
            wrapper.eq(ProcessTemplate::getKey, key);
        }
        if (name != null) {
            wrapper.like(ProcessTemplate::getName, name);
        }
        if (category != null) {
            wrapper.eq(ProcessTemplate::getCategory, category);
        }
        
        return page(page, wrapper);
    }

    @Override
    public void deleteProcessTemplate(String id) {
        // 最小可用实现：直接删除
        this.removeById(id);
    }

    public ProcessTemplate getByBusinessTypeAndVersion(String businessType, String version) {
        LambdaQueryWrapper<ProcessTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProcessTemplate::getBusinessType, businessType)
               .eq(ProcessTemplate::getTemplateVersion, version);
        return getOne(wrapper);
    }
} 
