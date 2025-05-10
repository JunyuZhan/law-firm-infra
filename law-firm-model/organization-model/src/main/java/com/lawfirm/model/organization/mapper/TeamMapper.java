package com.lawfirm.model.organization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.organization.entity.team.Team;
import com.lawfirm.model.organization.constant.OrganizationSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 团队数据访问接口
 */
@Mapper
public interface TeamMapper extends BaseMapper<Team> {

    /**
     * 根据团队编码查询
     *
     * @param code 团队编码
     * @return 团队信息
     */
    @Select(OrganizationSqlConstants.Team.SELECT_BY_CODE)
    Team selectByCode(@Param("code") String code);

    /**
     * 根据部门ID查询团队列表
     *
     * @param departmentId 部门ID
     * @return 团队列表
     */
    @Select(OrganizationSqlConstants.Team.SELECT_BY_DEPARTMENT_ID)
    List<Team> selectByDepartmentId(@Param("departmentId") Long departmentId);

    /**
     * 根据团队类型查询
     *
     * @param type 团队类型
     * @return 团队列表
     */
    @Select(OrganizationSqlConstants.Team.SELECT_BY_TYPE)
    List<Team> selectByType(@Param("type") String type);

    /**
     * 根据负责人ID查询团队列表
     *
     * @param leaderId 负责人ID
     * @return 团队列表
     */
    @Select(OrganizationSqlConstants.Team.SELECT_BY_LEADER_ID)
    List<Team> selectByLeaderId(@Param("leaderId") Long leaderId);

    /**
     * 根据成员ID查询所属团队列表
     *
     * @param memberId 成员ID
     * @return 团队列表
     */
    @Select(OrganizationSqlConstants.Team.SELECT_BY_MEMBER_ID)
    List<Team> selectByMemberId(@Param("memberId") Long memberId);
} 