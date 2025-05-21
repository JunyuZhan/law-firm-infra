package com.lawfirm.archive.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.archive.converter.ArchiveSyncRecordConverter;
import com.lawfirm.model.archive.converter.SyncConfigConverter;
import com.lawfirm.model.archive.dto.ArchiveSyncRecordDTO;
import com.lawfirm.model.archive.dto.SyncConfigDTO;
import com.lawfirm.model.archive.entity.ArchiveSyncRecord;
import com.lawfirm.model.archive.entity.SyncConfig;
import com.lawfirm.model.archive.mapper.ArchiveSyncRecordMapper;
import com.lawfirm.model.archive.mapper.SyncConfigMapper;
import com.lawfirm.model.archive.mapper.ArchiveMainMapper;
import com.lawfirm.model.archive.entity.ArchiveMain;
import com.lawfirm.model.archive.service.ArchiveSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 档案同步服务实现类
 */
@Service
@Slf4j
public class ArchiveSyncServiceImpl extends ServiceImpl<ArchiveSyncRecordMapper, ArchiveSyncRecord> 
        implements ArchiveSyncService {
    
    @Autowired
    private SyncConfigMapper syncConfigMapper;
    
    @Autowired
    private SyncConfigConverter syncConfigConverter;
    
    @Autowired
    private ArchiveSyncRecordConverter archiveSyncRecordConverter;
    
    @Autowired
    private ArchiveMainMapper archiveMainMapper;
    
    /**
     * 同步单个档案到外部系统
     *
     * @param archiveId 档案ID
     * @param systemCode 外部系统编码
     * @return 同步结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean syncArchive(String archiveId, String systemCode) {
        log.info("同步档案到外部系统，archiveId: {}, systemCode: {}", archiveId, systemCode);
        
        // 查询同步配置
        SyncConfig config = syncConfigMapper.findBySystemCode(systemCode);
        if (config == null || config.getEnabled() != 1) {
            log.warn("同步配置不存在或未启用，systemCode: {}", systemCode);
            return false;
        }
        
        try {
            // 实际同步逻辑，调用外部系统API
            // TODO: 实现实际同步逻辑
            
            // 查询档案信息
            ArchiveMain archive = archiveMainMapper.selectById(archiveId);
            if (archive == null) {
                log.error("档案不存在，archiveId: {}", archiveId);
                throw new RuntimeException("档案不存在");
            }
            
            // 根据同步配置获取外部系统API信息
            String apiUrl = config.getSyncUrl();
            String apiKey = config.getAuthToken();
            String apiSecret = config.getAuthToken(); // 使用相同的token作为密钥
            
            // 准备请求参数
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("archiveId", archiveId);
            requestParams.put("archiveNo", archive.getArchiveNo());
            requestParams.put("title", archive.getTitle());
            requestParams.put("sourceType", archive.getSourceType());
            requestParams.put("createTime", archive.getCreateTime());
            requestParams.put("createBy", archive.getCreateBy());
            
            // 添加更多档案元数据
            if (archive.getArchiveData() != null) {
                // 如果存在JSON格式的归档数据，解析并添加
                try {
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    Map<String, Object> archiveData = mapper.readValue(archive.getArchiveData(), 
                            new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
                    requestParams.putAll(archiveData);
                } catch (Exception e) {
                    log.warn("解析归档数据失败: {}", e.getMessage());
                }
            }
            
            // 实际调用HTTP客户端发送请求
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("X-API-KEY", apiKey);
            headers.set("X-API-SECRET", apiSecret);
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            
            org.springframework.http.HttpEntity<Map<String, Object>> requestEntity = 
                    new org.springframework.http.HttpEntity<>(requestParams, headers);
            
            // 使用RestTemplate发送请求
            org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
            
            // 设置超时
            org.springframework.http.client.SimpleClientHttpRequestFactory factory = 
                    new org.springframework.http.client.SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(5000); // 连接超时5秒
            factory.setReadTimeout(10000);   // 读取超时10秒
            restTemplate.setRequestFactory(factory);
            
            try {
                org.springframework.http.ResponseEntity<String> response = 
                        restTemplate.postForEntity(apiUrl, requestEntity, String.class);
                
                // 解析响应
                if (response.getStatusCode().is2xxSuccessful()) {
                    log.info("同步请求成功，响应: {}", response.getBody());
                    
                    // 检查响应内容中是否包含成功标识
                    boolean syncSuccess = checkSyncSuccess(response.getBody());
                    
                    if (syncSuccess) {
                        // 更新档案同步状态
                        archive.setIsSynced(1); // 已同步
                        archive.setSyncTime(LocalDateTime.now().toString()); // 转换为字符串类型
                        archiveMainMapper.updateById(archive);
                        
                        // 记录同步成功
                        ArchiveSyncRecord record = new ArchiveSyncRecord();
                        record.setArchiveId(archiveId);
                        record.setSyncSystemCode(systemCode);
                        record.setSyncTime(LocalDateTime.now());
                        record.setSyncStatus(1); // 成功
                        record.setSyncDataSnapshot(response.getBody()); // 保存响应数据
                        getBaseMapper().insert(record);
                        
                        log.info("档案同步成功，archiveId: {}, systemCode: {}", archiveId, systemCode);
                        return true;
                    } else {
                        // 同步失败，记录错误信息
                        log.error("外部系统处理失败，响应: {}", response.getBody());
                        // 记录同步失败
                        ArchiveSyncRecord record = new ArchiveSyncRecord();
                        record.setArchiveId(archiveId);
                        record.setSyncSystemCode(systemCode);
                        record.setSyncTime(LocalDateTime.now());
                        record.setSyncStatus(0); // 失败
                        record.setErrorMessage("外部系统处理失败: " + response.getBody());
                        record.setSyncDataSnapshot(response.getBody()); // 保存响应数据
                        getBaseMapper().insert(record);
                        return false;
                    }
                } else {
                    // 请求失败
                    log.error("同步请求失败，状态码: {}, 响应: {}", response.getStatusCode().value(), response.getBody());
                    throw new RuntimeException("同步请求失败: " + response.getStatusCode());
                }
            } catch (org.springframework.web.client.RestClientException e) {
                log.error("发送同步请求异常: {}", e.getMessage(), e);
                throw new RuntimeException("发送同步请求异常: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error("档案同步失败，archiveId: {}, systemCode: {}, error: {}", archiveId, systemCode, e.getMessage());
            
            // 记录同步失败
            ArchiveSyncRecord record = new ArchiveSyncRecord();
            record.setArchiveId(archiveId);
            record.setSyncSystemCode(systemCode);
            record.setSyncTime(LocalDateTime.now());
            record.setSyncStatus(0); // 失败
            record.setErrorMessage(e.getMessage());
            getBaseMapper().insert(record);
            
            return false;
        }
    }
    
    /**
     * 批量同步档案到外部系统
     *
     * @param archiveIds 档案ID列表
     * @param systemCode 外部系统编码
     * @return 同步结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Boolean> batchSyncArchives(List<String> archiveIds, String systemCode) {
        log.info("批量同步档案到外部系统，archiveCount: {}, systemCode: {}", archiveIds.size(), systemCode);
        
        Map<String, Boolean> results = new HashMap<>();
        
        for (String archiveId : archiveIds) {
            boolean success = syncArchive(archiveId, systemCode);
            results.put(archiveId, success);
        }
        
        return results;
    }
    
    /**
     * 同步所有未同步的档案
     *
     * @param systemCode 外部系统编码
     * @return 同步结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Boolean> syncPendingArchives(String systemCode) {
        log.info("同步所有未同步的档案，systemCode: {}", systemCode);
        
        // 查询同步配置
        SyncConfig config = syncConfigMapper.findBySystemCode(systemCode);
        if (config == null || config.getEnabled() != 1) {
            log.warn("同步配置不存在或未启用，systemCode: {}", systemCode);
            return new HashMap<>();
        }
        
        // 获取未同步的档案列表
        QueryWrapper<ArchiveMain> wrapper = new QueryWrapper<>();
        wrapper.eq("is_synced", 0).eq("deleted", 0);
        List<ArchiveMain> pendingArchives = archiveMainMapper.selectList(wrapper);
        List<String> pendingArchiveIds = new ArrayList<>();
        for (ArchiveMain archive : pendingArchives) {
            pendingArchiveIds.add(String.valueOf(archive.getId()));
        }
        
        return batchSyncArchives(pendingArchiveIds, systemCode);
    }
    
    /**
     * 添加或更新同步配置
     *
     * @param syncConfigDTO 同步配置DTO
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateSyncConfig(SyncConfigDTO syncConfigDTO) {
        log.info("添加或更新同步配置，systemCode: {}", syncConfigDTO.getSystemCode());
        
        // 查询是否已存在
        SyncConfig existingConfig = syncConfigMapper.findBySystemCode(syncConfigDTO.getSystemCode());
        
        SyncConfig config;
        if (existingConfig != null) {
            // 更新已有配置
            config = syncConfigConverter.updateEntity(syncConfigDTO, existingConfig);
        } else {
            // 创建新配置
            config = syncConfigConverter.toEntity(syncConfigDTO);
        }
        
        // 保存或更新
        if (existingConfig != null) {
            syncConfigMapper.updateById(config);
        } else {
            syncConfigMapper.insert(config);
        }
        
        log.info("同步配置保存成功，systemCode: {}", syncConfigDTO.getSystemCode());
        return true;
    }
    
    /**
     * 获取同步配置详情
     *
     * @param systemCode 外部系统编码
     * @return 同步配置DTO
     */
    @Override
    public SyncConfigDTO getSyncConfig(String systemCode) {
        log.info("获取同步配置详情，systemCode: {}", systemCode);
        
        SyncConfig config = syncConfigMapper.findBySystemCode(systemCode);
        if (config == null) {
            return null;
        }
        
        return syncConfigConverter.toDTO(config);
    }
    
    /**
     * 获取所有同步配置
     *
     * @return 同步配置列表
     */
    @Override
    public List<SyncConfigDTO> listAllSyncConfigs() {
        log.info("获取所有同步配置");
        
        List<SyncConfig> configs = syncConfigMapper.selectList(null);
        
        return syncConfigConverter.toDTOList(configs);
    }
    
    /**
     * 删除同步配置
     *
     * @param systemCode 外部系统编码
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSyncConfig(String systemCode) {
        log.info("删除同步配置，systemCode: {}", systemCode);
        
        SyncConfig config = syncConfigMapper.findBySystemCode(systemCode);
        if (config == null) {
            log.warn("同步配置不存在，systemCode: {}", systemCode);
            return false;
        }
        
        syncConfigMapper.deleteById(config.getId());
        
        log.info("同步配置删除成功，systemCode: {}", systemCode);
        return true;
    }
    
    /**
     * 启用同步配置
     *
     * @param systemCode 外部系统编码
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enableSyncConfig(String systemCode) {
        log.info("启用同步配置，systemCode: {}", systemCode);
        
        SyncConfig config = syncConfigMapper.findBySystemCode(systemCode);
        if (config == null) {
            log.warn("同步配置不存在，systemCode: {}", systemCode);
            return false;
        }
        
        config.setEnabled(1); // 使用整型而非布尔型
        config.setUpdateTime(LocalDateTime.now());
        syncConfigMapper.updateById(config);
        
        log.info("同步配置启用成功，systemCode: {}", systemCode);
        return true;
    }
    
    /**
     * 禁用同步配置
     *
     * @param systemCode 外部系统编码
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableSyncConfig(String systemCode) {
        log.info("禁用同步配置，systemCode: {}", systemCode);
        
        SyncConfig config = syncConfigMapper.findBySystemCode(systemCode);
        if (config == null) {
            log.warn("同步配置不存在，systemCode: {}", systemCode);
            return false;
        }
        
        config.setEnabled(0); // 使用整型而非布尔型
        config.setUpdateTime(LocalDateTime.now());
        syncConfigMapper.updateById(config);
        
        log.info("同步配置禁用成功，systemCode: {}", systemCode);
        return true;
    }
    
    /**
     * 查询同步记录
     *
     * @param page 分页参数
     * @param archiveId 档案ID
     * @param systemCode 外部系统编码
     * @return 同步记录分页结果
     */
    @Override
    public Page<ArchiveSyncRecordDTO> pageSyncRecords(Page<?> page, String archiveId, String systemCode) {
        log.info("分页查询同步记录，archiveId: {}, systemCode: {}", archiveId, systemCode);
        
        // 创建查询条件
        QueryWrapper<ArchiveSyncRecord> queryWrapper = new QueryWrapper<>();
        if (archiveId != null && !archiveId.isEmpty()) {
            queryWrapper.eq("archive_id", archiveId);
        }
        if (systemCode != null && !systemCode.isEmpty()) {
            queryWrapper.eq("sync_system_code", systemCode);
        }
        queryWrapper.orderByDesc("sync_time");
        
        // 执行分页查询
        Page<ArchiveSyncRecord> recordPage = new Page<>(page.getCurrent(), page.getSize());
        Page<ArchiveSyncRecord> resultPage = page(recordPage, queryWrapper);
        
        // 转换结果
        Page<ArchiveSyncRecordDTO> dtoPage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
        
        List<ArchiveSyncRecordDTO> dtoList = archiveSyncRecordConverter.toDTOList(resultPage.getRecords());
        
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }
    
    // 实现BaseService中的抽象方法
    @Override
    public boolean save(ArchiveSyncRecord entity) {
        return super.save(entity);
    }
    
    @Override
    public boolean saveBatch(List<ArchiveSyncRecord> entities) {
        return super.saveBatch(entities);
    }
    
    @Override
    public boolean update(ArchiveSyncRecord entity) {
        return super.updateById(entity);
    }
    
    @Override
    public boolean updateBatch(List<ArchiveSyncRecord> entities) {
        return super.updateBatchById(entities);
    }
    
    @Override
    public boolean remove(Long id) {
        return super.removeById(id);
    }
    
    @Override
    public boolean removeBatch(List<Long> ids) {
        return super.removeByIds(ids);
    }
    
    @Override
    public ArchiveSyncRecord getById(Long id) {
        return super.getById(id);
    }
    
    @Override
    public List<ArchiveSyncRecord> list(QueryWrapper<ArchiveSyncRecord> wrapper) {
        return super.list(wrapper);
    }
    
    @Override
    public Page<ArchiveSyncRecord> page(Page<ArchiveSyncRecord> page, QueryWrapper<ArchiveSyncRecord> wrapper) {
        return super.page(page, wrapper);
    }
    
    @Override
    public long count(QueryWrapper<ArchiveSyncRecord> wrapper) {
        return super.count(wrapper);
    }
    
    @Override
    public boolean exists(QueryWrapper<ArchiveSyncRecord> wrapper) {
        return count(wrapper) > 0;
    }
    
    @Override
    public Long getCurrentTenantId() {
        return 1L; // 默认租户ID
    }
    
    @Override
    public Long getCurrentUserId() {
        return 1L; // 默认用户ID
    }
    
    @Override
    public String getCurrentUsername() {
        return "admin"; // 默认用户名
    }
    
    /**
     * 检查同步响应是否成功
     * 
     * @param responseBody 响应体内容
     * @return 是否成功
     */
    private boolean checkSyncSuccess(String responseBody) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(responseBody);
            
            // 假设外部系统的响应格式包含code字段，0表示成功
            if (root.has("code")) {
                int code = root.get("code").asInt(-1);
                return code == 0;
            }
            
            // 或者包含success字段
            if (root.has("success")) {
                return root.get("success").asBoolean(false);
            }
            
            // 默认情况，认为成功
            return true;
        } catch (Exception e) {
            log.warn("解析同步响应失败: {}", e.getMessage());
            // 解析失败，默认为不成功
            return false;
        }
    }
}