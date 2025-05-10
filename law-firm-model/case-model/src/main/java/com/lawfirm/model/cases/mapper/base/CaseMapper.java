package com.lawfirm.model.cases.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.constants.CaseSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
} 