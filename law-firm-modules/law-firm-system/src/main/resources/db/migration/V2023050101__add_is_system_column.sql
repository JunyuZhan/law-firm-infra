-- Add is_system column to sys_config table
-- 是否系统内置（0否/1是）
ALTER TABLE sys_config 
ADD is_system BOOLEAN NOT NULL DEFAULT 0; 