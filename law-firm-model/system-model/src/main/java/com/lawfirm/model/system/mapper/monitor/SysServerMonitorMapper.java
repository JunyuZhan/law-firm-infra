package com.lawfirm.model.system.mapper.monitor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.monitor.SysServerMonitor;
import org.apache.ibatis.annotations.Insert;
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

    /**
     * 插入服务器监控数据
     * 显式定义insert方法解决"Invalid bound statement"问题
     * @param entity 服务器监控实体
     * @return 影响行数
     */
    @Insert("INSERT INTO sys_server_monitor (server_name, server_ip, cpu_usage, memory_usage, disk_usage, network_rx, network_tx, server_load, monitor_time, version, status, sort, deleted, create_time, update_time) " +
            "VALUES (#{serverName}, #{serverIp}, #{cpuUsage}, #{memoryUsage}, #{diskUsage}, #{networkRx}, #{networkTx}, #{serverLoad}, #{monitorTime}, #{version}, #{status}, #{sort}, #{deleted}, #{createTime}, #{updateTime})")
    int insert(SysServerMonitor entity);
} 