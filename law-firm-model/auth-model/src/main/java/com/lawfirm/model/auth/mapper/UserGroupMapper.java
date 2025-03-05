package com.lawfirm.model.auth.mapper;

import com.lawfirm.model.auth.entity.UserGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户组数据访问接口
 * 
 * @author lawfirm
 */
public interface UserGroupMapper {
    
    /**
     * 根据ID查询用户组
     * 
     * @param id 用户组ID
     * @return 用户组实体
     */
    UserGroup selectById(Long id);
    
    /**
     * 根据用户组编码查询用户组
     * 
     * @param code 用户组编码
     * @return 用户组实体
     */
    UserGroup selectByCode(String code);
    
    /**
     * 查询所有用户组
     * 
     * @return 用户组列表
     */
    List<UserGroup> selectAll();
    
    /**
     * 分页查询用户组列表
     * 
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param name 用户组名称（可选）
     * @return 用户组列表
     */
    List<UserGroup> selectPage(@Param("pageNum") Integer pageNum, 
                             @Param("pageSize") Integer pageSize,
                             @Param("name") String name);
    
    /**
     * 查询用户组总数
     * 
     * @param name 用户组名称（可选）
     * @return 用户组总数
     */
    int selectCount(@Param("name") String name);
    
    /**
     * 新增用户组
     * 
     * @param userGroup 用户组实体
     * @return 影响行数
     */
    int insert(UserGroup userGroup);
    
    /**
     * 更新用户组
     * 
     * @param userGroup 用户组实体
     * @return 影响行数
     */
    int update(UserGroup userGroup);
    
    /**
     * 删除用户组
     * 
     * @param id 用户组ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 批量删除用户组
     * 
     * @param ids 用户组ID数组
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);
    
    /**
     * 查询用户所属的用户组
     * 
     * @param userId 用户ID
     * @return 用户组列表
     */
    List<UserGroup> selectByUserId(Long userId);
    
    /**
     * 添加用户到用户组
     * 
     * @param userGroupId 用户组ID
     * @param userId 用户ID
     * @return 影响行数
     */
    int addUserToGroup(@Param("userGroupId") Long userGroupId, @Param("userId") Long userId);
    
    /**
     * 从用户组中移除用户
     * 
     * @param userGroupId 用户组ID
     * @param userId 用户ID
     * @return 影响行数
     */
    int removeUserFromGroup(@Param("userGroupId") Long userGroupId, @Param("userId") Long userId);
} 