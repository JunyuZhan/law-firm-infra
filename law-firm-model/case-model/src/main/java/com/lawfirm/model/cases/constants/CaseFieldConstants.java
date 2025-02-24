package com.lawfirm.model.cases.constants;

/**
 * 案件字段限制常量
 */
public interface CaseFieldConstants {

    /**
     * 案件编号相关限制
     */
    interface CaseNumber {
        /**
         * 最小长度
         */
        int MIN_LENGTH = 8;

        /**
         * 最大长度
         */
        int MAX_LENGTH = 32;

        /**
         * 编号格式正则
         * 格式：CASE-{年份}-{6位数字序号}
         * 示例：CASE-2024-000001
         */
        String PATTERN = "^CASE-\\d{4}-\\d{6}$";

        /**
         * 前缀
         */
        String PREFIX = "CASE-";
    }

    /**
     * 案件名称相关限制
     */
    interface CaseName {
        /**
         * 最小长度
         */
        int MIN_LENGTH = 4;

        /**
         * 最大长度
         */
        int MAX_LENGTH = 128;
    }

    /**
     * 案件描述相关限制
     */
    interface CaseDescription {
        /**
         * 最大长度
         */
        int MAX_LENGTH = 2000;
    }

    /**
     * 金额相关限制
     */
    interface Amount {
        /**
         * 最大金额
         */
        long MAX_AMOUNT = 999999999999L;

        /**
         * 金额精度（小数位数）
         */
        int PRECISION = 2;
    }

    /**
     * 法院相关字段限制
     */
    interface Court {
        /**
         * 法院名称最大长度
         */
        int NAME_MAX_LENGTH = 64;

        /**
         * 法庭最大长度
         */
        int TRIBUNAL_MAX_LENGTH = 32;

        /**
         * 法官姓名最大长度
         */
        int JUDGE_NAME_MAX_LENGTH = 16;
    }

    /**
     * 律师相关字段限制
     */
    interface Lawyer {
        /**
         * 律师姓名最大长度
         */
        int NAME_MAX_LENGTH = 16;

        /**
         * 执业证号最大长度
         */
        int LICENSE_MAX_LENGTH = 32;
    }

    /**
     * 委托人相关字段限制
     */
    interface Client {
        /**
         * 委托人姓名最大长度
         */
        int NAME_MAX_LENGTH = 32;

        /**
         * 证件号码最大长度
         */
        int ID_NUMBER_MAX_LENGTH = 32;

        /**
         * 联系电话最大长度
         */
        int PHONE_MAX_LENGTH = 16;

        /**
         * 电子邮箱最大长度
         */
        int EMAIL_MAX_LENGTH = 64;

        /**
         * 地址最大长度
         */
        int ADDRESS_MAX_LENGTH = 256;
    }

    /**
     * 分页相关限制
     */
    interface Page {
        /**
         * 默认页码
         */
        int DEFAULT_PAGE_NUM = 1;

        /**
         * 默认每页大小
         */
        int DEFAULT_PAGE_SIZE = 10;

        /**
         * 最大每页大小
         */
        int MAX_PAGE_SIZE = 100;
    }

    /**
     * 文件相关限制
     */
    interface File {
        /**
         * 最大文件大小（单位：MB）
         */
        long MAX_FILE_SIZE = 100;

        /**
         * 文件名最大长度
         */
        int NAME_MAX_LENGTH = 128;

        /**
         * 允许的文件类型
         */
        String[] ALLOWED_EXTENSIONS = {
            ".doc", ".docx", ".pdf", ".jpg", ".jpeg", 
            ".png", ".xls", ".xlsx", ".txt", ".zip", 
            ".rar", ".7z"
        };
    }

    /**
     * 备注相关限制
     */
    interface Remark {
        /**
         * 最大长度
         */
        int MAX_LENGTH = 500;
    }
} 