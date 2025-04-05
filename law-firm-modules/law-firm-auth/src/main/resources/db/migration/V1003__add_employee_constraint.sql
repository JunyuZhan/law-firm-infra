-- 添加员工表外键约束
ALTER TABLE employee ADD CONSTRAINT fk_employee_user_id FOREIGN KEY (user_id) REFERENCES sys_user(id);

-- 添加员工表索引
CREATE INDEX idx_employee_department_id ON employee(department_id);
CREATE INDEX idx_employee_position ON employee(position); 