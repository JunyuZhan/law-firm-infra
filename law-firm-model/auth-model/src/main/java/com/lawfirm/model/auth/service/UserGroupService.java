package com.lawfirm.model.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.auth.dto.usergroup.UserGroupCreateDTO;
import com.lawfirm.model.auth.dto.usergroup.UserGroupQueryDTO;
import com.lawfirm.model.auth.dto.usergroup.UserGroupUpdateDTO;
import com.lawfirm.model.auth.entity.UserGroup;
import com.lawfirm.model.auth.vo.UserGroupVO;
import com.lawfirm.model.base.service.BaseService;

import java.util.List;

/**
 * 用户组服务接口
 */
public interface UserGroupService extends BaseService<UserGroup> {
    
    /**
     * 创建用户组
     *
     * @param createDTO 创建参数
     * @return 新创建的用户组ID
     */
    Long createUserGroup(UserGroupCreateDTO createDTO);
    
    /**
     * 更新用户组
     *
     * @param id 用户组ID
     * @param updateDTO 更新参数
     */
    void updateUserGroup(Long id, UserGroupUpdateDTO updateDTO);
    
    /**
     * 删除用户组
     *
     * @param id 用户组ID
     */
    void deleteUserGroup(Long id);
    
    /**
     * 批量删除用户组
     *
     * @param ids 用户组ID列表
     */
    void deleteUserGroups(List<Long> ids);
    
    /**
     * 根据ID获取用户组详情
     *
     * @param id 用户组ID
     * @return 用户组详情
     */
    UserGroupVO getUserGroupById(Long id);
    
    /**
     * 分页查询用户组
     *
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    Page<UserGroupVO> pageUserGroups(UserGroupQueryDTO queryDTO);
    
    /**
     * 获取所有用户组
     *
     * @return 用户组列表
     */
    List<UserGroupVO> listAllUserGroups();
    
    /**
     * 获取用户组树结构
     *
     * @return 树形结构的用户组列表
     */
    List<UserGroupVO> getUserGroupTree();
    
    /**
     * 根据编码获取用户组
     *
     * @param code 用户组编码
     * @return 用户组实体
     */
    UserGroup getByCode(String code);
    
    /**
     * 更新用户组状态
     *
     * @param id 用户组ID
     * @param status 状态值
     */
    void updateStatus(Long id, Integer status);
    
    /**
     * 添加用户到用户组
     *
     * @param userGroupId 用户组ID
     * @param userId 用户ID
     */
    void addUserToGroup(Long userGroupId, Long userId);
    
    /**
     * 批量添加用户到用户组
     *
     * @param userGroupId 用户组ID
     * @param userIds 用户ID列表
     */
    void addUsersToGroup(Long userGroupId, List<Long> userIds);
    
    /**
     * 从用户组移除用户
     *
     * @param userGroupId 用户组ID
     * @param userId 用户ID
     */
    void removeUserFromGroup(Long userGroupId, Long userId);
    
    /**
     * 批量从用户组移除用户
     *
     * @param userGroupId 用户组ID
     * @param userIds 用户ID列表
     */
    void removeUsersFromGroup(Long userGroupId, List<Long> userIds);
    
    /**
     * 获取用户所属的用户组
     *
     * @param userId 用户ID
     * @return 用户组列表
     */
    List<UserGroupVO> listUserGroupsByUserId(Long userId);
} 