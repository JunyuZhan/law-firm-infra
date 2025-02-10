package com.lawfirm.core.workflow.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.core.workflow.entity.BusinessProcessEntity;
import com.lawfirm.core.workflow.model.BusinessProcess;
import com.lawfirm.core.workflow.repository.BusinessProcessRepository;
import com.lawfirm.core.workflow.service.BusinessProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 业务流程关联服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessProcessServiceImpl implements BusinessProcessService {

    private final BusinessProcessRepository businessProcessRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessProcess createBusinessProcess(BusinessProcess businessProcess) {
        BusinessProcessEntity entity = convertToEntity(businessProcess);
        entity.setCreateTime(LocalDateTime.now());
        entity = businessProcessRepository.save(entity);
        return convertToModel(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessProcess updateBusinessProcess(BusinessProcess businessProcess) {
        Optional<BusinessProcessEntity> optional = businessProcessRepository.findByBusinessTypeAndBusinessId(
                businessProcess.getBusinessType(), businessProcess.getBusinessId());
        if (optional.isEmpty()) {
            throw new RuntimeException("业务流程关联不存在");
        }

        BusinessProcessEntity entity = optional.get();
        updateEntity(entity, businessProcess);
        entity = businessProcessRepository.save(entity);
        return convertToModel(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBusinessProcess(String businessType, String businessId) {
        businessProcessRepository.deleteByBusinessTypeAndBusinessId(businessType, businessId);
    }

    @Override
    public BusinessProcess getBusinessProcess(String businessType, String businessId) {
        Optional<BusinessProcessEntity> optional = businessProcessRepository.findByBusinessTypeAndBusinessId(businessType, businessId);
        return optional.map(this::convertToModel).orElse(null);
    }

    @Override
    public BusinessProcess getByProcessInstanceId(String processInstanceId) {
        Optional<BusinessProcessEntity> optional = businessProcessRepository.findByProcessInstanceId(processInstanceId);
        return optional.map(this::convertToModel).orElse(null);
    }

    @Override
    public List<BusinessProcess> listBusinessProcesses(String businessType, String processStatus, String startUserId) {
        List<BusinessProcessEntity> entities = businessProcessRepository
                .findByBusinessTypeAndProcessStatusAndStartUserId(businessType, processStatus, startUserId);
        return entities.stream().map(this::convertToModel).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProcessStatus(String processInstanceId, String processStatus) {
        Optional<BusinessProcessEntity> optional = businessProcessRepository.findByProcessInstanceId(processInstanceId);
        if (optional.isPresent()) {
            BusinessProcessEntity entity = optional.get();
            entity.setProcessStatus(processStatus);
            if ("completed".equals(processStatus) || "terminated".equals(processStatus)) {
                entity.setEndTime(LocalDateTime.now());
            }
            entity.setUpdateTime(LocalDateTime.now());
            businessProcessRepository.save(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCurrentTask(String processInstanceId, String taskId, String taskName, String assignee) {
        Optional<BusinessProcessEntity> optional = businessProcessRepository.findByProcessInstanceId(processInstanceId);
        if (optional.isPresent()) {
            BusinessProcessEntity entity = optional.get();
            entity.setCurrentTaskId(taskId)
                    .setCurrentTaskName(taskName)
                    .setCurrentAssignee(assignee)
                    .setUpdateTime(LocalDateTime.now());
            businessProcessRepository.save(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFormData(String processInstanceId, Map<String, Object> formData) {
        Optional<BusinessProcessEntity> optional = businessProcessRepository.findByProcessInstanceId(processInstanceId);
        if (optional.isPresent()) {
            BusinessProcessEntity entity = optional.get();
            entity.setFormData(convertToJson(formData))
                    .setUpdateTime(LocalDateTime.now());
            businessProcessRepository.save(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProcessVariables(String processInstanceId, Map<String, Object> variables) {
        Optional<BusinessProcessEntity> optional = businessProcessRepository.findByProcessInstanceId(processInstanceId);
        if (optional.isPresent()) {
            BusinessProcessEntity entity = optional.get();
            entity.setProcessVariables(convertVariablesToJson(variables))
                    .setUpdateTime(LocalDateTime.now());
            businessProcessRepository.save(entity);
        }
    }

    /**
     * 转换为实体
     */
    private BusinessProcessEntity convertToEntity(BusinessProcess model) {
        return new BusinessProcessEntity()
                .setBusinessType(model.getBusinessType())
                .setBusinessId(model.getBusinessId())
                .setProcessDefinitionId(model.getProcessDefinitionId())
                .setProcessInstanceId(model.getProcessInstanceId())
                .setCurrentTaskId(model.getCurrentTaskId())
                .setCurrentTaskName(model.getCurrentTaskName())
                .setCurrentAssignee(model.getCurrentAssignee())
                .setFormData(convertToJson(model.getFormData()))
                .setProcessVariables(convertVariablesToJson(model.getProcessVariables()))
                .setTenantId(model.getTenantId())
                .setRemark(model.getRemark());
    }

    /**
     * 更新实体
     */
    private void updateEntity(BusinessProcessEntity entity, BusinessProcess model) {
        entity.setBusinessTitle(model.getBusinessTitle())
                .setProcessInstanceId(model.getProcessInstanceId())
                .setProcessDefinitionId(model.getProcessDefinitionId())
                .setProcessDefinitionKey(model.getProcessDefinitionKey())
                .setStartUserId(model.getStartUserId())
                .setStartTime(model.getStartTime())
                .setEndTime(model.getEndTime())
                .setProcessStatus(model.getProcessStatus())
                .setCurrentTaskId(model.getCurrentTaskId())
                .setCurrentTaskName(model.getCurrentTaskName())
                .setCurrentAssignee(model.getCurrentAssignee())
                .setFormData(convertToJson(model.getFormData()))
                .setProcessVariables(convertVariablesToJson(model.getProcessVariables()))
                .setTenantId(model.getTenantId())
                .setRemark(model.getRemark())
                .setUpdateTime(LocalDateTime.now());
    }

    /**
     * 转换为模型
     */
    private BusinessProcess convertToModel(BusinessProcessEntity entity) {
        if (entity == null) {
            return null;
        }

        return new BusinessProcess()
                .setBusinessType(entity.getBusinessType())
                .setBusinessId(entity.getBusinessId())
                .setBusinessTitle(entity.getBusinessTitle())
                .setProcessInstanceId(entity.getProcessInstanceId())
                .setProcessDefinitionId(entity.getProcessDefinitionId())
                .setProcessDefinitionKey(entity.getProcessDefinitionKey())
                .setStartUserId(entity.getStartUserId())
                .setStartTime(entity.getStartTime())
                .setEndTime(entity.getEndTime())
                .setProcessStatus(entity.getProcessStatus())
                .setCurrentTaskId(entity.getCurrentTaskId())
                .setCurrentTaskName(entity.getCurrentTaskName())
                .setCurrentAssignee(entity.getCurrentAssignee())
                .setFormData(convertFromJson(entity.getFormData(), Map.class))
                .setProcessVariables(convertJsonToVariables(entity.getProcessVariables()))
                .setTenantId(entity.getTenantId())
                .setRemark(entity.getRemark());
    }

    /**
     * 转换为JSON
     */
    private <T> String convertToJson(T data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("数据转换为JSON失败", e);
            throw new RuntimeException("数据转换为JSON失败", e);
        }
    }

    /**
     * 从JSON转换
     */
    private <T> T convertFromJson(String json, Class<T> valueType) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            log.error("JSON转换为数据失败", e);
            throw new RuntimeException("JSON转换为数据失败", e);
        }
    }

    private String convertVariablesToJson(Map<String, Object> variables) {
        if (variables == null || variables.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(variables);
        } catch (JsonProcessingException e) {
            log.error("变量转换为JSON失败", e);
            throw new RuntimeException("变量转换为JSON失败", e);
        }
    }

    private Map<String, Object> convertJsonToVariables(String variablesJson) {
        if (!StringUtils.hasText(variablesJson)) {
            return null;
        }
        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(variablesJson, typeRef);
        } catch (JsonProcessingException e) {
            log.error("JSON转换为变量失败", e);
            throw new RuntimeException("JSON转换为变量失败", e);
        }
    }
} 