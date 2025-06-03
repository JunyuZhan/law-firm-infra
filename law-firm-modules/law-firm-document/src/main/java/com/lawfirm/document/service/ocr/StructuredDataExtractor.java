package com.lawfirm.document.service.ocr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 结构化数据提取器
 * 根据文档类型从OCR识别的文本中提取结构化信息
 */
@Slf4j
@Component("structuredDataExtractor")  // 指定bean名称
public class StructuredDataExtractor {
    
    /**
     * 提取结构化数据
     * 
     * @param rawText 原始识别文本
     * @param documentType 文档类型
     * @return 结构化数据
     */
    public Map<String, Object> extract(String rawText, String documentType) {
        if (rawText == null || rawText.trim().isEmpty()) {
            return new HashMap<>();
        }
        
        return switch (documentType) {
            case "id_card" -> extractIdCardInfo(rawText);
            case "business_license" -> extractBusinessLicenseInfo(rawText);
            case "contract" -> extractContractInfo(rawText);
            case "court_document" -> extractCourtDocumentInfo(rawText);
            case "evidence" -> extractEvidenceInfo(rawText);
            case "invoice" -> extractInvoiceInfo(rawText);
            case "bank_card" -> extractBankCardInfo(rawText);
            default -> extractGeneralInfo(rawText);
        };
    }
    
    /**
     * 提取身份证信息
     */
    private Map<String, Object> extractIdCardInfo(String text) {
        Map<String, Object> result = new HashMap<>();
        
        // 姓名
        Pattern namePattern = Pattern.compile("姓名[：:]?\\s*([\\u4e00-\\u9fa5]{2,4})");
        Matcher nameMatcher = namePattern.matcher(text);
        if (nameMatcher.find()) {
            result.put("name", nameMatcher.group(1));
        }
        
        // 性别
        Pattern genderPattern = Pattern.compile("性别[：:]?\\s*([男女])");
        Matcher genderMatcher = genderPattern.matcher(text);
        if (genderMatcher.find()) {
            result.put("gender", genderMatcher.group(1));
        }
        
        // 民族
        Pattern ethnicPattern = Pattern.compile("民族[：:]?\\s*([\\u4e00-\\u9fa5]+)");
        Matcher ethnicMatcher = ethnicPattern.matcher(text);
        if (ethnicMatcher.find()) {
            result.put("ethnicity", ethnicMatcher.group(1));
        }
        
        // 出生日期
        Pattern birthPattern = Pattern.compile("出生[：:]?\\s*(\\d{4})年(\\d{1,2})月(\\d{1,2})日");
        Matcher birthMatcher = birthPattern.matcher(text);
        if (birthMatcher.find()) {
            result.put("birthDate", birthMatcher.group(1) + "-" + 
                      String.format("%02d", Integer.parseInt(birthMatcher.group(2))) + "-" +
                      String.format("%02d", Integer.parseInt(birthMatcher.group(3))));
        }
        
        // 身份证号
        Pattern idPattern = Pattern.compile("(\\d{17}[\\dXx])");
        Matcher idMatcher = idPattern.matcher(text);
        if (idMatcher.find()) {
            result.put("idNumber", idMatcher.group(1));
        }
        
        // 地址
        Pattern addressPattern = Pattern.compile("住址[：:]?\\s*([\\u4e00-\\u9fa5\\d\\s]+)");
        Matcher addressMatcher = addressPattern.matcher(text);
        if (addressMatcher.find()) {
            result.put("address", addressMatcher.group(1).trim());
        }
        
        return result;
    }
    
    /**
     * 提取营业执照信息
     */
    private Map<String, Object> extractBusinessLicenseInfo(String text) {
        Map<String, Object> result = new HashMap<>();
        
        // 企业名称
        Pattern companyPattern = Pattern.compile("名称[：:]?\\s*([\\u4e00-\\u9fa5\\(\\)（）\\d]+)");
        Matcher companyMatcher = companyPattern.matcher(text);
        if (companyMatcher.find()) {
            result.put("companyName", companyMatcher.group(1));
        }
        
        // 统一社会信用代码
        Pattern creditCodePattern = Pattern.compile("统一社会信用代码[：:]?\\s*([A-Z0-9]{18})");
        Matcher creditCodeMatcher = creditCodePattern.matcher(text);
        if (creditCodeMatcher.find()) {
            result.put("creditCode", creditCodeMatcher.group(1));
        }
        
        // 法定代表人
        Pattern legalRepPattern = Pattern.compile("法定代表人[：:]?\\s*([\\u4e00-\\u9fa5]{2,4})");
        Matcher legalRepMatcher = legalRepPattern.matcher(text);
        if (legalRepMatcher.find()) {
            result.put("legalRepresentative", legalRepMatcher.group(1));
        }
        
        // 注册资本
        Pattern capitalPattern = Pattern.compile("注册资本[：:]?\\s*([\\d.]+)万?元");
        Matcher capitalMatcher = capitalPattern.matcher(text);
        if (capitalMatcher.find()) {
            result.put("registeredCapital", capitalMatcher.group(1));
        }
        
        return result;
    }
    
    /**
     * 提取合同信息
     */
    private Map<String, Object> extractContractInfo(String text) {
        Map<String, Object> result = new HashMap<>();
        
        // 合同编号
        Pattern contractNoPattern = Pattern.compile("合同编号[：:]?\\s*([A-Z0-9\\-]+)");
        Matcher contractNoMatcher = contractNoPattern.matcher(text);
        if (contractNoMatcher.find()) {
            result.put("contractNumber", contractNoMatcher.group(1));
        }
        
        // 甲方
        Pattern partyAPattern = Pattern.compile("甲方[：:]?\\s*([\\u4e00-\\u9fa5\\(\\)（）\\d]+)");
        Matcher partyAMatcher = partyAPattern.matcher(text);
        if (partyAMatcher.find()) {
            result.put("partyA", partyAMatcher.group(1));
        }
        
        // 乙方
        Pattern partyBPattern = Pattern.compile("乙方[：:]?\\s*([\\u4e00-\\u9fa5\\(\\)（）\\d]+)");
        Matcher partyBMatcher = partyBPattern.matcher(text);
        if (partyBMatcher.find()) {
            result.put("partyB", partyBMatcher.group(1));
        }
        
        // 合同金额
        Pattern amountPattern = Pattern.compile("金额[：:]?\\s*([\\d,.]+)元");
        Matcher amountMatcher = amountPattern.matcher(text);
        if (amountMatcher.find()) {
            result.put("contractAmount", amountMatcher.group(1));
        }
        
        // 签订日期
        Pattern datePattern = Pattern.compile("(\\d{4})年(\\d{1,2})月(\\d{1,2})日");
        Matcher dateMatcher = datePattern.matcher(text);
        if (dateMatcher.find()) {
            result.put("signDate", dateMatcher.group(1) + "-" + 
                      String.format("%02d", Integer.parseInt(dateMatcher.group(2))) + "-" +
                      String.format("%02d", Integer.parseInt(dateMatcher.group(3))));
        }
        
        return result;
    }
    
    /**
     * 提取法院文书信息
     */
    private Map<String, Object> extractCourtDocumentInfo(String text) {
        Map<String, Object> result = new HashMap<>();
        
        // 案件编号
        Pattern caseNoPattern = Pattern.compile("案件编号[：:]?\\s*\\(([\\d]+)\\)([\\u4e00-\\u9fa5]+)([\\d]+)号");
        Matcher caseNoMatcher = caseNoPattern.matcher(text);
        if (caseNoMatcher.find()) {
            result.put("caseNumber", "(" + caseNoMatcher.group(1) + ")" + 
                      caseNoMatcher.group(2) + caseNoMatcher.group(3) + "号");
        }
        
        // 审理法院
        Pattern courtPattern = Pattern.compile("([\\u4e00-\\u9fa5]+人民法院)");
        Matcher courtMatcher = courtPattern.matcher(text);
        if (courtMatcher.find()) {
            result.put("court", courtMatcher.group(1));
        }
        
        // 当事人
        Pattern partyPattern = Pattern.compile("当事人[：:]?\\s*([\\u4e00-\\u9fa5]{2,4})");
        Matcher partyMatcher = partyPattern.matcher(text);
        if (partyMatcher.find()) {
            result.put("party", partyMatcher.group(1));
        }
        
        return result;
    }
    
    /**
     * 提取证据材料信息
     */
    private Map<String, Object> extractEvidenceInfo(String text) {
        Map<String, Object> result = new HashMap<>();
        
        // 证据编号
        Pattern evidenceNoPattern = Pattern.compile("证据[：:]?\\s*([A-Z0-9\\-]+)");
        Matcher evidenceNoMatcher = evidenceNoPattern.matcher(text);
        if (evidenceNoMatcher.find()) {
            result.put("evidenceNumber", evidenceNoMatcher.group(1));
        }
        
        // 提取关键词
        result.put("keywords", extractKeywords(text));
        
        return result;
    }
    
    /**
     * 提取发票信息
     */
    private Map<String, Object> extractInvoiceInfo(String text) {
        Map<String, Object> result = new HashMap<>();
        
        // 发票号码
        Pattern invoiceNoPattern = Pattern.compile("发票号码[：:]?\\s*([\\d]+)");
        Matcher invoiceNoMatcher = invoiceNoPattern.matcher(text);
        if (invoiceNoMatcher.find()) {
            result.put("invoiceNumber", invoiceNoMatcher.group(1));
        }
        
        // 金额
        Pattern amountPattern = Pattern.compile("金额[：:]?\\s*￥([\\d,.]+)");
        Matcher amountMatcher = amountPattern.matcher(text);
        if (amountMatcher.find()) {
            result.put("amount", amountMatcher.group(1));
        }
        
        return result;
    }
    
    /**
     * 提取银行卡信息
     */
    private Map<String, Object> extractBankCardInfo(String text) {
        Map<String, Object> result = new HashMap<>();
        
        // 卡号
        Pattern cardNoPattern = Pattern.compile("(\\d{4}\\s*\\d{4}\\s*\\d{4}\\s*\\d{4})");
        Matcher cardNoMatcher = cardNoPattern.matcher(text);
        if (cardNoMatcher.find()) {
            result.put("cardNumber", cardNoMatcher.group(1).replaceAll("\\s", ""));
        }
        
        // 银行名称
        Pattern bankPattern = Pattern.compile("([\\u4e00-\\u9fa5]+银行)");
        Matcher bankMatcher = bankPattern.matcher(text);
        if (bankMatcher.find()) {
            result.put("bankName", bankMatcher.group(1));
        }
        
        return result;
    }
    
    /**
     * 提取通用信息
     */
    private Map<String, Object> extractGeneralInfo(String text) {
        Map<String, Object> result = new HashMap<>();
        
        // 提取日期
        Pattern datePattern = Pattern.compile("(\\d{4})[年\\-/](\\d{1,2})[月\\-/](\\d{1,2})[日]?");
        Matcher dateMatcher = datePattern.matcher(text);
        if (dateMatcher.find()) {
            result.put("date", dateMatcher.group(1) + "-" + 
                      String.format("%02d", Integer.parseInt(dateMatcher.group(2))) + "-" +
                      String.format("%02d", Integer.parseInt(dateMatcher.group(3))));
        }
        
        // 提取金额
        Pattern amountPattern = Pattern.compile("([\\d,.]+)元");
        Matcher amountMatcher = amountPattern.matcher(text);
        if (amountMatcher.find()) {
            result.put("amount", amountMatcher.group(1));
        }
        
        // 提取关键词
        result.put("keywords", extractKeywords(text));
        
        return result;
    }
    
    /**
     * 提取关键词
     */
    private java.util.List<String> extractKeywords(String text) {
        // 简化实现，实际可以使用NLP技术提取关键词
        java.util.List<String> keywords = new java.util.ArrayList<>();
        
        // 法律相关关键词
        String[] legalKeywords = {"合同", "协议", "法院", "判决", "起诉", "被告", "原告", 
                                 "证据", "律师", "法律", "条款", "违约", "赔偿", "责任"};
        
        for (String keyword : legalKeywords) {
            if (text.contains(keyword)) {
                keywords.add(keyword);
            }
        }
        
        return keywords;
    }
} 