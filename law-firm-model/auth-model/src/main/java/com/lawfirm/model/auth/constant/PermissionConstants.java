package com.lawfirm.model.auth.constant;

/**
 * 权限点常量定义，统一风格：模块:操作
 * 便于全局统一引用和维护
 */
public interface PermissionConstants {
    // 用户管理模块
    String SYS_USER_CREATE = "sys:user:create";
    String SYS_USER_UPDATE = "sys:user:update";
    String SYS_USER_DELETE = "sys:user:delete";
    String SYS_USER_READ = "sys:user:read";
    // 角色管理模块
    String SYS_ROLE_CREATE = "sys:role:create";
    String SYS_ROLE_UPDATE = "sys:role:update";
    String SYS_ROLE_DELETE = "sys:role:delete";
    String SYS_ROLE_READ = "sys:role:read";
    // 权限管理模块
    String SYS_PERMISSION_CREATE = "sys:permission:create";
    String SYS_PERMISSION_UPDATE = "sys:permission:update";
    String SYS_PERMISSION_DELETE = "sys:permission:delete";
    String SYS_PERMISSION_READ = "sys:permission:read";
    String SYS_PERMISSION_VIEW = "sys:permission:view";
    String SYS_PERMISSION_CACHE_CLEAR = "sys:permission:cache:clear";
    String SYS_PERMISSION_CACHE_MANAGE = "sys:permission:cache:manage";

    // 案件模块
    String CASE_CREATE = "case:create";
    String CASE_EDIT = "case:edit";
    String CASE_DELETE = "case:delete";
    String CASE_VIEW = "case:view";
    String CASE_APPROVE = "case:approve";
    String CASE_ARCHIVE = "case:archive";

    // 合同模块
    String CONTRACT_CREATE = "contract:create";
    String CONTRACT_EDIT = "contract:edit";
    String CONTRACT_DELETE = "contract:delete";
    String CONTRACT_VIEW = "contract:view";
    String CONTRACT_APPROVE = "contract:approve";
    String CONTRACT_ARCHIVE = "contract:archive";

    // 合同审批模块
    String CONTRACT_REVIEW_CREATE = "contract:review:create";
    String CONTRACT_REVIEW_APPROVE = "contract:review:approve";
    String CONTRACT_REVIEW_REJECT = "contract:review:reject";
    String CONTRACT_REVIEW_VIEW = "contract:review:view";
    String CONTRACT_REVIEW_REVOKE = "contract:review:revoke";
    String CONTRACT_REVIEW_URGE = "contract:review:urge";
    String CONTRACT_REVIEW_TRANSFER = "contract:review:transfer";

    // 客户模块
    String CLIENT_CREATE = "client:create";
    String CLIENT_EDIT = "client:edit";
    String CLIENT_DELETE = "client:delete";
    String CLIENT_VIEW = "client:view";

    // 财务模块
    String FINANCE_CREATE = "finance:create";
    String FINANCE_EDIT = "finance:edit";
    String FINANCE_DELETE = "finance:delete";
    String FINANCE_VIEW = "finance:view";
    String FINANCE_APPROVE = "finance:approve";

    // 人事模块
    String PERSONNEL_CREATE = "personnel:create";
    String PERSONNEL_EDIT = "personnel:edit";
    String PERSONNEL_DELETE = "personnel:delete";
    String PERSONNEL_VIEW = "personnel:view";

    // 知识库模块
    String KNOWLEDGE_CREATE = "knowledge:create";
    String KNOWLEDGE_EDIT = "knowledge:edit";
    String KNOWLEDGE_DELETE = "knowledge:delete";
    String KNOWLEDGE_VIEW = "knowledge:view";

    // 分析模块
    String ANALYSIS_VIEW = "analysis:view";
    String ANALYSIS_EXPORT = "analysis:export";
    String ANALYSIS_CREATE = "analysis:create";
    String ANALYSIS_EDIT = "analysis:edit";
    String ANALYSIS_DELETE = "analysis:delete";

    // 档案模块
    String ARCHIVE_CREATE = "archive:create";
    String ARCHIVE_EDIT = "archive:edit";
    String ARCHIVE_DELETE = "archive:delete";
    String ARCHIVE_VIEW = "archive:view";

    // 文档模块
    String DOCUMENT_CREATE = "document:create";
    String DOCUMENT_EDIT = "document:edit";
    String DOCUMENT_DELETE = "document:delete";
    String DOCUMENT_VIEW = "document:view";

    // 证据模块
    String EVIDENCE_CREATE = "evidence:create";
    String EVIDENCE_EDIT = "evidence:edit";
    String EVIDENCE_DELETE = "evidence:delete";
    String EVIDENCE_VIEW = "evidence:view";
    String EVIDENCE_ARCHIVE = "evidence:archive";
    String EVIDENCE_TAG_ADD = "evidence:tag:add";
    String EVIDENCE_TAG_DELETE = "evidence:tag:delete";
    String EVIDENCE_EXPORT = "evidence:export";

    // 日程模块
    String SCHEDULE_CREATE = "schedule:create";
    String SCHEDULE_EDIT = "schedule:edit";
    String SCHEDULE_DELETE = "schedule:delete";
    String SCHEDULE_VIEW = "schedule:view";
    // 日程事件模块
    String SCHEDULE_EVENT_CREATE = "schedule:event:create";
    String SCHEDULE_EVENT_EDIT = "schedule:event:edit";
    String SCHEDULE_EVENT_DELETE = "schedule:event:delete";
    String SCHEDULE_EVENT_VIEW = "schedule:event:view";

    // 日程参与人模块
    String SCHEDULE_PARTICIPANT_CREATE = "schedule:participant:create";
    String SCHEDULE_PARTICIPANT_EDIT = "schedule:participant:edit";
    String SCHEDULE_PARTICIPANT_DELETE = "schedule:participant:delete";
    String SCHEDULE_PARTICIPANT_VIEW = "schedule:participant:view";

    // 日程提醒模块
    String SCHEDULE_REMINDER_CREATE = "schedule:reminder:create";
    String SCHEDULE_REMINDER_EDIT = "schedule:reminder:edit";
    String SCHEDULE_REMINDER_DELETE = "schedule:reminder:delete";
    String SCHEDULE_REMINDER_VIEW = "schedule:reminder:view";

    // 日程关联模块
    String SCHEDULE_RELATION_CREATE = "schedule:relation:create";
    String SCHEDULE_RELATION_EDIT = "schedule:relation:edit";
    String SCHEDULE_RELATION_DELETE = "schedule:relation:delete";
    String SCHEDULE_RELATION_VIEW = "schedule:relation:view";

    // 日历模块
    String SCHEDULE_CALENDAR_CREATE = "schedule:calendar:create";
    String SCHEDULE_CALENDAR_EDIT = "schedule:calendar:edit";
    String SCHEDULE_CALENDAR_DELETE = "schedule:calendar:delete";
    String SCHEDULE_CALENDAR_VIEW = "schedule:calendar:view";

    // 外部日历模块
    String SCHEDULE_CALENDAR_EXTERNAL = "schedule:calendar:external";
    String SCHEDULE_EXTERNAL_CALENDAR_IMPORT = "schedule:external-calendar:import";
    String SCHEDULE_EXTERNAL_CALENDAR_EXPORT = "schedule:external-calendar:export";
    String SCHEDULE_EXTERNAL_CALENDAR_VIEW = "schedule:external-calendar:view";
    String SCHEDULE_EXTERNAL_CALENDAR_MANAGE = "schedule:external-calendar:manage";
    String SCHEDULE_EXTERNAL_CALENDAR_SYNC = "schedule:external-calendar:sync";

    // 会议室模块
    String MEETING_ROOM_CREATE = "meeting:room:create";
    String MEETING_ROOM_EDIT = "meeting:room:edit";
    String MEETING_ROOM_DELETE = "meeting:room:delete";
    String MEETING_ROOM_VIEW = "meeting:room:view";

    // 会议室预定模块
    String MEETING_ROOM_BOOKING_CREATE = "meeting:room:booking:create";
    String MEETING_ROOM_BOOKING_EDIT = "meeting:room:booking:edit";
    String MEETING_ROOM_BOOKING_DELETE = "meeting:room:booking:delete";
    String MEETING_ROOM_BOOKING_VIEW = "meeting:room:booking:view";

    // 任务模块
    String TASK_CREATE = "task:create";
    String TASK_EDIT = "task:edit";
    String TASK_DELETE = "task:delete";
    String TASK_VIEW = "task:view";
    String TASK_COMMENT = "task:comment";

    // 任务标签模块
    String TASK_TAG_CREATE = "task:tag:create";
    String TASK_TAG_EDIT = "task:tag:edit";
    String TASK_TAG_DELETE = "task:tag:delete";
    String TASK_TAG_VIEW = "task:tag:view";

    // 任务评论模块
    String TASK_COMMENT_CREATE = "task:comment:create";
    String TASK_COMMENT_EDIT = "task:comment:edit";
    String TASK_COMMENT_DELETE = "task:comment:delete";
    String TASK_COMMENT_VIEW = "task:comment:view";

    // 任务附件模块
    String TASK_ATTACHMENT_CREATE = "task:attachment:create";
    String TASK_ATTACHMENT_EDIT = "task:attachment:edit";
    String TASK_ATTACHMENT_DELETE = "task:attachment:delete";
    String TASK_ATTACHMENT_VIEW = "task:attachment:view";

    // 系统模块
    String SYSTEM_CONFIG = "system:config";
    String SYSTEM_USER_MANAGE = "system:user:manage";
    String SYSTEM_ROLE_MANAGE = "system:role:manage";
    String SYSTEM_PERMISSION_MANAGE = "system:permission:manage";

    // 系统配置模块
    String SYSTEM_CONFIG_VIEW = "system:config:view";
    String SYSTEM_CONFIG_CREATE = "system:config:create";
    String SYSTEM_CONFIG_EDIT = "system:config:edit";
    String SYSTEM_CONFIG_DELETE = "system:config:delete";
    String SYSTEM_CONFIG_REFRESH = "system:config:refresh";

    // 系统字典模块
    String SYSTEM_DICT_VIEW = "system:dict:view";
    String SYSTEM_DICT_CREATE = "system:dict:create";
    String SYSTEM_DICT_EDIT = "system:dict:edit";
    String SYSTEM_DICT_DELETE = "system:dict:delete";
    String SYSTEM_DICT_REFRESH = "system:dict:refresh";

    // 系统字典项模块
    String SYSTEM_DICT_ITEM_VIEW = "system:dict:item:view";
    String SYSTEM_DICT_ITEM_CREATE = "system:dict:item:create";
    String SYSTEM_DICT_ITEM_EDIT = "system:dict:item:edit";
    String SYSTEM_DICT_ITEM_DELETE = "system:dict:item:delete";

    // 数据库备份模块
    String SYSTEM_DATABASE_BACKUP = "system:database:backup";

    // 数据库表结构模块
    String SYSTEM_DATABASE_SCHEMA_VIEW = "system:database:schema:view";
    String SYSTEM_DATABASE_SCHEMA_GENERATE = "system:database:schema:generate";
    String SYSTEM_DATABASE_SCHEMA_PROFILE = "system:database:schema:profile";

    // 系统监控模块
    String SYSTEM_MONITOR_SERVER = "system:monitor:server";
    String SYSTEM_MONITOR_SERVER_COLLECT = "system:monitor:server:collect";
    String SYSTEM_MONITOR_APP = "system:monitor:app";
    String SYSTEM_MONITOR_APP_COLLECT = "system:monitor:app:collect";
    String SYSTEM_MONITOR_DB = "system:monitor:db";
    String SYSTEM_MONITOR_DB_COLLECT = "system:monitor:db:collect";
    String SYSTEM_MONITOR_ALERT_VIEW = "system:alert:view";
    String SYSTEM_MONITOR_ALERT_CREATE = "system:alert:create";
    String SYSTEM_MONITOR_ALERT_HANDLE = "system:alert:handle";
    String SYSTEM_MONITOR_ALERT_CLOSE = "system:alert:close";
    String SYSTEM_MONITOR_AUDIT_VIEW = "system:audit:view";
    String SYSTEM_MONITOR_AUDIT_EXPORT = "system:audit:export";
    String SYSTEM_MONITOR_LOG_VIEW = "system:log:view";
    String SYSTEM_MONITOR_LOG_EXPORT = "system:log:export";
    String SYSTEM_MONITOR_LOG_CLEAN = "system:log:clean";
    String SYSTEM_MONITOR_SYSTEM = "system:monitor:system";
    String SYSTEM_MONITOR_JVM = "system:monitor:jvm";
    String SYSTEM_MONITOR_MEMORY = "system:monitor:memory";
    String SYSTEM_MONITOR_CPU = "system:monitor:cpu";
    String SYSTEM_MONITOR_DISK = "system:monitor:disk";
    String SYSTEM_MONITOR_NETWORK = "system:monitor:network";

    // 系统升级模块
    String SYSTEM_UPGRADE_VIEW = "system:upgrade:view";
    String SYSTEM_UPGRADE_CREATE = "system:upgrade:create";
    String SYSTEM_UPGRADE_EDIT = "system:upgrade:edit";
    String SYSTEM_UPGRADE_DELETE = "system:upgrade:delete";
    String SYSTEM_UPGRADE_EXECUTE = "system:upgrade:execute";
    String SYSTEM_UPGRADE_ROLLBACK = "system:upgrade:rollback";
    String SYSTEM_UPGRADE_PATCH_VIEW = "system:upgrade:patch:view";
    String SYSTEM_UPGRADE_PATCH_CREATE = "system:upgrade:patch:create";
    String SYSTEM_UPGRADE_PATCH_EDIT = "system:upgrade:patch:edit";
    String SYSTEM_UPGRADE_PATCH_DELETE = "system:upgrade:patch:delete";
    String SYSTEM_UPGRADE_PATCH_EXECUTE = "system:upgrade:patch:execute";
    String SYSTEM_UPGRADE_PATCH_ROLLBACK = "system:upgrade:patch:rollback";
    String SYSTEM_UPGRADE_CHECK = "system:upgrade:check";

    // 案件流程审批模块
    String PROCESS_APPROVAL_INITIATE = "process:approval:initiate";
    String PROCESS_APPROVAL_EDIT = "process:approval:edit";
    String PROCESS_APPROVAL_CANCEL = "process:approval:cancel";
    String PROCESS_APPROVAL_VIEW = "process:approval:view";
    String PROCESS_APPROVAL_APPROVE = "process:approval:approve";
    String PROCESS_APPROVAL_TRANSFER = "process:approval:transfer";
    String PROCESS_APPROVAL_URGE = "process:approval:urge";

    // 客户数据清理模块
    String CLIENT_CLEANUP_EXECUTE = "client:cleanup:execute";
    String CLIENT_CLEANUP_VIEW = "client:cleanup:view";

    // =================== 补充缺失的权限点定义 ===================
    
    // 模块级别权限点（顶级权限）
    String AUTH = "auth";                          // 认证模块
    String SYSTEM = "system";                      // 系统模块  
    String CLIENT = "client";                      // 客户模块
    String PERSONNEL = "personnel";                // 人事模块
    String ORGANIZATION = "organization";          // 组织模块
    
    // 认证模块权限点
    String AUTH_ROLE = "auth:role";                // 角色管理
    String AUTH_USER = "auth:user";                // 用户管理
    String AUTH_PERMISSION = "auth:permission";    // 权限管理
    String AUTH_PERMISSION_REQUEST = "auth:permission:request";  // 权限申请
    String AUTH_LOGIN_HISTORY = "auth:login:history";           // 登录历史
    
    // 系统模块权限点
    String SYSTEM_USER = "system:user";            // 系统用户管理
    String SYSTEM_ROLE = "system:role";            // 系统角色管理  
    String SYSTEM_PERMISSION = "system:permission"; // 系统权限管理
    String SYSTEM_MONITOR = "system:monitor";      // 系统监控
    String SYSTEM_MONITOR_CONFIG = "system:monitor:config";  // 监控配置
    String SYSTEM_MONITOR_ALERT = "system:monitor:alert";    // 监控告警
    String SYSTEM_UPGRADE = "system:upgrade";      // 系统升级
    String SYSTEM_UPGRADE_VERSION = "system:upgrade:version"; // 版本升级
    String SYSTEM_UPGRADE_PATCH = "system:upgrade:patch";     // 补丁升级
    
    // 人事模块权限点
    String PERSONNEL_LAWYER = "personnel:lawyer";        // 律师管理
    String PERSONNEL_EMPLOYEE = "personnel:employee";    // 员工管理
    String PERSONNEL_CONTRACT = "personnel:contract";    // 人事合同
    
    // 组织模块权限点
    String ORGANIZATION_DEPARTMENT = "organization:department";  // 部门管理
    String ORGANIZATION_POSITION = "organization:position";      // 职位管理
    String ORGANIZATION_TEAM = "organization:team";              // 团队管理

    // ...如有新模块/操作，按此规范补充
}