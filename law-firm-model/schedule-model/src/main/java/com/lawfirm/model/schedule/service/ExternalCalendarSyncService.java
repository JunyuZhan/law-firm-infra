package com.lawfirm.model.schedule.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 外部日历同步服务接口
 */
public interface ExternalCalendarSyncService {
    
    /**
     * 导入iCalendar格式的日历数据
     * 
     * @param userId 用户ID
     * @param inputStream iCalendar数据输入流
     * @param override 是否覆盖现有日程
     * @return 成功导入的日程数
     */
    int importICalendar(Long userId, InputStream inputStream, boolean override);
    
    /**
     * 导出用户日程为iCalendar格式
     * 
     * @param userId 用户ID
     * @param outputStream 输出流
     * @param startTimeFilter 开始时间过滤条件
     * @param endTimeFilter 结束时间过滤条件
     * @return 成功导出的日程数
     */
    int exportToICalendar(Long userId, OutputStream outputStream, String startTimeFilter, String endTimeFilter);
    
    /**
     * 同步Google日历数据
     * 
     * @param userId 用户ID
     * @param accessToken 访问令牌
     * @param twoWaySync 是否双向同步
     * @return 同步的日程数
     */
    int syncWithGoogleCalendar(Long userId, String accessToken, boolean twoWaySync);
    
    /**
     * 同步Outlook日历数据
     * 
     * @param userId 用户ID
     * @param accessToken 访问令牌
     * @param twoWaySync 是否双向同步
     * @return 同步的日程数
     */
    int syncWithOutlookCalendar(Long userId, String accessToken, boolean twoWaySync);
    
    /**
     * 获取用户已连接的外部日历账号
     * 
     * @param userId 用户ID
     * @return 外部日历账号列表
     */
    List<String> getConnectedCalendarAccounts(Long userId);
    
    /**
     * 添加外部日历账号
     * 
     * @param userId 用户ID
     * @param type 日历类型 (Google, Outlook等)
     * @param accessToken 访问令牌
     * @param refreshToken 刷新令牌
     * @param expireTime 过期时间
     * @return 是否成功
     */
    boolean addCalendarAccount(Long userId, String type, String accessToken, String refreshToken, long expireTime);
    
    /**
     * 移除外部日历账号
     * 
     * @param userId 用户ID
     * @param type 日历类型 (Google, Outlook等)
     * @return 是否成功
     */
    boolean removeCalendarAccount(Long userId, String type);
    
    /**
     * 设置日历同步配置
     * 
     * @param userId 用户ID
     * @param type 日历类型
     * @param syncDirection 同步方向 (1:导入, 2:导出, 3:双向)
     * @param syncInterval 同步间隔(分钟)
     * @param syncCategories 同步的日程类别
     * @return 是否成功
     */
    boolean setCalendarSyncConfig(Long userId, String type, int syncDirection, int syncInterval, List<Integer> syncCategories);
    
    /**
     * 获取日历同步配置
     * 
     * @param userId 用户ID
     * @param type 日历类型
     * @return 同步配置
     */
    Object getCalendarSyncConfig(Long userId, String type);
    
    /**
     * 立即同步日历
     * 
     * @param userId 用户ID
     * @param type 日历类型
     * @return 同步结果
     */
    Object syncCalendarNow(Long userId, String type);
    
    /**
     * 获取日历同步历史
     * 
     * @param userId 用户ID
     * @param type 日历类型
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 同步历史记录
     */
    Object getSyncHistory(Long userId, String type, int pageNum, int pageSize);
} 