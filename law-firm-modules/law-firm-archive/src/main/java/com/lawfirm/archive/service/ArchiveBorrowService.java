package com.lawfirm.archive.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.archive.model.entity.ArchiveBorrow;
import com.lawfirm.archive.model.enums.BorrowStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 档案借阅服务接口
 */
public interface ArchiveBorrowService {

    /**
     * 创建借阅记录
     */
    ArchiveBorrow createBorrow(ArchiveBorrow borrow);

    /**
     * 更新借阅记录
     */
    ArchiveBorrow updateBorrow(ArchiveBorrow borrow);

    /**
     * 获取借阅记录详情
     */
    ArchiveBorrow getBorrowById(Long id);

    /**
     * 获取借阅记录列表（分页）
     */
    Page<ArchiveBorrow> getBorrowPage(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 根据档案ID获取借阅记录
     */
    List<ArchiveBorrow> getBorrowsByArchiveId(Long archiveId);

    /**
     * 根据借阅人获取借阅记录
     */
    List<ArchiveBorrow> getBorrowsByBorrower(String borrower);

    /**
     * 根据状态获取借阅记录
     */
    List<ArchiveBorrow> getBorrowsByStatus(BorrowStatusEnum status);

    /**
     * 审批借阅申请
     */
    void approveBorrow(Long borrowId, String approver, String approvalOpinion, boolean approved);

    /**
     * 归还档案
     */
    void returnArchive(Long borrowId, LocalDateTime actualReturnTime);

    /**
     * 检查是否有逾期未还
     */
    boolean hasOverdueBorrows(String borrower);

    /**
     * 获取逾期未还列表
     */
    List<ArchiveBorrow> getOverdueBorrows();

    /**
     * 统计借阅数量（按状态）
     */
    Long countBorrowsByStatus(BorrowStatusEnum status);

    /**
     * 统计借阅数量（按借阅人）
     */
    Long countBorrowsByBorrower(String borrower);
} 