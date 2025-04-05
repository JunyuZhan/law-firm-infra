package com.lawfirm.schedule.service.impl;

import com.lawfirm.model.schedule.dto.ScheduleDTO;
import com.lawfirm.model.schedule.service.ExternalCalendarSyncService;
import com.lawfirm.model.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 外部日历同步服务实现类
 */
@Service("externalCalendarSyncService")
@RequiredArgsConstructor
@Slf4j
public class ExternalCalendarSyncServiceImpl implements ExternalCalendarSyncService {

    private final ScheduleService scheduleService;
    
    // 模拟用户的日历连接信息
    private final Map<Long, Map<String, Map<String, Object>>> userCalendarAccounts = new HashMap<>();
    // 模拟用户的日历同步配置
    private final Map<Long, Map<String, Map<String, Object>>> userSyncConfigs = new HashMap<>();
    // 模拟用户的同步历史
    private final Map<Long, Map<String, List<Map<String, Object>>>> userSyncHistory = new HashMap<>();
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int importICalendar(Long userId, InputStream inputStream, boolean override) {
        log.info("导入iCalendar数据，用户ID：{}，是否覆盖：{}", userId, override);
        
        // 这里应该实现iCalendar解析逻辑，转换为ScheduleDTO并保存
        // 由于是模拟实现，直接返回导入成功的日程数
        return 5;
    }
    
    @Override
    public int exportToICalendar(Long userId, OutputStream outputStream, String startTimeFilter, String endTimeFilter) {
        log.info("导出iCalendar数据，用户ID：{}，时间范围：{} - {}", userId, startTimeFilter, endTimeFilter);
        
        // 这里应该实现查询用户日程并转换为iCalendar格式写入输出流的逻辑
        // 由于是模拟实现，直接返回导出成功的日程数
        return 10;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncWithGoogleCalendar(Long userId, String accessToken, boolean twoWaySync) {
        log.info("同步Google日历，用户ID：{}，是否双向同步：{}", userId, twoWaySync);
        
        // 这里应该实现Google日历API调用逻辑，同步日程
        // 记录同步历史
        recordSyncHistory(userId, "google", true, "成功同步15个日程");
        
        return 15;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncWithOutlookCalendar(Long userId, String accessToken, boolean twoWaySync) {
        log.info("同步Outlook日历，用户ID：{}，是否双向同步：{}", userId, twoWaySync);
        
        // 这里应该实现Outlook日历API调用逻辑，同步日程
        // 记录同步历史
        recordSyncHistory(userId, "outlook", true, "成功同步12个日程");
        
        return 12;
    }
    
    @Override
    public List<String> getConnectedCalendarAccounts(Long userId) {
        log.info("获取已连接的日历账号，用户ID：{}", userId);
        
        // 从用户日历账号映射中获取
        Map<String, Map<String, Object>> accounts = userCalendarAccounts.get(userId);
        if (accounts == null) {
            return Collections.emptyList();
        }
        
        return new ArrayList<>(accounts.keySet());
    }
    
    @Override
    public boolean addCalendarAccount(Long userId, String type, String accessToken, String refreshToken, long expireTime) {
        log.info("添加日历账号，用户ID：{}，类型：{}", userId, type);
        
        // 获取或创建用户的日历账号映射
        Map<String, Map<String, Object>> accounts = userCalendarAccounts.computeIfAbsent(userId, k -> new HashMap<>());
        
        // 创建账号信息
        Map<String, Object> accountInfo = new HashMap<>();
        accountInfo.put("accessToken", accessToken);
        accountInfo.put("refreshToken", refreshToken);
        accountInfo.put("expireTime", expireTime);
        accountInfo.put("addTime", System.currentTimeMillis());
        
        // 添加账号
        accounts.put(type, accountInfo);
        
        return true;
    }
    
    @Override
    public boolean removeCalendarAccount(Long userId, String type) {
        log.info("移除日历账号，用户ID：{}，类型：{}", userId, type);
        
        // 获取用户的日历账号映射
        Map<String, Map<String, Object>> accounts = userCalendarAccounts.get(userId);
        if (accounts == null) {
            return false;
        }
        
        // 移除账号
        accounts.remove(type);
        
        return true;
    }
    
    @Override
    public boolean setCalendarSyncConfig(Long userId, String type, int syncDirection, int syncInterval, List<Integer> syncCategories) {
        log.info("设置日历同步配置，用户ID：{}，类型：{}，方向：{}，间隔：{}分钟", userId, type, syncDirection, syncInterval);
        
        // 获取或创建用户的同步配置映射
        Map<String, Map<String, Object>> configs = userSyncConfigs.computeIfAbsent(userId, k -> new HashMap<>());
        
        // 创建配置信息
        Map<String, Object> configInfo = new HashMap<>();
        configInfo.put("syncDirection", syncDirection);
        configInfo.put("syncInterval", syncInterval);
        configInfo.put("syncCategories", syncCategories);
        configInfo.put("updateTime", System.currentTimeMillis());
        
        // 添加配置
        configs.put(type, configInfo);
        
        return true;
    }
    
    @Override
    public Object getCalendarSyncConfig(Long userId, String type) {
        log.info("获取日历同步配置，用户ID：{}，类型：{}", userId, type);
        
        // 获取用户的同步配置映射
        Map<String, Map<String, Object>> configs = userSyncConfigs.get(userId);
        if (configs == null) {
            return null;
        }
        
        // 获取配置
        return configs.get(type);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object syncCalendarNow(Long userId, String type) {
        log.info("立即同步日历，用户ID：{}，类型：{}", userId, type);
        
        // 这里应该根据type调用不同的同步逻辑
        int count;
        switch (type.toLowerCase()) {
            case "google":
                count = syncWithGoogleCalendar(userId, "mock_token", true);
                break;
            case "outlook":
                count = syncWithOutlookCalendar(userId, "mock_token", true);
                break;
            default:
                count = 0;
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", count > 0);
        result.put("count", count);
        result.put("time", System.currentTimeMillis());
        
        return result;
    }
    
    @Override
    public Object getSyncHistory(Long userId, String type, int pageNum, int pageSize) {
        log.info("获取同步历史，用户ID：{}，类型：{}，页码：{}，每页大小：{}", userId, type, pageNum, pageSize);
        
        // 获取用户的同步历史映射
        Map<String, List<Map<String, Object>>> history = userSyncHistory.get(userId);
        if (history == null || !history.containsKey(type)) {
            return Collections.emptyList();
        }
        
        // 获取历史记录
        List<Map<String, Object>> records = history.get(type);
        
        // 分页处理
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, records.size());
        
        if (start >= records.size()) {
            return Collections.emptyList();
        }
        
        return records.subList(start, end);
    }
    
    /**
     * 记录同步历史
     */
    private void recordSyncHistory(Long userId, String type, boolean success, String message) {
        // 获取或创建用户的同步历史映射
        Map<String, List<Map<String, Object>>> history = userSyncHistory.computeIfAbsent(userId, k -> new HashMap<>());
        
        // 获取或创建类型的历史记录列表
        List<Map<String, Object>> records = history.computeIfAbsent(type, k -> new ArrayList<>());
        
        // 创建历史记录
        Map<String, Object> record = new HashMap<>();
        record.put("time", System.currentTimeMillis());
        record.put("success", success);
        record.put("message", message);
        
        // 添加记录
        records.add(0, record);
        
        // 限制历史记录数量
        if (records.size() > 100) {
            records.remove(records.size() - 1);
        }
    }
} 