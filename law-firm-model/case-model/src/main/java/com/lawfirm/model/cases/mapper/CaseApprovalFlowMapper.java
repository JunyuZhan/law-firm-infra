package com.lawfirm.model.cases.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.business.CaseApprovalFlow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("caseApprovalFlowMapper")
public interface CaseApprovalFlowMapper extends BaseMapper<CaseApprovalFlow> {
} 