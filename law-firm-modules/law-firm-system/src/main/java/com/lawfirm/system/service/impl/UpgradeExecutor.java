package com.lawfirm.system.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.util.file.FileUtils;
import com.lawfirm.system.model.UpgradeMetaInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipFile;

/**
 * 升级执行器
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UpgradeExecutor {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    
    @Value("${spring.datasource.username}")
    private String dbUsername;
    
    @Value("${spring.datasource.password}")
    private String dbPassword;
    
    @Value("${upgrade.backup.path}")
    private String backupPath;
    
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 解压升级包
     */
    public String extractUpgradePackage(String packagePath) throws Exception {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String extractPath = backupPath + "/temp/upgrade_" + timestamp;
        
        try (ZipFile zipFile = new ZipFile(packagePath)) {
            FileUtils.unzip(packagePath, extractPath);
            return extractPath;
        }
    }

    /**
     * 读取元信息
     */
    public UpgradeMetaInfo readMetaInfo(String extractPath) throws Exception {
        Path metaPath = Paths.get(extractPath, "meta.json");
        if (!Files.exists(metaPath)) {
            throw new BusinessException("升级包元信息文件不存在");
        }
        
        String metaJson = new String(Files.readAllBytes(metaPath), StandardCharsets.UTF_8);
        return objectMapper.readValue(metaJson, UpgradeMetaInfo.class);
    }

    /**
     * 执行升级脚本
     */
    public void executeUpgradeScripts(String packagePath) throws Exception {
        // 1. 解压升级包
        String extractPath = extractUpgradePackage(packagePath);
        
        try {
            // 2. 读取meta.json
            UpgradeMetaInfo metaInfo = readMetaInfo(extractPath);
            
            // 3. 执行SQL脚本
            executeSqlScripts(extractPath + "/scripts/upgrade");
            
            // 4. 更新系统文件
            updateSystemFiles(extractPath + "/files");
            
        } finally {
            // 5. 清理临时文件
            FileUtils.deleteDirectory(new File(extractPath));
        }
    }

    /**
     * 执行回滚脚本
     */
    public void executeRollbackScripts(String packagePath, String backupPath) throws Exception {
        // 1. 解压升级包
        String extractPath = extractUpgradePackage(packagePath);
        
        try {
            // 2. 读取meta.json
            UpgradeMetaInfo metaInfo = readMetaInfo(extractPath);
            
            // 3. 还原数据库
            if (metaInfo.getNeedBackup() && StringUtils.isNotBlank(backupPath)) {
                String[] paths = backupPath.split(";");
                if (paths.length > 0) {
                    restoreDatabase(paths[0]);
                }
                if (paths.length > 1) {
                    restoreSystemFiles(paths[1]);
                }
            } else {
                // 执行回滚脚本
                executeSqlScripts(extractPath + "/scripts/rollback");
            }
            
        } finally {
            // 4. 清理临时文件
            FileUtils.deleteDirectory(new File(extractPath));
        }
    }

    /**
     * 执行SQL脚本
     */
    private void executeSqlScripts(String scriptPath) {
        File scriptDir = new File(scriptPath);
        if (!scriptDir.exists() || !scriptDir.isDirectory()) {
            return;
        }
        
        // 获取所有SQL文件并排序
        File[] sqlFiles = scriptDir.listFiles((dir, name) -> name.endsWith(".sql"));
        if (sqlFiles == null || sqlFiles.length == 0) {
            return;
        }
        
        Arrays.sort(sqlFiles);
        
        for (File sqlFile : sqlFiles) {
            try {
                String sql = FileUtils.readFileToString(sqlFile, StandardCharsets.UTF_8);
                // 分割SQL语句
                String[] statements = sql.split(";");
                
                for (String statement : statements) {
                    if (StringUtils.isNotBlank(statement)) {
                        jdbcTemplate.execute(statement.trim());
                    }
                }
                
                log.info("执行SQL脚本成功: {}", sqlFile.getName());
                
            } catch (Exception e) {
                log.error("执行SQL脚本失败: {}", sqlFile.getName(), e);
                throw new BusinessException("执行SQL脚本失败: " + e.getMessage());
            }
        }
    }

    /**
     * 更新系统文件
     */
    private void updateSystemFiles(String filesPath) throws Exception {
        File filesDir = new File(filesPath);
        if (!filesDir.exists() || !filesDir.isDirectory()) {
            return;
        }
        
        // 1. 处理新增文件
        File newFilesDir = new File(filesPath + "/new");
        if (newFilesDir.exists()) {
            FileUtils.copyDirectory(newFilesDir, new File(System.getProperty("user.dir")));
        }
        
        // 2. 处理更新文件
        File updateFilesDir = new File(filesPath + "/update");
        if (updateFilesDir.exists()) {
            FileUtils.copyDirectory(updateFilesDir, new File(System.getProperty("user.dir")));
        }
        
        // 3. 处理删除文件
        File deleteListFile = new File(filesPath + "/delete/delete.txt");
        if (deleteListFile.exists()) {
            List<String> deleteFiles = FileUtils.readLines(deleteListFile, StandardCharsets.UTF_8);
            for (String file : deleteFiles) {
                FileUtils.deleteQuietly(new File(System.getProperty("user.dir"), file));
            }
        }
    }

    /**
     * 备份数据库
     */
    public String backupDatabase(String version) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String backupFile = String.format("%s/db_backup_%s_%s.sql", backupPath, version, timestamp);
            
            // 使用mysqldump命令备份
            String command = String.format("mysqldump -h%s -u%s -p%s %s > %s",
                getHostFromUrl(dbUrl),
                dbUsername,
                dbPassword,
                getDatabaseFromUrl(dbUrl),
                backupFile);
                
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            
            if (exitCode != 0) {
                throw new BusinessException("数据库备份失败");
            }
            
            log.info("数据库备份成功: {}", backupFile);
            return backupFile;
            
        } catch (Exception e) {
            log.error("数据库备份失败", e);
            throw new BusinessException("数据库备份失败: " + e.getMessage());
        }
    }

    /**
     * 还原数据库
     */
    public void restoreDatabase(String backupFile) {
        try {
            // 使用mysql命令还原
            String command = String.format("mysql -h%s -u%s -p%s %s < %s",
                getHostFromUrl(dbUrl),
                dbUsername,
                dbPassword,
                getDatabaseFromUrl(dbUrl),
                backupFile);
                
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            
            if (exitCode != 0) {
                throw new BusinessException("数据库还原失败");
            }
            
            log.info("数据库还原成功");
            
        } catch (Exception e) {
            log.error("数据库还原失败", e);
            throw new BusinessException("数据库还原失败: " + e.getMessage());
        }
    }

    /**
     * 备份系统文件
     */
    public String backupSystemFiles(String version) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String backupDir = String.format("%s/files_backup_%s_%s", backupPath, version, timestamp);
            
            // 备份整个系统目录
            FileUtils.copyDirectory(
                new File(System.getProperty("user.dir")),
                new File(backupDir),
                file -> !file.getPath().contains("backup") // 排除备份目录
            );
            
            log.info("系统文件备份成功: {}", backupDir);
            return backupDir;
            
        } catch (Exception e) {
            log.error("系统文件备份失败", e);
            throw new BusinessException("系统文件备份失败: " + e.getMessage());
        }
    }

    /**
     * 还原系统文件
     */
    public void restoreSystemFiles(String backupDir) {
        try {
            File systemDir = new File(System.getProperty("user.dir"));
            File backupDirFile = new File(backupDir);
            
            // 清空系统目录(排除特定目录)
            FileUtils.cleanDirectory(systemDir, file -> 
                !file.getPath().contains("backup") &&
                !file.getPath().contains("logs") &&
                !file.getPath().contains("temp")
            );
            
            // 还原文件
            FileUtils.copyDirectory(backupDirFile, systemDir);
            
            log.info("系统文件还原成功");
            
        } catch (Exception e) {
            log.error("系统文件还原失败", e);
            throw new BusinessException("系统文件还原失败: " + e.getMessage());
        }
    }

    /**
     * 从数据库URL中提取主机地址
     */
    private String getHostFromUrl(String url) {
        try {
            return url.substring(url.indexOf("//") + 2, url.lastIndexOf("/"));
        } catch (Exception e) {
            return "localhost";
        }
    }

    /**
     * 从数据库URL中提取数据库名
     */
    private String getDatabaseFromUrl(String url) {
        try {
            return url.substring(url.lastIndexOf("/") + 1);
        } catch (Exception e) {
            throw new BusinessException("无法获取数据库名");
        }
    }
} 