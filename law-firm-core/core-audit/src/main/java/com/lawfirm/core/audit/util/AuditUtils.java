package com.lawfirm.core.audit.util;

import com.lawfirm.common.web.utils.IpUtils;
import com.lawfirm.common.web.utils.WebUtils;
import com.lawfirm.model.log.dto.AuditLogDTO;
import lombok.experimental.UtilityClass;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 审计工具类
 */
@UtilityClass
public class AuditUtils {

    /**
     * 获取方法名
     */
    public String getMethodName(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        return signature.getDeclaringType().getSimpleName() + "." + signature.getName();
    }

    /**
     * 构建审计日志
     */
    public AuditLogDTO buildAuditLog(String module, String operation, String description) {
        return new AuditLogDTO()
                .setModule(module)
                .setDescription(description)
                .setOperatorIp(IpUtils.getIpAddr(WebUtils.getRequest()));
    }
} 