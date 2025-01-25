package com.lawfirm.cases.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.CaseReminder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件提醒Mapper接口
 */
@Mapper
public interface CaseReminderMapper extends BaseMapper<CaseReminder> {

    /**
     * 根据案件ID查询提醒列表
     */
    @Select("SELECT * FROM case_reminder WHERE case_id = #{caseId} AND deleted = 0 ORDER BY reminder_time DESC")
    List<CaseReminder> selectByCaseId(Long caseId);

    /**
     * 根据接收人ID查询提醒列表
     */
    @Select("SELECT * FROM case_reminder WHERE receiver_id = #{receiverId} AND deleted = 0 ORDER BY reminder_time DESC")
    List<CaseReminder> selectByReceiverId(Long receiverId);

    /**
     * 根据提醒类型查询提醒列表
     */
    @Select("SELECT * FROM case_reminder WHERE type = #{type} AND deleted = 0 ORDER BY reminder_time DESC")
    List<CaseReminder> selectByType(Integer type);

    /**
     * 查询指定时间范围内的提醒列表
     */
    @Select("SELECT * FROM case_reminder WHERE reminder_time BETWEEN #{startTime} AND #{endTime} AND deleted = 0 ORDER BY reminder_time")
    List<CaseReminder> selectByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 查询待处理的提醒列表
     */
    @Select("SELECT * FROM case_reminder WHERE status = 0 AND reminder_time <= #{currentTime} AND deleted = 0 ORDER BY reminder_time")
    List<CaseReminder> selectPendingReminders(LocalDateTime currentTime);
} 