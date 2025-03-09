package com.lawfirm.model.organization.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 律师事务所字段常量
 */
public interface FirmFieldConstants {

    /**
     * 执业许可相关常量
     */
    interface License {
        int NUMBER_MIN_LENGTH = 10;
        int NUMBER_MAX_LENGTH = 50;
        String NUMBER_PATTERN = "^[A-Z0-9-]{10,50}$";
    }

    /**
     * 商业信息相关常量
     */
    interface Business {
        int CREDIT_CODE_LENGTH = 18;
        String CREDIT_CODE_PATTERN = "^[A-Z0-9]{18}$";
        int LEGAL_REPRESENTATIVE_MAX_LENGTH = 50;
    }

    /**
     * 规模相关常量
     */
    interface Scale {
        int SMALL = 1;  // 小型律所（<20人）
        int MEDIUM = 2; // 中型律所（20-100人）
        int LARGE = 3;  // 大型律所（>100人）
    }

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
     * 营业信息限制
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
} 