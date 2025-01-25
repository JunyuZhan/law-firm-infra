package com.lawfirm.archive.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.archive.model.entity.Archive;
import com.lawfirm.archive.model.enums.ArchiveStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 档案服务接口
 */
public interface ArchiveService extends IService<Archive> {

    /**
     * 创建档案
     */
    Archive createArchive(Archive archive);

    /**
     * 更新档案
     */
    Archive updateArchive(Archive archive);

    /**
     * 删除档案
     */
    void deleteArchive(Long id);

    /**
     * 获取档案详情
     */
    Archive getArchiveById(Long id);

    /**
     * 获取档案列表（分页）
     */
    Page<Archive> getArchivePage(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 根据状态查询档案
     */
    List<Archive> getArchivesByStatus(ArchiveStatusEnum status);

    /**
     * 根据分类查询档案
     */
    List<Archive> getArchivesByCategory(Long categoryId);

    /**
     * 档案借出
     */
    void borrowArchive(Long archiveId, String borrower, LocalDateTime expectedReturnTime, String borrowReason);

    /**
     * 档案归还
     */
    void returnArchive(Long archiveId);

    /**
     * 档案销毁
     */
    void destroyArchive(Long archiveId, String operator, String reason);

    /**
     * 更新档案状态
     */
    void updateArchiveStatus(Long archiveId, ArchiveStatusEnum status);

    /**
     * 统计档案数量（按状态）
     */
    Long countArchivesByStatus(ArchiveStatusEnum status);

    /**
     * 统计档案数量（按分类）
     */
    Long countArchivesByCategory(Long categoryId);

    /**
     * 根据档案编号获取档案
     */
    Archive getByArchiveNumber(String archiveNumber);

    /**
     * 检查档案编号是否存在
     */
    boolean checkArchiveNumberExists(String archiveNumber);

    /**
     * 归档
     */
    void archive(Long id, String filingPerson);

    /**
     * 销毁
     */
    void destroy(Long id, String operator);

    /**
     * 借出
     */
    void lend(Long id, String borrower, LocalDateTime expectedReturnTime);
} 