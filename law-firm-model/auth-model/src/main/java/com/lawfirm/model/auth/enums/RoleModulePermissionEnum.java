package com.lawfirm.model.auth.enums;

import lombok.Getter;

/**
 * 角色-模块权限关系枚举（通用权限，不包含业务特定权限）
 */
@Getter
public enum RoleModulePermissionEnum {

    // 系统管理员的权限
    ADMIN_ALL(RoleEnum.ADMIN, ModuleTypeEnum.values(), OperationTypeEnum.FULL),

    // 普通用户的权限
    USER_DOCUMENT(RoleEnum.USER, ModuleTypeEnum.DOCUMENT, OperationTypeEnum.PERSONAL),
    USER_SCHEDULE(RoleEnum.USER, ModuleTypeEnum.SCHEDULE, OperationTypeEnum.PERSONAL),
    USER_MESSAGE(RoleEnum.USER, ModuleTypeEnum.MESSAGE, OperationTypeEnum.PERSONAL),
    
    // 用户审批示例
    USER_APPROVE(RoleEnum.USER, ModuleTypeEnum.DOCUMENT, OperationTypeEnum.APPROVE),
    
    // 用户申请示例
    USER_APPLY(RoleEnum.USER, ModuleTypeEnum.DOCUMENT, OperationTypeEnum.APPLY);

    private final RoleEnum role;
    private final ModuleTypeEnum module;
    private final OperationTypeEnum operation;

    RoleModulePermissionEnum(RoleEnum role, ModuleTypeEnum module, OperationTypeEnum operation) {
        this.role = role;
        this.module = module;
        this.operation = operation;
    }

    RoleModulePermissionEnum(RoleEnum role, ModuleTypeEnum[] modules, OperationTypeEnum operation) {
        this(role, modules[0], operation);
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