package com.lawfirm.model.cases.mapper.business;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.business.CaseNote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 案件笔记Mapper接口
 */
@Mapper
public interface CaseNoteMapper extends BaseMapper<CaseNote> {
    
    /**
     * 根据案件ID查询笔记列表
     *
     * @param caseId 案件ID
     * @return 笔记列表
     */
    @Select("SELECT * FROM case_note WHERE case_id = #{caseId} AND deleted = 0 ORDER BY create_time DESC")
    List<CaseNote> selectByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据创建者查询笔记
     *
     * @param creatorId 创建者ID
     * @return 笔记列表
     */
    @Select("SELECT * FROM case_note WHERE creator_id = #{creatorId} AND deleted = 0 ORDER BY create_time DESC")
    List<CaseNote> selectByCreator(@Param("creatorId") Long creatorId);
} 