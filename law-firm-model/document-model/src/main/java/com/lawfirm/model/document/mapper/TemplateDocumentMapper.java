package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.entity.template.TemplateDocument;
import org.apache.ibatis.annotations.Mapper;

/**
 * 模板文档数据访问层
 */
@Mapper
public interface TemplateDocumentMapper extends BaseMapper<TemplateDocument> {
    // 继承自BaseMapper的基础方法已经满足大部分需求
    // 如需自定义方法，可在此添加
} 