package com.lawfirm.model.cases.constants;

/**
 * 案件常量类
 * 定义案件相关常量
 */
public interface CaseConstants {

    /**
     * 案件团队角色：管理员
     */
    String ROLE_CASE_ADMIN = "CASE_ADMIN";

    /**
     * 案件团队角色：负责人
     */
    String ROLE_CASE_LEADER = "CASE_LEADER";

    /**
     * 案件团队角色：协办律师
     */
    String ROLE_CASE_ASSISTANT = "CASE_ASSISTANT";

    /**
     * 案件团队角色：普通成员
     */
    String ROLE_CASE_MEMBER = "CASE_MEMBER";

    /**
     * 案件状态：草稿
     */
    String STATUS_DRAFT = "DRAFT";

    /**
     * 案件状态：待审核
     */
    String STATUS_PENDING = "PENDING";

    /**
     * 案件状态：进行中
     */
    String STATUS_ACTIVE = "ACTIVE";

    /**
     * 案件状态：已完成
     */
    String STATUS_COMPLETED = "COMPLETED";

    /**
     * 案件状态：已归档
     */
    String STATUS_ARCHIVED = "ARCHIVED";

    /**
     * 案件状态：已取消
     */
    String STATUS_CANCELLED = "CANCELLED";
} 