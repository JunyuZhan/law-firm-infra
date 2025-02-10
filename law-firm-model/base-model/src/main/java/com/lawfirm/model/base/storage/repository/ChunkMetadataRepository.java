package com.lawfirm.model.base.storage.repository;

import com.lawfirm.model.base.storage.entity.ChunkMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分片元数据仓库接口
 */
@Repository
public interface ChunkMetadataRepository extends JpaRepository<ChunkMetadataEntity, String> {

    /**
     * 根据文件标识查询所有分片
     */
    List<ChunkMetadataEntity> findByIdentifierOrderByChunkNumber(String identifier);

    /**
     * 根据文件标识和分片号查询
     */
    ChunkMetadataEntity findByIdentifierAndChunkNumber(String identifier, Integer chunkNumber);

    /**
     * 根据文件标识删除所有分片
     */
    void deleteByIdentifier(String identifier);
} 