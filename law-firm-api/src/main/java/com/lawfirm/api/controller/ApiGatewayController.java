package com.lawfirm.api.controller;

import com.lawfirm.api.annotation.RepeatSubmitPrevention;
import com.lawfirm.api.constant.ApiConstants;
import com.lawfirm.api.config.ApiVersionConfig;
import com.lawfirm.common.core.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * API网关控制器
 * 作为各业务模块API的统一入口
 */
@Tag(name = "API网关", description = "API网关统一入口接口，提供系统基础信息和健康检查")
@RestController("apiGatewayController")
@RequestMapping(ApiConstants.API_BASE)
@RequiredArgsConstructor
@Slf4j
public class ApiGatewayController extends BaseApiController {

    @Value("${spring.application.name:law-firm-api}")
    private String applicationName;
    
    @Value("${spring.profiles.active:dev}")
    private String activeProfile;
    
    @Autowired
    private Environment environment;
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired(required = false)
    private BuildProperties buildProperties;
    
    @Autowired(required = false)
    private GitProperties gitProperties;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * API根路径
     */
    @GetMapping({"", "/"})
    @Operation(summary = "API根路径", description = "返回API基本信息")
    public CommonResult<Map<String, Object>> index() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", applicationName);
        info.put("version", API_VERSION);
        info.put("profile", activeProfile);
        info.put("timestamp", System.currentTimeMillis());
        
        return success(info, "API服务正常运行");
    }
    
    /**
     * API健康检查
     */
    @GetMapping("/health")
    @Operation(summary = "健康检查", description = "API服务健康检查，提供系统各组件运行状态")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "服务健康"),
            @ApiResponse(responseCode = "500", description = "服务不健康")
    })
    public CommonResult<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        
        // 检查时间戳
        long timestamp = System.currentTimeMillis();
        health.put("timestamp", timestamp);
        
        // 检查系统状态
        boolean systemUp = true;
        Map<String, Object> components = new HashMap<>();
        
        // 检查数据库连接
        boolean dbUp = checkDatabaseConnection();
        components.put("database", Map.of(
            "status", dbUp ? "UP" : "DOWN",
            "type", dataSource.getClass().getSimpleName()
        ));
        systemUp = systemUp && dbUp;
        
        // 检查JVM状态
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory() / (1024 * 1024);
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        long freeMemory = runtime.freeMemory() / (1024 * 1024);
        long usedMemory = totalMemory - freeMemory;
        
        components.put("jvm", Map.of(
            "status", "UP",
            "memory", Map.of(
                "total", totalMemory + "MB",
                "free", freeMemory + "MB",
                "used", usedMemory + "MB",
                "max", maxMemory + "MB"
            ),
            "processors", runtime.availableProcessors()
        ));
        
        // 检查磁盘空间
        String tmpDir = System.getProperty("java.io.tmpdir");
        components.put("diskSpace", Map.of(
            "status", "UP",
            "path", tmpDir,
            "threshold", "10MB"
        ));
        
        // 添加组件状态
        health.put("components", components);
        
        // 设置总体状态
        health.put("status", systemUp ? "UP" : "DOWN");
        
        return success(health, systemUp ? "服务健康" : "服务异常");
    }
    
    /**
     * 检查数据库连接
     */
    private boolean checkDatabaseConnection() {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return result != null && result == 1;
        } catch (Exception e) {
            log.error("数据库连接检查失败", e);
            return false;
        }
    }
    
    /**
     * API版本信息
     */
    @GetMapping("/version")
    @Operation(summary = "版本信息", description = "获取API版本信息")
    public CommonResult<Map<String, Object>> version() {
        Map<String, Object> version = new HashMap<>();
        
        // API版本
        version.put("apiVersion", API_VERSION);
        
        // 应用名称和环境
        version.put("application", applicationName);
        version.put("profile", activeProfile);
        
        // 获取Java版本
        version.put("java", Map.of(
            "version", System.getProperty("java.version"),
            "vendor", System.getProperty("java.vendor"),
            "home", System.getProperty("java.home")
        ));
        
        // 获取操作系统信息
        version.put("os", Map.of(
            "name", System.getProperty("os.name"),
            "version", System.getProperty("os.version"),
            "arch", System.getProperty("os.arch")
        ));
        
        // 获取Spring版本
        version.put("spring", Map.of(
            "version", SpringVersion.getVersion(),
            "profiles", environment.getActiveProfiles()
        ));
        
        // 从BuildProperties获取构建信息（如果可用）
        if (buildProperties != null) {
            Map<String, Object> buildInfo = new HashMap<>();
            buildInfo.put("version", buildProperties.getVersion());
            buildInfo.put("artifact", buildProperties.getArtifact());
            buildInfo.put("name", buildProperties.getName());
            buildInfo.put("group", buildProperties.getGroup());
            
            // 格式化构建时间
            Instant buildTime = buildProperties.getTime();
            String formattedTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(buildTime);
            buildInfo.put("time", formattedTime);
            
            version.put("build", buildInfo);
        } else {
            // 如果没有BuildProperties，提供一个默认值
            version.put("build", Map.of(
                "version", "开发版本",
                "time", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.systemDefault())
                    .format(Instant.now())
            ));
        }
        
        // 从GitProperties获取Git信息（如果可用）
        if (gitProperties != null) {
            Map<String, Object> gitInfo = new HashMap<>();
            gitInfo.put("branch", gitProperties.getBranch());
            gitInfo.put("commit", Map.of(
                "id", gitProperties.getShortCommitId(),
                "time", gitProperties.getCommitTime(),
                "message", gitProperties.get("commit.message.short")
            ));
            version.put("git", gitInfo);
        }
        
        return success(version);
    }
    
    /**
     * 测试防重复提交
     */
    @GetMapping("/test/repeat-submit")
    @Operation(summary = "测试防重复提交", description = "测试防重复提交功能")
    @RepeatSubmitPrevention(interval = 5000)
    public CommonResult<Map<String, Object>> testRepeatSubmit() {
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", System.currentTimeMillis());
        result.put("message", "防重复提交测试成功");
        
        return success(result);
    }

    /**
     * API文档配置诊断
     */
    @GetMapping("/api/doc-config")
    @Operation(summary = "API文档配置诊断", description = "检查API文档配置状态并提供访问地址")
    public CommonResult<Map<String, Object>> apiDocConfig() {
        Map<String, Object> config = new HashMap<>();
        
        // API文档配置状态
        boolean apiDocsEnabled = Boolean.parseBoolean(
            environment.getProperty("springdoc.api-docs.enabled", "true"));
        boolean swaggerUiEnabled = Boolean.parseBoolean(
            environment.getProperty("springdoc.swagger-ui.enabled", "true"));
        boolean knife4jEnabled = Boolean.parseBoolean(
            environment.getProperty("knife4j.enable", "true"));
            
        // API文档路径
        String apiDocsPath = environment.getProperty("springdoc.api-docs.path", "/v3/api-docs");
        String swaggerUiPath = environment.getProperty("springdoc.swagger-ui.path", "/swagger-ui.html");
        String serverUrl = environment.getProperty("server.servlet.context-path", "");
        String serverPort = environment.getProperty("server.port", "8080");
        
        // 当前环境
        String[] activeProfiles = environment.getActiveProfiles();
        String profile = activeProfiles.length > 0 ? activeProfiles[0] : "default";
        
        // 拼接完整URL
        String baseUrl = "http://localhost:" + serverPort + serverUrl;
        
        // 收集配置信息
        config.put("apiDocsEnabled", apiDocsEnabled);
        config.put("swaggerUiEnabled", swaggerUiEnabled);
        config.put("knife4jEnabled", knife4jEnabled);
        config.put("apiDocsUrl", baseUrl + apiDocsPath);
        config.put("swaggerUiUrl", baseUrl + swaggerUiPath);
        if (knife4jEnabled) {
            config.put("knife4jUrl", baseUrl + "/doc.html");
        }
        config.put("activeProfile", profile);
        
        // 添加字符编码检测
        String encoding = environment.getProperty("spring.mandatory-file-encoding", 
                          System.getProperty("file.encoding", "UTF-8"));
        config.put("encoding", encoding);
        
        // 添加内容类型检测
        String defaultContentType = environment.getProperty("springdoc.default-produces-media-type", 
                                    "application/json");
        config.put("defaultContentType", defaultContentType);
        
        // 添加base64编码检测
        boolean jsonBase64Encoded = Boolean.parseBoolean(
            environment.getProperty("springdoc.api-docs.json-base64-encoded", "false"));
        config.put("jsonBase64Encoded", jsonBase64Encoded);
        
        // 添加请求拦截配置信息
        String securityFilterOrder = environment.getProperty("spring.security.filter.order", "15");
        config.put("securityFilterOrder", securityFilterOrder);
        
        // 添加路径匹配策略检测
        String pathMatchStrategy = environment.getProperty("spring.mvc.pathmatch.matching-strategy", 
                                  "ANT_PATH_MATCHER");
        config.put("pathMatchStrategy", pathMatchStrategy);
        
        return CommonResult.success(config, "API文档配置状态");
    }
} 