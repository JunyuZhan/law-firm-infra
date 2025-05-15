package com.lawfirm.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页控制器
 */
@Controller
public class IndexController {
    
    /**
     * 首页，返回静态欢迎页面
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/welcome.html";
    }
} 