/**
 * 客户管理相关API接口
 * Vue Vben Admin 5.0 + Law-Firm-Infra
 * 
 * 注意：这些是相对路径，会自动拼接 baseURL: http://localhost:8080/api/v1
 */
import { lawFirmApi } from '../vben-5.0-config';
import type { ClientInfo, PageResult, FollowUpInfo } from '../vben-5.0-config';

/**
 * 客户管理API端点枚举
 */
enum ClientApi {
  // 客户相关 - 对应 ClientConstants.API_PREFIX = "/api/v1/clients"
  ClientList = '/clients/list',                 // ✅ ClientController.list()
  ClientDetail = '/clients',                    // ✅ ClientController.getClientById() - GET /{id}
  ClientCreate = '/clients',                    // ✅ ClientController.add() - POST
  ClientUpdate = '/clients',                    // ✅ ClientController.update() - PUT
  ClientDelete = '/clients',                    // ✅ ClientController.remove() - DELETE /{id}
  ClientStatus = '/clients',                    // ✅ ClientController.updateStatus() - PUT /{id}/status/{status}
  ClientCredit = '/clients',                    // ✅ ClientController.updateCreditLevel() - PUT /{id}/credit/{creditLevel}
  
  // 联系人相关 - 对应 ClientConstants.API_CONTACT_PREFIX = "/api/v1/contacts"
  ContactList = '/contacts/list',               // ✅ ContactController.list() - GET /list/{clientId}
  ContactPage = '/contacts/page',               // ✅ ContactController.page() - GET /page/{clientId}
  ContactDetail = '/contacts',                  // ✅ ContactController.getById() - GET /{id}
  ContactDefault = '/contacts/default',         // ✅ ContactController.getDefaultContact() - GET /default/{clientId}
  ContactCreate = '/contacts',                  // ✅ ContactController.add() - POST
  ContactUpdate = '/contacts',                  // ✅ ContactController.update() - PUT
  ContactDelete = '/contacts',                  // ✅ ContactController.remove() - DELETE /{id}
  
  // 跟进记录相关 - 对应 ClientConstants.API_FOLLOW_UP_PREFIX = "/api/v1/client-follow-ups"
  FollowUpList = '/client-follow-ups/list',     // ✅ FollowUpController.list() - GET /list/{clientId}
  FollowUpPage = '/client-follow-ups/page',     // ✅ FollowUpController.page() - GET /page
  FollowUpDetail = '/client-follow-ups',        // ✅ FollowUpController.getById() - GET /{id}
  FollowUpCreate = '/client-follow-ups',        // ✅ FollowUpController.add() - POST
  FollowUpUpdate = '/client-follow-ups',        // ✅ FollowUpController.update() - PUT
  FollowUpDelete = '/client-follow-ups',        // ✅ FollowUpController.remove() - DELETE /{id}
  
  // 标签相关 - 对应 ClientConstants.API_TAG_PREFIX = "/api/v1/client-tags"
  TagList = '/client-tags/list',                // ✅ TagController.list() - GET /list
  TagByType = '/client-tags/list',              // ✅ TagController.listByType() - GET /list/{tagType}
  TagDetail = '/client-tags',                   // ✅ TagController.getById() - GET /{id}
  TagCreate = '/client-tags',                   // ✅ TagController.add() - POST
  TagUpdate = '/client-tags',                   // ✅ TagController.update() - PUT
  TagDelete = '/client-tags',                   // ✅ TagController.remove() - DELETE /{id}
  ClientTags = '/client-tags/client',           // ✅ TagController.getClientTags() - GET /client/{clientId}
  AddTagToClient = '/client-tags/client',       // ✅ TagController.addTagToClient() - POST /client/{clientId}/tag/{tagId}
  RemoveTagFromClient = '/client-tags/client',  // ✅ TagController.removeTagFromClient() - DELETE /client/{clientId}/tag/{tagId}
  SetClientTags = '/client-tags/client',        // ✅ TagController.setClientTags() - PUT /client/{clientId}/tags
  
  // 分类相关 - 对应 ClientConstants.API_CATEGORY_PREFIX = "/api/v1/client-categories"
  CategoryTree = '/client-categories/tree',     // ✅ CategoryController.tree() - GET /tree
  CategoryList = '/client-categories/list',     // ✅ CategoryController.list() - GET /list
  CategoryDetail = '/client-categories',        // ✅ CategoryController.getById() - GET /{id}
  CategoryCreate = '/client-categories',        // ✅ CategoryController.add() - POST
  CategoryUpdate = '/client-categories',        // ✅ CategoryController.update() - PUT
  CategoryDelete = '/client-categories',        // ✅ CategoryController.remove() - DELETE /{id}
}

// ========================= 客户基础管理 API =========================

/**
 * 获取客户列表
 */
export async function getClientList(params?: {
  name?: string;
  type?: number;
  status?: number;
}): Promise<ClientInfo[]> {
  return lawFirmApi.get(ClientApi.ClientList, { params });
}

/**
 * 获取客户详情
 */
export async function getClientDetail(id: number): Promise<ClientInfo> {
  return lawFirmApi.get(`${ClientApi.ClientDetail}/${id}`);
}

/**
 * 创建客户
 */
export async function createClient(data: Omit<ClientInfo, 'id' | 'createTime' | 'updateTime'>): Promise<number> {
  return lawFirmApi.post(ClientApi.ClientCreate, data);
}

/**
 * 更新客户
 */
export async function updateClient(data: Partial<ClientInfo> & { id: number }): Promise<boolean> {
  return lawFirmApi.put(ClientApi.ClientUpdate, data);
}

/**
 * 删除客户
 */
export async function deleteClient(id: number): Promise<boolean> {
  return lawFirmApi.delete(`${ClientApi.ClientDelete}/${id}`);
}

/**
 * 更新客户状态
 */
export async function updateClientStatus(id: number, status: number): Promise<boolean> {
  return lawFirmApi.put(`${ClientApi.ClientStatus}/${id}/status/${status}`);
}

/**
 * 更新客户信用等级
 */
export async function updateClientCreditLevel(id: number, creditLevel: string): Promise<boolean> {
  return lawFirmApi.put(`${ClientApi.ClientCredit}/${id}/credit/${creditLevel}`);
}

// ========================= 客户联系人 API =========================

/**
 * 获取客户联系人列表
 */
export async function getClientContacts(clientId: number): Promise<any[]> {
  return lawFirmApi.get(`${ClientApi.ContactList}/${clientId}`);
}

/**
 * 获取客户默认联系人
 */
export async function getDefaultContact(clientId: number): Promise<any> {
  return lawFirmApi.get(`${ClientApi.ContactDefault}/${clientId}`);
}

// ========================= 客户跟进记录 API =========================

/**
 * 获取客户跟进记录列表
 */
export async function getClientFollowUps(clientId: number): Promise<FollowUpInfo[]> {
  return lawFirmApi.get(`${ClientApi.FollowUpList}/${clientId}`);
}

/**
 * 分页查询跟进记录
 */
export async function getFollowUpPage(params: {
  clientId?: number;
  status?: number;
  pageNum?: number;
  pageSize?: number;
}): Promise<PageResult<FollowUpInfo>> {
  return lawFirmApi.get(ClientApi.FollowUpPage, { params });
}

/**
 * 创建跟进记录
 */
export async function createFollowUp(data: Omit<FollowUpInfo, 'id' | 'createTime'>): Promise<number> {
  return lawFirmApi.post(ClientApi.FollowUpCreate, data);
}

// ========================= 客户标签管理 API =========================

/**
 * 获取所有标签列表
 */
export async function getTagList(): Promise<any[]> {
  return lawFirmApi.get(ClientApi.TagList);
}

/**
 * 获取客户标签
 */
export async function getClientTags(clientId: number): Promise<any[]> {
  return lawFirmApi.get(`${ClientApi.ClientTags}/${clientId}`);
}

/**
 * 设置客户标签
 */
export async function setClientTags(clientId: number, tagIds: number[]): Promise<boolean> {
  return lawFirmApi.put(`${ClientApi.SetClientTags}/${clientId}/tags`, tagIds);
}

// ========================= 客户分类管理 API =========================

/**
 * 获取分类树
 */
export async function getCategoryTree(): Promise<any[]> {
  return lawFirmApi.get(ClientApi.CategoryTree);
}

/**
 * 获取分类列表
 */
export async function getCategoryList(): Promise<any[]> {
  return lawFirmApi.get(ClientApi.CategoryList);
} 