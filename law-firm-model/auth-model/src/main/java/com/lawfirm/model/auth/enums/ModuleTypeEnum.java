package com.lawfirm.model.auth.enums;

import lombok.Getter;

/**
 * 系统模块类型枚举（通用模块，不包含业务特定模块）
 */
@Getter
public enum ModuleTypeEnum {

    SYSTEM("system", "系统管理", "系统管理模块"),
    USER("user", "用户管理", "用户管理模块"),
    ROLE("role", "角色管理", "角色管理模块"),
    PERMISSION("permission", "权限管理", "权限管理模块"),
    DOCUMENT("document", "文档管理", "通用文档管理"),
    SCHEDULE("schedule", "日程管理", "通用日程管理"),
    MESSAGE("message", "消息管理", "通用消息服务"),
    CONTRACT("contract", "合同管理", "合同业务模块"),
    CASE("case", "案件管理", "案件业务模块"),
    CLIENT("client", "客户管理", "客户业务模块"),
    FINANCE("finance", "财务管理", "财务业务模块"),
    PERSONNEL("personnel", "人事管理", "人事业务模块"),
    KNOWLEDGE("knowledge", "知识库", "知识库业务模块"),
    ANALYSIS("analysis", "分析管理", "分析业务模块");

    private final String code;
    private final String name;
    private final String description;

    ModuleTypeEnum(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
} 