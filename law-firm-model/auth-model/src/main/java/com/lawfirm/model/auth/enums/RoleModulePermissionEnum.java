package com.lawfirm.model.auth.enums;

import lombok.Getter;

/**
 * 角色-模块权限关系枚举
 */
@Getter
public enum RoleModulePermissionEnum {

    // 系统管理员的权限
    ADMIN_ALL(RoleEnum.ADMIN, ModuleTypeEnum.values(), OperationTypeEnum.FULL),

    // 律所主任的权限
    DIRECTOR_DASHBOARD(RoleEnum.DIRECTOR, ModuleTypeEnum.DASHBOARD, OperationTypeEnum.FULL),
    DIRECTOR_CASE(RoleEnum.DIRECTOR, ModuleTypeEnum.CASE, OperationTypeEnum.FULL),
    DIRECTOR_CLIENT(RoleEnum.DIRECTOR, ModuleTypeEnum.CLIENT, OperationTypeEnum.FULL),
    DIRECTOR_CONTRACT(RoleEnum.DIRECTOR, ModuleTypeEnum.CONTRACT, OperationTypeEnum.FULL),
    DIRECTOR_DOCUMENT(RoleEnum.DIRECTOR, ModuleTypeEnum.DOCUMENT, OperationTypeEnum.FULL),
    DIRECTOR_FINANCE(RoleEnum.DIRECTOR, ModuleTypeEnum.FINANCE, OperationTypeEnum.APPROVE),
    DIRECTOR_ADMIN(RoleEnum.DIRECTOR, ModuleTypeEnum.ADMIN, OperationTypeEnum.APPROVE),
    DIRECTOR_SYSTEM(RoleEnum.DIRECTOR, ModuleTypeEnum.SYSTEM, OperationTypeEnum.READ_ONLY),
    DIRECTOR_KNOWLEDGE(RoleEnum.DIRECTOR, ModuleTypeEnum.KNOWLEDGE, OperationTypeEnum.FULL),
    DIRECTOR_HR(RoleEnum.DIRECTOR, ModuleTypeEnum.HR, OperationTypeEnum.APPROVE),
    DIRECTOR_TASK(RoleEnum.DIRECTOR, ModuleTypeEnum.TASK, OperationTypeEnum.FULL),
    DIRECTOR_SCHEDULE(RoleEnum.DIRECTOR, ModuleTypeEnum.SCHEDULE, OperationTypeEnum.FULL),
    DIRECTOR_MESSAGE(RoleEnum.DIRECTOR, ModuleTypeEnum.MESSAGE, OperationTypeEnum.FULL),
    DIRECTOR_CONFLICT(RoleEnum.DIRECTOR, ModuleTypeEnum.CONFLICT, OperationTypeEnum.APPROVE),
    DIRECTOR_WORKFLOW(RoleEnum.DIRECTOR, ModuleTypeEnum.WORKFLOW, OperationTypeEnum.APPROVE),
    DIRECTOR_ANALYSIS(RoleEnum.DIRECTOR, ModuleTypeEnum.ANALYSIS, OperationTypeEnum.FULL),

    // 合伙人律师的权限
    PARTNER_DASHBOARD(RoleEnum.PARTNER, ModuleTypeEnum.DASHBOARD, OperationTypeEnum.FULL),
    PARTNER_CASE(RoleEnum.PARTNER, ModuleTypeEnum.CASE, OperationTypeEnum.TEAM),
    PARTNER_CLIENT(RoleEnum.PARTNER, ModuleTypeEnum.CLIENT, OperationTypeEnum.TEAM),
    PARTNER_CONTRACT(RoleEnum.PARTNER, ModuleTypeEnum.CONTRACT, OperationTypeEnum.TEAM),
    PARTNER_DOCUMENT(RoleEnum.PARTNER, ModuleTypeEnum.DOCUMENT, OperationTypeEnum.TEAM),
    PARTNER_KNOWLEDGE(RoleEnum.PARTNER, ModuleTypeEnum.KNOWLEDGE, OperationTypeEnum.TEAM),
    PARTNER_TASK(RoleEnum.PARTNER, ModuleTypeEnum.TASK, OperationTypeEnum.TEAM),
    PARTNER_SCHEDULE(RoleEnum.PARTNER, ModuleTypeEnum.SCHEDULE, OperationTypeEnum.TEAM),
    PARTNER_MESSAGE(RoleEnum.PARTNER, ModuleTypeEnum.MESSAGE, OperationTypeEnum.TEAM),
    PARTNER_CONFLICT(RoleEnum.PARTNER, ModuleTypeEnum.CONFLICT, OperationTypeEnum.APPLY),
    PARTNER_WORKFLOW(RoleEnum.PARTNER, ModuleTypeEnum.WORKFLOW, OperationTypeEnum.TEAM),
    PARTNER_ANALYSIS(RoleEnum.PARTNER, ModuleTypeEnum.ANALYSIS, OperationTypeEnum.READ_ONLY),

    // 执业律师的权限
    LAWYER_DASHBOARD(RoleEnum.LAWYER, ModuleTypeEnum.DASHBOARD, OperationTypeEnum.READ_ONLY),
    LAWYER_CASE(RoleEnum.LAWYER, ModuleTypeEnum.CASE, OperationTypeEnum.TEAM),
    LAWYER_CLIENT(RoleEnum.LAWYER, ModuleTypeEnum.CLIENT, OperationTypeEnum.TEAM),
    LAWYER_CONTRACT(RoleEnum.LAWYER, ModuleTypeEnum.CONTRACT, OperationTypeEnum.APPLY),
    LAWYER_DOCUMENT(RoleEnum.LAWYER, ModuleTypeEnum.DOCUMENT, OperationTypeEnum.TEAM),
    LAWYER_KNOWLEDGE(RoleEnum.LAWYER, ModuleTypeEnum.KNOWLEDGE, OperationTypeEnum.TEAM),
    LAWYER_TASK(RoleEnum.LAWYER, ModuleTypeEnum.TASK, OperationTypeEnum.TEAM),
    LAWYER_SCHEDULE(RoleEnum.LAWYER, ModuleTypeEnum.SCHEDULE, OperationTypeEnum.TEAM),
    LAWYER_MESSAGE(RoleEnum.LAWYER, ModuleTypeEnum.MESSAGE, OperationTypeEnum.TEAM),
    LAWYER_CONFLICT(RoleEnum.LAWYER, ModuleTypeEnum.CONFLICT, OperationTypeEnum.APPLY),
    LAWYER_WORKFLOW(RoleEnum.LAWYER, ModuleTypeEnum.WORKFLOW, OperationTypeEnum.APPLY),

    // 实习律师的权限
    TRAINEE_DASHBOARD(RoleEnum.TRAINEE, ModuleTypeEnum.DASHBOARD, OperationTypeEnum.READ_ONLY),
    TRAINEE_CASE(RoleEnum.TRAINEE, ModuleTypeEnum.CASE, OperationTypeEnum.READ_ONLY),
    TRAINEE_CLIENT(RoleEnum.TRAINEE, ModuleTypeEnum.CLIENT, OperationTypeEnum.READ_ONLY),
    TRAINEE_DOCUMENT(RoleEnum.TRAINEE, ModuleTypeEnum.DOCUMENT, OperationTypeEnum.READ_ONLY),
    TRAINEE_KNOWLEDGE(RoleEnum.TRAINEE, ModuleTypeEnum.KNOWLEDGE, OperationTypeEnum.READ_ONLY),
    TRAINEE_TASK(RoleEnum.TRAINEE, ModuleTypeEnum.TASK, OperationTypeEnum.PERSONAL),
    TRAINEE_SCHEDULE(RoleEnum.TRAINEE, ModuleTypeEnum.SCHEDULE, OperationTypeEnum.PERSONAL),
    TRAINEE_MESSAGE(RoleEnum.TRAINEE, ModuleTypeEnum.MESSAGE, OperationTypeEnum.PERSONAL),

    // 行政/财务人员的权限
    CLERK_FINANCE_DASHBOARD(RoleEnum.CLERK_FINANCE, ModuleTypeEnum.DASHBOARD, OperationTypeEnum.READ_ONLY),
    CLERK_FINANCE_FINANCE(RoleEnum.CLERK_FINANCE, ModuleTypeEnum.FINANCE, OperationTypeEnum.FULL),
    CLERK_FINANCE_ADMIN(RoleEnum.CLERK_FINANCE, ModuleTypeEnum.ADMIN, OperationTypeEnum.FULL),
    CLERK_FINANCE_DOCUMENT(RoleEnum.CLERK_FINANCE, ModuleTypeEnum.DOCUMENT, OperationTypeEnum.TEAM),
    CLERK_FINANCE_TASK(RoleEnum.CLERK_FINANCE, ModuleTypeEnum.TASK, OperationTypeEnum.TEAM),
    CLERK_FINANCE_SCHEDULE(RoleEnum.CLERK_FINANCE, ModuleTypeEnum.SCHEDULE, OperationTypeEnum.TEAM),
    CLERK_FINANCE_MESSAGE(RoleEnum.CLERK_FINANCE, ModuleTypeEnum.MESSAGE, OperationTypeEnum.TEAM),
    CLERK_FINANCE_WORKFLOW(RoleEnum.CLERK_FINANCE, ModuleTypeEnum.WORKFLOW, OperationTypeEnum.TEAM);

    private final RoleEnum role;
    private final ModuleTypeEnum module;
    private final OperationTypeEnum operation;

    RoleModulePermissionEnum(RoleEnum role, ModuleTypeEnum module, OperationTypeEnum operation) {
        this.role = role;
        this.module = module;
        this.operation = operation;
    }

    RoleModulePermissionEnum(RoleEnum role, ModuleTypeEnum[] modules, OperationTypeEnum operation) {
        this.role = role;
        this.module = modules[0]; // 只取第一个模块，因为管理员对所有模块都是完全权限
        this.operation = operation;
    }

    /**
     * 获取指定角色对指定模块的默认操作权限
     */
    public static OperationTypeEnum getDefaultOperation(RoleEnum role, ModuleTypeEnum module) {
        for (RoleModulePermissionEnum permission : values()) {
            if (permission.role == role && permission.module == module) {
                return permission.operation;
            }
        }
        return OperationTypeEnum.READ_ONLY; // 默认只读权限
    }

    /**
     * 判断指定角色是否有指定模块的指定操作权限
     */
    public static boolean hasPermission(RoleEnum role, ModuleTypeEnum module, OperationTypeEnum operation) {
        OperationTypeEnum defaultOperation = getDefaultOperation(role, module);
        if (defaultOperation == OperationTypeEnum.FULL) {
            return true;
        }
        return defaultOperation == operation;
    }
} 