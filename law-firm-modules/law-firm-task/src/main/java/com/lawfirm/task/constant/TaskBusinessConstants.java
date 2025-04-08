package com.lawfirm.task.constant;

/**
 * 任务业务常量
 * 
 * 定义任务模块业务层使用的常量，与模型层常量区分开。
 * 模型层常量应从law-firm-model/task-model中引用。
 */
public final class TaskBusinessConstants {
    
    private TaskBusinessConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
    
    /**
     * 服务相关常量
     */
    public static final class Service {
        /**
         * 任务服务操作类型
         */
        public static final String OPERATION_CREATE = "create";
        public static final String OPERATION_UPDATE = "update";
        public static final String OPERATION_DELETE = "delete";
        public static final String OPERATION_COMPLETE = "complete";
        public static final String OPERATION_CANCEL = "cancel";
        
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
        public static final String AUTH_RESOURCE_TASK = "task";
        public static final String AUTH_RESOURCE_TAG = "task_tag";
        public static final String AUTH_RESOURCE_COMMENT = "task_comment";
        public static final String AUTH_RESOURCE_ATTACHMENT = "task_attachment";
        
        /**
         * 消息模块集成
         */
        public static final String MESSAGE_TYPE_TASK_CREATED = "task_created";
        public static final String MESSAGE_TYPE_TASK_UPDATED = "task_updated";
        public static final String MESSAGE_TYPE_TASK_COMPLETED = "task_completed";
        public static final String MESSAGE_TYPE_TASK_ASSIGNED = "task_assigned";
        
        /**
         * 审计模块集成
         */
        public static final String AUDIT_TYPE_TASK = "task";
        public static final String AUDIT_TYPE_TAG = "task_tag";
        public static final String AUDIT_TYPE_COMMENT = "task_comment";
        public static final String AUDIT_TYPE_ATTACHMENT = "task_attachment";
    }
    
    /**
     * 配置相关常量
     */
    public static final class Config {
        /**
         * 配置文件前缀
         */
        public static final String CONFIG_PREFIX = "law-firm.task";
        
        /**
         * 默认文件上传路径
         */
        public static final String DEFAULT_UPLOAD_PATH = "/temp/task/upload";
    }
    
    /**
     * 控制器相关常量
     */
    public static final class Controller {
        /**
         * API路径前缀
         */
        public static final String API_PREFIX = "/api/v1/tasks";
        public static final String API_TAG_PREFIX = "/api/v1/task-tags";
        public static final String API_COMMENT_PREFIX = "/api/v1/tasks/{taskId}/comments";
        public static final String API_ATTACHMENT_PREFIX = "/api/v1/tasks/{taskId}/attachments";
        
        /**
         * 跨模块API路径
         */
        public static final String API_CASE_TASKS_PREFIX = "/api/v1/cases/{caseId}/tasks";
        public static final String API_CLIENT_TASKS_PREFIX = "/api/v1/clients/{clientId}/tasks";
        public static final String API_PERSONNEL_TASKS_PREFIX = "/api/v1/personnel/{userId}/tasks";
        public static final String API_DEPARTMENT_TASKS_PREFIX = "/api/v1/departments/{deptId}/tasks";
        
        /**
         * 响应消息
         */
        public static final String RESPONSE_SUCCESS = "操作成功";
        public static final String RESPONSE_CREATE_SUCCESS = "任务创建成功";
        public static final String RESPONSE_UPDATE_SUCCESS = "任务更新成功";
        public static final String RESPONSE_DELETE_SUCCESS = "任务删除成功";
        public static final String RESPONSE_COMPLETE_SUCCESS = "任务完成成功";
        public static final String RESPONSE_CANCEL_SUCCESS = "任务取消成功";
    }
} 