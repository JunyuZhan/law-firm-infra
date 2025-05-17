package com.lawfirm.model.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.storage.entity.file.ChunkInfo;
import com.lawfirm.model.storage.constant.StorageSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 文件分片信息数据访问接口
 */
@Mapper
public interface ChunkInfoMapper extends BaseMapper<ChunkInfo> {
    
    /**
     * 根据文件标识查询分片列表
     *
     * @param fileIdentifier 文件标识
     * @return 分片信息列表
     */
    @Select(StorageSqlConstants.ChunkInfo.FIND_BY_FILE_IDENTIFIER)
    List<ChunkInfo> findByFileIdentifier(@Param("fileIdentifier") String fileIdentifier);
    
    /**
     * 根据文件标识和分片索引查询
     *
     * @param fileIdentifier 文件标识
     * @param chunkIndex 分片索引
     * @return 分片信息
     */
    @Select(StorageSqlConstants.ChunkInfo.FIND_BY_FILE_IDENTIFIER_AND_CHUNK_INDEX)
    ChunkInfo findByFileIdentifierAndChunkIndex(@Param("fileIdentifier") String fileIdentifier, @Param("chunkIndex") Integer chunkIndex);
    
    /**
     * 统计文件分片数量
     *
     * @param fileIdentifier 文件标识
     * @return 分片数量
     */
    @Select(StorageSqlConstants.ChunkInfo.COUNT_BY_FILE_IDENTIFIER)
    int countByFileIdentifier(@Param("fileIdentifier") String fileIdentifier);
} 