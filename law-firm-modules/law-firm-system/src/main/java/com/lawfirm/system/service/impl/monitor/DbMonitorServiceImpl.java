package com.lawfirm.system.service.impl.monitor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.log.annotation.AuditLog;
import com.lawfirm.model.system.entity.monitor.SysDbMonitor;
import com.lawfirm.model.system.mapper.monitor.SysDbMonitorMapper;
import com.lawfirm.system.config.SystemModuleConfig.MonitorProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 数据库监控服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DbMonitorServiceImpl extends ServiceImpl<SysDbMonitorMapper, SysDbMonitor> {

    private final SysDbMonitorMapper dbMonitorMapper;
    private final JdbcTemplate jdbcTemplate;
    private final MonitorProperties monitorProperties;
    
    @Value("${spring.datasource.url:jdbc:mysql://localhost:3306/law_firm}")
    private String datasourceUrl;
    
    /**
     * 定时收集数据库监控数据
     */
    @Scheduled(fixedRateString = "${system.monitor.metrics-interval:60}", timeUnit = TimeUnit.SECONDS)
    @Transactional
    @CacheEvict(value = "monitorCache", allEntries = true)
    public void collectDbMetrics() {
        try {
            String dbName = extractDbNameFromUrl(datasourceUrl);
            
            SysDbMonitor monitor = new SysDbMonitor();
            monitor.setDbName(dbName);
            monitor.setDbUrl(datasourceUrl);
            
            // 收集数据库连接数据
            Map<String, Object> connInfo = getConnectionInfo();
            monitor.setActiveConnections((Integer) connInfo.getOrDefault("active", 0));
            monitor.setMaxConnections((Integer) connInfo.getOrDefault("max", 100));
            
            // 收集性能数据
            monitor.setQps(getQps());
            monitor.setTps(getTps());
            monitor.setSlowQueries(getSlowQueries());
            
            // 收集大小数据
            Map<String, Long> sizeInfo = getSizeInfo();
            monitor.setTableSize(sizeInfo.getOrDefault("tableSize", 0L));
            monitor.setIndexSize(sizeInfo.getOrDefault("indexSize", 0L));
            
            // 设置状态
            monitor.setDbStatus(determineStatus(monitor));
            monitor.setMonitorTime(LocalDateTime.now());
            
            dbMonitorMapper.insert(monitor);
            log.debug("数据库监控数据已收集: {}", monitor);
            
            // 检查是否需要告警
            checkAlert(monitor);
        } catch (Exception e) {
            log.error("收集数据库监控数据异常", e);
        }
    }
    
    /**
     * 获取数据库信息列表
     */
    @Cacheable(value = "monitorCache", key = "'dbList'")
    public List<SysDbMonitor> getDbList() {
        LambdaQueryWrapper<SysDbMonitor> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysDbMonitor::getMonitorTime);
        wrapper.last("LIMIT 100");
        return dbMonitorMapper.selectList(wrapper);
    }
    
    /**
     * 获取最新数据库信息
     */
    @Cacheable(value = "monitorCache", key = "'latestDb_' + #dbName")
    public SysDbMonitor getLatestDbInfo(String dbName) {
        return dbMonitorMapper.getLatestByDbName(dbName);
    }
    
    /**
     * 获取性能较差的数据库列表
     */
    @Cacheable(value = "monitorCache", key = "'poorPerformanceDbs'")
    public List<String> getPoorPerformanceDatabases() {
        return dbMonitorMapper.getPoorPerformanceDatabases();
    }
    
    /**
     * 从URL中提取数据库名称
     */
    private String extractDbNameFromUrl(String url) {
        try {
            if (url == null || url.trim().isEmpty()) {
                return "unknown";
            }
            
            // 尝试从JDBC URL中提取数据库名称
            // 示例: jdbc:mysql://localhost:3306/law_firm
            int lastSlashIndex = url.lastIndexOf('/');
            if (lastSlashIndex > 0 && lastSlashIndex < url.length() - 1) {
                String dbName = url.substring(lastSlashIndex + 1);
                // 去除URL中可能的参数
                int paramIndex = dbName.indexOf('?');
                if (paramIndex > 0) {
                    dbName = dbName.substring(0, paramIndex);
                }
                return dbName;
            }
            
            return "unknown";
        } catch (Exception e) {
            log.error("提取数据库名称异常", e);
            return "unknown";
        }
    }
    
    /**
     * 获取数据库连接信息
     */
    private Map<String, Object> getConnectionInfo() {
        try {
            // 实际项目中应该通过数据库连接池获取连接信息
            // 这里使用JdbcTemplate查询MySQL的连接状态
            String sql = "SELECT COUNT(*) as active, @@max_connections as max FROM information_schema.processlist";
            return jdbcTemplate.queryForMap(sql);
        } catch (Exception e) {
            log.error("获取数据库连接信息异常", e);
            return Map.of("active", 1, "max", 100);
        }
    }
    
    /**
     * 获取每秒查询数
     */
    private BigDecimal getQps() {
        try {
            // 在实际项目中，应该通过MySQL性能监控获取
            // 例如通过查询MySQL performance_schema
            // 这里返回模拟数据
            return BigDecimal.valueOf(Math.random() * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            log.error("获取QPS异常", e);
            return BigDecimal.ZERO;
        }
    }
    
    /**
     * 获取每秒事务数
     */
    private BigDecimal getTps() {
        try {
            // 在实际项目中，应该通过MySQL性能监控获取
            // 例如通过查询MySQL performance_schema
            // 这里返回模拟数据
            return BigDecimal.valueOf(Math.random() * 50).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            log.error("获取TPS异常", e);
            return BigDecimal.ZERO;
        }
    }
    
    /**
     * 获取慢查询数
     */
    private Integer getSlowQueries() {
        try {
            // 在实际项目中，应该查询slow_query_log表
            // 这里返回模拟数据
            return (int) (Math.random() * 10);
        } catch (Exception e) {
            log.error("获取慢查询数异常", e);
            return 0;
        }
    }
    
    /**
     * 获取数据库大小信息
     */
    private Map<String, Long> getSizeInfo() {
        try {
            // 实际项目中应该查询information_schema
            String dbName = extractDbNameFromUrl(datasourceUrl);
            String sql = "SELECT " +
                "SUM(data_length) AS tableSize, " +
                "SUM(index_length) AS indexSize " +
                "FROM information_schema.tables " +
                "WHERE table_schema = ?";
            
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, dbName);
            
            return Map.of(
                "tableSize", ((Number) result.getOrDefault("tableSize", 0)).longValue(),
                "indexSize", ((Number) result.getOrDefault("indexSize", 0)).longValue()
            );
        } catch (Exception e) {
            log.error("获取数据库大小信息异常", e);
            return Map.of("tableSize", 0L, "indexSize", 0L);
        }
    }
    
    /**
     * 根据监控数据判断数据库状态
     */
    private String determineStatus(SysDbMonitor monitor) {
        try {
            // 判断逻辑：
            // 1. 如果活动连接数超过最大连接数的80%，返回WARNING
            // 2. 如果活动连接数超过最大连接数的90%，返回ERROR
            // 3. 如果慢查询数超过5，返回WARNING
            // 4. 如果慢查询数超过10，返回ERROR
            if (monitor.getMaxConnections() > 0) {
                double connUsage = (double) monitor.getActiveConnections() / monitor.getMaxConnections() * 100;
                if (connUsage > 90) {
                    return "ERROR";
                } else if (connUsage > 80) {
                    return "WARNING";
                }
            }
            
            if (monitor.getSlowQueries() != null) {
                if (monitor.getSlowQueries() > 10) {
                    return "ERROR";
                } else if (monitor.getSlowQueries() > 5) {
                    return "WARNING";
                }
            }
            
            return "NORMAL";
        } catch (Exception e) {
            log.error("判断数据库状态异常", e);
            return "NORMAL";
        }
    }
    
    /**
     * 检查并触发告警
     */
    private void checkAlert(SysDbMonitor monitor) {
        try {
            // 检查连接数是否超过阈值
            if (monitor.getMaxConnections() > 0) {
                double connUsage = (double) monitor.getActiveConnections() / monitor.getMaxConnections() * 100;
                if (connUsage > 80) {
                    log.warn("数据库连接使用率超过阈值: {}%, 数据库: {}", 
                        String.format("%.2f", connUsage), monitor.getDbName());
                    // TODO: 调用告警服务发送告警
                }
            }
            
            // 检查慢查询数
            if (monitor.getSlowQueries() != null && monitor.getSlowQueries() > 5) {
                log.warn("数据库慢查询数超过阈值: {}, 数据库: {}", 
                    monitor.getSlowQueries(), monitor.getDbName());
                // TODO: 调用告警服务发送告警
            }
            
            // 检查状态
            if (!"NORMAL".equals(monitor.getDbStatus())) {
                log.warn("数据库状态异常: {}, 数据库: {}", 
                    monitor.getDbStatus(), monitor.getDbName());
                // TODO: 调用告警服务发送告警
            }
        } catch (Exception e) {
            log.error("检查告警异常", e);
        }
    }
} 