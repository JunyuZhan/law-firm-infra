-- 恢复案件表字段名
ALTER TABLE law_case
    CHANGE case_priority priority varchar(20) COMMENT '优先级',
    CHANGE case_difficulty difficulty varchar(20) COMMENT '难度等级',
    CHANGE case_importance importance varchar(20) COMMENT '重要程度',
    CHANGE case_fee_type fee_type varchar(20) COMMENT '收费方式',
    CHANGE case_source source varchar(20) COMMENT '案件来源',
    CHANGE case_type type varchar(20) COMMENT '案件类型',
    CHANGE case_status status varchar(20) COMMENT '案件状态',
    CHANGE case_progress progress varchar(20) COMMENT '案件进展',
    CHANGE case_handle_type handle_type varchar(20) COMMENT '办理方式'; 