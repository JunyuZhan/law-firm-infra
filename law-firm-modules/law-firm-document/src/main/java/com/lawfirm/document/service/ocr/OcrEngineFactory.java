package com.lawfirm.document.service.ocr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * OCR引擎工厂
 * 负责根据引擎类型获取对应的OCR引擎实现
 */
@Slf4j
@Component("ocrEngineFactory")  // 指定bean名称
public class OcrEngineFactory {
    
    private final Map<String, OcrEngine> ocrEngines;
    
    /**
     * 构造函数注入所有OCR引擎实现
     */
    @Autowired
    public OcrEngineFactory(List<OcrEngine> ocrEngineList) {
        this.ocrEngines = ocrEngineList.stream()
                .collect(Collectors.toMap(OcrEngine::getEngineType, Function.identity()));
        
        log.info("OCR引擎工厂初始化完成，可用引擎: {}", ocrEngines.keySet());
    }
    
    /**
     * 根据引擎类型获取OCR引擎
     * 
     * @param engineType 引擎类型
     * @return OCR引擎实例
     */
    public OcrEngine getEngine(String engineType) {
        OcrEngine engine = ocrEngines.get(engineType);
        
        if (engine == null) {
            log.warn("未找到OCR引擎: {}, 使用默认引擎", engineType);
            // 默认使用百度OCR
            engine = ocrEngines.get("baidu");
        }
        
        if (engine == null) {
            throw new IllegalStateException("没有可用的OCR引擎");
        }
        
        if (!engine.isAvailable()) {
            log.warn("OCR引擎 {} 不可用，尝试使用其他引擎", engineType);
            // 尝试找到第一个可用的引擎
            engine = ocrEngines.values().stream()
                    .filter(OcrEngine::isAvailable)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("没有可用的OCR引擎"));
        }
        
        return engine;
    }
    
    /**
     * 获取所有可用的引擎类型
     * 
     * @return 可用引擎类型列表
     */
    public List<String> getAvailableEngineTypes() {
        return ocrEngines.values().stream()
                .filter(OcrEngine::isAvailable)
                .map(OcrEngine::getEngineType)
                .toList();
    }
} 