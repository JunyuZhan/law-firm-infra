package com.lawfirm.model.organization.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 律所字段常量
 */
public interface FirmFieldConstants extends BaseConstants {

    /**
     * 律所名称长度限制
     */
    interface Name extends BaseConstants.Field {
        /**
         * 最小长度
         */
        int MIN_LENGTH = 4;

        /**
         * 最大长度
         */
        int MAX_LENGTH = 128;

        /**
         * 名称字段
         */
        String FIELD = "name";
    }

    /**
     * 执业许可证信息
     */
    interface License {
        /**
         * 许可证号长度
         */
        int NUMBER_LENGTH = 18;

        /**
         * 许可证号格式正则表达式
         */
        String NUMBER_PATTERN = "^[A-Z0-9]{18}$";

        /**
         * 许可证号字段
         */
        String NUMBER_FIELD = "license_number";

        /**
         * 许可证有效期字段
         */
        String EXPIRE_DATE_FIELD = "license_expire_date";
    }

    /**
     * 营业信息限制
     */
    interface Business {
        /**
         * 统一社会信用代码长度
         */
        int CREDIT_CODE_LENGTH = 18;

        /**
         * 统一社会信用代码格式正则表达式
         */
        String CREDIT_CODE_PATTERN = "^[A-Z0-9]{18}$";

        /**
         * 法定代表人姓名最大长度
         */
        int LEGAL_REPRESENTATIVE_MAX_LENGTH = 32;

        /**
         * 统一社会信用代码字段
         */
        String CREDIT_CODE_FIELD = "credit_code";

        /**
         * 法定代表人字段
         */
        String LEGAL_REPRESENTATIVE_FIELD = "legal_representative";
    }

    /**
     * 分所信息限制
     */
    interface Branch extends BaseConstants.Field {
        /**
         * 分所编码前缀
         */
        String CODE_PREFIX = "BR";

        /**
         * 分所名称最大长度
         */
        int NAME_MAX_LENGTH = 128;

        /**
         * 总所ID字段
         */
        String HEAD_OFFICE_ID_FIELD = "head_office_id";

        /**
         * 是否总所字段
         */
        String IS_HEAD_OFFICE_FIELD = "is_head_office";
    }

    /**
     * 办公室信息限制
     */
    interface Office extends BaseConstants.Field {
        /**
         * 办公室编码前缀
         */
        String CODE_PREFIX = "OF";

        /**
         * 办公室名称最大长度
         */
        int NAME_MAX_LENGTH = 64;

        /**
         * 办公室容量最小值
         */
        int MIN_CAPACITY = 1;

        /**
         * 办公室容量最大值
         */
        int MAX_CAPACITY = 1000;

        /**
         * 容量字段
         */
        String CAPACITY_FIELD = "capacity";

        /**
         * 使用状态字段
         */
        String USE_STATUS_FIELD = "use_status";
    }
} 