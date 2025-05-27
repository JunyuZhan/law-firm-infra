/**
 * 认证相关API接口
 * Vue Vben Admin 5.0 + Law-Firm-Infra
 * 
 * 注意：这些是相对路径，会自动拼接 baseURL: http://localhost:8080/api/v1
 */
import { lawFirmApi } from '../vben-5.0-config';
import type { LoginParams, LoginResult, UserInfo } from '../vben-5.0-config';

/**
 * 认证API端点枚举
 */
enum AuthApi {
  // 认证相关 - AuthController
  Login = '/auth/login',                          // 最终: /api/v1/auth/login
  Logout = '/auth/logout',                        // 最终: /api/v1/auth/logout  
  RefreshToken = '/auth/refreshToken',            // 最终: /api/v1/auth/refreshToken
  GetCaptcha = '/auth/getCaptcha',               // 最终: /api/v1/auth/getCaptcha
  
  // 用户相关 - UserController
  GetUserInfo = '/users/getUserInfo',             // 最终: /api/v1/users/getUserInfo
  GetPermCode = '/users/getUserPermissions',     // 最终: /api/v1/users/getUserPermissions (需要后端补充)
  GetMenus = '/users/getUserMenus',              // 最终: /api/v1/users/getUserMenus (需要后端补充)
}

/**
 * 登录接口
 */
export async function loginApi(params: LoginParams): Promise<LoginResult> {
  return lawFirmApi.post(AuthApi.Login, params);
}

/**
 * 获取用户信息
 */
export async function getUserInfo(): Promise<UserInfo> {
  return lawFirmApi.get(AuthApi.GetUserInfo);
}

/**
 * 获取用户权限码
 */
export async function getPermCode(): Promise<string[]> {
  return lawFirmApi.get(AuthApi.GetPermCode);
}

/**
 * 获取用户菜单
 */
export async function getUserMenus(): Promise<any[]> {
  return lawFirmApi.get(AuthApi.GetMenus);
}

/**
 * 登出接口
 */
export async function doLogout(): Promise<void> {
  return lawFirmApi.get(AuthApi.Logout);  // ✅ 修正：后端是GET请求
}

/**
 * 刷新token
 */
export async function refreshTokenApi(refreshToken: string): Promise<LoginResult> {
  return lawFirmApi.post(AuthApi.RefreshToken, { refreshToken });  // ✅ 修正：需要传递refreshToken参数
}

/**
 * 获取验证码
 */
export async function getCaptcha(): Promise<{
  captchaKey: string;
  captchaImage: string;
}> {
  return lawFirmApi.get(AuthApi.GetCaptcha);
}

/**
 * 检查权限
 */
export function hasPermission(permission: string, userPermissions: string[]): boolean {
  return userPermissions.includes(permission) || userPermissions.includes('*:*:*');
}

/**
 * 检查角色
 */
export function hasRole(role: string, userRoles: string[]): boolean {
  return userRoles.includes(role) || userRoles.includes('admin');
} 