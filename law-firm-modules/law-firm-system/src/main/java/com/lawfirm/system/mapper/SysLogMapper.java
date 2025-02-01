package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.SysLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统日志数据访问接口
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

    /**
     * 根据用户ID查询日志列表
     */
    @Select("SELECT * FROM sys_log WHERE user_id = #{userId} AND del_flag = 0")
    List<SysLog> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据模块查询日志列表
     */
    @Select("SELECT * FROM sys_log WHERE module = #{module} AND del_flag = 0")
    List<SysLog> selectByModule(@Param("module") String module);

    /**
     * 根据时间范围查询日志列表
     */
    @Select("SELECT * FROM sys_log WHERE create_time BETWEEN #{startTime} AND #{endTime} AND del_flag = 0")
    List<SysLog> selectByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 清理指定时间之前的日志
     */
    @Delete("DELETE FROM sys_log WHERE create_time < #{time}")
    void cleanBefore(@Param("time") LocalDateTime time);
} 