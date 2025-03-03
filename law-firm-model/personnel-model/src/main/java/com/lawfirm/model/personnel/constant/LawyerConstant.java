package com.lawfirm.model.personnel.constant;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 律师相关常量
 */
public interface LawyerConstant extends BaseConstants {

    /**
     * 律师职级
     */
    interface Level {
        /**
         * 实习律师
         */
        int INTERN = 0;

        /**
         * 初级律师
         */
        int JUNIOR = 1;

        /**
         * 中级律师
         */
        int MIDDLE = 2;

        /**
         * 高级律师
         */
        int SENIOR = 3;

        /**
         * 资深律师
         */
        int EXPERT = 4;

        /**
         * 合伙人
         */
        int PARTNER = 5;
    }

    /**
     * 执业状态
     */
    interface PracticeStatus {
        /**
         * 正常执业
         */
        int NORMAL = 0;

        /**
         * 暂停执业
         */
        int SUSPENDED = 1;

        /**
         * 注销执业
         */
        int CANCELLED = 2;
    }

    /**
     * 专业领域
     */
    interface PracticeArea {
        /**
         * 民商事
         */
        String CIVIL = "CIVIL";

        /**
         * 刑事
         */
        String CRIMINAL = "CRIMINAL";

        /**
         * 行政
         */
        String ADMINISTRATIVE = "ADMINISTRATIVE";

        /**
         * 知识产权
         */
        String IP = "IP";

        /**
         * 金融证券
         */
        String FINANCE = "FINANCE";

        /**
         * 公司业务
         */
        String CORPORATE = "CORPORATE";
    }

    /**
     * 表名
     */
    interface Table {
        /**
         * 律师基本信息表
         */
        String LAWYER = "per_lawyer";

        /**
         * 律师执业证书表
         */
        String LICENSE = "per_lawyer_license";

        /**
         * 律师专业领域表
         */
        String PRACTICE_AREA = "per_lawyer_practice_area";

        /**
         * 律师案件经历表
         */
        String CASE_EXPERIENCE = "per_lawyer_case_exp";
    }

    /**
     * 字段名
     */
    interface Field {
        /**
         * 律师编号
         */
        String LAWYER_CODE = "lawyer_code";

        /**
         * 执业证号
         */
        String LICENSE_NUMBER = "license_number";

        /**
         * 执业证书发证日期
         */
        String LICENSE_ISSUE_DATE = "license_issue_date";

        /**
         * 执业证书失效日期
         */
        String LICENSE_EXPIRE_DATE = "license_expire_date";

        /**
         * 执业年限
         */
        String PRACTICE_YEARS = "practice_years";

        /**
         * 专业领域
         */
        String PRACTICE_AREAS = "practice_areas";
    }

    /**
     * 缓存key
     */
    interface CacheKey {
        /**
         * 律师缓存key前缀
         */
        String LAWYER_PREFIX = "lawyer:";

        /**
         * 律师编号缓存key前缀
         */
        String LAWYER_CODE_PREFIX = "lawyer:code:";

        /**
         * 执业证号缓存key前缀
         */
        String LICENSE_NUMBER_PREFIX = "lawyer:license:";
    }

    /**
     * 错误码
     */
    interface ErrorCode {
        /**
         * 律师不存在
         */
        String LAWYER_NOT_FOUND = "LAWYER_NOT_FOUND";

        /**
         * 律师编号已存在
         */
        String LAWYER_CODE_EXISTS = "LAWYER_CODE_EXISTS";

        /**
         * 执业证号已存在
         */
        String LICENSE_NUMBER_EXISTS = "LICENSE_NUMBER_EXISTS";

        /**
         * 执业证书已过期
         */
        String LICENSE_EXPIRED = "LICENSE_EXPIRED";

        /**
         * 执业证书即将过期
         */
        String LICENSE_EXPIRING = "LICENSE_EXPIRING";
    }
} 