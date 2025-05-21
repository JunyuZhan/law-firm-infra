package com.lawfirm.archive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.archive.dto.ArchiveFileDTO;
import com.lawfirm.model.archive.dto.CaseArchiveDTO;
import com.lawfirm.model.archive.entity.ArchiveFile;
import com.lawfirm.model.archive.entity.ArchiveMain;
import com.lawfirm.model.archive.entity.CaseArchive;
import com.lawfirm.model.archive.enums.ArchiveStatusEnum;
import com.lawfirm.model.archive.mapper.ArchiveFileMapper;
import com.lawfirm.model.archive.mapper.ArchiveMainMapper;
import com.lawfirm.model.archive.mapper.CaseArchiveMapper;
import com.lawfirm.model.archive.service.ArchiveService;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.workflow.service.ProcessService;
import com.lawfirm.model.workflow.service.TaskService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 档案服务实现类
 */
@Service
@Slf4j
public class ArchiveServiceImpl extends ServiceImpl<ArchiveMainMapper, ArchiveMain>
        implements ArchiveService {

    @Autowired
    private CaseArchiveMapper caseArchiveMapper;
    
    @Autowired
    private ArchiveFileMapper archiveFileMapper;

    // Core层服务注入
    @Autowired
    @Qualifier("storageFileServiceImpl")
    private FileService fileService;
    
    @Autowired
    @Qualifier("storageBucketServiceImpl")
    private BucketService bucketService;
    
    @Autowired
    @Qualifier("searchServiceImpl")
    private SearchService searchService;
    
    @Autowired
    @Qualifier("coreAuditServiceImpl")
    private AuditService auditService;
    
    @Autowired
    @Qualifier("messageSender")
    private MessageSender messageSender;
    
    @Autowired
    @Qualifier("coreProcessServiceImpl")
    private ProcessService processService;
    
    @Autowired
    @Qualifier("taskServiceImpl")
    private TaskService taskService;

    /**
     * 实现BaseService中的exists抽象方法
     *
     * @param wrapper 查询条件
     * @return 是否存在
     */
    @Override
    public boolean exists(QueryWrapper<ArchiveMain> wrapper) {
        return baseMapper.selectCount(wrapper) > 0;
    }

    /**
     * 批量保存实体
     */
    @Override
    public boolean saveBatch(List<ArchiveMain> entities) {
        return super.saveBatch(entities);
    }
    
    /**
     * 保存实体
     */
    @Override
    public boolean save(ArchiveMain entity) {
        return super.save(entity);
    }
    
    /**
     * 更新实体
     */
    @Override
    public boolean update(ArchiveMain entity) {
        return super.updateById(entity);
    }
    
    /**
     * 批量更新实体
     */
    @Override
    public boolean updateBatch(List<ArchiveMain> entities) {
        return super.updateBatchById(entities);
    }
    
    /**
     * 删除实体
     */
    @Override
    public boolean remove(Long id) {
        return super.removeById(id);
    }
    
    /**
     * 批量删除实体
     */
    @Override
    public boolean removeBatch(List<Long> ids) {
        return super.removeByIds(ids);
    }
    
    /**
     * 根据ID查询
     */
    @Override
    public ArchiveMain getById(Long id) {
        return super.getById(id);
    }
    
    /**
     * 查询列表
     */
    @Override
    public List<ArchiveMain> list(QueryWrapper<ArchiveMain> wrapper) {
        return super.list(wrapper);
    }
    
    /**
     * 分页查询
     */
    @Override
    public Page<ArchiveMain> page(Page<ArchiveMain> page, QueryWrapper<ArchiveMain> wrapper) {
        return super.page(page, wrapper);
    }
    
    /**
     * 查询总数
     */
    @Override
    public long count(QueryWrapper<ArchiveMain> wrapper) {
        return super.count(wrapper);
    }
    
    /**
     * 获取当前租户ID
     */
    @Override
    public Long getCurrentTenantId() {
        // 从SecurityContext中获取租户ID，具体实现根据项目情况调整
        return null;
    }
    
    /**
     * 获取当前用户ID
     */
    @Override
    public Long getCurrentUserId() {
        return SecurityUtils.getUserId();
    }
    
    /**
     * 获取当前用户名
     */
    @Override
    public String getCurrentUsername() {
        return SecurityUtils.getUsername();
    }

    /**
     * 创建案件档案
     *
     * @param caseArchiveDTO 案件档案DTO
     * @return 案件档案ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createCaseArchive(CaseArchiveDTO caseArchiveDTO) {
        log.info("创建案件档案，caseId: {}", caseArchiveDTO.getCaseId());
        
        // 检查是否已存在归档记录
        CaseArchive existingArchive = caseArchiveMapper.selectOne(
                new QueryWrapper<CaseArchive>().eq("case_id", caseArchiveDTO.getCaseId())
        );
        
        if (existingArchive != null) {
            log.warn("案件 {} 已归档，无需重复操作", caseArchiveDTO.getCaseId());
            return String.valueOf(existingArchive.getId());
        }
        
        // 创建案件档案实体
        CaseArchive caseArchive = new CaseArchive();
        caseArchive.setId(null); // 使用数据库自增或雪花算法
        caseArchive.setCaseId(String.valueOf(caseArchiveDTO.getCaseId()));
        caseArchive.setCaseNo(caseArchiveDTO.getCaseNo());
        caseArchive.setCaseTitle(caseArchiveDTO.getCaseTitle());
        caseArchive.setLawyerId(caseArchiveDTO.getLawyerId() != null ? String.valueOf(caseArchiveDTO.getLawyerId()) : null);
        caseArchive.setLawyerName(caseArchiveDTO.getLawyerName());
        caseArchive.setClientId(caseArchiveDTO.getClientId() != null ? String.valueOf(caseArchiveDTO.getClientId()) : null);
        caseArchive.setClientName(caseArchiveDTO.getClientName());
        caseArchive.setCaseType(caseArchiveDTO.getCaseType());
        caseArchive.setCaseStatus(caseArchiveDTO.getCaseStatus());
        caseArchive.setCaseAmount(caseArchiveDTO.getCaseAmount() != null ? new BigDecimal(caseArchiveDTO.getCaseAmount().toString()) : null);
        caseArchive.setArchiveTime(LocalDateTime.now());
        caseArchive.setArchiveUserId(caseArchiveDTO.getHandlerId() != null ? String.valueOf(caseArchiveDTO.getHandlerId()) : null);
        caseArchive.setArchiveUserName(caseArchiveDTO.getHandlerName());
        caseArchive.setArchiveRemark(caseArchiveDTO.getRemark());
        
        // 保存案件档案
        caseArchiveMapper.insert(caseArchive);
        
        // 处理关联文件
        if (caseArchiveDTO.getFileList() != null && !caseArchiveDTO.getFileList().isEmpty()) {
            List<ArchiveFile> archiveFiles = new ArrayList<>();
            
            caseArchiveDTO.getFileList().forEach(fileDTO -> {
                ArchiveFile archiveFile = new ArchiveFile();
                archiveFile.setTitle(fileDTO.getFileName());
                archiveFile.setArchiveType(1); // 案件档案
                archiveFile.setArchiveStatus(1); // 已归档
                archiveFile.setArchiveNo(String.valueOf(caseArchive.getId()));
                archiveFile.setBusinessId(String.valueOf(caseArchiveDTO.getCaseId()));
                archiveFile.setBusinessType("CASE");
                archiveFile.setFilePath(fileDTO.getFilePath());
                archiveFile.setFileSize(fileDTO.getFileSize());
                archiveFile.setFileType(fileDTO.getFileType());
                archiveFile.setArchiveTime(new Date());
                archiveFile.setArchiveUserId(caseArchiveDTO.getHandlerId() != null ? String.valueOf(caseArchiveDTO.getHandlerId()) : null);
                archiveFile.setArchiveUserName(caseArchiveDTO.getHandlerName());
                archiveFile.setBorrowStatus(0); // 未借出
                archiveFile.setSort(fileDTO.getSort());
                archiveFiles.add(archiveFile);
            });
            
            // 批量保存档案文件
            for (ArchiveFile file : archiveFiles) {
                archiveFileMapper.insert(file);
            }
        }
        
        log.info("案件档案创建成功，archiveId: {}", caseArchive.getId());
        return String.valueOf(caseArchive.getId());
    }

    /**
     * 更新案件档案
     *
     * @param caseArchiveDTO 案件档案DTO
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCaseArchive(CaseArchiveDTO caseArchiveDTO) {
        log.info("更新案件档案，caseId: {}", caseArchiveDTO.getCaseId());
        
        // 查询案件档案
        CaseArchive existingArchive = caseArchiveMapper.selectOne(
                new QueryWrapper<CaseArchive>().eq("case_id", caseArchiveDTO.getCaseId())
        );
        
        if (existingArchive == null) {
            log.warn("案件 {} 未归档，无法更新", caseArchiveDTO.getCaseId());
            return false;
        }
        
        // 更新案件档案信息
        existingArchive.setCaseTitle(caseArchiveDTO.getCaseTitle());
        existingArchive.setCaseStatus(caseArchiveDTO.getCaseStatus());
        existingArchive.setCaseAmount(caseArchiveDTO.getCaseAmount() != null ? new BigDecimal(caseArchiveDTO.getCaseAmount().toString()) : null);
        existingArchive.setArchiveRemark(caseArchiveDTO.getRemark());
        
        // 保存更新
        caseArchiveMapper.updateById(existingArchive);
        
        return true;
    }

    /**
     * 删除案件档案
     *
     * @param id 案件档案ID
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCaseArchive(String id) {
        log.info("删除案件档案，archiveId: {}", id);
        
        // 查询档案是否存在
        CaseArchive archive = caseArchiveMapper.selectById(id);
        if (archive == null) {
            log.warn("档案 {} 不存在，无法删除", id);
            return false;
        }
        
        // 删除关联的档案文件
        QueryWrapper<ArchiveFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_id", archive.getCaseId())
                .eq("business_type", "CASE");
        List<ArchiveFile> files = archiveFileMapper.selectList(queryWrapper);
        
        if (files != null && !files.isEmpty()) {
            for (ArchiveFile file : files) {
                archiveFileMapper.deleteById(file.getId());
            }
        }
        
        // 删除档案记录
        caseArchiveMapper.deleteById(id);
        
        log.info("档案删除成功，archiveId: {}", id);
        return true;
    }

    /**
     * 获取案件档案详情
     *
     * @param id 案件档案ID
     * @return 案件档案DTO
     */
    @Override
    public CaseArchiveDTO getCaseArchiveDetail(String id) {
        log.info("获取案件档案详情，archiveId: {}", id);
        
        // 查询档案
        CaseArchive archive = caseArchiveMapper.selectById(id);
        if (archive == null) {
            log.warn("档案 {} 不存在", id);
            return null;
        }
        
        // 转换为DTO
        CaseArchiveDTO dto = new CaseArchiveDTO();
        if (archive.getCaseId() != null) {
            dto.setCaseId(Long.valueOf(archive.getCaseId()));
        }
        dto.setCaseTitle(archive.getCaseTitle());
        dto.setCaseNo(archive.getCaseNo());
        dto.setCaseType(archive.getCaseType());
        dto.setLawyerId(archive.getLawyerId() != null ? Long.valueOf(archive.getLawyerId()) : null);
        dto.setLawyerName(archive.getLawyerName());
        dto.setClientId(archive.getClientId() != null ? Long.valueOf(archive.getClientId()) : null);
        dto.setClientName(archive.getClientName());
        dto.setCaseStatus(archive.getCaseStatus());
        dto.setCaseAmount(archive.getCaseAmount() != null ? archive.getCaseAmount().doubleValue() : null);
        dto.setHandlerId(archive.getArchiveUserId() != null ? Long.valueOf(archive.getArchiveUserId()) : null);
        dto.setHandlerName(archive.getArchiveUserName());
        dto.setRemark(archive.getArchiveRemark());
        
        // 查询关联文件
        QueryWrapper<ArchiveFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_id", archive.getCaseId())
                .eq("business_type", "CASE")
                .orderByAsc("sort");
        List<ArchiveFile> files = archiveFileMapper.selectList(queryWrapper);
        
        if (files != null && !files.isEmpty()) {
            List<ArchiveFileDTO> fileDTOs = files.stream().map(file -> {
                ArchiveFileDTO fileDTO = new ArchiveFileDTO();
                fileDTO.setFileId(Long.valueOf(file.getId()));
                fileDTO.setFileName(file.getTitle());
                fileDTO.setFilePath(file.getFilePath());
                fileDTO.setFileSize(file.getFileSize());
                fileDTO.setFileType(file.getFileType());
                fileDTO.setSort(file.getSort());
                return fileDTO;
            }).collect(Collectors.toList());
            dto.setFileList(fileDTOs);
        }
        
        return dto;
    }

    /**
     * 分页查询案件档案
     *
     * @param page 分页参数
     * @param caseArchiveDTO 查询条件
     * @return 分页结果
     */
    @Override
    public Page<CaseArchiveDTO> pageCaseArchive(Page<CaseArchive> page, CaseArchiveDTO caseArchiveDTO) {
        log.info("分页查询案件档案");
        
        // 构建查询条件
        QueryWrapper<CaseArchive> queryWrapper = new QueryWrapper<>();
        if (caseArchiveDTO != null) {
            if (caseArchiveDTO.getCaseNo() != null && !caseArchiveDTO.getCaseNo().isEmpty()) {
                queryWrapper.like("case_no", caseArchiveDTO.getCaseNo());
            }
            if (caseArchiveDTO.getCaseTitle() != null && !caseArchiveDTO.getCaseTitle().isEmpty()) {
                queryWrapper.like("case_title", caseArchiveDTO.getCaseTitle());
            }
            if (caseArchiveDTO.getCaseType() != null) {
                queryWrapper.eq("case_type", caseArchiveDTO.getCaseType());
            }
            if (caseArchiveDTO.getCaseStatus() != null) {
                queryWrapper.eq("case_status", caseArchiveDTO.getCaseStatus());
            }
        }
        
        // 排序
        queryWrapper.orderByDesc("archive_time");
        
        // 执行查询
        Page<CaseArchive> resultPage = caseArchiveMapper.selectPage(page, queryWrapper);
        
        // 转换结果
        Page<CaseArchiveDTO> dtoPage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
        
        List<CaseArchiveDTO> dtoList = resultPage.getRecords().stream().map(archive -> {
            CaseArchiveDTO dto = new CaseArchiveDTO();
            dto.setCaseId(Long.valueOf(archive.getCaseId()));
            dto.setCaseTitle(archive.getCaseTitle());
            dto.setCaseNo(archive.getCaseNo());
            dto.setCaseType(archive.getCaseType());
            dto.setCaseStatus(archive.getCaseStatus());
            dto.setLawyerId(archive.getLawyerId() != null ? Long.valueOf(archive.getLawyerId()) : null);
            dto.setLawyerName(archive.getLawyerName());
            dto.setClientId(archive.getClientId() != null ? Long.valueOf(archive.getClientId()) : null);
            dto.setClientName(archive.getClientName());
            dto.setHandlerId(archive.getArchiveUserId() != null ? Long.valueOf(archive.getArchiveUserId()) : null);
            dto.setHandlerName(archive.getArchiveUserName());
            dto.setRemark(archive.getArchiveRemark());
            return dto;
        }).collect(Collectors.toList());
        
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }

    /**
     * 批量上传档案文件
     *
     * @param archiveFiles 档案文件列表
     * @return 上传成功的文件ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> batchUploadArchiveFiles(List<ArchiveFile> archiveFiles) {
        List<String> fileIds = new ArrayList<>();
        
        for (ArchiveFile file : archiveFiles) {
            // 设置档案状态为已归档 - 使用NORMAL常量 (正常状态)
            file.setArchiveStatus(ArchiveStatusEnum.NORMAL.getCode());
            file.setArchiveTime(new Date());
            file.setBorrowStatus(0); // 未借出
            
            // 保存档案文件
            archiveFileMapper.insert(file);
            fileIds.add(String.valueOf(file.getId()));
        }
        
        return fileIds;
    }

    /**
     * 借阅档案文件
     *
     * @param fileId 文件ID
     * @param borrowerId 借阅人ID
     * @param expectedReturnTime 预计归还时间
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean borrowArchiveFile(String fileId, String borrowerId, String expectedReturnTime) {
        ArchiveFile file = archiveFileMapper.selectById(fileId);
        if (file == null) {
            log.warn("档案文件不存在: {}", fileId);
            return false;
        }
        
        if (file.getBorrowStatus() == 1) {
            log.warn("档案文件已借出: {}", fileId);
            return false;
        }
        
        // 更新借阅信息
        file.setBorrowStatus(1); // 已借出
        file.setBorrowerId(borrowerId);
        file.setBorrowTime(new Date());
        // 将String类型的预期归还时间转换为Date
        // 这里应该根据实际情况使用DateUtils或SimpleDateFormat进行转换
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            file.setExpectedReturnTime(dateFormat.parse(expectedReturnTime));
        } catch (Exception e) {
            log.error("日期格式转换错误", e);
            return false;
        }
        
        archiveFileMapper.updateById(file);
        return true;
    }

    /**
     * 归还档案文件
     *
     * @param fileId 文件ID
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean returnArchiveFile(String fileId) {
        ArchiveFile file = archiveFileMapper.selectById(fileId);
        if (file == null) {
            log.warn("档案文件不存在: {}", fileId);
            return false;
        }
        
        if (file.getBorrowStatus() == 0) {
            log.warn("档案文件未借出，无需归还: {}", fileId);
            return false;
        }
        
        // 更新归还信息
        file.setBorrowStatus(0); // 未借出
        file.setActualReturnTime(new Date());
        
        archiveFileMapper.updateById(file);
        return true;
    }

    /**
     * 更新档案状态
     *
     * @param fileId 文件ID
     * @param status 新状态
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateArchiveStatus(String fileId, ArchiveStatusEnum status) {
        log.info("更新档案状态，fileId: {}, status: {}", fileId, status);
        
        // 查询文件
        ArchiveFile file = archiveFileMapper.selectById(fileId);
        if (file == null) {
            log.warn("档案文件 {} 不存在", fileId);
            return false;
        }
        
        // 更新状态
        file.setArchiveStatus(status.getCode());
        file.setUpdateTime(LocalDateTime.now());
        
        // 保存更新
        archiveFileMapper.updateById(file);
        
        log.info("档案状态更新成功，fileId: {}", fileId);
        return true;
    }

    /**
     * 获取案件相关的档案文件
     *
     * @param caseId 案件ID
     * @return 档案文件列表
     */
    @Override
    public List<ArchiveFile> getArchiveFilesByCaseId(String caseId) {
        log.info("获取案件相关的档案文件，caseId: {}", caseId);
        
        // 查询关联文件
        QueryWrapper<ArchiveFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_id", caseId)
                .eq("business_type", "CASE");
        
        return archiveFileMapper.selectList(queryWrapper);
    }

    /**
     * 根据档案编号获取档案主表
     */
    @Override
    public ArchiveMain getArchiveByNo(String archiveNo) {
        return baseMapper.selectByArchiveNo(archiveNo);
    }
} 