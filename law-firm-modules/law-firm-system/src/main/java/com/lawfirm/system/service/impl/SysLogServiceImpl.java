package com.lawfirm.system.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.model.system.entity.SysLog;
import com.lawfirm.system.mapper.SysLogMapper;
import com.lawfirm.system.model.dto.SysLogDTO;
import com.lawfirm.system.service.SysLogService;

import lombok.RequiredArgsConstructor;

/**
 * 系统日志服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog, SysLogDTO> implements SysLogService {

    private final SysLogMapper logMapper;

    // BaseServiceImpl abstract methods
    @Override
    protected SysLogDTO createDTO() {
        return new SysLogDTO();
    }

    @Override
    protected SysLog createEntity() {
        return new SysLog();
    }

    // SysLogService specific methods
    @Override
    public void createLog(SysLog log) {
        save(log);
    }

    @Override
    public void updateLog(SysLog log) {
        updateById(log);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    public List<SysLog> listByUserId(Long userId) {
        return logMapper.selectByUserId(userId);
    }

    @Override
    public List<SysLog> listByModule(String module) {
        return logMapper.selectByModule(module);
    }

    @Override
    public List<SysLog> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return logMapper.selectByTimeRange(startTime, endTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanLogs(LocalDateTime before) {
        logMapper.cleanBefore(before);
    }

    @Override
    public void exportLogs(LocalDateTime startTime, LocalDateTime endTime) {
        // TODO 导出日志功能待实现
    }

    @Override
    public void clean() {
        // 默认清理30天前的日志
        cleanLogs(LocalDateTime.now().minusDays(30));
    }

    @Override
    public void export() {
        // 默认导出最近7天的日志
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(7);
        exportLogs(startTime, endTime);
    }

    // BaseService interface methods
    @Override
    public SysLogDTO create(SysLogDTO dto) {
        return super.create(dto);
    }

    @Override
    public SysLogDTO update(SysLogDTO dto) {
        return super.update(dto);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        super.deleteBatch(ids);
    }

    @Override
    public SysLogDTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    public PageResult<SysLogDTO> page(Page<SysLog> page, QueryWrapper<SysLog> wrapper) {
        return super.page(page, wrapper);
    }

    @Override
    public List<SysLogDTO> list(QueryWrapper<SysLog> wrapper) {
        return super.list(wrapper);
    }

    // Entity conversion methods
    @Override
    public SysLogDTO toDTO(SysLog entity) {
        if (entity == null) {
            return null;
        }
        SysLogDTO dto = createDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public List<SysLogDTO> toDTOList(List<SysLog> entityList) {
        if (entityList == null) {
            return new ArrayList<>();
        }
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public SysLog toEntity(SysLogDTO dto) {
        if (dto == null) {
            return null;
        }
        SysLog entity = createEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public List<SysLog> toEntityList(List<SysLogDTO> dtoList) {
        if (dtoList == null) {
            return new ArrayList<>();
        }
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}