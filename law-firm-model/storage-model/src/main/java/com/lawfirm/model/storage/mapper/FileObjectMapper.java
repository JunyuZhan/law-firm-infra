package com.lawfirm.model.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.storage.entity.file.FileObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 文件对象数据访问接口
 */
public interface FileObjectMapper extends BaseMapper<FileObject> {
    
    /**
     * 根据文件标识查询
     *
     * @param identifier 文件标识
     * @return 文件对象
     */
    @Select("SELECT * FROM file_object WHERE identifier = #{identifier} LIMIT 1")
    FileObject findByIdentifier(@Param("identifier") String identifier);
    
    /**
     * 根据文件标识和桶ID查询
     *
     * @param identifier 文件标识
     * @param bucketId 桶ID
     * @return 文件对象
     */
    @Select("SELECT * FROM file_object WHERE identifier = #{identifier} AND bucket_id = #{bucketId} LIMIT 1")
    FileObject findByIdentifierAndBucketId(@Param("identifier") String identifier, @Param("bucketId") Long bucketId);
    
    /**
     * 根据桶ID查询文件列表
     *
     * @param bucketId 桶ID
     * @return 文件对象列表
     */
    @Select("SELECT * FROM file_object WHERE bucket_id = #{bucketId}")
    List<FileObject> findByBucketId(@Param("bucketId") Long bucketId);
    
    /**
     * 根据桶ID和文件路径查询
     *
     * @param bucketId 桶ID
     * @param filePath 文件路径
     * @return 文件对象
     */
    @Select("SELECT * FROM file_object WHERE bucket_id = #{bucketId} AND file_path = #{filePath} LIMIT 1")
    FileObject findByBucketIdAndFilePath(@Param("bucketId") Long bucketId, @Param("filePath") String filePath);
    
    /**
     * 更新文件状态
     *
     * @param id 文件ID
     * @param status 状态
     * @return 更新结果
     */
    @Update("UPDATE file_object SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 更新文件大小
     *
     * @param id 文件ID
     * @param fileSize 文件大小
     * @return 更新结果
     */
    @Update("UPDATE file_object SET file_size = #{fileSize} WHERE id = #{id}")
    int updateFileSize(@Param("id") Long id, @Param("fileSize") Long fileSize);
} 