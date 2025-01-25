package com.lawfirm.cases.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.Case;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 案件Mapper接口
 */
@Mapper
public interface CaseMapper extends BaseMapper<Case> {

    /**
     * 根据委托人ID查询案件列表
     */
    @Select("SELECT * FROM case_info WHERE client_id = #{clientId} AND deleted = 0")
    List<Case> selectByClientId(Long clientId);

    /**
     * 根据律师ID查询案件列表
     */
    @Select("SELECT * FROM case_info WHERE lawyer_id = #{lawyerId} AND deleted = 0")
    List<Case> selectByLawyerId(Long lawyerId);

    /**
     * 根据部门ID查询案件列表
     */
    @Select("SELECT * FROM case_info WHERE department_id = #{departmentId} AND deleted = 0")
    List<Case> selectByDepartmentId(Long departmentId);

    /**
     * 根据分支机构ID查询案件列表
     */
    @Select("SELECT * FROM case_info WHERE branch_id = #{branchId} AND deleted = 0")
    List<Case> selectByBranchId(Long branchId);
} 