package com.lawfirm.api.constant;

/**
 * API模块常量模板类
 * 各模块可参考此模板定义自己的常量类
 * 注意：这是一个模板类，不应被直接使用
 */
public class ApiModuleTemplate {
    
    /**
     * API前缀示例（使用现有的客户模块常量）
     * 实际使用时替换为对应模块的常量引用
     */
    public static final String API_PREFIX = ApiConstants.Module.CLIENT; // 对应 /api/v1/clients
    
    /**
     * 版本化API前缀
     * API层对业务模块的映射
     */
    public static final String API_VERSION_PREFIX = API_PREFIX; // 业务模块已包含版本号
    
    /**
     * 子资源前缀示例
     */
    public static final String API_SUB_RESOURCE_PREFIX = API_PREFIX + "/sub-resource";
    
    /**
     * 版本化子资源前缀示例
     */
    public static final String API_VERSION_SUB_RESOURCE_PREFIX = API_VERSION_PREFIX + "/sub-resource";
    
    /**
     * 常用操作URL示例
     */
    public static class Url {
        // 列表查询
        public static final String LIST = API_PREFIX + ApiConstants.Operation.LIST;
        
        // 分页查询
        public static final String PAGE = API_PREFIX + ApiConstants.Operation.PAGE;
        
        // 详情查询（需要拼接ID）
        public static final String DETAIL = API_PREFIX + "/{id}" + ApiConstants.Operation.DETAIL;
        
        // 创建
        public static final String CREATE = API_PREFIX + ApiConstants.Operation.CREATE;
        
        // 更新
        public static final String UPDATE = API_PREFIX + ApiConstants.Operation.UPDATE;
        
        // 删除
        public static final String DELETE = API_PREFIX + "/{id}" + ApiConstants.Operation.DELETE;
        
        // 批量删除
        public static final String BATCH_DELETE = API_PREFIX + ApiConstants.Operation.BATCH + ApiConstants.Operation.DELETE;
        
        // 导出
        public static final String EXPORT = API_PREFIX + ApiConstants.Operation.EXPORT;
        
        // 导入
        public static final String IMPORT = API_PREFIX + ApiConstants.Operation.IMPORT;
    }
    
    /**
     * 版本化API URL示例
     */
    public static class VersionUrl {
        // 列表查询
        public static final String LIST = API_VERSION_PREFIX + ApiConstants.Operation.LIST;
        
        // 分页查询
        public static final String PAGE = API_VERSION_PREFIX + ApiConstants.Operation.PAGE;
        
        // 详情查询（需要拼接ID）
        public static final String DETAIL = API_VERSION_PREFIX + "/{id}" + ApiConstants.Operation.DETAIL;
        
        // 创建
        public static final String CREATE = API_VERSION_PREFIX + ApiConstants.Operation.CREATE;
        
        // 更新
        public static final String UPDATE = API_VERSION_PREFIX + ApiConstants.Operation.UPDATE;
        
        // 删除
        public static final String DELETE = API_VERSION_PREFIX + "/{id}" + ApiConstants.Operation.DELETE;
    }
} 