-- 认证授权模块初始数据

-- 插入默认OAuth客户端
INSERT INTO oauth_client (
  client_id, client_secret, client_name, redirect_uri, 
  grant_types, scope, auto_approve, 
  access_token_validity, refresh_token_validity, status
) VALUES (
  'law-firm-web', '$2a$10$RVQOxjK2diCLJB1dIJvXluhq7KzEYPDjFCj.CR7BZUxoGvh9.5vNa', '律所Web客户端', 'http://localhost:3000/callback',
  'password,authorization_code,refresh_token', 'all', 0,
  7200, 604800, 1
), (
  'law-firm-admin', '$2a$10$RVQOxjK2diCLJB1dIJvXluhq7KzEYPDjFCj.CR7BZUxoGvh9.5vNa', '律所管理端', 'http://localhost:8000/callback',
  'password,authorization_code,refresh_token', 'all', 0,
  7200, 604800, 1
), (
  'law-firm-mobile', '$2a$10$RVQOxjK2diCLJB1dIJvXluhq7KzEYPDjFCj.CR7BZUxoGvh9.5vNa', '律所移动端', 'lawfirm://callback',
  'password,authorization_code,refresh_token', 'all', 0,
  7200, 604800, 1
);

-- 模拟插入社交账号绑定数据
INSERT INTO user_social (
  user_id, provider_type, provider_user_id, provider_user_name,
  union_id, verified, status
) VALUES (
  1, 'WECHAT', 'wxid_12345', '管理员',
  'unionid_12345', 1, 1
);

-- 用户登录历史示例数据
INSERT INTO user_login_history (
  user_id, login_type, ip_address, user_agent,
  login_time, login_status, device_type
) VALUES (
  1, 'PASSWORD', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36',
  NOW(), 1, 'PC'
); 