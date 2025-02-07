package com.lawfirm.staff.controller.clerk;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 印章申请管理
 */
@Tag(name = "文员-印章申请管理")
@RestController
@RequestMapping("/staff/clerk/seal/request")
@RequiredArgsConstructor
public class SealRequestController {
    // TODO: 实现印章申请相关接口
    // - 印章申请提交
    // - 申请状态查询
    // - 申请记录管理
    // - 印章使用登记
    // - 印章归还确认
} 