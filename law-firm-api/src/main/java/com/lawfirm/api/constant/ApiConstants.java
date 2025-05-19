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
    public static final String API_BASE = ApiVersionConfig.API_PREFIX;
    
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
     * 采用统一的命名规则：
     * 1. 非业务模块（系统功能）使用 /api/{module} 格式
     * 2. 业务模块统一使用版本化路径 /api/v1/{module} 格式
     */
    public static class Module {
        // 非版本化模块 - 系统功能
        
        // 用户认证模块
        public static final String AUTH = API_BASE + "/auth";
        
        // 系统管理模块
        public static final String SYSTEM = API_BASE + "/system";
        
        // 版本化模块 - 业务功能
        
        // 客户管理模块
        public static final String CLIENT = API_VERSION_PREFIX + "/clients";
        
        // 案件管理模块
        public static final String CASE = API_VERSION_PREFIX + "/cases";
        
        // 合同管理模块
        public static final String CONTRACT = API_VERSION_PREFIX + "/contracts";
        
        // 文档管理模块
        public static final String DOCUMENT = API_VERSION_PREFIX + "/documents";
        
        // 任务管理模块
        public static final String TASK = API_VERSION_PREFIX + "/tasks";
        
        // 日程管理模块
        public static final String SCHEDULE = API_VERSION_PREFIX + "/schedules";
        
        // 财务管理模块
        public static final String FINANCE = API_VERSION_PREFIX + "/finance";
        
        // 知识库管理模块
        public static final String KNOWLEDGE = API_VERSION_PREFIX + "/knowledge";
        
        // 归档管理模块 - 修改为版本化路径
        public static final String ARCHIVE = API_VERSION_PREFIX + "/archive";
        
        // 人事管理模块
        public static final String PERSONNEL = API_VERSION_PREFIX + "/personnel";
        
        // 统计分析模块 - 修改为版本化路径
        public static final String ANALYSIS = API_VERSION_PREFIX + "/analysis";
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