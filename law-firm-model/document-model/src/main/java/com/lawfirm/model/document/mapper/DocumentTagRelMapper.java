package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.entity.base.DocumentTagRel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文档标签关联Mapper接口
 */
@Mapper
public interface DocumentTagRelMapper extends BaseMapper<DocumentTagRel> {
    // 继承自BaseMapper的基础方法已经满足大部分需求
    // 如需自定义方法，可在此添加
} 