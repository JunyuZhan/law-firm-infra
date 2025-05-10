package com.lawfirm.model.cases.mapper.team;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.team.CaseAssignment;
import com.lawfirm.model.cases.constants.CaseSqlConstants;
import com.lawfirm.model.cases.entity.base.Case;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 案件分配Mapper接口
 */
@Mapper
public interface CaseAssignmentMapper extends BaseMapper<CaseAssignment> {
    
    /**
     * 根据案件ID查询分配记录
     *
     * @param caseId 案件ID
     * @return 分配记录列表
     */
    @Select("SELECT * FROM case_assignment WHERE case_id = #{caseId} AND deleted = 0")
    List<CaseAssignment> selectByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据律师ID查询被分配的案件
     *
     * @param lawyerId 律师ID
     * @return 案件列表
     */
    @Select(CaseSqlConstants.Assignment.SELECT_BY_LAWYER_ID)
    List<Case> selectByLawyerId(@Param("lawyerId") Long lawyerId);
} 