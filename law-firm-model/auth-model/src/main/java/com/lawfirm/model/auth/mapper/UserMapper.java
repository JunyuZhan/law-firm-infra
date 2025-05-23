package com.lawfirm.model.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.User;
import com.lawfirm.model.auth.constant.AuthSqlConstants;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户数据访问接口
 * 
 * @author JunyuZhan
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户实体
     */
    @Select(AuthSqlConstants.User.SELECT_BY_USERNAME)
    User selectByUsername(String username);
    
    /**
     * 根据手机号查询用户
     * 
     * @param phone 手机号
     * @return 用户实体
     */
    @Select(AuthSqlConstants.User.SELECT_BY_PHONE)
    User selectByPhone(String phone);
    
    /**
     * 根据邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户实体
     */
    @Select(AuthSqlConstants.User.SELECT_BY_EMAIL)
    User selectByEmail(String email);
    
    /**
     * 根据用户类型查询用户列表
     * 
     * @param userType 用户类型
     * @return 用户列表
     */
    List<User> selectByUserType(Integer userType);
    
    /**
     * 根据职位ID查询用户列表
     * 
     * @param positionId 职位ID
     * @return 用户列表
     */
    List<User> selectByPositionId(Long positionId);
    
    /**
     * 根据部门ID查询用户列表
     * 
     * @param departmentId 部门ID
     * @return 用户列表
     */
    List<User> selectByDepartmentId(Long departmentId);
    
    /**
     * 分页查询用户列表
     * 
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param username 用户名（可选）
     * @param status 状态（可选）
     * @param userType 用户类型（可选）
     * @param departmentId 部门ID（可选）
     * @return 用户列表
     */
    List<User> selectPage(@Param("pageNum") Integer pageNum, 
                         @Param("pageSize") Integer pageSize,
                         @Param("username") String username,
                         @Param("status") Integer status,
                         @Param("userType") Integer userType,
                         @Param("departmentId") Long departmentId);
    
    /**
     * 查询用户总数
     * 
     * @param username 用户名（可选）
     * @param status 状态（可选）
     * @param userType 用户类型（可选）
     * @param departmentId 部门ID（可选）
     * @return 用户总数
     */
    int selectCount(@Param("username") String username,
                   @Param("status") Integer status,
                   @Param("userType") Integer userType,
                   @Param("departmentId") Long departmentId);
    
    /**
     * 更新用户状态
     * 
     * @param id 用户ID
     * @param status 状态
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 更新用户密码
     * 
     * @param id 用户ID
     * @param password 加密后的密码
     * @return 影响行数
     */
    int updatePassword(@Param("id") Long id, @Param("password") String password);
    
    /**
     * 更新用户账号过期时间
     * 
     * @param id 用户ID
     * @param expireTime 过期时间
     * @return 影响行数
     */
    int updateAccountExpireTime(@Param("id") Long id, @Param("expireTime") String expireTime);
    
    /**
     * 更新用户密码过期时间
     * 
     * @param id 用户ID
     * @param expireTime 过期时间
     * @return 影响行数
     */
    int updatePasswordExpireTime(@Param("id") Long id, @Param("expireTime") String expireTime);
    
    /**
     * 查询用户所有角色ID
     * 
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> selectRoleIdsByUserId(Long userId);
    
    /**
     * 查询用户所有权限
     * 
     * @param userId 用户ID
     * @return 权限编码列表
     */
    List<String> selectUserPermissions(Long userId);
    
    /**
     * 查询用户所有权限
     * 
     * @param userId 用户ID
     * @return 权限编码列表
     */
    List<String> selectPermissionsByUserId(Long userId);
    
    /**
     * 删除用户角色关联
     */
    @Delete(AuthSqlConstants.User.DELETE_USER_ROLES)
    int deleteUserRoles(@Param("userId") Long userId);
    
    /**
     * 批量插入用户角色关联
     */
    @Insert(AuthSqlConstants.User.INSERT_USER_ROLES)
    int insertUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
    
    /**
     * 查询用户角色ID列表
     */
    @Select(AuthSqlConstants.User.SELECT_USER_ROLE_IDS)
    List<Long> selectUserRoleIds(@Param("userId") Long userId);
}