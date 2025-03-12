package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.entity.base.DocumentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文档信息数据访问层
 */
@Mapper
public interface DocumentInfoMapper extends BaseMapper<DocumentInfo> {
    // 继承自BaseMapper的基础方法已经满足大部分需求
    // 如需自定义方法，可在此添加
} 