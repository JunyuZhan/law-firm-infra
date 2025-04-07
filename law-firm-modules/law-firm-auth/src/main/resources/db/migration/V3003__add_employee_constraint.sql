-- 先创建employee表（如果不存在），解决表依赖问题
CREATE TABLE IF NOT EXISTS employee (
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  user_id BIGINT(20) COMMENT '关联用户ID',
  department_id BIGINT(20) COMMENT '部门ID',
  position VARCHAR(100) COMMENT '职位名称',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';

-- 添加员工表外键约束
ALTER TABLE employee ADD CONSTRAINT fk_employee_user_id FOREIGN KEY (user_id) REFERENCES sys_user(id);

-- 添加员工表索引
CREATE INDEX idx_employee_department_id ON employee(department_id);
CREATE INDEX idx_employee_position ON employee(position); 