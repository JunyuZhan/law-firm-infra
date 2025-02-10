package com.lawfirm.model.base.storage.service;

import com.lawfirm.model.base.storage.entity.ChunkMetadataEntity;
import com.lawfirm.model.base.storage.entity.FileMetadataEntity;

/**
 * 业务分片存储服务接口
 */
public interface BusinessChunkedStorageService {

    /**
     * 检查分片是否存在
     *
     * @param identifier 文件标识
     * @param chunkNumber 分片号
     * @return 是否存在
     */
    boolean checkChunk(String identifier, Integer chunkNumber);

    /**
     * 上传分片
     *
     * @param chunkMetadata 分片元数据
     * @param bytes 分片数据
     * @return 是否上传成功
     */
    boolean uploadChunk(ChunkMetadataEntity chunkMetadata, byte[] bytes);

    /**
     * 合并分片
     *
     * @param identifier 文件标识
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 文件元数据
     */
    FileMetadataEntity mergeChunks(String identifier, String businessType, String businessId);

    /**
     * 清理分片
     *
     * @param identifier 文件标识
     */
    void cleanChunks(String identifier);
} 