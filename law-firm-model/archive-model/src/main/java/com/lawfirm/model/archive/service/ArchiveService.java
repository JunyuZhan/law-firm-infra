package com.lawfirm.model.archive.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.archive.dto.CaseArchiveDTO;
import com.lawfirm.model.archive.entity.ArchiveFile;
import com.lawfirm.model.archive.entity.ArchiveMain;
import com.lawfirm.model.archive.entity.CaseArchive;
import com.lawfirm.model.archive.enums.ArchiveStatusEnum;
import com.lawfirm.model.base.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 档案管理服务接口
 */
@Service("archiveService")
public interface ArchiveService extends BaseService<ArchiveMain> {
    
    /**
     * 创建案件档案
     *
     * @param caseArchiveDTO 案件档案DTO
     * @return 案件档案ID
     */
    String createCaseArchive(CaseArchiveDTO caseArchiveDTO);
    
    /**
     * 更新案件档案
     *
     * @param caseArchiveDTO 案件档案DTO
     * @return 是否成功
     */
    boolean updateCaseArchive(CaseArchiveDTO caseArchiveDTO);
    
    /**
     * 删除案件档案
     *
     * @param id 案件档案ID
     * @return 是否成功
     */
    boolean deleteCaseArchive(String id);
    
    /**
     * 获取案件档案详情
     *
     * @param id 案件档案ID
     * @return 案件档案DTO
     */
    CaseArchiveDTO getCaseArchiveDetail(String id);
    
    /**
     * 分页查询案件档案
     *
     * @param page 分页参数
     * @param caseArchiveDTO 查询条件
     * @return 分页结果
     */
    Page<CaseArchiveDTO> pageCaseArchive(Page<CaseArchive> page, CaseArchiveDTO caseArchiveDTO);
    
    /**
     * 批量上传档案文件
     *
     * @param archiveFiles 档案文件列表
     * @return 上传成功的文件ID列表
     */
    List<String> batchUploadArchiveFiles(List<ArchiveFile> archiveFiles);
    
    /**
     * 借阅档案文件
     *
     * @param fileId 文件ID
     * @param borrowerId 借阅人ID
     * @param expectedReturnTime 预计归还时间
     * @return 是否成功
     */
    boolean borrowArchiveFile(String fileId, String borrowerId, String expectedReturnTime);
    
    /**
     * 归还档案文件
     *
     * @param fileId 文件ID
     * @return 是否成功
     */
    boolean returnArchiveFile(String fileId);
    
    /**
     * 更新档案状态
     *
     * @param fileId 文件ID
     * @param status 新状态
     * @return 是否成功
     */
    boolean updateArchiveStatus(String fileId, ArchiveStatusEnum status);
    
    /**
     * 获取案件相关的档案文件
     *
     * @param caseId 案件ID
     * @return 档案文件列表
     */
    List<ArchiveFile> getArchiveFilesByCaseId(String caseId);
} 