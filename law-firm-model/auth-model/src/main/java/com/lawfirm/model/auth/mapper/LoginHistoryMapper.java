package com.lawfirm.model.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.LoginHistory;
import com.lawfirm.model.auth.constant.AuthSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 登录历史数据访问接口
 * 
 * @author JunyuZhan
 */
@Mapper
public interface LoginHistoryMapper extends BaseMapper<LoginHistory> {
    
    /**
     * 根据用户ID查询登录历史
     * 
     * @param userId 用户ID
     * @return 登录历史列表
     */
    @Select(AuthSqlConstants.LoginHistory.SELECT_BY_USER_ID)
    List<LoginHistory> selectByUserId(Long userId);
    
    /**
     * 根据IP地址查询登录历史
     * 
     * @param ipAddress IP地址
     * @return 登录历史列表
     */
    @Select(AuthSqlConstants.LoginHistory.SELECT_BY_IP_ADDRESS)
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
    @Select(AuthSqlConstants.LoginHistory.SELECT_PAGE)
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
    @Select(AuthSqlConstants.LoginHistory.SELECT_COUNT)
    int selectCount(@Param("userId") Long userId,
                   @Param("loginType") Integer loginType,
                   @Param("status") Integer status,
                   @Param("startTime") Date startTime,
                   @Param("endTime") Date endTime);
    
    /**
     * 根据用户ID删除登录历史
     * 
     * @param userId 用户ID
     * @return 影响行数
     */
    @Select(AuthSqlConstants.LoginHistory.DELETE_BY_USER_ID)
    int deleteByUserId(Long userId);
    
    /**
     * 删除指定日期之前的登录历史
     * 
     * @param date 日期
     * @return 影响行数
     */
    @Select(AuthSqlConstants.LoginHistory.DELETE_BEFORE_DATE)
    int deleteBeforeDate(Date date);
    
    /**
     * 查询用户最后一次登录记录
     * 
     * @param userId 用户ID
     * @return 登录历史实体
     */
    @Select(AuthSqlConstants.LoginHistory.SELECT_LAST_LOGIN_BY_USER_ID)
    LoginHistory selectLastLoginByUserId(Long userId);
} 