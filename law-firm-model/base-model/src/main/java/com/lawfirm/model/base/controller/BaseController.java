package com.lawfirm.model.base.controller;

import com.lawfirm.common.web.support.ValidSupport;
import com.lawfirm.common.core.api.CommonResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 基础控制器
 * 提供基础的控制器功能
 */
public abstract class BaseController extends ValidSupport {

    /**
     * 线程安全的请求对象
     */
    protected static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();
    
    /**
     * 线程安全的响应对象
     */
    protected static ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<>();
    
    /**
     * 线程安全的会话对象
     */
    protected static ThreadLocal<HttpSession> sessionHolder = new ThreadLocal<>();

    /**
     * 请求前自动执行的方法，用于初始化ThreadLocal
     */
    @ModelAttribute
    public void initThreadLocal(HttpServletRequest request, HttpServletResponse response) {
        requestHolder.set(request);
        responseHolder.set(response);
        sessionHolder.set(request.getSession());
    }

    /**
     * 获取当前请求对象
     */
    protected HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    /**
     * 获取当前响应对象
     */
    protected HttpServletResponse getCurrentResponse() {
        return responseHolder.get();
    }

    /**
     * 获取当前会话对象
     */
    protected HttpSession getCurrentSession() {
        return sessionHolder.get();
    }

    /**
     * 清理ThreadLocal
     */
    protected void clearThreadLocal() {
        requestHolder.remove();
        responseHolder.remove();
        sessionHolder.remove();
    }

    /**
     * 返回成功结果
     */
    protected <T> CommonResult<T> success() {
        return CommonResult.success();
    }

    /**
     * 返回成功结果
     */
    protected <T> CommonResult<T> success(T data) {
        return CommonResult.success(data);
    }

    /**
     * 返回成功结果
     */
    protected <T> CommonResult<T> success(String message) {
        return CommonResult.success(message);
    }

    /**
     * 返回成功结果
     */
    protected <T> CommonResult<T> success(T data, String message) {
        return CommonResult.success(data, message);
    }

    /**
     * 返回错误结果
     */
    protected <T> CommonResult<T> error() {
        return CommonResult.error();
    }

    /**
     * 返回错误结果
     */
    protected <T> CommonResult<T> error(String message) {
        return CommonResult.error(message);
    }

    /**
     * 返回错误结果
     */
    protected <T> CommonResult<T> error(int code, String message) {
        return CommonResult.error(code, message);
    }
} 