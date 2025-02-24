package com.lawfirm.common.core.constant;

/**
 * 通用常量
 * 只定义框架级的通用常量，业务相关的常量应该由具体的业务模块自行定义
 */
public interface CommonConstants {
    /**
     * 通用状态
     */
    int STATUS_NORMAL = 0;    // 正常
    int STATUS_DISABLE = 1;   // 禁用
    int STATUS_DELETE = 2;    // 删除

    /**
     * 布尔值
     */
    int TRUE = 1;
    int FALSE = 0;

    /**
     * 分页默认值
     */
    int DEFAULT_PAGE_SIZE = 10;       // 默认每页记录数
    int DEFAULT_PAGE_NUMBER = 1;      // 默认当前页码
    int MAX_PAGE_SIZE = 100;         // 最大每页记录数
    
    /**
     * 排序方向
     */
    String SORT_ASC = "asc";         // 升序
    String SORT_DESC = "desc";       // 降序
    
    /**
     * 时间格式
     */
    String DATE_FORMAT = "yyyy-MM-dd";
    String TIME_FORMAT = "HH:mm:ss";
    String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 字符集
     */
    String UTF8 = "UTF-8";
    String GBK = "GBK";
    
    /**
     * 符号
     */
    String COMMA = ",";
    String DOT = ".";
    String COLON = ":";
    String SEMICOLON = ";";
    String UNDERSCORE = "_";
    String SLASH = "/";
    String BACKSLASH = "\\";
} 