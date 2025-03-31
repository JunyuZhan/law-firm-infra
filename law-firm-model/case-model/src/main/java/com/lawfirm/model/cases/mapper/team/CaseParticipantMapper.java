package com.lawfirm.model.cases.mapper.team;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.team.CaseParticipant;
import com.lawfirm.model.cases.constants.CaseSqlConstants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 案件参与者Mapper接口
 */
public interface CaseParticipantMapper extends BaseMapper<CaseParticipant> {
    
    /**
     * 根据案件ID查询参与人
     *
     * @param caseId 案件ID
     * @return 参与人列表
     */
    @Select(CaseSqlConstants.Participant.SELECT_BY_CASE_ID)
    List<CaseParticipant> selectByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据参与人类型查询
     *
     * @param caseId 案件ID
     * @param type 参与人类型
     * @return 参与人列表
     */
    @Select(CaseSqlConstants.Participant.SELECT_BY_TYPE)
    List<CaseParticipant> selectByType(@Param("caseId") Long caseId, @Param("type") Integer type);
} 