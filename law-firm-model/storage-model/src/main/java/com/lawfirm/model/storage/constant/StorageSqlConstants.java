package com.lawfirm.model.storage.constant;

/**
 * 存储模块SQL常量类
 * 集中管理存储相关SQL查询语句，提高可维护性
 */
public class StorageSqlConstants {

    /**
     * 根据桶名称统计数量
     */
    public static final String COUNT_BY_BUCKET_NAME = 
            "SELECT COUNT(*) FROM storage_bucket WHERE bucket_name = #{bucketName}";
    
    /**
     * 根据桶名称查询
     */
    public static final String FIND_BY_BUCKET_NAME = 
            "SELECT * FROM storage_bucket WHERE bucket_name = #{bucketName} LIMIT 1";
    
    /**
     * 根据存储类型查询列表
     */
    public static final String FIND_BY_STORAGE_TYPE = 
            "SELECT * FROM storage_bucket WHERE storage_type = #{storageType} AND status = 1";
    
    /**
     * 根据桶ID查询总文件大小
     */
    public static final String SUM_FILE_SIZE_BY_BUCKET_ID = 
            "SELECT SUM(file_size) FROM file_object WHERE bucket_id = #{bucketId}";
    
    /**
     * 根据桶ID查询
     */
    public static final String FIND_BY_ID = 
            "SELECT * FROM storage_bucket WHERE id = #{id} LIMIT 1";
    
    /**
     * 文件对象相关SQL
     */
    public static class FileObject {
        /**
         * 根据文件标识查询
         */
        public static final String FIND_BY_IDENTIFIER = 
                "SELECT * FROM file_object WHERE identifier = #{identifier} LIMIT 1";
        
        /**
         * 根据文件标识和桶ID查询
         */
        public static final String FIND_BY_IDENTIFIER_AND_BUCKET_ID = 
                "SELECT * FROM file_object WHERE identifier = #{identifier} AND bucket_id = #{bucketId} LIMIT 1";
        
        /**
         * 根据桶ID查询文件列表
         */
        public static final String FIND_BY_BUCKET_ID = 
                "SELECT * FROM file_object WHERE bucket_id = #{bucketId}";
        
        /**
         * 根据桶ID和文件路径查询
         */
        public static final String FIND_BY_BUCKET_ID_AND_FILE_PATH = 
                "SELECT * FROM file_object WHERE bucket_id = #{bucketId} AND file_path = #{filePath} LIMIT 1";
        
        /**
         * 更新文件状态
         */
        public static final String UPDATE_STATUS = 
                "UPDATE file_object SET status = #{status} WHERE id = #{id}";
        
        /**
         * 更新文件大小
         */
        public static final String UPDATE_FILE_SIZE = 
                "UPDATE file_object SET file_size = #{fileSize} WHERE id = #{id}";
    }
    
    /**
     * 分片信息相关SQL
     */
    public static class ChunkInfo {
        /**
         * 根据文件标识查询分片列表
         */
        public static final String FIND_BY_FILE_IDENTIFIER = 
                "SELECT * FROM storage_chunk_info WHERE file_identifier = #{fileIdentifier} ORDER BY chunk_index";
        
        /**
         * 根据文件标识和分片索引查询
         */
        public static final String FIND_BY_FILE_IDENTIFIER_AND_CHUNK_INDEX = 
                "SELECT * FROM storage_chunk_info WHERE file_identifier = #{fileIdentifier} AND chunk_index = #{chunkIndex} LIMIT 1";
        
        /**
         * 统计文件分片数量
         */
        public static final String COUNT_BY_FILE_IDENTIFIER = 
                "SELECT COUNT(*) FROM storage_chunk_info WHERE file_identifier = #{fileIdentifier}";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private StorageSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 