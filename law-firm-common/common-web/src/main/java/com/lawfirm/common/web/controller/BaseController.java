package com.lawfirm.common.web.controller;

import com.lawfirm.common.web.request.PageRequest;
import com.lawfirm.common.web.response.PageResponse;
import com.lawfirm.common.web.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 基础控制器
 */
public abstract class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession session;

    /**
     * 获取当前请求对象
     */
    protected HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取当前响应对象
     */
    protected HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取当前会话对象
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取请求属性
     */
    protected ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取当前页码
     */
    protected Integer getPageNum() {
        String pageNum = request.getParameter("pageNum");
        return pageNum != null ? Integer.parseInt(pageNum) : 1;
    }

    /**
     * 获取每页记录数
     */
    protected Integer getPageSize() {
        String pageSize = request.getParameter("pageSize");
        return pageSize != null ? Integer.parseInt(pageSize) : 10;
    }

    /**
     * 获取排序字段
     */
    protected String getOrderBy() {
        return request.getParameter("orderBy");
    }

    /**
     * 获取排序方式
     */
    protected Boolean getIsAsc() {
        String isAsc = request.getParameter("isAsc");
        return isAsc == null || Boolean.parseBoolean(isAsc);
    }

    /**
     * 获取分页请求
     */
    protected PageRequest getPageRequest() {
        PageRequest pageRequest = new PageRequest();
        Integer pageNum = getPageNum();
        Integer pageSize = getPageSize();
        String orderBy = getOrderBy();
        Boolean isAsc = getIsAsc();
        if (pageRequest != null) {
            pageRequest.setPageNum(pageNum);
            pageRequest.setPageSize(pageSize);
            pageRequest.setOrderBy(orderBy);
            pageRequest.setOrderType(isAsc ? "asc" : "desc");
            pageRequest.setIsAsc(isAsc);
        }
        return pageRequest;
    }

    /**
     * 响应返回结果
     */
    protected <T> R<T> toResponse(T data) {
        return R.ok(data);
    }

    /**
     * 响应返回结果
     */
    protected <T> R<PageResponse<T>> toResponse(PageResponse<T> page) {
        return R.ok(page);
    }
} 