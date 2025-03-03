package com.lawfirm.core.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.workflow.entity.ProcessPermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程权限Mapper接口
 * 基于MyBatis Plus提供流程权限的数据库访问
 *
 * @author cursor
 * @date 2023/03/03
 */
@Mapper
public interface ProcessPermissionMapper extends BaseMapper<ProcessPermission> {
} 