package com.lawfirm.cases.constant;

/**
 * 案件业务常量
 * 
 * 定义案件模块业务层使用的常量，与模型层常量区分开。
 * 模型层常量应从law-firm-model/case-model中引用。
 */
public final class CaseBusinessConstants {
    
    private CaseBusinessConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
    
    /**
     * 服务相关常量
     */
    public static final class Service {
        /**
         * 案件服务操作类型
         */
        public static final String OPERATION_CREATE = "create";
        public static final String OPERATION_UPDATE = "update";
        public static final String OPERATION_DELETE = "delete";
        public static final String OPERATION_CLOSE = "close";
        public static final String OPERATION_REOPEN = "reopen";
        public static final String OPERATION_ARCHIVE = "archive";
        
        /**
         * 案件团队操作类型
         */
        public static final String TEAM_OPERATION_ADD = "add_member";
        public static final String TEAM_OPERATION_REMOVE = "remove_member";
        public static final String TEAM_OPERATION_CHANGE_ROLE = "change_role";
        
        /**
         * 默认页大小
         */
        public static final int DEFAULT_PAGE_SIZE = 20;
    }
    
    /**
     * 集成相关常量
     */
    public static final class Integration {
        /**
         * 认证模块集成
         */
        public static final String AUTH_RESOURCE_CASE = "case";
        public static final String AUTH_RESOURCE_TEAM = "case_team";
        public static final String AUTH_RESOURCE_DOCUMENT = "case_document";
        public static final String AUTH_RESOURCE_FEE = "case_fee";
        
        /**
         * 消息模块集成
         */
        public static final String MESSAGE_TYPE_CASE_CREATED = "case_created";
        public static final String MESSAGE_TYPE_CASE_UPDATED = "case_updated";
        public static final String MESSAGE_TYPE_CASE_STATUS_CHANGED = "case_status_changed";
        
        /**
         * 审计模块集成
         */
        public static final String AUDIT_TYPE_CASE = "case";
        public static final String AUDIT_TYPE_TEAM = "case_team";
        public static final String AUDIT_TYPE_DOCUMENT = "case_document";
    }
    
    /**
     * 配置相关常量
     */
    public static final class Config {
        /**
         * 配置文件前缀
         */
        public static final String CONFIG_PREFIX = "law-firm.case";
        
        /**
         * 默认文件上传路径
         */
        public static final String DEFAULT_UPLOAD_PATH = "/temp/case/upload";
        
        /**
         * 默认流程定义文件
         */
        public static final String DEFAULT_PROCESS_DEFINITION = "case-process.bpmn20.xml";
    }
    
    /**
     * 控制器相关常量
     */
    public static final class Controller {
        /**
         * API路径前缀
         */
        public static final String API_PREFIX = "/api/cases";
        public static final String API_TEAM_PREFIX = "/api/cases/teams";
        public static final String API_DOCUMENT_PREFIX = "/api/cases/documents";
        public static final String API_FEE_PREFIX = "/api/cases/fees";
        public static final String API_PROCESS_PREFIX = "/api/cases/processes";
        
        /**
         * 响应消息
         */
        public static final String RESPONSE_SUCCESS = "操作成功";
        public static final String RESPONSE_CREATE_SUCCESS = "案件创建成功";
        public static final String RESPONSE_UPDATE_SUCCESS = "案件更新成功";
        public static final String RESPONSE_DELETE_SUCCESS = "案件删除成功";
    }
} 