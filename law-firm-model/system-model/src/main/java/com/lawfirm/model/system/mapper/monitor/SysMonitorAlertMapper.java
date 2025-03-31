package com.lawfirm.model.system.mapper.monitor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.monitor.SysMonitorAlert;
import com.lawfirm.model.system.constant.SystemSqlConstants;
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
} 