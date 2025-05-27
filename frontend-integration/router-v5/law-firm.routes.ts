// 法律事务所管理系统路由配置 - Vue Vben Admin 5.0
import type { RouteRecordRaw } from 'vue-router';

/**
 * 法律事务所业务路由配置
 * 基于Vue Vben Admin 5.0的路由结构
 */
export const lawFirmRoutes: RouteRecordRaw[] = [
  {
    path: '/lawfirm',
    name: 'LawFirm',
    component: () => import('#/layouts/basic-layout.vue'),
    meta: {
      title: '法律事务所',
      icon: 'lucide:scale',
      order: 1,
      roles: ['admin', 'lawyer', 'assistant'],
    },
    children: [
      // 工作台
      {
        path: 'dashboard',
        name: 'LawFirmDashboard',
        component: () => import('#/views/lawfirm/dashboard/index.vue'),
        meta: {
          title: '工作台',
          icon: 'lucide:layout-dashboard',
          order: 1,
        },
      },
      // 客户管理
      {
        path: 'client',
        name: 'ClientManagement',
        meta: {
          title: '客户管理',
          icon: 'lucide:users',
          order: 2,
        },
        children: [
          {
            path: 'list',
            name: 'ClientList',
            component: () => import('#/views/lawfirm/client/list/index.vue'),
            meta: {
              title: '客户列表',
              permissions: ['client:view'],
            },
          },
          {
            path: 'detail/:id',
            name: 'ClientDetail',
            component: () => import('#/views/lawfirm/client/detail/index.vue'),
            meta: {
              title: '客户详情',
              hideInMenu: true,
              permissions: ['client:view'],
            },
          },
          {
            path: 'followup',
            name: 'ClientFollowUp',
            component: () => import('#/views/lawfirm/client/followup/index.vue'),
            meta: {
              title: '跟进管理',
              permissions: ['client:followup'],
            },
          },
        ],
      },
      // 案件管理
      {
        path: 'case',
        name: 'CaseManagement',
        meta: {
          title: '案件管理',
          icon: 'lucide:briefcase',
          order: 3,
        },
        children: [
          {
            path: 'list',
            name: 'CaseList',
            component: () => import('#/views/lawfirm/case/list/index.vue'),
            meta: {
              title: '案件列表',
              permissions: ['case:view'],
            },
          },
          {
            path: 'detail/:id',
            name: 'CaseDetail',
            component: () => import('#/views/lawfirm/case/detail/index.vue'),
            meta: {
              title: '案件详情',
              hideInMenu: true,
              permissions: ['case:view'],
            },
          },
          {
            path: 'documents',
            name: 'CaseDocuments',
            component: () => import('#/views/lawfirm/case/documents/index.vue'),
            meta: {
              title: '案件文档',
              permissions: ['case:document'],
            },
          },
          {
            path: 'timeline',
            name: 'CaseTimeline',
            component: () => import('#/views/lawfirm/case/timeline/index.vue'),
            meta: {
              title: '案件时间线',
              permissions: ['case:timeline'],
            },
          },
        ],
      },
      // 文档管理
      {
        path: 'document',
        name: 'DocumentManagement',
        meta: {
          title: '文档管理',
          icon: 'lucide:file-text',
          order: 4,
        },
        children: [
          {
            path: 'list',
            name: 'DocumentList',
            component: () => import('#/views/lawfirm/document/list/index.vue'),
            meta: {
              title: '文档库',
              permissions: ['document:view'],
            },
          },
          {
            path: 'template',
            name: 'DocumentTemplate',
            component: () => import('#/views/lawfirm/document/template/index.vue'),
            meta: {
              title: '文档模板',
              permissions: ['document:template'],
            },
          },
          {
            path: 'approval',
            name: 'DocumentApproval',
            component: () => import('#/views/lawfirm/document/approval/index.vue'),
            meta: {
              title: '文档审批',
              permissions: ['document:approval'],
            },
          },
        ],
      },
      // 财务管理
      {
        path: 'finance',
        name: 'FinanceManagement',
        meta: {
          title: '财务管理',
          icon: 'lucide:banknote',
          order: 5,
          roles: ['admin', 'finance'],
        },
        children: [
          {
            path: 'billing',
            name: 'FinanceBilling',
            component: () => import('#/views/lawfirm/finance/billing/index.vue'),
            meta: {
              title: '计费管理',
              permissions: ['finance:billing'],
            },
          },
          {
            path: 'invoice',
            name: 'FinanceInvoice',
            component: () => import('#/views/lawfirm/finance/invoice/index.vue'),
            meta: {
              title: '发票管理',
              permissions: ['finance:invoice'],
            },
          },
          {
            path: 'payment',
            name: 'FinancePayment',
            component: () => import('#/views/lawfirm/finance/payment/index.vue'),
            meta: {
              title: '收付款管理',
              permissions: ['finance:payment'],
            },
          },
          {
            path: 'report',
            name: 'FinanceReport',
            component: () => import('#/views/lawfirm/finance/report/index.vue'),
            meta: {
              title: '财务报表',
              permissions: ['finance:report'],
            },
          },
        ],
      },
      // AI助手
      {
        path: 'ai',
        name: 'AIAssistant',
        meta: {
          title: 'AI助手',
          icon: 'lucide:brain',
          order: 6,
        },
        children: [
          {
            path: 'chat',
            name: 'AIChat',
            component: () => import('#/views/lawfirm/ai/chat/index.vue'),
            meta: {
              title: '智能对话',
              permissions: ['ai:chat'],
            },
          },
          {
            path: 'document-analysis',
            name: 'AIDocumentAnalysis',
            component: () => import('#/views/lawfirm/ai/document-analysis/index.vue'),
            meta: {
              title: '文档分析',
              permissions: ['ai:document'],
            },
          },
          {
            path: 'risk-assessment',
            name: 'AIRiskAssessment',
            component: () => import('#/views/lawfirm/ai/risk-assessment/index.vue'),
            meta: {
              title: '风险评估',
              permissions: ['ai:risk'],
            },
          },
          {
            path: 'legal-research',
            name: 'AILegalResearch',
            component: () => import('#/views/lawfirm/ai/legal-research/index.vue'),
            meta: {
              title: '法律研究',
              permissions: ['ai:research'],
            },
          },
        ],
      },
      // 系统管理
      {
        path: 'system',
        name: 'SystemManagement',
        meta: {
          title: '系统管理',
          icon: 'lucide:settings',
          order: 7,
          roles: ['admin'],
        },
        children: [
          {
            path: 'user',
            name: 'SystemUser',
            component: () => import('#/views/lawfirm/system/user/index.vue'),
            meta: {
              title: '用户管理',
              permissions: ['system:user'],
            },
          },
          {
            path: 'role',
            name: 'SystemRole',
            component: () => import('#/views/lawfirm/system/role/index.vue'),
            meta: {
              title: '角色管理',
              permissions: ['system:role'],
            },
          },
          {
            path: 'menu',
            name: 'SystemMenu',
            component: () => import('#/views/lawfirm/system/menu/index.vue'),
            meta: {
              title: '菜单管理',
              permissions: ['system:menu'],
            },
          },
          {
            path: 'org',
            name: 'SystemOrg',
            component: () => import('#/views/lawfirm/system/org/index.vue'),
            meta: {
              title: '组织管理',
              permissions: ['system:org'],
            },
          },
          {
            path: 'dict',
            name: 'SystemDict',
            component: () => import('#/views/lawfirm/system/dict/index.vue'),
            meta: {
              title: '字典管理',
              permissions: ['system:dict'],
            },
          },
        ],
      },
      // 系统监控
      {
        path: 'monitor',
        name: 'SystemMonitor',
        meta: {
          title: '系统监控',
          icon: 'lucide:monitor',
          order: 8,
          roles: ['admin'],
        },
        children: [
          {
            path: 'server',
            name: 'MonitorServer',
            component: () => import('#/views/lawfirm/monitor/server/index.vue'),
            meta: {
              title: '服务器监控',
              permissions: ['monitor:server'],
            },
          },
          {
            path: 'application',
            name: 'MonitorApplication',
            component: () => import('#/views/lawfirm/monitor/application/index.vue'),
            meta: {
              title: '应用监控',
              permissions: ['monitor:app'],
            },
          },
          {
            path: 'database',
            name: 'MonitorDatabase',
            component: () => import('#/views/lawfirm/monitor/database/index.vue'),
            meta: {
              title: '数据库监控',
              permissions: ['monitor:db'],
            },
          },
          {
            path: 'alert',
            name: 'MonitorAlert',
            component: () => import('#/views/lawfirm/monitor/alert/index.vue'),
            meta: {
              title: '告警管理',
              permissions: ['monitor:alert'],
            },
          },
        ],
      },
    ],
  },
];

/**
 * 注册法律事务所路由
 */
export function setupLawFirmRoutes() {
  return lawFirmRoutes;
} 