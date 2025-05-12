package com.lawfirm.model.system.mapper.monitor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.monitor.SysAppMonitor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 应用监控数据Mapper接口
 */
@Mapper
public interface SysAppMonitorMapper extends BaseMapper<SysAppMonitor> {
    
    /**
     * 获取应用最近一条监控数据
     *
     * @param appName 应用名称
     * @param instanceId 实例ID
     * @return 应用监控数据
     */
    @Select("SELECT * FROM sys_app_monitor WHERE app_name = #{appName} AND instance_id = #{instanceId} ORDER BY monitor_time DESC LIMIT 1")
    SysAppMonitor getLatestByAppAndInstance(String appName, String instanceId);
    
    /**
     * 获取指定时间段内的JVM内存使用情况
     *
     * @param appName 应用名称
     * @param instanceId 实例ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return JVM内存使用列表
     */
    @Select("SELECT monitor_time, jvm_memory_used, jvm_memory_max FROM sys_app_monitor WHERE app_name = #{appName} AND instance_id = #{instanceId} AND monitor_time BETWEEN #{startTime} AND #{endTime} ORDER BY monitor_time")
    List<SysAppMonitor> getJvmMemoryByTimeRange(String appName, String instanceId, String startTime, String endTime);
    
    /**
     * 插入应用监控数据
     * 显式定义insert方法解决"Invalid bound statement"问题
     *
     * @param entity 应用监控实体
     * @return 影响行数
     */
    @Insert("INSERT INTO sys_app_monitor (app_name, instance_id, jvm_memory_used, jvm_memory_max, thread_count, gc_count, monitor_time, version, status, sort, deleted, create_time, update_time) " +
           "VALUES (#{appName}, #{instanceId}, #{jvmMemoryUsed}, #{jvmMemoryMax}, #{threadCount}, #{gcCount}, #{monitorTime}, #{version}, #{status}, #{sort}, #{deleted}, #{createTime}, #{updateTime})")
    int insert(SysAppMonitor entity);
} 