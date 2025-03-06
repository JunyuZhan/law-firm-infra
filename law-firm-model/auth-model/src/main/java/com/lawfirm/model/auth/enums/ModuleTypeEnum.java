package com.lawfirm.model.auth.enums;

import lombok.Getter;
import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 功能模块枚举
 */
@Getter
public enum ModuleTypeEnum implements BaseEnum<String> {
    
    /**
     * 仪表盘
     */
    DASHBOARD("dashboard", "仪表盘", OperationTypeEnum.READ_ONLY),
    
    /**
     * 案件管理
     */
    CASE("case", "案件管理", OperationTypeEnum.FULL),
    
    /**
     * 客户管理
     */
    CLIENT("client", "客户管理", OperationTypeEnum.FULL),
    
    /**
     * 合同管理
     */
    CONTRACT("contract", "合同管理", OperationTypeEnum.APPROVE),
    
    /**
     * 文档管理
     */
    DOCUMENT("document", "文档管理", OperationTypeEnum.TEAM),
    
    /**
     * 财务管理
     */
    FINANCE("finance", "财务管理", OperationTypeEnum.APPROVE),
    
    /**
     * 行政管理
     */
    ADMIN("admin", "行政管理", OperationTypeEnum.APPROVE),
    
    /**
     * 系统设置
     */
    SYSTEM("system", "系统设置", OperationTypeEnum.FULL),
    
    /**
     * 知识库管理
     */
    KNOWLEDGE("knowledge", "知识库管理", OperationTypeEnum.TEAM),
    
    /**
     * 人力资源
     */
    HR("hr", "人力资源", OperationTypeEnum.APPROVE),
    
    /**
     * 任务管理
     */
    TASK("task", "任务管理", OperationTypeEnum.TEAM),
    
    /**
     * 日程管理
     */
    SCHEDULE("schedule", "日程管理", OperationTypeEnum.TEAM),
    
    /**
     * 消息管理
     */
    MESSAGE("message", "消息管理", OperationTypeEnum.TEAM),
    
    /**
     * 利益冲突
     */
    CONFLICT("conflict", "利益冲突", OperationTypeEnum.APPROVE),
    
    /**
     * 工作流程
     */
    WORKFLOW("workflow", "工作流程", OperationTypeEnum.APPROVE),
    
    /**
     * 数据分析
     */
    ANALYSIS("analysis", "数据分析", OperationTypeEnum.READ_ONLY);

    private final String code;
    private final String desc;
    private final OperationTypeEnum defaultOperation;
    
    ModuleTypeEnum(String code, String desc, OperationTypeEnum defaultOperation) {
        this.code = code;
        this.desc = desc;
        this.defaultOperation = defaultOperation;
    }

    @Override
    public String getValue() {
        return code;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    /**
     * 获取模块的默认操作权限
     */
    public OperationTypeEnum getDefaultOperation() {
        return defaultOperation;
    }
} 