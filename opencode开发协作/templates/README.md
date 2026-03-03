# OpenCode + AMis 平台闭环生成套件（示例脚手架）

本 zip 包含：
- OpenAPI vendor extensions 规范（x-permission/x-ui/x-dict/x-mask/x-audit 等）
- 菜单/权限/动作/字段/数据范围策略的版本化配置（草稿/发布/回滚/灰度/审计/导入导出）OpenAPI 示例片段
- AMis schema 模板：菜单管理、RBAC、菜单动作/字段/动作策略、版本管理（diff/copy/rebuild/summary）等
- OpenCode Prompts：自动推断模块、批量生成 schema、生成菜单/路由/权限树/动作目录/字段目录/策略菜谱等

> 注意：这是“工程化资产包 + 示例 OpenAPI”，不是一个可直接运行的前端/后端工程。
> 你可以把这些文件拷到你现有仓库的对应目录，再按你们的实际 API basePath、认证方式、路由渲染入口做轻量适配。
