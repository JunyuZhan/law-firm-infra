package com.lawfirm.cases.mapper;

import com.lawfirm.common.data.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.CaseStatusLog;
import com.lawfirm.cases.model.dto.CaseStatusLogDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CaseStatusLogMapper extends BaseMapper<CaseStatusLogDTO, CaseStatusLog> {
} 