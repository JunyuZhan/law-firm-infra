-- 重命名案件表字段
ALTER TABLE law_case
    CHANGE priority case_priority varchar(20) COMMENT '优先级',
    CHANGE difficulty case_difficulty varchar(20) COMMENT '难度等级',
    CHANGE importance case_importance varchar(20) COMMENT '重要程度',
    CHANGE fee_type case_fee_type varchar(20) COMMENT '收费方式',
    CHANGE source case_source varchar(20) COMMENT '案件来源',
    CHANGE type case_type varchar(20) COMMENT '案件类型',
    CHANGE status case_status varchar(20) COMMENT '案件状态',
    CHANGE progress case_progress varchar(20) COMMENT '案件进展',
    CHANGE handle_type case_handle_type varchar(20) COMMENT '办理方式'; 