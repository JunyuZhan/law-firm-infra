package com.lawfirm.model.archive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.archive.entity.ArchiveMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 档案主表Mapper接口
 */
@Mapper
public interface ArchiveMainMapper extends BaseMapper<ArchiveMain> {
    /**
     * 根据档案编号查询档案主表
     */
    @Select("SELECT * FROM archive_main WHERE archive_no = #{archiveNo} AND deleted = 0 LIMIT 1")
    ArchiveMain selectByArchiveNo(@Param("archiveNo") String archiveNo);
} 