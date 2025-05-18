package com.lawfirm.api.constant;

import com.lawfirm.api.config.ApiVersionConfig;

/**
 * API常量类
 * 统一管理API路径和版本
 */
public class ApiConstants {
    
    /**
     * API基础路径
     */
    public static final String API_BASE = "/api";
    
    /**
     * 当前API版本
     */
    public static final String API_VERSION = ApiVersionConfig.CURRENT_API_VERSION;
    
    /**
     * 带版本的API路径前缀
     */
    public static final String API_VERSION_PREFIX = ApiVersionConfig.API_VERSION_PREFIX;
    
    /**
     * API模块常量
     * 映射到各业务模块定义的API前缀
     */
    public static class Module {
        // 用户认证模块 - 对应AuthConstants.Api.AUTH
        public static final String AUTH = API_BASE + "/auth";
        
        // 系统管理模块
        public static final String SYSTEM = API_BASE + "/system";
        
        // 客户管理模块 - 对应ClientConstants.API_PREFIX /api/v1/clients
        public static final String CLIENT = API_BASE + "/" + API_VERSION + "/clients";
        
        // 案件管理模块 - 对应CaseBusinessConstants.Controller.API_PREFIX /api/v1/cases
        public static final String CASE = API_BASE + "/" + API_VERSION + "/cases";
        
        // 合同管理模块 - 对应ContractConstants.API_PREFIX
        public static final String CONTRACT = API_BASE + "/" + API_VERSION + "/contracts";
        
        // 文档管理模块
        public static final String DOCUMENT = API_BASE + "/" + API_VERSION + "/documents";
        
        // 任务管理模块 - 对应TaskBusinessConstants.Controller.API_PREFIX /api/v1/tasks
        public static final String TASK = API_BASE + "/" + API_VERSION + "/tasks";
        
        // 日程管理模块
        public static final String SCHEDULE = API_BASE + "/" + API_VERSION + "/schedules";
        
        // 财务管理模块
        public static final String FINANCE = API_BASE + "/" + API_VERSION + "/finance";
        
        // 知识库管理模块 - 对应KnowledgeConstants.API_PREFIX
        public static final String KNOWLEDGE = API_BASE + "/" + API_VERSION + "/knowledge";
        
        // 归档管理模块
        public static final String ARCHIVE = API_BASE + "/archive";
        
        // 人事管理模块
        public static final String PERSONNEL = API_BASE + "/" + API_VERSION + "/personnel";
        
        // 统计分析模块
        public static final String ANALYSIS = API_BASE + "/analysis";
    }
    
    /**
     * API操作常量
     * 定义通用的API操作后缀
     */
    public static class Operation {
        // 查询操作
        public static final String QUERY = "/query";
        
        // 列表操作
        public static final String LIST = "/list";
        
        // 分页查询
        public static final String PAGE = "/page";
        
        // 获取详情
        public static final String DETAIL = "/detail";
        
        // 创建操作
        public static final String CREATE = "/create";
        
        // 更新操作
        public static final String UPDATE = "/update";
        
        // 删除操作
        public static final String DELETE = "/delete";
        
        // 批量操作前缀
        public static final String BATCH = "/batch";
        
        // 导出操作
        public static final String EXPORT = "/export";
        
        // 导入操作
        public static final String IMPORT = "/import";
    }
} 