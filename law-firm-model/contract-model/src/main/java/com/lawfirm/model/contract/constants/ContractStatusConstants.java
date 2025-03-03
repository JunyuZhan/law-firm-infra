package com.lawfirm.model.contract.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 合同状态常量类
 */
public class ContractStatusConstants {
    public static final int DRAFT = BaseConstants.Default.STATUS_ENABLED; // 草稿状态
    public static final int UNDER_REVIEW = BaseConstants.Default.STATUS_ENABLED; // 审核中状态
    public static final int EFFECTIVE = BaseConstants.Default.STATUS_ENABLED; // 已生效状态
    public static final int TERMINATED = BaseConstants.Default.STATUS_DISABLED; // 已终止状态
    public static final int EXPIRED = BaseConstants.Default.STATUS_DISABLED; // 已到期状态
} 