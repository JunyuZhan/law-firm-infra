package com.lawfirm.model.analysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.analysis.entity.AnalysisTaskHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 分析任务历史Mapper接口
 */
@Mapper
public interface AnalysisTaskHistoryMapper extends BaseMapper<AnalysisTaskHistory> {
    @Select("SELECT * FROM analysis_task_history WHERE task_id = #{taskId} ORDER BY start_time DESC")
    List<AnalysisTaskHistory> selectByTaskId(Long taskId);
} 