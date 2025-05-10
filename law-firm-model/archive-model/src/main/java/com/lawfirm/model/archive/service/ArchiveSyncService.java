package com.lawfirm.model.archive.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.archive.dto.ArchiveSyncRecordDTO;
import com.lawfirm.model.archive.dto.SyncConfigDTO;
import com.lawfirm.model.archive.entity.ArchiveSyncRecord;
import com.lawfirm.model.archive.entity.SyncConfig;
import com.lawfirm.model.base.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 档案同步服务接口
 */
@Service("archiveSyncService")
public interface ArchiveSyncService extends BaseService<ArchiveSyncRecord> {
    
    /**
     * 同步单个档案到外部系统
     *
     * @param archiveId 档案ID
     * @param systemCode 外部系统编码
     * @return 同步结果
     */
    boolean syncArchive(String archiveId, String systemCode);
    
    /**
     * 批量同步档案到外部系统
     *
     * @param archiveIds 档案ID列表
     * @param systemCode 外部系统编码
     * @return 同步结果
     */
    Map<String, Boolean> batchSyncArchives(List<String> archiveIds, String systemCode);
    
    /**
     * 同步所有未同步的档案
     *
     * @param systemCode 外部系统编码
     * @return 同步结果
     */
    Map<String, Boolean> syncPendingArchives(String systemCode);
    
    /**
     * 添加或更新同步配置
     *
     * @param syncConfigDTO 同步配置DTO
     * @return 是否成功
     */
    boolean saveOrUpdateSyncConfig(SyncConfigDTO syncConfigDTO);
    
    /**
     * 获取同步配置详情
     *
     * @param systemCode 外部系统编码
     * @return 同步配置DTO
     */
    SyncConfigDTO getSyncConfig(String systemCode);
    
    /**
     * 获取所有同步配置
     *
     * @return 同步配置列表
     */
    List<SyncConfigDTO> listAllSyncConfigs();
    
    /**
     * 删除同步配置
     *
     * @param systemCode 外部系统编码
     * @return 是否成功
     */
    boolean deleteSyncConfig(String systemCode);
    
    /**
     * 启用同步配置
     *
     * @param systemCode 外部系统编码
     * @return 是否成功
     */
    boolean enableSyncConfig(String systemCode);
    
    /**
     * 禁用同步配置
     *
     * @param systemCode 外部系统编码
     * @return 是否成功
     */
    boolean disableSyncConfig(String systemCode);
    
    /**
     * 查询同步记录
     *
     * @param page 分页参数
     * @param archiveId 档案ID
     * @param systemCode 外部系统编码
     * @return 同步记录分页结果
     */
    Page<ArchiveSyncRecordDTO> pageSyncRecords(Page<?> page, String archiveId, String systemCode);
} 