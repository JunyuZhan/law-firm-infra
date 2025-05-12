package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.schedule.dto.ExternalCalendarDTO;
import com.lawfirm.model.schedule.entity.ExternalCalendar;
import com.lawfirm.model.schedule.mapper.ExternalCalendarMapper;
import com.lawfirm.model.schedule.service.ExternalCalendarService;
import com.lawfirm.model.schedule.vo.ExternalCalendarVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 外部日历服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalCalendarServiceImpl extends ServiceImpl<ExternalCalendarMapper, ExternalCalendar> implements ExternalCalendarService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createExternalCalendar(ExternalCalendarDTO calendarDTO) {
        log.info("创建外部日历: {}", calendarDTO.getName());
        
        ExternalCalendar externalCalendar = new ExternalCalendar();
        BeanUtils.copyProperties(calendarDTO, externalCalendar);
        
        // 如果未设置用户ID，使用1作为默认值
        if (externalCalendar.getUserId() == null) {
            externalCalendar.setUserId(1L); // 默认用户ID
        }
        
        // 设置其他默认值
        externalCalendar.setLastSyncTime(LocalDateTime.now());
        externalCalendar.setProviderStatus(1); // 正常状态
        
        // 保存实体
        save(externalCalendar);
        
        return externalCalendar.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateExternalCalendar(Long id, ExternalCalendarDTO calendarDTO) {
        log.info("更新外部日历, ID: {}, 名称: {}", id, calendarDTO.getName());
        
        // 获取原有记录
        ExternalCalendar externalCalendar = getById(id);
        if (externalCalendar == null) {
            log.error("外部日历不存在, ID: {}", id);
            return false;
        }
        
        // 更新属性
        BeanUtils.copyProperties(calendarDTO, externalCalendar);
        externalCalendar.setId(id); // 确保ID不变
        
        return updateById(externalCalendar);
    }

    @Override
    public ExternalCalendarVO getExternalCalendarDetail(Long id) {
        log.info("获取外部日历详情, ID: {}", id);
        
        ExternalCalendar calendar = getById(id);
        if (calendar == null) {
            log.error("外部日历不存在, ID: {}", id);
            return null;
        }
        
        return convertToVO(calendar);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteExternalCalendar(Long id) {
        log.info("删除外部日历, ID: {}", id);
        return removeById(id);
    }

    @Override
    public List<ExternalCalendarVO> listUserExternalCalendars(Long userId) {
        log.info("获取用户外部日历列表, 用户ID: {}", userId);
        
        // 如果未提供用户ID，使用默认值
        if (userId == null) {
            userId = 1L; // 默认用户ID
        }
        
        // 构建查询条件
        LambdaQueryWrapper<ExternalCalendar> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExternalCalendar::getUserId, userId);
        
        // 查询并转换结果
        List<ExternalCalendar> calendars = list(queryWrapper);
        return calendars.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ExternalCalendarVO> page(Page<ExternalCalendar> page, String keyword, Integer providerType, Integer status) {
        log.info("分页查询外部日历, 关键字: {}, 提供商类型: {}, 状态: {}", keyword, providerType, status);
        
        // 构建查询条件
        LambdaQueryWrapper<ExternalCalendar> queryWrapper = new LambdaQueryWrapper<>();
        
        // 关键字搜索
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(ExternalCalendar::getName, keyword)
                    .or()
                    .like(ExternalCalendar::getDescription, keyword);
        }
        
        // 提供商类型过滤
        if (providerType != null) {
            queryWrapper.eq(ExternalCalendar::getProviderType, providerType);
        }
        
        // 状态过滤
        if (status != null) {
            queryWrapper.eq(ExternalCalendar::getProviderStatus, status);
        }
        
        // 排序
        queryWrapper.orderByDesc(ExternalCalendar::getUpdateTime);
        
        // 执行分页查询
        Page<ExternalCalendar> result = page(page, queryWrapper);
        
        // 转换为VO
        Page<ExternalCalendarVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        
        List<ExternalCalendarVO> records = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        voPage.setRecords(records);
        
        return voPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncExternalCalendar(Long id, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("同步外部日历, ID: {}, 时间范围: {} - {}", id, startTime, endTime);
        
        // 获取日历信息
        ExternalCalendar calendar = getById(id);
        if (calendar == null) {
            log.error("外部日历不存在, ID: {}", id);
            return 0;
        }
        
        // 更新最后同步时间
        calendar.setLastSyncTime(LocalDateTime.now());
        updateById(calendar);
        
        // 模拟同步结果
        return 10;
    }

    @Override
    public String getAuthorizationUrl(ExternalCalendarDTO calendarDTO) {
        log.info("获取授权URL, 提供商类型: {}", calendarDTO.getProviderType());
        
        // 根据不同提供商类型返回不同的授权URL
        switch (calendarDTO.getProviderType()) {
            case 1: // Google
                return "https://accounts.google.com/o/oauth2/auth?client_id=your-client-id&redirect_uri=your-redirect-uri&scope=https://www.googleapis.com/auth/calendar&response_type=code";
            case 2: // Microsoft
                return "https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=your-client-id&redirect_uri=your-redirect-uri&scope=Calendars.ReadWrite&response_type=code";
            case 3: // Apple
                return "https://appleid.apple.com/auth/authorize?client_id=your-client-id&redirect_uri=your-redirect-uri&scope=calendars&response_type=code";
            default:
                return "https://example.com/auth";
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handleAuthCallback(Integer providerType, String code, String state) {
        log.info("处理授权回调, 提供商类型: {}, 授权码: {}, 状态: {}", providerType, code, state);
        
        // 模拟授权成功
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateExternalCalendarStatus(Long id, Integer status) {
        log.info("更新外部日历状态, ID: {}, 状态: {}", id, status);
        
        ExternalCalendar calendar = getById(id);
        if (calendar == null) {
            log.error("外部日历不存在, ID: {}", id);
            return false;
        }
        
        calendar.setProviderStatus(status);
        return updateById(calendar);
    }
    
    @Override
    public Long getCurrentUserId() {
        // 简单实现，实际应从安全上下文获取
        return 1L;
    }
    
    @Override
    public String getCurrentUsername() {
        // 简单实现，实际应从安全上下文获取
        return "admin";
    }
    
    @Override
    public Long getCurrentTenantId() {
        // 实现BaseService接口的抽象方法
        return 1L;
    }
    
    @Override
    public boolean exists(QueryWrapper<ExternalCalendar> queryWrapper) {
        // 实现BaseService接口的抽象方法
        return count(queryWrapper) > 0;
    }
    
    @Override
    public boolean saveBatch(List<ExternalCalendar> entityList) {
        // 实现BaseService接口的抽象方法
        return saveOrUpdateBatch(entityList);
    }
    
    @Override
    public boolean updateBatch(List<ExternalCalendar> entityList) {
        // 实现BaseService接口的抽象方法
        return updateBatchById(entityList);
    }
    
    @Override
    public ExternalCalendar getById(Long id) {
        // 实现BaseService接口的抽象方法
        return super.getById(id);
    }
    
    @Override
    public boolean remove(Long id) {
        // 实现BaseService接口的抽象方法
        return removeById(id);
    }
    
    @Override
    public boolean removeBatch(List<Long> idList) {
        // 实现BaseService接口的抽象方法
        return removeByIds(idList);
    }
    
    @Override
    public boolean update(ExternalCalendar entity) {
        // 实现BaseService接口的抽象方法
        return updateById(entity);
    }
    
    @Override
    public boolean save(ExternalCalendar entity) {
        // 实现BaseService接口的抽象方法
        return super.save(entity);
    }
    
    @Override
    public List<ExternalCalendar> list(QueryWrapper<ExternalCalendar> queryWrapper) {
        // 实现BaseService接口的抽象方法
        return super.list(queryWrapper);
    }
    
    @Override
    public Page<ExternalCalendar> page(Page<ExternalCalendar> page, QueryWrapper<ExternalCalendar> queryWrapper) {
        // 实现BaseService接口的抽象方法
        return super.page(page, queryWrapper);
    }
    
    @Override
    public long count(QueryWrapper<ExternalCalendar> queryWrapper) {
        // 实现BaseService接口的抽象方法
        return super.count(queryWrapper);
    }
    
    /**
     * 将实体转换为VO
     */
    private ExternalCalendarVO convertToVO(ExternalCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        
        ExternalCalendarVO vo = new ExternalCalendarVO();
        BeanUtils.copyProperties(calendar, vo);
        
        // 设置提供商类型名称
        switch (calendar.getProviderType()) {
            case 1:
                vo.setProviderTypeName("Google");
                break;
            case 2:
                vo.setProviderTypeName("Microsoft");
                break;
            case 3:
                vo.setProviderTypeName("Apple");
                break;
            case 4:
                vo.setProviderTypeName("其他");
                break;
            default:
                vo.setProviderTypeName("未知");
        }
        
        // 设置状态名称
        switch (calendar.getProviderStatus()) {
            case 1:
                vo.setStatusName("正常");
                break;
            case 2:
                vo.setStatusName("同步失败");
                break;
            case 3:
                vo.setStatusName("授权过期");
                break;
            case 4:
                vo.setStatusName("已暂停");
                break;
            default:
                vo.setStatusName("未知");
        }
        
        // 设置用户名（实际项目中应从用户服务获取）
        vo.setUsername("用户" + calendar.getUserId());
        
        // 设置事件数量（实际项目中应查询关联的事件数量）
        vo.setEventCount(0);
        
        return vo;
    }
} 