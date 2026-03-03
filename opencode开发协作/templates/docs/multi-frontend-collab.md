# 多前端模板说明（routeKey + route-mapping）
- 后端：菜单返回 routeKey（稳定标识）
- 前端：route-mapping.json 把 routeKey 映射到自己的 schemaUrl（或页面实现）
- 前端启动：先调用 /api/platform/capabilities 校验能力和兼容版本
