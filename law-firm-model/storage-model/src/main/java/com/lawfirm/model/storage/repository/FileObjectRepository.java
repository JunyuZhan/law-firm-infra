package com.lawfirm.model.storage.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.enums.FileTypeEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 文件对象数据访问接口
 */
@Mapper
public interface FileObjectRepository extends BaseMapper<FileObject> {
    
    /**
     * 根据存储桶ID查询文件列表
     *
     * @param bucketId 存储桶ID
     * @return 文件列表
     */
    @Select("SELECT * FROM file_object WHERE bucket_id = #{bucketId}")
    List<FileObject> findByBucketId(@Param("bucketId") Long bucketId);
    
    /**
     * 根据存储桶ID和文件类型查询文件列表
     *
     * @param bucketId 存储桶ID
     * @param fileType 文件类型
     * @return 文件列表
     */
    @Select("SELECT * FROM file_object WHERE bucket_id = #{bucketId} AND file_type = #{fileType}")
    List<FileObject> findByBucketIdAndFileType(@Param("bucketId") Long bucketId, @Param("fileType") FileTypeEnum fileType);
    
    /**
     * 计算存储桶已用大小
     *
     * @param bucketId 存储桶ID
     * @return 已用大小（字节）
     */
    @Select("SELECT SUM(storage_size) FROM file_object WHERE bucket_id = #{bucketId}")
    Long sumFileSizeByBucketId(@Param("bucketId") Long bucketId);
    
    /**
     * 计算存储桶文件数量
     *
     * @param bucketId 存储桶ID
     * @return 文件数量
     */
    @Select("SELECT COUNT(*) FROM file_object WHERE bucket_id = #{bucketId}")
    long countByBucketId(@Param("bucketId") Long bucketId);
    
    /**
     * 根据MD5查询文件
     *
     * @param md5 文件MD5值
     * @return 文件对象
     */
    @Select("SELECT * FROM file_object WHERE md5 = #{md5} LIMIT 1")
    FileObject findByMd5(@Param("md5") String md5);
    
    /**
     * 根据文件名模糊查询
     *
     * @param fileName 文件名（包含通配符）
     * @return 文件列表
     */
    @Select("SELECT * FROM file_object WHERE file_name LIKE CONCAT('%', #{fileName}, '%')")
    List<FileObject> findByFileNameLike(@Param("fileName") String fileName);
    
    /**
     * 根据ID查询文件对象
     *
     * @param id 文件ID
     * @return 文件对象
     */
    @Select("SELECT * FROM file_object WHERE id = #{id} LIMIT 1")
    FileObject findById(@Param("id") Long id);
}