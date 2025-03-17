package com.lawfirm.cases.integration.auth;

import com.lawfirm.model.auth.enums.DataScopeEnum;
import com.lawfirm.model.auth.enums.OperationTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * 案件数据权限提供者
 * 为权限系统提供案件相关的数据权限过滤规则
 */
@Slf4j
@Component
@Lazy
@RequiredArgsConstructor
public class CaseDataPermissionProvider {

    private final CasePermissionProvider casePermissionProvider;

    /**
     * 获取用户在案件模块的数据权限范围
     *
     * @param userId 用户ID
     * @param dataScope 数据范围类型
     * @return 可访问的案件ID集合
     */
    public Set<Long> getAccessibleCaseIds(Long userId, DataScopeEnum dataScope) {
        if (userId == null) {
            return Collections.emptySet();
        }

        // 根据数据范围类型返回不同的权限集合
        switch (dataScope) {
            case ALL:
                // 全部数据权限，通常只有系统管理员拥有，不做过滤
                return null;
            case DEPARTMENT_FULL:
                // 部门数据权限，获取用户所在部门下的案件
                return getDepartmentCaseIds(userId);
            case DEPARTMENT_RELATED:
                // 部门及以下数据权限，获取用户所在部门及子部门的案件
                return getDepartmentAndBelowCaseIds(userId);
            case PERSONAL:
                // 个人数据权限，仅获取用户自己的案件
                return getPersonalCaseIds(userId);
            case CUSTOM:
                // 自定义数据权限，根据业务规则自定义过滤逻辑
                return getCustomCaseIds(userId);
            default:
                log.warn("未知的数据范围类型: {}", dataScope);
                return Collections.emptySet();
        }
    }

    /**
     * 获取可以查看的案件ID列表
     *
     * @param userId 用户ID
     * @return 案件ID集合
     */
    public Set<Long> getReadableCaseIds(Long userId) {
        return casePermissionProvider.getUserAccessibleCaseIds(userId, OperationTypeEnum.READ_ONLY);
    }

    /**
     * 获取可以更新的案件ID列表
     *
     * @param userId 用户ID
     * @return 案件ID集合
     */
    public Set<Long> getUpdatableCaseIds(Long userId) {
        return casePermissionProvider.getUserAccessibleCaseIds(userId, OperationTypeEnum.CREATE);
    }

    /**
     * 获取用户所在部门的案件ID列表
     *
     * @param userId 用户ID
     * @return 案件ID集合
     */
    private Set<Long> getDepartmentCaseIds(Long userId) {
        // 实际实现需要根据用户所在部门查询部门下的案件
        // 示例实现，此处返回可读案件
        return getReadableCaseIds(userId);
    }

    /**
     * 获取用户所在部门及子部门的案件ID列表
     *
     * @param userId 用户ID
     * @return 案件ID集合
     */
    private Set<Long> getDepartmentAndBelowCaseIds(Long userId) {
        // 实际实现需要根据用户所在部门及子部门查询案件
        // 示例实现，此处返回可读案件
        return getReadableCaseIds(userId);
    }

    /**
     * 获取用户个人的案件ID列表
     *
     * @param userId 用户ID
     * @return 案件ID集合
     */
    private Set<Long> getPersonalCaseIds(Long userId) {
        // 获取用户创建或负责的案件
        return casePermissionProvider.getUserAccessibleCaseIds(userId, OperationTypeEnum.FULL);
    }

    /**
     * 获取用户自定义权限范围内的案件ID列表
     *
     * @param userId 用户ID
     * @return 案件ID集合
     */
    private Set<Long> getCustomCaseIds(Long userId) {
        // 根据业务规则自定义过滤逻辑
        // 示例实现，此处返回可读案件
        return getReadableCaseIds(userId);
    }
} 