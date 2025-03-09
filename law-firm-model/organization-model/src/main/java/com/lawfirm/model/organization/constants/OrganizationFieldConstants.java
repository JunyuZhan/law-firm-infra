package com.lawfirm.model.organization.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 组织字段常量
 */
public interface OrganizationFieldConstants {

    /**
     * 编码相关常量
     */
    interface Code {
        int MIN_LENGTH = 2;
        int MAX_LENGTH = 50;
        String PATTERN = "^[A-Z0-9_-]{2,50}$";
    }

    /**
     * 名称相关常量
     */
    interface Name {
        int MIN_LENGTH = 2;
        int MAX_LENGTH = 50;
    }

    /**
     * 描述相关常量
     */
    interface Description {
        int MAX_LENGTH = 500;
    }

    /**
     * 联系方式相关常量
     */
    interface Contact {
        int PHONE_MAX_LENGTH = 20;
        int EMAIL_MAX_LENGTH = 50;
        int ADDRESS_MAX_LENGTH = 200;
    }

    /**
     * 状态常量
     */
    interface Status {
        int DISABLED = 0;
        int ENABLED = 1;
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