-- 更新流程模板表
ALTER TABLE wf_process_template
    ADD COLUMN suspended TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否挂起' AFTER latest,
    ADD COLUMN description VARCHAR(512) COMMENT '流程描述' AFTER remark;

-- 更新业务流程关联表
ALTER TABLE wf_business_process
    ADD COLUMN business_key VARCHAR(64) COMMENT '业务键' AFTER business_title,
    ADD COLUMN suspended TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否挂起' AFTER process_status,
    MODIFY COLUMN process_status VARCHAR(32) NOT NULL COMMENT '流程状态（0-草稿，1-运行中，2-已完成，3-已终止）';

-- 添加索引
ALTER TABLE wf_business_process
    ADD INDEX idx_business_key (business_key); 