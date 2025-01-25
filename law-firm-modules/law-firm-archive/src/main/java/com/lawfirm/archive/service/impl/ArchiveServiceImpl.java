package com.lawfirm.archive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.archive.converter.ArchiveConverter;
import com.lawfirm.archive.mapper.ArchiveMapper;
import com.lawfirm.archive.model.entity.Archive;
import com.lawfirm.archive.model.enums.ArchiveStatusEnum;
import com.lawfirm.archive.service.ArchiveService;
import com.lawfirm.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 档案服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArchiveServiceImpl extends ServiceImpl<ArchiveMapper, Archive> implements ArchiveService {

    private final ArchiveMapper archiveMapper;
    private final ArchiveConverter archiveConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Archive createArchive(Archive archive) {
        // 检查档案编号是否存在
        if (checkArchiveNumberExists(archive.getArchiveNumber())) {
            throw new BusinessException("档案编号已存在");
        }
        
        // 设置初始状态
        archive.setStatus(ArchiveStatusEnum.IN_STORAGE.getCode());
        archive.setFilingTime(LocalDateTime.now());
        
        // 保存档案
        archiveMapper.insert(archive);
        return archive;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Archive updateArchive(Archive archive) {
        // 检查档案是否存在
        Archive existingArchive = getArchiveById(archive.getId());
        if (existingArchive == null) {
            throw new BusinessException("档案不存在");
        }
        
        // 检查档案状态
        if (ArchiveStatusEnum.DESTROYED.getCode().equals(existingArchive.getStatus())) {
            throw new BusinessException("档案已销毁，无法修改");
        }
        
        // 更新档案
        archiveMapper.updateById(archive);
        return archive;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArchive(Long id) {
        // 检查档案是否存在
        Archive archive = getArchiveById(id);
        if (archive == null) {
            throw new BusinessException("档案不存在");
        }
        
        // 检查档案状态
        if (ArchiveStatusEnum.BORROWED.getCode().equals(archive.getStatus())) {
            throw new BusinessException("档案已借出，无法删除");
        }
        
        // 删除档案
        archiveMapper.deleteById(id);
    }

    @Override
    public Archive getArchiveById(Long id) {
        return archiveMapper.selectById(id);
    }

    @Override
    public Page<Archive> getArchivePage(Integer pageNum, Integer pageSize, String keyword) {
        // 构建查询条件
        LambdaQueryWrapper<Archive> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.like(Archive::getArchiveNumber, keyword)
                    .or()
                    .like(Archive::getArchiveName, keyword);
        }
        
        // 执行分页查询
        return archiveMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public List<Archive> getArchivesByStatus(ArchiveStatusEnum status) {
        LambdaQueryWrapper<Archive> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Archive::getStatus, status.getCode());
        return archiveMapper.selectList(wrapper);
    }

    @Override
    public List<Archive> getArchivesByCategory(Long categoryId) {
        LambdaQueryWrapper<Archive> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Archive::getCategoryId, categoryId);
        return archiveMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void borrowArchive(Long archiveId, String borrower, LocalDateTime expectedReturnTime, String borrowReason) {
        // 检查档案是否存在
        Archive archive = getArchiveById(archiveId);
        if (archive == null) {
            throw new BusinessException("档案不存在");
        }
        
        // 检查档案状态
        if (!ArchiveStatusEnum.IN_STORAGE.getCode().equals(archive.getStatus())) {
            throw new BusinessException("档案当前状态不可借出");
        }
        
        // 更新档案状态
        archive.setStatus(ArchiveStatusEnum.BORROWED.getCode());
        archive.setCurrentBorrower(borrower);
        archive.setExpectedReturnTime(expectedReturnTime);
        
        archiveMapper.updateById(archive);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnArchive(Long archiveId) {
        // 检查档案是否存在
        Archive archive = getArchiveById(archiveId);
        if (archive == null) {
            throw new BusinessException("档案不存在");
        }
        
        // 检查档案状态
        if (!ArchiveStatusEnum.BORROWED.getCode().equals(archive.getStatus())) {
            throw new BusinessException("档案当前状态不是借出状态");
        }
        
        // 更新档案状态
        archive.setStatus(ArchiveStatusEnum.IN_STORAGE.getCode());
        archive.setCurrentBorrower(null);
        archive.setExpectedReturnTime(null);
        
        archiveMapper.updateById(archive);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void destroyArchive(Long archiveId, String operator, String reason) {
        // 检查档案是否存在
        Archive archive = getArchiveById(archiveId);
        if (archive == null) {
            throw new BusinessException("档案不存在");
        }
        
        // 检查档案状态
        if (ArchiveStatusEnum.BORROWED.getCode().equals(archive.getStatus())) {
            throw new BusinessException("档案已借出，无法销毁");
        }
        
        // 更新档案状态
        archive.setStatus(ArchiveStatusEnum.DESTROYED.getCode());
        archive.setRemarks(String.format("销毁原因：%s；操作人：%s；销毁时间：%s", 
                reason, operator, LocalDateTime.now()));
        
        archiveMapper.updateById(archive);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArchiveStatus(Long archiveId, ArchiveStatusEnum status) {
        // 检查档案是否存在
        Archive archive = getArchiveById(archiveId);
        if (archive == null) {
            throw new BusinessException("档案不存在");
        }
        
        // 更新档案状态
        archive.setStatus(status.getCode());
        archiveMapper.updateById(archive);
    }

    @Override
    public Long countArchivesByStatus(ArchiveStatusEnum status) {
        LambdaQueryWrapper<Archive> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Archive::getStatus, status.getCode());
        return archiveMapper.selectCount(wrapper);
    }

    @Override
    public Long countArchivesByCategory(Long categoryId) {
        LambdaQueryWrapper<Archive> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Archive::getCategoryId, categoryId);
        return archiveMapper.selectCount(wrapper);
    }

    @Override
    public Archive getByArchiveNumber(String archiveNumber) {
        return lambdaQuery()
                .eq(Archive::getArchiveNumber, archiveNumber)
                .one();
    }

    @Override
    public boolean checkArchiveNumberExists(String archiveNumber) {
        return lambdaQuery()
                .eq(Archive::getArchiveNumber, archiveNumber)
                .exists();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void archive(Long id, String filingPerson) {
        Archive archive = getById(id);
        if (archive == null) {
            throw new BusinessException("档案不存在");
        }
        
        archive.setStatus(ArchiveStatusEnum.IN_STORAGE.name());
        archive.setFilingPerson(filingPerson);
        archive.setFilingTime(LocalDateTime.now());
        updateById(archive);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void destroy(Long id, String operator) {
        Archive archive = getById(id);
        if (archive == null) {
            throw new BusinessException("档案不存在");
        }
        if (!ArchiveStatusEnum.IN_STORAGE.name().equals(archive.getStatus())) {
            throw new BusinessException("只有在库的档案才能销毁");
        }
        
        archive.setStatus(ArchiveStatusEnum.DESTROYED.name());
        updateById(archive);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lend(Long id, String borrower, LocalDateTime expectedReturnTime) {
        Archive archive = getById(id);
        if (archive == null) {
            throw new BusinessException("档案不存在");
        }
        if (!ArchiveStatusEnum.IN_STORAGE.name().equals(archive.getStatus())) {
            throw new BusinessException("只有在库的档案才能借出");
        }
        
        archive.setStatus(ArchiveStatusEnum.BORROWED.name());
        archive.setCurrentBorrower(borrower);
        archive.setExpectedReturnTime(expectedReturnTime);
        updateById(archive);
    }
} 