/**
 * 案件事件定义包
 * 
 * 本包定义了案件模块所有的事件类型，包括：
 * - 案件事件基类 {@link com.lawfirm.model.cases.event.CaseEvent}
 * - 案件创建事件 {@link com.lawfirm.model.cases.event.CaseCreatedEvent}
 * - 案件状态变更事件 {@link com.lawfirm.model.cases.event.CaseStatusChangedEvent}
 * - 案件文档添加事件 {@link com.lawfirm.model.cases.event.CaseDocumentAddedEvent}
 * 
 * 这些事件将在业务模块中被实例化并发布到事件总线，
 * 由适当的监听器处理以实现系统间的松耦合通信。
 * 
 * 所有的事件类都应继承自 {@link com.lawfirm.model.cases.event.CaseEvent} 基类。
 */
package com.lawfirm.model.cases.event; 