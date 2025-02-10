package com.lawfirm.cases.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.cases.model.entity.CaseApproval;
import org.apache.ibatis.annotations.Mapper;

/**
 * 案件立案审批Mapper接口
 */
@Mapper
public interface CaseApprovalMapper extends BaseMapper<CaseApproval> {
} 