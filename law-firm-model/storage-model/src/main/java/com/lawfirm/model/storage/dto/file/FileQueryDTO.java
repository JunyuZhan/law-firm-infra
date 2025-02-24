package com.lawfirm.model.storage.dto.file;

import com.lawfirm.model.base.query.BaseQuery;
import com.lawfirm.model.storage.enums.FileTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文件查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FileQueryDTO extends BaseQuery {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private FileTypeEnum fileType;

    /**
     * 存储桶ID
     */
    private Long bucketId;

    /**
     * 文件标签
     */
    private String tag;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
} 