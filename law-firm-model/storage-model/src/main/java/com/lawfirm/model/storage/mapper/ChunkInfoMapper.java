package com.lawfirm.model.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.storage.entity.file.ChunkInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 文件分片信息数据访问接口
 */
public interface ChunkInfoMapper extends BaseMapper<ChunkInfo> {
    
    /**
     * 根据文件标识查询分片列表
     *
     * @param fileIdentifier 文件标识
     * @return 分片列表
     */
    @Select("SELECT * FROM storage_chunk_info WHERE file_identifier = #{fileIdentifier} ORDER BY chunk_index")
    List<ChunkInfo> findByFileIdentifier(@Param("fileIdentifier") String fileIdentifier);
    
    /**
     * 根据文件标识和分片索引查询
     *
     * @param fileIdentifier 文件标识
     * @param chunkIndex 分片索引
     * @return 分片信息
     */
    @Select("SELECT * FROM storage_chunk_info WHERE file_identifier = #{fileIdentifier} AND chunk_index = #{chunkIndex} LIMIT 1")
    ChunkInfo findByFileIdentifierAndChunkIndex(@Param("fileIdentifier") String fileIdentifier, @Param("chunkIndex") Integer chunkIndex);
    
    /**
     * 根据文件标识统计分片数量
     *
     * @param fileIdentifier 文件标识
     * @return 分片数量
     */
    @Select("SELECT COUNT(*) FROM storage_chunk_info WHERE file_identifier = #{fileIdentifier}")
    long countByFileIdentifier(@Param("fileIdentifier") String fileIdentifier);
} 