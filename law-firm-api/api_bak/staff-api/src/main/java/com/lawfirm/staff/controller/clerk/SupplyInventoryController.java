package com.lawfirm.staff.controller.clerk;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 办公用品库存管理
 */
@Tag(name = "文员-办公用品库存管理")
@RestController
@RequestMapping("/staff/clerk/supply/inventory")
@RequiredArgsConstructor
public class SupplyInventoryController {
    // TODO: 实现办公用品库存相关接口
    // - 库存入库登记
    // - 库存出库登记
    // - 库存盘点管理
    // - 库存预警设置
    // - 库存统计报表
    // - 库存变动记录
    // - 低库存提醒
    // - 库存位置管理
} 