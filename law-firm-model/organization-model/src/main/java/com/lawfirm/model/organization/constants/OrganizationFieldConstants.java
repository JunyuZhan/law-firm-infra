package com.lawfirm.model.organization.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 组织架构字段常量
 */
public interface OrganizationFieldConstants extends BaseConstants {

    /**
     * 编码长度限制
     */
    interface Code extends BaseConstants.Field {
        /**
         * 最小长度
         */
        int MIN_LENGTH = 4;

        /**
         * 最大长度
         */
        int MAX_LENGTH = 32;

        /**
         * 编码格式正则表达式（字母、数字和下划线）
         */
        String PATTERN = "^[a-zA-Z0-9_]{4,32}$";
    }

    /**
     * 名称长度限制
     */
    interface Name {
        /**
         * 最小长度
         */
        int MIN_LENGTH = 2;

        /**
         * 最大长度
         */
        int MAX_LENGTH = 64;
    }

    /**
     * 描述长度限制
     */
    interface Description {
        /**
         * 最大长度
         */
        int MAX_LENGTH = 512;
    }

    /**
     * 联系信息长度限制
     */
    interface Contact {
        /**
         * 电话号码最大长度
         */
        int PHONE_MAX_LENGTH = 20;

        /**
         * 邮箱最大长度
         */
        int EMAIL_MAX_LENGTH = 64;

        /**
         * 地址最大长度
         */
        int ADDRESS_MAX_LENGTH = 256;
    }

    /**
     * 排序范围限制
     */
    interface Sort extends BaseConstants.Field {
        /**
         * 最小值
         */
        int MIN_VALUE = 0;

        /**
         * 最大值
         */
        int MAX_VALUE = 9999;

        /**
         * 默认排序字段
         */
        String DEFAULT_SORT_FIELD = BaseConstants.Field.SORT;
    }
} 