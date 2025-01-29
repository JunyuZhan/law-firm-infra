package com.lawfirm.common.core.constant;

public interface CommonConstants {
    // 系统状态
    Integer STATUS_NORMAL = 0;    // 正常
    Integer STATUS_DISABLE = 1;   // 禁用
    Integer STATUS_DELETE = 2;    // 删除

    // 用户类型
    Integer USER_TYPE_ADMIN = 0;     // 管理员
    Integer USER_TYPE_LAWYER = 1;    // 律师
    Integer USER_TYPE_CLERK = 2;     // 职员
    Integer USER_TYPE_CLIENT = 3;    // 客户

    // 案件状态
    Integer CASE_STATUS_DRAFT = 0;      // 草稿
    Integer CASE_STATUS_ACTIVE = 1;     // 进行中
    Integer CASE_STATUS_SUSPEND = 2;    // 暂停
    Integer CASE_STATUS_CLOSED = 3;     // 结案
    
    // 文档状态
    Integer DOC_STATUS_DRAFT = 0;       // 草稿
    Integer DOC_STATUS_REVIEW = 1;      // 审核中
    Integer DOC_STATUS_APPROVED = 2;    // 已批准
    Integer DOC_STATUS_REJECTED = 3;    // 已拒绝

    // 分页默认值
    Long DEFAULT_CURRENT = 1L;    // 默认当前页
    Long DEFAULT_SIZE = 10L;      // 默认每页大小
    String ASC = "asc";          // 升序
    String DESC = "desc";        // 降序
} 