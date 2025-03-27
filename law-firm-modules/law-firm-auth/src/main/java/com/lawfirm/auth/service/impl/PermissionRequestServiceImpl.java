package com.lawfirm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.auth.dto.permission.PermissionApprovalDTO;
import com.lawfirm.model.auth.dto.permission.PermissionRequestDTO;
import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.entity.PermissionRequest;
import com.lawfirm.model.auth.mapper.PermissionMapper;
import com.lawfirm.model.auth.mapper.PermissionRequestMapper;
import com.lawfirm.model.auth.service.PermissionRequestService;
import com.lawfirm.model.auth.vo.PermissionRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限申请服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionRequestServiceImpl extends ServiceImpl<PermissionRequestMapper, PermissionRequest> implements PermissionRequestService {

    private final PermissionMapper permissionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRequest(Long userId, PermissionRequestDTO dto) {
        // 检查权限是否存在
        Permission permission = getPermissionByCode(dto.getPermissionCode());
        if (permission == null) {
            log.warn("权限不存在: {}", dto.getPermissionCode());
            throw new IllegalArgumentException("权限不存在: " + dto.getPermissionCode());
        }
        
        // 创建权限申请
        PermissionRequest request = new PermissionRequest();
        request.setUserId(userId);
        request.setBusinessType(dto.getBusinessType());
        request.setBusinessId(dto.getBusinessId());
        request.setPermissionCode(dto.getPermissionCode());
        request.setReason(dto.getReason());
        request.setStatus(PermissionRequest.STATUS_PENDING);
        request.setCreateTime(LocalDateTime.now());
        request.setUpdateTime(LocalDateTime.now());
        request.setExpireTime(dto.getExpireTime());
        
        save(request);
        log.info("创建权限申请: {}", request.getId());
        return request.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean approveRequest(Long approverId, PermissionApprovalDTO dto) {
        // 获取申请
        PermissionRequest request = getById(dto.getRequestId());
        if (request == null) {
            log.warn("权限申请不存在: {}", dto.getRequestId());
            return false;
        }
        
        // 检查状态
        if (request.getStatus() != PermissionRequest.STATUS_PENDING) {
            log.warn("权限申请已处理: {}", dto.getRequestId());
            return false;
        }
        
        // 更新申请状态
        request.setApproverId(approverId);
        request.setApproveTime(LocalDateTime.now());
        request.setApproveRemark(dto.getRemark());
        request.setStatus(dto.getApproved() ? PermissionRequest.STATUS_APPROVED : PermissionRequest.STATUS_REJECTED);
        request.setUpdateTime(LocalDateTime.now());
        
        boolean result = updateById(request);
        log.info("审批权限申请: {}, 结果: {}", dto.getRequestId(), result);
        return result;
    }

    @Override
    public List<PermissionRequestVO> getPendingRequests(Long approverId) {
        // 查询待审批的申请
        List<PermissionRequest> requests = baseMapper.selectPendingRequests(approverId);
        if (CollectionUtils.isEmpty(requests)) {
            return Collections.emptyList();
        }
        
        // 转换为VO
        return requests.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public PermissionRequestVO getRequestDetail(Long requestId) {
        PermissionRequest request = getById(requestId);
        if (request == null) {
            return null;
        }
        
        return convertToVO(request);
    }

    @Override
    public List<PermissionRequestVO> getUserRequests(Long userId) {
        // 查询用户的申请
        List<PermissionRequest> requests = baseMapper.selectUserRequests(userId);
        if (CollectionUtils.isEmpty(requests)) {
            return Collections.emptyList();
        }
        
        // 转换为VO
        return requests.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasTemporaryPermission(Long userId, String permissionCode, String businessType, Long businessId) {
        // 查询有效的权限申请
        LambdaQueryWrapper<PermissionRequest> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PermissionRequest::getUserId, userId)
                   .eq(PermissionRequest::getPermissionCode, permissionCode)
                   .eq(PermissionRequest::getStatus, PermissionRequest.STATUS_APPROVED);
        
        // 如果指定了业务，则加上业务条件
        if (businessType != null) {
            queryWrapper.eq(PermissionRequest::getBusinessType, businessType);
            
            if (businessId != null) {
                queryWrapper.eq(PermissionRequest::getBusinessId, businessId);
            }
        }
        
        // 检查是否过期
        LocalDateTime now = LocalDateTime.now();
        queryWrapper.and(wrapper -> wrapper.isNull(PermissionRequest::getExpireTime)
                                          .or()
                                          .gt(PermissionRequest::getExpireTime, now));
        
        return count(queryWrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelRequest(Long userId, Long requestId) {
        // 获取申请
        PermissionRequest request = getById(requestId);
        if (request == null) {
            log.warn("权限申请不存在: {}", requestId);
            return false;
        }
        
        // 检查是否是申请人
        if (!userId.equals(request.getUserId())) {
            log.warn("无权撤销他人的申请: {}", requestId);
            return false;
        }
        
        // 检查状态
        if (request.getStatus() != PermissionRequest.STATUS_PENDING) {
            log.warn("权限申请已处理，无法撤销: {}", requestId);
            return false;
        }
        
        // 删除申请
        boolean result = removeById(requestId);
        log.info("撤销权限申请: {}, 结果: {}", requestId, result);
        return result;
    }
    
    /**
     * 根据编码获取权限
     */
    private Permission getPermissionByCode(String code) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Permission::getCode, code);
        return permissionMapper.selectOne(queryWrapper);
    }
    
    /**
     * 将申请实体转换为VO
     */
    private PermissionRequestVO convertToVO(PermissionRequest request) {
        if (request == null) {
            return null;
        }
        
        PermissionRequestVO vo = new PermissionRequestVO();
        BeanUtils.copyProperties(request, vo);
        
        // 设置权限名称
        if (request.getPermissionCode() != null) {
            Permission permission = getPermissionByCode(request.getPermissionCode());
            if (permission != null) {
                vo.setPermissionName(permission.getName());
            }
        }
        
        // 设置状态名称
        if (request.getStatus() != null) {
            switch (request.getStatus()) {
                case PermissionRequest.STATUS_PENDING:
                    vo.setStatusName("待审批");
                    break;
                case PermissionRequest.STATUS_APPROVED:
                    vo.setStatusName("已批准");
                    break;
                case PermissionRequest.STATUS_REJECTED:
                    vo.setStatusName("已拒绝");
                    break;
                default:
                    vo.setStatusName("未知");
                    break;
            }
        }
        
        return vo;
    }
} 