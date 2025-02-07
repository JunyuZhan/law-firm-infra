package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.common.log.domain.OperationLogDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统日志Mapper接口
 */
@Mapper
public interface SysLogMapper extends BaseMapper<OperationLogDO> {

    /**
     * 根据用户ID查询日志列表
     */
    @Select("SELECT * FROM sys_operation_log WHERE operator_id = #{userId} AND del_flag = 0")
    List<OperationLogDO> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据模块查询日志列表
     */
    @Select("SELECT * FROM sys_operation_log WHERE module = #{module} AND del_flag = 0")
    List<OperationLogDO> selectByModule(@Param("module") String module);

    /**
     * 根据时间范围查询日志列表
     */
    @Select("SELECT * FROM sys_operation_log WHERE operation_time BETWEEN #{startTime} AND #{endTime} AND del_flag = 0")
    List<OperationLogDO> selectByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 清理指定时间之前的日志
     */
    @Delete("DELETE FROM sys_operation_log WHERE operation_time < #{time}")
    void cleanBefore(@Param("time") LocalDateTime time);
} 