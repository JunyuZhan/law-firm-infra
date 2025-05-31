package com.lawfirm.model.auth.vo;

import com.lawfirm.model.auth.enums.DataScopeEnum;
import com.lawfirm.model.auth.enums.ModuleTypeEnum;
import com.lawfirm.model.auth.enums.OperationTypeEnum;
import com.lawfirm.model.auth.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 权限矩阵视图对象
 * 用于向前端返回权限矩阵数据
 *
 * @author System
 */
@Data
@Schema(description = "权限矩阵视图对象")
public class PermissionMatrixVO {

    @Schema(description = "用户ID（如果是用户权限矩阵）")
    private Long userId;

    @Schema(description = "角色类型（如果是角色权限矩阵）")
    private RoleEnum roleType;

    @Schema(description = "权限矩阵类型：FULL-完整矩阵，USER-用户矩阵，ROLE-角色矩阵")
    private String matrixType;

    @Schema(description = "权限矩阵数据：角色 -> 模块 -> 操作权限映射")
    private Map<String, Map<String, RoleModulePermission>> permissionMatrix;

    @Schema(description = "用户可访问的模块列表")
    private List<ModuleTypeEnum> accessibleModules;

    @Schema(description = "权限检查结果缓存")
    private Map<String, Boolean> permissionCache;

    @Schema(description = "生成时间戳")
    private Long timestamp;

    /**
     * 角色模块权限信息
     */
    @Data
    @Schema(description = "角色模块权限信息")
    public static class RoleModulePermission {

        @Schema(description = "是否有权限")
        private Boolean hasPermission;

        @Schema(description = "数据访问范围")
        private DataScopeEnum dataScope;

        @Schema(description = "可执行的操作类型列表")
        private List<OperationTypeEnum> allowedOperations;

        @Schema(description = "权限描述")
        private String permissionDescription;

        @Schema(description = "是否只读")
        private Boolean readOnly;

        @Schema(description = "是否需要审批")
        private Boolean requiresApproval;

        public RoleModulePermission() {}

        public RoleModulePermission(Boolean hasPermission, DataScopeEnum dataScope) {
            this.hasPermission = hasPermission;
            this.dataScope = dataScope;
        }

        public RoleModulePermission(Boolean hasPermission, DataScopeEnum dataScope, 
                                  List<OperationTypeEnum> allowedOperations) {
            this.hasPermission = hasPermission;
            this.dataScope = dataScope;
            this.allowedOperations = allowedOperations;
        }

        /**
         * 生成权限描述
         */
        public String generatePermissionDescription() {
            if (!hasPermission) {
                return "无权限";
            }

            StringBuilder desc = new StringBuilder();
            if (dataScope != null) {
                switch (dataScope) {
                    case ALL:
                        desc.append("✓全所");
                        break;
                    case DEPARTMENT_FULL:
                        desc.append("✓部门全权");
                        break;
                    case TEAM:
                        desc.append("✓团队");
                        break;
                    case DEPARTMENT_RELATED:
                        desc.append("✓部门相关");
                        break;
                    case PERSONAL:
                        desc.append("✓个人");
                        break;
                    default:
                        desc.append("✓");
                }
            }

            if (readOnly != null && readOnly) {
                desc.append("/只读");
            }

            if (requiresApproval != null && requiresApproval) {
                desc.append("/审批");
            }

            this.permissionDescription = desc.toString();
            return this.permissionDescription;
        }
    }

    /**
     * 矩阵类型常量
     */
    public static class MatrixType {
        public static final String FULL = "FULL";
        public static final String USER = "USER";
        public static final String ROLE = "ROLE";
    }

    public PermissionMatrixVO() {
        this.timestamp = System.currentTimeMillis();
    }

    public PermissionMatrixVO(String matrixType) {
        this.matrixType = matrixType;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 检查指定权限是否存在于缓存中
     */
    public Boolean getCachedPermission(String permissionKey) {
        return permissionCache != null ? permissionCache.get(permissionKey) : null;
    }

    /**
     * 缓存权限检查结果
     */
    public void cachePermission(String permissionKey, Boolean hasPermission) {
        if (permissionCache == null) {
            permissionCache = new java.util.HashMap<>();
        }
        permissionCache.put(permissionKey, hasPermission);
    }
}