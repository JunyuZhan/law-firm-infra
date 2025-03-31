package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.entity.base.DocumentTag;
import com.lawfirm.model.document.constant.DocumentSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 文档标签数据访问层
 */
@Mapper
public interface DocumentTagMapper extends BaseMapper<DocumentTag> {
    // 继承自BaseMapper的基础方法已经满足大部分需求
    // 如需自定义方法，可在此添加
    
    /**
     * 根据标签名称查询
     *
     * @param tagName 标签名称
     * @return 标签信息
     */
    @Select(DocumentSqlConstants.Tag.SELECT_BY_NAME)
    DocumentTag selectByName(@Param("tagName") String tagName);
    
    /**
     * 查询热门标签
     *
     * @param limit 数量限制
     * @return 热门标签列表
     */
    @Select(DocumentSqlConstants.Tag.SELECT_HOT_TAGS)
    List<DocumentTag> selectHotTags(@Param("limit") Integer limit);
} 