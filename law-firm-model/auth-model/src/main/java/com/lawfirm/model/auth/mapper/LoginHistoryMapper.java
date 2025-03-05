package com.lawfirm.model.auth.mapper;

import com.lawfirm.model.auth.entity.LoginHistory;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 登录历史数据访问接口
 * 
 * @author lawfirm
 */
public interface LoginHistoryMapper {
    
    /**
     * 根据ID查询登录历史
     * 
     * @param id 登录历史ID
     * @return 登录历史实体
     */
    LoginHistory selectById(Long id);
    
    /**
     * 根据用户ID查询登录历史
     * 
     * @param userId 用户ID
     * @return 登录历史列表
     */
    List<LoginHistory> selectByUserId(Long userId);
    
    /**
     * 根据IP地址查询登录历史
     * 
     * @param ipAddress IP地址
     * @return 登录历史列表
     */
    List<LoginHistory> selectByIpAddress(String ipAddress);
    
    /**
     * 分页查询登录历史
     * 
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param userId 用户ID（可选）
     * @param loginType 登录类型（可选）
     * @param status 状态（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return 登录历史列表
     */
    List<LoginHistory> selectPage(@Param("pageNum") Integer pageNum, 
                                @Param("pageSize") Integer pageSize,
                                @Param("userId") Long userId,
                                @Param("loginType") Integer loginType,
                                @Param("status") Integer status,
                                @Param("startTime") Date startTime,
                                @Param("endTime") Date endTime);
    
    /**
     * 查询登录历史总数
     * 
     * @param userId 用户ID（可选）
     * @param loginType 登录类型（可选）
     * @param status 状态（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return 登录历史总数
     */
    int selectCount(@Param("userId") Long userId,
                   @Param("loginType") Integer loginType,
                   @Param("status") Integer status,
                   @Param("startTime") Date startTime,
                   @Param("endTime") Date endTime);
    
    /**
     * 新增登录历史
     * 
     * @param loginHistory 登录历史实体
     * @return 影响行数
     */
    int insert(LoginHistory loginHistory);
    
    /**
     * 更新登录历史
     * 
     * @param loginHistory 登录历史实体
     * @return 影响行数
     */
    int update(LoginHistory loginHistory);
    
    /**
     * 删除登录历史
     * 
     * @param id 登录历史ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 根据用户ID删除登录历史
     * 
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteByUserId(Long userId);
    
    /**
     * 删除指定日期之前的登录历史
     * 
     * @param date 日期
     * @return 影响行数
     */
    int deleteBeforeDate(Date date);
    
    /**
     * 查询用户最后一次登录记录
     * 
     * @param userId 用户ID
     * @return 登录历史实体
     */
    LoginHistory selectLastLoginByUserId(Long userId);
} 