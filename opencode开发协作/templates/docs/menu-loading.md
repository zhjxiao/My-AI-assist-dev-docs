# 菜单加载与兜底策略

优先级：
1) GET /api/menus/tree?scene=SIDEBAR 成功且非空 -> 用后端菜单
2) 否则 fallback：/schemas/_system/menu.json（openapi tags.x-menu 生成）

打开 tab：建议调用 /api/menus/{menuId}/config/resolve 一次拿齐生效 bundle（支持灰度）。
