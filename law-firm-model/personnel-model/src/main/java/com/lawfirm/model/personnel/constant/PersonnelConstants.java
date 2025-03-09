package com.lawfirm.model.personnel.constant;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 人事模块统一常量
 * 整合并规范化所有人事相关常量定义
 */
public interface PersonnelConstants extends BaseConstants {

    /**
     * 数据库表名
     */
    interface Table {
        String PERSON = "per_person";
        String EMPLOYEE = "per_employee";
        String LAWYER = "per_lawyer";
        String STAFF = "per_staff";
        String CONTACT = "per_contact";
        String CONTRACT = "per_contract";
        String EMPLOYEE_ORGANIZATION = "per_employee_organization";
        String EMPLOYEE_POSITION_HISTORY = "per_employee_position_history";
        String EDUCATION_EXPERIENCE = "per_education_experience";
        String WORK_EXPERIENCE = "per_work_experience";
    }
    
    /**
     * 数据库字段
     */
    interface Field {
        /**
         * 人员通用字段
         */
        interface Person {
            String ID = "id";
            String PERSON_CODE = "person_code";
            String NAME = "name";
            String ENGLISH_NAME = "english_name";
            String GENDER = "gender";
            String BIRTH_DATE = "birth_date";
            String ID_TYPE = "id_type";
            String ID_NUMBER = "id_number";
            String MOBILE = "mobile";
            String EMAIL = "email";
            String TYPE = "type";
            String FIRM_ID = "firm_id";
            String EMERGENCY_CONTACT = "emergency_contact";
            String EMERGENCY_MOBILE = "emergency_mobile";
            String PHOTO_URL = "photo_url";
        }
        
        /**
         * 员工字段
         */
        interface Employee {
            String WORK_NUMBER = "work_number";
            String USER_ID = "user_id";
            String DEPARTMENT_ID = "department_id";
            String POSITION_ID = "position_id";
            String ENTRY_DATE = "entry_date";
            String REGULAR_DATE = "regular_date";
            String RESIGN_DATE = "resign_date";
            String WORK_EMAIL = "work_email";
            String WORK_PHONE = "work_phone";
            String OFFICE_LOCATION = "office_location";
            String SUPERVISOR_ID = "supervisor_id";
            String WORK_YEARS = "work_years";
            String EDUCATION = "education";
            String GRADUATE_SCHOOL = "graduate_school";
            String MAJOR = "major";
            String GRADUATE_YEAR = "graduate_year";
            String EMPLOYEE_TYPE = "employee_type";
            String CONTRACT_STATUS = "contract_status";
            String CURRENT_CONTRACT_ID = "current_contract_id";
        }
        
        /**
         * 律师字段
         */
        interface Lawyer {
            String LICENSE_NUMBER = "license_number";
            String LICENSE_ISSUE_DATE = "license_issue_date";
            String LICENSE_EXPIRE_DATE = "license_expire_date";
            String PRACTICE_YEARS = "practice_years";
            String LEVEL = "level";
            String PRACTICE_AREAS = "practice_areas";
            String EXPERTISE = "expertise";
            String PROFILE = "profile";
            String ACHIEVEMENTS = "achievements";
            String TEAM_ID = "team_id";
            String MENTOR_ID = "mentor_id";
            String IS_PARTNER = "is_partner";
            String PARTNER_DATE = "partner_date";
            String EQUITY_RATIO = "equity_ratio";
        }

        /**
         * 教育经历字段
         */
        interface EducationExperience {
            String EMPLOYEE_ID = "employee_id";
            String SCHOOL_NAME = "school_name";
            String COLLEGE_NAME = "college_name";
            String MAJOR = "major";
            String DEGREE = "degree";
            String EDUCATION = "education";
            String START_DATE = "start_date";
            String END_DATE = "end_date";
            String IS_FULL_TIME = "is_full_time";
            String IS_HIGHEST = "is_highest";
            String CERTIFICATE_URL = "certificate_url";
            String DESCRIPTION = "description";
        }

        /**
         * 工作经历字段
         */
        interface WorkExperience {
            String EMPLOYEE_ID = "employee_id";
            String COMPANY_NAME = "company_name";
            String DEPARTMENT_NAME = "department_name";
            String POSITION_NAME = "position_name";
            String START_DATE = "start_date";
            String END_DATE = "end_date";
            String IS_CURRENT = "is_current";
            String JOB_DESCRIPTION = "job_description";
            String LEAVE_REASON = "leave_reason";
            String REFERENCE_PERSON = "reference_person";
            String REFERENCE_CONTACT = "reference_contact";
            String REFERENCE_URL = "reference_url";
        }

        /**
         * 员工职位历史字段
         */
        interface EmployeePositionHistory {
            String EMPLOYEE_ID = "employee_id";
            String ORGANIZATION_ID = "organization_id";
            String FROM_POSITION_ID = "from_position_id";
            String FROM_POSITION_NAME = "from_position_name";
            String TO_POSITION_ID = "to_position_id";
            String TO_POSITION_NAME = "to_position_name";
            String EFFECTIVE_DATE = "effective_date";
            String CHANGE_REASON = "change_reason";
            String OPERATOR_ID = "operator_id";
            String OPERATOR_NAME = "operator_name";
            String REMARK = "remark";
        }

        /**
         * 员工组织关系字段
         */
        interface EmployeeOrganization {
            String EMPLOYEE_ID = "employee_id";
            String ORGANIZATION_ID = "organization_id";
            String ORGANIZATION_TYPE = "organization_type";
            String POSITION_ID = "position_id";
            String IS_PRIMARY = "is_primary";
            String START_DATE = "start_date";
            String END_DATE = "end_date";
            String STATUS = "status";
            String REMARK = "remark";
        }
    }
    
    /**
     * 状态常量
     */
    interface Status {
        // 通用状态
        int DISABLED = 0;
        int ENABLED = 1;
        
        // 员工状态
        int ON_JOB = 1;
        int RESIGNED = 2;
        int PROBATION = 3;
        int ON_LEAVE = 4;
        int SUSPENDED = 5;
    }
    
    /**
     * 性别
     */
    interface Gender {
        int UNKNOWN = 0;
        int MALE = 1;
        int FEMALE = 2;
    }
    
    /**
     * 证件类型
     */
    interface IdType {
        int ID_CARD = 1;
        int PASSPORT = 2;
        int OTHER = 9;
    }
    
    /**
     * 学历
     */
    interface Education {
        int HIGH_SCHOOL = 1;
        int COLLEGE = 2;
        int BACHELOR = 3;
        int MASTER = 4;
        int DOCTOR = 5;
    }
    
    /**
     * 员工类型
     */
    interface EmployeeType {
        int FULL_TIME = 1;
        int PART_TIME = 2;
        int INTERN = 3;
        int OUTSOURCED = 4;
    }
    
    /**
     * 合同状态
     */
    interface ContractStatus {
        int UNSIGNED = 0;
        int SIGNED = 1;
        int EXPIRED = 2;
        int TERMINATED = 3;
    }
    
    /**
     * 律师级别
     */
    interface LawyerLevel {
        int INTERN = 1;
        int JUNIOR = 2;
        int MIDDLE = 3;
        int SENIOR = 4;
        int EXPERT = 5;
        int PARTNER = 6;
    }
    
    /**
     * 人员类型
     */
    interface PersonType {
        int LAWYER = 1;
        int STAFF = 2;
        int CLIENT = 3;
        int PARTNER = 4;
    }
    
    /**
     * 合同类型
     */
    interface ContractType {
        int LABOR = 1;       // 劳动合同
        int SERVICE = 2;     // 服务合同
        int CONFIDENTIAL = 3; // 保密协议
        int NON_COMPETE = 4; // 竞业限制协议
    }
    
    /**
     * 错误码
     */
    interface ErrorCode {
        String EMPLOYEE_NOT_FOUND = "EMPLOYEE_NOT_FOUND";
        String EMPLOYEE_CODE_EXISTS = "EMPLOYEE_CODE_EXISTS";
        String INVALID_STATUS_CHANGE = "INVALID_STATUS_CHANGE";
        String LAWYER_NOT_FOUND = "LAWYER_NOT_FOUND";
        String LICENSE_NUMBER_EXISTS = "LICENSE_NUMBER_EXISTS";
    }
    
    /**
     * 缓存键
     */
    interface CacheKey {
        String EMPLOYEE_PREFIX = "employee:";
        String LAWYER_PREFIX = "lawyer:";
        String STAFF_PREFIX = "staff:";
    }
    
    /**
     * 组织关系类型
     */
    interface RelationType {
        String PRIMARY = "PRIMARY";
        String SECONDARY = "SECONDARY";
        String TEMPORARY = "TEMPORARY";
    }
    
    /**
     * 人员角色类型
     */
    interface RoleType {
        String FIRM_DIRECTOR = "director";  // 律所主任
        String PARTNER = "partner";         // 合伙人律师
        String LAWYER = "lawyer";           // 执业律师
        String TRAINEE = "trainee";         // 实习律师
        String CLERK = "clerk";             // 行政人员
        String FINANCE = "finance";         // 财务人员
        String ADMIN = "admin";             // 系统管理员
    }
    
    /**
     * 操作权限类型
     */
    interface OperationType {
        String FULL = "FULL";           // 完全权限
        String READ_ONLY = "READ_ONLY"; // 只读权限
        String PERSONAL = "PERSONAL";   // 个人数据权限
        String APPROVE = "APPROVE";     // 审批权限
        String APPLY = "APPLY";         // 申请权限
    }
    
    /**
     * 数据权限范围
     */
    interface DataScope {
        String ALL = "ALL";           // 全部数据
        String FIRM = "FIRM";         // 律所数据
        String DEPARTMENT = "DEPARTMENT"; // 部门数据
        String TEAM = "TEAM";         // 团队数据
        String PERSONAL = "PERSONAL"; // 个人数据
        String CUSTOM = "CUSTOM";     // 自定义范围
    }
    
    /**
     * 模块编码
     */
    interface ModuleCode {
        String PERSONNEL = "personnel";    // 人事管理
        String LAWYER = "lawyer";          // 律师管理
        String CASE = "case";              // 案件管理
        String CONTRACT = "contract";      // 合同管理
        String FINANCE = "finance";        // 财务管理
        String DOCUMENT = "document";      // 文档管理
        String KNOWLEDGE = "knowledge";    // 知识管理
        String ORGANIZATION = "organization"; // 组织管理
    }
} 