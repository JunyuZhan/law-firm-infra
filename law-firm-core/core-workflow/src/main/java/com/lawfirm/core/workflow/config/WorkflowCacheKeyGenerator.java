package com.lawfirm.core.workflow.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * 工作流缓存键生成器
 */
@Component
public class WorkflowCacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return generateKey(params);
    }
    
    /**
     * 生成缓存键
     */
    private Object generateKey(Object... params) {
        if (params.length == 0) {
            return new Object();
        }
        if (params.length == 1) {
            Object param = params[0];
            if (param != null && !param.getClass().isArray()) {
                return param;
            }
        }
        return Arrays.asList(params);
    }
    
    /**
     * 生成任务查询缓存键
     */
    public String generateTaskQueryKey(String processInstanceId, String taskDefinitionKey,
                                     String assignee, String owner, String tenantId) {
        StringBuilder key = new StringBuilder("taskQuery:");
        
        if (StringUtils.hasText(processInstanceId)) {
            key.append("pi=").append(processInstanceId).append("_");
        }
        if (StringUtils.hasText(taskDefinitionKey)) {
            key.append("tk=").append(taskDefinitionKey).append("_");
        }
        if (StringUtils.hasText(assignee)) {
            key.append("as=").append(assignee).append("_");
        }
        if (StringUtils.hasText(owner)) {
            key.append("ow=").append(owner).append("_");
        }
        if (StringUtils.hasText(tenantId)) {
            key.append("te=").append(tenantId);
        }
        
        return key.toString();
    }
    
    /**
     * 生成流程定义缓存键
     */
    public String generateProcessDefinitionKey(String processDefinitionId) {
        return "processDefinition:" + processDefinitionId;
    }
    
    /**
     * 生成流程实例缓存键
     */
    public String generateProcessInstanceKey(String processInstanceId) {
        return "processInstance:" + processInstanceId;
    }
} 