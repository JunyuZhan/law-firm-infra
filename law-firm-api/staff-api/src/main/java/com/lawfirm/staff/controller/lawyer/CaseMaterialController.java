package com.lawfirm.staff.controller.lawyer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 案件材料管理
 */
@Tag(name = "律师-案件材料管理")
@RestController
@RequestMapping("/staff/lawyer/case/material")
@RequiredArgsConstructor
public class CaseMaterialController {
    // TODO: 实现案件材料管理相关接口
    // - 材料收集管理
    //   - 材料清单维护
    //   - 材料收集登记
    //   - 材料完整性检查
    //   - 材料补充提醒
    
    // - 材料分类管理
    //   - 分类标准设置
    //   - 材料分类整理
    //   - 分类调整变更
    //   - 分类查询统计
    
    // - 材料归档管理
    //   - 归档材料整理
    //   - 归档编号生成
    //   - 归档位置记录
    //   - 归档状态跟踪
    
    // - 材料借阅管理
    //   - 借阅申请处理
    //   - 借阅记录跟踪
    //   - 归还提醒管理
    //   - 超期处理
    
    // - 材料安全管理
    //   - 材料密级设置
    //   - 访问权限控制
    //   - 操作日志记录
    //   - 安全审计跟踪
    
    // - 材料共享管理
    //   - 共享范围设置
    //   - 共享链接生成
    //   - 共享期限控制
    //   - 共享记录跟踪
} 