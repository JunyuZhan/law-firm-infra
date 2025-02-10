package com.lawfirm.model.base.storage.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文件分片信息
 */
@Data
@Accessors(chain = true)
public class ChunkInfo {
    
    /**
     * 当前分片号
     */
    private Integer chunkNumber;
    
    /**
     * 分片大小
     */
    private Long chunkSize;
    
    /**
     * 当前分片大小
     */
    private Long currentChunkSize;
    
    /**
     * 文件总大小
     */
    private Long totalSize;
    
    /**
     * 分片总数
     */
    private Integer totalChunks;
    
    /**
     * 文件标识
     */
    private String identifier;
    
    /**
     * 文件名
     */
    private String filename;
    
    /**
     * 相对路径
     */
    private String relativePath;
    
    /**
     * 文件类型
     */
    private String contentType;
} 