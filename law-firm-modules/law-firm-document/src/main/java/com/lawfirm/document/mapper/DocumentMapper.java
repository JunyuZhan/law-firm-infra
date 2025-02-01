package com.lawfirm.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.base.enums.StatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DocumentMapper extends BaseMapper<Document> {
    
    @Select("SELECT * FROM t_document WHERE status = #{status}")
    List<Document> selectByStatus(@Param("status") StatusEnum status);
    
    @Select("SELECT * FROM t_document WHERE document_number = #{documentNumber}")
    List<Document> selectByDocumentNumber(@Param("documentNumber") String documentNumber);
} 