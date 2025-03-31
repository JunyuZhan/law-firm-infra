package com.lawfirm.model.system.mapper.monitor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.monitor.SysMonitorData;
import com.lawfirm.model.system.constant.SystemSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 系统监控数据Mapper接口
 */
@Mapper
public interface SysMonitorDataMapper extends BaseMapper<SysMonitorData> {
    
    /**
     * 查询指定时间段内的监控数据
     *
     * @param monitorType 监控类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 监控数据列表
     */
    @Select(SystemSqlConstants.Monitor.SELECT_DATA_BY_TIME_RANGE)
    List<SysMonitorData> selectByTimeRange(
            @Param("monitorType") String monitorType,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime);
    
    /**
     * 查询最新的监控数据
     *
     * @param monitorType 监控类型
     * @return 最新监控数据
     */
    @Select(SystemSqlConstants.Monitor.SELECT_LATEST_DATA)
    SysMonitorData selectLatestData(@Param("monitorType") String monitorType);
} 