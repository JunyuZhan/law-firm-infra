package com.lawfirm.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class DatabaseBackupUtil {

    /**
     * 执行数据库备份
     * @param host 数据库主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @param database 数据库名
     * @param backupPath 备份路径
     * @return 备份文件路径
     */
    public String backup(String host, String port, String username, String password, String database, String backupPath) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String backupFileName = database + "_" + sdf.format(new Date()) + ".sql";
            String filePath = backupPath + File.separator + backupFileName;

            // 创建备份目录
            File backupDir = new File(backupPath);
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }

            // 构建 mysqldump 命令
            List<String> command = List.of(
                "mysqldump",
                "--host=" + host,
                "--port=" + port,
                "--user=" + username,
                "--password=" + password,
                "--databases",
                database,
                "--result-file=" + filePath,
                "--default-character-set=utf8mb4",
                "--single-transaction",
                "--skip-comments",
                "--skip-extended-insert"
            );

            // 执行备份命令
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                log.info("数据库备份成功：{}", filePath);
                return filePath;
            } else {
                log.error("数据库备份失败，退出码：{}", exitCode);
                throw new RuntimeException("数据库备份失败");
            }
        } catch (IOException | InterruptedException e) {
            log.error("数据库备份异常", e);
            throw new RuntimeException("数据库备份异常", e);
        }
    }
}