package com.lawfirm.document.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.document.config.OnlyOfficeConfig;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.auth.entity.User;
import com.lawfirm.model.auth.service.UserService;
import com.lawfirm.common.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * OnlyOffice集成服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "lawfirm.onlyoffice.enabled", havingValue = "true")
public class OnlyOfficeService {

    private final OnlyOfficeConfig onlyOfficeConfig;
    private final DocumentService documentService;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    /**
     * 生成编辑配置
     *
     * @param documentId 文档ID
     * @return 编辑配置JSON
     */
    public Map<String, Object> generateEditorConfig(Long documentId) {
        try {
            // 获取文档信息
            BaseDocument document = documentService.getById(documentId);
            if (document == null) {
                throw new IllegalArgumentException("文档不存在");
            }

            // 获取当前用户
            User currentUser = getCurrentUser();

            // 构建配置
            Map<String, Object> config = new HashMap<>();
            
            // 文档配置
            Map<String, Object> documentConfig = new HashMap<>();
            documentConfig.put("fileType", getFileExtension(document.getFileName()));
            documentConfig.put("key", generateDocumentKey(document));
            documentConfig.put("title", document.getTitle());
            documentConfig.put("url", getDocumentDownloadUrl(document));

            // 权限配置
            Map<String, Object> permissions = new HashMap<>();
            permissions.put("edit", hasEditPermission(currentUser, document));
            permissions.put("download", hasDownloadPermission(currentUser, document));
            permissions.put("print", hasPrintPermission(currentUser, document));
            permissions.put("review", hasReviewPermission(currentUser, document));
            permissions.put("comment", hasCommentPermission(currentUser, document));
            documentConfig.put("permissions", permissions);

            config.put("document", documentConfig);

            // 编辑器配置
            Map<String, Object> editorConfig = new HashMap<>();
            editorConfig.put("mode", getEditorMode(currentUser, document));
            editorConfig.put("lang", "zh");
            editorConfig.put("callbackUrl", onlyOfficeConfig.getCallbackUrl() + "/" + documentId);

            // 用户配置
            Map<String, Object> user = new HashMap<>();
            user.put("id", currentUser.getId().toString());
            user.put("name", currentUser.getUsername());
            editorConfig.put("user", user);

            // 协同编辑配置
            if (onlyOfficeConfig.getCoEdit().isEnabled()) {
                Map<String, Object> coEditing = new HashMap<>();
                coEditing.put("mode", "fast");
                coEditing.put("change", true);
                editorConfig.put("coEditing", coEditing);
            }

            config.put("editorConfig", editorConfig);

            // JWT签名
            if (onlyOfficeConfig.getSecret() != null && !onlyOfficeConfig.getSecret().isEmpty()) {
                String token = generateJwtToken(config);
                config.put("token", token);
            }

            return config;

        } catch (Exception e) {
            log.error("生成OnlyOffice编辑配置失败", e);
            throw new RuntimeException("生成编辑配置失败", e);
        }
    }

    /**
     * 处理OnlyOffice回调
     *
     * @param documentId 文档ID
     * @param callbackData 回调数据
     * @return 处理结果
     */
    public Map<String, Object> handleCallback(Long documentId, Map<String, Object> callbackData) {
        try {
            log.info("OnlyOffice回调: documentId={}, data={}", documentId, callbackData);

            Integer status = (Integer) callbackData.get("status");
            String downloadUrl = (String) callbackData.get("url");

            Map<String, Object> response = new HashMap<>();
            response.put("error", 0);

            switch (status) {
                case 1: // 编辑中
                    log.debug("文档编辑中: {}", documentId);
                    break;
                    
                case 2: // 准备保存
                case 3: // 保存错误
                    log.debug("文档保存状态: {}, documentId: {}", status, documentId);
                    break;
                    
                case 4: // 关闭编辑器，需要保存文档
                    if (downloadUrl != null && !downloadUrl.isEmpty()) {
                        saveDocumentFromCallback(documentId, downloadUrl);
                        log.info("文档保存成功: {}", documentId);
                    }
                    break;
                    
                case 6: // 强制保存
                case 7: // 错误保存
                    if (downloadUrl != null && !downloadUrl.isEmpty()) {
                        saveDocumentFromCallback(documentId, downloadUrl);
                    }
                    break;
                    
                default:
                    log.warn("未知的回调状态: {}", status);
            }

            return response;

        } catch (Exception e) {
            log.error("处理OnlyOffice回调失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", 1);
            errorResponse.put("message", e.getMessage());
            return errorResponse;
        }
    }

    /**
     * 下载文档供OnlyOffice访问
     *
     * @param documentId 文档ID
     * @param response HTTP响应对象
     */
    public void downloadDocument(Long documentId, jakarta.servlet.http.HttpServletResponse response) {
        try {
            log.info("OnlyOffice请求下载文档: documentId={}", documentId);
            
            // 获取文档信息
            BaseDocument document = documentService.getById(documentId);
            if (document == null) {
                response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // 权限检查
            User currentUser = getCurrentUser();
            if (!hasDownloadPermission(currentUser, document)) {
                response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // 获取文档内容流
            java.io.InputStream documentStream = documentService.downloadDocument(documentId);
            if (documentStream == null) {
                response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // 设置响应头
            response.setContentType(getContentType(document.getFileName()));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + document.getFileName() + "\"");
            if (document.getFileSize() != null) {
                response.setContentLengthLong(document.getFileSize());
            }
            
            // 允许OnlyOffice跨域访问
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

            // 输出文件流
            try (java.io.OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = documentStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }

            log.info("文档下载完成: documentId={}, fileName={}", documentId, document.getFileName());

        } catch (Exception e) {
            log.error("下载文档失败: documentId={}", documentId, e);
            try {
                response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (Exception ignored) {
                // 响应可能已经提交，忽略异常
            }
        }
    }

    /**
     * 从回调URL保存文档
     */
    private void saveDocumentFromCallback(Long documentId, String downloadUrl) {
        try {
            // 从OnlyOffice下载更新后的文档
            byte[] documentBytes = restTemplate.getForObject(downloadUrl, byte[].class);
            
            if (documentBytes != null) {
                // 保存到存储系统
                documentService.updateDocumentContent(documentId, 
                    new java.io.ByteArrayInputStream(documentBytes));
                
                log.info("文档内容更新成功: {}", documentId);
            }
        } catch (Exception e) {
            log.error("从回调URL保存文档失败: documentId={}, url={}", documentId, downloadUrl, e);
            throw new RuntimeException("保存文档失败", e);
        }
    }

    /**
     * 生成文档密钥
     */
    private String generateDocumentKey(BaseDocument document) {
        // 使用文档ID+修改时间生成唯一密钥，修复LocalDateTime.getTime()错误
        String keySource = document.getId() + "_" + 
            (document.getUpdateTime() != null ? 
                document.getUpdateTime().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli() : 
                document.getCreateTime().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli());
        return Base64.getEncoder().encodeToString(keySource.getBytes());
    }

    /**
     * 获取文档下载URL
     */
    private String getDocumentDownloadUrl(BaseDocument document) {
        // 生成公开的文档下载URL
        return onlyOfficeConfig.getCallbackUrl().replace("/callback", "/download/" + document.getId());
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        }
        return "txt";
    }

    /**
     * 获取编辑器模式
     */
    private String getEditorMode(User user, BaseDocument document) {
        return hasEditPermission(user, document) ? "edit" : "view";
    }

    /**
     * 根据文件名获取Content-Type
     */
    private String getContentType(String fileName) {
        if (fileName == null) {
            return "application/octet-stream";
        }
        
        String extension = getFileExtension(fileName);
        switch (extension.toLowerCase()) {
            case "doc":
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt":
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "pdf":
                return "application/pdf";
            case "txt":
                return "text/plain";
            case "html":
            case "htm":
                return "text/html";
            case "json":
                return "application/json";
            case "xml":
                return "text/xml";
            default:
                return "application/octet-stream";
        }
    }

    /**
     * 生成JWT Token
     */
    private String generateJwtToken(Map<String, Object> payload) throws Exception {
        String header = Base64.getEncoder().encodeToString(
            "{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes(StandardCharsets.UTF_8));
        
        String payloadJson = objectMapper.writeValueAsString(payload);
        String payloadBase64 = Base64.getEncoder().encodeToString(
            payloadJson.getBytes(StandardCharsets.UTF_8));
        
        String signatureInput = header + "." + payloadBase64;
        
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(
            onlyOfficeConfig.getSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKey);
        
        byte[] signatureBytes = mac.doFinal(signatureInput.getBytes(StandardCharsets.UTF_8));
        String signature = Base64.getEncoder().encodeToString(signatureBytes);
        
        return header + "." + payloadBase64 + "." + signature;
    }

    /**
     * 获取当前用户
     */
    private User getCurrentUser() {
        // 使用正确的SecurityUtils方法
        Long userId = SecurityUtils.getUserId();
        return userService.getById(userId);
    }

    // 权限检查方法
    private boolean hasEditPermission(User user, BaseDocument document) {
        return SecurityUtils.hasPermission("document:edit");
    }

    private boolean hasDownloadPermission(User user, BaseDocument document) {
        return SecurityUtils.hasPermission("document:view");
    }

    private boolean hasPrintPermission(User user, BaseDocument document) {
        return SecurityUtils.hasPermission("document:view");
    }

    private boolean hasReviewPermission(User user, BaseDocument document) {
        return SecurityUtils.hasPermission("document:edit");
    }

    private boolean hasCommentPermission(User user, BaseDocument document) {
        return SecurityUtils.hasPermission("document:edit");
    }
} 