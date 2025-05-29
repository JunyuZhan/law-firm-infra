package com.lawfirm.client.controller;

import com.lawfirm.client.service.strategy.importstrategy.ImportStrategy;
import com.lawfirm.client.service.strategy.importstrategy.ImportStrategy.ImportResult;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.client.vo.ClientVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.lawfirm.common.security.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.lawfirm.client.constant.ClientConstants;
import com.lawfirm.client.service.impl.ClientServiceImpl;
import com.lawfirm.client.util.ClientConverter;

/**
 * 客户导入导出控制器
 */
@Tag(name = "客户导入导出管理")
@Slf4j
@RestController("importExportController")
@RequiredArgsConstructor
@RequestMapping(ClientConstants.API_IMPORT_EXPORT_PREFIX)
public class ImportExportController extends BaseController {

    private final List<ImportStrategy> importStrategies;
    private final ClientServiceImpl clientService;

    /**
     * 导入客户数据
     *
     * @param file 导入文件
     * @return 导入结果
     */
    @PostMapping("/import")
    @PreAuthorize("hasAuthority('" + CLIENT_EDIT + "')")
    public CommonResult<ImportResult> importClients(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return error("上传文件不能为空");
        }

        String fileName = file.getOriginalFilename();
        ImportStrategy strategy = getImportStrategy(fileName);
        
        if (strategy == null) {
            return error("不支持的文件格式");
        }

        try {
            // 验证文件
            List<String> errors = strategy.validate(file.getInputStream());
            if (!errors.isEmpty()) {
                ImportResult result = new ImportResult();
                result.setErrorMessages(errors);
                result.setTotalCount(0);
                result.setSuccessCount(0);
                result.setFailureCount(0);
                return success(result);
            }

            // 导入数据
            Long operatorId = getCurrentUserId();
            ImportResult result = strategy.importClients(file.getInputStream(), operatorId);
            return success(result);
        } catch (IOException e) {
            log.error("导入客户数据失败", e);
            return error("导入失败：" + e.getMessage());
        }
    }

    /**
     * 下载导入模板
     *
     * @param type 模板类型（excel/csv）
     * @param response HTTP响应
     */
    @GetMapping("/template/{type}")
    @PreAuthorize("hasAuthority('" + CLIENT_EDIT + "')")
    public void downloadTemplate(@PathVariable("type") String type, HttpServletResponse response) {
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=client_import_template." + type);
            
            // 根据类型生成不同的模板
            if ("excel".equals(type)) {
                // 生成Excel模板
            } else if ("csv".equals(type)) {
                // 生成CSV模板
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("不支持的模板类型");
                return;
            }
            
            response.flushBuffer();
        } catch (IOException e) {
            log.error("下载模板失败", e);
        }
    }

    /**
     * 导出客户数据
     *
     * @param ids 客户ID数组
     * @param response HTTP响应
     */
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('" + CLIENT_EDIT + "')")
    public void exportClients(@RequestParam("ids") List<Long> ids, HttpServletResponse response) {
        try {
            // 查询客户数据
            List<ClientVO> clients = clientService.listByIds(ids).stream()
                .map(ClientConverter::toVO)
                .collect(Collectors.toList());
            // 创建Excel
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("客户数据");
            // 表头
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("客户ID");
            header.createCell(1).setCellValue("客户名称");
            header.createCell(2).setCellValue("客户编号");
            header.createCell(3).setCellValue("客户类型");
            // ...可扩展更多字段
            // 填充数据
            int rowIdx = 1;
            for (ClientVO client : clients) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(client.getId());
                row.createCell(1).setCellValue(client.getClientName());
                row.createCell(2).setCellValue(client.getClientNo());
                row.createCell(3).setCellValue(client.getClientType() != null ? client.getClientType().toString() : "");
            }
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=clients_export.xlsx");
            workbook.write(response.getOutputStream());
            workbook.close();
            response.flushBuffer();
        } catch (Exception e) {
            log.error("导出客户数据失败", e);
            try { response.getWriter().write("导出失败: " + e.getMessage()); } catch (IOException ignored) {}
        }
    }

    /**
     * 获取导入策略
     *
     * @param fileName 文件名
     * @return 导入策略
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
        // 从安全上下文获取用户ID
        return SecurityUtils.getUserId();
    }
}
