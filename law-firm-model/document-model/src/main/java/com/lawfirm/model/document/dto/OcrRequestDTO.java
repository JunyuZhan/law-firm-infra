package com.lawfirm.model.document.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * OCR识别请求DTO
 */
@Data
public class OcrRequestDTO {
    
    /**
     * 上传的图片文件
     */
    @NotNull(message = "图片文件不能为空")
    private MultipartFile imageFile;
    
    /**
     * 文档类型
     * contract-合同, id_card-身份证, business_license-营业执照, 
     * court_document-法院文书, evidence-证据材料, general-通用
     */
    @Size(max = 50, message = "文档类型长度不能超过50")
    private String documentType = "general";
    
    /**
     * OCR引擎类型
     * tesseract-开源引擎, baidu-百度, tencent-腾讯, aliyun-阿里云
     */
    @Size(max = 20, message = "OCR引擎类型长度不能超过20")
    private String engineType = "baidu";
    
    /**
     * 是否需要结构化处理
     */
    private Boolean needStructured = false;
    
    /**
     * 识别语言
     * zh-中文, en-英文, zh_en-中英混合
     */
    @Size(max = 10, message = "识别语言长度不能超过10")
    private String language = "zh_en";
    
    /**
     * 关联的业务ID（案件ID、合同ID等）
     */
    private Long businessId;
    
    /**
     * 业务类型
     */
    @Size(max = 50, message = "业务类型长度不能超过50")
    private String businessType;
} 