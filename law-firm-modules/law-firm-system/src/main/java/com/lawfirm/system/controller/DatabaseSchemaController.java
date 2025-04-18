package com.lawfirm.system.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.data.util.DatabaseSchemaGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 数据库表结构管理控制器
 * 仅在开发环境启用
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/db/schema")
@Tag(name = "数据库表结构管理", description = "提供数据库表结构生成和查看功能")
@Profile({"dev", "develop"})
@ConditionalOnProperty(name = "lawfirm.database.auto-generate-schema", havingValue = "true")
public class DatabaseSchemaController {

    private final DatabaseSchemaGenerator schemaGenerator;
    private final Environment environment;
    
    @Value("${lawfirm.database.schema-output-dir:./db-scripts}")
    private String schemaOutputDir;
    
    @GetMapping("/generate")
    @Operation(summary = "生成表结构", description = "触发重新生成数据库表结构SQL脚本")
    public CommonResult<String> generateSchema(
            @Parameter(description = "输出目录") @RequestParam(required = false) String outputDir
    ) {
        try {
            // 使用反射调用私有方法
            java.lang.reflect.Method method = schemaGenerator.getClass().getDeclaredMethod("generateDbScripts");
            method.setAccessible(true);
            method.invoke(schemaGenerator);
            
            return CommonResult.success("数据库表结构生成成功，脚本已输出到: " + 
                    (outputDir != null ? outputDir : schemaOutputDir));
        } catch (Exception e) {
            log.error("生成数据库表结构失败", e);
            return CommonResult.error("生成数据库表结构失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/view")
    @Operation(summary = "查看表结构", description = "查看生成的表结构SQL脚本")
    public CommonResult<String> viewSchema() {
        try {
            File sqlFile = new File(schemaOutputDir, "all-tables.sql");
            if(!sqlFile.exists()) {
                return CommonResult.error("SQL脚本文件不存在，请先生成表结构");
            }
            
            String sql = new String(Files.readAllBytes(Paths.get(sqlFile.getAbsolutePath())));
            return CommonResult.success(sql);
        } catch (Exception e) {
            log.error("读取数据库表结构脚本失败", e);
            return CommonResult.error("读取数据库表结构脚本失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/profile")
    @Operation(summary = "查看当前环境", description = "查看当前激活的环境配置")
    public CommonResult<String[]> getProfile() {
        String[] profiles = environment.getActiveProfiles();
        if(profiles.length == 0) {
            profiles = environment.getDefaultProfiles();
        }
        return CommonResult.success(profiles);
    }
} 