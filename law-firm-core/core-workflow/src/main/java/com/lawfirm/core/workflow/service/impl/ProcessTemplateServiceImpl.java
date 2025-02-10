package com.lawfirm.core.workflow.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.util.json.JsonUtils;
import com.lawfirm.core.workflow.entity.ProcessTemplateEntity;
import com.lawfirm.core.workflow.enums.ProcessTemplateEnum;
import com.lawfirm.core.workflow.repository.ProcessTemplateRepository;
import com.lawfirm.core.workflow.service.ProcessTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 流程模板管理服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessTemplateServiceImpl implements ProcessTemplateService {

    private final ProcessTemplateRepository processTemplateRepository;
    private final RepositoryService repositoryService;
    private final BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deployTemplate(ProcessTemplateEnum template, MultipartFile file) {
        try {
            // 验证流程定义
            if (!validateTemplate(file)) {
                throw new RuntimeException("流程定义验证失败");
            }

            // 部署流程定义
            Deployment deployment = repositoryService.createDeployment()
                    .addInputStream(file.getOriginalFilename(), file.getInputStream())
                    .name(template.getProcessName())
                    .category(template.getCategory())
                    .deploy();

            // 获取流程定义
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deployment.getId())
                    .singleResult();

            // 更新历史版本
            processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey())
                    .ifPresent(entity -> {
                        entity.setLatest(false);
                        processTemplateRepository.save(entity);
                    });

            // 保存流程模板
            ProcessTemplateEntity entity = new ProcessTemplateEntity()
                    .setProcessKey(template.getProcessKey())
                    .setProcessName(template.getProcessName())
                    .setCategory(template.getCategory())
                    .setDeploymentId(deployment.getId())
                    .setProcessDefinitionId(processDefinition.getId())
                    .setFileName(file.getOriginalFilename())
                    .setXmlContent(new String(file.getBytes()))
                    .setVersion(processDefinition.getVersion())
                    .setLatest(true)
                    .setCreateTime(LocalDateTime.now());

            processTemplateRepository.save(entity);

            return deployment.getId();
        } catch (IOException e) {
            throw new RuntimeException("部署流程模板失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateTemplate(ProcessTemplateEnum template, MultipartFile file) {
        // 删除旧版本
        Optional<ProcessTemplateEntity> optional = processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey());
        if (optional.isPresent()) {
            ProcessTemplateEntity oldEntity = optional.get();
            repositoryService.deleteDeployment(oldEntity.getDeploymentId(), true);
        }

        // 部署新版本
        return deployTemplate(template, file);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTemplate(ProcessTemplateEnum template, boolean cascade) {
        Optional<ProcessTemplateEntity> optional = processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey());
        if (optional.isPresent()) {
            ProcessTemplateEntity entity = optional.get();
            repositoryService.deleteDeployment(entity.getDeploymentId(), cascade);
            processTemplateRepository.delete(entity);
        }
    }

    @Override
    public String getTemplateDefinitionId(ProcessTemplateEnum template) {
        Optional<ProcessTemplateEntity> optional = processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey());
        return optional.map(ProcessTemplateEntity::getProcessDefinitionId).orElse(null);
    }

    @Override
    public InputStream getTemplateXml(ProcessTemplateEnum template) {
        Optional<ProcessTemplateEntity> optional = processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey());
        if (optional.isPresent()) {
            ProcessTemplateEntity entity = optional.get();
            return new ByteArrayInputStream(entity.getXmlContent().getBytes());
        }
        return null;
    }

    @Override
    public InputStream getTemplateDiagram(ProcessTemplateEnum template) {
        Optional<ProcessTemplateEntity> optional = processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey());
        if (optional.isPresent()) {
            ProcessTemplateEntity entity = optional.get();
            return repositoryService.getProcessDiagram(entity.getProcessDefinitionId());
        }
        return null;
    }

    @Override
    public List<ProcessTemplateEnum> listTemplates(String category) {
        return List.of(ProcessTemplateEnum.values()).stream()
                .filter(template -> category == null || category.equals(template.getCategory()))
                .toList();
    }

    @Override
    public Map<String, Object> getTemplateForm(ProcessTemplateEnum template) {
        Optional<ProcessTemplateEntity> optional = processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey());
        return optional.map(entity -> fromJson(entity.getFormDefinition())).orElse(null);
    }

    @Override
    public Map<String, Object> getTemplateNodes(ProcessTemplateEnum template) {
        Optional<ProcessTemplateEntity> optional = processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey());
        return optional.map(entity -> fromJson(entity.getNodeDefinition())).orElse(null);
    }

    @Override
    public boolean validateTemplate(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(() -> new ByteArrayInputStream(bytes), false, false);
            
            ProcessValidator processValidator = new ProcessValidatorFactory().createDefaultProcessValidator();
            List<ValidationError> errors = processValidator.validate(bpmnModel);
            return errors.isEmpty();
        } catch (IOException e) {
            throw new RuntimeException("验证流程模板失败", e);
        }
    }

    /**
     * 从JSON转换
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> fromJson(String json) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        return JsonUtils.fromJson(json, new TypeReference<Map<String, Object>>() {});
    }
} 