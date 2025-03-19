package com.lawfirm.api.adaptor.client;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.client.service.strategy.importstrategy.ImportStrategy;
import com.lawfirm.client.service.strategy.importstrategy.ImportStrategy.ImportResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 客户导入导出适配器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImportExportAdaptor extends BaseAdaptor {

    private final List<ImportStrategy> importStrategies;

    /**
     * 导入客户数据
     */
    public ImportResult importClients(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        String fileName = file.getOriginalFilename();
        ImportStrategy strategy = getImportStrategy(fileName);
        
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的文件格式");
        }

        // 验证文件
        List<String> errors = strategy.validate(file.getInputStream());
        if (!errors.isEmpty()) {
            ImportResult result = new ImportResult();
            result.setErrorMessages(errors);
            result.setTotalCount(0);
            result.setSuccessCount(0);
            result.setFailureCount(0);
            return result;
        }

        // 导入数据
        Long operatorId = getCurrentUserId();
        return strategy.importClients(file.getInputStream(), operatorId);
    }

    /**
     * 下载导入模板
     */
    public void downloadTemplate(String type, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=client_import_template." + type);
        
        // 根据类型生成不同的模板
        if ("excel".equals(type)) {
            // 生成Excel模板
            // TODO: 实现Excel模板生成
        } else if ("csv".equals(type)) {
            // 生成CSV模板
            // TODO: 实现CSV模板生成
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("不支持的模板类型");
            return;
        }
        
        response.flushBuffer();
    }

    /**
     * 导出客户数据
     */
    public void exportClients(List<Long> ids, HttpServletResponse response) {
        // TODO: 实现客户数据导出
    }

    /**
     * 获取导入策略
     */
    private ImportStrategy getImportStrategy(String fileName) {
        if (fileName == null) {
            return null;
        }

        // 将策略列表转换为Map，以便快速查找
        Map<String, ImportStrategy> strategyMap = importStrategies.stream()
                .collect(Collectors.toMap(ImportStrategy::getType, Function.identity()));

        // 根据文件扩展名选择策略
        String lowerFileName = fileName.toLowerCase();
        if (lowerFileName.endsWith(".xlsx") || lowerFileName.endsWith(".xls")) {
            return strategyMap.get("excel");
        } else if (lowerFileName.endsWith(".csv")) {
            return strategyMap.get("csv");
        }

        // 如果没有匹配的策略，尝试使用supports方法查找
        return importStrategies.stream()
                .filter(strategy -> strategy.supports(fileName))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取当前用户ID
     */
    private Long getCurrentUserId() {
        // TODO: 实现从安全上下文获取用户ID
        return 1L; // 默认管理员ID
    }
} 