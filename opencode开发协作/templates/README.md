# 多前端模板（同一后端）

## 本地启动
后端：
```bash
cd backend-springboot
mvn spring-boot:run
```

前端 Admin：
```bash
cd frontends/admin-amis
python -m http.server 7001
```

前端 OPS：
```bash
cd frontends/ops-amis
python -m http.server 7002
```

默认后端：http://localhost:8080
- OpenAPI: http://localhost:8080/spec/openapi.yaml
- Capabilities: http://localhost:8080/api/platform/capabilities

## 核心机制
- 后端菜单返回 routeKey
- 每个前端维护 route-mapping.json（routeKey -> schemaUrl）
