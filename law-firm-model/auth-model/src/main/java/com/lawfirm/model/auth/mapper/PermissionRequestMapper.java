package com.lawfirm.model.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.PermissionRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限申请数据访问接口
 */
@Mapper
public interface PermissionRequestMapper extends BaseMapper<PermissionRequest> {

    /**
     * 查询待审批的权限申请列表
     *
     * @param approverId 审批人ID
     * @return 权限申请列表
     */
    List<PermissionRequest> selectPendingRequests(@Param("approverId") Long approverId);
    
    /**
     * 查询用户的权限申请列表
     *
     * @param userId 用户ID
     * @return 权限申请列表
     */
    List<PermissionRequest> selectUserRequests(@Param("userId") Long userId);
} 