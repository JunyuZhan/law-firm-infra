package com.lawfirm.model.base.storage.repository;

import com.lawfirm.model.base.storage.entity.FileMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件元数据仓库接口
 */
@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadataEntity, String> {
    
    /**
     * 根据业务类型和业务ID查询文件列表
     */
    List<FileMetadataEntity> findByBusinessTypeAndBusinessId(String businessType, String businessId);
    
    /**
     * 根据存储路径查询文件
     */
    FileMetadataEntity findByPath(String path);
    
    /**
     * 根据业务类型和业务ID删除文件
     */
    void deleteByBusinessTypeAndBusinessId(String businessType, String businessId);
}