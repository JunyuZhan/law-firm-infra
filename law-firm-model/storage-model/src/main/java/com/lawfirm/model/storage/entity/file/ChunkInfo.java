package com.lawfirm.model.storage.entity.file;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.storage.entity.base.BaseStorage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件分片信息实体
 */
@Data
@TableName("storage_chunk_info")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ChunkInfo extends BaseStorage {

    private static final long serialVersionUID = 1L;

    /**
     * 文件唯一标识
     */
    @TableField("file_id")
    private String fileId;
    
    /**
     * 文件名
     */
    @TableField("filename")
    private String filename;

    /**
     * 分片索引，从0开始
     */
    @TableField("chunk_index")
    private Integer chunkIndex;

    /**
     * 总分片数
     */
    @TableField("total_chunks")
    private Integer totalChunks;

    /**
     * 分片大小（字节）
     */
    @TableField("chunk_size")
    private Long chunkSize;

    /**
     * 分片文件存储路径
     */
    @TableField("chunk_path")
    private String chunkPath;
    
    /**
     * 分片MD5校验码
     */
    @TableField("md5")
    private String md5;
} 