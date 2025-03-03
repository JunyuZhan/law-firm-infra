package com.lawfirm.model.personnel.constant;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 员工相关常量
 */
public interface EmployeeConstant extends BaseConstants {

    /**
     * 员工状态
     */
    interface Status {
        /**
         * 试用期
         */
        int PROBATION = 0;

        /**
         * 正式
         */
        int REGULAR = 1;

        /**
         * 实习
         */
        int INTERN = 2;

        /**
         * 兼职
         */
        int PART_TIME = 3;
    }

    /**
     * 员工类型
     */
    interface Type {
        /**
         * 全职
         */
        int FULL_TIME = 1;

        /**
         * 兼职
         */
        int PART_TIME = 2;

        /**
         * 实习
         */
        int INTERN = 3;

        /**
         * 外包
         */
        int OUTSOURCED = 4;
    }

    /**
     * 合同类型
     */
    interface ContractType {
        /**
         * 固定期限
         */
        int FIXED_TERM = 1;

        /**
         * 无固定期限
         */
        int INDEFINITE = 2;

        /**
         * 实习协议
         */
        int INTERNSHIP = 3;

        /**
         * 劳务协议
         */
        int LABOR = 4;
    }

    /**
     * 表名
     */
    interface Table {
        /**
         * 员工基本信息表
         */
        String EMPLOYEE = "per_employee";

        /**
         * 员工合同表
         */
        String CONTRACT = "per_employee_contract";

        /**
         * 员工教育经历表
         */
        String EDUCATION = "per_employee_education";

        /**
         * 员工工作经历表
         */
        String WORK_EXPERIENCE = "per_employee_work_exp";
    }

    /**
     * 字段名
     */
    interface Field {
        /**
         * 员工编号
         */
        String EMPLOYEE_CODE = "employee_code";

        /**
         * 工号
         */
        String WORK_NUMBER = "work_number";

        /**
         * 入职日期
         */
        String ENTRY_DATE = "entry_date";

        /**
         * 转正日期
         */
        String REGULAR_DATE = "regular_date";

        /**
         * 离职日期
         */
        String RESIGN_DATE = "resign_date";

        /**
         * 合同开始日期
         */
        String CONTRACT_START_DATE = "contract_start_date";

        /**
         * 合同结束日期
         */
        String CONTRACT_END_DATE = "contract_end_date";
    }

    /**
     * 缓存key
     */
    interface CacheKey {
        /**
         * 员工缓存key前缀
         */
        String EMPLOYEE_PREFIX = "employee:";

        /**
         * 员工编号缓存key前缀
         */
        String EMPLOYEE_CODE_PREFIX = "employee:code:";

        /**
         * 员工工号缓存key前缀
         */
        String WORK_NUMBER_PREFIX = "employee:work_number:";
    }

    /**
     * 错误码
     */
    interface ErrorCode {
        /**
         * 员工不存在
         */
        String EMPLOYEE_NOT_FOUND = "EMPLOYEE_NOT_FOUND";

        /**
         * 员工编号已存在
         */
        String EMPLOYEE_CODE_EXISTS = "EMPLOYEE_CODE_EXISTS";

        /**
         * 工号已存在
         */
        String WORK_NUMBER_EXISTS = "WORK_NUMBER_EXISTS";

        /**
         * 合同信息不存在
         */
        String CONTRACT_NOT_FOUND = "CONTRACT_NOT_FOUND";

        /**
         * 合同日期无效
         */
        String INVALID_CONTRACT_DATE = "INVALID_CONTRACT_DATE";
    }
} 