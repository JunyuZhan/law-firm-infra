package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文档Repository接口
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {
    
    /**
     * 根据分类ID查询文档列表
     */
    List<Document> findByCategoryId(Long categoryId);
    
    /**
     * 根据案件ID查询文档列表
     */
    List<Document> findByCaseId(Long caseId);
    
    /**
     * 根据合同ID查询文档列表
     */
    List<Document> findByContractId(Long contractId);
    
    /**
     * 根据客户ID查询文档列表
     */
    List<Document> findByClientId(Long clientId);
    
    /**
     * 根据文档编号查询文档
     */
    Document findByDocumentNumber(String documentNumber);
    
    /**
     * 根据律所ID查询文档列表
     */
    List<Document> findByLawFirmId(Long lawFirmId);
    
    /**
     * 根据部门ID查询文档列表
     */
    List<Document> findByDepartmentId(String departmentId);
    
    /**
     * 全文检索
     */
    @Query(value = "SELECT d FROM Document d WHERE " +
            "LOWER(d.documentName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.keywords) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Document> searchDocuments(String keyword);
    
    /**
     * 查询需要归档的文档
     */
    @Query("SELECT d FROM Document d WHERE d.needArchive = true AND d.archiveTime IS NULL")
    List<Document> findNeedArchiveDocuments();
} 