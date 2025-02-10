package com.lawfirm.model.base.storage.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分片元数据实体
 */
@Data
@Entity
@Table(name = "chunk_metadata", indexes = {
    @Index(name = "idx_identifier_chunk", columnList = "identifier,chunk_number", unique = true),
    @Index(name = "idx_path", columnList = "path", unique = true)
})
public class ChunkMetadataEntity {

    @Id
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 文件标识
     */
    @Column(name = "identifier", nullable = false)
    private String identifier;

    /**
     * 分片号
     */
    @Column(name = "chunk_number", nullable = false)
    private Integer chunkNumber;

    /**
     * 分片大小
     */
    @Column(name = "chunk_size", nullable = false)
    private Long chunkSize;

    /**
     * 当前分片大小
     */
    @Column(name = "current_chunk_size", nullable = false)
    private Long currentChunkSize;

    /**
     * 文件总大小
     */
    @Column(name = "total_size", nullable = false)
    private Long totalSize;

    /**
     * 分片总数
     */
    @Column(name = "total_chunks", nullable = false)
    private Integer totalChunks;

    /**
     * 文件名
     */
    @Column(name = "filename", nullable = false)
    private String filename;

    /**
     * 相对路径
     */
    @Column(name = "relative_path")
    private String relativePath;

    /**
     * 文件类型
     */
    @Column(name = "content_type")
    private String contentType;

    /**
     * 存储路径
     */
    @Column(name = "path", nullable = false)
    private String path;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
} 