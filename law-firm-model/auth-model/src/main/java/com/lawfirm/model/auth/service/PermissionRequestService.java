package com.lawfirm.model.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.auth.dto.permission.PermissionApprovalDTO;
import com.lawfirm.model.auth.dto.permission.PermissionRequestDTO;
import com.lawfirm.model.auth.entity.PermissionRequest;
import com.lawfirm.model.auth.vo.PermissionRequestVO;

import java.util.List;

/**
 * 权限申请服务接口
 */
public interface PermissionRequestService extends IService<PermissionRequest> {
    
    /**
     * 创建权限申请
     *
     * @param userId 用户ID
     * @param dto 申请参数
     * @return 申请ID
     */
    Long createRequest(Long userId, PermissionRequestDTO dto);
    
    /**
     * 审批权限申请
     *
     * @param approverId 审批人ID
     * @param dto 审批参数
     * @return 是否成功
     */
    boolean approveRequest(Long approverId, PermissionApprovalDTO dto);
    
    /**
     * 获取待审批的权限申请列表
     *
     * @param approverId 审批人ID
     * @return 申请列表
     */
    List<PermissionRequestVO> getPendingRequests(Long approverId);
    
    /**
     * 获取申请详情
     *
     * @param requestId 申请ID
     * @return 申请详情
     */
    PermissionRequestVO getRequestDetail(Long requestId);
    
    /**
     * 获取用户的申请列表
     *
     * @param userId 用户ID
     * @return 申请列表
     */
    List<PermissionRequestVO> getUserRequests(Long userId);
    
    /**
     * 检查用户是否有临时权限
     *
     * @param userId 用户ID
     * @param permissionCode 权限编码
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 是否有权限
     */
    boolean hasTemporaryPermission(Long userId, String permissionCode, String businessType, Long businessId);
    
    /**
     * 撤销申请
     *
     * @param userId 用户ID
     * @param requestId 申请ID
     * @return 是否成功
     */
    boolean cancelRequest(Long userId, Long requestId);
} 