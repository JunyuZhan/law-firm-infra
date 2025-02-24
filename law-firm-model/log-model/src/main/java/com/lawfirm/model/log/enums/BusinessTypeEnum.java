package com.lawfirm.model.log.enums;

import lombok.Getter;

/**
 * 业务类型枚举
 */
@Getter
public enum BusinessTypeEnum {

    /**
     * 用户管理
     */
    USER("USER", "用户管理"),

    /**
     * 角色管理
     */
    ROLE("ROLE", "角色管理"),

    /**
     * 菜单管理
     */
    MENU("MENU", "菜单管理"),

    /**
     * 部门管理
     */
    DEPT("DEPT", "部门管理"),

    /**
     * 岗位管理
     */
    POST("POST", "岗位管理"),

    /**
     * 字典管理
     */
    DICT("DICT", "字典管理"),

    /**
     * 参数设置
     */
    CONFIG("CONFIG", "参数设置"),

    /**
     * 通知公告
     */
    NOTICE("NOTICE", "通知公告"),

    /**
     * 日志管理
     */
    LOG("LOG", "日志管理"),

    /**
     * 案件管理
     */
    CASE("CASE", "案件管理"),

    /**
     * 合同管理
     */
    CONTRACT("CONTRACT", "合同管理"),

    /**
     * 客户管理
     */
    CLIENT("CLIENT", "客户管理"),

    /**
     * 文档管理
     */
    DOCUMENT("DOCUMENT", "文档管理"),

    /**
     * 其他
     */
    OTHER("OTHER", "其他");

    /**
     * 业务类型编码
     */
    private final String code;

    /**
     * 业务类型描述
     */
    private final String desc;

    BusinessTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 