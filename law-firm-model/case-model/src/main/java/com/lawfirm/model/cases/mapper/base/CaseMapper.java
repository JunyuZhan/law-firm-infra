package com.lawfirm.model.cases.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.constants.CaseSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 案件Mapper接口
 */
@Mapper
public interface CaseMapper extends BaseMapper<Case> {
    
    /**
     * 根据案件编号查询
     *
     * @param caseNo 案件编号
     * @return 案件信息
     */
    @Select(CaseSqlConstants.Case.SELECT_BY_CASE_NO)
    Case selectByCaseNo(@Param("caseNo") String caseNo);
    
    /**
     * 根据客户ID查询案件列表
     *
     * @param clientId 客户ID
     * @return 案件列表
     */
    @Select(CaseSqlConstants.Case.SELECT_BY_CLIENT_ID)
    List<Case> selectByClientId(@Param("clientId") Long clientId);
    
    /**
     * 根据案件状态查询
     *
     * @param status 案件状态
     * @return 案件列表
     */
    @Select(CaseSqlConstants.Case.SELECT_BY_STATUS)
    List<Case> selectByStatus(@Param("status") Integer status);
    
    /**
     * 根据案件类型查询
     *
     * @param type 案件类型
     * @return 案件列表
     */
    @Select(CaseSqlConstants.Case.SELECT_BY_TYPE)
    List<Case> selectByType(@Param("type") Integer type);
    
    /**
     * 查询指定法院的案件
     *
     * @param court 法院名称
     * @return 案件列表
     */
    @Select(CaseSqlConstants.Case.SELECT_BY_COURT)
    List<Case> selectByCourt(@Param("court") String court);

    /**
     * 按律师统计案件数量
     */
    @Select({
        "<script>",
        "SELECT lawyer_id, lawyer_name, COUNT(*) AS case_count",
        "FROM case_info",
        "WHERE deleted = 0",
        "  <if test='start != null'>AND filing_time &gt;= #{start}</if>",
        "  <if test='end != null'>AND filing_time &lt;= #{end}</if>",
        "GROUP BY lawyer_id, lawyer_name",
        "</script>"
    })
    List<Map<String, Object>> countCasesByLawyer(@Param("start") LocalDate start, @Param("end") LocalDate end);

    /**
     * 按律师统计结案数
     */
    @Select({
        "<script>",
        "SELECT lawyer_id, lawyer_name, COUNT(*) AS closed_count",
        "FROM case_info",
        "WHERE deleted = 0 AND case_status = 3",
        "  <if test='start != null'>AND closing_time &gt;= #{start}</if>",
        "  <if test='end != null'>AND closing_time &lt;= #{end}</if>",
        "GROUP BY lawyer_id, lawyer_name",
        "</script>"
    })
    List<Map<String, Object>> countClosedCasesByLawyer(@Param("start") LocalDate start, @Param("end") LocalDate end);

    /**
     * 按案件类型统计案件总数
     */
    @Select({
        "<script>",
        "SELECT case_type, COUNT(*) AS case_count",
        "FROM case_info",
        "WHERE deleted = 0",
        "GROUP BY case_type",
        "</script>"
    })
    List<Map<String, Object>> countCasesByType();

    /**
     * 按案件类型统计结案数（case_status=70为已结案）
     */
    @Select({
        "<script>",
        "SELECT case_type, COUNT(*) AS closed_count",
        "FROM case_info",
        "WHERE deleted = 0 AND case_status = 70",
        "GROUP BY case_type",
        "</script>"
    })
    List<Map<String, Object>> countClosedCasesByType();

    /**
     * 按案件类型统计胜诉数（case_status=70且case_result=1为胜诉）
     */
    @Select({
        "<script>",
        "SELECT case_type, COUNT(*) AS win_count",
        "FROM case_info",
        "WHERE deleted = 0 AND case_status = 70 AND case_result = 1",
        "GROUP BY case_type",
        "</script>"
    })
    List<Map<String, Object>> countWonCasesByType();

    /**
     * 按案件类型统计和解数（case_status=70且case_result=5为和解）
     */
    @Select({
        "<script>",
        "SELECT case_type, COUNT(*) AS settle_count",
        "FROM case_info",
        "WHERE deleted = 0 AND case_status = 70 AND case_result = 5",
        "GROUP BY case_type",
        "</script>"
    })
    List<Map<String, Object>> countSettledCasesByType();

    /**
     * 按案件类型统计败诉数（case_status=70且case_result=3为败诉）
     */
    @Select({
        "<script>",
        "SELECT case_type, COUNT(*) AS lose_count",
        "FROM case_info",
        "WHERE deleted = 0 AND case_status = 70 AND case_result = 3",
        "GROUP BY case_type",
        "</script>"
    })
    List<Map<String, Object>> countLostCasesByType();

    /**
     * 按月统计案件量（立案时间）
     */
    @Select({
        "<script>",
        "SELECT DATE_FORMAT(filing_time, '%Y-%m') AS month, COUNT(*) AS case_count",
        "FROM case_info",
        "WHERE deleted = 0",
        "  <if test='start != null'>AND filing_time &gt;= #{start}</if>",
        "  <if test='end != null'>AND filing_time &lt;= #{end}</if>",
        "GROUP BY month",
        "ORDER BY month ASC",
        "</script>"
    })
    List<Map<String, Object>> countCasesByMonth(@Param("start") LocalDate start, @Param("end") LocalDate end);

    /**
     * 按月统计结案量（结案时间，case_status=70）
     */
    @Select({
        "<script>",
        "SELECT DATE_FORMAT(closing_time, '%Y-%m') AS month, COUNT(*) AS closed_count",
        "FROM case_info",
        "WHERE deleted = 0 AND case_status = 70",
        "  <if test='start != null'>AND closing_time &gt;= #{start}</if>",
        "  <if test='end != null'>AND closing_time &lt;= #{end}</if>",
        "GROUP BY month",
        "ORDER BY month ASC",
        "</script>"
    })
    List<Map<String, Object>> countClosedCasesByMonth(@Param("start") LocalDate start, @Param("end") LocalDate end);

    /**
     * 按主办律师统计案件总数
     */
    @Select({
        "<script>",
        "SELECT lawyer_id, lawyer_name, COUNT(*) AS case_count",
        "FROM case_info",
        "WHERE deleted = 0",
        "GROUP BY lawyer_id, lawyer_name",
        "</script>"
    })
    List<Map<String, Object>> countCasesByLawyerAll();

    /**
     * 按主办律师统计结案数（case_status=70为已结案）
     */
    @Select({
        "<script>",
        "SELECT lawyer_id, lawyer_name, COUNT(*) AS closed_count",
        "FROM case_info",
        "WHERE deleted = 0 AND case_status = 70",
        "GROUP BY lawyer_id, lawyer_name",
        "</script>"
    })
    List<Map<String, Object>> countClosedCasesByLawyerAll();

    /**
     * 按客户统计案件数
     */
    @Select({
        "<script>",
        "SELECT client_id, client_name, COUNT(*) AS case_count",
        "FROM case_info",
        "WHERE deleted = 0",
        "GROUP BY client_id, client_name",
        "</script>"
    })
    List<Map<String, Object>> countCasesByClient();

    /**
     * 按客户统计案件总金额（actual_amount）
     */
    @Select({
        "<script>",
        "SELECT client_id, client_name, SUM(actual_amount) AS total_amount",
        "FROM case_info",
        "WHERE deleted = 0",
        "GROUP BY client_id, client_name",
        "</script>"
    })
    List<Map<String, Object>> sumCaseAmountByClient();

    /**
     * 按团队统计主办律师案件数
     */
    @Select({
        "<script>",
        "SELECT lawyer_id, lawyer_name, COUNT(*) AS case_count",
        "FROM case_info",
        "WHERE deleted = 0 AND team_id = #{teamId}",
        "GROUP BY lawyer_id, lawyer_name",
        "</script>"
    })
    List<Map<String, Object>> countCasesByLawyerInTeam(@Param("teamId") Long teamId);

    /**
     * 按部门统计主办律师案件数
     */
    @Select({
        "<script>",
        "SELECT lawyer_id, lawyer_name, COUNT(*) AS case_count",
        "FROM case_info",
        "WHERE deleted = 0 AND department_id = #{departmentId}",
        "GROUP BY lawyer_id, lawyer_name",
        "</script>"
    })
    List<Map<String, Object>> countCasesByLawyerInDepartment(@Param("departmentId") Long departmentId);
} 