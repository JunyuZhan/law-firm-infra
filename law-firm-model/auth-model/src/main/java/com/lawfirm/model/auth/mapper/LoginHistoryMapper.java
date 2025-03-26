package com.lawfirm.model.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.LoginHistory;
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
    @Select("SELECT * FROM auth_login_history WHERE user_id = #{userId} AND deleted = 0 ORDER BY login_time DESC")
    List<LoginHistory> selectByUserId(Long userId);
    
    /**
     * 根据IP地址查询登录历史
     * 
     * @param ipAddress IP地址
     * @return 登录历史列表
     */
    @Select("SELECT * FROM auth_login_history WHERE ip_address = #{ipAddress} AND deleted = 0 ORDER BY login_time DESC")
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
    @Select("<script>SELECT * FROM auth_login_history " +
            "<where>" +
            "  deleted = 0" +
            "  <if test='userId != null'>AND user_id = #{userId}</if>" +
            "  <if test='loginType != null'>AND login_type = #{loginType}</if>" +
            "  <if test='status != null'>AND status = #{status}</if>" +
            "  <if test='startTime != null'>AND login_time &gt;= #{startTime}</if>" +
            "  <if test='endTime != null'>AND login_time &lt;= #{endTime}</if>" +
            "</where>" +
            "ORDER BY login_time DESC " +
            "<if test='pageNum != null and pageSize != null'>LIMIT #{pageNum}, #{pageSize}</if>" +
            "</script>")
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
    @Select("<script>SELECT COUNT(*) FROM auth_login_history " +
            "<where>" +
            "  deleted = 0" +
            "  <if test='userId != null'>AND user_id = #{userId}</if>" +
            "  <if test='loginType != null'>AND login_type = #{loginType}</if>" +
            "  <if test='status != null'>AND status = #{status}</if>" +
            "  <if test='startTime != null'>AND login_time &gt;= #{startTime}</if>" +
            "  <if test='endTime != null'>AND login_time &lt;= #{endTime}</if>" +
            "</where>" +
            "</script>")
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
    @Select("UPDATE auth_login_history SET deleted = 1 WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);
    
    /**
     * 删除指定日期之前的登录历史
     * 
     * @param date 日期
     * @return 影响行数
     */
    @Select("UPDATE auth_login_history SET deleted = 1 WHERE login_time < #{date}")
    int deleteBeforeDate(Date date);
    
    /**
     * 查询用户最后一次登录记录
     * 
     * @param userId 用户ID
     * @return 登录历史实体
     */
    @Select("SELECT * FROM auth_login_history WHERE user_id = #{userId} AND deleted = 0 ORDER BY login_time DESC LIMIT 1")
    LoginHistory selectLastLoginByUserId(Long userId);
} 