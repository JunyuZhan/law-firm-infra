package com.lawfirm.common.security.context;

import cn.dev33.satoken.context.SaHolder;
import com.lawfirm.common.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * 租户安全上下文
 */
@Slf4j
public class TenantSecurityContextHolder {
    
    private static final String TENANT_ID_KEY = "tenantId";
    private static final String TENANT_NAME_KEY = "tenantName";
    private static final String TENANT_CODE_KEY = "tenantCode";

    /**
     * 获取租户ID
     */
    public static Long getTenantId() {
        try {
            Object tenantId = SaHolder.getStorage().get(TENANT_ID_KEY);
            return tenantId != null ? Long.valueOf(tenantId.toString()) : null;
        } catch (Exception e) {
            log.error("获取租户ID异常", e);
            throw new BusinessException("获取租户ID异常");
        }
    }

    /**
     * 设置租户ID
     */
    public static void setTenantId(Long tenantId) {
        if (tenantId != null) {
            SaHolder.getStorage().set(TENANT_ID_KEY, String.valueOf(tenantId));
        }
    }

    /**
     * 获取租户名称
     */
    public static String getTenantName() {
        try {
            Object tenantName = SaHolder.getStorage().get(TENANT_NAME_KEY);
            return tenantName != null ? tenantName.toString() : null;
        } catch (Exception e) {
            log.error("获取租户名称异常", e);
            throw new BusinessException("获取租户名称异常");
        }
    }

    /**
     * 设置租户名称
     */
    public static void setTenantName(String tenantName) {
        if (tenantName != null) {
            SaHolder.getStorage().set(TENANT_NAME_KEY, tenantName);
        }
    }

    /**
     * 获取租户编码
     */
    public static String getTenantCode() {
        try {
            Object tenantCode = SaHolder.getStorage().get(TENANT_CODE_KEY);
            return tenantCode != null ? tenantCode.toString() : null;
        } catch (Exception e) {
            log.error("获取租户编码异常", e);
            throw new BusinessException("获取租户编码异常");
        }
    }

    /**
     * 设置租户编码
     */
    public static void setTenantCode(String tenantCode) {
        if (tenantCode != null) {
            SaHolder.getStorage().set(TENANT_CODE_KEY, tenantCode);
        }
    }

    /**
     * 清除租户信息
     */
    public static void clear() {
        SaHolder.getStorage().delete(TENANT_ID_KEY);
        SaHolder.getStorage().delete(TENANT_NAME_KEY);
        SaHolder.getStorage().delete(TENANT_CODE_KEY);
    }
} 