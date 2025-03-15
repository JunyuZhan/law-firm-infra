package com.lawfirm.model.client.constant;

/**
 * 客户管理模块常量定义
 */
public class ClientConstant {

    /**
     * 客户状态
     */
    public static final class Status {
        /**
         * 正常
         */
        public static final int NORMAL = 0;
        
        /**
         * 禁用
         */
        public static final int DISABLED = 1;
        
        /**
         * 删除
         */
        public static final int DELETED = 2;
    }
    
    /**
     * 客户类型
     */
    public static final class ClientType {
        /**
         * 个人
         */
        public static final int PERSONAL = 1;
        
        /**
         * 企业
         */
        public static final int ENTERPRISE = 2;
    }
    
    /**
     * 客户等级
     */
    public static final class ClientLevel {
        /**
         * 普通客户
         */
        public static final int NORMAL = 1;
        
        /**
         * VIP客户
         */
        public static final int VIP = 2;
        
        /**
         * 核心客户
         */
        public static final int CORE = 3;
    }
    
    /**
     * 标签类型
     */
    public static final class TagType {
        /**
         * 业务标签
         */
        public static final int BUSINESS = 1;
        
        /**
         * 状态标签
         */
        public static final int STATUS = 2;
        
        /**
         * 价值标签
         */
        public static final int VALUE = 3;
        
        /**
         * 特征标签
         */
        public static final int FEATURE = 4;
    }
    
    /**
     * 联系人相关常量
     */
    public static final class Contact {
        /**
         * 默认联系人
         */
        public static final int DEFAULT = 1;
        
        /**
         * 非默认联系人
         */
        public static final int NOT_DEFAULT = 0;
    }
    
    /**
     * 地址相关常量
     */
    public static final class Address {
        /**
         * 默认地址
         */
        public static final int DEFAULT = 1;
        
        /**
         * 非默认地址
         */
        public static final int NOT_DEFAULT = 0;
        
        /**
         * 注册地址
         */
        public static final int TYPE_REGISTERED = 1;
        
        /**
         * 办公地址
         */
        public static final int TYPE_OFFICE = 2;
        
        /**
         * 通讯地址
         */
        public static final int TYPE_MAILING = 3;
    }
    
    /**
     * 跟进记录相关常量
     */
    public static final class FollowUp {
        /**
         * 跟进方式：电话
         */
        public static final int METHOD_PHONE = 1;
        
        /**
         * 跟进方式：邮件
         */
        public static final int METHOD_EMAIL = 2;
        
        /**
         * 跟进方式：会议
         */
        public static final int METHOD_MEETING = 3;
        
        /**
         * 跟进方式：拜访
         */
        public static final int METHOD_VISIT = 4;
        
        /**
         * 跟进状态：待跟进
         */
        public static final int STATUS_PENDING = 0;
        
        /**
         * 跟进状态：已完成
         */
        public static final int STATUS_COMPLETED = 1;
        
        /**
         * 跟进状态：已取消
         */
        public static final int STATUS_CANCELED = 2;
    }
    
    /**
     * 数据库字段最大长度
     */
    public static final class Length {
        /**
         * 客户名称最大长度
         */
        public static final int CLIENT_NAME = 100;
        
        /**
         * 客户编号最大长度
         */
        public static final int CLIENT_NO = 30;
        
        /**
         * 联系电话最大长度
         */
        public static final int PHONE = 20;
        
        /**
         * 电子邮箱最大长度
         */
        public static final int EMAIL = 100;
        
        /**
         * 联系人姓名最大长度
         */
        public static final int CONTACT_NAME = 50;
        
        /**
         * 证件号码最大长度
         */
        public static final int ID_NUMBER = 50;
        
        /**
         * 标签名称最大长度
         */
        public static final int TAG_NAME = 20;
    }
} 