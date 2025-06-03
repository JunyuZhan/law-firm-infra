package com.lawfirm.document.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.document.dto.OcrRequestDTO;
import com.lawfirm.model.document.service.OcrService;
import com.lawfirm.model.document.vo.OcrResultVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * OCR识别控制器
 */
@Tag(name = "OCR识别", description = "OCR文字识别相关接口")
@Slf4j
@RestController
@RequestMapping("/api/document/ocr")
@RequiredArgsConstructor
@Validated
public class OcrController {
    
    private final OcrService ocrService;
    
    @Operation(summary = "图片文字识别", description = "上传图片进行OCR文字识别")
    @PostMapping("/recognize")
    public CommonResult<OcrResultVO> recognizeImage(
            @Parameter(description = "图片文件") @RequestParam("file") @NotNull MultipartFile file,
            @Parameter(description = "文档类型") @RequestParam(value = "documentType", defaultValue = "general") String documentType,
            @Parameter(description = "OCR引擎类型") @RequestParam(value = "engineType", defaultValue = "baidu") String engineType,
            @Parameter(description = "识别语言") @RequestParam(value = "language", defaultValue = "zh_en") String language,
            @Parameter(description = "是否需要结构化处理") @RequestParam(value = "needStructured", defaultValue = "false") Boolean needStructured,
            @Parameter(description = "业务ID") @RequestParam(value = "businessId", required = false) Long businessId,
            @Parameter(description = "业务类型") @RequestParam(value = "businessType", required = false) String businessType
    ) {
        log.info("接收到OCR识别请求，文件名: {}, 文档类型: {}, 引擎: {}", 
                file.getOriginalFilename(), documentType, engineType);
        
        // 构建请求DTO
        OcrRequestDTO requestDTO = new OcrRequestDTO();
        requestDTO.setImageFile(file);
        requestDTO.setDocumentType(documentType);
        requestDTO.setEngineType(engineType);
        requestDTO.setLanguage(language);
        requestDTO.setNeedStructured(needStructured);
        requestDTO.setBusinessId(businessId);
        requestDTO.setBusinessType(businessType);
        
        OcrResultVO result = ocrService.recognizeImage(requestDTO);
        
        return CommonResult.success(result);
    }
    
    @Operation(summary = "批量图片识别", description = "批量上传图片进行OCR识别")
    @PostMapping("/batch-recognize")
    public CommonResult<List<OcrResultVO>> batchRecognize(
            @Parameter(description = "图片文件列表") @RequestParam("files") @NotEmpty List<MultipartFile> files,
            @Parameter(description = "文档类型") @RequestParam(value = "documentType", defaultValue = "general") String documentType,
            @Parameter(description = "OCR引擎类型") @RequestParam(value = "engineType", defaultValue = "baidu") String engineType
    ) {
        log.info("接收到批量OCR识别请求，文件数量: {}", files.size());
        
        List<OcrResultVO> results = ocrService.batchRecognize(files, documentType, engineType);
        
        return CommonResult.success(results);
    }
    
    @Operation(summary = "获取支持的文档类型", description = "获取系统支持的文档类型列表")
    @GetMapping("/document-types")
    public CommonResult<List<String>> getSupportedDocumentTypes() {
        List<String> documentTypes = ocrService.getSupportedDocumentTypes();
        return CommonResult.success(documentTypes);
    }
    
    @Operation(summary = "获取可用的OCR引擎", description = "获取系统可用的OCR引擎列表")
    @GetMapping("/engines")
    public CommonResult<List<String>> getAvailableEngines() {
        List<String> engines = ocrService.getAvailableEngines();
        return CommonResult.success(engines);
    }
    
    @Operation(summary = "获取OCR识别历史", description = "根据业务ID和类型获取OCR识别历史记录")
    @GetMapping("/history")
    public CommonResult<List<OcrResultVO>> getOcrHistory(
            @Parameter(description = "业务ID") @RequestParam("businessId") @NotNull Long businessId,
            @Parameter(description = "业务类型") @RequestParam("businessType") @NotNull String businessType
    ) {
        List<OcrResultVO> history = ocrService.getOcrHistory(businessId, businessType);
        return CommonResult.success(history);
    }
} 