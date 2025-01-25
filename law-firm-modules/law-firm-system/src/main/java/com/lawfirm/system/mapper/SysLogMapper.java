package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统操作日志Mapper接口
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

    /**
     * 根据用户ID查询操作日志列表
     */
    @Select("SELECT * FROM sys_log WHERE user_id = #{userId} AND deleted = 0 ORDER BY create_time DESC")
    List<SysLog> selectByUserId(Long userId);

    /**
     * 根据模块查询操作日志列表
     */
    @Select("SELECT * FROM sys_log WHERE module = #{module} AND deleted = 0 ORDER BY create_time DESC")
    List<SysLog> selectByModule(String module);

    /**
     * 根据时间范围查询操作日志列表
     */
    @Select("SELECT * FROM sys_log WHERE create_time BETWEEN #{startTime} AND #{endTime} AND deleted = 0 ORDER BY create_time DESC")
    List<SysLog> selectByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 清理指定时间之前的操作日志
     */
    @Select("DELETE FROM sys_log WHERE create_time < #{time}")
    void cleanBefore(LocalDateTime time);
} 