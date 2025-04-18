package com.lawfirm.model.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.auth.dto.user.UserCreateDTO;
import com.lawfirm.model.auth.dto.user.UserUpdateDTO;
import com.lawfirm.model.auth.dto.user.UserQueryDTO;
import com.lawfirm.model.auth.entity.User;
import com.lawfirm.model.auth.vo.UserVO;
import com.lawfirm.model.auth.vo.UserInfoVO;
import com.lawfirm.model.base.service.BaseService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 用户服务接口 - 仅包含认证授权相关的方法
 */
public interface UserService extends BaseService<User> {
    
    /**
     * 创建用户
     *
     * @param createDTO 用户创建DTO
     * @return 用户ID
     */
    Long createUser(UserCreateDTO createDTO);
    
    /**
     * 更新用户
     *
     * @param id 用户ID
     * @param updateDTO 用户更新DTO
     */
    void updateUser(Long id, UserUpdateDTO updateDTO);
    
    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);
    
    /**
     * 批量删除用户
     *
     * @param ids 用户ID列表
     */
    void deleteUsers(List<Long> ids);
    
    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return 用户视图对象
     */
    UserVO getUserById(Long id);
    
    /**
     * 分页查询用户
     *
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    Page<UserVO> pageUsers(UserQueryDTO queryDTO);
    
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    User getByUsername(String username);
    
    /**
     * 修改密码
     *
     * @param id 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updatePassword(Long id, String oldPassword, String newPassword);
    
    /**
     * 重置密码
     *
     * @param id 用户ID
     * @return 新密码
     */
    String resetPassword(Long id);
    
    /**
     * 更新用户状态
     *
     * @param id 用户ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);
    
    /**
     * 获取用户权限列表
     *
     * @param id 用户ID
     * @return 权限标识列表
     */
    List<String> getUserPermissions(Long id);
    
    /**
     * 根据用户名加载用户详情（用于认证）
     *
     * @param username 用户名
     * @return 用户详情
     */
    UserDetails loadUserByUsername(String username);
    
    /**
     * 根据邮箱加载用户详情（用于认证）
     *
     * @param email 邮箱
     * @return 用户详情
     */
    UserDetails loadUserByEmail(String email);
    
    /**
     * 根据手机号加载用户详情（用于认证）
     *
     * @param mobile 手机号
     * @return 用户详情
     */
    UserDetails loadUserByMobile(String mobile);
    
    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户实体
     */
    User getByEmail(String email);
    
    /**
     * 根据手机号查询用户
     *
     * @param mobile 手机号
     * @return 用户实体
     */
    User getByMobile(String mobile);
    
    /**
     * 分配用户角色
     */
    void assignRoles(Long userId, List<Long> roleIds);
    
    /**
     * 获取用户角色ID列表
     */
    List<Long> getUserRoleIds(Long userId);
    
    /**
     * 获取用户信息
     */
    UserInfoVO getUserInfo(Long userId);
    
    /**
     * 获取用户详细信息（适配vue-vben-admin）
     * 包含更多字段，如realName, avatar, desc等
     * 
     * @param userId 用户ID
     * @return 详细的用户信息
     */
    UserInfoVO getUserDetailInfo(Long userId);
} 