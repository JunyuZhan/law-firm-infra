package com.lawfirm.core.audit.context;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 审计上下文
 */
public class AuditContext {
    
    private static final ThreadLocal<AuditInfo> CONTEXT = new ThreadLocal<>();

    public static void setOperator(String operatorId, String operatorName) {
        AuditInfo info = getContext();
        info.setOperatorId(operatorId)
            .setOperatorName(operatorName);
    }

    public static void setIp(String ip) {
        getContext().setOperatorIp(ip);
    }

    public static AuditInfo get() {
        return getContext();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    private static AuditInfo getContext() {
        AuditInfo info = CONTEXT.get();
        if (info == null) {
            info = new AuditInfo();
            CONTEXT.set(info);
        }
        return info;
    }

    @Data
    @Accessors(chain = true)
    public static class AuditInfo {
        /**
         * 操作人ID
         */
        private String operatorId;
        
        /**
         * 操作人名称
         */
        private String operatorName;
        
        /**
         * 操作人IP
         */
        private String operatorIp;
        
        /**
         * 模块
         */
        private String module;
    }
} 