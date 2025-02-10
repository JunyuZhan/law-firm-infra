package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.DocumentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文档分类Repository接口
 */
@Repository
public interface DocumentCategoryRepository extends JpaRepository<DocumentCategory, Long> {
    
    /**
     * 根据父ID查询子分类
     */
    List<DocumentCategory> findByParentId(Long parentId);
    
    /**
     * 根据编码查询分类
     */
    DocumentCategory findByCode(String code);
    
    /**
     * 检查编码是否存在
     */
    boolean existsByCode(String code);
    
    /**
     * 根据律所ID查询分类列表
     */
    List<DocumentCategory> findByLawFirmId(Long lawFirmId);
    
    /**
     * 根据路径前缀查询分类列表
     */
    List<DocumentCategory> findByPathStartingWith(String pathPrefix);
    
    /**
     * 查询顶级分类
     */
    List<DocumentCategory> findByParentIdIsNull();
    
    /**
     * 查询分类路径
     */
    @Query("SELECT c FROM DocumentCategory c WHERE c.id IN " +
            "(SELECT CAST(TRIM(p) AS long) FROM DocumentCategory d, " +
            "IN (SELECT CAST(TRIM(regexp_split_to_table(d.path, '/')) AS text) p) " +
            "WHERE d.id = :categoryId)")
    List<DocumentCategory> findCategoryPath(Long categoryId);
    
    /**
     * 统计子分类数量
     */
    long countByParentId(Long parentId);
    
    /**
     * 根据层级查询分类
     */
    List<DocumentCategory> findByLevel(Integer level);
} 