package com.demo.amis;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final DemoStore store;

  public UserController(DemoStore store) {
    this.store = store;
  }

  @GetMapping
  public ApiResponse<PageResult<DemoStore.User>> list(
      @RequestParam(value="pageNo", defaultValue="1") int pageNo,
      @RequestParam(value="pageSize", defaultValue="20") int pageSize,
      @RequestParam(value="keyword", required=false) String keyword,
      @RequestParam(value="menuId", required=false) String menuId
  ) {
    List<DemoStore.User> all = new ArrayList<DemoStore.User>(store.users.values());
    if (keyword != null && keyword.trim().length() > 0) {
      final String k = keyword.trim().toLowerCase();
      all = all.stream().filter(u ->
        (u.username!=null && u.username.toLowerCase().contains(k)) ||
        (u.realName!=null && u.realName.toLowerCase().contains(k))
      ).collect(Collectors.toList());
    }
    all.sort(Comparator.comparing(u -> u.id));
    int from = Math.max(0, (pageNo-1)*pageSize);
    int to = Math.min(all.size(), from + pageSize);
    List<DemoStore.User> page = from >= all.size() ? new ArrayList<DemoStore.User>() : all.subList(from, to);
    return ApiResponse.ok(new PageResult<DemoStore.User>(page, all.size()));
  }

  @PostMapping
  public ApiResponse<DemoStore.User> create(@RequestBody Map<String,Object> body) {
    DemoStore.User u = new DemoStore.User();
    u.id = "u" + (store.users.size()+1);
    u.username = str(body.get("username"));
    u.realName = str(body.get("realName"));
    u.orgId = str(body.get("orgId"));
    u.status = str(body.get("status"));
    u.phone = str(body.get("phone"));
    u.createdAt = java.time.OffsetDateTime.now().toString();
    store.users.put(u.id, u);
    return ApiResponse.ok(u);
  }

  @GetMapping("/{id}")
  public ApiResponse<DemoStore.User> detail(@PathVariable("id") String id) {
    DemoStore.User u = store.users.get(id);
    if (u == null) return ApiResponse.fail(404, "NOT_FOUND");
    return ApiResponse.ok(u);
  }

  @PutMapping("/{id}")
  public ApiResponse<DemoStore.User> update(@PathVariable("id") String id, @RequestBody Map<String,Object> body) {
    DemoStore.User u = store.users.get(id);
    if (u == null) return ApiResponse.fail(404, "NOT_FOUND");
    if (body.containsKey("realName")) u.realName = str(body.get("realName"));
    if (body.containsKey("orgId")) u.orgId = str(body.get("orgId"));
    if (body.containsKey("status")) u.status = str(body.get("status"));
    if (body.containsKey("phone")) u.phone = str(body.get("phone"));
    return ApiResponse.ok(u);
  }

  @DeleteMapping("/{id}")
  public ApiResponse<Object> delete(@PathVariable("id") String id) {
    store.users.remove(id);
    return ApiResponse.ok(null);
  }

  @GetMapping("/{id}/roles")
  public ApiResponse<List<String>> getRoles(@PathVariable("id") String id) {
    Set<String> rs = store.userRoles.getOrDefault(id, new HashSet<String>());
    return ApiResponse.ok(new ArrayList<String>(rs));
  }

  @PutMapping("/{id}/roles")
  public ApiResponse<Object> setRoles(@PathVariable("id") String id, @RequestBody Map<String,Object> body) {
    Object roleIdsObj = body.get("roleIds");
    Set<String> rs = new HashSet<String>();
    if (roleIdsObj instanceof List) {
      for (Object o : (List<?>) roleIdsObj) rs.add(String.valueOf(o));
    }
    store.userRoles.put(id, rs);
    return ApiResponse.ok(null);
  }

  private String str(Object o) { return o == null ? null : String.valueOf(o); }
}
