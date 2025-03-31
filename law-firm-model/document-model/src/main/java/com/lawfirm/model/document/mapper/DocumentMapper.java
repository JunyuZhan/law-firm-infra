package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.constant.DocumentSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文档数据访问层
 * 注解方式实现SQL映射，SQL语句集中到常量类管理
 */
@Mapper
public interface DocumentMapper extends BaseMapper<BaseDocument> {
    /**
     * 根据文件类型查询文档
     * 
     * @param fileType 文件类型
     * @return 文档列表
     */
    @Select(DocumentSqlConstants.SELECT_BY_FILE_TYPE)
    List<BaseDocument> selectByFileType(@Param("fileType") String fileType);
    
    /**
     * 根据文档标题模糊查询
     * 
     * @param keyword 关键词(需预先过滤特殊字符，防止SQL注入)
     * @return 文档列表
     */
    @Select(DocumentSqlConstants.SELECT_BY_TITLE_KEYWORD)
    List<BaseDocument> selectByTitleKeyword(@Param("keyword") String keyword);
} 