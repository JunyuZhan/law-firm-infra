-- 证据模块常用字典数据初始化
-- 版本: V13002

-- 初始化证据标签
INSERT INTO evidence_tag (name, description, create_time)
VALUES
  ('书证', '书面证据', NOW()),
  ('物证', '实物证据', NOW()),
  ('证人证言', '证人提供的证言', NOW()),
  ('视听资料', '音视频等资料', NOW());

-- 初始化证据目录（演示）
INSERT INTO evidence_catalog (case_id, name, level, leaf, create_time)
VALUES
  (1, '案件A证据目录', 1, 0, NOW()),
  (1, '案件A-书证', 2, 1, NOW()),
  (1, '案件A-物证', 2, 1, NOW());

-- 初始化证据（演示）
INSERT INTO evidence (case_id, name, type, source, proof_matter, submit_time, create_time)
VALUES
  (1, '购销合同', '书证', '原告提交', '证明买卖关系', NOW(), NOW()),
  (1, '监控录像', '视听资料', '被告提交', '证明现场情况', NOW(), NOW()); 