package com.lawfirm.archive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.archive.converter.ArchiveBorrowConverter;
import com.lawfirm.archive.mapper.ArchiveBorrowMapper;
import com.lawfirm.archive.mapper.ArchiveMapper;
import com.lawfirm.archive.model.entity.Archive;
import com.lawfirm.archive.model.entity.ArchiveBorrow;
import com.lawfirm.archive.model.enums.ArchiveStatusEnum;
import com.lawfirm.archive.model.enums.BorrowStatusEnum;
import com.lawfirm.archive.service.ArchiveBorrowService;
import com.lawfirm.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 档案借阅服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArchiveBorrowServiceImpl implements ArchiveBorrowService {

    private final ArchiveBorrowMapper borrowMapper;
    private final ArchiveMapper archiveMapper;
    private final ArchiveBorrowConverter borrowConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArchiveBorrow createBorrow(ArchiveBorrow borrow) {
        // 检查档案是否存在
        Archive archive = archiveMapper.selectById(borrow.getArchiveId());
        if (archive == null) {
            throw new BusinessException("档案不存在");
        }
        
        // 检查档案状态
        if (!ArchiveStatusEnum.IN_STORAGE.getCode().equals(archive.getStatus())) {
            throw new BusinessException("档案当前状态不可借出");
        }
        
        // 检查借阅人是否有逾期未还
        if (hasOverdueBorrows(borrow.getBorrower())) {
            throw new BusinessException("存在逾期未还档案，无法继续借阅");
        }
        
        // 设置借阅状态和时间
        borrow.setStatus(BorrowStatusEnum.BORROWED.getCode());
        borrow.setBorrowTime(LocalDateTime.now());
        
        // 更新档案状态
        archive.setStatus(ArchiveStatusEnum.BORROWED.getCode());
        archive.setCurrentBorrower(borrow.getBorrower());
        archive.setExpectedReturnTime(borrow.getExpectedReturnTime());
        
        // 保存借阅记录和更新档案状态
        borrowMapper.insert(borrow);
        archiveMapper.updateById(archive);
        
        return borrow;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArchiveBorrow updateBorrow(ArchiveBorrow borrow) {
        // 检查借阅记录是否存在
        ArchiveBorrow existingBorrow = getBorrowById(borrow.getId());
        if (existingBorrow == null) {
            throw new BusinessException("借阅记录不存在");
        }
        
        // 检查状态是否允许更新
        if (BorrowStatusEnum.RETURNED.getCode().equals(existingBorrow.getStatus())) {
            throw new BusinessException("已归还的借阅记录不可修改");
        }
        
        borrowMapper.updateById(borrow);
        return borrow;
    }

    @Override
    public ArchiveBorrow getBorrowById(Long id) {
        return borrowMapper.selectById(id);
    }

    @Override
    public Page<ArchiveBorrow> getBorrowPage(Integer pageNum, Integer pageSize, String keyword) {
        LambdaQueryWrapper<ArchiveBorrow> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.like(ArchiveBorrow::getBorrower, keyword);
        }
        return borrowMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public List<ArchiveBorrow> getBorrowsByArchiveId(Long archiveId) {
        LambdaQueryWrapper<ArchiveBorrow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArchiveBorrow::getArchiveId, archiveId)
                .orderByDesc(ArchiveBorrow::getBorrowTime);
        return borrowMapper.selectList(wrapper);
    }

    @Override
    public List<ArchiveBorrow> getBorrowsByBorrower(String borrower) {
        LambdaQueryWrapper<ArchiveBorrow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArchiveBorrow::getBorrower, borrower)
                .orderByDesc(ArchiveBorrow::getBorrowTime);
        return borrowMapper.selectList(wrapper);
    }

    @Override
    public List<ArchiveBorrow> getBorrowsByStatus(BorrowStatusEnum status) {
        LambdaQueryWrapper<ArchiveBorrow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArchiveBorrow::getStatus, status.getCode())
                .orderByDesc(ArchiveBorrow::getBorrowTime);
        return borrowMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveBorrow(Long borrowId, String approver, String approvalOpinion, boolean approved) {
        // 检查借阅记录是否存在
        ArchiveBorrow borrow = getBorrowById(borrowId);
        if (borrow == null) {
            throw new BusinessException("借阅记录不存在");
        }
        
        // 更新审批信息
        borrow.setApprover(approver);
        borrow.setApprovalTime(LocalDateTime.now());
        borrow.setApprovalOpinion(approvalOpinion);
        
        if (approved) {
            borrow.setStatus(BorrowStatusEnum.BORROWED.getCode());
        } else {
            borrow.setStatus(BorrowStatusEnum.RETURNED.getCode());
            // 如果不批准，恢复档案状态
            Archive archive = archiveMapper.selectById(borrow.getArchiveId());
            archive.setStatus(ArchiveStatusEnum.IN_STORAGE.getCode());
            archive.setCurrentBorrower(null);
            archive.setExpectedReturnTime(null);
            archiveMapper.updateById(archive);
        }
        
        borrowMapper.updateById(borrow);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnArchive(Long borrowId, LocalDateTime actualReturnTime) {
        // 检查借阅记录是否存在
        ArchiveBorrow borrow = getBorrowById(borrowId);
        if (borrow == null) {
            throw new BusinessException("借阅记录不存在");
        }
        
        // 检查状态是否为借出
        if (!BorrowStatusEnum.BORROWED.getCode().equals(borrow.getStatus())) {
            throw new BusinessException("当前状态不是借出状态");
        }
        
        // 更新借阅记录
        borrow.setStatus(BorrowStatusEnum.RETURNED.getCode());
        borrow.setActualReturnTime(actualReturnTime);
        
        // 更新档案状态
        Archive archive = archiveMapper.selectById(borrow.getArchiveId());
        archive.setStatus(ArchiveStatusEnum.IN_STORAGE.getCode());
        archive.setCurrentBorrower(null);
        archive.setExpectedReturnTime(null);
        
        borrowMapper.updateById(borrow);
        archiveMapper.updateById(archive);
    }

    @Override
    public boolean hasOverdueBorrows(String borrower) {
        LambdaQueryWrapper<ArchiveBorrow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArchiveBorrow::getBorrower, borrower)
                .eq(ArchiveBorrow::getStatus, BorrowStatusEnum.BORROWED.getCode())
                .lt(ArchiveBorrow::getExpectedReturnTime, LocalDateTime.now());
        return borrowMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<ArchiveBorrow> getOverdueBorrows() {
        LambdaQueryWrapper<ArchiveBorrow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArchiveBorrow::getStatus, BorrowStatusEnum.BORROWED.getCode())
                .lt(ArchiveBorrow::getExpectedReturnTime, LocalDateTime.now())
                .orderByAsc(ArchiveBorrow::getExpectedReturnTime);
        return borrowMapper.selectList(wrapper);
    }

    @Override
    public Long countBorrowsByStatus(BorrowStatusEnum status) {
        LambdaQueryWrapper<ArchiveBorrow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArchiveBorrow::getStatus, status.getCode());
        return borrowMapper.selectCount(wrapper);
    }

    @Override
    public Long countBorrowsByBorrower(String borrower) {
        LambdaQueryWrapper<ArchiveBorrow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArchiveBorrow::getBorrower, borrower);
        return borrowMapper.selectCount(wrapper);
    }
} 