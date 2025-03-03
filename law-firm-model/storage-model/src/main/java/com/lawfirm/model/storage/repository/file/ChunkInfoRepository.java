package com.lawfirm.model.storage.repository.file;

import com.lawfirm.model.storage.entity.file.ChunkInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文件分片信息数据访问接口
 */
@Repository
public interface ChunkInfoRepository extends JpaRepository<ChunkInfo, Long> {
    
    /**
     * 根据文件ID和分片索引查询
     *
     * @param fileId     文件ID
     * @param chunkIndex 分片索引
     * @return 分片信息
     */
    ChunkInfo findByFileIdAndChunkIndex(String fileId, Integer chunkIndex);
    
    /**
     * 根据文件ID查询所有分片，按照分片索引排序
     *
     * @param fileId 文件ID
     * @return 分片信息列表
     */
    List<ChunkInfo> findByFileIdOrderByChunkIndex(String fileId);
    
    /**
     * 根据文件ID查询已上传的分片索引列表
     *
     * @param fileId 文件ID
     * @return 分片索引列表
     */
    @Query("SELECT c.chunkIndex FROM ChunkInfo c WHERE c.fileId = :fileId ORDER BY c.chunkIndex")
    List<Integer> findChunkIndexesByFileId(@Param("fileId") String fileId);
    
    /**
     * 根据文件ID删除所有分片信息
     *
     * @param fileId 文件ID
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM ChunkInfo c WHERE c.fileId = :fileId")
    void deleteByFileId(@Param("fileId") String fileId);
    
    /**
     * 根据文件ID统计分片数量
     *
     * @param fileId 文件ID
     * @return 分片数量
     */
    long countByFileId(String fileId);
} 