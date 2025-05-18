package com.lawfirm.core.audit.service.impl;

import com.lawfirm.model.log.service.LogService;
import com.lawfirm.model.log.dto.BaseLogDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.log.dto.LogExportDTO;
import com.lawfirm.model.log.dto.LogQueryDTO;

/**
 * 日志服务实现，主要用于日志清理等后台任务，日志记录依然由AOP注解完成。
 */
@Service("logService")
public class LogServiceImpl implements LogService<BaseLogDTO> {
    // 内存日志存储模拟
    private static final List<BaseLogDTO> LOG_STORE = new ArrayList<>();

    @Override
    public Integer cleanExpiredLogs(Integer days) {
        // 简单模拟：移除days天前的日志
        long now = System.currentTimeMillis();
        int before = LOG_STORE.size();
        LOG_STORE.removeIf(log -> {
            try {
                java.lang.reflect.Method m = log.getClass().getMethod("getCreateTime");
                Object val = m.invoke(log);
                if (val instanceof java.util.Date) {
                    return now - ((java.util.Date) val).getTime() > days * 24 * 3600 * 1000L;
                }
            } catch (Exception ignore) {}
            return false;
        });
        return before - LOG_STORE.size();
    }

    @Override
    public Long recordLog(BaseLogDTO logDTO) {
        if (logDTO != null) {
            LOG_STORE.add(logDTO);
            return (long) LOG_STORE.size();
        }
        return null;
    }

    @Override
    public String exportLogs(LogExportDTO exportDTO) {
        // 简单导出为字符串
        return LOG_STORE.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public Page<BaseLogDTO> page(Page<BaseLogDTO> page, LogQueryDTO queryDTO) {
        // 简单分页
        int start = (int) ((page.getCurrent() - 1) * page.getSize());
        int end = Math.min(start + (int) page.getSize(), LOG_STORE.size());
        List<BaseLogDTO> records = LOG_STORE.subList(Math.max(0, start), end);
        page.setRecords(records);
        page.setTotal(LOG_STORE.size());
        return page;
    }

    @Override
    public BaseLogDTO getById(Long id) {
        return LOG_STORE.stream().filter(log -> log.getId().equals(id)).findFirst().orElse(null);
    }
} 