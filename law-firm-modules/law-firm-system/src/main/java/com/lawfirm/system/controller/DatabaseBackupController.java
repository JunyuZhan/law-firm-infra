package com.lawfirm.system.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.util.DatabaseBackupUtil;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawfirm.system.constant.SystemConstants;

import java.io.File;
import java.net.URI;

/**
 * 数据库备份控制器
 */
@Tag(name = "数据库备份", description = "提供数据库备份相关接口")
@RestController("databaseBackupController")
@RequestMapping(SystemConstants.API_DATABASE_PREFIX)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DatabaseBackupController extends BaseController {

    private final DatabaseBackupUtil databaseBackupUtil;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 备份数据库
     */
    @Operation(
        summary = "备份数据库",
        description = "创建数据库备份文件并提供下载"
    )
    @GetMapping("/backup")
    @RequiresPermissions("system:database:backup")
    @Log(title = "数据库备份", businessType = "EXPORT")
    public ResponseEntity<FileSystemResource> backupDatabase() {
        try {
            // 解析数据库连接URL
            URI uri = new URI(datasourceUrl.substring(5)); // 去掉"jdbc:"前缀
            String host = uri.getHost();
            String port = String.valueOf(uri.getPort());
            String database = uri.getPath().substring(1); // 去掉开头的"/"

            // 执行备份
            String backupPath = databaseBackupUtil.backup(host, port, username, password, database, "backup");

            // 返回备份文件
            File backupFile = new File(backupPath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", backupFile.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new FileSystemResource(backupFile));
        } catch (Exception e) {
            throw new RuntimeException("数据库备份失败", e);
        }
    }
} 