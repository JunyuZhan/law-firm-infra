package com.lawfirm.model.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.log.entity.audit.AuditRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 审计记录Mapper接口
 */
@Mapper
public interface AuditRecordMapper extends BaseMapper<AuditRecord> {
} 