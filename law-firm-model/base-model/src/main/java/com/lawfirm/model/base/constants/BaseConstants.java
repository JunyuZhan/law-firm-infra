package com.lawfirm.model.base.constants;

/**
 * 基础常量
 */
public interface BaseConstants {

    /**
     * 系统默认值
     */
    interface Default {
        /**
         * 默认页码
         */
        int PAGE_NUM = 1;

        /**
         * 默认每页大小
         */
        int PAGE_SIZE = 10;

        /**
         * 默认排序方式
         */
        String ORDER_TYPE = "desc";

        /**
         * 默认状态（启用）
         */
        int STATUS_ENABLED = 0;

        /**
         * 默认状态（禁用）
         */
        int STATUS_DISABLED = 1;
    }

    /**
     * 通用字段名
     */
    interface Field {
        /**
         * ID字段
         */
        String ID = "id";

        /**
         * 创建时间字段
         */
        String CREATE_TIME = "create_time";

        /**
         * 更新时间字段
         */
        String UPDATE_TIME = "update_time";

        /**
         * 状态字段
         */
        String STATUS = "status";

        /**
         * 排序字段
         */
        String SORT = "sort";

        /**
         * 租户ID字段
         */
        String TENANT_ID = "tenant_id";
    }

    /**
     * 通用符号
     */
    interface Symbol {
        String COMMA = ",";
        String DOT = ".";
        String COLON = ":";
        String SEMICOLON = ";";
        String UNDERSCORE = "_";
        String HYPHEN = "-";
        String SLASH = "/";
        String BACKSLASH = "\\";
        String SPACE = " ";
        String EMPTY = "";
    }
} 