package com.lawfirm.model.auth.service;

import com.lawfirm.model.auth.dto.permission.PermissionRequestDTO;

import java.util.List;

/**
 * 业务权限服务接口
 * 提供跨模块的业务对象权限检查功能
 */
public interface BusinessPermissionService {
    
    /**
     * 检查用户是否有业务对象的操作权限
     *
     * @param userId 用户ID
     * @param businessType 业务类型(CASE,DOCUMENT,CONTRACT等)
     * @param businessId 业务ID
     * @param operation 操作类型(CREATE,READ,UPDATE,DELETE,APPROVE)
     * @return 是否有权限
     */
    boolean checkPermission(Long userId, String businessType, Long businessId, String operation);
    
    /**
     * 申请临时权限
     *
     * @param userId 用户ID
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @param permissionCode 权限编码
     * @param reason 申请理由
     * @return 申请ID
     */
    Long requestTemporaryPermission(Long userId, String businessType, Long businessId, 
                                   String permissionCode, String reason);
    
    /**
     * 批准临时权限申请
     *
     * @param approverId 审批人ID
     * @param requestId 申请ID
     * @param approved 是否批准
     * @param remark 备注
     * @return 是否成功
     */
    boolean approvePermissionRequest(Long approverId, Long requestId, boolean approved, String remark);
    
    /**
     * 检查用户能否获取指定数据范围的数据
     *
     * @param userId 用户ID
     * @param dataScope 数据范围
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 是否有权限
     */
    boolean checkDataScope(Long userId, int dataScope, String businessType, Long businessId);
    
    /**
     * 获取用户在指定业务对象上的最大权限范围
     *
     * @param userId 用户ID
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 权限范围
     */
    int getUserDataScope(Long userId, String businessType, Long businessId);
    
    /**
     * 检查用户是否拥有指定团队的特定资源类型权限
     *
     * @param userId 用户ID
     * @param teamId 团队ID
     * @param resourceType 资源类型
     * @return 是否有权限
     */
    boolean checkUserHasTeamPermission(Long userId, Long teamId, String resourceType);
    
    /**
     * 检查用户是否拥有指定团队的多个资源类型权限中的至少一个
     *
     * @param userId 用户ID
     * @param teamId 团队ID
     * @param resourceTypes 资源类型列表
     * @return 是否有权限
     */
    boolean checkUserHasTeamPermission(Long userId, Long teamId, List<String> resourceTypes);
} 