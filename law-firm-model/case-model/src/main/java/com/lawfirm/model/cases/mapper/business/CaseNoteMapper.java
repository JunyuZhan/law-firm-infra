package com.lawfirm.model.cases.mapper.business;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.business.CaseNote;
import com.lawfirm.model.cases.constants.CaseSqlConstants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 案件笔记Mapper接口
 */
public interface CaseNoteMapper extends BaseMapper<CaseNote> {
    
    /**
     * 根据案件ID查询笔记
     *
     * @param caseId 案件ID
     * @return 笔记列表
     */
    @Select(CaseSqlConstants.Note.SELECT_BY_CASE_ID)
    List<CaseNote> selectByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据创建人查询笔记
     *
     * @param creatorId 创建人ID
     * @return 笔记列表
     */
    @Select(CaseSqlConstants.Note.SELECT_BY_CREATOR)
    List<CaseNote> selectByCreator(@Param("creatorId") Long creatorId);
} 