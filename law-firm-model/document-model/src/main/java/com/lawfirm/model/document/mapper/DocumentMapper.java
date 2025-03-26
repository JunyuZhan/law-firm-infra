package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.entity.base.BaseDocument;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文档数据访问层
 * 完全使用注解方式，不依赖XML映射文件
 */
@Mapper
public interface DocumentMapper extends BaseMapper<BaseDocument> {
    /**
     * 根据文件类型查询文档
     */
    @Select("SELECT * FROM doc_document WHERE file_type = #{fileType} AND deleted = 0")
    List<BaseDocument> selectByFileType(@Param("fileType") String fileType);
    
    /**
     * 根据文档标题模糊查询
     */
    @Select("SELECT * FROM doc_document WHERE title LIKE CONCAT('%', #{keyword}, '%') AND deleted = 0")
    List<BaseDocument> selectByTitleKeyword(@Param("keyword") String keyword);
} 