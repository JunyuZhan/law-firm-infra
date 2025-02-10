package com.lawfirm.model.base.storage.service;

import com.lawfirm.model.base.storage.model.ChunkInfo;
import com.lawfirm.model.base.storage.model.FileMetadata;

/**
 * 分片上传服务接口
 */
public interface ChunkedStorageService {
    
    /**
     * 检查分片是否存在
     *
     * @param chunkInfo 分片信息
     * @return 是否存在
     */
    boolean checkChunk(ChunkInfo chunkInfo);
    
    /**
     * 上传分片
     *
     * @param chunkInfo 分片信息
     * @param bytes 分片数据
     * @return 是否上传成功
     */
    boolean uploadChunk(ChunkInfo chunkInfo, byte[] bytes);
    
    /**
     * 合并分片
     *
     * @param chunkInfo 分片信息
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 文件元数据
     */
    FileMetadata mergeChunks(ChunkInfo chunkInfo, String businessType, String businessId);
    
    /**
     * 清理分片
     *
     * @param identifier 文件标识
     */
    void cleanChunks(String identifier);
} 