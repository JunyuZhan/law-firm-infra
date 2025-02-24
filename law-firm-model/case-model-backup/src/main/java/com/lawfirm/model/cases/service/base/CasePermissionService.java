package com.lawfirm.model.cases.service.base;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.cases.dto.base.CasePermissionDTO;
import com.lawfirm.model.cases.vo.base.CaseAccessLogVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件权限服务接口
 */
public interface CasePermissionService {

    /**
     * 设置案件权限
     *
     * @param caseId 案件ID
     * @param permissionDTO 权限设置
     * @return 是否成功
     */
    Boolean setCasePermission(Long caseId, CasePermissionDTO permissionDTO);

    /**
     * 检查用户对案件的权限
     *
     * @param userId 用户ID
     * @param caseId 案件ID
     * @param operationType 操作类型
     * @return 是否有权限
     */
    Boolean checkPermission(Long userId, Long caseId, String operationType);

    /**
     * 授予临时权限
     *
     * @param userId 用户ID
     * @param caseId 案件ID
     * @param permissions 权限列表
     * @param expireTime 过期时间
     * @return 是否成功
     */
    Boolean grantTemporaryPermission(Long userId, Long caseId, List<String> permissions, LocalDateTime expireTime);

    /**
     * 撤销权限
     *
     * @param userId 用户ID
     * @param caseId 案件ID
     * @param permissions 权限列表
     * @return 是否成功
     */
    Boolean revokePermission(Long userId, Long caseId, List<String> permissions);

    /**
     * 获取案件的权限设置
     *
     * @param caseId 案件ID
     * @return 权限设置
     */
    CasePermissionDTO getCasePermission(Long caseId);

    /**
     * 获取用户在案件上的权限
     *
     * @param userId 用户ID
     * @param caseId 案件ID
     * @return 权限列表
     */
    List<String> getUserPermissions(Long userId, Long caseId);

    /**
     * 批量设置案件权限
     *
     * @param caseIds 案件ID列表
     * @param permissionDTO 权限设置
     * @return 是否成功
     */
    Boolean batchSetPermissions(List<Long> caseIds, CasePermissionDTO permissionDTO);

    /**
     * 获取案件访问日志
     *
     * @param caseId 案件ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 访问日志
     */
    PageDTO<CaseAccessLogVO> getAccessLogs(Long caseId, LocalDateTime startTime, 
            LocalDateTime endTime, Integer pageNum, Integer pageSize);

    /**
     * 获取异常访问记录
     *
     * @param caseId 案件ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 异常访问记录
     */
    List<CaseAccessLogVO> getAbnormalAccess(Long caseId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 继承案件权限
     *
     * @param sourceCaseId 源案件ID
     * @param targetCaseId 目标案件ID
     * @return 是否成功
     */
    Boolean inheritPermissions(Long sourceCaseId, Long targetCaseId);
} 