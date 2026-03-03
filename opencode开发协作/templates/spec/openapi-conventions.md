# OpenAPI 约定（用于自动推断模块）

1. 每个 operation 必须且只有 1 个 tag（模块名）
2. x-permission 必填
3. 列表分页：pageNo/pageSize；响应：data.items/data.total
4. tags[].x-menu 用于默认菜单/路由生成（title/icon/path/schemaRef/order）
5. tags[].x-actions 可补充非 API 动作（export/resetPwd 等）
