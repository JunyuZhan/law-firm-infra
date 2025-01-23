package com.lawfirm.core.workflow.service.impl;

import com.lawfirm.core.workflow.model.Form;
import com.lawfirm.core.workflow.service.IFormService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.form.TaskFormData;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 表单服务实现
 */
@Slf4j
@Service
public class FormServiceImpl implements IFormService {
    
    private final org.flowable.engine.FormService flowableFormService;
    private final org.flowable.engine.TaskService taskService;
    private final RuntimeService runtimeService;
    private final MongoTemplate mongoTemplate;
    
    public FormServiceImpl(org.flowable.engine.FormService flowableFormService, 
                         org.flowable.engine.TaskService taskService,
                         RuntimeService runtimeService,
                         MongoTemplate mongoTemplate) {
        this.flowableFormService = flowableFormService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.mongoTemplate = mongoTemplate;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Form createForm(Form form) {
        form.setCreateTime(LocalDateTime.now());
        form.setUpdateTime(LocalDateTime.now());
        return mongoTemplate.save(form);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Form updateForm(Form form) {
        form.setUpdateTime(LocalDateTime.now());
        return mongoTemplate.save(form);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteForm(String formId) {
        Query query = new Query(Criteria.where("id").is(formId));
        mongoTemplate.remove(query, Form.class);
    }
    
    @Override
    public Form getForm(String formId) {
        return mongoTemplate.findById(formId, Form.class);
    }
    
    @Override
    public Form getFormByKey(String formKey) {
        Query query = new Query(Criteria.where("key").is(formKey));
        return mongoTemplate.findOne(query, Form.class);
    }
    
    @Override
    public List<Form> listForms(String key, String name, String category, String tenantId) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        
        if (StringUtils.hasText(key)) {
            criteria.and("key").is(key);
        }
        if (StringUtils.hasText(name)) {
            criteria.and("name").regex(name);
        }
        if (StringUtils.hasText(category)) {
            criteria.and("category").is(category);
        }
        if (StringUtils.hasText(tenantId)) {
            criteria.and("tenantId").is(tenantId);
        }
        
        query.addCriteria(criteria);
        return mongoTemplate.find(query, Form.class);
    }
    
    @Override
    public Form getTaskForm(String taskId) {
        TaskFormData taskFormData = flowableFormService.getTaskFormData(taskId);
        if (taskFormData != null && StringUtils.hasText(taskFormData.getFormKey())) {
            return getFormByKey(taskFormData.getFormKey());
        }
        return null;
    }
    
    @Override
    public Form getStartForm(String processDefinitionId) {
        StartFormData startFormData = flowableFormService.getStartFormData(processDefinitionId);
        if (startFormData != null && StringUtils.hasText(startFormData.getFormKey())) {
            return getFormByKey(startFormData.getFormKey());
        }
        return null;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitTaskForm(String taskId, String formId, Map<String, Object> variables) {
        Form form = getForm(formId);
        if (form == null) {
            throw new IllegalArgumentException("Form not found: " + formId);
        }
        
        if (variables != null && !variables.isEmpty()) {
            taskService.complete(taskId, variables);
        } else {
            taskService.complete(taskId);
        }
        
        log.info("Task form submitted: taskId={}, formId={}", taskId, formId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String submitStartForm(String processDefinitionId, String formId, Map<String, Object> variables) {
        Form form = getForm(formId);
        if (form == null) {
            throw new IllegalArgumentException("Form not found: " + formId);
        }
        
        org.flowable.engine.runtime.ProcessInstance processInstance;
        if (variables != null && !variables.isEmpty()) {
            processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
        } else {
            processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
        }
        
        log.info("Start form submitted: processDefinitionId={}, formId={}, processInstanceId={}",
                processDefinitionId, formId, processInstance.getId());
        
        return processInstance.getId();
    }
} 