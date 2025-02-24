# 菜单管理接口

## 1. 获取菜单列表

### 接口说明
- 接口路径：`GET /api/system/menu/list`
- 接口说明：获取菜单列表(树形结构)

### 请求参数
```json
{
    "menuName": "",              // 菜单名称
    "status": 1                  // 状态(0-禁用，1-正常)
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "menuId": 1,
            "parentId": 0,
            "menuName": "系统管理",
            "menuType": "M",           // 菜单类型（M目录 C菜单 F按钮）
            "orderNum": 1,             // 显示顺序
            "path": "system",          // 路由地址
            "component": "Layout",     // 组件路径
            "perms": "",              // 权限标识
            "icon": "system",         // 菜单图标
            "visible": "0",           // 显示状态（0显示 1隐藏）
            "status": "0",            // 菜单状态（0正常 1停用）
            "isFrame": "1",           // 是否外链（0是 1否）
            "isCache": "0",           // 是否缓存（0缓存 1不缓存）
            "createTime": "2024-01-01 00:00:00",
            "children": [
                {
                    "menuId": 2,
                    "parentId": 1,
                    "menuName": "用户管理",
                    "menuType": "C",
                    "orderNum": 1,
                    "path": "user",
                    "component": "system/user/index",
                    "perms": "system:user:list",
                    "icon": "user",
                    "visible": "0",
                    "status": "0",
                    "isFrame": "1",
                    "isCache": "0",
                    "createTime": "2024-01-01 00:00:00"
                }
            ]
        }
    ],
    "success": true
}
```

## 2. 获取菜单详情

### 接口说明
- 接口路径：`GET /api/system/menu/{menuId}`
- 接口说明：获取菜单详细信息

### 请求参数
- menuId：菜单ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "menuId": 1,
        "parentId": 0,
        "menuName": "系统管理",
        "menuType": "M",
        "orderNum": 1,
        "path": "system",
        "component": "Layout",
        "perms": "",
        "icon": "system",
        "visible": "0",
        "status": "0",
        "isFrame": "1",
        "isCache": "0",
        "createTime": "2024-01-01 00:00:00"
    },
    "success": true
}
```

## 3. 新增菜单

### 接口说明
- 接口路径：`POST /api/system/menu`
- 接口说明：新增菜单信息

### 请求参数
```json
{
    "parentId": 0,               // 父菜单ID
    "menuName": "系统管理",       // 菜单名称
    "menuType": "M",            // 菜单类型（M目录 C菜单 F按钮）
    "orderNum": 1,              // 显示顺序
    "path": "system",           // 路由地址
    "component": "Layout",      // 组件路径
    "perms": "",               // 权限标识
    "icon": "system",          // 菜单图标
    "visible": "0",            // 显示状态（0显示 1隐藏）
    "status": "0",             // 菜单状态（0正常 1停用）
    "isFrame": "1",            // 是否外链（0是 1否）
    "isCache": "0"             // 是否缓存（0缓存 1不缓存）
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "新增成功",
    "success": true
}
```

## 4. 修改菜单

### 接口说明
- 接口路径：`PUT /api/system/menu`
- 接口说明：修改菜单信息

### 请求参数
```json
{
    "menuId": 1,                // 菜单ID
    "parentId": 0,              // 父菜单ID
    "menuName": "系统管理",       // 菜单名称
    "menuType": "M",            // 菜单类型（M目录 C菜单 F按钮）
    "orderNum": 1,              // 显示顺序
    "path": "system",           // 路由地址
    "component": "Layout",      // 组件路径
    "perms": "",               // 权限标识
    "icon": "system",          // 菜单图标
    "visible": "0",            // 显示状态（0显示 1隐藏）
    "status": "0",             // 菜单状态（0正常 1停用）
    "isFrame": "1",            // 是否外链（0是 1否）
    "isCache": "0"             // 是否缓存（0缓存 1不缓存）
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "修改成功",
    "success": true
}
```

## 5. 删除菜单

### 接口说明
- 接口路径：`DELETE /api/system/menu/{menuId}`
- 接口说明：删除菜单信息

### 请求参数
- menuId：菜单ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

## 6. 获取菜单下拉树列表

### 接口说明
- 接口路径：`GET /api/system/menu/treeselect`
- 接口说明：获取菜单下拉树列表

### 请求参数
无

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "id": 1,
            "label": "系统管理",
            "children": [
                {
                    "id": 2,
                    "label": "用户管理"
                }
            ]
        }
    ],
    "success": true
}
```

## 7. 获取角色菜单树

### 接口说明
- 接口路径：`GET /api/system/menu/roleMenuTreeselect/{roleId}`
- 接口说明：获取角色菜单树

### 请求参数
- roleId：角色ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "menus": [              // 菜单树
            {
                "id": 1,
                "label": "系统管理",
                "children": [
                    {
                        "id": 2,
                        "label": "用户管理"
                    }
                ]
            }
        ],
        "checkedKeys": [1, 2]   // 选中的菜单ID列表
    },
    "success": true
}
``` 