package com.lawfirm.model.cases.mapper.team;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.team.CaseTeamMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 案件团队成员Mapper接口
 */
@Mapper
public interface CaseTeamMemberMapper extends BaseMapper<CaseTeamMember> {
    
    /**
     * 根据成员ID查询关联的所有案件ID
     * 
     * @param memberId 成员ID
     * @return 案件ID列表
     */
    @Select("SELECT case_id FROM case_team_member WHERE member_id = #{memberId}")
    List<Long> selectCaseIdsByMemberId(@Param("memberId") Long memberId);
} 