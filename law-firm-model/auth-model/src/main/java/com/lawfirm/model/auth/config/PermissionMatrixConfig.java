package com.lawfirm.model.auth.config;

import com.lawfirm.model.auth.enums.DataScopeEnum;
import com.lawfirm.model.auth.enums.ModuleTypeEnum;
import com.lawfirm.model.auth.enums.OperationTypeEnum;
import com.lawfirm.model.auth.enums.RoleEnum;
import lombok.Data;

import java.util.*;

/**
 * 权限矩阵配置类
 * 定义角色与模块的权限映射关系，对应前端权限矩阵
 *
 * @author System
 */
@Data
public class PermissionMatrixConfig {

    /**
     * 权限矩阵映射：角色 -> 模块 -> 权限配置
     */
    private static final Map<RoleEnum, Map<ModuleTypeEnum, ModulePermissionConfig>> PERMISSION_MATRIX;

    static {
        PERMISSION_MATRIX = initializePermissionMatrix();
    }

    /**
     * 模块权限配置
     */
    @Data
    public static class ModulePermissionConfig {
        private boolean hasAccess;                    // 是否有访问权限
        private DataScopeEnum dataScope;             // 数据访问范围
        private Set<OperationTypeEnum> allowedOperations; // 允许的操作类型
        private boolean readOnly;                    // 是否只读
        private boolean requiresApproval;            // 是否需要审批
        private boolean canCreateForTeam;            // 是否可以为团队创建

        public ModulePermissionConfig() {
            this.allowedOperations = new HashSet<>();
        }

        public ModulePermissionConfig(boolean hasAccess, DataScopeEnum dataScope) {
            this();
            this.hasAccess = hasAccess;
            this.dataScope = dataScope;
        }

        public ModulePermissionConfig(boolean hasAccess, DataScopeEnum dataScope, 
                                    OperationTypeEnum... operations) {
            this(hasAccess, dataScope);
            this.allowedOperations.addAll(Arrays.asList(operations));
        }

        public ModulePermissionConfig readOnly() {
            this.readOnly = true;
            this.allowedOperations.clear();
            this.allowedOperations.add(OperationTypeEnum.READ_ONLY);
            return this;
        }

        public ModulePermissionConfig withApproval() {
            this.requiresApproval = true;
            this.allowedOperations.add(OperationTypeEnum.APPROVE);
            return this;
        }

        public ModulePermissionConfig withTeamCreate() {
            this.canCreateForTeam = true;
            this.allowedOperations.add(OperationTypeEnum.CREATE);
            return this;
        }

        public ModulePermissionConfig withOperations(OperationTypeEnum... operations) {
            this.allowedOperations.addAll(Arrays.asList(operations));
            return this;
        }
    }

    /**
     * 初始化权限矩阵
     * 基于前端权限矩阵文档定义
     */
    private static Map<RoleEnum, Map<ModuleTypeEnum, ModulePermissionConfig>> initializePermissionMatrix() {
        Map<RoleEnum, Map<ModuleTypeEnum, ModulePermissionConfig>> matrix = new HashMap<>();

        // 管理员权限配置
        Map<ModuleTypeEnum, ModulePermissionConfig> adminPermissions = new HashMap<>();
        adminPermissions.put(ModuleTypeEnum.SYSTEM, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        adminPermissions.put(ModuleTypeEnum.CASE, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        adminPermissions.put(ModuleTypeEnum.CLIENT, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        adminPermissions.put(ModuleTypeEnum.CONTRACT, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        adminPermissions.put(ModuleTypeEnum.DOCUMENT, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        adminPermissions.put(ModuleTypeEnum.FINANCE, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        adminPermissions.put(ModuleTypeEnum.PERSONNEL, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        adminPermissions.put(ModuleTypeEnum.KNOWLEDGE, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        adminPermissions.put(ModuleTypeEnum.SCHEDULE, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        adminPermissions.put(ModuleTypeEnum.MESSAGE, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        adminPermissions.put(ModuleTypeEnum.ANALYSIS, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        matrix.put(RoleEnum.ADMIN, adminPermissions);

        // 律所主任权限配置
        Map<ModuleTypeEnum, ModulePermissionConfig> directorPermissions = new HashMap<>();
        directorPermissions.put(ModuleTypeEnum.SYSTEM, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.CREATE, OperationTypeEnum.READ_ONLY, OperationTypeEnum.EDIT));
        directorPermissions.put(ModuleTypeEnum.CASE, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        directorPermissions.put(ModuleTypeEnum.CLIENT, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        directorPermissions.put(ModuleTypeEnum.CONTRACT, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        directorPermissions.put(ModuleTypeEnum.DOCUMENT, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        directorPermissions.put(ModuleTypeEnum.FINANCE, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.APPROVE));
        directorPermissions.put(ModuleTypeEnum.PERSONNEL, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        directorPermissions.put(ModuleTypeEnum.KNOWLEDGE, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        directorPermissions.put(ModuleTypeEnum.SCHEDULE, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        directorPermissions.put(ModuleTypeEnum.MESSAGE, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        directorPermissions.put(ModuleTypeEnum.ANALYSIS, new ModulePermissionConfig(true, DataScopeEnum.ALL, OperationTypeEnum.FULL));
        matrix.put(RoleEnum.DIRECTOR, directorPermissions);

        // 合伙人权限配置
        Map<ModuleTypeEnum, ModulePermissionConfig> partnerPermissions = new HashMap<>();
        partnerPermissions.put(ModuleTypeEnum.CASE, new ModulePermissionConfig(true, DataScopeEnum.TEAM, OperationTypeEnum.FULL));
        partnerPermissions.put(ModuleTypeEnum.CLIENT, new ModulePermissionConfig(true, DataScopeEnum.TEAM, OperationTypeEnum.FULL));
        partnerPermissions.put(ModuleTypeEnum.CONTRACT, new ModulePermissionConfig(true, DataScopeEnum.TEAM, OperationTypeEnum.FULL));
        partnerPermissions.put(ModuleTypeEnum.DOCUMENT, new ModulePermissionConfig(true, DataScopeEnum.TEAM, OperationTypeEnum.FULL));
        partnerPermissions.put(ModuleTypeEnum.FINANCE, new ModulePermissionConfig(false, null)); // 无权限
        partnerPermissions.put(ModuleTypeEnum.PERSONNEL, new ModulePermissionConfig(true, DataScopeEnum.TEAM, OperationTypeEnum.FULL));
        partnerPermissions.put(ModuleTypeEnum.KNOWLEDGE, new ModulePermissionConfig(true, DataScopeEnum.TEAM, OperationTypeEnum.FULL));
        partnerPermissions.put(ModuleTypeEnum.SCHEDULE, new ModulePermissionConfig(true, DataScopeEnum.TEAM, OperationTypeEnum.FULL));
        partnerPermissions.put(ModuleTypeEnum.MESSAGE, new ModulePermissionConfig(true, DataScopeEnum.TEAM, OperationTypeEnum.FULL));
        partnerPermissions.put(ModuleTypeEnum.ANALYSIS, new ModulePermissionConfig(true, DataScopeEnum.TEAM, OperationTypeEnum.FULL));
        matrix.put(RoleEnum.PARTNER, partnerPermissions);

        // 执业律师权限配置
        Map<ModuleTypeEnum, ModulePermissionConfig> lawyerPermissions = new HashMap<>();
        lawyerPermissions.put(ModuleTypeEnum.CASE, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.CREATE, OperationTypeEnum.READ_ONLY, OperationTypeEnum.EDIT).withTeamCreate());
        lawyerPermissions.put(ModuleTypeEnum.CLIENT, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.CREATE, OperationTypeEnum.READ_ONLY, OperationTypeEnum.EDIT).withTeamCreate());
        lawyerPermissions.put(ModuleTypeEnum.CONTRACT, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.CREATE, OperationTypeEnum.READ_ONLY, OperationTypeEnum.EDIT).withTeamCreate());
        lawyerPermissions.put(ModuleTypeEnum.DOCUMENT, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.CREATE, OperationTypeEnum.READ_ONLY, OperationTypeEnum.EDIT).withTeamCreate());
        lawyerPermissions.put(ModuleTypeEnum.FINANCE, new ModulePermissionConfig(false, null)); // 无权限
        lawyerPermissions.put(ModuleTypeEnum.PERSONNEL, new ModulePermissionConfig(false, null)); // 无权限
        lawyerPermissions.put(ModuleTypeEnum.KNOWLEDGE, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.CREATE, OperationTypeEnum.READ_ONLY, OperationTypeEnum.EDIT).withTeamCreate());
        lawyerPermissions.put(ModuleTypeEnum.SCHEDULE, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.CREATE, OperationTypeEnum.READ_ONLY, OperationTypeEnum.EDIT).withTeamCreate());
        lawyerPermissions.put(ModuleTypeEnum.MESSAGE, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.CREATE, OperationTypeEnum.READ_ONLY, OperationTypeEnum.EDIT).withTeamCreate());
        lawyerPermissions.put(ModuleTypeEnum.ANALYSIS, new ModulePermissionConfig(false, null)); // 无权限
        matrix.put(RoleEnum.LAWYER, lawyerPermissions);

        // 实习律师权限配置
        Map<ModuleTypeEnum, ModulePermissionConfig> traineePermissions = new HashMap<>();
        traineePermissions.put(ModuleTypeEnum.CASE, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.READ_ONLY));
        traineePermissions.put(ModuleTypeEnum.CLIENT, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.READ_ONLY));
        traineePermissions.put(ModuleTypeEnum.CONTRACT, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.READ_ONLY));
        traineePermissions.put(ModuleTypeEnum.DOCUMENT, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.READ_ONLY));
        traineePermissions.put(ModuleTypeEnum.FINANCE, new ModulePermissionConfig(false, null)); // 无权限
        traineePermissions.put(ModuleTypeEnum.PERSONNEL, new ModulePermissionConfig(false, null)); // 无权限
        traineePermissions.put(ModuleTypeEnum.KNOWLEDGE, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.READ_ONLY));
        traineePermissions.put(ModuleTypeEnum.SCHEDULE, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.CREATE, OperationTypeEnum.READ_ONLY, OperationTypeEnum.EDIT));
        traineePermissions.put(ModuleTypeEnum.MESSAGE, new ModulePermissionConfig(true, DataScopeEnum.PERSONAL, OperationTypeEnum.CREATE, OperationTypeEnum.READ_ONLY, OperationTypeEnum.EDIT));
        traineePermissions.put(ModuleTypeEnum.ANALYSIS, new ModulePermissionConfig(false, null)); // 无权限
        matrix.put(RoleEnum.TRAINEE, traineePermissions);

        // 行政/财务人员权限配置
        Map<ModuleTypeEnum, ModulePermissionConfig> clerkPermissions = new HashMap<>();
        clerkPermissions.put(ModuleTypeEnum.CASE, new ModulePermissionConfig(false, null)); // 无权限
        clerkPermissions.put(ModuleTypeEnum.CLIENT, new ModulePermissionConfig(false, null)); // 无权限
        clerkPermissions.put(ModuleTypeEnum.CONTRACT, new ModulePermissionConfig(true, DataScopeEnum.DEPARTMENT_RELATED, OperationTypeEnum.READ_ONLY));
        clerkPermissions.put(ModuleTypeEnum.DOCUMENT, new ModulePermissionConfig(true, DataScopeEnum.DEPARTMENT_RELATED, OperationTypeEnum.FULL));
        clerkPermissions.put(ModuleTypeEnum.FINANCE, new ModulePermissionConfig(true, DataScopeEnum.DEPARTMENT_FULL, OperationTypeEnum.FULL));
        clerkPermissions.put(ModuleTypeEnum.PERSONNEL, new ModulePermissionConfig(true, DataScopeEnum.DEPARTMENT_FULL, OperationTypeEnum.FULL));
        clerkPermissions.put(ModuleTypeEnum.KNOWLEDGE, new ModulePermissionConfig(false, null)); // 无权限
        clerkPermissions.put(ModuleTypeEnum.SCHEDULE, new ModulePermissionConfig(true, DataScopeEnum.DEPARTMENT_RELATED, OperationTypeEnum.FULL));
        clerkPermissions.put(ModuleTypeEnum.MESSAGE, new ModulePermissionConfig(true, DataScopeEnum.DEPARTMENT_RELATED, OperationTypeEnum.FULL));
        clerkPermissions.put(ModuleTypeEnum.ANALYSIS, new ModulePermissionConfig(true, DataScopeEnum.DEPARTMENT_FULL, OperationTypeEnum.FULL));
        matrix.put(RoleEnum.CLERK, clerkPermissions);

        // 财务人员权限配置（与行政人员类似，但财务权限更高）
        Map<ModuleTypeEnum, ModulePermissionConfig> financePermissions = new HashMap<>(clerkPermissions);
        matrix.put(RoleEnum.FINANCE, financePermissions);

        return matrix;
    }

    /**
     * 获取指定角色的模块权限配置
     */
    public static ModulePermissionConfig getPermissionConfig(RoleEnum role, ModuleTypeEnum module) {
        Map<ModuleTypeEnum, ModulePermissionConfig> rolePermissions = PERMISSION_MATRIX.get(role);
        if (rolePermissions == null) {
            return new ModulePermissionConfig(false, null); // 默认无权限
        }
        return rolePermissions.getOrDefault(module, new ModulePermissionConfig(false, null));
    }

    /**
     * 获取所有角色的权限矩阵
     */
    public static Map<RoleEnum, Map<ModuleTypeEnum, ModulePermissionConfig>> getFullMatrix() {
        return new HashMap<>(PERMISSION_MATRIX);
    }

    /**
     * 获取指定角色的所有权限配置
     */
    public static Map<ModuleTypeEnum, ModulePermissionConfig> getRolePermissions(RoleEnum role) {
        return PERMISSION_MATRIX.getOrDefault(role, new HashMap<>());
    }
}