package com.lawfirm.model.ai.enums;

/**
 * AI模型状态枚举
 */
public enum ModelStatus {
    TRAINING, // 训练中
    TRAINED,  // 已训练
    DEPLOYED, // 已部署
    FAILED;    // 失败
} 