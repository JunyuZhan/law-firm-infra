package com.lawfirm.common.web.support;

import com.lawfirm.common.core.constant.CommonConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 分页支持类
 * 提供分页参数的获取和验证功能
 */
public abstract class PageSupport extends WebSupport {

    /**
     * 获取当前页码
     * 默认为1
     */
    protected Integer getPageNum() {
        String pageNum = getRequest().getParameter("pageNum");
        if (StringUtils.isBlank(pageNum)) {
            return CommonConstants.DEFAULT_PAGE_NUMBER;
        }
        try {
            int num = Integer.parseInt(pageNum);
            return num > 0 ? num : CommonConstants.DEFAULT_PAGE_NUMBER;
        } catch (NumberFormatException e) {
            return CommonConstants.DEFAULT_PAGE_NUMBER;
        }
    }

    /**
     * 获取每页记录数
     * 默认为10,最大为100
     */
    protected Integer getPageSize() {
        String pageSize = getRequest().getParameter("pageSize");
        if (StringUtils.isBlank(pageSize)) {
            return CommonConstants.DEFAULT_PAGE_SIZE;
        }
        try {
            int size = Integer.parseInt(pageSize);
            if (size <= 0) {
                return CommonConstants.DEFAULT_PAGE_SIZE;
            }
            return Math.min(size, CommonConstants.MAX_PAGE_SIZE);
        } catch (NumberFormatException e) {
            return CommonConstants.DEFAULT_PAGE_SIZE;
        }
    }

    /**
     * 获取排序字段
     */
    protected String getOrderBy() {
        return getRequest().getParameter("orderBy");
    }

    /**
     * 获取排序方向
     * 默认为升序(asc)
     */
    protected String getOrderType() {
        String orderType = getRequest().getParameter("orderType");
        if (StringUtils.isBlank(orderType)) {
            return CommonConstants.SORT_ASC;
        }
        orderType = orderType.toLowerCase();
        return CommonConstants.SORT_DESC.equals(orderType) ? 
               CommonConstants.SORT_DESC : CommonConstants.SORT_ASC;
    }

    /**
     * 获取是否升序
     * 默认为true
     */
    protected Boolean getIsAsc() {
        return CommonConstants.SORT_ASC.equals(getOrderType());
    }
} 