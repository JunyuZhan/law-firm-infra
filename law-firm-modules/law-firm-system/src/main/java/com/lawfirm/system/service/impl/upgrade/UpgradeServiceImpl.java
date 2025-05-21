package com.lawfirm.system.service.impl.upgrade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.log.annotation.AuditLog;
import com.lawfirm.core.storage.service.support.FileOperator;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.dto.file.FileUploadDTO;
import com.lawfirm.model.storage.vo.FileVO;
import com.lawfirm.model.system.dto.upgrade.*;
import com.lawfirm.model.system.entity.upgrade.Patch;
import com.lawfirm.model.system.entity.upgrade.Upgrade;
import com.lawfirm.model.system.mapper.upgrade.PatchMapper;
import com.lawfirm.model.system.mapper.upgrade.UpgradeMapper;
import com.lawfirm.model.system.service.UpgradeService;
import com.lawfirm.model.system.vo.upgrade.PatchVO;
import com.lawfirm.model.system.vo.upgrade.UpgradeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import com.lawfirm.system.exception.SystemException;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Base64;

@Slf4j
@Service("systemUpgradeServiceImpl")
@ConditionalOnExpression("'${spring.profiles.active}' != 'nodb'")
public class UpgradeServiceImpl extends BaseServiceImpl<UpgradeMapper, Upgrade> implements UpgradeService {

    private final PatchMapper patchMapper;
    private final FileService fileService;
    private final JdbcTemplate jdbcTemplate;
    private final FileOperator fileOperator;

    public UpgradeServiceImpl(
        PatchMapper patchMapper,
        @Qualifier("storageFileServiceImpl") FileService fileService,
        JdbcTemplate jdbcTemplate,
        FileOperator fileOperator
    ) {
        this.patchMapper = patchMapper;
        this.fileService = fileService;
        this.jdbcTemplate = jdbcTemplate;
        this.fileOperator = fileOperator;
    }

    @Override
    public List<UpgradeVO> selectUpgradeList(UpgradeQueryDTO queryDTO) {
        // 构建查询条件
        QueryWrapper<Upgrade> wrapper = new QueryWrapper<>();
        if (queryDTO.getVersion() != null) {
            wrapper.like("upgrade_version", queryDTO.getVersion());
        }
        if (queryDTO.getTitle() != null) {
            wrapper.like("title", queryDTO.getTitle());
        }
        if (queryDTO.getUpgradeType() != null) {
            wrapper.eq("upgrade_type", queryDTO.getUpgradeType());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq("upgrade_status", queryDTO.getStatus());
        }

        // 执行查询
        List<Upgrade> list = list(wrapper);
        
        // 转换为VO
        List<UpgradeVO> voList = new ArrayList<>();
        for (Upgrade upgrade : list) {
            voList.add(convertToVO(upgrade));
        }
        
        return voList;
    }

    @Override
    public UpgradeVO selectUpgradeById(Long id) {
        return convertToVO(getById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AuditLog(description = "新增升级", operateType = "CREATE", businessType = "UPGRADE")
    public void insertUpgrade(UpgradeCreateDTO createDTO) {
        Upgrade upgrade = new Upgrade();
        BeanUtils.copyProperties(createDTO, upgrade);
        upgrade.setUpgradeStatus("PENDING");
        save(upgrade);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AuditLog(description = "修改升级", operateType = "UPDATE", businessType = "UPGRADE")
    public void updateUpgrade(UpgradeUpdateDTO updateDTO) {
        Upgrade upgrade = getById(updateDTO.getId());
        if (upgrade == null) {
            throw SystemException.notFound("升级记录");
        }
        BeanUtils.copyProperties(updateDTO, upgrade);
        update(upgrade);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AuditLog(description = "执行升级", operateType = "UPDATE", businessType = "UPGRADE")
    public void executeUpgrade(Long id) {
        Upgrade upgrade = getById(id);
        if (upgrade == null) {
            throw SystemException.notFound("升级记录");
        }
        
        // 检查状态
        if (!"PENDING".equals(upgrade.getUpgradeStatus())) {
            throw SystemException.invalidState("执行升级");
        }
        
        try {
            // 更新状态为升级中
            upgrade.setUpgradeStatus("UPGRADING");
            update(upgrade);
            
            // 获取关联的补丁并按顺序执行
            QueryWrapper<Patch> wrapper = new QueryWrapper<>();
            wrapper.eq("upgrade_id", id);
            wrapper.orderByAsc("execute_order");
            List<Patch> patches = patchMapper.selectList(wrapper);
            
            for (Patch patch : patches) {
                executePatch(patch.getId());
            }
            
            // 更新状态为成功
            upgrade.setUpgradeStatus("SUCCESS");
            upgrade.setUpgradeTime(System.currentTimeMillis());
            update(upgrade);
        } catch (Exception e) {
            // 更新状态为失败
            upgrade.setUpgradeStatus("FAILED");
            update(upgrade);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AuditLog(description = "回滚升级", operateType = "UPDATE", businessType = "UPGRADE")
    public void rollbackUpgrade(Long id) {
        Upgrade upgrade = getById(id);
        if (upgrade == null) {
            throw SystemException.notFound("升级记录");
        }
        
        // 检查状态
        if (!"SUCCESS".equals(upgrade.getUpgradeStatus()) && !"FAILED".equals(upgrade.getUpgradeStatus())) {
            throw SystemException.invalidState("回滚");
        }
        
        try {
            // 获取关联的补丁并按相反顺序回滚
            QueryWrapper<Patch> wrapper = new QueryWrapper<>();
            wrapper.eq("upgrade_id", id);
            wrapper.orderByDesc("execute_order");
            List<Patch> patches = patchMapper.selectList(wrapper);
            
            for (Patch patch : patches) {
                rollbackPatch(patch.getId());
            }
            
            // 更新状态为已回滚
            upgrade.setUpgradeStatus("ROLLBACK");
            upgrade.setRollbackTime(System.currentTimeMillis());
            update(upgrade);
        } catch (Exception e) {
            throw new SystemException("回滚失败: " + e.getMessage(), e);
        }
    }

    @Override
    public List<PatchVO> selectPatchList(PatchQueryDTO queryDTO) {
        // 构建查询条件
        QueryWrapper<Patch> wrapper = new QueryWrapper<>();
        if (queryDTO.getUpgradeId() != null) {
            wrapper.eq("upgrade_id", queryDTO.getUpgradeId());
        }
        if (queryDTO.getPatchType() != null) {
            wrapper.eq("patch_type", queryDTO.getPatchType());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq("patch_status", queryDTO.getStatus());
        }
        wrapper.orderByAsc("execute_order");

        // 执行查询
        List<Patch> list = patchMapper.selectList(wrapper);
        
        // 转换为VO
        List<PatchVO> voList = new ArrayList<>();
        for (Patch patch : list) {
            voList.add(convertToPatchVO(patch));
        }
        
        return voList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AuditLog(description = "上传补丁文件", operateType = "CREATE", businessType = "UPGRADE")
    public String uploadPatchFile(Long upgradeId, MultipartFile file) {
        // 检查升级记录是否存在
        Upgrade upgrade = getById(upgradeId);
        if (upgrade == null) {
            throw SystemException.notFound("升级记录");
        }

        try {
            // 构建文件上传参数
            FileUploadDTO uploadDTO = new FileUploadDTO();
            uploadDTO.setFileName(file.getOriginalFilename());
            uploadDTO.setBucketId(1L); // 使用系统默认的存储桶
            uploadDTO.setDescription("升级补丁文件");
            uploadDTO.setTags("upgrade,patch");
            uploadDTO.setContent(Base64.getEncoder().encodeToString(file.getBytes()));

            // 上传文件
            FileVO fileVO = fileService.upload(uploadDTO);
            
            return fileVO.getStoragePath();
        } catch (IOException e) {
            log.error("补丁文件上传失败", e);
            throw new SystemException("补丁文件上传失败: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AuditLog(description = "新增补丁", operateType = "CREATE", businessType = "UPGRADE")
    public void insertPatch(PatchCreateDTO createDTO) {
        // 检查升级记录是否存在
        Upgrade upgrade = getById(createDTO.getUpgradeId());
        if (upgrade == null) {
            throw SystemException.notFound("升级记录");
        }
        
        Patch patch = new Patch();
        BeanUtils.copyProperties(createDTO, patch);
        patch.setPatchStatus("PENDING");
        patchMapper.insert(patch);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AuditLog(description = "删除补丁", operateType = "DELETE", businessType = "UPGRADE")
    public void deletePatchById(Long patchId) {
        Patch patch = patchMapper.selectById(patchId);
        if (patch == null) {
            throw SystemException.notFound("补丁记录");
        }
        
        // 检查状态
        if (!"PENDING".equals(patch.getPatchStatus())) {
            throw SystemException.invalidState("删除待执行的补丁");
        }
        
        patchMapper.deleteById(patchId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AuditLog(description = "执行补丁", operateType = "UPDATE", businessType = "UPGRADE")
    public void executePatch(Long patchId) {
        Patch patch = patchMapper.selectById(patchId);
        if (patch == null) {
            throw SystemException.notFound("补丁记录");
        }
        
        // 检查状态
        if (!"PENDING".equals(patch.getPatchStatus())) {
            throw SystemException.invalidState("执行补丁");
        }
        
        try {
            // 更新状态为执行中
            patch.setPatchStatus("EXECUTING");
            patchMapper.updateById(patch);
            
            // 根据补丁类型执行不同的逻辑
            switch (patch.getPatchType()) {
                case "SQL":
                    executeSqlPatch(patch);
                    break;
                case "SCRIPT":
                    executeScriptPatch(patch);
                    break;
                case "FILE":
                    executeFilePatch(patch);
                    break;
                default:
                    throw SystemException.invalidState("不支持的补丁类型");
            }
            
            // 更新状态为成功
            patch.setPatchStatus("SUCCESS");
            patch.setExecuteTime(System.currentTimeMillis());
            patchMapper.updateById(patch);
        } catch (Exception e) {
            // 更新状态为失败
            patch.setPatchStatus("FAILED");
            patchMapper.updateById(patch);
            throw new SystemException("执行补丁失败: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AuditLog(description = "回滚补丁", operateType = "UPDATE", businessType = "UPGRADE")
    public void rollbackPatch(Long patchId) {
        Patch patch = patchMapper.selectById(patchId);
        if (patch == null) {
            throw SystemException.notFound("补丁记录");
        }
        
        // 检查状态
        if (!"SUCCESS".equals(patch.getPatchStatus()) && !"FAILED".equals(patch.getPatchStatus())) {
            throw SystemException.invalidState("回滚");
        }
        
        try {
            // 根据补丁类型执行不同的回滚逻辑
            switch (patch.getPatchType()) {
                case "SQL":
                    rollbackSqlPatch(patch);
                    break;
                case "SCRIPT":
                    rollbackScriptPatch(patch);
                    break;
                case "FILE":
                    rollbackFilePatch(patch);
                    break;
                default:
                    throw SystemException.invalidState("不支持的补丁类型");
            }
            
            // 更新状态为已回滚
            patch.setPatchStatus("ROLLBACK");
            patch.setRollbackTime(System.currentTimeMillis());
            patchMapper.updateById(patch);
        } catch (Exception e) {
            throw new SystemException("回滚补丁失败: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean checkUpdate() {
        try {
            // 查询最新的成功升级记录
            QueryWrapper<Upgrade> wrapper = new QueryWrapper<>();
            wrapper.eq("upgrade_status", "SUCCESS");
            wrapper.orderByDesc("upgrade_version");
            wrapper.last("LIMIT 1");
            
            Upgrade latestUpgrade = getOne(wrapper);
            String currentVersion = latestUpgrade != null ? latestUpgrade.getUpgradeVersion() : "0.0.0";
            
            // 查询待升级的记录
            wrapper = new QueryWrapper<>();
            wrapper.gt("upgrade_version", currentVersion);
            wrapper.eq("upgrade_status", "PENDING");
            wrapper.orderByAsc("upgrade_version");
            
            List<Upgrade> pendingUpgrades = list(wrapper);
            
            return !pendingUpgrades.isEmpty();
        } catch (Exception e) {
            log.error("检查更新失败", e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AuditLog(description = "删除升级", operateType = "DELETE", businessType = "UPGRADE")
    public void deleteUpgradeById(Long id) {
        // 删除关联的补丁记录
        QueryWrapper<Patch> wrapper = new QueryWrapper<>();
        wrapper.eq("upgrade_id", id);
        patchMapper.delete(wrapper);
        
        // 删除升级记录
        removeById(id);
    }

    private UpgradeVO convertToVO(Upgrade upgrade) {
        if (upgrade == null) {
            return null;
        }
        UpgradeVO vo = new UpgradeVO();
        BeanUtils.copyProperties(upgrade, vo);
        return vo;
    }

    private PatchVO convertToPatchVO(Patch patch) {
        if (patch == null) {
            return null;
        }
        PatchVO vo = new PatchVO();
        BeanUtils.copyProperties(patch, vo);
        return vo;
    }

    private void executeSqlPatch(Patch patch) {
        try {
            // 下载SQL文件
            byte[] sqlContent = fileService.download(Long.parseLong(patch.getFilePath()));
            String sqlScript = new String(sqlContent, StandardCharsets.UTF_8);
            
            // 分割SQL语句
            String[] sqlStatements = sqlScript.split(";");
            
            // 执行每条SQL语句
            for (String sql : sqlStatements) {
                if (StringUtils.hasText(sql)) {
                    jdbcTemplate.execute(sql.trim());
                }
            }
        } catch (Exception e) {
            throw new SystemException("执行SQL补丁失败: " + e.getMessage(), e);
        }
    }

    private void executeScriptPatch(Patch patch) {
        try {
            // 下载脚本文件
            byte[] scriptContent = fileService.download(Long.parseLong(patch.getFilePath()));
            
            // 创建临时脚本文件
            Path tempScript = Files.createTempFile("patch_", getScriptExtension(patch.getFilePath()));
            Files.write(tempScript, scriptContent);
            
            // 设置脚本可执行权限
            tempScript.toFile().setExecutable(true);
            
            // 构建命令
            List<String> command = buildScriptCommand(tempScript.toString(), patch.getFilePath());
            
            // 执行脚本
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            
            // 等待脚本执行完成
            if (!process.waitFor(30, TimeUnit.MINUTES)) {
                process.destroy();
                throw SystemException.invalidState("脚本执行超时");
            }
            
            // 检查执行结果
            int exitCode = process.exitValue();
            if (exitCode != 0) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String error = reader.lines().reduce("", (a, b) -> a + "\n" + b);
                    throw new SystemException("脚本执行失败: " + error);
                }
            }
            
            // 清理临时文件
            Files.deleteIfExists(tempScript);
        } catch (Exception e) {
            throw new SystemException("执行脚本补丁失败: " + e.getMessage(), e);
        }
    }

    private void executeFilePatch(Patch patch) {
        try {
            // 下载补丁文件
            byte[] fileContent = fileService.download(Long.parseLong(patch.getFilePath()));
            
            // 解析补丁文件路径
            String targetPath = patch.getDescription(); // 使用description字段存储目标路径
            if (!StringUtils.hasText(targetPath)) {
                throw SystemException.invalidState("未指定目标文件路径");
            }
            
            // 备份原文件
            Path target = Paths.get(targetPath);
            if (Files.exists(target)) {
                Path backup = Paths.get(targetPath + ".backup");
                Files.copy(target, backup, StandardCopyOption.REPLACE_EXISTING);
            }
            
            // 应用补丁文件
            Files.write(target, fileContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new SystemException("执行文件补丁失败: " + e.getMessage(), e);
        }
    }

    private void rollbackSqlPatch(Patch patch) {
        try {
            // 下载SQL文件
            byte[] sqlContent = fileService.download(Long.parseLong(patch.getFilePath()));
            String sqlScript = new String(sqlContent, StandardCharsets.UTF_8);
            
            // 解析回滚SQL
            String rollbackSql = extractRollbackSql(sqlScript);
            if (!StringUtils.hasText(rollbackSql)) {
                throw SystemException.invalidState("未找到回滚SQL");
            }
            
            // 执行回滚SQL
            String[] sqlStatements = rollbackSql.split(";");
            for (String sql : sqlStatements) {
                if (StringUtils.hasText(sql)) {
                    jdbcTemplate.execute(sql.trim());
                }
            }
        } catch (Exception e) {
            throw new SystemException("回滚SQL补丁失败: " + e.getMessage(), e);
        }
    }

    private void rollbackScriptPatch(Patch patch) {
        try {
            // 下载脚本文件
            byte[] scriptContent = fileService.download(Long.parseLong(patch.getFilePath()));
            
            // 创建临时脚本文件
            Path tempScript = Files.createTempFile("patch_rollback_", getScriptExtension(patch.getFilePath()));
            Files.write(tempScript, scriptContent);
            
            // 设置脚本可执行权限
            tempScript.toFile().setExecutable(true);
            
            // 构建回滚命令
            List<String> command = buildScriptCommand(tempScript.toString(), patch.getFilePath());
            command.add("--rollback"); // 添加回滚参数
            
            // 执行回滚脚本
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            
            // 等待脚本执行完成
            if (!process.waitFor(30, TimeUnit.MINUTES)) {
                process.destroy();
                throw SystemException.invalidState("回滚脚本执行超时");
            }
            
            // 检查执行结果
            int exitCode = process.exitValue();
            if (exitCode != 0) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String error = reader.lines().reduce("", (a, b) -> a + "\n" + b);
                    throw new SystemException("回滚脚本执行失败: " + error);
                }
            }
            
            // 清理临时文件
            Files.deleteIfExists(tempScript);
        } catch (Exception e) {
            throw new SystemException("回滚脚本补丁失败: " + e.getMessage(), e);
        }
    }

    private void rollbackFilePatch(Patch patch) {
        try {
            // 解析补丁文件路径
            String targetPath = patch.getDescription(); // 使用description字段存储目标路径
            if (!StringUtils.hasText(targetPath)) {
                throw SystemException.invalidState("未指定目标文件路径");
            }
            
            // 检查备份文件是否存在
            Path target = Paths.get(targetPath);
            Path backup = Paths.get(targetPath + ".backup");
            if (!Files.exists(backup)) {
                throw SystemException.notFound("未找到备份文件");
            }
            
            // 恢复备份文件
            Files.copy(backup, target, StandardCopyOption.REPLACE_EXISTING);
            
            // 删除备份文件
            Files.deleteIfExists(backup);
        } catch (Exception e) {
            throw new SystemException("回滚文件补丁失败: " + e.getMessage(), e);
        }
    }

    private String getScriptExtension(String filePath) {
        String extension = filePath.substring(filePath.lastIndexOf('.'));
        return extension.isEmpty() ? ".sh" : extension;
    }

    private List<String> buildScriptCommand(String scriptPath, String originalPath) {
        List<String> command = new ArrayList<>();
        String extension = getScriptExtension(originalPath).toLowerCase();
        
        switch (extension) {
            case ".sh":
                command.add("bash");
                break;
            case ".py":
                command.add("python");
                break;
            case ".js":
                command.add("node");
                break;
            default:
                throw SystemException.invalidState("不支持的脚本类型: " + extension);
        }
        
        command.add(scriptPath);
        return command;
    }

    private String extractRollbackSql(String sqlScript) {
        // 查找回滚SQL部分
        int rollbackIndex = sqlScript.indexOf("-- ROLLBACK");
        if (rollbackIndex == -1) {
            return null;
        }
        
        // 提取回滚SQL
        return sqlScript.substring(rollbackIndex).replace("-- ROLLBACK", "").trim();
    }
} 