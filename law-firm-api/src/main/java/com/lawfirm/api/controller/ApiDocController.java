package com.lawfirm.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;

/**
 * API文档控制器
 * <p>
 * 提供API文档入口
 * </p>
 */
@Slf4j
@Controller("apiDocController")
@RequestMapping
public class ApiDocController {

    /**
     * 重定向到API文档首页
     */
    @GetMapping({"/", "/index"})
    public String index() {
        return "redirect:/doc.html";
    }
    
    /**
     * 重定向到Knife4j文档页面
     */
    @GetMapping("/knife4j")
    public String knife4j() {
        return "redirect:/doc.html";
    }

    /**
     * API文档页面
     */
    @GetMapping("/api-docs")
    public String apiDocs() {
        return "redirect:/doc.html";
    }
} 