package com.lawfirm.cases.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.cases.model.entity.CaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 案件信息Mapper接口
 */
@Mapper
public interface CaseInfoMapper extends BaseMapper<CaseInfo> {
    
    /**
     * 根据案件编号查询案件信息
     */
    @Select("SELECT * FROM case_info WHERE case_number = #{caseNumber} AND deleted = 0")
    CaseInfo selectByCaseNumber(String caseNumber);
    
    /**
     * 根据委托人ID查询案件列表
     */
    @Select("SELECT * FROM case_info WHERE client_id = #{clientId} AND deleted = 0")
    List<CaseInfo> selectByClientId(Long clientId);
    
    /**
     * 根据主办律师ID查询案件列表
     */
    @Select("SELECT * FROM case_info WHERE lawyer_id = #{lawyerId} AND deleted = 0")
    List<CaseInfo> selectByLawyerId(Long lawyerId);
    
    /**
     * 根据案件状态查询案件列表
     */
    @Select("SELECT * FROM case_info WHERE case_status = #{status} AND deleted = 0")
    List<CaseInfo> selectByStatus(String status);
} 