package com.lawfirm.model.personnel.constant;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 人事模块常量
 */
public interface PersonnelConstant extends BaseConstants {

    /**
     * 人员状态
     */
    interface Status {
        /**
         * 在职
         */
        int ON_JOB = 0;

        /**
         * 离职
         */
        int RESIGNED = 1;

        /**
         * 休假
         */
        int ON_LEAVE = 2;

        /**
         * 停职
         */
        int SUSPENDED = 3;
    }

    /**
     * 律师职级
     */
    interface LawyerLevel {
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
    }

    /**
     * 人员类型
     */
    interface PersonType {
        /**
         * 律师
         */
        int LAWYER = 1;

        /**
         * 行政人员
         */
        int STAFF = 2;

        /**
         * 实习生
         */
        int INTERN = 3;
    }

    /**
     * 数据库表名
     */
    interface Table {
        /**
         * 人员表
         */
        String PERSON = "per_person";

        /**
         * 员工表
         */
        String EMPLOYEE = "per_employee";

        /**
         * 律师表
         */
        String LAWYER = "per_lawyer";

        /**
         * 行政人员表
         */
        String STAFF = "per_staff";

        /**
         * 联系方式表
         */
        String CONTACT = "per_contact";

        /**
         * 合同表
         */
        String CONTRACT = "per_contract";
    }

    /**
     * 字段名
     */
    interface Field {
        /**
         * 人员编号
         */
        String PERSON_CODE = "person_code";

        /**
         * 员工编号
         */
        String EMPLOYEE_CODE = "employee_code";

        /**
         * 律师执业证号
         */
        String LICENSE_NUMBER = "license_number";

        /**
         * 所属部门ID
         */
        String DEPARTMENT_ID = "department_id";

        /**
         * 职位ID
         */
        String POSITION_ID = "position_id";
    }

    /**
     * 缓存key
     */
    interface CacheKey {
        /**
         * 人员缓存key前缀
         */
        String PERSON_PREFIX = "person:";

        /**
         * 员工缓存key前缀
         */
        String EMPLOYEE_PREFIX = "employee:";

        /**
         * 律师缓存key前缀
         */
        String LAWYER_PREFIX = "lawyer:";
    }

    /**
     * 错误码
     */
    interface ErrorCode {
        /**
         * 人员不存在
         */
        String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";

        /**
         * 员工不存在
         */
        String EMPLOYEE_NOT_FOUND = "EMPLOYEE_NOT_FOUND";

        /**
         * 律师不存在
         */
        String LAWYER_NOT_FOUND = "LAWYER_NOT_FOUND";

        /**
         * 执业证号已存在
         */
        String LICENSE_NUMBER_EXISTS = "LICENSE_NUMBER_EXISTS";
    }
} 