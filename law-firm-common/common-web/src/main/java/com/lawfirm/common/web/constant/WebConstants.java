package com.lawfirm.common.web.constant;

/**
 * Web常量定义
 */
public interface WebConstants {

    /**
     * 成功状态码
     */
    int SUCCESS_CODE = 200;

    /**
     * 失败状态码
     */
    int ERROR_CODE = 500;

    /**
     * 未授权状态码
     */
    int UNAUTHORIZED_CODE = 401;

    /**
     * 禁止访问状态码
     */
    int FORBIDDEN_CODE = 403;

    /**
     * 未找到状态码
     */
    int NOT_FOUND_CODE = 404;

    /**
     * 默认每页记录数
     */
    int DEFAULT_PAGE_SIZE = 10;

    /**
     * 默认当前页码
     */
    int DEFAULT_PAGE_NUM = 1;

    /**
     * 默认排序方向
     */
    boolean DEFAULT_IS_ASC = true;

    /**
     * 成功消息
     */
    String SUCCESS_MESSAGE = "success";

    /**
     * 失败消息
     */
    String ERROR_MESSAGE = "未知异常，请联系管理员";

    /**
     * Token请求头名称
     */
    String TOKEN_HEADER = "Authorization";

    /**
     * Token前缀
     */
    String TOKEN_PREFIX = "Bearer ";
} 