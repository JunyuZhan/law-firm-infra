package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.util.file.FileUtils;
import com.lawfirm.system.enums.UpgradeStatusEnum;
import com.lawfirm.system.mapper.SysUpgradeLogMapper;
import com.lawfirm.system.mapper.SysUpgradePackageMapper;
import com.lawfirm.system.model.dto.UpgradePackageDTO;
import com.lawfirm.system.model.entity.SysUpgradeLog;
import com.lawfirm.system.model.entity.SysUpgradePackage;
import com.lawfirm.system.model.vo.UpgradeLogVO;
import com.lawfirm.system.model.vo.UpgradePackageVO;
import com.lawfirm.system.service.SysUpgradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统升级Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUpgradeServiceImpl implements SysUpgradeService {
    
    private final SysUpgradePackageMapper upgradePackageMapper;
    private final SysUpgradeLogMapper upgradeLogMapper;
    
    @Value("${upgrade.package.path}")
    private String upgradePackagePath;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpgradePackageVO uploadPackage(MultipartFile file, UpgradePackageDTO dto) {
        // 1. 验证文件
        if (file == null || file.isEmpty()) {
            throw new BusinessException("升级包文件不能为空");
        }
        if (!file.getOriginalFilename().endsWith(".zip")) {
            throw new BusinessException("升级包文件必须是zip格式");
        }
        
        try {
            // 2. 保存文件
            String fileName = generateFileName(file.getOriginalFilename());
            Path targetPath = Paths.get(upgradePackagePath, fileName);
            Files.createDirectories(targetPath.getParent());
            file.transferTo(targetPath.toFile());
            
            // 3. 保存记录
            SysUpgradePackage upgradePackage = new SysUpgradePackage();
            BeanUtils.copyProperties(dto, upgradePackage);
            upgradePackage.setFilePath(targetPath.toString());
            upgradePackage.setMd5(FileUtils.calculateMD5(targetPath.toFile()));
            upgradePackage.setFileSize(file.getSize());
            upgradePackage.setStatus(UpgradeStatusEnum.PENDING.getCode());
            upgradePackageMapper.insert(upgradePackage);
            
            // 4. 记录日志
            saveLog(upgradePackage.getId(), "上传升级包", "上传升级包: " + dto.getPackageName(), true, null);
            
            return convertToVO(upgradePackage);
            
        } catch (IOException e) {
            log.error("保存升级包文件失败", e);
            throw new BusinessException("保存升级包文件失败: " + e.getMessage());
        }
    }
    
    @Override
    public Page<UpgradePackageVO> getPackages(Page<UpgradePackageVO> page) {
        Page<SysUpgradePackage> packagePage = upgradePackageMapper.selectPage(
            new Page<>(page.getCurrent(), page.getSize()),
            new LambdaQueryWrapper<SysUpgradePackage>()
                .orderByDesc(SysUpgradePackage::getCreateTime)
        );
        
        return new Page<UpgradePackageVO>()
            .setCurrent(packagePage.getCurrent())
            .setSize(packagePage.getSize())
            .setTotal(packagePage.getTotal())
            .setRecords(packagePage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList()));
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeUpgrade(Long packageId) {
        // 1. 获取升级包
        SysUpgradePackage upgradePackage = upgradePackageMapper.selectById(packageId);
        if (upgradePackage == null) {
            throw new BusinessException("升级包不存在");
        }
        if (!UpgradeStatusEnum.PENDING.getCode().equals(upgradePackage.getStatus())) {
            throw new BusinessException("升级包状态不正确");
        }
        
        try {
            // 2. 更新状态为升级中
            upgradePackage.setStatus(UpgradeStatusEnum.UPGRADING.getCode());
            upgradePackageMapper.updateById(upgradePackage);
            saveLog(packageId, "开始升级", "开始执行升级: " + upgradePackage.getPackageName(), true, null);
            
            // 3. 备份(如果需要)
            if (upgradePackage.getNeedBackup()) {
                String dbBackupPath = upgradeExecutor.backupDatabase(upgradePackage.getVersion());
                String filesBackupPath = upgradeExecutor.backupSystemFiles(upgradePackage.getVersion());
                upgradePackage.setBackupPath(dbBackupPath + ";" + filesBackupPath);
                upgradePackageMapper.updateById(upgradePackage);
            }
            
            // 4. 执行升级脚本
            upgradeExecutor.executeUpgradeScripts(upgradePackage.getFilePath());
            
            // 5. 更新状态为成功
            upgradePackage.setStatus(UpgradeStatusEnum.SUCCESS.getCode());
            upgradePackageMapper.updateById(upgradePackage);
            saveLog(packageId, "升级完成", "升级执行完成: " + upgradePackage.getPackageName(), true, null);
            
        } catch (Exception e) {
            // 升级失败
            log.error("执行升级失败", e);
            upgradePackage.setStatus(UpgradeStatusEnum.FAILED.getCode());
            upgradePackage.setErrorMessage(e.getMessage());
            upgradePackageMapper.updateById(upgradePackage);
            saveLog(packageId, "升级失败", "升级执行失败: " + e.getMessage(), false, e.getMessage());
            throw new BusinessException("执行升级失败: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rollbackUpgrade(Long packageId) {
        // 1. 获取升级包
        SysUpgradePackage upgradePackage = upgradePackageMapper.selectById(packageId);
        if (upgradePackage == null) {
            throw new BusinessException("升级包不存在");
        }
        if (!UpgradeStatusEnum.FAILED.getCode().equals(upgradePackage.getStatus())) {
            throw new BusinessException("只能回滚失败的升级");
        }
        
        try {
            // 2. 执行回滚
            upgradeExecutor.executeRollbackScripts(upgradePackage.getFilePath(), upgradePackage.getBackupPath());
            
            // 3. 更新状态为待升级
            upgradePackage.setStatus(UpgradeStatusEnum.PENDING.getCode());
            upgradePackage.setErrorMessage(null);
            upgradePackageMapper.updateById(upgradePackage);
            saveLog(packageId, "回滚完成", "升级回滚完成: " + upgradePackage.getPackageName(), true, null);
            
        } catch (Exception e) {
            log.error("执行回滚失败", e);
            saveLog(packageId, "回滚失败", "升级回滚失败: " + e.getMessage(), false, e.getMessage());
            throw new BusinessException("执行回滚失败: " + e.getMessage());
        }
    }
    
    @Override
    public List<UpgradeLogVO> getLogs(Long packageId) {
        List<SysUpgradeLog> logs = upgradeLogMapper.selectList(
            new LambdaQueryWrapper<SysUpgradeLog>()
                .eq(SysUpgradeLog::getPackageId, packageId)
                .orderByDesc(SysUpgradeLog::getCreateTime)
        );
        
        return logs.stream()
            .map(this::convertToLogVO)
            .collect(Collectors.toList());
    }
    
    /**
     * 生成文件名
     */
    private String generateFileName(String originalFilename) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return "upgrade_" + timestamp + extension;
    }
    
    /**
     * 保存日志
     */
    private void saveLog(Long packageId, String operation, String detail, Boolean success, String errorMessage) {
        SysUpgradeLog log = new SysUpgradeLog();
        log.setPackageId(packageId);
        log.setOperation(operation);
        log.setDetail(detail);
        log.setSuccess(success);
        log.setErrorMessage(errorMessage);
        upgradeLogMapper.insert(log);
    }
    
    /**
     * 执行升级脚本
     */
    private void executeUpgradeScripts(SysUpgradePackage upgradePackage) {
        // TODO: 实现升级脚本执行逻辑
    }
    
    /**
     * 执行回滚脚本
     */
    private void executeRollbackScripts(SysUpgradePackage upgradePackage) {
        // TODO: 实现回滚脚本执行逻辑
    }
    
    /**
     * 转换为VO
     */
    private UpgradePackageVO convertToVO(SysUpgradePackage entity) {
        if (entity == null) {
            return null;
        }
        UpgradePackageVO vo = new UpgradePackageVO();
        BeanUtils.copyProperties(entity, vo);
        UpgradeStatusEnum status = UpgradeStatusEnum.getByCode(entity.getStatus());
        if (status != null) {
            vo.setStatusDesc(status.getDesc());
        }
        return vo;
    }
    
    /**
     * 转换为日志VO
     */
    private UpgradeLogVO convertToLogVO(SysUpgradeLog entity) {
        if (entity == null) {
            return null;
        }
        UpgradeLogVO vo = new UpgradeLogVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
} 