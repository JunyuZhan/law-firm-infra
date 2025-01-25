package com.lawfirm.cases.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.cases.entity.CaseProgress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 案件进展记录Mapper接口
 */
@Mapper
public interface CaseProgressMapper extends BaseMapper<CaseProgress> {

    /**
     * 根据案件ID查询进展记录列表
     */
    @Select("SELECT * FROM case_progress WHERE case_id = #{caseId} AND deleted = 0 ORDER BY handle_time DESC")
    List<CaseProgress> selectByCaseId(Long caseId);

    /**
     * 根据处理人ID查询进展记录列表
     */
    @Select("SELECT * FROM case_progress WHERE handler_id = #{handlerId} AND deleted = 0 ORDER BY handle_time DESC")
    List<CaseProgress> selectByHandlerId(Long handlerId);

    /**
     * 根据进展阶段查询进展记录列表
     */
    @Select("SELECT * FROM case_progress WHERE stage = #{stage} AND deleted = 0 ORDER BY handle_time DESC")
    List<CaseProgress> selectByStage(Integer stage);
} 