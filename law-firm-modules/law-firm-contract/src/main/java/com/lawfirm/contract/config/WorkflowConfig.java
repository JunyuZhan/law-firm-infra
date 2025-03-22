package com.lawfirm.contract.config;

import org.springframework.context.annotation.Configuration;

/**
 * 工作流配置类
 * 负责合同模块与core-workflow组件的集成配置
 */
@Configuration("contractWorkflowConfig")
public class WorkflowConfig {
    
    // 工作流相关的配置会在这里定义
    // 例如：流程定义键、任务类型等常量
    
    /**
     * 合同审批流程定义键
     */
    public static final String CONTRACT_APPROVAL_PROCESS_KEY = "contract_approval";
    
    /**
     * 合同变更审批流程定义键
     */
    public static final String CONTRACT_CHANGE_PROCESS_KEY = "contract_change";
    
    /**
     * 合同终止审批流程定义键
     */
    public static final String CONTRACT_TERMINATE_PROCESS_KEY = "contract_terminate";
    
    /**
     * 合同关联的业务前缀
     */
    public static final String CONTRACT_BUSINESS_KEY_PREFIX = "CONTRACT_";
    
    /**
     * 审批通过结果
     */
    public static final String APPROVAL_RESULT_APPROVE = "APPROVE";
    
    /**
     * 审批拒绝结果
     */
    public static final String APPROVAL_RESULT_REJECT = "REJECT";
} 