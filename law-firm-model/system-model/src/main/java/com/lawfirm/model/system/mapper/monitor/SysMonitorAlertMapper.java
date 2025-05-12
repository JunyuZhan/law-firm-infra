package com.lawfirm.model.system.mapper.monitor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.monitor.SysMonitorAlert;
import com.lawfirm.model.system.constant.SystemSqlConstants;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统监控告警Mapper接口
 */
@Mapper
public interface SysMonitorAlertMapper extends BaseMapper<SysMonitorAlert> {
    
    /**
     * 查询未处理的告警
     *
     * @return 未处理告警列表
     */
    @Select(SystemSqlConstants.Monitor.SELECT_UNPROCESSED_ALERTS)
    List<SysMonitorAlert> selectUnprocessedAlerts();
    
    /**
     * 插入告警记录
     * 显式定义insert方法解决"Invalid bound statement"问题
     *
     * @param entity 告警实体
     * @return 影响行数
     */
    @Insert("INSERT INTO sys_monitor_alert (alert_id, type, level, message, alert_status, handler, handle_time, handle_result, version, status, sort, deleted, create_time, update_time) " +
           "VALUES (#{alertId}, #{type}, #{level}, #{message}, #{alertStatus}, #{handler}, #{handleTime}, #{handleResult}, #{version}, #{status}, #{sort}, #{deleted}, #{createTime}, #{updateTime})")
    int insert(SysMonitorAlert entity);
} 