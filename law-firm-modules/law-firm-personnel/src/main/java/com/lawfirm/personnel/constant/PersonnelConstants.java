package com.lawfirm.personnel.constant;

/**
 * 人事模块常量定义
 */
public class PersonnelConstants {
    
    /**
     * 员工状态
     */
    public static class EmployeeStatus {
        /** 试用期 */
        public static final Integer PROBATION = 1;
        /** 正式 */
        public static final Integer REGULAR = 2;
        /** 离职 */
        public static final Integer LEAVE = 3;
        
        public static String getName(Integer status) {
            if (status == null) {
                return null;
            }
            switch (status) {
                case 1:
                    return "试用期";
                case 2:
                    return "正式";
                case 3:
                    return "离职";
                default:
                    return "未知";
            }
        }
    }
    
    /**
     * 性别
     */
    public static class Gender {
        /** 女 */
        public static final Integer FEMALE = 0;
        /** 男 */
        public static final Integer MALE = 1;
        
        public static String getName(Integer gender) {
            if (gender == null) {
                return null;
            }
            switch (gender) {
                case 0:
                    return "女";
                case 1:
                    return "男";
                default:
                    return "未知";
            }
        }
    }
    
    /**
     * 档案类型
     */
    public static class ArchiveType {
        /** 教育经历 */
        public static final String EDUCATION = "EDUCATION";
        /** 工作经历 */
        public static final String WORK = "WORK";
        /** 证书 */
        public static final String CERTIFICATE = "CERTIFICATE";
        /** 合同 */
        public static final String CONTRACT = "CONTRACT";
        /** 其他 */
        public static final String OTHER = "OTHER";
    }
    
    /**
     * 证照类型
     */
    public static class CertificateType {
        /** 身份证 */
        public static final String ID_CARD = "ID_CARD";
        /** 学历证书 */
        public static final String DIPLOMA = "DIPLOMA";
        /** 资格证书 */
        public static final String QUALIFICATION = "QUALIFICATION";
        /** 其他 */
        public static final String OTHER = "OTHER";
    }
} 