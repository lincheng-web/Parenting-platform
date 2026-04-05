# 管理员系统数据库表设计

## 1. 管理员表（sys_admin）

| 字段名 | 数据类型 | 长度 | 约束 | 描述 |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | 20 | `PRIMARY KEY, AUTO_INCREMENT` | 管理员ID |
| `username` | `VARCHAR` | 50 | `NOT NULL, UNIQUE` | 用户名 |
| `password_hash` | `VARCHAR` | 100 | `NOT NULL` | 密码哈希 |
| `real_name` | `VARCHAR` | 50 | `NOT NULL` | 真实姓名 |
| `phone` | `VARCHAR` | 20 | `UNIQUE` | 手机号 |
| `email` | `VARCHAR` | 100 | `UNIQUE` | 邮箱 |
| `status` | `TINYINT` | 1 | `NOT NULL DEFAULT 1` | 状态：1-启用，0-禁用 |
| `create_time` | `DATETIME` | - | `NOT NULL DEFAULT CURRENT_TIMESTAMP` | 创建时间 |
| `update_time` | `DATETIME` | - | `NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP` | 更新时间 |

## 2. 角色表（sys_role）

| 字段名 | 数据类型 | 长度 | 约束 | 描述 |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | 20 | `PRIMARY KEY, AUTO_INCREMENT` | 角色ID |
| `role_name` | `VARCHAR` | 50 | `NOT NULL, UNIQUE` | 角色名称 |
| `role_code` | `VARCHAR` | 50 | `NOT NULL, UNIQUE` | 角色编码 |
| `description` | `VARCHAR` | 200 | - | 角色描述 |
| `status` | `TINYINT` | 1 | `NOT NULL DEFAULT 1` | 状态：1-启用，0-禁用 |
| `create_time` | `DATETIME` | - | `NOT NULL DEFAULT CURRENT_TIMESTAMP` | 创建时间 |
| `update_time` | `DATETIME` | - | `NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP` | 更新时间 |

## 3. 权限表（sys_permission）

| 字段名 | 数据类型 | 长度 | 约束 | 描述 |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | 20 | `PRIMARY KEY, AUTO_INCREMENT` | 权限ID |
| `permission_name` | `VARCHAR` | 100 | `NOT NULL` | 权限名称 |
| `permission_code` | `VARCHAR` | 100 | `NOT NULL, UNIQUE` | 权限编码 |
| `type` | `TINYINT` | 1 | `NOT NULL` | 权限类型：1-菜单，2-按钮，3-接口 |
| `url` | `VARCHAR` | 255 | - | 权限URL（接口地址或菜单路径） |
| `method` | `VARCHAR` | 10 | - | HTTP方法（GET, POST, PUT, DELETE等） |
| `parent_id` | `BIGINT` | 20 | `DEFAULT 0` | 父权限ID |
| `sort` | `INT` | 10 | `DEFAULT 0` | 排序 |
| `status` | `TINYINT` | 1 | `NOT NULL DEFAULT 1` | 状态：1-启用，0-禁用 |
| `create_time` | `DATETIME` | - | `NOT NULL DEFAULT CURRENT_TIMESTAMP` | 创建时间 |
| `update_time` | `DATETIME` | - | `NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP` | 更新时间 |

## 4. 菜单表（sys_menu）

| 字段名 | 数据类型 | 长度 | 约束 | 描述 |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | 20 | `PRIMARY KEY, AUTO_INCREMENT` | 菜单ID |
| `menu_name` | `VARCHAR` | 50 | `NOT NULL` | 菜单名称 |
| `path` | `VARCHAR` | 255 | `NOT NULL` | 菜单路径 |
| `component` | `VARCHAR` | 255 | - | 组件路径 |
| `icon` | `VARCHAR` | 50 | - | 菜单图标 |
| `parent_id` | `BIGINT` | 20 | `DEFAULT 0` | 父菜单ID |
| `level` | `INT` | 10 | `DEFAULT 1` | 菜单级别 |
| `sort` | `INT` | 10 | `DEFAULT 0` | 排序 |
| `status` | `TINYINT` | 1 | `NOT NULL DEFAULT 1` | 状态：1-启用，0-禁用 |
| `create_time` | `DATETIME` | - | `NOT NULL DEFAULT CURRENT_TIMESTAMP` | 创建时间 |
| `update_time` | `DATETIME` | - | `NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP` | 更新时间 |

## 5. 管理员-角色关联表（sys_admin_role）

| 字段名 | 数据类型 | 长度 | 约束 | 描述 |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | 20 | `PRIMARY KEY, AUTO_INCREMENT` | 关联ID |
| `admin_id` | `BIGINT` | 20 | `NOT NULL, FOREIGN KEY` | 管理员ID |
| `role_id` | `BIGINT` | 20 | `NOT NULL, FOREIGN KEY` | 角色ID |

## 6. 角色-权限关联表（sys_role_permission）

| 字段名 | 数据类型 | 长度 | 约束 | 描述 |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | 20 | `PRIMARY KEY, AUTO_INCREMENT` | 关联ID |
| `role_id` | `BIGINT` | 20 | `NOT NULL, FOREIGN KEY` | 角色ID |
| `permission_id` | `BIGINT` | 20 | `NOT NULL, FOREIGN KEY` | 权限ID |

## 7. 角色-菜单关联表（sys_role_menu）

| 字段名 | 数据类型 | 长度 | 约束 | 描述 |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | 20 | `PRIMARY KEY, AUTO_INCREMENT` | 关联ID |
| `role_id` | `BIGINT` | 20 | `NOT NULL, FOREIGN KEY` | 角色ID |
| `menu_id` | `BIGINT` | 20 | `NOT NULL, FOREIGN KEY` | 菜单ID |

## 8. 系统日志表（sys_log）

| 字段名 | 数据类型 | 长度 | 约束 | 描述 |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | 20 | `PRIMARY KEY, AUTO_INCREMENT` | 日志ID |
| `admin_id` | `BIGINT` | 20 | `FOREIGN KEY` | 操作人ID |
| `admin_name` | `VARCHAR` | 50 | `NOT NULL` | 操作人姓名 |
| `operation` | `VARCHAR` | 200 | `NOT NULL` | 操作内容 |
| `ip` | `VARCHAR` | 50 | `NOT NULL` | 操作IP |
| `method` | `VARCHAR` | 255 | `NOT NULL` | 请求方法 |
| `params` | `TEXT` | - | - | 请求参数 |
| `result` | `TINYINT` | 1 | `NOT NULL DEFAULT 1` | 操作结果：1-成功，0-失败 |
| `error_msg` | `TEXT` | - | - | 错误信息 |
| `create_time` | `DATETIME` | - | `NOT NULL DEFAULT CURRENT_TIMESTAMP` | 操作时间 |

## 9. 系统配置表（sys_config）

| 字段名 | 数据类型 | 长度 | 约束 | 描述 |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | 20 | `PRIMARY KEY, AUTO_INCREMENT` | 配置ID |
| `config_key` | `VARCHAR` | 100 | `NOT NULL, UNIQUE` | 配置键 |
| `config_value` | `TEXT` | - | `NOT NULL` | 配置值 |
| `description` | `VARCHAR` | 200 | - | 配置描述 |
| `status` | `TINYINT` | 1 | `NOT NULL DEFAULT 1` | 状态：1-启用，0-禁用 |
| `create_time` | `DATETIME` | - | `NOT NULL DEFAULT CURRENT_TIMESTAMP` | 创建时间 |
| `update_time` | `DATETIME` | - | `NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP` | 更新时间 |
