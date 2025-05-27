/**
 * Vue Vben Admin 5.0 配置文件
 * Law-Firm-Infra 前端集成配置
 * 
 * 包含：HTTP配置、类型定义、请求/响应拦截器
 */

import type { AxiosRequestConfig, AxiosResponse } from 'axios';
import { requestClient } from '#/api/request';

/**
 * 法律事务所系统API配置
 */

// API基础配置
export const API_CONFIG = {
  baseURL: import.meta.env.VITE_GLOB_API_URL || 'http://localhost:8080',
  apiPrefix: import.meta.env.VITE_GLOB_API_URL_PREFIX || '/api/v1',
  timeout: 10 * 1000,
  withCredentials: false,
};

// 创建请求实例
export const lawFirmApi = requestClient.create({
  baseURL: `${API_CONFIG.baseURL}${API_CONFIG.apiPrefix}`,
  timeout: API_CONFIG.timeout,
});

// 法律事务所系统特定的API类型定义
export interface LawFirmApiResult<T = any> {
  code: number;
  data: T;
  message: string;
  success: boolean;
  result: T;
}

// 分页结果类型
export interface PageResult<T = any> {
  records: T[];
  total: number;
  pageNum: number;
  pageSize: number;
}

// 分页参数类型
export interface PageParams {
  pageNum: number;
  pageSize: number;
  [key: string]: any;
}

// 用户信息类型
export interface UserInfo {
  id: number;
  username: string;
  realName: string;
  email: string;
  phone: string;
  avatar?: string;
  roles: string[];
  permissions: string[];
  tenantId: number;
}

// 登录参数
export interface LoginParams {
  username: string;
  password: string;
  captcha?: string;
  captchaKey?: string;
}

// 登录结果
export interface LoginResult {
  token: string;
  userInfo: UserInfo;
  expires: number;
}

// 菜单类型
export interface MenuInfo {
  id: number;
  parentId: number;
  name: string;
  path: string;
  component: string;
  icon?: string;
  orderNum: number;
  type: number; // 0=目录 1=菜单 2=按钮
  visible: boolean;
  children?: MenuInfo[];
}

// 客户信息类型
export interface ClientInfo {
  id: number;
  name: string;
  type: number; // 1=个人 2=企业
  phone: string;
  email: string;
  address: string;
  status: number;
  createTime: string;
  updateTime: string;
}

// 案件信息类型
export interface CaseInfo {
  id: number;
  caseNumber: string;
  title: string;
  type: number;
  status: number;
  clientId: number;
  clientName: string;
  lawyerId: number;
  lawyerName: string;
  createTime: string;
  updateTime: string;
}

// 文档信息类型
export interface DocumentInfo {
  id: number;
  name: string;
  type: string;
  size: number;
  path: string;
  caseId?: number;
  clientId?: number;
  uploadTime: string;
  status: number;
}

// 跟进记录类型
export interface FollowUpInfo {
  id: number;
  clientId: number;
  caseId?: number;
  content: string;
  nextFollowTime: string;
  status: number;
  assigneeId: number;
  assigneeName: string;
  createTime: string;
}

// 监控数据类型
export interface MonitorData {
  serverMonitor: {
    serverName: string;
    cpuUsage: number;
    memoryUsage: number;
    diskUsage: number;
    networkRx: number;
    networkTx: number;
  };
  appMonitor: {
    appName: string;
    jvmMemoryUsed: number;
    jvmMemoryMax: number;
    threadCount: number;
    gcCount: number;
  };
  dbMonitor: {
    dbName: string;
    activeConnections: number;
    maxConnections: number;
    qps: number;
    tps: number;
    slowQueries: number;
  };
}

// 响应数据转换函数
export function transformResponse<T>(response: LawFirmApiResult<T>): T {
  const { success, result, data, message } = response;
  
  if (success) {
    return result || data;
  }
  
  throw new Error(message || '请求失败');
}

// 请求拦截器配置
lawFirmApi.interceptors.request.use(
  (config) => {
    // 添加认证token
    const token = localStorage.getItem('token');
    if (token) {
      config.headers = config.headers || {};
      config.headers.Authorization = `Bearer ${token}`;
    }
    
    // 添加租户信息
    const tenantId = localStorage.getItem('tenantId');
    if (tenantId) {
      config.headers = config.headers || {};
      config.headers['X-Tenant-Id'] = tenantId;
    }
    
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器配置
lawFirmApi.interceptors.response.use(
  (response) => {
    const { data } = response;
    
    // 直接返回业务数据
    if (data.success) {
      return data.result || data.data;
    }
    
    // 处理业务错误
    throw new Error(data.message || '请求失败');
  },
  (error) => {
    // 处理HTTP错误
    if (error.response?.status === 401) {
      // 清除token并跳转登录
      localStorage.removeItem('token');
      localStorage.removeItem('userInfo');
      window.location.href = '/login';
    }
    
    return Promise.reject(error);
  }
);

export default lawFirmApi; 