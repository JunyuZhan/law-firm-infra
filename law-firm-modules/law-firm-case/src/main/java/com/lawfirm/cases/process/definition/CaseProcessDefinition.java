package com.lawfirm.cases.process.definition;

import java.util.HashMap;
import java.util.Map;

/**
 * 案件流程定义
 * <p>
 * 定义案件处理的工作流程，包括不同阶段、节点和流转规则
 * </p>
 */
public class CaseProcessDefinition {

    // 流程状态定义
    public static final String STATUS_DRAFT = "draft";                  // 草稿
    public static final String STATUS_SUBMITTED = "submitted";          // 已提交
    public static final String STATUS_UNDER_REVIEW = "under_review";    // 审核中
    public static final String STATUS_APPROVED = "approved";            // 已批准
    public static final String STATUS_REJECTED = "rejected";            // 已拒绝
    public static final String STATUS_IN_PROGRESS = "in_progress";      // 进行中
    public static final String STATUS_COMPLETED = "completed";          // 已完成
    public static final String STATUS_CLOSED = "closed";                // 已关闭
    public static final String STATUS_CANCELED = "canceled";            // 已取消
    
    // 流程节点定义
    public static final String NODE_CREATE = "create";                  // 创建节点
    public static final String NODE_SUBMIT = "submit";                  // 提交节点
    public static final String NODE_REVIEW = "review";                  // 审核节点
    public static final String NODE_PROCESS = "process";                // 处理节点
    public static final String NODE_COMPLETE = "complete";              // 完成节点
    public static final String NODE_CLOSE = "close";                    // 关闭节点
    public static final String NODE_CANCEL = "cancel";                  // 取消节点
    
    // 流程角色定义
    public static final String ROLE_CREATOR = "creator";                // 创建者
    public static final String ROLE_SUBMITTER = "submitter";            // 提交者
    public static final String ROLE_REVIEWER = "reviewer";              // 审核者
    public static final String ROLE_HANDLER = "handler";                // 处理者
    public static final String ROLE_SUPERVISOR = "supervisor";          // 监督者
    public static final String ROLE_ADMINISTRATOR = "administrator";    // 管理员
    
    // 流程事件定义
    public static final String EVENT_CREATED = "created";               // 创建事件
    public static final String EVENT_SUBMITTED = "submitted";           // 提交事件
    public static final String EVENT_REVIEWED = "reviewed";             // 审核事件
    public static final String EVENT_APPROVED = "approved";             // 批准事件
    public static final String EVENT_REJECTED = "rejected";             // 拒绝事件
    public static final String EVENT_PROCESSED = "processed";           // 处理事件
    public static final String EVENT_COMPLETED = "completed";           // 完成事件
    public static final String EVENT_CLOSED = "closed";                 // 关闭事件
    public static final String EVENT_CANCELED = "canceled";             // 取消事件
    
    // 状态流转映射
    private static final Map<String, Map<String, String>> STATE_TRANSITION_MAP = new HashMap<>();
    
    static {
        // 草稿状态可流转到：已提交、已取消
        Map<String, String> draftTransitions = new HashMap<>();
        draftTransitions.put(EVENT_SUBMITTED, STATUS_SUBMITTED);
        draftTransitions.put(EVENT_CANCELED, STATUS_CANCELED);
        STATE_TRANSITION_MAP.put(STATUS_DRAFT, draftTransitions);
        
        // 已提交状态可流转到：审核中、已取消
        Map<String, String> submittedTransitions = new HashMap<>();
        submittedTransitions.put(EVENT_REVIEWED, STATUS_UNDER_REVIEW);
        submittedTransitions.put(EVENT_CANCELED, STATUS_CANCELED);
        STATE_TRANSITION_MAP.put(STATUS_SUBMITTED, submittedTransitions);
        
        // 审核中状态可流转到：已批准、已拒绝
        Map<String, String> underReviewTransitions = new HashMap<>();
        underReviewTransitions.put(EVENT_APPROVED, STATUS_APPROVED);
        underReviewTransitions.put(EVENT_REJECTED, STATUS_REJECTED);
        STATE_TRANSITION_MAP.put(STATUS_UNDER_REVIEW, underReviewTransitions);
        
        // 已批准状态可流转到：进行中
        Map<String, String> approvedTransitions = new HashMap<>();
        approvedTransitions.put(EVENT_PROCESSED, STATUS_IN_PROGRESS);
        STATE_TRANSITION_MAP.put(STATUS_APPROVED, approvedTransitions);
        
        // 已拒绝状态可流转到：草稿、已取消
        Map<String, String> rejectedTransitions = new HashMap<>();
        rejectedTransitions.put(EVENT_CREATED, STATUS_DRAFT);
        rejectedTransitions.put(EVENT_CANCELED, STATUS_CANCELED);
        STATE_TRANSITION_MAP.put(STATUS_REJECTED, rejectedTransitions);
        
        // 进行中状态可流转到：已完成、已取消
        Map<String, String> inProgressTransitions = new HashMap<>();
        inProgressTransitions.put(EVENT_COMPLETED, STATUS_COMPLETED);
        inProgressTransitions.put(EVENT_CANCELED, STATUS_CANCELED);
        STATE_TRANSITION_MAP.put(STATUS_IN_PROGRESS, inProgressTransitions);
        
        // 已完成状态可流转到：已关闭
        Map<String, String> completedTransitions = new HashMap<>();
        completedTransitions.put(EVENT_CLOSED, STATUS_CLOSED);
        STATE_TRANSITION_MAP.put(STATUS_COMPLETED, completedTransitions);
    }
    
    /**
     * 检查状态转换是否有效
     *
     * @param currentStatus 当前状态
     * @param event 触发事件
     * @return 是否可以转换
     */
    public static boolean isValidTransition(String currentStatus, String event) {
        Map<String, String> transitions = STATE_TRANSITION_MAP.get(currentStatus);
        return transitions != null && transitions.containsKey(event);
    }
    
    /**
     * 获取下一个状态
     *
     * @param currentStatus 当前状态
     * @param event 触发事件
     * @return 下一个状态，如果转换无效则返回null
     */
    public static String getNextStatus(String currentStatus, String event) {
        Map<String, String> transitions = STATE_TRANSITION_MAP.get(currentStatus);
        if (transitions != null) {
            return transitions.get(event);
        }
        return null;
    }
}
