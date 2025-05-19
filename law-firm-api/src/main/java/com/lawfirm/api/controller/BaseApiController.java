package com.lawfirm.api.controller;

import com.lawfirm.api.config.ApiVersionConfig;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.base.controller.BaseController;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 基础API控制器
 * 提供API版本控制和公共方法
 */
public abstract class BaseApiController extends BaseController {

    /**
     * 当前API版本
     * 直接使用ApiVersionConfig中的定义
     */
    protected static final String API_VERSION = ApiVersionConfig.CURRENT_API_VERSION;
    
    /**
     * API版本前缀
     * 直接使用ApiVersionConfig中的定义
     */
    protected static final String API_VERSION_PREFIX = ApiVersionConfig.API_VERSION_PREFIX;
    
    /**
     * 初始化请求和响应
     */
    @Override
    @ModelAttribute
    public void initThreadLocal(HttpServletRequest request, HttpServletResponse response) {
        super.initThreadLocal(request, response);
        
        // 添加API版本响应头
        response.setHeader("X-API-Version", API_VERSION);
    }
    
    /**
     * 获取API版本
     */
    protected String getApiVersion() {
        return API_VERSION;
    }
    
    /**
     * 获取API版本前缀
     */
    protected String getApiVersionPrefix() {
        return API_VERSION_PREFIX;
    }
    
    /**
     * 成功响应
     */
    protected <T> CommonResult<T> success() {
        return CommonResult.success();
    }
    
    /**
     * 成功响应
     */
    protected <T> CommonResult<T> success(T data) {
        return CommonResult.success(data);
    }
    
    /**
     * 成功响应
     */
    protected <T> CommonResult<T> success(String message) {
        return CommonResult.success(message);
    }
    
    /**
     * 成功响应
     */
    protected <T> CommonResult<T> success(T data, String message) {
        return CommonResult.success(data, message);
    }
    
    /**
     * 错误响应
     */
    protected <T> CommonResult<T> error() {
        return CommonResult.error();
    }
    
    /**
     * 错误响应
     */
    protected <T> CommonResult<T> error(String message) {
        return CommonResult.error(message);
    }
    
    /**
     * 错误响应
     */
    protected <T> CommonResult<T> error(int code, String message) {
        return CommonResult.error(code, message);
    }
}