package com.lawfirm.model.cases.mapper.team;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.team.CaseTeam;
import com.lawfirm.model.cases.constants.CaseSqlConstants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 案件团队Mapper接口
 */
public interface CaseTeamMapper extends BaseMapper<CaseTeam> {
    
    /**
     * 根据案件ID查询团队
     *
     * @param caseId 案件ID
     * @return 团队列表
     */
    @Select(CaseSqlConstants.Team.SELECT_BY_CASE_ID)
    List<CaseTeam> selectByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据负责人查询团队
     *
     * @param leaderId 负责人ID
     * @return 团队列表
     */
    @Select(CaseSqlConstants.Team.SELECT_BY_LEADER)
    List<CaseTeam> selectByLeader(@Param("leaderId") Long leaderId);
} 