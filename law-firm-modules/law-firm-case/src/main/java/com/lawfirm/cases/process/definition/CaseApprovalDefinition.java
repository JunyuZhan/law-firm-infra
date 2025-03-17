package com.lawfirm.cases.process.definition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 案件审批流程定义
 * <p>
 * 定义案件审批的流程，包括审批阶段、审批人角色和审批规则
 * </p>
 */
public class CaseApprovalDefinition {

    // 审批类型定义
    public static final String TYPE_CASE_CREATE = "case_create";           // 案件创建审批
    public static final String TYPE_CASE_CLOSE = "case_close";             // 案件结案审批
    public static final String TYPE_DOCUMENT_CONFIDENTIAL = "doc_conf";    // 文档保密审批
    public static final String TYPE_EXPENSE = "expense";                   // 费用审批
    public static final String TYPE_TEAM_CHANGE = "team_change";           // 团队变更审批
    
    // 审批状态定义
    public static final String STATUS_PENDING = "pending";             // 待审批
    public static final String STATUS_APPROVED = "approved";           // 已批准
    public static final String STATUS_REJECTED = "rejected";           // 已拒绝
    public static final String STATUS_WITHDRAWN = "withdrawn";         // 已撤回
    
    // 审批人角色定义
    public static final String ROLE_DEPARTMENT_MANAGER = "dept_manager";         // 部门经理
    public static final String ROLE_CASE_MANAGER = "case_manager";               // 案件管理员
    public static final String ROLE_FINANCIAL_MANAGER = "financial_manager";     // 财务经理
    public static final String ROLE_SENIOR_PARTNER = "senior_partner";           // 高级合伙人
    public static final String ROLE_MANAGING_PARTNER = "managing_partner";       // 管理合伙人
    public static final String ROLE_COMPLIANCE_OFFICER = "compliance_officer";   // 合规官
    
    // 审批流程定义，每种审批类型对应的审批角色链
    private static final Map<String, List<String>> APPROVAL_FLOW_MAP = new HashMap<>();
    
    static {
        // 案件创建审批流程：部门经理 -> 案件管理员
        APPROVAL_FLOW_MAP.put(TYPE_CASE_CREATE, 
                Arrays.asList(ROLE_DEPARTMENT_MANAGER, ROLE_CASE_MANAGER));
        
        // 案件结案审批流程：部门经理 -> 高级合伙人
        APPROVAL_FLOW_MAP.put(TYPE_CASE_CLOSE, 
                Arrays.asList(ROLE_DEPARTMENT_MANAGER, ROLE_SENIOR_PARTNER));
        
        // 文档保密审批流程：合规官
        APPROVAL_FLOW_MAP.put(TYPE_DOCUMENT_CONFIDENTIAL, 
                Arrays.asList(ROLE_COMPLIANCE_OFFICER));
        
        // 费用审批流程：部门经理 -> 财务经理 -> 管理合伙人(金额大时)
        APPROVAL_FLOW_MAP.put(TYPE_EXPENSE, 
                Arrays.asList(ROLE_DEPARTMENT_MANAGER, ROLE_FINANCIAL_MANAGER, ROLE_MANAGING_PARTNER));
        
        // 团队变更审批流程：部门经理 -> 高级合伙人
        APPROVAL_FLOW_MAP.put(TYPE_TEAM_CHANGE, 
                Arrays.asList(ROLE_DEPARTMENT_MANAGER, ROLE_SENIOR_PARTNER));
    }
    
    /**
     * 获取审批流程中的角色列表
     *
     * @param approvalType 审批类型
     * @return 审批角色列表
     */
    public static List<String> getApprovalRoles(String approvalType) {
        return APPROVAL_FLOW_MAP.getOrDefault(approvalType, Arrays.asList(ROLE_DEPARTMENT_MANAGER));
    }
    
    /**
     * 获取下一级审批人角色
     *
     * @param approvalType 审批类型
     * @param currentRole 当前角色
     * @return 下一级审批角色，如果当前已是最后一级则返回null
     */
    public static String getNextApprovalRole(String approvalType, String currentRole) {
        List<String> roles = getApprovalRoles(approvalType);
        int currentIndex = roles.indexOf(currentRole);
        
        if (currentIndex >= 0 && currentIndex < roles.size() - 1) {
            return roles.get(currentIndex + 1);
        }
        
        return null;
    }
    
    /**
     * 检查是否为最终审批人
     *
     * @param approvalType 审批类型
     * @param role 角色
     * @return 是否为最终审批人
     */
    public static boolean isFinalApprover(String approvalType, String role) {
        List<String> roles = getApprovalRoles(approvalType);
        return !roles.isEmpty() && roles.get(roles.size() - 1).equals(role);
    }
    
    /**
     * 判断角色是否有权审批
     *
     * @param approvalType 审批类型
     * @param role 角色
     * @return 是否有审批权限
     */
    public static boolean hasApprovalAuthority(String approvalType, String role) {
        return getApprovalRoles(approvalType).contains(role);
    }
}
