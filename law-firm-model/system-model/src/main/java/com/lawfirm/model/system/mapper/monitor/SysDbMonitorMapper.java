package com.lawfirm.model.system.mapper.monitor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.monitor.SysDbMonitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 数据库监控数据Mapper接口
 */
@Mapper
public interface SysDbMonitorMapper extends BaseMapper<SysDbMonitor> {
    
    /**
     * 获取数据库最近一条监控数据
     *
     * @param dbName 数据库名称
     * @return 数据库监控数据
     */
    @Select("SELECT * FROM sys_db_monitor WHERE db_name = #{dbName} ORDER BY monitor_time DESC LIMIT 1")
    SysDbMonitor getLatestByDbName(String dbName);
    
    /**
     * 获取指定时间段内的数据库活动连接数
     *
     * @param dbName 数据库名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 活动连接数列表
     */
    @Select("SELECT monitor_time, active_connections, max_connections FROM sys_db_monitor WHERE db_name = #{dbName} AND monitor_time BETWEEN #{startTime} AND #{endTime} ORDER BY monitor_time")
    List<SysDbMonitor> getConnectionsByTimeRange(String dbName, String startTime, String endTime);
    
    /**
     * 获取性能较差的数据库列表
     *
     * @return 性能较差的数据库列表
     */
    @Select("SELECT DISTINCT db_name FROM sys_db_monitor WHERE db_status = 'WARNING' OR db_status = 'ERROR' ORDER BY monitor_time DESC LIMIT 10")
    List<String> getPoorPerformanceDatabases();
} 