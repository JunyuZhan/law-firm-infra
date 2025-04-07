-- 创建统计分析记录表
CREATE TABLE IF NOT EXISTS `analysis_record` (
    `id` bigint NOT NULL COMMENT '主键ID',
    `analysis_type` varchar(32) NOT NULL COMMENT '分析类型',
    `start_time` datetime NOT NULL COMMENT '分析开始时间',
    `end_time` datetime NOT NULL COMMENT '分析结束时间',
    `result_json` text COMMENT '分析结果JSON',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_analysis_type` (`analysis_type`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='统计分析记录表';

-- 创建统计分析报告表
CREATE TABLE IF NOT EXISTS `analysis_report` (
    `id` bigint NOT NULL COMMENT '主键ID',
    `analysis_type` varchar(32) NOT NULL COMMENT '分析类型',
    `title` varchar(128) NOT NULL COMMENT '报告标题',
    `description` varchar(512) DEFAULT NULL COMMENT '报告描述',
    `start_time` datetime NOT NULL COMMENT '报告开始时间',
    `end_time` datetime NOT NULL COMMENT '报告结束时间',
    `content_json` text COMMENT '报告内容JSON',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_analysis_type` (`analysis_type`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='统计分析报告表'; 
