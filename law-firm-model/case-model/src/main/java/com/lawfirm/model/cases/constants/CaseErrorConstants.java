package com.lawfirm.model.cases.constants;

/**
 * 案件错误码常量
 */
public interface CaseErrorConstants {

    /**
     * 系统错误码（5位数字：1xxxx）
     */
    interface System {
        /**
         * 系统内部错误
         */
        String INTERNAL_ERROR = "10000";

        /**
         * 数据库操作错误
         */
        String DATABASE_ERROR = "10001";

        /**
         * 缓存操作错误
         */
        String CACHE_ERROR = "10002";

        /**
         * 文件操作错误
         */
        String FILE_ERROR = "10003";

        /**
         * 网络请求错误
         */
        String NETWORK_ERROR = "10004";
    }

    /**
     * 业务错误码（5位数字：2xxxx）
     */
    interface Business {
        /**
         * 案件不存在
         */
        String CASE_NOT_FOUND = "20000";

        /**
         * 案件编号已存在
         */
        String CASE_NUMBER_EXISTS = "20001";

        /**
         * 案件编号格式错误
         */
        String INVALID_CASE_NUMBER = "20002";

        /**
         * 案件状态错误
         */
        String INVALID_STATUS = "20003";

        /**
         * 状态流转错误
         */
        String INVALID_STATUS_CHANGE = "20004";

        /**
         * 案件类型错误
         */
        String INVALID_CASE_TYPE = "20005";

        /**
         * 案由错误
         */
        String INVALID_CASE_CAUSE = "20006";

        /**
         * 利益冲突
         */
        String CONFLICT_OF_INTEREST = "20007";
    }

    /**
     * 参数错误码（5位数字：3xxxx）
     */
    interface Parameter {
        /**
         * 参数为空
         */
        String PARAMETER_EMPTY = "30000";

        /**
         * 参数格式错误
         */
        String PARAMETER_FORMAT_ERROR = "30001";

        /**
         * 参数值错误
         */
        String PARAMETER_VALUE_ERROR = "30002";

        /**
         * 必填参数缺失
         */
        String REQUIRED_PARAMETER_MISSING = "30003";

        /**
         * 参数超出范围
         */
        String PARAMETER_OUT_OF_RANGE = "30004";
    }

    /**
     * 权限错误码（5位数字：4xxxx）
     */
    interface Permission {
        /**
         * 未登录
         */
        String NOT_LOGIN = "40000";

        /**
         * 无权限
         */
        String NO_PERMISSION = "40001";

        /**
         * 登录过期
         */
        String LOGIN_EXPIRED = "40002";

        /**
         * 角色权限不足
         */
        String INSUFFICIENT_ROLE = "40003";

        /**
         * 需要审批
         */
        String NEED_APPROVAL = "40004";
    }

    /**
     * 状态错误码（5位数字：5xxxx）
     */
    interface Status {
        /**
         * 状态不允许操作
         */
        String STATUS_NOT_ALLOWED = "50000";

        /**
         * 状态已变更
         */
        String STATUS_CHANGED = "50001";

        /**
         * 状态冲突
         */
        String STATUS_CONFLICT = "50002";

        /**
         * 状态已锁定
         */
        String STATUS_LOCKED = "50003";

        /**
         * 状态已过期
         */
        String STATUS_EXPIRED = "50004";
    }
} 