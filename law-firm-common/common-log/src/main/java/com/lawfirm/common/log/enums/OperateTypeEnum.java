package com.lawfirm.common.log.enums;

import lombok.Getter;

/**
 * 操作类型枚举
 */
@Getter
public enum OperateTypeEnum {

    /**
     * 查询
     */
    QUERY("QUERY", "查询"),

    /**
     * 新增
     */
    CREATE("CREATE", "新增"),

    /**
     * 修改
     */
    UPDATE("UPDATE", "修改"),

    /**
     * 删除
     */
    DELETE("DELETE", "删除"),

    /**
     * 授权
     */
    GRANT("GRANT", "授权"),

    /**
     * 导出
     */
    EXPORT("EXPORT", "导出"),

    /**
     * 导入
     */
    IMPORT("IMPORT", "导入"),

    /**
     * 强制退出
     */
    FORCE_LOGOUT("FORCE_LOGOUT", "强制退出"),

    /**
     * 清空数据
     */
    CLEAN("CLEAN", "清空数据"),

    /**
     * 其他
     */
    OTHER("OTHER", "其他");

    /**
     * 操作类型编码
     */
    private final String code;

    /**
     * 操作类型描述
     */
    private final String desc;

    OperateTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 