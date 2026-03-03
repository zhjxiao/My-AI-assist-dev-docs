# OpenAPI Vendor Extensions（生成友好规范）

## x-permission（接口权限点）
- 位置：paths.*.*（operation）
- 类型：string
- 示例：`x-permission: "user:create"`
- 用途：前端按钮/入口可见控制；后端鉴权（推荐同源）

## x-ui（字段 UI 信息）
- 位置：components.schemas.*.properties.*
- 类型：object
- 常用字段：
  - control: amis 控件 type 或自研控件（org-selector/group-selector/dict-select/user-picker）
  - label/placeholder/order/layout/required/visibleOn/enableWhen

## x-dict（字典/枚举来源）
- 位置：schema.properties.*
- type: enum|remote
- remote：`{code, api, valueField, labelField}`
- 生成规则：remote → dict-select(dictCode=code)

## x-mask（脱敏）
- 位置：schema.properties.*
- `{type: phone|idCard|email|name|amount|custom, rule?: string}`
- 建议：后端返回已脱敏值；查看原值需额外权限 `mask:unmask`

## x-audit（审计）
- 位置：operation
- `{enabled, action, keys[]}`
