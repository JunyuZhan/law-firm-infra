package com.lawfirm.auth.service.impl;

import com.lawfirm.model.auth.config.PermissionMatrixConfig;
import com.lawfirm.model.auth.dto.permission.PermissionMatrixDTO;
import com.lawfirm.model.auth.entity.Role;
import com.lawfirm.model.auth.entity.UserRole;
import com.lawfirm.model.auth.enums.DataScopeEnum;
import com.lawfirm.model.auth.enums.ModuleTypeEnum;
import com.lawfirm.model.auth.enums.OperationTypeEnum;
import com.lawfirm.model.auth.mapper.UserRoleMapper;
import com.lawfirm.model.auth.service.PermissionChecker;
import com.lawfirm.model.auth.service.PermissionMatrixService;
import com.lawfirm.model.auth.service.RoleService;
import com.lawfirm.model.auth.vo.PermissionMatrixVO;
import com.lawfirm.model.auth.enums.RoleEnum;
import com.lawfirm.model.personnel.enums.PersonRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限矩阵服务实现类
 * 提供权限矩阵的生成、查询和权限检查功能
 *
 * @author System
 */
@Slf4j
@Service("permissionMatrixService")
@RequiredArgsConstructor
public class PermissionMatrixServiceImpl implements PermissionMatrixService, PermissionChecker {

    private final UserRoleMapper userRoleMapper;
    private final RoleService roleService;

    @Override
    @Cacheable(value = "permissionMatrix", key = "'full'")
    public PermissionMatrixVO getFullPermissionMatrix() {
        log.info("生成完整权限矩阵");
        
        PermissionMatrixVO matrixVO = new PermissionMatrixVO(PermissionMatrixVO.MatrixType.FULL);
        Map<String, Map<String, PermissionMatrixVO.RoleModulePermission>> permissionMatrix = new HashMap<>();
        
        // 遍历所有角色
        for (RoleEnum role : RoleEnum.values()) {
            Map<String, PermissionMatrixVO.RoleModulePermission> modulePermissions = new HashMap<>();
            
            // 遍历所有模块
            for (ModuleTypeEnum module : ModuleTypeEnum.values()) {
                PermissionMatrixConfig.ModulePermissionConfig config = 
                    PermissionMatrixConfig.getPermissionConfig(role, module);
                
                PermissionMatrixVO.RoleModulePermission permission = convertToRoleModulePermission(config);
                modulePermissions.put(module.getCode(), permission);
            }
            
            permissionMatrix.put(role.getCode(), modulePermissions);
        }
        
        matrixVO.setPermissionMatrix(permissionMatrix);
        return matrixVO;
    }

    @Override
    @Cacheable(value = "userPermissionMatrix", key = "#userId")
    public PermissionMatrixVO getUserPermissionMatrix(Long userId) {
        log.info("生成用户权限矩阵，用户ID: {}", userId);
        
        // 获取用户的角色
        RoleEnum userRole = getUserRole(userId);
        if (userRole == null) {
            log.warn("用户没有人员角色，用户ID: {}", userId);
            return createEmptyMatrix(userId);
        }
        
        PermissionMatrixVO matrixVO = new PermissionMatrixVO(PermissionMatrixVO.MatrixType.USER);
        matrixVO.setUserId(userId);
        matrixVO.setRoleType(userRole);
        
        Map<String, Map<String, PermissionMatrixVO.RoleModulePermission>> permissionMatrix = new HashMap<>();
        Map<String, PermissionMatrixVO.RoleModulePermission> modulePermissions = new HashMap<>();
        List<ModuleTypeEnum> accessibleModules = new ArrayList<>();
        
        // 遍历所有模块，生成用户权限
        for (ModuleTypeEnum module : ModuleTypeEnum.values()) {
            PermissionMatrixConfig.ModulePermissionConfig config = 
                PermissionMatrixConfig.getPermissionConfig(userRole, module);
            
            PermissionMatrixVO.RoleModulePermission permission = convertToRoleModulePermission(config);
            modulePermissions.put(module.getCode(), permission);
            
            if (config.isHasAccess()) {
                accessibleModules.add(module);
            }
        }
        
        permissionMatrix.put(userRole.getCode(), modulePermissions);
        matrixVO.setPermissionMatrix(permissionMatrix);
        matrixVO.setAccessibleModules(accessibleModules);
        
        return matrixVO;
    }

    @Override
    @Cacheable(value = "rolePermissionMatrix", key = "#roleEnum.code")
    public PermissionMatrixVO getRolePermissionMatrix(RoleEnum roleEnum) {
        log.info("生成角色权限矩阵，角色: {}", roleEnum.getCode());
        
        PermissionMatrixVO matrixVO = new PermissionMatrixVO(PermissionMatrixVO.MatrixType.ROLE);
        matrixVO.setRoleType(roleEnum);
        
        Map<String, Map<String, PermissionMatrixVO.RoleModulePermission>> permissionMatrix = new HashMap<>();
        Map<String, PermissionMatrixVO.RoleModulePermission> modulePermissions = new HashMap<>();
        List<ModuleTypeEnum> accessibleModules = new ArrayList<>();
        
        // 遍历所有模块
        for (ModuleTypeEnum module : ModuleTypeEnum.values()) {
            PermissionMatrixConfig.ModulePermissionConfig config = 
                PermissionMatrixConfig.getPermissionConfig(roleEnum, module);
            
            PermissionMatrixVO.RoleModulePermission permission = convertToRoleModulePermission(config);
            modulePermissions.put(module.getCode(), permission);
            
            if (config.isHasAccess()) {
                accessibleModules.add(module);
            }
        }
        
        permissionMatrix.put(roleEnum.getCode(), modulePermissions);
        matrixVO.setPermissionMatrix(permissionMatrix);
        matrixVO.setAccessibleModules(accessibleModules);
        
        return matrixVO;
    }

    @Override
    public boolean checkUserPermission(Long userId, ModuleTypeEnum moduleType, OperationTypeEnum operationType) {
        log.debug("检查用户权限，用户ID: {}, 模块: {}, 操作: {}", userId, moduleType.getCode(), operationType.getCode());
        
        RoleEnum userRole = getUserRole(userId);
        if (userRole == null) {
            log.debug("用户没有人员角色，无权限，用户ID: {}", userId);
            return false;
        }
        
        PermissionMatrixConfig.ModulePermissionConfig config = 
            PermissionMatrixConfig.getPermissionConfig(userRole, moduleType);
        
        if (!config.isHasAccess()) {
            log.debug("用户无模块访问权限，用户ID: {}, 模块: {}", userId, moduleType.getCode());
            return false;
        }
        
        // 检查操作权限
        boolean hasOperation = config.getAllowedOperations().contains(operationType) ||
                              config.getAllowedOperations().contains(OperationTypeEnum.FULL);
        
        log.debug("权限检查结果: {}, 用户ID: {}, 模块: {}, 操作: {}", hasOperation, userId, moduleType.getCode(), operationType.getCode());
        return hasOperation;
    }

    @Override
    public DataScopeEnum getUserDataScope(Long userId, ModuleTypeEnum moduleType) {
        log.debug("获取用户数据范围，用户ID: {}, 模块: {}", userId, moduleType.getCode());
        
        RoleEnum userRole = getUserRole(userId);
        if (userRole == null) {
            return DataScopeEnum.PERSONAL; // 默认个人范围
        }
        
        PermissionMatrixConfig.ModulePermissionConfig config = 
            PermissionMatrixConfig.getPermissionConfig(userRole, moduleType);
        
        DataScopeEnum dataScope = config.getDataScope();
        return dataScope != null ? dataScope : DataScopeEnum.PERSONAL;
    }

    @Override
    public Map<String, Boolean> batchCheckUserPermissions(Long userId, List<PermissionMatrixDTO> permissionRequests) {
        log.debug("批量检查用户权限，用户ID: {}, 请求数量: {}", userId, permissionRequests.size());
        
        Map<String, Boolean> results = new HashMap<>();
        
        RoleEnum userRole = getUserRole(userId);
        if (userRole == null) {
            // 用户没有角色，所有权限都为false
            for (PermissionMatrixDTO request : permissionRequests) {
                String key = request.generateRequestKey();
                results.put(key, false);
            }
            return results;
        }
        
        for (PermissionMatrixDTO request : permissionRequests) {
            String key = request.generateRequestKey();
            boolean hasPermission = checkUserPermission(userId, request.getModuleType(), request.getOperationType());
            results.put(key, hasPermission);
        }
        
        return results;
    }

    @Override
    public List<ModuleTypeEnum> getUserAccessibleModules(Long userId) {
        log.debug("获取用户可访问模块，用户ID: {}", userId);
        
        RoleEnum userRole = getUserRole(userId);
        if (userRole == null) {
            return Collections.emptyList();
        }
        
        List<ModuleTypeEnum> accessibleModules = new ArrayList<>();
        
        for (ModuleTypeEnum module : ModuleTypeEnum.values()) {
            PermissionMatrixConfig.ModulePermissionConfig config = 
                PermissionMatrixConfig.getPermissionConfig(userRole, module);
            
            if (config.isHasAccess()) {
                accessibleModules.add(module);
            }
        }
        
        return accessibleModules;
    }

    @Override
    public List<OperationTypeEnum> getUserModuleOperations(Long userId, ModuleTypeEnum moduleType) {
        log.debug("获取用户模块操作权限，用户ID: {}, 模块: {}", userId, moduleType.getCode());
        
        RoleEnum userRole = getUserRole(userId);
        if (userRole == null) {
            return Collections.emptyList();
        }
        
        PermissionMatrixConfig.ModulePermissionConfig config = 
            PermissionMatrixConfig.getPermissionConfig(userRole, moduleType);
        
        if (!config.isHasAccess()) {
            return Collections.emptyList();
        }
        
        return new ArrayList<>(config.getAllowedOperations());
    }

    @Override
    @CacheEvict(value = {"permissionMatrix", "userPermissionMatrix", "rolePermissionMatrix"}, allEntries = true)
    public void refreshPermissionMatrix() {
        log.info("刷新权限矩阵缓存");
        // 缓存会被自动清除，下次访问时重新生成
    }

    @Override
    public Map<String, Object> validatePermissionMatrix() {
        log.info("验证权限矩阵配置完整性");
        
        Map<String, Object> report = new HashMap<>();
        List<String> missingConfigs = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        
        // 检查每个角色是否都有完整的模块配置
        for (RoleEnum role : RoleEnum.values()) {
            for (ModuleTypeEnum module : ModuleTypeEnum.values()) {
                PermissionMatrixConfig.ModulePermissionConfig config = 
                    PermissionMatrixConfig.getPermissionConfig(role, module);
                
                if (config == null) {
                    missingConfigs.add(String.format("角色[%s]缺少模块[%s]的权限配置", role.getName(), module.getName()));
                }
            }
        }
        
        // 检查权限配置的合理性
        for (RoleEnum role : RoleEnum.values()) {
            Map<ModuleTypeEnum, PermissionMatrixConfig.ModulePermissionConfig> rolePermissions = 
                PermissionMatrixConfig.getRolePermissions(role);
            
            for (Map.Entry<ModuleTypeEnum, PermissionMatrixConfig.ModulePermissionConfig> entry : rolePermissions.entrySet()) {
                PermissionMatrixConfig.ModulePermissionConfig config = entry.getValue();
                
                // 检查有访问权限但没有操作权限的情况
                if (config.isHasAccess() && CollectionUtils.isEmpty(config.getAllowedOperations())) {
                    warnings.add(String.format("角色[%s]对模块[%s]有访问权限但没有操作权限", 
                        role.getName(), entry.getKey().getName()));
                }
                
                // 检查数据范围配置
                if (config.isHasAccess() && config.getDataScope() == null) {
                    warnings.add(String.format("角色[%s]对模块[%s]没有配置数据范围", 
                        role.getName(), entry.getKey().getName()));
                }
            }
        }
        
        report.put("valid", missingConfigs.isEmpty());
        report.put("missingConfigs", missingConfigs);
        report.put("warnings", warnings);
        report.put("totalRoles", RoleEnum.values().length);
        report.put("totalModules", ModuleTypeEnum.values().length);
        report.put("checkTime", System.currentTimeMillis());
        
        return report;
    }

    /**
     * 获取用户的角色
     */
    private RoleEnum getUserRole(Long userId) {
        try {
            // 通过用户角色映射获取用户角色关联
            List<UserRole> userRoles = userRoleMapper.selectByUserId(userId);
            if (userRoles == null || userRoles.isEmpty()) {
                return null;
            }
            // 获取第一个角色（假设用户只有一个主要角色）
            UserRole userRole = userRoles.get(0);
            Long roleId = userRole.getRoleId();
            
            // 通过角色服务获取角色信息
            Role role = roleService.getById(roleId);
            if (role == null) {
                return null;
            }
            return RoleEnum.getByCode(role.getCode());
        } catch (Exception e) {
            log.error("获取用户角色失败，用户ID: {}", userId, e);
            return null;
        }
    }

    /**
     * 转换权限配置为视图对象
     */
    private PermissionMatrixVO.RoleModulePermission convertToRoleModulePermission(
            PermissionMatrixConfig.ModulePermissionConfig config) {
        
        PermissionMatrixVO.RoleModulePermission permission = new PermissionMatrixVO.RoleModulePermission();
        permission.setHasPermission(config.isHasAccess());
        permission.setDataScope(config.getDataScope());
        permission.setAllowedOperations(new ArrayList<>(config.getAllowedOperations()));
        permission.setReadOnly(config.isReadOnly());
        permission.setRequiresApproval(config.isRequiresApproval());
        
        // 生成权限描述
        permission.generatePermissionDescription();
        
        return permission;
    }

    /**
     * 创建空的权限矩阵
     */
    private PermissionMatrixVO createEmptyMatrix(Long userId) {
        PermissionMatrixVO matrixVO = new PermissionMatrixVO(PermissionMatrixVO.MatrixType.USER);
        matrixVO.setUserId(userId);
        matrixVO.setPermissionMatrix(new HashMap<>());
        matrixVO.setAccessibleModules(Collections.emptyList());
        return matrixVO;
    }

    // ========== PermissionChecker接口实现 ==========

    @Override
    public boolean hasPermission(Long userId, String moduleCode, OperationTypeEnum operationType) {
        log.debug("检查用户权限 - 用户ID: {}, 模块: {}, 操作: {}", userId, moduleCode, operationType);
        
        // 根据模块编码获取模块类型
        ModuleTypeEnum moduleType = getModuleTypeByCode(moduleCode);
        if (moduleType == null) {
            log.warn("无效的模块编码: {}", moduleCode);
            return false;
        }
        
        return checkUserPermission(userId, moduleType, operationType);
    }

    @Override
    public DataScopeEnum getDataScope(Long userId, String moduleCode) {
        log.debug("获取用户数据范围 - 用户ID: {}, 模块: {}", userId, moduleCode);
        
        // 根据模块编码获取模块类型
        ModuleTypeEnum moduleType = getModuleTypeByCode(moduleCode);
        if (moduleType == null) {
            log.warn("无效的模块编码: {}", moduleCode);
            return DataScopeEnum.PERSONAL; // 默认返回个人范围
        }
        
        return getUserDataScope(userId, moduleType);
    }

    @Override
    public boolean hasRole(Long userId, String roleCode) {
        log.debug("检查用户角色 - 用户ID: {}, 角色: {}", userId, roleCode);
        
        try {
            RoleEnum userRole = getUserRole(userId);
            if (userRole == null) {
                return false;
            }
            
            RoleEnum targetRole = RoleEnum.getByCode(roleCode);
            if (targetRole == null) {
                log.warn("无效的角色编码: {}", roleCode);
                return false;
            }
            
            // 使用角色层级检查，支持角色继承
            return userRole.hasRole(targetRole);
        } catch (Exception e) {
            log.error("检查用户角色时发生错误", e);
            return false;
        }
    }

    @Override
    public boolean hasPermission(Long userId, String permissionCode) {
        log.debug("检查用户权限码 - 用户ID: {}, 权限码: {}", userId, permissionCode);
        
        // 这里可以根据权限码的格式解析出模块和操作
        // 假设权限码格式为: module:operation (如: user:read, document:create)
        String[] parts = permissionCode.split(":");
        if (parts.length != 2) {
            log.warn("无效的权限码格式: {}, 应为 module:operation", permissionCode);
            return false;
        }
        
        String moduleCode = parts[0];
        String operationCode = parts[1];
        
        ModuleTypeEnum moduleType = getModuleTypeByCode(moduleCode);
        OperationTypeEnum operationType = OperationTypeEnum.getByCode(operationCode);
        
        if (moduleType == null || operationType == null) {
            log.warn("无效的权限码组成部分 - 模块: {}, 操作: {}", moduleCode, operationCode);
            return false;
        }
        
        return checkUserPermission(userId, moduleType, operationType);
    }

    /**
     * 根据模块编码获取模块类型枚举
     */
    private ModuleTypeEnum getModuleTypeByCode(String moduleCode) {
        for (ModuleTypeEnum moduleType : ModuleTypeEnum.values()) {
            if (moduleType.getCode().equals(moduleCode)) {
                return moduleType;
            }
        }
        return null;
    }
}