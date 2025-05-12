package com.lawfirm.model.client.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;
import com.lawfirm.model.client.constant.ClientSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 客户跟进记录Mapper接口
 */
@Mapper
public interface FollowUpMapper extends BaseMapper<ClientFollowUp> {
    
    /**
     * 获取客户的跟进记录
     *
     * @param clientId 客户ID
     * @return 跟进记录列表
     */
    @Select(ClientSqlConstants.FollowUp.SELECT_BY_CLIENT_ID)
    List<ClientFollowUp> selectByClientId(@Param("clientId") Long clientId);
    
    /**
     * 获取指定时间范围内的跟进记录
     *
     * @param clientId 客户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 跟进记录列表
     */
    @Select(ClientSqlConstants.FollowUp.SELECT_BY_TIME_RANGE)
    List<ClientFollowUp> selectByTimeRange(
            @Param("clientId") Long clientId, 
            @Param("startTime") Date startTime, 
            @Param("endTime") Date endTime);
    
    /**
     * 获取指定类型的跟进记录
     *
     * @param clientId 客户ID
     * @param followUpType 跟进类型
     * @return 跟进记录列表
     */
    @Select(ClientSqlConstants.FollowUp.SELECT_BY_TYPE)
    List<ClientFollowUp> selectByType(
            @Param("clientId") Long clientId, 
            @Param("followUpType") String followUpType);
    
    /**
     * 获取用户负责的客户跟进记录
     *
     * @param userId 用户ID
     * @return 跟进记录列表
     */
    @Select(ClientSqlConstants.FollowUp.SELECT_BY_USER_ID)
    List<ClientFollowUp> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 查询需要提醒的跟进记录
     *
     * @param remindTime 提醒时间
     * @return 跟进记录列表
     */
    @Select(ClientSqlConstants.FollowUp.SELECT_REMINDERS)
    List<ClientFollowUp> selectReminders(@Param("remindTime") Date remindTime);
    
    /**
     * 根据条件查询跟进记录列表
     * 显式定义selectList方法解决"Invalid bound statement"问题
     *
     * @param queryWrapper 查询条件
     * @return 跟进记录列表
     */
    @Select("SELECT * FROM client_follow_up ${ew.customSqlSegment}")
    List<ClientFollowUp> selectList(@Param(Constants.WRAPPER) Wrapper<ClientFollowUp> queryWrapper);
} 