package com.lawfirm.model.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.workflow.entity.base.BaseProcess;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程Mapper接口
 * 负责BaseProcess实体的数据访问操作
 */
@Mapper
public interface ProcessMapper extends BaseMapper<BaseProcess> {
    // 可以添加自定义查询方法
} 