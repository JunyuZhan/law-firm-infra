package com.lawfirm.model.system.mapper.monitor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.monitor.SysServerMonitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 服务器监控数据Mapper接口
 */
@Mapper
public interface SysServerMonitorMapper extends BaseMapper<SysServerMonitor> {
    
    /**
     * 获取最近一条服务器监控数据
     *
     * @param serverIp 服务器IP
     * @return 服务器监控数据
     */
    @Select("SELECT * FROM sys_server_monitor WHERE server_ip = #{serverIp} ORDER BY monitor_time DESC LIMIT 1")
    SysServerMonitor getLatestByServerIp(String serverIp);
    
    /**
     * 获取指定时间段内的服务器CPU使用率
     *
     * @param serverIp 服务器IP
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return CPU使用率列表
     */
    @Select("SELECT monitor_time, cpu_usage FROM sys_server_monitor WHERE server_ip = #{serverIp} AND monitor_time BETWEEN #{startTime} AND #{endTime} ORDER BY monitor_time")
    List<SysServerMonitor> getCpuUsageByTimeRange(String serverIp, String startTime, String endTime);
} 