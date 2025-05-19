package com.lawfirm.api.constant;

/**
 * API模块常量模板类
 * 各模块可参考此模板定义自己的常量类
 * 注意：这是一个模板类，不应被直接使用
 */
public class ApiModuleTemplate {
    
    /**
     * API前缀示例 - 以客户模块为例
     * 实际使用时替换为对应模块的常量引用
     */
    public static final String API_PREFIX = ApiConstants.Module.CLIENT; // 对应 /api/v1/clients
    
    /**
     * 子资源前缀示例
     */
    public static final String API_SUB_RESOURCE_PREFIX = API_PREFIX + "/sub-resource";
    
    /**
     * 常用API路径示例
     * 按RESTful风格定义API路径
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
     * 其他高级路径示例
     */
    public static class AdvancedUrl {
        // 按状态查询
        public static final String QUERY_BY_STATUS = API_PREFIX + "/status/{status}";
        
        // 统计接口
        public static final String STATISTICS = API_PREFIX + "/statistics";
        
        // 搜索接口
        public static final String SEARCH = API_PREFIX + "/search";
    }
} 