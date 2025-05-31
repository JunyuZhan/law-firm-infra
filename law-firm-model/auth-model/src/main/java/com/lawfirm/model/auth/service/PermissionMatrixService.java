package com.lawfirm.model.auth.service;

import com.lawfirm.model.auth.dto.permission.PermissionMatrixDTO;
import com.lawfirm.model.auth.vo.PermissionMatrixVO;
import com.lawfirm.model.auth.enums.RoleEnum;
import com.lawfirm.model.auth.enums.ModuleTypeEnum;
import com.lawfirm.model.auth.enums.OperationTypeEnum;
import com.lawfirm.model.auth.enums.DataScopeEnum;

import java.util.List;
import java.util.Map;

/**
 * 权限矩阵服务接口
 * 提供权限矩阵的生成、查询和权限检查功能
 * 将前端权限矩阵逻辑集中到后端处理
 *
 * @author System
 */
public interface PermissionMatrixService {

    /**
     * 获取完整的权限矩阵
     * 包含所有角色对所有模块的权限配置
     *
     * @return 权限矩阵视图对象
     */
    PermissionMatrixVO getFullPermissionMatrix();

    /**
     * 获取指定用户的权限矩阵
     * 根据用户的角色生成个性化权限矩阵
     *
     * @param userId 用户ID
     * @return 用户权限矩阵
     */
    PermissionMatrixVO getUserPermissionMatrix(Long userId);

    /**
     * 获取指定角色的权限矩阵
     *
     * @param roleEnum 角色枚举
     * @return 角色权限矩阵
     */
    PermissionMatrixVO getRolePermissionMatrix(RoleEnum roleEnum);

    /**
     * 检查用户是否有指定模块的操作权限
     *
     * @param userId 用户ID
     * @param moduleType 模块类型
     * @param operationType 操作类型
     * @return 是否有权限
     */
    boolean checkUserPermission(Long userId, ModuleTypeEnum moduleType, OperationTypeEnum operationType);

    /**
     * 检查用户在指定模块的数据访问范围
     *
     * @param userId 用户ID
     * @param moduleType 模块类型
     * @return 数据访问范围
     */
    DataScopeEnum getUserDataScope(Long userId, ModuleTypeEnum moduleType);

    /**
     * 批量检查用户权限
     * 用于前端一次性获取多个模块的权限状态
     *
     * @param userId 用户ID
     * @param permissionRequests 权限检查请求列表
     * @return 权限检查结果映射 (moduleCode:operationType -> hasPermission)
     */
    Map<String, Boolean> batchCheckUserPermissions(Long userId, List<PermissionMatrixDTO> permissionRequests);

    /**
     * 获取用户可访问的模块列表
     *
     * @param userId 用户ID
     * @return 可访问的模块类型列表
     */
    List<ModuleTypeEnum> getUserAccessibleModules(Long userId);

    /**
     * 获取用户在指定模块的可执行操作列表
     *
     * @param userId 用户ID
     * @param moduleType 模块类型
     * @return 可执行的操作类型列表
     */
    List<OperationTypeEnum> getUserModuleOperations(Long userId, ModuleTypeEnum moduleType);

    /**
     * 刷新权限矩阵缓存
     * 当权限配置发生变化时调用
     */
    void refreshPermissionMatrix();

    /**
     * 验证权限矩阵配置的完整性
     * 检查是否所有角色和模块都有对应的权限配置
     *
     * @return 验证结果报告
     */
    Map<String, Object> validatePermissionMatrix();
}